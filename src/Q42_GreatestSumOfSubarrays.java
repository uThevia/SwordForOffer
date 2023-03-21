/**
 * 整型数组所有子数组的和的最大值
 * 子数组要求连续,时间复杂度O(n)
 */
public class Q42_GreatestSumOfSubarrays {
    /**
     * M2 动态规划	O(n)
     * f(i) 以第i个数字结尾的子数组的最大和
     * 目标是 $\max_i f(i)$
     * $$
     * f(i)=\max{(f(i-1)+nums[i],nums[i])}
     * $$
     *
     * $$
     * f(i) =
     * \begin{cases}
     * nums[i]		\quad	&, i = 0 \or f(i-1) \le 0
     * \\ f(i-1) + nums[i]	\quad	&, i \ne 0 \and f(i-1) > 0
     * \end{cases}
     * $$
     */
    /** 原地修改 */
    public static int dp(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            nums[i] = max(nums[i - 1] + nums[i], nums[i]);
            max = max(max, nums[i]);
        }
        return max;
    }

    /** 不修改 */
    public static int dpNotChangeInput(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = nums[0];
        int former = 0;         // f[i - 1]
        int current = nums[0];  // f[i]
        for (int i = 1; i < nums.length; i++) {
            current = nums[i];
            if (former > 0) {
                current += former;
            }
            if (current > max) {
                max = current;
            }
            former = current;
        }
        return max;
    }

    private static int max(int a, int b) {
        return a > b ? a: b;
    }


    /**
     * M3 分治法	O(n), O(logn)
     *
     * 定义操作get(a,l,r)为查询序列a区间[l,r]内的最大子段和
     * 目标即为get(a,0,n-1)
     * (类似归并排序) 取m=(l+r)/2 分治求解[l,m],[m+1,r], 直到长度1就是正负数
     * 但如何将分治区间的信息合并到大区间: [l,m],[m+1,r] -> [l,r]
     * 	要维护区间的那些信息, 如何合并信息
     * 由于子序列需要连续, 最大和跨过区间的唯一可能就是通过2个子区间的端点
     * 	左子区间包含右端点 + 右子区间包含左端点
     * 对于一个区间需要维护4个量:
     * 	isum	区间和
     * 	lsum	包含l的最大和
     * 	rsum	包含r的最大和
     * 	msum	最大和
     * 合并 记左右子区间为左右
     * 	isum = 左isum + 右isum
     * 	lsum = max(左lsum, 左isum+右lsum)
     * 	rsum = max(右rsum, 左rsum+右isum)
     * 	msum = max(左msum, 右msum, 左rsum+右lsum)
     */
    public static int dc(int[] nums) {
        return dcRecursion(nums, 0, nums.length - 1).mSum;
    }
    private static Status dcRecursion(int[] a, int l, int r) {
        if (l == r) {
            return new Status(a[l], a[l], a[l], a[l]);
        }
        int m = (l + r) >> 1;
        Status lSub = dcRecursion(a, l, m);
        Status rSub = dcRecursion(a, m + 1, r);
        return pushUp(lSub, rSub);
    }
    /**
     * 子区间整合到区间
     */
    private static Status pushUp(Status l, Status r) {
        int iSum = l.iSum + r.iSum;
        int lSum = Math.max(l.lSum, l.iSum + r.lSum);
        int rSum = Math.max(r.rSum, r.iSum + l.rSum);
        int mSum = Math.max(Math.max(l.mSum, r.mSum), l.rSum + r.lSum);
        return new Status(lSum, rSum, mSum, iSum);
    }
    /**
     * 区间的信息:
     * 	isum	区间和
     * 	lsum	包含l的最大和
     * 	rsum	包含r的最大和
     * 	msum	最大和
     */
    private static class Status {
        public int lSum, rSum, mSum, iSum;

        public Status(int lSum, int rSum, int mSum, int iSum) {
            this.lSum = lSum;
            this.rSum = rSum;
            this.mSum = mSum;
            this.iSum = iSum;
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, -2, 4, -3, 5, -10, 3, 0};
        System.out.println(dp(nums.clone()));   // 会修改原数组 传递副本
        System.out.println(dpNotChangeInput(nums));
        System.out.println(dc(nums));

    }
}
