from graph import Graph

def dls(graph:Graph, start:str, goal:str, depth_limit:int):
    visited = set()
    traverse_path = []
    found = False

    def dfs_helper(node, path, depth):
        if depth > depth_limit:
            return [], traverse_path, found
        
        # normal
        visited.add(node)
        traverse_path.append(node)

        if node == goal:
            return path, traverse_path, True
        
        
        neighbors = graph.get_neighbors(node)
        for neighbor in neighbors:
            if neighbor[0] not in visited:
                res = dfs_helper(neighbor[0], path + [neighbor[0]], depth+1)
                if res[2]:
                    return res
                
        return [], traverse_path, found



    return dfs_helper(start, [start], 0)