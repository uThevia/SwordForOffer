package algorithms.bit;

public class Math {
    /**
     * 其中sign(a)表示a的符号位(负数-1 其他0) 而不是通用的sign函数
     * a >> 31 = sign(a)
     *      -1, a < 0
     *      0,  a >= 0
     *
     * diff = b - a
     * min(a,b) = b - diff * sign(diff)
     * max(a,b) = a + diff * sign(diff)
     *
     * 负数的补码是绝对值反码+1
     * 若 a > 0 有 -a = ~a + 1
     *      ~(-a - 1) = a
     * 1 = 0xFFFFFFFF
     *      a ^ 0 = a
     *      a ^ -1 = ~a
     *
     * 若 a > 0
     *      (a + 0) ^ 0 = a
     *      (-a + -1) ^ -1 = a
     * abs(a) = (a + sign(a)) ^ sign(a)
     */

    private static final int INT_LENGTH = 31;

    public static int max(final int a, final int b) {
        final int diff = b - a;
        // b < a: b - (diff & -1)
        // b > a: b - (diff & 0)
        return b - (diff & (diff >> INT_LENGTH));
    }

    public static int min(final int a, final int b) {
        final int diff = b - a;
        // b < a: a + (diff & -1)
        // b > a: a + (diff & 0)
        return a + (diff & (diff >> INT_LENGTH));
    }

    public static int abs(final int a) {
        final int sign = (a >> 31);
        return (a  ^ sign) - sign;
        // return (a + sign) ^ sign;
        // return a >> 31 == 0 ? a : (~a + 1);
    }


    /**
     * 均值 向下取整
     */
    public static int meanRoundDown(int a, int b) {
        // 交集 + 差集 / 2
        return (a & b) + ((a ^ b) >> 1);
    }
    /**
     * 均值 向上取整
     */
    public static int meanRoundUp(int a, int b) {
        // 并集 - 差集 / 2
        return (a | b) - ((a ^ b) >> 1);
    }

    public static double mean(int... values) {
        double mean = values[0];
        for (int index = 1; index < values.length; index++) {
            double value = values[index];
            mean += (value - mean) / (index + 1);
        }
        return mean;
    }

    public static void swap(int[] nums, int i, int j) {
        /* 上越界
        nums[i] = nums[i] + nums[j];    // 上越界
        nums[j] = nums[i] - nums[j];
        nums[i] = nums[i] - nums[j];
         */
        /*
        b ^ (a ^ b) = a
        (a ^ b) ^ a = b
         */
        nums[i] ^= nums[j] ^= nums[i] ^= nums[j];
    }

    public static boolean isEven(int a) {
        return 0 == (a & 1);
    }
    public static boolean isOdd(int a) {
        return 1 == (a & 1);
    }

    public static int opposite(int a) {
        return ~a + 1;
    }

    /**
     * 无符号整型 交换高和低的半部分
     */
    public static int swapHighAndLowBit(int a) {
        int HALF_INT_LENGTH = INT_LENGTH / 2;
        return (a >>> HALF_INT_LENGTH) | (a << HALF_INT_LENGTH);
    }
    /**
     * 无符号整型 反转
     * 分治 类似于归并自底向上: 组的大小为 2,4,8,16
     * 每一步将组内的元素(2个子组)相对反转
     * 取组: 位与 左右子组所在位置置1的常数
     * 反转: 左子组右移组大小, 右子组左移组大小
     * 如: 2组  取奇数位 & 0xAAAA, 取偶数位 & 0x5555, 左子右移1, 右子左移1
     */
    public static int reverse(int a) {
        a = ((a & 0xAAAA) >> 1) | ((a & 0x5555) << 1);
        a = ((a & 0xCCCC) >> 2) | ((a & 0x3333) << 2);
        a = ((a & 0xF0F0) >> 4) | ((a & 0x0F0F) << 4);
        a = ((a & 0xFF00) >> 8) | ((a & 0x00FF) << 8);
        return a;
    }

    public static int numbeOf1s(int a) {
        int count = 0;
        while(a != 0){
            a = a & (a - 1);
            count++;
        } return count;
    }

    public static void main(String[] args) {
        int a = 2;
        int b = -3;

        assert min(a, b) == b;
        assert max(a, b) == a;
        assert abs(a) == a;
        assert abs(b) == -b;
        assert meanRoundDown(a, b) == -1;
        assert meanRoundUp(a, b) == 0;


        System.out.println("Math: test successfully.");
    }
}
