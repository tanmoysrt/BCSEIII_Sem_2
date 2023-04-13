from graph import Graph
from collections import deque

def bfs(graph:Graph, start:str, goal:str):
    queue = deque()
    visited = set()
    traverse_path = []

    found = False
    solution_path = ""

    queue.append((start, [start]))

    while queue:
        if found:
            break
        node, path = queue.popleft()
        traverse_path.append(node)
        visited.add(node)

        if node == goal:
            found = True
            solution_path = path
            break

        neighbors = graph.get_neighbors(node)
        for neighbor in neighbors:
            if neighbor[0] not in visited:
                queue.append((neighbor[0], path + [neighbor[0]]))

    return solution_path, traverse_path, found