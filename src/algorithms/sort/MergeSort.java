package algorithms.sort;

import java.util.Arrays;

/**
 * 归并排序
 */
public class MergeSort {
    
    /** 归并排序 */
    public static void mergeSort(int[] arr) {
        // 辅助数组，避免递归中重复创建数组增加空间复杂度
        int[] tempBuffer = new int[arr.length];
        mergeSortRecursion(arr, 0, arr.length - 1, tempBuffer);
    }
    
    private static void mergeSortRecursion(int[] arr, int left, int right, int[] tempBuffer) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortRecursion(arr, left, mid, tempBuffer);
            mergeSortRecursion(arr, mid + 1, right, tempBuffer);
            merge(arr, left, mid, right, tempBuffer);
        }
    }
    
    private static void merge(int[] arr, int left, int mid, int right, int[] tempBuffer) {
        // 左右部分开始下标
        int l = left;
        int r = mid + 1;
        // 辅助数组的当前下标
        int i = l;
        
        // 按序合并左右部分
        while (l <= mid && r <= right) {
            if (arr[l] <= arr[r]) {
                tempBuffer[i++] = arr[l++];
            } else {
                tempBuffer[i++] = arr[r++];
            }
        }
        // 拷贝剩余的左或右部分
        while (l <= mid) {
            tempBuffer[i++] = arr[l++];
        }
        while (r <= right) {
            tempBuffer[i++] = arr[r++];
        }
        
        // 将排好序的辅助数组拷贝回原数组
        for (i = left; i <= right; i++) {
            arr[i] = tempBuffer[i];
        }
    }
    
    public static void main(String[] args) {
        int[] array = {5, 1, 4, 2, 8}; // 待排序数组
        mergeSort(array);
        System.out.println(Arrays.toString(array));
    }
    
    
}
