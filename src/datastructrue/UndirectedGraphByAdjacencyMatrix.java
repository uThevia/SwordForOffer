package datastructrue;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
/**
 *  无向图
 *  邻接矩阵法
 *
 */
public class UndirectedGraphByAdjacencyMatrix {
    private int V;
    private int[][] adjMatrix;

    public UndirectedGraphByAdjacencyMatrix(int V) {
        this.V = V;
        adjMatrix = new int[V][V];
        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                adjMatrix[i][j] = 0;
            }
        }
    }

    public void addEdge(int u, int v) {
        adjMatrix[u][v] = 1;
        adjMatrix[v][u] = 1;
    }

    public void dps(int v) {
        boolean[] visited = new boolean[V];
        dfs(v, visited);
    }

    private void dfs(int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(v + " ");
        for (int i = 0; i < V; ++i) {
            if (adjMatrix[v][i] == 1 && !visited[i]) {
                dfs(i, visited);
            }
        }
    }

    public void bps(int v) {
        boolean[] visited = new boolean[V];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        visited[v] = true;
        queue.add(v);
        while (!queue.isEmpty()) {
            v = queue.poll();
            System.out.print(v + " ");
            for (int i = 0; i < V; ++i) {
                if (adjMatrix[v][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                }
            }
        }
    }

    public void dijkstra(int src) {
        int[] dist = new int[V];
        boolean[] visited = new boolean[V];
        PriorityQueue<Node> pq = new PriorityQueue<Node>(V, new Node());
        for (int i = 0; i < V; ++i) {
            dist[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }
        dist[src] = 0;
        pq.offer(new Node(src, 0));
        while (!pq.isEmpty()) {
            int u = pq.poll().id;
            visited[u] = true;
            for (int v = 0; v < V; ++v) {
                if (adjMatrix[u][v] == 1 && !visited[v]) {
                    int alt = dist[u] + 1;
                    if (alt < dist[v]) {
                        dist[v] = alt;
                        pq.offer(new Node(v, dist[v]));
                    }
                }
            }
        }
        for (int i = 0; i < V; ++i) {
            System.out.print(dist[i] + " ");
        }
        System.out.println();
    }

    public void prim() {
        int[] parent = new int[V];
        int[] key = new int[V];
        boolean[] mstSet = new boolean[V];
        for (int i = 0; i < V; ++i) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
        key[0] = 0;
        parent[0] = -1;
        for (int count = 0; count < V - 1; ++count) {
            int u = minKey(key, mstSet);
            mstSet[u] = true;
            for (int v = 0; v < V; ++v) {
                if (adjMatrix[u][v] == 1 && !mstSet[v] && key[v] > adjMatrix[u][v]) {
                    parent[v] = u;
                    key[v] = adjMatrix[u][v];
                }
            }
        }
        for (int i = 1; i < V; ++i) {
            System.out.println(parent[i] + " - " + i + " " + key[i]);
        }
    }

    private int minKey(int[] key, boolean[] mstSet) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < V; ++i) {
            if (!mstSet[i] && key[i] < min) {
                min = key[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private class Node implements Comparator<Node> {
        public int id;
        public int cost;

        public Node() {
        }

        public Node(int id, int cost) {
            this.id = id;
            this.cost = cost;
        }

        @Override
        public int compare(Node node1, Node node2) {
            if (node1.cost < node2.cost) {
                return -1;
            }
            if (node1.cost > node2.cost) {
                return 1;
            }
            return 0;
        }
    }

    public static void main(String[] args) {
        UndirectedGraphByAdjacencyMatrix g = new UndirectedGraphByAdjacencyMatrix(5);
        g.addEdge(0, 1);
        g.addEdge(0, 4);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(3, 4);

        System.out.println("Depth-first search:");
        g.dps(0);
        System.out.println();

        System.out.println("Breadth-first search:");
        g.bps(0);
        System.out.println();

        System.out.println("Dijkstra's shortest path algorithm:");
        g.dijkstra(0);

        System.out.println("Prim's minimum spanning tree algorithm:");
        g.prim();
    }
}
