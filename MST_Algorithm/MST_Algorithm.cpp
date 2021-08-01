#include <iostream>
#include <vector>
#include <string>
#include <algorithm> 

using namespace std;


//union연결리스트 구현하기 (1) - 노드(City)
// 노드에꼭 들어갈 값:도시번호, 도시명, 도시의 인구수
class Node {
public:
	int idx;
	Node* next; //이 Node들의 연결리스트 ->집합
	string nara;
	int saram;
	int setID = -1; //union에 소속하게될때 setID를 저장

	Node(int i, string s, int sa) :next(NULL), idx(i), nara(s), saram(sa), setID(-i) {} 
	//union에 setID를 초기에는"자신의 ID의 음수값"을 넣어준다.(분류,구분을위해서)
	Node() : next(NULL), idx(-1), nara(" "), saram(-1) {}
};

//edge->간선 정보 넣기
class Edge {
public:
	int idx1;
	int idx2;
	int len;

	Edge(int d1, int d2, int lenn) : idx1(d1), idx2(d2), len(lenn) {}
};



class Disjointset { //각각의 노드들이 union될때 저장되는 집합을 linked list형식으로 구현하였다
public:
	int ssize = 0;
	int lenlen = 0;
	Node* head;
	Node* tail;

	Disjointset() :head(NULL), tail(NULL) {}

	void insert(Node* n, Node* t) { //union되는집합(이어지는집합) 의 head와 tail을 인자로 받는다.
		if (head == NULL) { head = n; tail = n; } //아직 집합이 생성되기 전일때, head가 될 노드를 입력시켜주는 과정
		else {
			tail->next = n; //기존의 집합 뒤에 union되는 집합의 head를 이어준다
			if (t == NULL) { tail = n; } //집합이 아닌 노드1개가 연결될때는, 소속된 집합이없기때문에 tail이 없음. 마지막에 입력된 노드가 tail이됨
			else { tail = t; } //tail을 지정해주기
		}
	}


};

/////////////////
Node* nodes[1000000]; //도시노드-> id별로 저장(찾기 쉽게)
vector <Edge*> edges; //간선노드 정보 저장
Disjointset* arr[1000000];//집합들의 모음!
int N, M, q, MSTs, eidx;


bool cmp(Edge* a, Edge* b) {//sort를 어떻게 할지 지정해주는 함수->조건대로...
	if (a->len < b->len) { return true; }
	else if (a->len > b->len) { return false; }
	else {
		int p1 = nodes[a->idx1]->saram + nodes[a->idx2]->saram;
		int p2 = nodes[b->idx1]->saram + nodes[b->idx2]->saram;
		if (p1 > p2) { return true; }
		else if (p1 < p2) { return false; }
		else {
			int d1, d2;
			//
			if (a->idx1 < a->idx2) { d1 = a->idx1; }
			else { d1 = a->idx2; }
			if (b->idx1 > b->idx2) { d2 = b->idx1; }
			else { d2 = b->idx2; }
			//min을 못써서

			if (d1 < d2) { return true; }
			else { return false; }
		}
	}
}


int find(int);
int GroupSize(int);
int Union(int, int, int);
void doN(int, int);
void doF(int, int);


