/**
 * 地上有一个m行n列的方格。一个机器人从坐标原点(0, 0)的格子开始移动，每次可向上下左右移动一格，但不能进入行坐标和列坐标的数位之和大于k的格子。
 * 请问该机器人能够到达多少个格子？
 */
public class Q13_RobotMovingRange {

    /**
     * 回溯法
     */
    public static int backtracking(int m, int n, int k, int start_x, int start_y) {
        // 检查输入
        if (m <= 0 || n <= 0 || k <= 0) {
            return 0;
        }

        boolean[][] visited = new boolean[m][n];

        return stepByRecursion(m, n, k, start_x, start_y, visited);
        /*
        int[] counts = new int[1];  // 用int[]保存int以作为方法输入参数
        stepByRecursion(m, n, k, start_x, start_y, visited, counts);
        return counts[0];
        */
    }

    /**
     * 回溯法 递归每一步
     * 深度优先 树中结点都是合法结点
     * 结点值count是以当前结点为根结点的树的结点数
     * 叶结点是无合法相邻格子
     * @return 当前点(x,y)由下至上返回的格子数
     */
    private static int stepByRecursion(int m, int n, int k, int x, int y, boolean[][] visited) {
        int count = 0;  // 从(x,y)出发的相邻合法格子数
        boolean isNow = isLegal(m, n, k, x, y, visited);

        if (!isNow) {
            return count;
        }

        visited[x][y] = true;

        // 计算从当前点出发的相邻合法格子数
        count += 1 // 当前点
                + stepByRecursion(m, n, k, x - 1, y, visited)   // 上
                + stepByRecursion(m, n, k, x + 1, y, visited)   // 下
                + stepByRecursion(m, n, k, x, y - 1, visited)   // 左
                + stepByRecursion(m, n, k, x, y + 1, visited)   // 右
                ;

        return count;
    }
    /** 同上
     * 回溯法 递归每一步
     * 记录回溯树的结点数
     * 但在方法外用参数保存结果 由于值传递形参不能改变实参 使用int[1]保存int结果
     * */
    private static void stepByRecursion(int m, int n, int k, int x, int y, boolean[][] visited, int[] counts) {
        boolean isNow = isLegal(m, n, k, x, y, visited);

        if (!isNow) {
            return;
        }

        visited[x][y] = true;
        counts[0] += 1; // 当前点

        // 依次访问相邻点
        stepByRecursion(m, n, k, x - 1, y, visited, counts);    // 上
        stepByRecursion(m, n, k, x + 1, y, visited, counts);    // 下
        stepByRecursion(m, n, k, x, y - 1, visited, counts);    // 左
        stepByRecursion(m, n, k, x, y + 1, visited, counts);    // 右

        return;
    }

    /**
     * 是否合法 (x,y)
     * 不越界, 未访问过, k合法
     */
    private static boolean isLegal(int m, int n, int k, int x, int y, boolean[][] visited) {
        return isBoundaryLegal(m, n, x, y) && !visited[x][y] &&  isKLegal(k, x, y);
    }
    /**
     * 是否边界合法 (x,y)
     */
    private static boolean isBoundaryLegal(int m, int n, int x, int y) {
        return x >=0 && x < m && y >= 0 && y < n;
    }

    /**
     * 是否k合法 (x,y)
     */
    private static boolean isKLegal(int k, int x, int y) {
        return getDigitlNumber(x) + getDigitlNumber(y) <= k;
    }

    private static int getDigitlNumber(int num) {
        int sum = 0;
        while (num != 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }


    public static void main(String[] args) {
        System.out.println(backtracking(4,3,2, 0, 0));
    }

}
