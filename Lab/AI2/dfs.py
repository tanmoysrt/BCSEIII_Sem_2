from graph import Graph

def dfs(graph:Graph, start:str, goal:str):
    visited = set()
    traverse_path = []

    def dfs_helper(node, path):
        visited.add(node)
        traverse_path.append(node)

        if node == goal:
            return path, traverse_path, True
        
        neighbors = graph.get_neighbors(node)
        for neighbor in neighbors:
            if neighbor[0] not in visited:
                res = dfs_helper(neighbor[0], path + [neighbor[0]])
                if res[2]:
                    return res
                
        return [], traverse_path, False



    return dfs_helper(start, [start])