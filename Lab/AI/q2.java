import java.util.*;

class Graph2 {
    private final Map<String, List<String>> adjacencyList;

    public Graph2() {
        this.adjacencyList = new HashMap<>();
    }

    public void addEdge(String vertexA, String vertexB) {
        List<String> neighbours = adjacencyList.computeIfAbsent(vertexA, k -> new ArrayList<>());
        neighbours.add(vertexB);
        neighbours = adjacencyList.computeIfAbsent(vertexB, k -> new ArrayList<>());
        neighbours.add(vertexA);
    }

    public List<String> getNeighbours(String vertex) {
        List<String> neighbours = adjacencyList.get(vertex);
        if(neighbours == null) return new ArrayList<>();
        return neighbours;
    }


    public List<String> BFS(String startVertex, String endVertex) {
        Map<String,String> parent = new HashMap<>();
        List<String> visitedNodes = new ArrayList<>();

        Set<String> visited = new HashSet<>();
        Deque<String> queue = new ArrayDeque<>();
        visited.add(startVertex);
        queue.add(startVertex);
        parent.put(startVertex,null);
        while (!queue.isEmpty()) {
            String currentVertex = queue.poll();
            visitedNodes.add(currentVertex);
            if (Objects.equals(currentVertex, endVertex)) {
                printOrderOfNodes(visitedNodes);
                return path(parent, startVertex, endVertex);
            }
            List<String> neighbours = getNeighbours(currentVertex);
            for (String neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                    parent.put(neighbour,currentVertex);
                }
            }
        }
        return new ArrayList<>();
    }

    public List<String> DFS(String startVertex, String endVertex) {
        Map<String,String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();
        List<String> visitedNodes = new ArrayList<>();
        DFSUtil(startVertex, visited, visitedNodes, parent, endVertex);
        printOrderOfNodes(visitedNodes);
        return path(parent, startVertex, endVertex);
    }

    private boolean DFSUtil(String vertex, Set<String> visited, List<String> visitedNodes, Map<String,String> parent, String endVertex) {
        visited.add(vertex);
        visitedNodes.add(vertex);
        if(Objects.equals(vertex, endVertex)) return true;
        List<String> neighbours = getNeighbours(vertex);
        if(neighbours != null) {
            for (String neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    parent.put(neighbour,vertex);
                    if(DFSUtil(neighbour, visited, visitedNodes, parent, endVertex)) return true;
                }
            }
        }
        return false;
    }
    private List<String> path(Map<String,String> parent, String start, String end) {
        List<String> path = new ArrayList<>();
        String current = end;
        while(current!=null){
            path.add(0, current);
            current = parent.get(current);
        }
        return path;
    }

    private void  printOrderOfNodes(List<String> visitedNodes){
        System.out.print("Order of nodes : ");
        for (int i = 0; i < visitedNodes.size(); i++) {
            System.out.print(visitedNodes.get(i) +  " ");
        }
        System.out.println();
    }
}

public class q2{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph2 graph = new Graph2();

        System.out.println("Enter edges [-1 to exit]:");
        while (true) {
            String n1 = sc.nextLine();
            String n2 = sc.nextLine();
            if (Objects.equals(n1, "-1") || Objects.equals(n2, "-1")) {
                break;
            }
            graph.addEdge(n1, n2);
        }
        System.out.println("Enter src and dest edge");
        String src = sc.nextLine();
        String dest = sc.nextLine();
        System.out.println("----- BFS Result ----");
        List<String> path1 = graph.BFS(src, dest);
        printTraversePath(path1, src, dest);
        System.out.println("----- DFS Result ----");
        List<String> path2 = graph.DFS(src, dest);
        printTraversePath(path2, src, dest);
    }

    public static  void printTraversePath(List<String> path, String src, String dest){
        if (path.size() > 0) {
            System.out.println("There is a path exists between " + src + " and " + dest);
            System.out.println("The Solution path ");
            for (String vertex : path) {
                System.out.print(vertex + " ");
            }
            System.out.println();
        } else {
            System.out.println("There is no path between "+src+" and "+dest);
        }
    }
}
