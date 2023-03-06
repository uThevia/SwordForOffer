/**
 * 旋转数组的最小数字
 * **数组的旋转n次**指把元素向后移n次, 末尾元素倒序插入开头
 * 数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]]
 * 旋转数组由2个有序子数组组成, 最小元素就是分界线, 左>右
 */
public class Q11_MinInRotateArray {
    /**
     * 二分查找法
     * @param numbers 旋转数组
     * @return 旋转数组的最小数字
     */
    public static int binarySearch(int [] numbers){
        int length;
        if (null == numbers || 0 == (length = numbers.length)){
            System.out.println("BinarySearch: 输入数组为空或长度为0");
            return -1;
        }

        int left = 0;
        int right = length - 1;
        if (left == right || numbers[left] < numbers[right] )    // 数组是单元素 或 有序数组(旋转0或kn次)
        {
            return numbers[left];
        }

        int mid;
        while (left < right - 1) {     // 停止条件: left,right = 最大值,最小值
            mid = (left + right) / 2;

            // 特例 left,mid,right的值都相等 无法二分查找需要顺序查找
            if (numbers[left] == numbers[mid] && numbers[mid] == numbers[right]) {
                int result = numbers[left];
                for (int i = left + 1; i <= right; i++) {
                    if (numbers[i] < result) {
                        result = numbers[i];
                    }
                }
                return  result;
            }

            if (numbers[left] <= numbers[mid]) {
                left = mid;
            }else {
                right = mid;
            }
        }

        return numbers[right];
    }

    public static void main(String[] args) {
        int[] a = {3,4,5,1,2};
        int[] b = {1,0,1,1,1};
        int[] c = {1,1,1,0,1};
        System.out.println(binarySearch(a));
        System.out.println(binarySearch(b));
        System.out.println(binarySearch(b));
    }
}
