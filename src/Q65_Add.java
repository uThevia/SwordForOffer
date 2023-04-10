/**
 * 不用加减乘除做加法
 */
public class Q65_Add {
    /**
     * 位运算	O(1)
     * 位加 = 无进位和 + 进位
     * 无进位加 等效 异或
     * 进位 等效 位与左移1位
     * 进位加 等效 异或 + 位与左移1位
     *
     * 令和s=a+b, 无进位和n, 进位c
     * $$
     * s = a + b = n + c
     * \\ n = a \oplus b
     * \\ c = (a \& b) << 1
     * $$
     * 循环求n,c 直到c=0结果为n: 将n,c作为新的a,b求和s
     */
    public static int add(int a, int b) {
        while(b != 0) { 			// 循环直到进位为0
            int c = (a & b) << 1;	 // c 进位
            a ^= b; 				// a = n
            b = c; 					// b = c
        }
        return a;
    }
}
