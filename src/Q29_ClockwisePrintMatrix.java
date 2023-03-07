import java.util.ArrayList;
import java.util.List;

/**
 * 顺时针打印矩阵
 */
public class Q29_ClockwisePrintMatrix {
    /**
     * 递归法
     */
    public static<T> List<T> recursion(T[][] matrix) {
        List<T> result = new ArrayList<>();

        int m, n;
        if (matrix == null || (m = matrix.length) == 0 || (n = matrix[0].length) == 0) {
            return result;
        }

        recursionStep(result, matrix, m, n , 0);
        return result;
    }

    /**
     * 递归遍历每层外壳: 一层壳由2个点唯一确定 起始点(start,start)和右下点(endX, endY)
     * @param result
     * @param matrix
     * @param m 矩阵行数
     * @param n 矩阵列数
     * @param start 壳的起始点(start,start)
     * @param <T>
     */
    private static<T> void recursionStep(List<T> result, T[][] matrix, int m, int n, int start) {
        // 壳的右下点 (endX, endY)
        int endX = m - 1 - start;
        int endY = n - 1 - start;

        // 基例 壳为
        // 0行或0列
        if (start > endX || start > endY) {     // 等效 start - 1 == endX || start - 1 == endY
            return;
        }
        // 1行或1列
        if (start == endX) {   // 1行: 包含1行1列
            for (int j = start; j <= endY; j++) {
                result.add(matrix[start][j]);
            }
            return;
        } else if (start == endY) {    // 1列
            // 1列
            for (int i = start; i <= endX; i++) {
                result.add(matrix[i][start]);
            }
            return;
        }

        // 外层壳(环)依次为 首行,尾列,尾行(逆),首列(逆); 因为重复中间跳首最后跳首位
            // 2行或2列包含于其中
        // 首行
        for (int j = start; j <= endY; j++) {
            result.add(matrix[start][j]);
        }
        // 尾列
        for (int i = start + 1; i <= endX; i++) {
            result.add(matrix[i][endY]);
        }
        // 尾行
        for (int j = endY - 1; j >= start; j--) {
            result.add(matrix[endX][j]);
        }
        // 首列
        for (int i = endX - 1; i >= start + 1; i--) {
            result.add(matrix[i][start]);
        }

        recursionStep(result, matrix, m, n, start + 1);     // 递归里壳
    }


    /**
     * 循环法
     * 模拟遍历路径
     */
    public int[] spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return new int[0];
        }

        int m = matrix.length, n = matrix[0].length;

        boolean[][] visited = new boolean[m][n];   // 访问标志
        int total = m * n;  // 元素数
        int[] result = new int[total];   // 结果

        int row = 0, column = 0;    // 当前坐标
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};    // 方向数组 = {右, 下, 左, 上}, 每个方向={行距离, 列距离}
        int directionIndex = 0; // 当前方向 方向数组的下标
        int nextRow = 0, nextColumn = 0;    // 尝试的新坐标

        for (int i = 0; i < total; i++) {
            // 访问
            result[i] = matrix[row][column];
            visited[row][column] = true;
            // 获取尝试的新坐标
            nextRow = row + directions[directionIndex][0];
            nextColumn = column + directions[directionIndex][1];
            if (nextRow < 0 || nextRow >= m || nextColumn < 0 || nextColumn >= n || visited[nextRow][nextColumn]) { // 新坐标不合法: 越界或已访问
                directionIndex = (directionIndex + 1) % 4;   // 更换方向: 下一个方向
            }
            // 获取正确的新坐标
            row += directions[directionIndex][0];
            column += directions[directionIndex][1];
        }
        return result;
    }


    public static void main(String[] args) {
        int m = 6;
        int n = 4;
        Integer[][] matrix = new Integer[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = i * n + j;
            }
        }
        // System.out.println(Arrays.deepToString(matrix));
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%2d", matrix[i][j]);
                System.out.print(", ");
            }
            System.out.println();
        }

        System.out.println(recursion(matrix));
    }
}
