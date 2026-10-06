import java.util.Arrays;

public class BellmanFord {

    static class Edge {
        int source;
        int destination;
        int weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static void bellmanFord(
            int vertices,
            Edge[] edges,
            int source) {

        int INF = Integer.MAX_VALUE;

        int[] distance = new int[vertices];

        Arrays.fill(distance, INF);

        distance[source] = 0;

        for (int i = 1; i < vertices; i++) {
            boolean updated = false;

            for (Edge edge : edges) {

                if (distance[edge.source] != INF &&
                    distance[edge.source] + edge.weight
                    < distance[edge.destination]) {

                    distance[edge.destination] =
                            distance[edge.source] + edge.weight;

                    updated = true;
                }
            }

            if (!updated) {
                break;
            }
        }

        for (Edge edge : edges) {
            if (distance[edge.source] != INF &&
                distance[edge.source] + edge.weight
                < distance[edge.destination]) {

                System.out.println(
                        "Graph contains a negative-weight cycle."
                );

                return;
            }
        }

        System.out.println(
                "Shortest distances from vertex "
                + source + ":"
        );

        for (int i = 0; i < vertices; i++) {
            if (distance[i] == INF) {
                System.out.println(
                        "Vertex " + i + ": INF"
                );
            } else {
                System.out.println(
                        "Vertex " + i + ": "
                        + distance[i]
                );
            }
        }
    }

    public static void main(String[] args) {

        int vertices = 5;

        Edge[] edges = {
            new Edge(0, 1, 6),
            new Edge(0, 2, 7),
            new Edge(1, 2, 8),
            new Edge(1, 3, 5),
            new Edge(1, 4, -4),
            new Edge(2, 3, -3),
            new Edge(2, 4, 9),
            new Edge(3, 1, -2),
            new Edge(4, 0, 2),
            new Edge(4, 3, 7)
        };

        bellmanFord(vertices, edges, 0);
    }
}
