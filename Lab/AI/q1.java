import java.util.*;

class Graph {
    private Map<Integer, List<Integer>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addEdge(int vertexA, int vertexB) {
        List<Integer> neighbours = adjacencyList.get(vertexA);
        if (neighbours == null) {
            neighbours = new ArrayList<>();
            adjacencyList.put(vertexA, neighbours);
        }
        neighbours.add(vertexB);
        neighbours = adjacencyList.get(vertexB);
        if(neighbours == null){
            neighbours = new ArrayList<>();
            adjacencyList.put(vertexB, neighbours);
        }
        neighbours.add(vertexA);
    }

    public List<Integer> getNeighbours(int vertex) {
        List<Integer> neighbours = adjacencyList.get(vertex);
        if(neighbours == null) return new ArrayList<>();
        return neighbours;
    }


    public List<Integer> BFS(int startVertex, int endVertex) {
        Map<Integer,Integer> parent = new HashMap<>();
        List<Integer> visitedNodes = new ArrayList<>();

        Set<Integer> visited = new HashSet<>();
        Deque<Integer> queue = new ArrayDeque<>();
        visited.add(startVertex);
        queue.add(startVertex);
        parent.put(startVertex,null);
        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            visitedNodes.add(currentVertex);
            if (currentVertex == endVertex) {
                printOrderOfNodes(visitedNodes);
                return path(parent, startVertex, endVertex);
            }
            List<Integer> neighbours = getNeighbours(currentVertex);
            for (int neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                    parent.put(neighbour,currentVertex);
                }
            }
        }
        return new ArrayList<>();
    }

    public List<Integer> DFS(int startVertex, int endVertex) {
        Map<Integer,Integer> parent = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        List<Integer> visitedNodes = new ArrayList<>();
        DFSUtil(startVertex, visited, visitedNodes, parent, endVertex);
        printOrderOfNodes(visitedNodes);
        return path(parent, startVertex, endVertex);
    }

    private boolean DFSUtil(int vertex, Set<Integer> visited, List<Integer> visitedNodes, Map<Integer,Integer> parent, int endVertex) {
        visited.add(vertex);
        visitedNodes.add(vertex);
        if(vertex == endVertex) return true;
        List<Integer> neighbours = getNeighbours(vertex);
        if(neighbours != null) {
            for (int neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    parent.put(neighbour,vertex);
                    if(DFSUtil(neighbour, visited, visitedNodes, parent, endVertex)) return true;
                }
            }
        }
        return false;
    }
    private List<Integer> path(Map<Integer,Integer> parent, int start, int end) {
        List<Integer> path = new ArrayList<>();
        Integer current = end;
        while(current!=null){
            path.add(0, current);
            current = parent.get(current);
        }
        return path;
    }

    private void  printOrderOfNodes(List<Integer> visitedNodes){
        System.out.print("Order of nodes : ");
        for (int i = 0; i < visitedNodes.size(); i++) {
            System.out.print(visitedNodes.get(i) +  " ");
        }
        System.out.println();
    }
}

public class q1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph graph = new Graph();

        System.out.println("Enter edges [-1 to exit]:");
        while (true) {
            int n1 = sc.nextInt();
            int n2 = sc.nextInt();
            if (n1 == -1 || n2 == -1) {
                break;
            }
            graph.addEdge(n1, n2);
        }
        System.out.println("Enter src and dest edge");
        int src = sc.nextInt();
        int dest = sc.nextInt();
        System.out.println("----- BFS Result ----");
        List<Integer> path1 = graph.BFS(src, dest);
        printTraversePath(path1, src, dest);
        System.out.println("----- DFS Result ----");
        List<Integer> path2 = graph.DFS(src, dest);
        printTraversePath(path2, src, dest);
    }

    public static  void printTraversePath(List<Integer> path, int src, int dest){
        if (path.size() > 0) {
            System.out.println("There is a path exists between " + src + " and " + dest);
            System.out.println("The Solution path ");
            for (int vertex : path) {
                System.out.print(vertex + " ");
            }
            System.out.println();
        } else {
            System.out.println("There is no path between "+src+" and "+dest);
        }
    }
}

