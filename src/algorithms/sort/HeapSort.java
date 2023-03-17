package algorithms.sort;

/**
 * 堆排序
 */
public class HeapSort {
    /**
     * 堆排序算法
     */
    public static void heapSort(int[] arr) {
        // 构建堆
        buildHeap(arr);
        // 从堆中取出每个元素，再重新构建堆
        for (int i = arr.length - 1; i > 0; i--) {
            // 将堆中第一个数（最大数）与当前数交换位置
            swap(arr, 0, i);
            // 重新构建堆（不包括已经取出的数）
            heapify(arr, 0, i);
        }
    }

    /**
     * 构建堆
     * 将数组 arr 转化为一个最大堆
     */
    private static void buildHeap(int[] arr) {
        // 从二叉树中倒数第二层的最后一个节点开始进行 heapify 操作
        int lastNodeIndex = arr.length - 1;
        int parentNodeIndex = (lastNodeIndex - 1) / 2;
        for (int i = parentNodeIndex; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
    }

    /**
     * 堆化
     * 将以 rootIndex 为根节点的子树转化为一个最大堆
     *
     * @param arr
     * @param rootIndex   当前子树的根节点索引
     * @param heapifySize 堆化数组的大小
     */
    private static void heapify(int[] arr, int rootIndex, int heapifySize) {
        int leftChildIndex = rootIndex * 2 + 1;
        int rightChildIndex = rootIndex * 2 + 2;
        int largestIndex = rootIndex;

        // 找到左右种比较大的那个数，判断是否需要交换位置
        if (leftChildIndex < heapifySize && arr[leftChildIndex] > arr[largestIndex]) {
            largestIndex = leftChildIndex;
        }
        if (rightChildIndex < heapifySize && arr[rightChildIndex] > arr[largestIndex]) {
            largestIndex = rightChildIndex;
        }
        if (largestIndex != rootIndex) {
            // 交换根节点与左右子节点中最大的节点的位置
            swap(arr, rootIndex, largestIndex);
            // 递归调用 heapify 方法，处理调换后的子树
            heapify(arr, largestIndex, heapifySize);
        }
    }


    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
