import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

// Create a Graph class to store nodes and edges or download a Graph library
// Such as JUNG. Use it to implement Breadth First Search and Depth First Search
// Follow the video from class if you need a reference
public class GraphHW {

        public static class Node {
            private String name;

            public Node(String name) {
                this.name = name;
            }

            public String getId() {
                return name;
            }

            @Override
            public String toString() {
                return name;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                Node node = (Node) obj;
                return name.equals(node.name);
            }

           // @Override
//            public int hashCode() {
//                return name.hashCode();
//            }
        }

        public static class Edge {
            private String label;

            public Edge(String label) {
                this.label = label;
            }
            public String getLabel() {
                return label;
            }

            @Override
            public String toString() {
                return label;
            }
        }

    public static void main(String[] args) {
        SparseGraph<Node, Edge> g = new SparseGraph<>();

        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");
        Node d = new Node("D");

        g.addVertex(a);
        g.addVertex(b);
        g.addVertex(c);
        g.addVertex(d);

        g.addEdge(new Edge("ab"), a, b);
        g.addEdge(new Edge("bc"), b, c);
        g.addEdge(new Edge("cd"), c, d);
        g.addEdge(new Edge("dc"), d, c);

       BFSOnAll(g);
       DFSOnAll(g);
    }

    public static void BFS(Graph<Node, Edge> g, Node start) {
            Set<Node> visited = new HashSet<>();
            Queue<Node> queue = new LinkedList<>();

            visited.add(start);
            queue.add(start);

            while (!queue.isEmpty()) {
                Node current = queue.poll();
                System.out.print(current + " ");

                for(Node adjacent : g.getNeighbors(current)) {
                    if (!visited.contains(adjacent)) {
                        visited.add(adjacent);
                        queue.add(adjacent);
                    }
                }
            }
    }

    public static void DFS(Graph<Node, Edge> g, Node start) {
            Set<Node> visited = new HashSet<>();
            DFS2(g, start, visited);
    }
    public static void DFS2(Graph<Node, Edge> g, Node start, Set<Node> visited) {
        visited.add(start);
        System.out.print(start + " ");

        for(Node adjacent : g.getNeighbors(start)){
            if(!visited.contains(adjacent)) {
                visited.add(adjacent);
                DFS2(g, adjacent, visited);
            }
        }
    }

    public static void BFSOnAll(Graph<Node, Edge> g) {
        for (Node node : g.getVertices()) {
            System.out.print("BFS starting from " + node + ": ");
            BFS(g, node);
            System.out.println(); // line break
        }
    }
    public static void DFSOnAll(Graph<Node, Edge> g) {
        for (Node node : g.getVertices()) {
            System.out.print("DFS starting from " + node + ": ");
            DFS(g, node);
            System.out.println(); // line break
        }
    }


}