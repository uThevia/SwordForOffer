package algorithms.sort;

/**
 * 希尔排序
 */
public class ShellSort {
    /**
     * 希尔排序算法 - 对数组 arr 进行排序
     *
     * @param arr 待排序的整型数组
     */
    public static void shellSort(int[] arr) {
        int gap = arr.length / 2; // 设置步长
        // 当步长为 1 时即完成排序
        while (gap > 0) {
            // 对每一组进行插入排序
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                // 每组中相邻元素之间相差 gap 个位置，比较并交换相邻元素，直到找到正确位置
                while (j >= gap && arr[j - gap] > arr[j]) {
                    swap(arr, j - gap, j);
                    j -= gap;
                }
            }
            gap /= 2; // 步长除以 2，继续对数组进行分组和排序
        }
    }

    /**
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
