package datastructrue;

import java.util.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

public class DirectedGraph {
    private Map<Integer, List<Edge>> adjacencyList = new HashMap<Integer, List<Edge>>();

    public void addEdge(int vertex1, int vertex2, int weight) {
        List<Edge> neighbors = adjacencyList.getOrDefault(vertex1, new ArrayList<Edge>());
        neighbors.add(new Edge(vertex1, vertex2, weight));
        adjacencyList.put(vertex1, neighbors);
    }

    public void dfs(int start) {
        Set<Integer> visited = new HashSet<Integer>();
        dfs(start, visited);
    }

    private void dfs(int vertex, Set<Integer> visited) {
        visited.add(vertex);
        System.out.print(vertex + " ");
        List<Edge> neighbors = adjacencyList.get(vertex);
        if (neighbors != null) {
            for (Edge neighbor : neighbors) {
                if (!visited.contains(neighbor.vertex2)) {
                    dfs(neighbor.vertex2, visited);
                }
            }
        }
    }

    public void bfs(int start) {
        Queue<Integer> queue = new LinkedList<Integer>();
        Set<Integer> visited = new HashSet<Integer>();
        queue.offer(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");
            List<Edge> neighbors = adjacencyList.get(vertex);
            if (neighbors != null) {
                for (Edge neighbor : neighbors) {
                    if (!visited.contains(neighbor.vertex2)) {
                        queue.offer(neighbor.vertex2);
                        visited.add(neighbor.vertex2);
                    }
                }
            }
        }
    }

    public List<Integer> shortestPath(int start, int end) {
        Map<Integer, Integer> distances = new HashMap<Integer, Integer>();
        Map<Integer, Integer> previous = new HashMap<Integer, Integer>();
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer vertex1, Integer vertex2) {
                return distances.get(vertex1) - distances.get(vertex2);
            }
        });
        Set<Integer> visited = new HashSet<Integer>();

        distances.put(start, 0);
        queue.offer(start);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            if (visited.contains(vertex)) {
                continue;
            }
            visited.add(vertex);
            if (vertex == end) {
                break;
            }
            List<Edge> neighbors = adjacencyList.get(vertex);
            if (neighbors != null) {
                for (Edge neighbor : neighbors) {
                    int neighborVertex = neighbor.vertex2;
                    int neighborWeight = neighbor.weight;
                    int distance = distances.getOrDefault(vertex, Integer.MAX_VALUE) + neighborWeight;
                    if (distance < distances.getOrDefault(neighborVertex, Integer.MAX_VALUE)) {
                        distances.put(neighborVertex, distance);
                        previous.put(neighborVertex, vertex);
                        queue.offer(neighborVertex);
                    }
                }
            }
        }

        List<Integer> path = new ArrayList<Integer>();
        int current = end;
        while (current != start) {
            path.add(current);
            current = previous.get(current);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }

    public Set<Edge> minimumSpanningTree() {
        Set<Edge> mst = new HashSet<Edge>();
        Set<Integer> visited = new HashSet<Integer>();
        PriorityQueue<Edge> queue = new PriorityQueue<Edge>(new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                return edge1.weight - edge2.weight;
            }
        });

        int startVertex = adjacencyList.keySet().iterator().next();
        visited.add(startVertex);
        queue.addAll(adjacencyList.get(startVertex));

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            if (visited.contains(edge.vertex2)) {
                continue;
            }
            visited.add(edge.vertex2);
            mst.add(edge);
            List<Edge> neighbors = adjacencyList.get(edge.vertex2);
            if (neighbors != null) {
                for (Edge neighbor : neighbors) {
                    if (!visited.contains(neighbor.vertex2)) {
                        queue.offer(neighbor);
                    }
                }
            }
        }

        return mst;
    }

    private class Edge {
        public int vertex1;
        public int vertex2;
        public int weight;

        public Edge(int vertex1, int vertex2, int weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + vertex1;
            result = prime * result + vertex2;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Edge other = (Edge) obj;
            if (vertex1 != other.vertex1)
                return false;
            if (vertex2 != other.vertex2)
                return false;
            return true;
        }
    }

    public static void main(String[] args) {
        DirectedGraph graph = new DirectedGraph();
        graph.addEdge(1, 2, 5);
        graph.addEdge(1, 3, 6);
        graph.addEdge(2, 3, 1);
        graph.addEdge(2, 4, 2);
        graph.addEdge(3, 4, 5);
        graph.addEdge(4, 1, 4);

        System.out.println("DFS traversal:");
        graph.dfs(1);
        System.out.println();

        System.out.println("BFS traversal:");
        graph.bfs(1);
        System.out.println();

        System.out.println("Shortest path from vertex 1 to vertex 4:");
        List<Integer> shortestPath = graph.shortestPath(1, 4);
        for (Integer vertex : shortestPath) {
            System.out.print(vertex + " ");
        }
        System.out.println();

        System.out.println("Minimum spanning tree:");
        Set<Edge> mst = graph.minimumSpanningTree();
        for (Edge edge : mst) {
            System.out.println(edge.vertex1 + " -> " + edge.vertex2 + " (" + edge.weight + ")");
        }
        /*
        DFS traversal:
        1 2 3 4
        BFS traversal:
        1 2 3 4
        Shortest path from vertex 1 to vertex 4:
        1 2 4
        Minimum spanning tree:
        2 -> 3 (1)
        1 -> 2 (5)
        2 -> 4 (2)
        */
    }
}