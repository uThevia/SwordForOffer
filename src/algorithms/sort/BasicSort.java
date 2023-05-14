package algorithms.sort;

import java.util.Arrays;

/**
 * 基本排序     平均   最好   最坏      空间  修改  稳定性
 * 冒泡排序     n^2   n     n^2       1    原地  稳定
 * 选择排序     n^2   n^2   n^2       1    原地  不稳定
 * 插入排序     n^2   n     n^2       1    原地  稳定
 * 希尔排序     nlogn nlogn nlog^2(n) 1    原地  不稳定
 * 归并排序     nlogn nlogn nlogn     n    外置  稳定
 * 快速排序     nlogn nlogn n^2       logn 原地  不稳定
 * 堆排序　     nlogn nlogn nlogn     1    原地  不稳定
 * 计数排序     n+k   n+k   n+k       k    外置  稳定
 * 桶排序　     n+k   n+k   n^2       n+k  外置  稳定
 * 基数排序     n*k   n*k   n*k       n+k  外置  稳定
 */
public class BasicSort {
    /** 冒泡排序 */
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        // 外层循环用于控制轮数
        for (int i = 0; i < n - 1; i++) {
            // 内层循环用于控制每轮比较次数
            for (int j = 0; j < n - i - 1; j++) {
                // 如果前一个数比后一个数大，则交换两个数的位置
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    /** 选择排序 */
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        // 遍历数组，每次选出未排序序列的最小元素并放到已排序序列的最后面
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            // 遍历未排序序列，找出最小元素的下标
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            // 交换未排序序列的最小元素和已排序序列的下一个元素
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }

    /** 插入排序 */
    // 插入排序函数
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        // 遍历数组，将未排序序列中的每一个元素插入到已排序序列的合适位置
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            // 将已排序序列中比key大的元素向后移动，为key腾出插入的位置
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key; // 插入key到合适的位置
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 1, 4, 2, 8}; // 待排序数组
        int[] array;
        array = arr.clone();
        bubbleSort(array);
        System.out.println("bubbleSort: " + Arrays.toString(array));
        array = arr.clone();
        selectionSort(array);
        System.out.println("bubbleSort: " + Arrays.toString(array));
        array = arr.clone();
        insertionSort(array);
        System.out.println("bubbleSort: " + Arrays.toString(array));
    }
}
