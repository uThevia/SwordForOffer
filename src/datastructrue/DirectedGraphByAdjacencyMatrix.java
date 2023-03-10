package datastructrue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Stack;

/**
 * 有向图
 * 邻接矩阵法
 * 包括拓扑排序, Dijkstra, Bellman-Ford最短路径算法
 */
public class DirectedGraphByAdjacencyMatrix {
    private int V;
    private int[][] adjMatrix;

    public DirectedGraphByAdjacencyMatrix(int V) {
        this.V = V;
        adjMatrix = new int[V][V];
    }

    public void addEdge(int v, int w) {
        adjMatrix[v][w] = 1;
    }

    public void dps(int v) {
        boolean[] visited = new boolean[V];
        dfsUtil(v, visited);
    }

    private void dfsUtil(int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(v + " ");
        for (int i = 0; i < V; ++i) {
            if (adjMatrix[v][i] == 1 && !visited[i]) {
                dfsUtil(i, visited);
            }
        }
    }

    public void bps(int start) {
        boolean[] visited = new boolean[V];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        visited[start] = true;
        queue.add(start);
        while (queue.size() != 0) {
            int v = queue.poll();
            System.out.print(v + " ");
            for (int i = 0; i < V; ++i) {
                if (adjMatrix[v][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                }
            }
        }
    }

    public void topologicalSort() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[V];
        for (int i = 0; i < V; ++i) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }
        System.out.print("Topological sorting: ");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
        System.out.println();
    }

    private void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;
        for (int i = 0; i < V; ++i) {
            if (adjMatrix[v][i] == 1 && !visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }
        stack.push(v);
    }

    public void dijkstra(int start) {
        int[] dist = new int[V];
        boolean[] visited = new boolean[V];
        PriorityQueue<Node> pq = new PriorityQueue<Node>(V, new Node());
        for (int i = 0; i < V; ++i) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[start] = 0;
        pq.add(new Node(start, 0));
        while (!pq.isEmpty()) {
            int u = pq.remove().id;
            visited[u] = true;
            for (int i = 0; i < V; ++i) {
                if (adjMatrix[u][i] == 1 && !visited[i]) {
                    int weight = 1; // 假设每条边的权值都为1
                    int newDist = dist[u] + weight;
                    if (newDist < dist[i]) {
                        dist[i] = newDist;
                        pq.add(new Node(i, dist[i]));
                    }
                }
            }
        }
        System.out.println("Dijkstra shortest path algorithm:");
        System.out.println("Vertex Distance from Source");
        for (int i = 0; i < V; ++i) {
            System.out.println(i + "\t" + dist[i]);
        }
    }

    public void prim() {
        int[] key = new int[V];
        int[] parent = new int[V];
        boolean[] visited = new boolean[V];
        for (int i = 0; i < V; ++i) {
            key[i] = Integer.MAX_VALUE;
        }
        key[0] = 0;
        parent[0] = -1;
        for (int i = 0; i < V - 1; ++i) {
            int u = minKey(key, visited);
            visited[u] = true;
            for (int j = 0; j < V; ++j) {
                if (adjMatrix[u][j] != 0 && !visited[j] && adjMatrix[u][j] < key[j]) {
                    parent[j] = u;
                    key[j] = adjMatrix[u][j];
                }
            }
        }
        System.out.println("Minimum spanning tree (Prim's algorithm):");
        System.out.println("Edge \tWeight");
        for (int i = 1; i < V; ++i) {
            System.out.println(parent[i] + " - " + i + "\t" + adjMatrix[i][parent[i]]);
        }
    }

    private int minKey(int[] key, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < V; ++i) {
            if (!visited[i] && key[i] < min) {
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
        DirectedGraphByAdjacencyMatrix g = new DirectedGraphByAdjacencyMatrix(5);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        g.addEdge(3, 4);

        System.out.print("DFS traversal: ");
        g.dps(0);
        System.out.println();

        System.out.print("BFS traversal: ");
        g.bps(0);
        System.out.println();

        g.topologicalSort();

        g.dijkstra(0);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 4);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 4);

        g.prim();
    }
}
