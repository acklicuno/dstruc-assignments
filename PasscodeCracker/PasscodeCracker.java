import java.io.*;
import java.util.*;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

public class PasscodeCracker {
    public static void main(String[] args) throws IOException {
        DirectedSparseGraph<Character, String> graph = new DirectedSparseGraph<>();
        Set<String> seenEdges = new HashSet<>();

        // keylog file line by line
        //Each being 3 total digits
        BufferedReader br = new BufferedReader(new FileReader("keylog.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            char a = line.charAt(0);
            char b = line.charAt(1);
            char c = line.charAt(2);

            // AMake vertexes for the digits
            graph.addVertex(a);
            graph.addVertex(b);
            graph.addVertex(c);
            // edges for login attempt in order
            String edge1 = a + "->" + b;
            String edge2 = b + "->" + c;
            //add edge
            //Only if not added
            if (!seenEdges.contains(edge1)) {
                graph.addEdge(edge1, a, b);
                seenEdges.add(edge1);
            }
            if (!seenEdges.contains(edge2)) {
                graph.addEdge(edge2, b, c);
                seenEdges.add(edge2);
            }
        }
        br.close();

        // Perform topological sort
        List<Character> passcode = topologicalSort(graph);

        // Print the passcode
        for (char ch : passcode) {
            System.out.print(ch);
        }
        System.out.println();
    }

    private static List<Character> topologicalSort(DirectedSparseGraph<Character, String> graph) {
        List<Character> result = new ArrayList<>();
        Map<Character, Integer> indegree = new HashMap<>();

        // For every edge
        // Increment the indegree of the destination node
        for (Character v : graph.getVertices()) {
            indegree.put(v, 0);
        }
        for (String edge : graph.getEdges()) {
            Character target = graph.getDest(edge);
            indegree.put(target, indegree.get(target) + 1);
        }

        // Find all nodes of indegree 0
        //Place these first
        Queue<Character> queue = new LinkedList<>();
        for (Character v : indegree.keySet()) {
            if (indegree.get(v) == 0) {
                queue.add(v);
            }
        }
        //While there is something in the queue
        // Remove nodes of indegree 0 and add to results
        while (!queue.isEmpty()) {
            Character current = queue.poll();
            result.add(current);
            //for all nodes pointed to, decrease the indeggree of neighbor
            //If 0, add to queue
            for (Character neighbor : graph.getSuccessors(current)) {
                indegree.put(neighbor, indegree.get(neighbor) - 1);
                if (indegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return result;
    }
}
