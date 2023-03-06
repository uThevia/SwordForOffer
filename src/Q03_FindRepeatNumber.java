import java.util.HashSet;
import java.util.Set;

/**
 * 找到数组中重复的任意1个数字
 * 数组长度为n,数组中的数字为0~n-1
 */
public class Q03_FindRepeatNumber {
    /**
     * M1 集合
     * 添加直到重复 O(n) O(n)
     */
    public static int bySet(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();
        for (int num : nums) {
            if (!set.add(num))     // set.add(num) 重复时添加失败返回False
            {
                return num;
            }

//            // 等效方式
//            if (set.contains(num))
//                return num;
//            set.add(num);

        }
        return -1;
    }

    /**
     * M2 原地重排
     * 值和索引对应 O(n) O(1)
     */
    public static int inplaceRearrangement(int[] nums) {
        for (int i=0; i < nums.length; ) {
            // nums[i]=i
            if (nums[i] == i) {
                ++i;
                continue;
            }
            // nums[i]=nums[nums[i]]
            if (nums[i] == nums[nums[i]]) {
                return  nums[i];
            }
            // swap(nums[i], nums[nums[i]])
            int temp = nums[i];
            nums[i] = nums[nums[i]];
            nums[temp] = temp;
        }
        return -1;
    }


    /**
     * 不修改数组
     */

    /**
     * M3. 二分法
     *
     */
    public static int dichotomy(int[] nums) {

        int start = 0;
        int end = nums.length - 1;
        int mid = -1;

        while(start < end) {
            mid = (start + end) / 2;
            if (countValueNum(nums, start, mid) > (mid-start+1)){
                end = mid;
                continue;
            }else {
                start = mid + 1;
                continue;
            }
        }

//        // 若不确定是否一定有重复的 需要检验start
//        if (countValueNum(nums, start, start) <= 1)
//                return -1;

        return start;
    }
    private static int countValueNum(int[] nums, int left, int right) {
        int count = 0;
        for (int num : nums) {
            if (num >= left && num <=right) {
                count++;
            }
        }
        return count;
    }


    /**
     * 检查输入有效性
     */
    private static boolean checkInput(int[] nums){
        if (nums==null) {
            return false;
        }
        for (int j=0;j<nums.length;j++){
            if (nums[j]<1||nums[j]>=nums.length) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 0, 2, 5, 3};
        System.out.println(bySet(nums));
        System.out.println(inplaceRearrangement(nums));
        System.out.println(dichotomy(nums));
    }
}
