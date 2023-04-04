import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 递增数组中和为目标s的任意2个数字
 */
public class Q57_SumEqualS {
    public static int[] twoPointers(int[] nums, int target) {
        int i = 0, j = nums.length - 1;
        int sum;    // 双指针的和
        while(i < j) {
            sum = nums[i] + nums[j];
            if(sum < target) {
                i++;
            } else if(sum > target) {
                j--;
            } else {
                return new int[] { nums[i], nums[j] };
            }
        }
        return new int[0];  // 结果不存在
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 4, 7, 11};
        int target = 22;
        System.out.println(Arrays.toString(twoPointers(nums, target)));
        target = 11;
        System.out.println(Arrays.toString(twoPointers(nums, target)));

        target = 18;
        System.out.println(Arrays.deepToString(FindContinuousSequence.formula(target)));
        System.out.println(Arrays.deepToString(FindContinuousSequence.slidingWindow(target)));
    }
}


class FindContinuousSequence{
    /**
     * M1 枚举+公式	O(n)
     * 从每个数x开始利用等差数列求和公式求解一元二次方程求y
     */
    public static int[][] formula(int target) {
        List<int[]> res = new ArrayList<>();
        int i = 1;
        double j = 0;
        int iUpperBound = (target + 1) / 2;   // i的上界 \lceil{s/2}\rceil=(1+s)/2
        while(i < iUpperBound) {
            j = (-1 + Math.sqrt(1 + 4 * (2.0 * target + i * i - i))) / 2;   // 2.0使乘法结果转为double避免越界
            int jInt = (int) j;
            if(i < j && j == jInt) {
                int[] ans = new int[jInt - i + 1];
                for(int k = i; k <= jInt; k++) {
                    ans[k - i] = k;
                }
                res.add(ans);
            }
            i++;
        }
        return res.toArray(new int[res.size()][]);
    }

    /**
     * 双指针/滑动窗口	O(n)
     * 利用序列和和目标的关系来调整窗口
     */
    public static int[][] slidingWindow(int target) {
        List<int[]> res = new ArrayList<int[]>();
        for (int l = 1, r = 2, s = 3; l < r;) {
            if (s == target) {
                // 等于目标 增加结果 左端左移
                int[] ans = new int[r - l + 1];
                for (int i = l; i <= r; ++i) {
                    ans[i - l] = i;
                }
                res.add(ans);
                s -= l;
                l++;
            } else if (s < target) {
                // 小于目标 右端右移
                r++;
                s += r;
            } else {
                // 大于目标 左端左移
                s -= l;
                l++;
            }
        }
        return res.toArray(new int[res.size()][]);
    }
}
