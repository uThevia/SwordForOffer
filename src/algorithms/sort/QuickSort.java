package algorithms.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 快速排序
 */
public class QuickSort {

    /**
     * 快速排序算法
     */
    public static void quickSort(int[] arr) {
        quickSortRecursion(arr, 0, arr.length - 1);
    }

    /**
     * 快速排序辅助方法
     * 对 arr 中索引从 left 到 right 的元素进行快速排序
     */
    private static void quickSortRecursion(int[] arr, int left, int right) {
        if (left < right) {
            // partition 方法返回主元素的索引
            int pivotIndex = partitionFill(arr, left, right);
            // 对主元素左边的子数组进行递归排序
            quickSortRecursion(arr, left, pivotIndex - 1);
            // 对主元素右边的子数组进行递归排序
            quickSortRecursion(arr, pivotIndex + 1, right);
        }
    }

    /**
     * 划分 partition 方法
     * 选定主元素，并将数组 arr 划分为左、中、右三个部分  <, pivot, >
     *
     * @param arr   待划分的整型数组
     * @param left  左边界
     * @param right 右边界
     * @return 主元素的索引
     */
    private static int partition(int[] arr, int left, int right) {
        // 选定主元素
        int pivot = arr[right];
        // 初始化左指针、右指针和主元素索引
        int i = left - 1;
        int j = right;

        // 循环直到左右指针相遇
        while (i < j) {
            // 从左边开始移动 i 指针，直到找到第一个大于等于主元素的位置
            while (i < j && arr[i] < pivot) {
                ++i;
            }
            // 从右边开始移动 j 指针，直到找到第一个小于等于主元素的位置
            while (i < j && arr[j] > pivot) {
                --j;
            }
            if (i < j) {
                // 交换左右指针所指位置的元素
                swap(arr, i, j);
            }
        }
        // 交换主元素与左右指针相遇的位置的元素
        swap(arr, i, right);

        // 返回主元素的索引
        return i;
    }



    /** --------------------- 切分 ---------------------*/
    /**
     * 切分基于二分法 根据条件将序列分成左右2部分
     * 根据支点数的大小关系将序列分为 <=, 支点, >
     * 支点数取法: 首位, 随机取3的中间值
     * 也有三分切分 将序列分为 <,=,> 需用到3指针
     * 切分应用于快排, 寻找第k大数(中位数,k个最小的数)
     */
    /**
     * M1 交换法
     * 双路快速排序 顺序法的效率改进
     * 双指针 分别找到左右首个不符合对应条件的元素 交换
     * 目标 <=, pivot, >
     * 非常严格 不能改动
     */
    private static int partitioSwap(int[] nums, int left, int right) {
        int pivot = nums[right];  // 支点
        // 双指针
        int i = left;
        int j = right;  // 必须包含支点
        while (i < j) {
            // 必须先动支点异侧指针 (朝向支点的指针)
            while (i < j && nums[i] < pivot) {
                ++i;
            }
            while (i < j && nums[j] >= pivot) {  // 必须包含=
                --j;
            }
            if (i < j) {    // 未达到终止条件
                swap(nums, i, j);
            }
        }
        swap(nums, i, right);   // 支点交换位置一定是异侧的先动指针
        return i;
    }

    /**
     * M2 挖坑法 / 填充法
     * 交换法的写法改进 不需要交换
     * 双指针 分别找到左右首个不符合对应条件的元素 复制到右左去
     * 目标   <=, pivot, >
     */
    private static int partitionFill(int[] nums, int left, int right) {
        int pivot = nums[right];  // 支点
        int i = left;
        int j = right;  // 必须包含支点
        while (i < j) {
            // 必须先动支点异侧指针 (朝向支点的指针)
            while (i < j && nums[i] <= pivot) {  // 必须包含=
                ++i;
            }
            nums[j] = nums[i];
            while (i < j && nums[j] > pivot) {
                --j;
            }
            nums[i] = nums[j];
        }
        nums[j] = pivot;    // 支点赋值位置一定是跟循环交替的 也是最初取的(同侧)指针j
        return j;
    }

    /**
     * M3 顺序法   最差方法
     * 双指针 左指针指向左部分后继, 右指针遍历数组(不包含支点)将符合左部分条件的交换到左侧去
     * 目标   <=, pivot, >
     * 交换次数远多于交换法 不推荐
     */
    private static int partitionOrder(int[] nums, int left, int right) {
        int pivot = nums[right];  // 支点
        int i = left;
        for (int j = left; j < right; j++) {    // 必须不包含支点 包含另一头
            if (nums[j] <= nums[i]) {   // 必须包含=
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, i, right);    // 支点交换位置一定是异侧指针
        return i;
    }


    private static void swap(int[] nums, int i , int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 随机切分
     * 随机选取支点
     * 本方法随机取1个点
     * 一半取3个点选中值
     */
    private int randomizedPartition(int[] nums, int left, int right) {
        // 随机获取下标 [left,right]
        int i = new Random().nextInt(right - left + 1) + left;
        // 将随机选取的支点更换到默认切分方法的支点位置
        swap(nums, right, i);
        return partition(nums, left, right);
    }
    
    
    public static void main(String[] args) {
        int[] array = {5, 1, 4, 2, 8}; // 待排序数组
        quickSort(array);
        System.out.println(Arrays.toString(array));
    }
}
