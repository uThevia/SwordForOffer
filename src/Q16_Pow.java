/**
 * 数值的整数次方
 * 实现pow(x, n)即$x^n$
 * 不考虑大数情况
 */
public class Q16_Pow {

    /**
     * 快速幂
     * x^a  = (x^2)^(a/2)
     * 二分法迭代求幂
     */
    /** 循环体 */
    public static double quickPower(double x, int n){	// pow∈ N+
        // 单独处理 x = 0
        if (isZero(x)){
            if (0 == n) {
                System.out.println("quickPower:: 输入不合法x=n=0");
                return 0;
            }else {
                return 0;
            }
        }

        double rem = 1.0;
        boolean isNegetive = false; // 负数幂标志
        if (n < 0) {    // 负数幂
            n *= -1;    // 转化为正数
            isNegetive = true;
        }
        while (0 != n) {	// 在a=1时已不用求幂但入循环能通过odd的if分支将x传递给rem
            if (1 == n % 2)	// odd
            {
                rem = rem * x;
            }
            x = (x * x);
            n /= 2;
        }
        if (isNegetive)     // 负数
        {
            rem = 1.0 / rem;
        }
        return rem;
    }
    /** 二进制写法 */
    public static double quickPowerByBit(double x, int n){	// a∈ N+
        // 单独处理 x = 0
        if (isZero(x)){
            if (0 == n) {
                System.out.println("quickPower:: 输入不合法x=n=0");
                return 0;
            }else {
                return 0;
            }
        }
        double rem = 1;
        boolean isNegetive = false; // 负数幂标志
        if (n < 0) {    // 负数幂
            n *= -1;    // 转化为正数
            isNegetive = true;
        }
        // 幂看成二进制处理
        while (0 != n) {	// a > 0; 直到处理完二进制长度为止
            if (1 == (n & 1))	//  幂的二进制右数第b位(当前最低位)为1
            {
                rem = rem * x;	// 将x^2^b 计入
            }
            x *= x;	// x^2^(b+1)
            n >>= 1;			// 舍弃二进制右数第b位(当前最低位)
        }
        if (isNegetive)     // 负数
        {
            rem = 1.0 / rem;
        }
        return rem;
    }

    /** 递归体 */
    public static double quickPowerByRecursion(double x, int n) {
        // 单独处理 x = 0
        if (isZero(x)){
            if (0 == n) {
                System.out.println("quickPower:: 输入不合法x=a=0");
                return 0;
            }else {
                return 0;
            }
        }

        long N = n;
        return N >= 0 ? quickMul(x, N) : 1.0 / quickMul(x, -N);
    }
    /** 递归体的每一步 */
    public static double quickMul(double x, long N) {
        if (N == 0) {
            return 1.0;
        }
        double y = quickMul(x, N / 2);
        return N % 2 == 0 ? y * y : y * y * x;
    }

    /**
     * double判零
     * 整形可以直接判零, 浮点型不行 运算会出现误差
     */
    public static boolean isZero(double x){
        return Math.abs(x) < 2 * Double.MIN_VALUE;
    }


    public static void main(String[] args) {
        double x = 0;
        int[] as = {0, 3,-3};
        for (int a:as){
            System.out.println(x + "^" + a +" = ");
            System.out.println(quickPower(x, a));
            System.out.println(quickPowerByBit(x, a));
            System.out.println(quickPowerByRecursion(x, a));
            System.out.println();
        }

        double x2 = 2;
        for (int a: as){
            System.out.println(x2 + "^" + a +" = ");
            System.out.println(quickPower(x2, a));
            System.out.println(quickPowerByBit(x2, a));
            System.out.println(quickPowerByRecursion(x2, a));
            System.out.println();
        }

    }

}
