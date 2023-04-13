from dls import dls

def idfs(graph, start, goal, depth_limit):
    for i in range(depth_limit):
        res = dls(graph, start, goal, i)
        if res[2]:
            return res
    return [], [], False