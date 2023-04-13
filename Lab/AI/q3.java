package ailab;
import java.util.*;
import java.util.stream.*;
public class q3 {
    static class Node implements Comparable<Node>{
        byte a[], p;
        Node(byte a[]){
            this.a = new byte[9];
            for(byte i = 0 ; i < 9 ; p = a[i] == 0 ? i : p, this.a[i] = a[i++]);
        }
        boolean equal(byte[] a) {
            return ((this.a[0] == a[0]) && (this.a[1] == a[1]) && (this.a[2] == a[2]) &&
                    (this.a[3] == a[3]) && (this.a[4] == a[4]) && (this.a[5] == a[5]) &&
                    (this.a[6] == a[6]) && (this.a[7] == a[7]) && (this.a[8] == a[8]));
        }
        static boolean equal(Node x, Node y) {
            return ((x.a[0] == y.a[0]) && (x.a[1] == y.a[1]) && (x.a[2] == y.a[2]) &&
                    (x.a[3] == y.a[3]) && (x.a[4] == y.a[4]) && (x.a[5] == y.a[5]) &&
                    (x.a[6] == y.a[6]) && (x.a[7] == y.a[7]) && (x.a[8] == y.a[8]));
        }
        public String toString() {
            return "["+a[0]+", "+a[1]+", "+a[2]+", "+
                    a[3]+", "+a[4]+", "+a[5]+", "+
                    a[6]+", "+a[7]+", "+a[8]+"]";
        }
        void display(String s1, String s2) {
            System.out.println(	s1+a[0]+""+a[1]+""+a[2]+"\n"+
                    s2+a[3]+""+a[4]+""+a[5]+"\n"+
                    s2+a[6]+""+a[7]+""+a[8]+"\n");
        }
        @Override
        public int compareTo(Node o) {
            // TODO Auto-generated method stub
            return 0;
        }
    }
    class Graph{
        Map<Node, Set<Node>> L;	double T = 0.0;
        Node s, e;	byte a[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        final String[] M = {"L", "R", "U", "D"};
        Graph(byte[] s, byte[] e){
            if(valid(s) && valid(e)) {
                this.L = new HashMap<>();
                this.s = new Node(s);
                this.e = new Node(e);
                display();
            }
            else
                System.out.println("Invalid inputs");
        }
        boolean valid(byte[] x) {
            if(x.length == 9) {
                if ((x[0]*(x[0] - 8) <= 0) && (x[1]*(x[1] - 8) <= 0) && (x[2]*(x[2] - 8) <= 0) &&
                        (x[3]*(x[3] - 8) <= 0) && (x[4]*(x[4] - 8) <= 0) && (x[5]*(x[5] - 8) <= 0) &&
                        (x[6]*(x[6] - 8) <= 0) && (x[7]*(x[7] - 8) <= 0) && (x[8]*(x[8] - 8) <= 0)) {
                    a[0] = a[1] = a[2] = a[3] = a[4] = a[5] = a[6] = a[7] = a[8] = 0;
                    a[x[0]]++;	a[x[1]]++;	a[x[2]]++;
                    a[x[3]]++;	a[x[4]]++;	a[x[5]]++;
                    a[x[6]]++;	a[x[7]]++;	a[x[8]]++;
                    return( (a[0] == 1) && (a[1] == 1) && (a[2] == 1) &&
                            (a[3] == 1) && (a[4] == 1) && (a[5] == 1) &&
                            (a[6] == 1) && (a[7] == 1) && (a[8] == 1));
                }
            }
            return false;
        }
        // Node is isomorphic to S9, Node n <=> n.a[p] = 0
        // transition(n) = { L if p%3 > 0, R if p%3 < 2, U if p/3 > 0, D if p/3 < 2}
        // m = {(0, L), (1, R), (2, U), (3, D)}
        int tran(Node x, int c) {
            if(c == 0)
                return (x.p % 3 > 0) ? x.p - 1 : -1;
            else if(c == 1)
                return (x.p % 3 < 2) ? x.p + 1 : -1;
            else if(c == 2)
                return (x.p / 3 > 0) ? x.p - 3 : -1;
            else
                return (x.p / 3 < 2) ? x.p + 3 : -1;
        }
        void tran(Node x) {
//			System.out.println("Moves for " + x + " = " + Arrays.asList(0, 1, 2, 3).stream()
//					.filter(v -> tran(x, v) > -1).map(v -> conv(swap(x, v)))
//					.collect(Collectors.toList()) + "\n");
            System.out.println("Moves for " + x + " = " + Arrays.asList(0, 1, 2, 3).stream()
                    .filter(v -> tran(x, v) > -1).map(v -> M[v]).collect(Collectors.toList()) + "\n");
        }
        byte[] swap(Node x, int c) {
            byte b[] = new byte[9];
            Stream.iterate(0, i -> i + 1).limit(9).forEach(i -> b[i] = x.a[i]);
            b[x.p] = b[c]; b[c] = 0;
            return b;
        }
        String conv(byte[] b) {
            return "[" + b[0] + ", " + b[1] + ", " + b[2] + ", " + b[3] + ", " + b[4] + ", " +
                    b[5] + ", " + b[6] + ", " + b[7] + ", " + b[8] + "]";
        }
        public void display() {
            System.out.println("8-Puzzle problem with\n" +
                    "Start node = \t"+s.a[0]+""+s.a[1]+""+s.a[2]+"\t\tEnd node =\t"+e.a[0]+""+e.a[1]+""+e.a[2]+
                    "\n\t\t"+s.a[3]+""+s.a[4]+""+s.a[5]+"\t\t\t\t"+e.a[3]+""+e.a[4]+""+e.a[5]+
                    "\n\t\t"+s.a[6]+""+s.a[7]+""+s.a[8]+"\t,\t\t\t"+e.a[6]+""+e.a[7]+""+e.a[8]+"\n");
        }
        void showAL() {
            System.out.println("Adjacency list : {\t" + L.entrySet().stream()
                    .map(x -> x.getKey() + " = [" + x.getValue().stream().map(y -> y + "")
                            .reduce((a, b) -> a + ", " + b).get() + "]")
                    .reduce((a, b) -> a + ",\n\t\t\t" + b).get() + "}");
        }
        void path(boolean c, List<Node> p) {
            T = System.nanoTime() - T;	T *= 0.000001;
            if(c) {
                String s[] = p.stream().reduce(new String[] {"Path ->", "\t", "\t"},
                        (a, n) -> new String[] {
                                a[0]+"\t"+n.a[0]+""+n.a[1]+""+n.a[2]+"",
                                a[1]+"\t"+n.a[3]+""+n.a[4]+""+n.a[5]+"",
                                a[2]+"\t"+n.a[6]+""+n.a[7]+""+n.a[8]+""},
                        (a, b) -> new String[] {
                                a[0]+"\t"+b[0],
                                a[1]+"\t"+b[1],
                                a[2]+"\t"+b[2]
                });
                System.out.println(s[0] + "\n" + s[1] + "\n" + s[2] + "\n\nTime = " + T + "ms\n");
            }
            else
                System.out.println("No path found\n\nTime = " + T + "ms\n");
        }
        private boolean bfs(Queue<Node> l, List<Node> v, List<Node> p) {
            Map<Node, Node> q = new HashMap<>();
            Node x = null;	int z = 0;
            for(l.add(s) , z = 1 ; z > 0 ; ){
                x = l.peek();
                //(x = l.peek()).display("Current node =\t", "\t\t");
                v.add(0, x);
                //tran(x);
                if(Node.equal(x, e))
                    break;
                z += Arrays.asList(0, 1, 2, 3).stream().map(i -> tran(l.peek(), i))
                        .filter(i -> i > -1).map(i -> swap(l.peek(), i))
                        .filter(i -> !v.stream().parallel().anyMatch(j -> j.equal(i)))
                        .filter(i -> !l.stream().parallel().anyMatch(j -> j.equal(i)))
                        .reduce(-1, (y, i) -> {Node k = new Node(i);
                            l.add(k); q.put(k, l.peek()); return ++y;}, (u, y) -> u + y);
                l.remove();
            }
            if(z == 0)
                return false;
            for(p.add(0, x) ; x != s ; x = q.get(x) , p.add(0, x));
            return true;
        }
        private boolean bls(Queue<Node> l, List<Node> v, List<Node> p, int c) {
            Map<Node, Node> q = new HashMap<>();
            Node x = null;	int z = 0;
            for(l.add(s) , z = 1 ; z > 0 ; ){
                x = l.peek();
                //(x = l.peek()).display("Current node =\t", "\t\t");
                v.add(0, x);
                //tran(x);
                if(Node.equal(x, e))
                    break;
                z += Arrays.asList(0, 1, 2, 3).stream().map(i -> tran(l.peek(), i))
                        .filter(i -> i > -1).map(i -> swap(l.peek(), i))
                        .filter(i -> !v.stream().parallel().anyMatch(j -> j.equal(i)))
                        .filter(i -> !l.stream().parallel().anyMatch(j -> j.equal(i)))
                        .limit(c).reduce(-1, (y, i) -> {Node k = new Node(i);
                            l.add(k); q.put(k, l.peek()); return ++y;}, (u, y) -> u + y);
                l.remove();
            }
            if(z == 0)
                return false;
            for(p.add(0, x) ; x != s ; x = q.get(x) , p.add(0, x));
            return true;
        }
        private boolean ibs(Queue<Node> l, List<Node> v, List<Node> p) {
            int d = 4, i = 0;
            for(i = 0 ; i <= d ; i++) {
                System.out.println("Breadth-limit = " + i + "\n");
                if(bls(l, v, p, i))
                    return true;
                //path(false, p);
                v = new LinkedList<>();
                l = new PriorityQueue<>();
            }
            return false;
        }
        private boolean dfs(Node s, List<Node> v, List<Node> p) {
            //s.display("Current node =\t", "\t\t");
            v.add(0, s);
            p.add(0, s);
            if(Node.equal(s, e)) {
                Collections.reverse(p);
                return true;
            }
            //tran(s);
            if(Arrays.asList(0, 1, 2, 3).stream().map(i -> tran(s, i)).filter(i -> i > -1)
                    .map(i -> swap(s, i)).filter(i -> !v.stream().parallel().anyMatch(j -> j.equal(i)))
                    .reduce(false, (y, i) -> y ? y : dfs(new Node(i), v, p), (y, b) -> y || b))
                return true;
            p.remove(0);
            return false;
        }
        private boolean dls(Node s, List<Node> v, List<Node> p, int c, int d) {
            //s.display("Current node =\t", "\t\t");
            v.add(0, s);
            p.add(0, s);
            if(Node.equal(s, e)) {
                Collections.reverse(p);
                return true;
            }
            if(d == c) {
                p.remove(0);
                return false;
            }
            //tran(s);
            if(Arrays.asList(0, 1, 2, 3).stream().map(i -> tran(s, i)).filter(i -> i > -1)
                    .map(i -> swap(s, i)).filter(i -> !v.stream().parallel().anyMatch(j -> j.equal(i)))
                    .reduce(false, (y, i) -> y ? y : dls(new Node(i), v, p, c, d + 1), (y, b) -> y || b))
                return true;
            p.remove(0);
            return false;
        }
        private boolean ids(Node s, List<Node> v, List<Node> p) {
            int d = 200, i = 0;
            for(i = 0 ; i <= d ; i++) {
                System.out.println("Depth-limit = " + i + "\n");
                if(dls(s, v, p, i, 0))
                    return true;
                v = new LinkedList<>();
            }
            return false;
        }
        void search(int c) {
            List<Node> p, v;	Queue<Node> l;
            System.out.println("Running search techniques...\n");
            p = new LinkedList<>(); v = new LinkedList<>(); l = new LinkedList<>();
            System.out.println("\nRunning bfs :-\n");	T = System.nanoTime();
            path(bfs(l, v, p), p);
			if(c > -1) {
				p = new LinkedList<>(); v = new LinkedList<>(); l = new LinkedList<>();
				System.out.println("\nRunning bls with c = "+ c +" :-\n");	T = System.nanoTime();
				path(bls(l, v, p, c), p);
			}
			p = new LinkedList<>(); v = new LinkedList<>(); l = new PriorityQueue<>();
			System.out.println("\nRunning ibs :-\n");	T = System.nanoTime();
			path(ibs(l, v, p), p);
			p = new LinkedList<>(); v = new LinkedList<>(); l = new LinkedList<>();
			System.out.println("\nRunning dfs :-\n");	T = System.nanoTime();
			path(dfs(s, v, p), p);
			if(c > -1) {
				p = new LinkedList<>(); v = new LinkedList<>(); l = new LinkedList<>();
				System.out.println("\nRunning dls with c = " + c + " :-\n");	T = System.nanoTime();
				path(dls(s, v, p, c, 0), p);
			}
			p = new LinkedList<>(); v = new LinkedList<>(); l = new LinkedList<>();
			System.out.println("\nRunning ids :-\n");	T = System.nanoTime();
			path(ids(s, v, p), p);
        }
    }
    public static void main(String args[]) {
        //0, 8, 7, 6, 5, 4, 3, 2, 1		-> 28
        //1, 2, 3, 0, 8, 7, 6, 5, 4
        //1, 2, 3, 4, 5, 6, 7, 0, 8
        //1, 2, 3, 4, 5, 6, 0, 7, 8
        //1, 2, 3, 4, 5, 0, 7, 8, 6
        //1, 2, 3, 4, 6, 8, 7, 5, 0
        byte s[] = {1, 2, 3, 4, 5, 6, 7, 8, 0}, e[] = {1, 2, 3, 4, 5, 0, 7, 8, 6};
        q3 ob = new q3();
        Graph G = ob.new Graph(s, e);
        G.search(2);
    }
}