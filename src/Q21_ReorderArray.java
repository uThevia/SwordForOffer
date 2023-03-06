import java.util.Arrays;
import java.util.function.Function;

/**
 * 数组调整顺序 使奇数位于偶数前面
 * 数组调整顺序的条件可以任意更替
 */
public class Q21_ReorderArray {

    /**
     * 快排中的切分法
     */
    public static int[] partition(int[] array) {
        int n;
        if (null == array || 0 == (n = array.length)) {
            return array;
        }

        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            // left右移直到遇到偶数
            while (left < n && array[left] % 2 == 1) {          // 注意不要忘处理左越界
                ++left;
            }
            // right左移直到遇到偶数
            while (right > left && array[right] % 2 == 0) {     // 注意不要忘处理右越界 包含在 右>左 中
                --right;
            }
            // 交换左右
            if (left < right) {
                swap(array, left, right);
            }
        }
        return  array;
    }

    private static void swap(int[] array, int left, int right) {
        int n;
        if (null == array || 0 == (n = array.length)) {
            return;
        }
        if (left < 0 || left >= n || right < 0 || right >= n) {
            return;
        }

        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }


    /**
     * 泛型数组调整顺序 条件为函数变量返回的逻辑值
     * @param array
     * @param function  Function<Integer, Boolean> 函数变量 输入Integer返回Boolean 数组左侧符合的条件
     * @return
     */
    public static<T> T[] partition(T[] array, Function<T, Boolean> function) {
        int n;
        if (null == array || 0 == (n = array.length)) {
            return array;
        }

        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            // left右移直到不符合左侧条件
            while (left < n && function.apply(array[left])) {  // 跳过符合左侧条件
                ++left;
            }
            // right左移直到符合左侧条件
            while (right > left && !function.apply(array[right])) {    // 跳过符合不左侧条件
                --right;
            }
            // 交换左右
            if (left < right) {
                swap(array, left, right);
            }
        }
        return  array;
    }
    private static<T> void swap(T[] array, int left, int right) {
        int n;
        if (null == array || 0 == (n = array.length)) {
            return;
        }
        if (left < 0 || left >= n || right < 0 || right >= n) {
            return;
        }

        T temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static void main(String[] args) {
        int[] intArray = {1,2,3,4,5,6};
        System.out.println(Arrays.toString(partition(intArray)));
        Integer[] IntegerArray = {1,2,3,4,5,6};
        System.out.println(Arrays.toString(partition(IntegerArray, num -> num % 2 == 1)));
    }


}
