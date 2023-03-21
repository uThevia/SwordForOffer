import java.util.Arrays;

/**
 * 把数组排成最小的数
 * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个
 *
 * 例如，输入数组{3,32,321}则打印出这3个数字能排成的最小数字321323
 */
public class Q45_SortArrayForMinNumber {
    /**
     * 比较直接使用字符串比较, 排序使用快排
     *
     * 排序规则:
     * 对于数m,n可拼接成mn或nm, 根据mn和nm的大小判断出m,n的排序大小(对应前后位置)
     * $$
     * \begin{cases}
     * \overline{mn} < \overline{nm}, \quad m小于n
     * \\ \overline{mn} = \overline{nm}, \quad m等于n
     * \\ \overline{mn} > \overline{nm}, \quad m大于n
     * \end{cases}
     * $$
     * 拼接时使用字符串避免整型越界, 且排序规则刚好和字符串字典序一致
     *
     * 这种排序运算具有自反性,对称性,传递性, 所以结果数字一定是最小的
     */
    public static String string(int[] nums) {
        // 检查输入
        int n;
        if (nums == null || (n = nums.length) == 0) {
            return "";
        }
        // 数字转为字符串
        String[] strings = new String[n];
        for (int i = 0; i < n; i++) {
            strings[i] = String.valueOf(nums[i]);
        }
        // 排序: 升序, 比较器为字符串的比较器(字典序), 比较规则为 xy ? yx
        // Arrays.sort(strings, (x, y) -> (x + y).compareTo(y + x));
        quickSort(strings);
        // 结果
        StringBuilder res = new StringBuilder();
        for (String string : strings) {
            res.append(string);
        }
        return res.toString();
    }

    public static void main(String[] args) {
        int[] nums = {3,32,321};
        System.out.println(string(nums));
    }

    /**
     * 快排
     */
    public static void quickSort(String[] strs) {
        quickSort(strs, 0, strs.length - 1);
    }
    private static void quickSort(String[] strs, int l, int r) {
        if(l >= r) {
            return;
        }
        int i = partition(strs, l ,r);
        quickSort(strs, l, i - 1);
        quickSort(strs, i + 1, r);
    }
    private static int partition(String[] strs, int l, int r) {
        // 切分比较方法是 xy ? yx
        String pivot = strs[l]; // 枢轴: 左端点
        int i = l;
        int j = r;
        String tmp = strs[i];   // 暂存枢轴
        while(i < j) {
            while(i < j && (strs[j] + pivot).compareTo(pivot + strs[j]) >= 0) {
                j--;
            }
            while(i < j && (strs[i] + pivot).compareTo(pivot + strs[i]) <= 0) {
                i++;
            }
            // swap(i,j)
            tmp = strs[i];
            strs[i] = strs[j];
            strs[j] = tmp;
        }
        // swap(i,l)
        strs[i] = strs[l];
        strs[l] = tmp;
        return i;
    }
}
