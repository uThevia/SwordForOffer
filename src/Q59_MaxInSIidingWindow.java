import java.util.*;
import static java.lang.Math.max;

/**
 * 给定一个数组nums和滑动窗口大小k，找出所有滑动窗口里的最大值
 */
public class Q59_MaxInSIidingWindow {


    /**
     * 最大堆	O(nlogn), O(n)
     * 维护最大堆, 最大值是根
     * 窗口右移 加入新值并判断删除旧值
     * 	删除 判断堆顶是否在窗口内, 不在就迭代删根直到根在窗口内
     * 	所以加入元素是需要同时加入下标, 且比较元素大小时也要考虑重写比较器以比较下标大小
     */
    public static int[] maxheap(int[] nums, int k) {
        int n = nums.length;
        if (n == 0 || n < k) {
            return new int[0];
        }
        int[] res = new int[n - k + 1];
        // 最大堆中元素为int[2]={值,下标}
        PriorityQueue<int[]> maxheap = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] pair1, int[] pair2) {
                // 依顺序比较值和下标
                return pair1[0] != pair2[0] ? pair2[0] - pair1[0] : pair2[1] - pair1[1];
            }
        });
        // 基例: 第0个窗口
        for (int i = 0; i < k; ++i) {
            maxheap.offer(new int[]{nums[i], i});
        }
        res[0] = maxheap.peek()[0];
        //
        for (int i = k; i < n; ++i) {
            maxheap.offer(new int[]{nums[i], i});
            // 删除根直到根在窗口内
            while (maxheap.peek()[1] <= i - k) {
                maxheap.poll();
            }
            res[i - k + 1] = maxheap.peek()[0];
        }
        return res;
    }

    /**
     * M1 单调队列	O(n), O(k)
     * 单调队列只需存储窗口内严格单调递减的元素
     *  队首是(最旧的)最大值
     * 值较大的大下标会掩盖掉小下标, 即小下标且值较小不可能是最大值下标
     */
    public static int[] monotonicQueue(int[] nums, int k) {
        int n = nums.length;
        if (n == 0 || n < k) {
            return new int[0];
        }
        int[] res = new int[n - k + 1];
        Deque<Integer> maxQueue = new LinkedList<Integer>();
        // 基例: 第0个窗口
        for (int i = 0; i < k; ++i) {
            int num = nums[i];
            while (!maxQueue.isEmpty() && num > maxQueue.peekLast()) {
                maxQueue.pollLast();
            }
            maxQueue.addLast(num);
        }
        res[0] = maxQueue.peekFirst();
        // 窗口1:(n-k+1)
        for (int i = k; i < n; ++i) {
            // 删旧: 仅当其等于队首值
            if (maxQueue.peekFirst() == nums[i - k]) {
                maxQueue.removeFirst();
            }
            // 加新
            int num = nums[i];
            while (!maxQueue.isEmpty() && num > maxQueue.peekLast()) {
                maxQueue.pollLast();
            }
            maxQueue.addLast(num);
            res[i - k + 1] = maxQueue.peekFirst();
        }
        return res;
    }

    /**
     * M2 分治	O(n), O(n)
     * 将数组按窗口大小k分组, 预先计算每个分组的最大值
     * 	末分组的元素个数可能不满k
     * 对于非分组的窗口[i:i+k-1] : k不整除i
     *  跨越2个分组, 最大值是左分组的后缀最大值和右分组的前缀最大值之间的较大值
     * 所以需要预先计算每个分组的前缀最大值和后缀最大值
     *  每个分组的最大值也可以通过整个分组的前缀最大值或后缀最大值得到
     * 最终每个窗口的最大值是左分组的后缀最大值和右分组的前缀最大值之间的较大值
     */
    public static int[] dc(int[] nums, int k) {
        int n = nums.length;
        if (n == 0 || n < k) {
            return new int[0];
        }
        int[] res = new int[n - k + 1];

        // 前缀最大值
        int[] prefixMax = new int[n];
        // 后缀最大值
        int[] suffixMax = new int[n];
        // 预处理前缀和后缀最大值
        for (int i = 0; i < n; ++i) {
            if (i % k == 0) {
                prefixMax[i] = nums[i];
            } else {
                prefixMax[i] = max(prefixMax[i - 1], nums[i]);
            }
        }
        for (int i = n - 1; i >= 0; --i) {
            if (i == n - 1 || (i + 1) % k == 0) {
                suffixMax[i] = nums[i];
            } else {
                suffixMax[i] = max(suffixMax[i + 1], nums[i]);
            }
        }
        // 计算窗口的最大值
        for (int i = 0; i <= n - k; ++i) {
            res[i] = max(prefixMax[i + k - 1], suffixMax[i]);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,5,3,1,5,9};
        int k =3;
        System.out.println(Arrays.toString(maxheap(nums, k)));
        System.out.println(Arrays.toString(monotonicQueue(nums, k)));
        System.out.println(Arrays.toString(dc(nums, k)));
    }
}
