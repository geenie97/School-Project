from pprint import pprint
import numpy as np
from sympy import *
import scipy
from scipy.linalg import qr


##읽어주세요
###워드파일에 나온예시로는 대각화가 제대로됐는지 긴가민가했는데(실수로계산되어서ㅜ)
###5.2 예제3번을 확인하니 대각화가 제대로되는것을 확인 할 수 있었습니다.
##eigenvalue값이 다른것같아도 벡터값자체는 같습니다 ㅜㅜ



num = int(input("A:"))

A = []

n = num

while n>0:
    n -= 1
    inp=input(">").strip()
    row = list(map(float,inp.split()))
    A.append(row)

###########determinant함수만들깅
n =num

def minimatrix(i,A):
    minimat = []
    size = len(A)
    for j in range(1,size):
        row = []
        for k in range(size):
            if(k != i):
                row.append(A[j][k])
        minimat.append(row)
    return minimat

            

x = Symbol('x')

B=[]
for i in range(num):
    row = []
    for j in range(num):
        if(i==j):
            row.append(A[i][j]-x)
        else:
            row.append(A[i][j])
    
    B.append(row)    



def determinant(A,size):
    det = 0
    if size == 1:
        return A[0][0]
    else:
        for i in range(size):
            det += pow(-1,0+i)*A[0][i]*determinant(minimatrix(i,A),size-1)
        return det


getlam= determinant(B,num)

sol = solve(getlam)

n=len(sol)



## eigenvalue, eigenvector 구해주기

P=[]

for i in range(n):
    print("Eigenvalue",i+1,"=",sol[i])


for i in range(n):
    C=[]
    
    for j in range(num):
        row = []
        for k in range(num):
            if(j==k):
                row.append(float(A[j][k]-sol[i]))
            else:
                row.append(float(A[j][k]))
    
        C.append(row)
    value1 = Matrix(C)
    print(C)
    Z=value1.nullspace()
    print("Eigenvector",i+1,"=",Z)
    for g in range(len(Z)):
        col=[]
        for l in range(num):
            col.append(Z[g][l])
        P.append(col)    
        

########

P1=[]
for i in range(len(P)):
    row = []
    for j in range(len(P)):
        row.append(float(P[j][i]))
    P1.append(row)    


print("P:",P1)


########
def fin():
    b = np.array(P1)
    c = np.linalg.inv(b)

    print("P의 역행렬:",c)


    a=np.array(A)
    p=np.array(P1)
    pi=np.array(c)

    middle =np.dot(c,a)
    fin = np.dot(middle,p)

    print("D:",fin)



def checking(A):
    if len(A)<num:
        print("대각화 불가능")
    else:
        if determinant(A,num) != 0:
            print("대각화 가능")
            fin()

        else:
            print("대각화 불가능")


checking(P1)



