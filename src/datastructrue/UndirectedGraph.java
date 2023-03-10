package datastructrue;

import java.util.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

public class UndirectedGraph {

    private HashMap<Integer, HashSet<Integer>> adjacencyList;

    public UndirectedGraph() {
        adjacencyList = new HashMap<Integer, HashSet<Integer>>();
    }

    public void addVertex(int vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            adjacencyList.put(vertex, new HashSet<Integer>());
        }
    }

    public void addEdge(int vertex1, int vertex2) {
        addVertex(vertex1);
        addVertex(vertex2);
        adjacencyList.get(vertex1).add(vertex2);
        adjacencyList.get(vertex2).add(vertex1);
    }

    public void removeVertex(int vertex) {
        if (adjacencyList.containsKey(vertex)) {
            HashSet<Integer> neighbors = adjacencyList.get(vertex);
            for (int neighbor : neighbors) {
                adjacencyList.get(neighbor).remove(vertex);
            }
            adjacencyList.remove(vertex);
        }
    }

    public void removeEdge(int vertex1, int vertex2) {
        if (adjacencyList.containsKey(vertex1) && adjacencyList.containsKey(vertex2)) {
            adjacencyList.get(vertex1).remove(vertex2);
            adjacencyList.get(vertex2).remove(vertex1);
        }
    }

    public void dfs(int start) {
        HashSet<Integer> visited = new HashSet<Integer>();
        dfsHelper(start, visited);
    }

    private void dfsHelper(int vertex, HashSet<Integer> visited) {
        visited.add(vertex);
        System.out.print(vertex + " ");
        for (int neighbor : adjacencyList.get(vertex)) {
            if (!visited.contains(neighbor)) {
                dfsHelper(neighbor, visited);
            }
        }
    }

    public void bfs(int start) {
        HashSet<Integer> visited = new HashSet<Integer>();
        Queue<Integer> queue = new LinkedList<Integer>();
        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");
            for (int neighbor : adjacencyList.get(vertex)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
    }

    public List<Integer> shortestPath(int start, int end) {
        HashMap<Integer, Integer> distance = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> previous = new HashMap<Integer, Integer>();
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer vertex1, Integer vertex2) {
                return distance.get(vertex1) - distance.get(vertex2);
            }
        });

        distance.put(start, 0);
        previous.put(start, null);
        queue.offer(start);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            if (vertex == end) {
                break;
            }
            for (int neighbor : adjacencyList.get(vertex)) {
                int newDistance = distance.get(vertex) + 1;
                if (!distance.containsKey(neighbor) || newDistance < distance.get(neighbor)) {
                    distance.put(neighbor, newDistance);
                    previous.put(neighbor, vertex);
                    queue.offer(neighbor);
                }
            }
        }

        List<Integer> path = new ArrayList<Integer>();
        if (!previous.containsKey(end)) {
            return path;
        }
        Integer current = end;  // 从map获取V可能为null
        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    public Set<Edge> minimumSpanningTree() {
        Set<Edge> mst = new HashSet<Edge>();
        PriorityQueue<Edge> queue = new PriorityQueue<Edge>(new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                return edge1.weight - edge2.weight;
            }
        });
        HashSet<Integer> visited = new HashSet<Integer>();
        int start = adjacencyList.keySet().iterator().next();
        visited.add(start);
        for (int neighbor : adjacencyList.get(start)) {
            queue.offer(new Edge(start, neighbor, 1));
        }

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            int vertex1 = edge.vertex1;
            int vertex2 = edge.vertex2;
            int weight = edge.weight;
            if (visited.contains(vertex1) && visited.contains(vertex2)) {
                continue;
            }
            visited.add(vertex1);
            visited.add(vertex2);
            mst.add(edge);
            for (int neighbor : adjacencyList.get(vertex1)) {
                if (!visited.contains(neighbor)) {
                    queue.offer(new Edge(vertex1, neighbor, 1));
                }
            }
            for (int neighbor : adjacencyList.get(vertex2)) {
                if (!visited.contains(neighbor)) {
                    queue.offer(new Edge(vertex2, neighbor, 1));
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
        UndirectedGraph directedGraph = new UndirectedGraph();
        directedGraph.addEdge(0, 1);
        directedGraph.addEdge(0, 2);
        directedGraph.addEdge(1, 2);
        directedGraph.addEdge(2, 3);
        directedGraph.addEdge(3, 4);
        directedGraph.addEdge(4, 5);

        System.out.println("DFS:");
        directedGraph.dfs(0);
        System.out.println();

        System.out.println("BFS:");
        directedGraph.bfs(0);
        System.out.println();

        System.out.println("Shortest Path from 0 to 5:");
        List<Integer> shortestPath = directedGraph.shortestPath(0, 5);
        System.out.println(shortestPath);

        System.out.println("Minimum Spanning Tree:");
        Set<Edge> mst = directedGraph.minimumSpanningTree();
        for (Edge edge : mst) {
            System.out.println(edge.vertex1 + " - " + edge.vertex2);
        }

        /*
        DFS:
        0 1 2 3 4 5
        BFS:
        0 1 2 3 4 5
        Shortest Path from 0 to 5:
        [0, 2, 3, 4, 5]
        Minimum Spanning Tree:
        0 - 1
        1 - 2
        2 - 3
        3 - 4
        4 - 5
         */
    }
}

