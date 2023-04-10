/**
 * 求1至n的和
 * 不能使用乘除法, 条件判断语句(for,while,if,else,switch,case,A?B:C)
 */
public class Q64_Accumulate {
    /**
     * 逻辑运算符的短路效应	O(n)
     * 借助逻辑运算符的短路效应可实现递归: 基例条件放在左边, 右边是(先遍历的)递归
     * 在java中独立逻辑运算必须声明变量且逻辑运算值不能是整型
     */
    static int res = 0;
    public static int sum(int n) {
        boolean x = n > 1 && sum(n - 1) > 0;
        res += n;
        return res;
    }
    /** 简写 */
    public static int sumNums(int n) {
        boolean x = n > 1 && (n += sumNums(n - 1)) > 0;
        return n;
    }
}
