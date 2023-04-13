from graph import Graph
from collections import deque
from bfs import bfs
from dfs import dfs
from dls import dls
from idfs import idfs

if __name__ == "__main__":
    graph = Graph()
    graph.add_vertex('A', 'B', True, 0)
    graph.add_vertex('A', 'C', True, 0)
    graph.add_vertex('B', 'D', True, 0)
    graph.add_vertex('B', 'E', True, 0)
    graph.add_vertex('C', 'F', True, 0)
    graph.add_vertex('C', 'G', True, 0)
    graph.add_vertex('D', 'H', True, 0)
    graph.add_vertex('D', 'I', True, 0)
    graph.add_vertex('E', 'J', True, 0)
    graph.add_vertex('E', 'K', True, 0)
    graph.add_vertex('F', 'L', True, 0)
    graph.add_vertex('F', 'M', True, 0)
    graph.add_vertex('G', 'N', True, 0)
    graph.add_vertex('G', 'O', True, 0)
    
    start_state = 'A'
    goal_state = 'M'

    print("Running BFS...")
    res = bfs(graph, start_state, goal_state)
    print("Found: {}".format(res[2]))
    print("Solution path: {}".format(" > ".join(res[0])))
    print("Traverse path: {}".format(" > ".join(res[1])))
    print()
    print("Running DFS...")
    res = dfs(graph, start_state, goal_state)
    print("Found: {}".format(res[2]))
    print("Solution path: {}".format(" > ".join(res[0])))
    print("Traverse path: {}".format(" > ".join(res[1])))
    print()
    res = dls(graph, start_state, goal_state, 2)
    print("Running DLS...")
    print("Found: {}".format(res[2]))
    print("Solution path: {}".format(" > ".join(res[0])))
    print("Traverse path: {}".format(" > ".join(res[1])))
    print()
    print("Running IDFS...")
    res = idfs(graph, start_state, goal_state, 50)
    print("Found: {}".format(res[2]))
    print("Solution path: {}".format(" > ".join(res[0])))
    print("Traverse path: {}".format(" > ".join(res[1])))
    print()