#include <iostream>
#include <vector>
#include <string>
#include <algorithm> 

using namespace std;


//union���Ḯ��Ʈ �����ϱ� (1) - ���(City)
// ��忡�� �� ��:���ù�ȣ, ���ø�, ������ �α���
class Node {
public:
	int idx;
	Node* next; //�� Node���� ���Ḯ��Ʈ ->����
	string nara;
	int saram;
	int setID = -1; //union�� �Ҽ��ϰԵɶ� setID�� ����

	Node(int i, string s, int sa) :next(NULL), idx(i), nara(s), saram(sa), setID(-i) {} 
	//union�� setID�� �ʱ⿡��"�ڽ��� ID�� ������"�� �־��ش�.(�з�,���������ؼ�)
	Node() : next(NULL), idx(-1), nara(" "), saram(-1) {}
};

//edge->���� ���� �ֱ�
class Edge {
public:
	int idx1;
	int idx2;
	int len;

	Edge(int d1, int d2, int lenn) : idx1(d1), idx2(d2), len(lenn) {}
};



class Disjointset { //������ ������ union�ɶ� ����Ǵ� ������ linked list�������� �����Ͽ���
public:
	int ssize = 0;
	int lenlen = 0;
	Node* head;
	Node* tail;

	Disjointset() :head(NULL), tail(NULL) {}

	void insert(Node* n, Node* t) { //union�Ǵ�����(�̾���������) �� head�� tail�� ���ڷ� �޴´�.
		if (head == NULL) { head = n; tail = n; } //���� ������ �����Ǳ� ���϶�, head�� �� ��带 �Է½����ִ� ����
		else {
			tail->next = n; //������ ���� �ڿ� union�Ǵ� ������ head�� �̾��ش�
			if (t == NULL) { tail = n; } //������ �ƴ� ���1���� ����ɶ���, �Ҽӵ� �����̾��⶧���� tail�� ����. �������� �Էµ� ��尡 tail�̵�
			else { tail = t; } //tail�� �������ֱ�
		}
	}


};

/////////////////
Node* nodes[1000000]; //���ó��-> id���� ����(ã�� ����)
vector <Edge*> edges; //������� ���� ����
Disjointset* arr[1000000];//���յ��� ����!
int N, M, q, MSTs, eidx;


