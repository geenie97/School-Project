#include "includes.h"
#define F_SCK 40000UL  // SCK 클록 값 = 40 Khz
#include <avr/io.h>
#include <avr/interrupt.h>
#define  TASK_STK_SIZE  OS_TASK_DEF_STK_SIZE
#define  N_TASKS        5


#define UCHAR unsigned char // UCHAR 정의
#define USHORT unsigned short // USHORT 정의
#define ATS75_ADDR 0x98 // 0b10011000, 7비트를 1비트 left shift
#define ATS75_CONFIG_REG 1
#define ATS75_TEMP_REG 0

#define ALARM 29
#define WANT 25;

#define F_CPU   16000000UL   // CPU frequency = 16 Mhz
#include <util/delay.h>


volatile INT16U   FndNum; //FND에출력하는 현재 온도
volatile INT16U   WantNum; //FND에 출력하는 원하는 온도
void* level[2];
volatile INT8U state; //원하는 온도(0)와 현재온도(1)를 번갈아가면서 표기하기위한 state
//volatile INT8U WANT =25;
volatile INT8U LED;
INT8U err;

OS_STK    TaskStk[N_TASKS][TASK_STK_SIZE];
OS_EVENT* Mbox;
OS_EVENT* Mqueue;
OS_FLAG_GRP* e_grp;

void  TemperatureTask(void* data);
void  FndTask(void* data);
void WantTask(void* data);
void  FndDisplayTask(void* data);
void AlarmTask(void* data);

int main(void)
{
	OSInit();
	OS_ENTER_CRITICAL();
	TCCR0 = 0x07;
	TIMSK = _BV(TOIE0);
	TCNT0 = 256 - (CPU_CLOCK_HZ / OS_TICKS_PER_SEC / 1024);
	DDRA = 0xff;
	OS_EXIT_CRITICAL();


	Mbox = OSMboxCreate((void*)0);

	Mqueue = OSQCreate(&level[0], 2);
	e_grp = OSFlagCreate(0x00, &err);

	OSTaskCreate(AlarmTask, (void*)0, (void*)& TaskStk[0][TASK_STK_SIZE - 1], 0);
	OSTaskCreate(TemperatureTask, (void*)0, (void*)& TaskStk[1][TASK_STK_SIZE - 1], 1);
	OSTaskCreate(FndTask, (void*)0, (void*)& TaskStk[2][TASK_STK_SIZE - 1], 2);
	OSTaskCreate(WantTask, (void*)0, (void*)& TaskStk[3][TASK_STK_SIZE - 1], 3);
	OSTaskCreate(FndDisplayTask, (void*)0, (void*)& TaskStk[4][TASK_STK_SIZE - 1], 4);

	OSStart();

	return 0;
}

