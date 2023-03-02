import java.util.ArrayList;
import java.util.List;

public class Q15_NumbeOf1sInBinary {


    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(0x7FFFFFFF);   // 2147483647 最大正数  0111...
        list.add(0x80000000);   // -2147483648 最大负数 1000...
        list.add(0xFFFFFFFF);   // -1

        for (Integer n : list){
            System.out.println("n = " + n);
            System.out.println("shift(n) = " + shift(n));
            System.out.println("shiftOne(n) = " + shiftOne(n));
            System.out.println("subtractOneThenAnd(n) = " + subtractOneThenAnd(n));
            System.out.println();
        }
    }

    /**
     * 左移1 O(k)
     * 而不是整数 将1与整数每位对齐取与
     * 从右至左 每位和one与仅当结果为one时个数+1, 左移one
     * 整形有多少位就需要右移多少位 int需32位
     */
    public static int shiftOne(int n) {
        int count = 0;
        int one = 1;    // 1左移若干位
        while (one != 0){
            if (one == (n & one))   // 左移若干位的1 与n取与结果为该位的值
                ++count;
            one <<= 1 ;    // 1左移
        }
        return count;
    }

    /**
     * 减1取与
     * 二进制整数减1的结果是原整数最右一个1及其之后取反
     * 再与原取与的结果是原整数删除最右一个1
     * 例如`1100 - 1 = 1001`, 1100 & `1001 = 1000`
     * 每一次减1取与就是删除1个最右1位
     * 共能进行多少次就有多少个1
     */
    public static int subtractOneThenAnd(int n) {
        int count = 0;
        while (n != 0) {
            n = n & (n - 1);
            ++count;
        }
        return count;
    }

    /**
     * 右移n  O(logn)
     * 从右至左 每位和1与仅当结果为1时个数+1, 无符号右移
     */
    public static int shift_unsign(int n) {
        int count = 0;
        while (n != 0) {
            if (1 == (n & 1))
                ++count;
            n >>>= 1; // 无符号右移 避免
        }
        return count;
    }
    /**
     *  修正负数: 右移负数最高位1不会变成0 以满足等效于/2
     *  负数最高位取反并记录下最高位的1(`count++`) 变成正数处理
     */
    public static int shift(int n) {
        int count = 0;
        // 负数最高位取反: 避免右移最高位保持为1的死循环
        if (n < 0) {
            n &= Integer.MAX_VALUE;     // 负数最高位取反
            ++count;
        }
        while (n != 0) {
            if (1 == (n & 1))
                ++count;
            n >>= 1; // 右移
        }
        return count;
    }

    /** 最高位取反 */
    int notHighestBit(int n) {
        if (n > 0)
            n |= Integer.MIN_VALUE; // 正数最高位取反
        else if (n < 0)
            n &= Integer.MAX_VALUE;     // 负数最高位取反
        return n;
    }
}
