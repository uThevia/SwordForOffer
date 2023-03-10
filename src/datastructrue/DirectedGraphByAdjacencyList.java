package datastructrue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Stack;

/**
 * 有向图
 * 邻接表法
 * 包括拓扑排序, Dijkstra, Bellman-Ford最短路径算法
 */
public class DirectedGraphByAdjacencyList {
    private int V;
    private LinkedList<Integer>[] adjList;

    public DirectedGraphByAdjacencyList(int v) {
        V = v;
        adjList = new LinkedList[V];
        for (int i = 0; i < V; ++i) {
            adjList[i] = new LinkedList<Integer>();
        }
    }

    public void addEdge(int src, int dest) {
        adjList[src].add(dest);
    }

    public void dps(int start) {
        boolean[] visited = new boolean[V];
        dfsUtil(start, visited);
    }

    private void dfsUtil(int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(v + " ");
        Iterator<Integer> iter = adjList[v].listIterator();
        while (iter.hasNext()) {
            int n = iter.next();
            if (!visited[n]) {
                dfsUtil(n, visited);
            }
        }
    }

    public void bps(int start) {
        boolean[] visited = new boolean[V];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        visited[start] = true;
        queue.add(start);
        while (queue.size() != 0) {
            start = queue.poll();
            System.out.print(start + " ");
            Iterator<Integer> iter = adjList[start].listIterator();
            while (iter.hasNext()) {
                int n = iter.next();
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }

    public void topologicalSort() {
        Stack<Integer> stack = new Stack<Integer>();
        boolean[] visited = new boolean[V];
        for (int i = 0; i < V; ++i) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }
        while (!stack.empty()) {
            System.out.print(stack.pop() + " ");
        }
    }

    private void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;
        Iterator<Integer> iter = adjList[v].listIterator();
        while (iter.hasNext()) {
            int n = iter.next();
            if (!visited[n]) {
                topologicalSortUtil(n, visited, stack);
            }
        }
        stack.push(v);
    }

    public void dijkstra(int start) {
        PriorityQueue<Node> pq = new PriorityQueue<Node>(V, new Node());
        int[] dist = new int[V];
        for (int i = 0; i < V; ++i) {
            dist[i] = Integer.MAX_VALUE;
        }
        pq.add(new Node(start, 0));
        dist[start] = 0;
        while (!pq.isEmpty()) {
            int u = pq.poll().id;
            Iterator<Integer> iter = adjList[u].listIterator();
            while (iter.hasNext()) {
                int v = iter.next();
                int weight = 1; // 假设每条边的权值都为1
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }
        System.out.println("Dijkstra's shortest path algorithm:");
        System.out.println("Vertex Distance from Source");
        for (int i = 0; i < V; ++i) {
            System.out.println(i + "\t\t" + dist[i]);
        }
    }

    public void bellmanFord(int start) {
        int[] dist = new int[V];
        for (int i = 0; i < V; ++i) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[start] = 0;
        for (int i = 1; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                Iterator<Integer> iter = adjList[j].listIterator();
                while (iter.hasNext()) {
                    int n = iter.next();
                    int weight = 1; // 假设每条边的权值都为1
                    if (dist[j] != Integer.MAX_VALUE && dist[j] + weight < dist[n]) {
                        dist[n] = dist[j] + weight;
                    }
                }
            }
        }
        System.out.println("Bellman-Ford shortest path algorithm:");
        System.out.println("Vertex Distance from Source");
        for (int i = 0; i < V; ++i) {
            System.out.println(i + "\t\t" + dist[i]);
        }
    }

    private class Node implements Comparator<Node> {
        int id;
        int cost;

        public Node() {
        }

        public Node(int id, int cost) {
            this.id = id;
            this.cost = cost;
        }

        @Override
        public int compare(Node n1, Node n2) {
            return n1.cost - n2.cost;
        }
    }

    public static void main(String[] args) {
        DirectedGraphByAdjacencyList graph = new DirectedGraphByAdjacencyList(6);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 2);

        System.out.println("Depth-first traversal:");
        graph.dps(0);
        System.out.println();

        System.out.println("Breadth-first traversal:");
        graph.bps(0);
        System.out.println();

        System.out.println("Topological sorting:");
        graph.topologicalSort();
        System.out.println();

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);

        graph.dijkstra(0);

        graph.bellmanFord(0);
    }
}