from pprint import pprint
import numpy as np

num = int(input("A:"))

A = []

n = num
m = num
while n>0:
    n -= 1
    inp=input(">").strip()


    row = list(map(float,inp.split()))
    A.append(row)


n = len(A)



L = [[0 for i in range(n)] for i in range(n)]
for i in range(0,n):
    L[i][i] = 1

n=len(A)

U = [[0 for i in range(0,n)] for i in range(n)]
for i in range(0,n):
    for j in range(0,n):
        U[i][j] = A[i][j]


for i in range(0,n): 
    maxElem = abs(U[i][i])
    maxRow = i
    for k in range(i+1, n): 
        if(abs(U[k][i]) > maxElem):
            maxElem = abs(U[k][i]) 
            maxRow = k

    for k in range(i, n): 
        tmp=U[maxRow][k]
        U[maxRow][k]=U[i][k]
        U[i][k]=tmp
    for k in range(i+1,n):
        c = U[k][i]/float(U[i][i])
        L[k][i] = c 
        for j in range(i, n):
            U[k][j] -= c*U[i][j] 

		
    for k in range(i+1, n):
        U[k][i]=0
        n = len(L)

print("L:")
pprint(L, indent=4,width= 30)


print("U:")
pprint(U, indent=4,width= 30)


