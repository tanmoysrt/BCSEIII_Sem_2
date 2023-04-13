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

    public List<String> DLS(String startVertex, String endVertex, int depth) {
        Map<String,String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();
        List<String> visitedNodes = new ArrayList<>();
        DLSUtil(startVertex, visited, visitedNodes, parent, endVertex, depth);
        printOrderOfNodes(visitedNodes);
        return path(parent, startVertex, endVertex);
    }

    private boolean DLSUtil(String vertex, Set<String> visited, List<String> visitedNodes, Map<String,String> parent, String endVertex, int depth) {
        visited.add(vertex);
        visitedNodes.add(vertex);
        if(Objects.equals(vertex, endVertex)) return true;
        if(depth == 0) return false;
        List<String> neighbours = getNeighbours(vertex);
        if(neighbours != null) {
            for (String neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    parent.put(neighbour,vertex);
                    if(DLSUtil(neighbour, visited, visitedNodes, parent, endVertex, depth-1)) return true;
                }
            }
        }
        return false;
    }

    public List<String> IDDFS(String startVertex, String endVertex, int maxDepth) {
        Map<String,String> parent = new HashMap<>();
        List<String> visitedNodes = new ArrayList<>();
        for (int i = 0; i < maxDepth; i++) {
            Set<String> visited = new HashSet<>();
            if(DLSUtil(startVertex, visited, visitedNodes, parent, endVertex, i)) {
                printOrderOfNodes(visitedNodes);
                return path(parent, startVertex, endVertex);
            }
        }
        return new ArrayList<>();
    }

    // IBS full form Iterative Best First Search, it is a variant of A* search algorithm
    public List<String> IBS(String startVertex, String endVertex) {
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
        System.out.println("----- DLS Result ----");
        System.out.println("Enter depth");
        int depth = sc.nextInt();
        List<String> path3 = graph.DLS(src, dest, depth);
        printTraversePath(path3, src, dest);
        System.out.println("----- IDDFS Result ----");
        System.out.println("Enter max depth");
        int maxDepth = sc.nextInt();
        List<String> path4 = graph.IDDFS(src, dest, maxDepth);
        printTraversePath(path4, src, dest);
        System.out.println("----- IBS Result ----");
        List<String> path5 = graph.IBS(src, dest);
        printTraversePath(path5, src, dest);


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

