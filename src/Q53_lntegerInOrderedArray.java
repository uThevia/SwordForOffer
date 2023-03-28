/**
 * 有序数组中的整数
 * 二分查找法
 */
public class Q53_lntegerInOrderedArray {
    /**
     * lntegerFrequency
     * 统计一个数字在排序数组中出现的次数
     *
     * 查找到下标后, 继续二分直到找到最小和最大下标
     */
    /**
     * 用循环实现二分搜索
     * 左右边界不包含target, 避免target不存在
     *  左边界是首个小于目标的下标
     *  右边界是首个大于目标的下标
     */
    public static int integerFrequencyLoop(int[] nums, int target) {
        int n = nums.length;
        // 搜索左边界 left
        int l = 0;
        int r = n - 1;
        int m;
        while (l <= r) {
            m = l + ((r - l) >> 1);
            if (nums[m] < target) {
                l = m + 1;
            } else {        // 搜索左边界时 等于目标搜索左部分
                r = m - 1;
            }
        }
        int left = r;

        // 不存在target: 提前返回
        if (l < n && nums[l] != target) {
            return 0;
        }

        // 搜索右边界 right
        l = 0;
        r = n - 1;
        while (l <= r) {
            m = l + ((r - l) >> 1);
            if (nums[m] <= target) {    // 搜索左边界时 等于目标搜索右部分
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        int right = l;
        return right - left - 1;
    }
    /**
     * 函数实现二分搜索
     * 只需要找到左边界, 通过目标+1和目标左边界的差
     * 或 只需要找到右边界, 通过目标-1和目标右边界的差
     */
    public static int integerFrequency(int[] nums, int target) {
        return integerFrequencyBS(nums, target + 1) - integerFrequencyBS(nums, target);
    }
    /**
     * 找到target的左边界(不包含target)
     */
    public static int integerFrequencyBS(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        int m;
        while (l <= r) {
            m = l + ((r - l) >> 1);
            if (nums[m] < target) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return r;
    }
    /**
     * 根据逻辑标志找target的左/右边界(不包含target)
     */
    public static int integerFrequencyBS(int[] nums, int target, boolean isRight) {
        int l = 0;
        int r = nums.length - 1;
        int m;
        while (l <= r) {
            m = l + ((r - l) >> 1);
            if (nums[m] < target || (isRight && nums[m] == target)) {   // 右边界包含=
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return isRight ? l : r;
    }


    /**
     * Missinglnteger
     * 数组中的缺失数字
     * 严格递增整型数组 长度n-1 值域[0,n-1], 只有一个整数不在
     *
     * 缺失数前的数和下标相等, 缺失数后的数比下标大1
     * 二分查找直到分界线: 首个与下标不等的位置的下标
     */
    public static int missinglnteger(int[] nums) {
        int l = 0;
        int r = nums.length - 1;
        int m;
        while (l <= r) {
            m = l + ((r - l) >> 1);
            if (nums[m] == m) {     // 左部分 值等于下标 更新左指针
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return l;   // 缺失数是右部分的首个位置的下标
    }

    /**
     * lntegerEqualIndex
     * 数组中和下标相等的元素
     * 严格递增整型数组中找到任意一个和下标相等的元素
     *
     * 相等区间前的数比下标小, 相等区间后的数比下标大
     * 	    相等区间指值和下标相等的一个区间, 严格递增中一定连续
     * 二分查找直到分界区间: 与下标相等的位置区间的下标
     */
    public static int integerEqualIndex(int[] nums) {
        int l = 0;
        int r = nums.length - 1;
        int m;
        while (l <= r) {
            m = l + ((r - l) >> 1);
            if (nums[m] == m) {     // 找到一个结果
                return m;
            } else if (nums[m] < m) {     // 左部分 值小于下标 更新左指针
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return -1;   // 不存在结果
    }
}
