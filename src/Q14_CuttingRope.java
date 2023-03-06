/**
 * 剪绳子
 * 一根长度为整数n的绳子, 剪成整数长度若干段,求这些绳子长度的连乘积最大值
 * 与原题不同 可以不剪绳子
 */
public class Q14_CuttingRope {
    public static int formula(int n){
        int result = 1; // 乘积结果初始化为1
        if (n < 0)  // 不合法输入
        {
            result = 0;
        } else if (n <= 3)    // 基例
        {
            result =  n;
        } else {
            int m = n / 3;
            switch (n % 3){
                case 0: result = (int) Math.pow(3, m);
                    break;
                case 1: result = (int) Math.pow(3, m - 1) * 4;
                    break;
                case 2: result = (int) Math.pow(3, m) * 2;
                    break;
            }
        }
        return result;
    }

    public static int greedy(int n){
        int result = 1; // 乘积结果初始化为1
        if (n < 0)  // 不合法输入
        {
            result = 0;
        } else if (n <= 4)    // 基例
        {
            result =  n;
        } else {
            final int BEST_CUT_LENGTH = 3;
            while (n > 4) {
                result *= BEST_CUT_LENGTH;
                n -= BEST_CUT_LENGTH;
            }
            result *= n; // 乘上基例
        }
        return result;
    }

    public static int dynamic(int n){
        int result = 1; // 乘积结果初始化为1
        int BASE_MAX = 4;   // 基例最大值

        if (n < 0)  // 不合法输入
        {
            result = 0;
        } else if (n <= BASE_MAX)    // 基例
        {
            result =  n;
        } else {
            // final int BEST_CUT_LENGTH = 3;
            int[] dp = new int[n + 1];  // 要表示dp[0:n]需要n+1
            for (int i = 1; i <= BASE_MAX; i++) {
                dp[i] = i;
            }
            for (int i = BASE_MAX + 1; i <= n; i++){
                int dptemp = 0;   // 暂存dp[i]循环j=2:i/2的最大结果
                for (int j = 2; j <= i / 2; j++) {
                    /*
                    int maxtemp = Math.max(i, dp[j] * dp[i-j]);
                    if (dptemp < maxtemp)
                        dptemp = maxtemp;
                     */
                    dptemp = Math.max(dptemp, Math.max(i, dp[j] * dp[i-j]));
                }
                dp[i] = dptemp;
                /*
                // 优化动态规划
                dp[i] *= BEST_CUT_LENGTH * dp[i - BEST_CUT_LENGTH];
                 */
            }
            result = dp[n];
        }
        return result;
    }

    public static void main(String[] args) {
        int n = 6;
        System.out.println(formula(n));
        System.out.println(greedy(n));
        System.out.println(dynamic(n));
    }
}
