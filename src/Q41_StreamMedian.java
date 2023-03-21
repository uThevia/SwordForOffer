import java.util.PriorityQueue;

/**
 * 数据流中的中位数
 */
public class Q41_StreamMedian {

    /**
     * 输入数据流 IntStream
     * 方便起见, 输入类型为int[]
     */
    public static double getMedian(int[] nums) {
        /**
         * 维护左右部分的最大,最小堆
         */
        PriorityQueue<Integer> leftMaxHeap = new PriorityQueue<Integer>((a,b) -> (b-a));;   // 最大堆逆序
        PriorityQueue<Integer> rightMinHeap = new PriorityQueue<Integer>((a,b) -> (a-b));;  // 最小堆正序
        for (int num : nums) {
            add(num, leftMaxHeap, rightMinHeap);
        }
        return getMedian(leftMaxHeap, rightMinHeap);
    }

    /**
     * M 堆法 logn, n
     * 优先队列实现
     * 优先队列的底层就是最大堆和最小堆
     * 分别用逆序,正序优先队列维护左右部分, 保持2部分数量相同(差为0或1)
     *
     * 无需判断数属于左右哪部分, 需要维持数量左>=右(中位数=奇左偶均)
     *     如果左=右 则数先加入右再将左最大值加入左
     *     如果左>右 则数先加入左再将左最大值加入右
     */
    private static void add(int num, PriorityQueue<Integer> leftMaxHeap, PriorityQueue<Integer> rightMinHeap) {
        if (leftMaxHeap.size() == rightMinHeap.size()) {
            rightMinHeap.add(num);
            leftMaxHeap.add(rightMinHeap.poll());
        } else {
            leftMaxHeap.add(num);
            rightMinHeap.add(leftMaxHeap.poll());
        }
    }
    private static double getMedian(PriorityQueue<Integer> leftMaxHeap, PriorityQueue<Integer> rightMinHeap) {
        return leftMaxHeap.size() == rightMinHeap.size() ? (leftMaxHeap.peek() + rightMinHeap.peek()) / 2.0 : leftMaxHeap.peek();
    }

    /**
     * 默认写法2
     * 写法1
     *  数根据左右根的大小关系加入到一侧
     * 	插入后若左右差为2就则将少的那部分的根加入到另一部分作为根
     * 写法3
     *    数先加入左再将左最大值加入右
     *    如果左<右 则将右最小值加入左
     *
     * 3种插入左右堆的方法
     * 第一种代码复杂点, 但操作堆次数少
     * 第二种代码简单, 但固定每次插入操作2次堆
     * 第三种比第二种代码不简单, 但肯能每次插入操作4次堆
     * 面试时选择第二种
     */
    private static void addWritingStyle1(int num, PriorityQueue<Integer> leftMaxHeap, PriorityQueue<Integer> rightMinHeap) {
        if (rightMinHeap.isEmpty() || num <= rightMinHeap.peek()) {
            rightMinHeap.offer(num);
            if (leftMaxHeap.size() + 1 < rightMinHeap.size()) {
                leftMaxHeap.offer(rightMinHeap.poll());
            }
        } else {
            leftMaxHeap.offer(num);
            if (leftMaxHeap.size() > rightMinHeap.size()) {
                rightMinHeap.offer(leftMaxHeap.poll());
            }
        }
    }
    private static void addWritingStyle3(int num, PriorityQueue<Integer> leftMaxHeap, PriorityQueue<Integer> rightMinHeap) {
        leftMaxHeap.add(num);
        rightMinHeap.add(leftMaxHeap.poll());
        if (leftMaxHeap.size() < rightMinHeap.size()) {
            leftMaxHeap.add(rightMinHeap.poll());
        }
    }

}
