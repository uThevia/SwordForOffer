/**
 * 求整数1～n的十进制中1出现的次数
 */
public class Q43_NumberOfl {
    /**
     * 每位考虑 每位1出现次数加总即为目标
     * 从低位到高位记每位为第k位(从0开始),
     */
    /**
     * 公式法
     * 第k位出现1个次数为
     * $$
     * \lfloor\frac{n}{10^{k+1}}\rfloor*10^k + \min{(\max{(n\mod 10^{k+1}-10^k+1, 0)}), 10^k}
     * $$
     *      min中的$10^k$是对应cur=2:9
     *      $n\mod 10^{k+1}-10^k+1$对应cur=1
     *      max中的0是对应cur=0
     */
    public int formula(int n) {
        if (n < 0) {
            return -1;
        }
        // kth power of 10, 10^k, 初始k=0
        long kP10 = 1;     // long是因为会越界int
        int res = 0;
        while (kP10 <= n) {
            res += (n / (kP10 * 10)) * kP10 + Math.min(Math.max(n % (kP10 * 10) - kP10 + 1, 0), kP10);
            kP10 *= 10;
        }
        return res;
    }

    /**
     * 条件法
     * 记第k位cur, 高位high, 低位low, $10^k$为d
     * 第k位出现1个次数为
     * $$
     * \begin{cases}
     * high*d			\quad&, cur = 0
     * \\ high*d + low+1	\quad&, cur = 1
     * \\ (high+1)*d		\quad&, cur = 2:9
     * \end{cases}
     * $$
     */
    public int cases(int n) {
        if (n < 0) {
            return -1;
        }

        int res = 0;

        int digit = 1;
        int high = n / 10;
        int cur = n % 10;
        int low = 0;

        while(high != 0 || cur != 0) {  // 直到 high,cur = 0
            if(cur == 0) {
                res += high * digit;
            } else if(cur == 1) {
                res += high * digit + low + 1;
            } else {
                res += (high + 1) * digit;
            }
            // 更新
            low += cur * digit;
            cur = high % 10;
            high /= 10;
            digit *= 10;
        }
        return res;
    }
}
