/**
 * 一个整型数组中除2个数字外其他数字都出现了两次, 请找出这2个数字。
 * 要求时间复杂度O(n)，空间复杂度O(1)
 */
public class Q56_TwoNumbersAppearOnce {
    /**
     * 位二分	O(n)
     * 在数组异或结果数字中找到首个为1的位(第n位)。
     * 以第n位是否1将数组分成2个子数组，重复出现数字一定被分配到同个子数组, 2个唯一数字一定分到不同子数组,
     * 再对2个子数组分别异或得到这2个数。
     */
    public int[] bitBinarySearch(int[] nums) {
        // 异或结果
        int ret = 0;
        for (int n : nums) {
            ret ^= n;
        }
        // 最低为1位的值
        int div = 1;
        while ((div & ret) == 0) {
            div <<= 1;
        }
        // 找到2个数: 分别是最低为1位为0或1的所有数的异或结果
        int a = 0, b = 0;
        for (int n : nums) {
            if ((div & n) == 0) {
                a ^= n;
            } else {
                b ^= n;
            }
        }
        return new int[] {a, b};
    }
}

/**
 * 数组中除1个数字出现1次外都出现k次, 请找出这个数字
 * 位和求余	O(n)
 * 将所有数字位求和对k求余即为结果
 * 出现k次的数字的位求和被k整除
 */
class SingleNumber {
    public int bitAnd(int[] nums, int k) {
        int intSize = Integer.SIZE;
        int[] counts = new int[intSize];
        for(int num : nums) {
            for(int j = 0; j < intSize; j++) {
                counts[j] += num & 1;
                num >>>= 1;
            }
        }
        int res = 0;
        for(int i = 0; i < intSize; i++) {
            res <<= 1;
            res |= counts[intSize - 1 - i] % k;
        }
        return res;
    }

    /**
     * 有限状态自动机 Finite-state machine
     * 只针对k=3
     */
    public int fsm(int[] nums) {
        int ones = 0, twos = 0;
        for(int num : nums){
            ones = ones ^ num & ~twos;
            twos = twos ^ num & ~ones;
        }
        return ones;
    }
}