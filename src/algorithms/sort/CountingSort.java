package algorithms.sort;

/**
 * 计数排序
 */
public class CountingSort {
    /**
     * 计数排序算法
     */
    public static void countingSort(int[] arr) {
        // 找到数组中最大的元素
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        // 开辟一个桶，数组下标即为元素值，值为每个元素出现次数
        int[] countArr = new int[max + 1];

        // 统计每个元素出现的次数
        for (int i = 0; i < arr.length; i++) {
            countArr[arr[i]]++;
        }

        // 依次累加桶中每个元素的值，统计小于等于当前元素的元素个数
        for (int i = 1; i < countArr.length; i++) {
            countArr[i] += countArr[i - 1];
        }

        // 创建一个与原数组等长的辅助数组
        int[] sortedArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            // 找到元素 arr[i] 在数组中的下标
            int index = countArr[arr[i]] - 1;
            // 将 arr[i] 插入到辅助数组 sortedArr 的对应位置
            sortedArr[index] = arr[i];
            // 每插入一个元素，对应的桶的元素值减一
            countArr[arr[i]]--;
        }

        // 将排过序的辅助数组中的元素拷贝回原数组中
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sortedArr[i];
        }
    }

}
