package algorithms.sort;

import java.util.LinkedList;
import java.util.List;

/**
 * 基数排序
 */
public class RadixSort {
    /**
     * 基数排序算法
     */
    public static void radixSort(int[] arr) {
        int maxDigit = getMaxDigit(arr); // 获取数组中最大数字的位数

        for (int digit = 1; digit <= maxDigit; digit++) {
            // 初始化 10 个桶，每个桶里存放的是数字字符为 i 的元素
            List<Integer>[] buckets = new List[10];
            for (int i = 0; i < buckets.length; i++) {
                buckets[i] = new LinkedList<Integer>();
            }

            // 将每个数字按照当前位的大小放入对应的桶中
            for (int i = 0; i < arr.length; i++) {
                int digitNum = getDigitNum(arr[i], digit);
                buckets[digitNum].add(arr[i]);
            }

            // 将桶中的元素按照先进先出的原则重新放回原数组中
            int index = 0;
            for (int i = 0; i < buckets.length; i++) {
                for (int j = 0; j < buckets[i].size(); j++) {
                    arr[index++] = buckets[i].get(j);
                }
            }
        }
    }

    /**
     * 获取 num 的第 digit 位数值
     *
     * @param num   源数值
     * @param digit 位数
     * @return 数字 num 的第 digit 位数值
     */
    public static int getDigitNum(int num, int digit) {
        return (num / (int) Math.pow(10, digit - 1)) % 10;
    }

    /**
     * 获取数组 arr 中数值的最大位数
     *
     * @param arr 待求取位数的整型数组
     * @return 数组 arr 中数值的最大位数
     */
    public static int getMaxDigit(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        int maxDigit = 0;
        while (max > 0) {
            max /= 10;
            maxDigit++;
        }
        return maxDigit;
    }

}