int main() {
	cin >> N >> M >> q;
	for (int i = 0; i < N; i++) {//도시 정보 입력+저장
		int id, num; string name;
		cin >> id >> name >> num;
		nodes[id] = new Node(id, name, num);
	}

	for (int i = 0; i < M; i++) {//간선 정보 입력
		int d1, d2, l;
		cin >> d1 >> d2 >> l;
		edges.push_back(new Edge(d1, d2, l));
	}
	MSTs = 0;//MST가 완성되었는지 확인하는 변수
	sort(edges.begin(), edges.end(), cmp); //edge의 길이가 낮은 순서대로 정렬

	string key, k1, k2;
	int v1, v2, k;
	for (int i = 0; i < q; i++) {//명령 구간
		cin >> key;

		if (key == "N") {
			cin >> v1 >> k;

			doN(v1, k);
		}
		else if (key == "L") { // v가 포함된 집합의 원소갯수
			cin >> v1;
			cout << GroupSize(v1) << "\n";
		}
		else if (key == "I") {
			////간선이 sort된 순서대로 앞에서부터 뽑아서 써주기
			int d1 = edges[eidx]->idx1;
			int d2 = edges[eidx]->idx2;
			int len = edges[eidx]->len;
			int ans = Union(d1, d2, len); //union한단계 실행-> 리턴값이 -1이거나 union된 집합 head의 idx이다

			if (ans == -1) {//not union 일때
				cout << "not union" << "\n";
				eidx++; //간선을 사용했으니 다음 간선으로
			}
			else {
				MSTs++;//union에 성공했다는 뜻 ->MST의 노드의수가 1개 늘어났다는뜻
				if (MSTs == N - 1) {
					cout << ans << " " << arr[ans]->ssize << " " << arr[ans]->lenlen << "\n";
					break;
				}
				else {
					cout << ans << " " << arr[ans]->ssize << "\n";
					eidx++;//다음 간선으로
				}
			}
			////
		}
		else if (key == "F") {
			cin >> v1 >> v2;
			doF(v1, v2);
		}
		else if (key == "W") {
			cin >> v1;
			if (nodes[v1] == NULL) { cout << 0 << "\n"; } //없는 노드를 불러냈을때
			else if (nodes[v1]->setID < 0) { cout << 0 << "\n"; } //아직 집합에 속해있지 않은 노드를 불러냈을때
			else cout << arr[nodes[v1]->setID]->lenlen << "\n"; //집합에저장된 총 비용
		}
		else if (key == "Q") {
			while (true) {//I기능을 MST가 완성될때까지 진행시켜주면 된다.주석 동일
				int d1 = edges[eidx]->idx1;
				int d2 = edges[eidx]->idx2;
				int len = edges[eidx]->len;
				int ans = Union(d1, d2, len); //union한단계 실행
					////

				if (ans == -1) {
					eidx++;
				}
				else {
					MSTs++;
					if (MSTs == N - 1) {
						cout << ans << " " << arr[ans]->lenlen << "\n";
						break;
					}
					else {
						eidx++;
					}
				}
				//
			}
			break; //MST가 완성되었을때 while문에서 나옴. 프로그램 전체를 끝내줘야하니 한번 더 break;

		}

	}
}

int find(int d1) {//head 노드의ID 리턴해준다
	if (nodes[d1]->setID < 0) return d1; //node의 setID가 지정이 되지 않았을때 : 자기 자신의 idx가 자신의 headNode
	else return nodes[d1]->setID;  //저장되어잇는 setID리턴
}


int GroupSize(int idx) {
	if (nodes[idx]->setID < 0) {//아직 속해있는 집합이 없는경우 1return(자기자신)
		return 1;
	}
	else {//속해있는 집합이 있는경우,
		return arr[nodes[idx]->setID]->ssize; //  노드에 기록된 자기 집합의 headidx 로 자신의 집합 찾아간다. 그 후 저장된 ssize(원소의갯수)출력
	}
}

