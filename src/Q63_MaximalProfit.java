import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * 股票的最大利润
 * 数组中保存股票时序价格，求出买卖一次的最大利润
 */
public class Q63_MaximalProfit {
    /**
     * 简单	O(n)
     */
    public static int simple(int[] prices) {
        int res = 0;
        if (prices.length == 0) {
            return res;
        }
        int sell = prices[0];      // 卖出价格
        int buy = prices[0];      // 买入价格
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < buy) {        // 此时价格更便宜 买入
                buy = prices[i];
                sell = buy;                  // 卖出必须在买入之后
            } else if (prices[i] > sell) { // 此时价格更高 卖出
                sell = prices[i];
                if (sell - buy > res) {      // 更新最大利润
                    res = sell - buy;
                }
            }
        }
        return res;
    }
    
    /**
     * 双指针	O(n)
     * 是对简单的简化
     */
    public static int twoPointers(int[] prices) {
        int res = 0;
        int i = 0;	// 最小值指针
        int j = 1;	// 极大值指针
        while(j < prices.length) {
            if (prices[i] > prices[j]) {
                i = j;
            }else {
                res = max(res, prices[j] - prices[i]);
            }
            j += 1;
        }
        return res;
    }
    
    /**
     * 动态规划	O(n)
     * 令f(n)为问题规模为n: 前n天的最大利润, n从0开始
     * 基例f(0) = 0, 返回f[n-1]
     * f(n)比f(n-1)多的是 第n天卖出 前n-1天最低价买入
     * f(n) = \max{(f(n-1), A[n] - \min{(A[0:n-1])})}
     */
    public static int dp(int[] prices) {
        // 基例 f(0)=0
        int res = 0;
        int minValue = prices[0];
        for (int i = 1; i < prices.length; i++) {
            res = max(res, prices[i] - minValue);
            minValue = min(minValue, prices[i]);
        }
        return res;
    }
    
    /**
     * 贪心	O(n)
     * 计算持有的持续利润, 为负就置0重新开始.
     * 比较所有最大收益持有期的利润即为结果
     */
    public static int greedy(int[] prices) {
        int res = 0;
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            profit = prices[i] - prices[i - 1] + profit;
            if (profit < 0) {
                profit = 0;
            } else {
                res = max(res, profit);
            }
        }
        return res;
    }
    
}
