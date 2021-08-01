#define HAVE_STRUCT_TIMESPEC
#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>



sem_t semaphore;
pthread_t thread[5];

typedef struct process {
	int class_id;
	int process_id;
	int priority;
	int time;

}process;


process ready_q[500];
process queue1[100];
process queue2[100];
process queue3[100];
process queue4[100];

int ssize, q1 = 0, q2 = 0,  q3 = 0, q4 = 0,q5 = 0;


//이게  첫번째!
void* FCFS(void * fcfs) {
	sem_wait(&semaphore);
	for (int i = 0; i < q1; i++) {
		for (int j = 0; j < queue1[i].time; j++) {
			printf("%d ", queue1[i].process_id);
		}
	printf("\n");
	}
	sem_post(&semaphore);
	
	return NULL;
}

// 이게  두번째 큐!
void* SJF(void * sjf) {
	sem_wait(&semaphore);
	//도착시간은 다 같기때문에
	for (int i = 0; i < q2; i++) {
		for (int j = i + 1; j < q2; j++) {
			if (queue2[i].time > queue2[j].time) {//시간이 더 작을수록 ..앞으로....(i가 앞에있으니까 더 크자나!바꿔줘야지)
				process pro = queue2[i];
				queue2[i] = queue2[j];
				queue2[j] = pro;
				//swap해주기
			}
		}
	}//위치 조정 끝

	for (int i = 0; i < q2; i++) {
		for (int j = 0; j < queue2[i].time; j++) {
			printf("%d ", queue2[i].process_id);
		}
		printf("\n");
	}
	sem_post(&semaphore);
	
	return NULL;
}
//세번째 큐!
void* RR(void * rr) {
	sem_wait(&semaphore);
	 //Time Quantum for Queue3 is 2
	int aidx = q3;
	for (int i = 0; i < aidx;i++) {
		if (queue3[i].time <= 2) {//퀀텀 타임보다 수행시간이 짧을때
			for (int j = 0; j < queue3[i].time; j++) {
				printf("%d ", queue3[i].process_id);
			}
			printf("\n");
		}
		else 
		{ //클때 ->일단 퀀텀 타임인 2번씩 실행해 주기
			for (int j = 0; j < 2; j++) {
				printf("%d ", queue3[i].process_id);
			}
			printf("\n");
			//

			queue3[aidx] = queue3[i];
			queue3[aidx].time -= 2;
			aidx++;//만약 완료가 되지않고 뒤로 밀림-> for문이 밀린만큼 돌아가도록 설정!
		}
	
	}

	sem_post(&semaphore);


	return NULL;
}

//4번째 큐
void* PriorityS(void* ps) {
	sem_wait(&semaphore);

	for (int i = 0; i < q4; i++) {
		for (int j = i + 1; j < q4; j++) {
			if (queue4[i].priority > queue4[j].priority) {
				process pro = queue4[i];
				queue4[i] = queue4[j];
				queue4[j] = pro;
				//swap해주기
			}
		}
	}//위치 조정 끝

	for (int i = 0; i < q4; i++) {
		for (int j = 0; j < queue4[i].time; j++) {
			printf("%d ", queue4[i].process_id);
		}
		printf("\n");
	}

	sem_post(&semaphore);

	return NULL;
}


int fileOpen() {
	FILE* op = fopen("input.txt", "r");

	if (op == NULL) { return 0; }
	if ((op = fopen("input.txt", "r")) == NULL) {
			printf("파일 읽기 오류! \n");
			return 0;
		}

	int idx = 0;
	while (1) {
		int r = fscanf(op, "%d %d %d %d", 
			&ready_q[idx].class_id,&ready_q[idx].process_id,
			&ready_q[idx].priority,&ready_q[idx].time);
		idx++;
		ssize++;
		if (r == EOF) { break; }	
	} 

}



void* MQS(void* arg) {//큐들간의 스케쥴링을 다루는법!d


	for(int i=0; i<ssize-1;i++) {
		if (ready_q[i].class_id == 1) {
			queue1[q1] = ready_q[i];
			q1++;
		}
		else if (ready_q[i].class_id == 2) {
			queue2[q2] = ready_q[i];
			q2++;
		}
		else if (ready_q[i].class_id == 3) {
			queue3[q3] = ready_q[i];
			q3++;
		}
		else if (ready_q[i].class_id == 4) {
			queue4[q4] = ready_q[i];
			q4++;
		}
	}

	printf("START FCFS SCHEDULING\n");

	pthread_create(&thread[1], NULL, FCFS, NULL);
	pthread_join(thread[1], NULL);

	////////////////

	printf("START SJF SCHEDULING\n");

	pthread_create(&thread[2], NULL, SJF, NULL);
	pthread_join(thread[2], NULL);

	///////

	printf("START ROUND-ROBIN SCHEDULING\n");

	pthread_create(&thread[3], NULL,RR, NULL);
	pthread_join(thread[3], NULL);

	///
	printf("START PRIORIY SCHEDULING\n");

	pthread_create(&thread[4], NULL,PriorityS, NULL);
	pthread_join(thread[4], NULL);

	sem_post(&semaphore);
	return NULL;
}


int main() {

	sem_init(&semaphore, 0, 1);

	int m = fileOpen();
	if (m == 0) {
		printf("Failed to open input file\n");
		return 0;
	}


	pthread_t a; //thread_id
	printf("START MQS\n--------------------------------\n");
	pthread_create(&a, NULL, MQS, NULL);	 //스레드를 생성한다.
	pthread_join(a, NULL); //my_thread가 끝날 때 까지 기다린다.
	printf("SCHEDULING FINISHED");

		
	sem_destroy(&semaphore);
	return 0;
}
	