void InitI2C()
{
	DDRC = 0xff; DDRG = 0xff;
	PORTD = 3;                   // For Pull-up override value
	SFIOR &= ~(1 << PUD);          // PUD
	TWSR = 0;                   // TWPS0 = 0, TWPS1 = 0
	TWBR = 32;                  // for 100  K Hz bus clock
	TWCR = _BV(TWEA) | _BV(TWEN);   // TWEA = Ack pulse is generated
							// TWEN = TWI 동작을 가능하게 한다*/
}
void write_twi_1byte_nopreset(UCHAR reg, UCHAR data)
{
	TWCR = (1 << TWINT) | (1 << TWSTA) | (1 << TWEN); // START 전송
	while (((TWCR & (1 << TWINT)) == 0x00) || ((TWSR & 0xf8) != 0x08 && (TWSR & 0xf8) != 0x10)); // ACK를 기다림
	TWDR = ATS75_ADDR | 0;  // SLA+W 준비, W=0
	TWCR = (1 << TWINT) | (1 << TWEN);  // SLA+W 전송
	while (((TWCR & (1 << TWINT)) == 0x00) || (TWSR & 0xf8) != 0x18);
	TWDR = reg;    // aTS75 Reg 값 준비
	TWCR = (1 << TWINT) | (1 << TWEN);  // aTS75 Reg 값 전송
	while (((TWCR & (1 << TWINT)) == 0x00) || (TWSR & 0xF8) != 0x28);
	TWDR = data;    // DATA 준비
	TWCR = (1 << TWINT) | (1 << TWEN);  // DATA 전송
	while (((TWCR & (1 << TWINT)) == 0x00) || (TWSR & 0xF8) != 0x28);
	TWCR = (1 << TWINT) | (1 << TWSTO) | (1 << TWEN); // STOP 전송
}
void write_twi_0byte_nopreset(UCHAR reg)
{
	TWCR = (1 << TWINT) | (1 << TWSTA) | (1 << TWEN); // START 전송
	while (((TWCR & (1 << TWINT)) == 0x00) || ((TWSR & 0xf8) != 0x08 && (TWSR & 0xf8) != 0x10));  // ACK를 기다림
	TWDR = ATS75_ADDR | 0; // SLA+W 준비, W=0
	TWCR = (1 << TWINT) | (1 << TWEN);  // SLA+W 전송
	while (((TWCR & (1 << TWINT)) == 0x00) || (TWSR & 0xf8) != 0x18);
	TWDR = reg;    // aTS75 Reg 값 준비
	TWCR = (1 << TWINT) | (1 << TWEN);  // aTS75 Reg 값 전송
	while (((TWCR & (1 << TWINT)) == 0x00) || (TWSR & 0xF8) != 0x28);
	TWCR = (1 << TWINT) | (1 << TWSTO) | (1 << TWEN); // STOP 전송
}
INT8U ReadTemperature(void)
{
	int value;

	TWCR = _BV(TWSTA) | _BV(TWINT) | _BV(TWEN);
	while (!(TWCR & _BV(TWINT)));

	TWDR = 0x98 + 1; //TEMP_I2C_ADDR + 1
	TWCR = _BV(TWINT) | _BV(TWEN);
	while (!(TWCR & _BV(TWINT)));

	TWCR = _BV(TWINT) | _BV(TWEN) | _BV(TWEA);
	while (!(TWCR & _BV(TWINT)));

	//온도센서는 16bit 기준으로 값을 가져오므로
	//8비트씩 2번을 받아야 한다.
	value = TWDR << 8;
	TWCR = _BV(TWINT) | _BV(TWEN);
	while (!(TWCR & _BV(TWINT)));

	value |= TWDR;
	TWCR = _BV(TWINT) | _BV(TWEN) | _BV(TWSTO);

	value >>= 8;

	TIMSK = (value >= 33) ? TIMSK | _BV(TOIE2) : TIMSK & ~_BV(TOIE2);

	return value;
}
void TemperatureTask(void* data)
{
	volatile INT8U   value;
	data = data;

	InitI2C();
	write_twi_1byte_nopreset(ATS75_CONFIG_REG, 0x00); // 9비트, Normal
	write_twi_0byte_nopreset(ATS75_TEMP_REG); // Temp Reg 포인팅

	//
	while (1) {
		OS_ENTER_CRITICAL();
		value = ReadTemperature();
		OS_EXIT_CRITICAL();

		OS_ENTER_CRITICAL();
		if (value > ALARM) {
			PORTA |= 0x0f;
			OSFlagPost(e_grp, 0x01, OS_FLAG_SET, &err); //flag로 alram하도록 보내주기
		}

		OS_EXIT_CRITICAL();
		OSTimeDlyHMSM(0, 0, 1, 0);

		OS_ENTER_CRITICAL();
		OSMboxPost(Mbox, (void*)& value); //mbox로 현재온도 data보내주기
		OS_EXIT_CRITICAL();
		OSTimeDlyHMSM(0, 0, 1, 0);
	}
}

void WantTask(void* data) {
	data = data;
	unsigned char LEDs[]={0x01, 0x07, 0x0f, 0x1f, 0x7f, 0xff};
	volatile int want=WANT;

	while (1) {

		//want값 전송하기
		OS_ENTER_CRITICAL();
		OSQPost(Mqueue, (void*)& want);

		//현재 온도에 대한 정보 보내주기
		if (FndNum <= 10) {
			OSQPost(Mqueue, (void*)&LEDs[0]);
		}
		else if (FndNum > 10 && FndNum <= 20) {
			OSQPost(Mqueue, (void*)&LEDs[1]); //1단계로 set!
		}
		else if (FndNum > 20 && FndNum <= 25) {
			OSQPost(Mqueue, (void*)& LEDs[2]); //2단계로 set
		}
		else if (FndNum > 25 && FndNum <= 30) {
			OSQPost(Mqueue, (void*)& LEDs[3]);
		}
		else if (FndNum > 30 && FndNum < 40) {
			OSQPost(Mqueue, (void*)& LEDs[4]);//4
		}
		else {
			OSQPost(Mqueue, (void*)& LEDs[5]);;//5단계..
		}
		OS_EXIT_CRITICAL();
		OSTimeDlyHMSM(0, 0, 1, 0);
		

		
	}
}


