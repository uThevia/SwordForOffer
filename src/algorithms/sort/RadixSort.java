package algorithms.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 基数排序
 */
public class RadixSort {
    /**
     * 基数排序算法
     */
    final static int TEN = 10;
    public static void radixSort(int[] arr) {
        int maxDigit = getMaxDigit(arr); // 获取数组中最大数字的位数

        for (int digit = 1; digit <= maxDigit; digit++) {
            // 初始化 TEN 个桶，每个桶里存放的是数字字符为 i 的元素
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
        return (num / (int) Math.pow(TEN, digit - 1)) % TEN;
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
            max /= TEN;
            maxDigit++;
        }
        return maxDigit;
    }
    
    
    /** 基数排序 */
    public static int[] radixSort2(int[] arr) {
        if (arr == null || arr.length < 2) return arr;
        final int TEN = 10;
        int n = arr.length;
        // 找出最大值
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (max < arr[i]) max = arr[i];
        }
        // 计算最大值的位数
        int bitNum = 1;
        while ((max /= TEN) > 0) {
            bitNum++;
        }
        // 创建10个桶 对应1位10个数字
        ArrayList<LinkedList<Integer>> bucketList = new ArrayList<>(TEN);
        for (int i = 0; i < TEN; i++) {
            bucketList.add(new LinkedList<Integer>());
        }
        // 依次从低到高遍历所有位 分别进行排序
        for (int i = 1; i <= bitNum; i++) {
            // 将所有数加入桶
            for (int j = 0; j < n; j++) {
                // 获取数的倒数第i位
                int radix = (arr[j] / (int) Math.pow(TEN, i - 1)) % TEN;
                // 放进对应桶
                bucketList.get(radix).add(arr[j]);
            }
            // 合并所有桶的数回原数组
            int index = 0;
            for (int k = 0; k < TEN; k++) {
                for (Integer value : bucketList.get(k)) {
                    arr[index++] = value;
                }
                // 清光桶
                bucketList.get(k).clear();
            }
        }
        return arr;
    }
    
    public static void main(String[] args) {
        int[] array = {155, 314, 45, 233, 878, 4, 45}; // 待排序数组
        radixSort2(array);
        System.out.println(Arrays.toString(array));
    }

}
