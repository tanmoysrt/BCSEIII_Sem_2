from collections import deque

class Puzzle:
    def __init__(self, board):
        self.board = board
        self.rows = len(board)
        self.cols = len(board[0])
        self.goal = tuple([i for i in range(1, self.rows*self.cols)] + [0])
    
    def get_blank(self):
        for i in range(self.rows):
            for j in range(self.cols):
                if self.board[i][j] == 0:
                    return (i, j)
    
    def is_goal(self):
        return tuple([j for i in self.board for j in i]) == self.goal
    
    def get_neighbors(self):
        neighbors = []
        i, j = self.get_blank()
        for r, c in [(i+1,j), (i-1,j), (i,j+1), (i,j-1)]:
            if 0 <= r < self.rows and 0 <= c < self.cols:
                new_board = [row[:] for row in self.board]
                new_board[i][j], new_board[r][c] = new_board[r][c], new_board[i][j]
                neighbors.append(Puzzle(new_board))
        return neighbors
    
    def __eq__(self, other):
        return tuple([j for i in self.board for j in i]) == tuple([j for i in other.board for j in i])
    
    def __hash__(self):
        return hash(tuple([j for i in self.board for j in i]))
        
def bfs(initial):
    visited = set()
    queue = deque([initial])
    while queue:
        curr = queue.popleft()
        if curr.is_goal():
            return curr
        for neighbor in curr.get_neighbors():
            if neighbor not in visited:
                visited.add(neighbor)
                queue.append(neighbor)
    return None

initial = Puzzle([[1, 2, 3], [0, 4, 6], [7, 5, 8]])
solution = bfs(initial)
# print(solution.board)
# if solution:
#     print("Solution found:")
#     curr = solution
#     path = []
#     while curr:
#         path.append(curr.board)
#         curr = curr.parent
#     path.reverse()
#     for board in path:
#         print(" ")
#         for row in board:
#             print(row)
# else:
#     print("No solution found.")
