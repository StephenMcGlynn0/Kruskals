//Stephen Mc Glynn

import java.util.*;

// Represents a single edge in the graph
class Edge implements Comparable<Edge> {
    int src, dest, weight;

    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    // Compare edges based on weight (used for sorting)
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

class KruskalSimple {
    int vertices;
    List<Edge> edges = new ArrayList<>();

    KruskalSimple(int v) {
        vertices = v;
    }

    void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }

    // Find the representative (root) of a set — used in union-find
    int find(int parent[], int i) {
        if (parent[i] == i)
            return i;
        return find(parent, parent[i]); // Recursively find parent
    }

    // Union operation — connect two disjoint sets
    void union(int parent[], int x, int y) {
        int xset = find(parent, x);
        int yset = find(parent, y);
        parent[xset] = yset;
    }

    void kruskalMST() {
        Collections.sort(edges);
        int[] parent = new int[vertices];
        for (int i = 0; i < vertices; i++)
            parent[i] = i;

        List<Edge> result = new ArrayList<>();
        for (Edge currentEdge : edges) {

            // Find the representative (root) of each vertex in the edge
            int rootOfSource = find(parent, currentEdge.src);
            int rootOfDestination = find(parent, currentEdge.dest);

            // If the two vertices belong to different sets,
            // adding this edge will NOT form a cycle.
            if (rootOfSource != rootOfDestination) {
                result.add(currentEdge); // Include this edge in the MST

                // Merge the two sets so future edges know these vertices are connected
                union(parent, rootOfSource, rootOfDestination);
            }
        }

        System.out.println("Edges in the MST:");
        int totalWeight = 0;
        for (Edge e : result) {
            System.out.println(e.src + " -- " + e.dest + " == " + e.weight);
            totalWeight += e.weight;
        }
        System.out.println("Total weight: " + totalWeight + "\n");
    }

    public static void main(String[] args) {
        System.out.println("=== Test 1: Basic graph ===");
        KruskalSimple g1 = new KruskalSimple(4);
        g1.addEdge(0, 1, 10);
        g1.addEdge(0, 2, 6);
        g1.addEdge(0, 3, 5);
        g1.addEdge(1, 3, 15);
        g1.addEdge(2, 3, 4);
        g1.kruskalMST();

        System.out.println("=== Test 2: Disconnected graph ===");
        KruskalSimple g2 = new KruskalSimple(5);
        g2.addEdge(0, 1, 2);
        g2.addEdge(1, 2, 3);
        // 3 and 4 are isolated
        g2.kruskalMST();

        System.out.println("=== Test 3: Single edge ===");
        KruskalSimple g3 = new KruskalSimple(2);
        g3.addEdge(0, 1, 7);
        g3.kruskalMST();

        System.out.println("=== Test 4: All equal weights ===");
        KruskalSimple g4 = new KruskalSimple(3);
        g4.addEdge(0, 1, 1);
        g4.addEdge(1, 2, 1);
        g4.addEdge(0, 2, 1);
        g4.kruskalMST();
    }
}