bool cmp(Edge* a, Edge* b) {//sort�� ��� ���� �������ִ� �Լ�->���Ǵ��...
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
			//min�� ���Ἥ

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
	for (int i = 0; i < N; i++) {//���� ���� �Է�+����
		int id, num; string name;
		cin >> id >> name >> num;
		nodes[id] = new Node(id, name, num);
	}

	for (int i = 0; i < M; i++) {//���� ���� �Է�
		int d1, d2, l;
		cin >> d1 >> d2 >> l;
		edges.push_back(new Edge(d1, d2, l));
	}
	MSTs = 0;//MST�� �ϼ��Ǿ����� Ȯ���ϴ� ����
	sort(edges.begin(), edges.end(), cmp); //edge�� ���̰� ���� ������� ����

	string key, k1, k2;
	int v1, v2, k;
	for (int i = 0; i < q; i++) {//��� ����
		cin >> key;

		if (key == "N") {
			cin >> v1 >> k;

			doN(v1, k);
		}
		else if (key == "L") { // v�� ���Ե� ������ ���Ұ���
			cin >> v1;
			cout << GroupSize(v1) << "\n";
		}
		else if (key == "I") {
			////������ sort�� ������� �տ������� �̾Ƽ� ���ֱ�
			int d1 = edges[eidx]->idx1;
			int d2 = edges[eidx]->idx2;
			int len = edges[eidx]->len;
			int ans = Union(d1, d2, len); //union�Ѵܰ� ����-> ���ϰ��� -1�̰ų� union�� ���� head�� idx�̴�

			if (ans == -1) {//not union �϶�
				cout << "not union" << "\n";
				eidx++; //������ ��������� ���� ��������
			}
			else {
				MSTs++;//union�� �����ߴٴ� �� ->MST�� ����Ǽ��� 1�� �þ�ٴ¶�
				if (MSTs == N - 1) {
					cout << ans << " " << arr[ans]->ssize << " " << arr[ans]->lenlen << "\n";
					break;
				}
				else {
					cout << ans << " " << arr[ans]->ssize << "\n";
					eidx++;//���� ��������
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
			if (nodes[v1] == NULL) { cout << 0 << "\n"; } //���� ��带 �ҷ�������
			else if (nodes[v1]->setID < 0) { cout << 0 << "\n"; } //���� ���տ� �������� ���� ��带 �ҷ�������
			else cout << arr[nodes[v1]->setID]->lenlen << "\n"; //���տ������ �� ���
		}
		else if (key == "Q") {
			while (true) {//I����� MST�� �ϼ��ɶ����� ��������ָ� �ȴ�.�ּ� ����
				int d1 = edges[eidx]->idx1;
				int d2 = edges[eidx]->idx2;
				int len = edges[eidx]->len;
				int ans = Union(d1, d2, len); //union�Ѵܰ� ����
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
			break; //MST�� �ϼ��Ǿ����� while������ ����. ���α׷� ��ü�� ��������ϴ� �ѹ� �� break;

		}

	}
}

int find(int d1) {//head �����ID �������ش�
	if (nodes[d1]->setID < 0) return d1; //node�� setID�� ������ ���� �ʾ����� : �ڱ� �ڽ��� idx�� �ڽ��� headNode
	else return nodes[d1]->setID;  //����Ǿ��մ� setID����
}


int GroupSize(int idx) {
	if (nodes[idx]->setID < 0) {//���� �����ִ� ������ ���°�� 1return(�ڱ��ڽ�)
		return 1;
	}
	else {//�����ִ� ������ �ִ°��,
		return arr[nodes[idx]->setID]->ssize; //  ��忡 ��ϵ� �ڱ� ������ headidx �� �ڽ��� ���� ã�ư���. �� �� ����� ssize(�����ǰ���)���
	}
}

int Union(int d1, int d2, int len) {
	if (find(d1) != find(d2)) { //�ٸ�����(�̶� union�� ������ ��尪���� ��츶�� �������ش�)
		Node* n1 = nodes[d1]; Node* n2 = nodes[d2];
		int g1 = GroupSize(d1); int g2 = GroupSize(d2);
		if (g1 == 1 && g2 == 1) {//�� �� ���տ� �������� ������!
			if (d1 < d2) {
				arr[d1] = new Disjointset(); //�Ѵ� ���տ� �������� ������, head����� idx���� ���ο� ���Ϻ� �����Ѵ�
				arr[d1]->insert(n1, NULL); arr[d1]->insert(n2, NULL);  //���� ����ִ� ���տ� head���� ����ְ�, ����� ��� ����־��ֱ�
				//setID����, �� ��� ������Ʈ, ������ ���Ұ��� ������Ʈ
				n1->setID = d1; n2->setID = d1;
				arr[d1]->lenlen = len;
				arr[d1]->ssize = 2;
				return d1; //union���������� ������ ���� head return
			}
			else {//���� ���� ���. ��������� �ٸ�
				arr[d2] = new Disjointset();
				arr[d2]->insert(n2, NULL);  arr[d2]->insert(n1, NULL);
				n1->setID = d2; n2->setID = d2;
				arr[d2]->lenlen = len;
				arr[d2]->ssize = 2;
				return d2;
			}
		}
		else {//�� ���ð� ���� ������ weight ��->person->idx
			if (g1 > g2 || (g1 == g2 && n1->setID < n2->setID)) { //weigthed-union heuristic��� -> weight�� ���ٸ� ������ head���� ��
				if (GroupSize(d2) == 1) {
					arr[n1->setID]->insert(n2, NULL);//insert�Ǵ� ��尡 ���� ������ ������, 
					arr[n1->setID]->lenlen += len;
					arr[n1->setID]->ssize += 1;
					n2->setID = n1->setID;
				}
				else {
					arr[n1->setID]->insert(arr[n2->setID]->head, arr[n2->setID]->tail);//insert�Ǵ� ����� head��, tail�� ���ڷ� �־��ֱ�
					arr[n1->setID]->lenlen += arr[n2->setID]->lenlen + len; //���յ��� union������ �������� ���� ������Ʈ 
					arr[n1->setID]->ssize += arr[n2->setID]->ssize;  //������ ���� ���� ������Ʈ
					////�̾��� ������ ��� ���ҵ� �� setID�� ������Ʈ ���ش�
					Node* temp = arr[n2->setID]->head; 
					for (int i = 0; i < g2; i++) {
						temp->setID = n1->setID;
						temp = temp->next;
					}
				}
				return n1->setID; 
			}
			else if (g1 < g2 || g1 == g2 && n1->setID > n2->setID) {//���� ��������
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
	else { return -1; } //union�� �������� ���Ͽ����� -1����
}


void doN(int v1, int k) {
	if (nodes[v1] == NULL) { cout << "no exist" << "\n"; } //������� ���� ���ó�带 �Է��Ͽ�����
	else if (nodes[v1]->setID < 0) {//���� ������ ������ ���� ��带 �ҷ�����
		if (k == 0) { cout << v1 << " " << nodes[v1]->nara << "\n"; } //0�϶� : �ڱ��ڽ� ������ֱ�
		else { cout << "no exist" << "\n"; }
	}
	else if (arr[nodes[v1]->setID]->ssize < k + 1) { cout << "no exist" << "\n"; } //������ ������ idx���� ū �������� ���Ҷ�
	else {
		Node* temp = arr[nodes[v1]->setID]->head;   
		while (k--) {//k��° ã���� ����
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