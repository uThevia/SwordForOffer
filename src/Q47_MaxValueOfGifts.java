import static java.lang.Math.max;
/**
 * 礼物的最大价值
 * 在一个m*n棋盘的每格都放有一个礼物，每个礼物都有一定的价值(>0)。
 * 从棋盘的左上角开始，每次向右或向下移动一格、直到到达棋盘右下角。
 * 请计算最多能拿到多少价值的礼物？
 */
public class Q47_MaxValueOfGifts {
    /**
     * M 动态规划	O(mn),O(min(m,n))
     * f(m,n)=a(m,n) + max(f(m-1,n),f(n-1))
     * f(0,0)=a(0,0)
     *
     * 优化空间
     * 表用二维数组存储	,O(mn)
     * 表用一维数组存储	,O(n)
     * f(m,n)只与f(m-1,n),f(n-1)有关
     * 一维数组只需存储外层循环
     *      当循环顺序是先行后列时, 不用存储第i-2行及以上 只需存储第i-1行的值,
     *      遍历到(i,j)时  下标1:j-1存储当前行(第i行)的值 下标j:n存储前一行(第i-1行)的值
     */
    public static int dp(int[][] nums) {
        int m, n;
        if (nums == null || (m = nums.length) == 0 || (n = nums[0].length) == 0) {
            return 0;
        }
        int[] dp = new int[n];
        // 循环遍历 先行后列
        for (int i = 0; i < m; i++) {
            // 单独处理第0列
            dp[0] = nums[i][0] + dp[0];
            for (int j = 1; j < n; j++) {
                dp[j] = nums[i][j] + max(dp[j-1], dp[j]);
            }
        }
        return dp[n-1];
    }

    public static void main(String[] args) {
        int[][] nums = {
                {1, 2, 3}
                , {4, 5, 6}
                , {7, 8, 9}
        };
        System.out.println(dp(nums));   // 29
    }
}
