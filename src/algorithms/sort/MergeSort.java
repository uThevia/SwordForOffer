package algorithms.sort;

/**
 * 归并排序
 */
public class MergeSort {
    /**
     * 归并排序算法 - 对数组 arr 进行排序
     *
     * @param arr 待排序的整型数组
     */
    public static void mergeSort(int[] arr) {
        // 创建一个等长的辅助数组，避免重复创建数组增加空间复杂度
        int[] temp = new int[arr.length];
        // 调用辅助方法 mergeSortHelper，开始归并排序
        mergeSortRecursion(arr, 0, arr.length - 1, temp);
    }

    /**
     * 归并排序辅助方法 - 对 arr 中索引从 left 到 right 的元素进行归并排序
     *
     * @param arr        待排序的整型数组
     * @param left       左边界
     * @param right      右边界
     * @param tempBuffer 辅助数组，用来存放排序后的结果
     */
    private static void mergeSortRecursion(int[] arr, int left, int right, int[] tempBuffer) {
        if (left < right) {
            // 将数组分成左右两个部分，分别进行递归的归并排序
            int mid = (left + right) / 2;
            mergeSortRecursion(arr, left, mid, tempBuffer);
            mergeSortRecursion(arr, mid + 1, right, tempBuffer);
            // 将两个有序的子数组合并为一个有序数组
            merge(arr, left, mid + 1, right, tempBuffer);
        }
    }

    /**
     * 归并方法 - 将 arr 中左半部分（leftStart 到 mid）和右半部分（rightStart 到 rightEnd）合并为一个有序的数组
     *
     * @param arr        待排序的整型数组
     * @param leftStart  左边部分的起始索引
     * @param rightStart 右边部分的起始索引
     * @param rightEnd   右边部分的结束索引
     * @param tempBuffer 辅助数组，用来存放排序后的结果
     */
    private static void merge(int[] arr, int leftStart, int rightStart, int rightEnd, int[] tempBuffer) {
        // 记录左半部分的结束索引和右半部分的结束索引
        int leftEnd = rightStart - 1;
        int index = leftStart; // 用于记录辅助数组的下标
        int numElements = rightEnd - leftStart + 1; // 记录归并区间的元素个数

        // 将左半部分和右半部分按序合并到辅助数组中
        while (leftStart <= leftEnd && rightStart <= rightEnd) {
            if (arr[leftStart] <= arr[rightStart]) {
                tempBuffer[index++] = arr[leftStart++];
            } else {
                tempBuffer[index++] = arr[rightStart++];
            }
        }

        // 将剩余的左半部分或右半部分的元素拷贝到辅助数组中
        while (leftStart <= leftEnd) {
            tempBuffer[index++] = arr[leftStart++];
        }

        while (rightStart <= rightEnd) {
            tempBuffer[index++] = arr[rightStart++];
        }

        // 将排好序的辅助数组中的元素拷贝回原数组中
        for (int i = 0; i < numElements; i++, rightEnd--) {
            arr[rightEnd] = tempBuffer[rightEnd];
        }
    }

}
