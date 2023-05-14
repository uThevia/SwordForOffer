package algorithms.sort;

/** 希尔排序 */
public class ShellSort {

    /** 希尔排序 */
    public static void shellSort(int[] arr) {
    int n = arr.length;
    // 步长
    for (int gap = n / 2; gap > 0; gap /= 2) {
        // 按数组顺序 对每组的非首个元素进行组内插入排序: 组内相邻元素间距为步长
        for (int i = gap; i < n; i++) {
            // 每组的非首个元素
            int temp = arr[i];
            // 将当前元素插入到组内有序区的正确位置
            int j;	// 每组的有序区元素下标
            for(j = i - gap; j >= 0 && temp < arr[j]; j -= gap) {
                arr[j + gap] = arr[j];
            }
            arr[j + gap] = temp;
        }
    }
}

}
