package algorithms.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 桶排序
 */
public class BucketSort {
    /**
     * 桶排序算法 - 对数组 arr 进行排序
     *
     * @param arr 待排序的整型数组
     */
    public static void bucketSort(int[] arr) {
        int bucketCount = 10; // 设置桶的数量
        List<Integer>[] buckets = new List[bucketCount]; // 创建桶

        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<Integer>();
        }

        // 将数组中的元素按照一定的规则分配到各个桶中
        for (int i = 0; i < arr.length; i++) {
            int bucketIndex = arr[i] / bucketCount;
            buckets[bucketIndex].add(arr[i]);
        }

        // 对每个桶中的元素进行排序
        for (int i = 0; i < bucketCount; i++) {
            Collections.sort(buckets[i]); // 使用JDK自带的排序算法进行排序
        }

        // 依次将每个桶中的元素输出到原序列中，即为有序序列
        int index = 0;
        for (int i = 0; i < bucketCount; i++) {
            for (int j = 0; j < buckets[i].size(); j++) {
                arr[index++] = buckets[i].get(j);
            }
        }
    }

}
