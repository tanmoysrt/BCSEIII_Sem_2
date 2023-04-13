class Graph:
    def __init__(self, graph_dict=None):
        if graph_dict is None:
            graph_dict = {}
        self.graph_dict = graph_dict

    def get_neighbors(self, vertex):
        if vertex not in self.graph_dict:
            return []
        return self.graph_dict[vertex]
    
    def add_vertex(self, parent, child, directed=False, weight=0):
        if parent not in self.graph_dict:
            self.graph_dict[parent] = []
        self.graph_dict[parent].append((child, weight))
        if not directed:
            self.add_vertex(child, parent, True, weight)
    



# if __name__ == "__main__":
#     graph = Graph()
#     graph.add_vertex('A', 'B', True)
#     graph.add_vertex('A', 'C')
#     graph.add_vertex('B', 'D')
#     graph.add_vertex('B', 'E', True, 5)
#     graph.add_vertex('C', 'F')
#     graph.add_vertex('C', 'G')
#     graph.add_vertex('D', 'H')
#     graph.add_vertex('D', 'I')
#     graph.add_vertex('E', 'J')
#     graph.add_vertex('E', 'K')
#     graph.add_vertex('F', 'L')
#     graph.add_vertex('F', 'M')
#     graph.add_vertex('G', 'N')
#     graph.add_vertex('G', 'O')
#     print(graph.graph_dict)