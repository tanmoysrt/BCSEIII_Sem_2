from collections import deque


def next_state_water_jug(state, capacities):
    new_states = []

    # Fill the first jug
    new_states.append((capacities[0], state[1]))
    # Fill the second jug
    new_states.append((state[0], capacities[1]))
    # Empty the first jug
    new_states.append((0, state[1]))
    # Empty the second jug
    new_states.append((state[0], 0))
    # Pour from the first jug to the second jug
    transfer_amount = min(state[0], capacities[1] - state[1])
    new_states.append((state[0] - transfer_amount, state[1] + transfer_amount))
    # Pour from the second jug to the first jug
    transfer_amount = min(state[1], capacities[0] - state[0])
    new_states.append((state[0] + transfer_amount, state[1] - transfer_amount))

    return new_states

def water_jug_bfs(state, goal, capacities):
    queue = deque()
    visited = set()
    traverse_path = []

    found = False
    solution_path = ""

    queue.append((state, [state]))

    while queue:
        if found:
            break
        state, path = queue.popleft()
        traverse_path.append(state)
        visited.add(state)

        if state == goal:
            found = True
            solution_path = path
            break


        new_states = next_state_water_jug(state, capacities)
        for new_state in new_states:
            if new_state not in visited:
                queue.append((new_state, path + [new_state]))

    return solution_path, traverse_path, found



def water_jug_dfs(state, goal, capacities):
    visited = set()
    traverse_path = []
    found = False

    def dfs_helper(node, path):
        visited.add(node)
        traverse_path.append(node)

        if node == goal:
            return path, traverse_path, True
        
        new_states = next_state_water_jug(node, capacities)
        for new_state in new_states:
            if new_state not in visited:
                res = dfs_helper(new_state, path + [new_state])
                if res[2]:
                    return res
                
        return [], traverse_path, found
                
    return dfs_helper(state, [state])

if __name__ == "__main__":
    start = (0, 0)
    goal = (1, 0)
    capacities = [4, 3]
    print("BFS")
    solution_path, traverse_path, found = water_jug_bfs(start, goal, capacities)
    print(solution_path)
    print(traverse_path)
    print(found)
    print("DFS")
    solution_path, traverse_path, found = water_jug_dfs(start, goal, capacities)
    print(solution_path)
    print(traverse_path)
    print(found)
    # print(next_state_water_jug(start, capacities))