package datastructrue.graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 无向图
 * 邻接表法
 * 邻接表 `adj` 是一个数组，每个元素是一个链表，存储了该顶点的邻居。
 * `dps` 和 `bps` 分别是深度优先搜索和广度优先搜索算法的实现。这两种算法都需要用到一个布尔数组 `visited` 来标记顶点是否被访问过。
 * `dijkstra` 是 Dijkstra 最短路径算法的实现。我们用一个 `dist` 数组来记录源顶点到每个顶点的最短路径长度，用一个优先队列 `pq` 来存储待处理的顶点，按照距离从小到大排序。
 * `prim` 是 Prim 最小生成树算法的实现。我们用一个布尔数组 `visited` 来标记顶点是否被加入生成树中，用一个 `key` 数组来记录源顶点到每个顶点的距离，用一个 `parent` 数组来记录每个顶点的父节点，用一个优先队列 `pq` 来存储待处理的顶点，按照距离从小到大排序。
 */
public class UndirectedGraphByAdjacencyList {
    private int V; // 图的顶点数
    private LinkedList<Integer>[] adj; // 邻接表表示图

    // 构造函数，初始化图的顶点数和邻接表
    public UndirectedGraphByAdjacencyList(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    // 添加无向边
    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    // 深度优先搜索
    public void dps(int v) {
        boolean[] visited = new boolean[V];
        dfs(v, visited);
    }

    private void dfs(int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(v + " ");
        for (int w : adj[v]) {
            if (!visited[w]) {
                dfs(w, visited);
            }
        }
    }

    // 广度优先搜索
    public void bps(int v) {
        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();
        visited[v] = true;
        queue.offer(v);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            System.out.print(cur + " ");
            for (int w : adj[cur]) {
                if (!visited[w]) {
                    visited[w] = true;
                    queue.offer(w);
                }
            }
        }
    }

    // Dijkstra 最短路径算法
    public void dijkstra(int src) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>(V, new Node());
        pq.offer(new Node(src, 0));
        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int u = cur.vertex;
            for (int v : adj[u]) {
                int weight = 1; // 假设每条边权重为 1
                if (dist[v] > dist[u] + weight) {
                    dist[v] = dist[u] + weight;
                    pq.offer(new Node(v, dist[v]));
                }
            }
        }
        System.out.println(Arrays.toString(dist));
    }

    private class Node implements Comparator<Node> {
        int vertex;
        int weight;

        public Node() {
        }

        public Node(int v, int w) {
            vertex = v;
            weight = w;
        }

        @Override
        public int compare(Node n1, Node n2) {
            return n1.weight - n2.weight;
        }
    }

    // Prim 最小生成树算法
    public void prim() {
        boolean[] visited = new boolean[V];
        int[] key = new int[V];
        int[] parent = new int[V];
        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        key[0] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>(V, new Node());
        pq.offer(new Node(0, 0));
        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int u = cur.vertex;
            visited[u] = true;
            for (int v : adj[u]) {
                int weight = 1; //
                if (!visited[v] && key[v] > weight) {
                    key[v] = weight;
                    parent[v] = u;
                    pq.offer(new Node(v, key[v]));
                }
            }
        }
        for (int i = 1; i < V; ++i) {
            System.out.println(parent[i] + " - " + i);
        }
    }


    public static void main(String[] args) {
        UndirectedGraphByAdjacencyMatrix g = new UndirectedGraphByAdjacencyMatrix(5);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        System.out.println("深度优先搜索结果：");
        g.dps(0);
        System.out.println();
        System.out.println("广度优先搜索结果：");
        g.bps(0);
        System.out.println();
        System.out.println("Dijkstra 最短路径算法结果：");
        g.dijkstra(0);
        System.out.println("Prim 最小生成树算法结果：");
        g.prim();
    }

}