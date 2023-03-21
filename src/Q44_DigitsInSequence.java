/**
 * 数字序列中某一位的数字, 位数从0开始
 * 数字序列: 拼接自然数, 0123456789101112131415…
 */
public class Q44_DigitsInSequence {

    /**
     * 公式法	O(logn)
     * k位数字的位数和
     * $$
     * d(k)=
     * \begin{cases}
     * 10=9*10^0 + 1	\quad &,k=1
     * \\ 9*10^{k-1}	\quad &,k>1
     * \end{cases}
     * $$
     * 1到k位所有数字的位数和S(k),
     * $$
     * S(k) = 10 + 90*2 + ... + 9*10^{k-1}*k
     * 	= (k - \frac{1}{9}) * 10^k + \frac{10}{9}
     * $$
     * 求出n的位数k
     * $$
     * S(k-1) < n \le S(k)
     * $$
     * 求出n是k位数的第几个数并根据剩余位数判断是哪个数字
     * $$
     * t = n \% S(k-1), \quad \text{t是n是在k位数的剩余值}
     * \\ t = k * a + b, b \in {1:k}, \quad \text{a是k位数的第几个数, b是该数的剩余位数}
     * \\ \quad a = t / k
     * \\ \quad b = t \% k
     * \\ \quad \text{if(b == 0){a -= 1; b = k}}
     * $$
     */
    public static int formula(int n) {
        if (n < 0) {
            return -1;
        }

        int res;    // 数位
        // 初始化变量: k = 1
        int digit = 1;         // k
        long ksub1P10 = 1;   // 10^(k-1), start k位数的起始值
        long dk = 9;        // d(k)
        while (n > dk) {    // 直到 n <= dk
            n -= dk;
            ++digit;
            ksub1P10 *= 10;
            dk = 9 * ksub1P10 * digit;   // d(k) = 9 * 10^(k-1) * k
        }
        // 此时 t == n

        /* 序数和余数的2种方法: 从1或从0开始
        // 法1 修改余数取1:k, 修正余0: 序数是前个数, 数位是最后位
        int ordinal = n / digit;    // a 序数 在k位数中的第几个数, 从1开始
        int digitIndex = n % digit; // b 数位 在该数的第几位, 从1开始
        if (digitIndex == 0) {
            --ordinal;
            digitIndex = digit;
        }
        long num = ksub1P10 + ordinal;  // 数
        res = (int) (num / Math.pow(10, digit - digitIndex) % 10);
        */


        // 法2: 将序数和数位u从0开始: n - 1 修正输入序数
        int ordinal = (n - 1) / digit;    // a 序数 在k位数中的第几个数, 从0开始
        int digitIndex = (n - 1) % digit; // b 数位 在该数的第几位, 从0开始
        long num = ksub1P10 + ordinal;  // 数
        res = (int) (num / Math.pow(10, digit - digitIndex - 1) % 10);

        /*
        求大数第b位的3种方法:
        1. 先删高位再删低位:
        res = (int) (num % Math.pow(10, digitIndex) / Math.pow(10, digitIndex - 1));
        2. 先删低位再删高位: 效率最低, 额外算一次幂
        res = (int) (num / Math.pow(10, digit - digitIndex) % 10);
        3. 大数转String索引取位:
        res = Long.toString(num).charAt(digitIndex - 1) - '0';
         */
        return res;
    }


    public static void main(String[] args) {
        int n = -1; // -1
        System.out.println("n = " + n + ": " + formula(n));
        n = 0;      // 0
        System.out.println("n = " + n + ": " + formula(n));
        n = 2;      // 2
        System.out.println("n = " + n + ": " + formula(n));
        n = 15;     // 2
        System.out.println("n = " + n + ": " + formula(n));
        n = 2890;   // 1
        System.out.println("n = " + n + ": " + formula(n));
    }
}
