import java.util.*;

/**
 * 数组中的逆序对
 * 求数组中的逆序对的个数
 * 逆序对指数组中前后大小降序的2个数字
 */
class Q51_lnversePairs {
    /**
     * M1 分治
     * 类似自底向上的归并排序 增加统计逆序以计算结果
     * 分治的治过程可以用while或for循环实现
     *      while循环以左右指针
     *      for循环以排序数组的指针
     */
    /** 1.1, while循环实现 */
    public static int dc(int[] nums) {
        if (nums == null) {
            return 0;
        }
        // 原数组复制 避免修改原数组
        int[] copy = Arrays.copyOf(nums, nums.length);
        return dcRecursion(copy,  0, nums.length -1);
    }
    /**
     * @param nums  归并有序    原数组复制的归并排序中间结果
     */
    private static int dcRecursion(int[] nums, int l, int r) {
        // 终止
        if (l >= r) {
            return 0;
        }
        // 分:
        int mid = l + ((r - l) >> 1);
        int lres = dcRecursion(nums,  l, mid);         // 左部分结果
        int rres = dcRecursion(nums, mid + 1, r);    // 右部分结果
        // 治:
        // temp 保存nums[l:r]输入值
        int[] temp = new int[r - l + 1];
        for (int i = l; i <= r; i++) {
            temp[i - l] = nums[i];
        }
        int lp = l;         // 左部分指针
        int rp = mid + 1;   // 右部分指针
        int index = l;      // 归并排序结果指针
        int curres = 0;     // 合并结果
        while (lp <= mid && rp <= r) {          // 终止条件: 左部分遍历完或右部分遍历完
            while (lp <= mid && temp[lp - l] <= temp[rp - l]) {
                nums[index++] = temp[lp++ - l];
            }
            curres += mid + 1 - lp;
            nums[index++] = temp[rp++ - l];
        }
        // 以下2个循环只会执行1个, 无所谓先后
        while (lp <= mid) {             // 右部分遍历完
            nums[index++] = temp[lp++ - l];
        }
        while (rp <= r) {               // 左部分遍历完
            nums[index++] = temp[rp++ - l];
        }
        return lres + curres + rres;
    }

    /** 1.2, for循环实现 */
    public static int dcFor(int[] nums) {
        if (nums == null) {
            return 0;
        }
        // 原数组复制 避免修改原数组
        int[] copy = Arrays.copyOf(nums, nums.length);
        int[] temp = new int[nums.length];
        return dcForRecursion(copy, temp,  0, nums.length -1);
    }
    /**
     *
     * @param nums  归并有序    原数组复制的归并排序中间结果
     * @param temp  为了治保存nums输入结果
     */
    private static int dcForRecursion(int[] nums, int[] temp, int l, int r) {
        // 终止
        if (l >= r) {
            return 0;
        }
        // 分:
        int mid = l + ((r - l) >> 1);
        int lres = dcForRecursion(nums, temp, l, mid);         // 左部分结果
        int rres = dcForRecursion(nums, temp, mid + 1, r);    // 右部分结果
        // 治:
        // temp保存nums[l:r]输入值
        for (int i = l; i <= r; i++) {
            temp[i] = nums[i];
        }
        int lp = l;         // 左部分指针
        int rp = mid + 1;   // 右部分指针
        int curres = 0;     // 合并结果
        for (int k = l; k <= r; k++) {
            if (lp > mid) {                     // 左部分已遍历完
                nums[k] = temp[rp++];
            } else if (rp > r) {                // 右部分已遍历完
                nums[k] = temp[lp++];
            } else if (temp[lp] <= temp[rp]) {  // 左指针值与右指针值不是逆序
                nums[k] = temp[lp++];
            } else {                            // 左指针值与右指针值是逆序
                curres += mid + 1 - lp;
                nums[k] = temp[rp++];
            }
        }
        return lres + curres + rres;
    }


    /**
     * M2 树状数组	O(nlogn), O(n)
     */
    public static int byBIT(int[] nums) {
        if (nums == null) {
            return 0;
        }
        int len = nums.length;
        // 离散化：使值域由稀疏到排列
        // 1、使用二分搜索树 为了去除重复元素
        Set<Integer> treeSet = new TreeSet<>();
        for (int i = 0; i < len; i++) {
            treeSet.add(nums[i]);
        }
        // 2、把排名存在哈希表里方便查询
        Map<Integer, Integer> rankMap = new HashMap<>();
        int rankIndex = 1;
        for (Integer num : treeSet) {
            rankMap.put(num, rankIndex);
            rankIndex++;
        }
        // 在树状数组内部计算前缀和: 反向遍历, 先给对应的排名 + 1，再查询前缀和
        int count = 0;
        BIT bit = new BIT(rankMap.size());
        for (int i = len - 1; i >= 0; i--) {
            int rank = rankMap.get(nums[i]);
            bit.update(rank, 1);
            count += bit.query(rank - 1);
        }
        return count;
    }

    /**
     * 树状数组
     * 是一种实现了高效查询「前缀和」与「单点更新」操作的数据结构
     * 单点更新 update(i,v)	索引i值+v
     * 区间查询/前缀和 query(i)	1:i值和
     * 修改和查询都是O(logn)
     */
    public static class BIT {
        /**
         * tree int[]   树状数组
         * length
         */
        private int[] tree;
        private int length;

        public BIT(int length) {
            this.length = length;
            this.tree = new int[length + 1];
        }

        /**
         * 返回整数的最低位1 及其之后的0
         * 删除整数非最低位1
         */
        public static int lowbit(int x) {
            return x & (-x);
        }

        /**
         * 区间查询/前缀和
         * 查询小于等于 tree[index] 的元素个数
         */
        public int query(int i) {
            int ret = 0;
            while (i != 0) {
                ret += tree[i];
                i -= lowbit(i);
            }
            return ret;
        }

        /**
         * 单点更新：将index值 + delta
         */
        public void update(int i, int delta) {
            while (i <= length) {
                tree[i] += delta;
                i += lowbit(i);
            }
        }

        public int length() {
            return this.length;
        }
    }

    public static void main(String[] args) {
        int[] nums0 = {0};
        System.out.println(dc(nums0));
        System.out.println(dcFor(nums0));
        System.out.println(byBIT(nums0));
        int[] nums = {1,5,7,9, 0,2,4,10};
        System.out.println(dc(nums));
        System.out.println(dcFor(nums));
        System.out.println(byBIT(nums));
    }
}