int Union(int d1, int d2, int len) {
	if (find(d1) != find(d2)) { //다를때만(이때 union된 집합의 헤드값들을 경우마다 리턴해준다)
		Node* n1 = nodes[d1]; Node* n2 = nodes[d2];
		int g1 = GroupSize(d1); int g2 = GroupSize(d2);
		if (g1 == 1 && g2 == 1) {//둘 다 집합에 속해있지 않을때!
			if (d1 < d2) {
				arr[d1] = new Disjointset(); //둘다 집합에 속해있지 않으니, head노드의 idx값에 새로운 집하블 생성한다
				arr[d1]->insert(n1, NULL); arr[d1]->insert(n2, NULL);  //아직 비어있는 집합에 head먼저 집어넣고, 연결될 노드 집어넣어주기
				//setID갱신, 총 비용 업데이트, 집합의 원소갯수 업데이트
				n1->setID = d1; n2->setID = d1;
				arr[d1]->lenlen = len;
				arr[d1]->ssize = 2;
				return d1; //union성공했을땐 성공한 집합 head return
			}
			else {//위와 같은 방식. 연결순서만 다름
				arr[d2] = new Disjointset();
				arr[d2]->insert(n2, NULL);  arr[d2]->insert(n1, NULL);
				n1->setID = d2; n2->setID = d2;
				arr[d2]->lenlen = len;
				arr[d2]->ssize = 2;
				return d2;
			}
		}
		else {//두 도시가 속한 집합의 weight 비교->person->idx
			if (g1 > g2 || (g1 == g2 && n1->setID < n2->setID)) { //weigthed-union heuristic방식 -> weight가 같다면 집합의 head노드들 비교
				if (GroupSize(d2) == 1) {
					arr[n1->setID]->insert(n2, NULL);//insert되는 노드가 속한 집합이 없을때, 
					arr[n1->setID]->lenlen += len;
					arr[n1->setID]->ssize += 1;
					n2->setID = n1->setID;
				}
				else {
					arr[n1->setID]->insert(arr[n2->setID]->head, arr[n2->setID]->tail);//insert되는 노드의 head값, tail값 인자로 넣어주기
					arr[n1->setID]->lenlen += arr[n2->setID]->lenlen + len; //집합들이 union됐으니 간선들의 총합 업데이트 
					arr[n1->setID]->ssize += arr[n2->setID]->ssize;  //집합의 원소 갯수 업데이트
					////이어진 집합의 노드 원소들 의 setID를 업데이트 해준다
					Node* temp = arr[n2->setID]->head; 
					for (int i = 0; i < g2; i++) {
						temp->setID = n1->setID;
						temp = temp->next;
					}
				}
				return n1->setID; 
			}
			else if (g1 < g2 || g1 == g2 && n1->setID > n2->setID) {//위와 같은과정
				if (GroupSize(d1) == 1) {
					arr[n2->setID]->insert(n1, NULL);//insert
					arr[n2->setID]->lenlen += len;
					arr[n2->setID]->ssize += 1;
					n1->setID = n2->setID;
				}
				else {
					arr[n2->setID]->insert(arr[n1->setID]->head, arr[n1->setID]->tail);//insert
					arr[n2->setID]->lenlen += arr[n1->setID]->lenlen + len;
					arr[n2->setID]->ssize += arr[n1->setID]->ssize;
					Node* temp = arr[n1->setID]->head;
					for (int i = 0; i < g1; i++) {
						temp->setID = n2->setID;
						temp = temp->next;
					}
				}
				return n2->setID;
			}
		}
	}
	else { return -1; } //union이 성공하지 못하였을때 -1리턴
}


void doN(int v1, int k) {
	if (nodes[v1] == NULL) { cout << "no exist" << "\n"; } //저장되지 않은 도시노드를 입력하였을때
	else if (nodes[v1]->setID < 0) {//아직 집합이 생기지 않은 노드를 불렀을때
		if (k == 0) { cout << v1 << " " << nodes[v1]->nara << "\n"; } //0일때 : 자기자신 출력해주기
		else { cout << "no exist" << "\n"; }
	}
	else if (arr[nodes[v1]->setID]->ssize < k + 1) { cout << "no exist" << "\n"; } //집합의 원소의 idx보다 큰 번지수를 원할때
	else {
		Node* temp = arr[nodes[v1]->setID]->head;   
		while (k--) {//k번째 찾으러 가기
			temp = temp->next;
		}
		cout << temp->idx << " " << temp->nara << "\n";
	}
}

void doF(int v1, int v2) {
	if (find(v1) == find(v2)) {
		cout << "True " << find(v1) << "\n"; 
	}
	else {
		cout << "False " << find(v1) << " " << find(v2) << "\n";
	}
}