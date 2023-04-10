/**
 * 约瑟夫环问题
 * 将[0:n-1]排成一个圆圈, 从数字0开始删除第m个数(删除后从下个数字开始计数),
 * 求出最后剩下的数字
 */
public class Q62_LastNumberlnCircle {
    /**
     * 只关注最终数字的序号变化
     *
     * 删除A[m-1]并从索引m处开始计数 相当于数组左旋m(左移m并将[0:m)附加到右端)
     * 令f(n)为剩下n人时胜利者的下标
     *      f(n-1) = (f(n) - m) \mod n
     * 逆推
     * 从只剩下胜利者开始逆推
     * 	胜利者只剩一人 最后下标一定是0
     *      f(n) = (f(n - 1) + m) \mod n
     */
    public static int lastRemaining(int n, int m) {
        int f = 0;	// 胜利者只剩一人 最后下标一定是0
        // 逆推
        for (int i = 2; i <= n; ++i) {
            f = (f + m) % i;	//循环右移m位
        }
        return f;
    }
}