void FndTask(void* data)
{
	data = data;
	
	while (1) {

		OSTimeDlyHMSM(0, 0, 1, 0);
		//온도 Task에서 받아온것 전달받기 ->FndDisplayTask를 실행하기위하여~
		OS_ENTER_CRITICAL();
		state = 1;
		FndNum = *(INT8U*)OSMboxPend(Mbox, 0, &err);
		OS_EXIT_CRITICAL();
		OSTimeDlyHMSM(0, 0, 1, 0);

		//Want Task에서 받아온 값 전달받기
		OS_ENTER_CRITICAL();
		state = 0;
		WantNum = *(INT8U*)OSQPend(Mqueue, 0, &err);
		LED = *(INT8U*)OSQPend(Mqueue, 0, &err); //현재 온도의 level led로 표시해줄것임
		OS_EXIT_CRITICAL();
		OSTimeDlyHMSM(0, 0, 1, 0);


	}
}
void FndDisplayTask(void* data)
{
	unsigned char FND_DATA[] = {
	   0x3f, // 0
	   0x06, // 1
	   0x5b, // 2
	   0x4f, // 3
	   0x66, // 4
	   0x6d, // 5
	   0x7d, // 6
	   0x27, // 7
	   0x7f, // 8
	   0x6f, // 9
	   0x77, // A
	   0x7c, // B
	   0x39, // C
	   0x5e, // D
	   0x79, // E
	   0x71, // F
	   0x80, // .
	   0x40, // -
	   0x08,  // _
	   0x00 // null
	};
	unsigned int num0, num1, num2, num3;

	data = data;

	DDRC = 0xff;
	DDRG = 0x0f;



	while (1) {
		if (state == 0) { //원하는 값을 출력하는화면-> 원하는 온도 앞에는 '-' 를 붙였다.
			num3 = 17;
			num2 = (WantNum / 10) % 10;
			num1 = WantNum % 10;
			num0 = (((WantNum & 0x00ff) & 0x80) == 0x80) * 5;
			PORTC = FND_DATA[num3];
			PORTG = 0x08;
			_delay_ms(2);
			PORTC = FND_DATA[num2];
			PORTG = 0x04;
			_delay_ms(3);
			PORTC = FND_DATA[num1];
			PORTC |= 0x80;
			PORTG = 0x02;
			_delay_ms(2);
			PORTC = FND_DATA[num0];
			PORTG = 0x01;
			_delay_ms(3);
		}
		else {
			num3 = 0;
			num2 = (FndNum / 10) % 10;
			num1 = FndNum % 10;
			num0 = (((FndNum & 0x00ff) & 0x80) == 0x80) * 5;
			PORTC = FND_DATA[num3];
			PORTG = 0x08;
			_delay_ms(2);
			PORTC = FND_DATA[num2];
			PORTG = 0x04;
			_delay_ms(3);
			PORTC = FND_DATA[num1];
			PORTC |= 0x80;
			PORTG = 0x02;
			_delay_ms(2);
			PORTC = FND_DATA[num0];
			PORTG = 0x01;
			_delay_ms(3);
		}

		if (state == 0) { //원하는값을 출력하는화면
			OS_ENTER_CRITICAL();
			PORTA = LED; //전달받은 현재 온도의 LED
			OS_EXIT_CRITICAL();
		}
		else {
			PORTA = 0x00;
		}
	}
}



void AlarmTask(void* data) {
	OSFlagPend(e_grp, 0x01, OS_FLAG_WAIT_SET_ALL + OS_FLAG_CONSUME, 0, &err);
	//temperature task에서 , 적정 온도를 넘었을때 post해주는 flag기다리기
	PORTA = 0xff;
	DDRB = 0x10;

	////sound
	int count = 0;
	while (count != 400) {
		PORTB = 0x10;
		_delay_ms(1);
		PORTB = 0x00;
		_delay_ms(1);
		count++;
	}
	sei();
	PORTA = 0x00;

	
}

