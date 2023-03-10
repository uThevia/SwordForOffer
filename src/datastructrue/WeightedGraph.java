package datastructrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.*;

/**
 * 在这个示例程序中，我们定义了一个 `WeightedGraph` 类，使用了 `HashMap` 和 `HashSet` 实现带权图。我们实现了以下方法：
 *
 * - `addEdge`：添加带权边。
 * - `hasEdge`：判断是否有从一个节点到另一个节点的边。
 * - `getAdjacentEdges`：获取从指定节点出发的所有边。
 * - `dfs`：实现深度优先搜索算法。
 * - `bfs`：实现广度优先搜索算法。
 * - `dijkstra`：实现 Dijkstra 最短路径算法。
 * - `prim`：实现 Prim 最小生成树算法。
 *
 * 在 `main` 方法中，我们创建了一个带权图，并进行了深度优先搜索、广度优先搜索、Dijkstra 最短路径和 Prim 最小生成树的演示。
 */
public class WeightedGraph {
    private Map<Integer, Map<Integer, Double>> graph; // 存储带权图的数据结构

    public WeightedGraph() {
        graph = new HashMap<Integer, Map<Integer, Double>>();
    }

    // 添加带权边
    public void addEdge(int from, int to, double weight) {
        if (!graph.containsKey(from)) {
            graph.put(from, new HashMap<Integer, Double>());
        }
        graph.get(from).put(to, weight);
    }

    // 判断是否有从 from 到 to 的边
    public boolean hasEdge(int from, int to) {
        return graph.containsKey(from) && graph.get(from).containsKey(to);
    }

    // 获取从指定节点出发的所有边
    public Map<Integer, Double> getAdjacentEdges(int from) {
        if (!graph.containsKey(from)) {
            return new HashMap<Integer, Double>();  // 不存在节点返回空集合而不是null 后续不需要处理空指针问题
        }
        return graph.get(from);
    }

    // 深度优先搜索
    public void dfs(int start, Set<Integer> visited) {
        visited.add(start);
        System.out.print(start + " ");

        Map<Integer, Double> adjacentEdges = getAdjacentEdges(start);
        for (int neighbor : adjacentEdges.keySet()) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited);
            }
        }

    }

    // 广度优先搜索
    public void bfs(int start) {
        Queue<Integer> queue = new LinkedList<Integer>();
        Set<Integer> visited = new HashSet<Integer>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            System.out.print(current + " ");

            Map<Integer, Double> adjacentEdges = getAdjacentEdges(current);

            for (int neighbor : adjacentEdges.keySet()) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }

        }
    }


    public Map<Integer, Double> dijkstra(int start) {
        PriorityQueue<double[]> queue = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));
        Map<Integer, Double> distances = new HashMap<>();

        queue.offer(new double[]{start, 0.0});
        while (!queue.isEmpty()) {
            double[] current = queue.poll();
            int node = (int) current[0];
            double distance = current[1];

            if (distances.containsKey(node)) {
                continue;
            }
            distances.put(node, distance);

            Map<Integer, Double> adjacentEdges = getAdjacentEdges(node);
            for (int neighbor : adjacentEdges.keySet()) {
                if (!distances.containsKey(neighbor)) {
                    queue.offer(new double[]{neighbor, distance + adjacentEdges.get(neighbor)});
                }
            }

        }

        return distances;
    }

    // Prim 最小生成树算法
    public Set<Integer> prim(int start) {
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<double[]> queue = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));

        queue.offer(new double[]{start, 0.0});
        while (!queue.isEmpty()) {
            double[] current = queue.poll();
            int node = (int) current[0];
            double weight = current[1];

            if (visited.contains(node)) {
                continue;
            }
            visited.add(node);

            for (int neighbor : getAdjacentEdges(node).keySet()) {
                if (!visited.contains(neighbor)) {
                    queue.offer(new double[]{neighbor, graph.get(node).get(neighbor)});
                }
            }
        }
        return visited;
    }


    public static void main(String[] args) {
        WeightedGraph graph = new WeightedGraph();

        graph.addEdge(1, 2, 1.0);
        graph.addEdge(1, 3, 4.0);
        graph.addEdge(2, 3, 2.0);
        graph.addEdge(2, 4, 5.0);
        graph.addEdge(3, 4, 1.0);

        System.out.println("深度优先搜索结果：");
        Set<Integer> visited = new HashSet<>();
        graph.dfs(1, visited);
        System.out.println();

        System.out.println("广度优先搜索结果：");
        graph.bfs(1);
        System.out.println();

        System.out.println("Dijkstra 最短路径结果：");
        Map<Integer, Double> distances = graph.dijkstra(1);
        for (int node : distances.keySet()) {
            System.out.println("从节点 1 到节点 " + node + " 的最短距离为：" + distances.get(node));
        }

        System.out.println("Prim 最小生成树结果：");
        Set<Integer> mst = graph.prim(1);
        System.out.println("最小生成树的节点集合为：" + mst);
    }
}
