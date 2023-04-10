import java.util.HashSet;
import java.util.Set;

/**
 * 扑克牌中的顺子
 * 从若干副扑克牌中随机抽5张牌，判断是否一个顺子
 * 2-10为数字本身, A为1(不可当成14), JQK为11:13，大小王为0可以看成任意数字。
 */
public class Q61_ContinousCards {
    /**
     * 集合法     O(n)
     * n张牌是顺子的充要条件是牌取[min:max](即无重复)且max-min=n-1
     * 考虑0则
     * n张牌是顺子的充要条件是牌无重复且max-min<n
     */
    public static boolean isStraight(int[] nums) {
        Set<Integer> repeat = new HashSet<>();	// 集合 判断重复
        int min = 14, max = 0;	// 最值
        for(int num : nums) {
            if(num == 0) {		// 跳过0
                continue;
            }
            if(repeat.contains(num)) {	// 若牌重复 提前返回false
                return false;
            }
            repeat.add(num); 			// 添加牌
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        return max - min < 5;
    }
    
    /**
     * 若考虑真实世界的顺子:
     * A可当成14, 顺子特例只有1种 TJQKA
     */
    public static boolean isStraightReal(int[] nums) {
        Set<Integer> repeat = new HashSet<>();	// 集合 判断重复
        int min = 14, max = 0;	// 最值
        int secondMin = 14;		// 保存第2小的数: 考虑 A=14
        for(int num : nums) {
            if(num == 0) {		// 跳过0
                continue;
            }
            if(repeat.contains(num)) {	// 若牌重复 提前返回false
                return false;
            }
            repeat.add(num); 			// 添加牌
            if (num < min) {
                secondMin = min;
                min = num;
            } else if(num < secondMin) {	// 已去重所以一定secondMin≠min
                secondMin = num;
            }
            max = Math.max(max, num);
        }
        // 考虑 A=14
        if (min == 1 && secondMin > 9) {	// 第2小>=T
            return true;
        }
        return max - min < 5;
    }
}
