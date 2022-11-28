import numpy as np

grid = np.array([[1, 3, 2, 5, 4, 6, 9, 8, 7],
                 [4, 6, 5, 8, 7, 9, 3, 2, 1],
                 [7, 9, 8, 2, 1, 3, 6, 5, 4],
                 [8, 5, 9, 7, 6, 1, 0, 2, 0],
                 [4, 2, 6, 8, 5, 3, 7, 9, 1],
                 [7, 1, 3, 9, 2, 4, 8, 5, 6],
                 [9, 0, 1, 5, 3, 7, 2, 1, 4],
                 [2, 8, 7, 4, 1, 9, 6, 3, 5],
                 [3, 0, 0, 4, 8, 1, 1, 7, 9]])

for i in range(0,9,3):
    for j in range(0,9,3):
        box = grid[i][j:j+3]+grid[i+1][j:j+3]+grid[i+2][j:j+3]
        print(grid[i][j:j+3]+grid[i+1][j:j+3]+grid[i+2][j:j+3])
        break
        #print(grid[i,j:j+3])