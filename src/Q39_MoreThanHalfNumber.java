import java.util.Arrays;

/**
 * 找出数组中出现次数超过一半的数字
 */
public class Q39_MoreThanHalfNumber {
    /**
     * 不存在或输入不合法返回 Integer.MIN_VALUE;
     */
    /**
     * M1 切分法	O(n)	原地修改
     * 结果一定是中位数
     * **数组中找第k大的数有O(n)算法 切分Partition(快排第一步)**
     * 切分基于二分法 将序列分为某条件的满足和不满足2部分
     * 	    随机选取pivot(支点)并根据与其大小关系分为左右2部分
     * 	    根据支点下标和k的关系得出目标在哪个部分
     */
    public static int partition(int[] nums) {
        // int[]转Integer[]以适用T[]:   Arrays.stream(nums).boxed().toArray(Integer[]::new)
        if (checkInvalidArray(Arrays.stream(nums).boxed().toArray(Integer[]::new))) {
            return Integer.MIN_VALUE;
        }
        int n = nums.length;
        int left = 0;
        int right = n - 1;
        final int MID = n / 2;    // 中位数下标
        int index = 0;      // 目前支点的下标
        // 找到中位数
        while (index != MID) {
            index = partition(nums, left, right);
            if (index > MID) {
                right = index - 1;
            }else { // index > MID
                left = index + 1;
            }
            // index == MID 会直接退出循环
        }
        int res =  nums[index];
        if (!checkMoreThanHalf(nums, res)) {
            return Integer.MIN_VALUE;
        }
        return res;
    }

    /**
     * 切分 交换法
     * 双指针 分别找到左右首个不符合对应条件的元素 交换
     * 目标 <=, pivot, >
     * 非常严格 不能改动
     */
    private static int partition(int[] nums, int left, int right) {
        int pivot = nums[right];  // 支点
        // 双指针
        int i = left;
        int j = right;  // 必须包含支点
        while (i < j) {
            // 必须先动支点异侧指针 (朝向支点的指针)
            while (i < j && nums[i] < pivot) {
                ++i;
            }
            while (i < j && nums[j] >= pivot) {  // 必须包含=
                --j;
            }
            if (i < j) {    // 未达到终止条件
                swap(nums, i, j);
            }
        }
        swap(nums, i, right);   // 支点交换位置一定是异侧的先动指针
        return i;
    }

    private static void swap(int[] nums, int i ,int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 检查输入数组合法性
     */
    private static<T> boolean checkInvalidArray(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 检查给定数是否超过数组一半数量
     * @param nums
     * @param number
     * @param <T>
     * @return
     */
    private static<T> boolean checkMoreThanHalf(int[] nums, int number) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == number) {
                ++count;
            }
        }
        return count >= (nums.length / 2);
    }


    /**
     * M2 Boyer-Moore 摩尔投票法	O(n)	不修改
     * 目标次数超过一半 则比其他所有数出现次数还多
     * 维护2个值 当前数及其计数
     * 当遇到同值+1 不同值-1, 清零后更换值及其计数
     * 可以理解为将数组中任意2个不同值抵消, 则最终剩下的一定是目标
     */
    public static int countNums(int[] nums) {
        if (checkInvalidArray(Arrays.stream(nums).boxed().toArray(Integer[]::new))) {
            return Integer.MIN_VALUE;
        }
        int count = 0;
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (count == 0) {
                res = nums[i];
                ++count;
            } else if (nums[i] == res) {
                ++count;
            } else {
                --count;
            }
        }
        if (!checkMoreThanHalf(nums, res)) {
            return Integer.MIN_VALUE;
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,2,2,2};
        // int[] nums = {1,2,3,4,5,6};

        System.out.println(partition(nums));
        System.out.println(countNums(nums));
    }

}
