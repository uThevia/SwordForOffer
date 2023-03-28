import java.util.*;

/**
 * 第一个只出现一次的字符
 * 找出字符串中第一个只出现一次的字符
 * 找出字符流中第一个只出现一次的字符
 */
public class Q50_FirstNotRepeatingChar {
    /**
     * M 哈希表	O(n),O(|Σ|)
     * 用哈希表存储出现字符
     *
     * 区别在于遍历字符串存储哈希表后如何找到首个不重复字符
     * 1, 哈希表存储字符标志(或频数)
     * 	第二次遍历字符串直到标志为真(或频数1)
     * 2, 哈希表存储字符索引
     * 	重复字符的索引置为非法值-1
     * 	遍历哈希表直到非-1的最小值
     * 3, 哈希表存储字符标志, 队列顺序存储字符且延迟删除
     * 	遍历字符串时, 哈希表存储字符索引键值对,
     * 	并将字符存储到队列中: 未重复加入, 重复则不断弹出队首(延迟删除)
     * 	遍历结束后队首即为首个未重复字符
     * 4, 有序哈希表 LinkedHashMap
     * 	有序哈希表的键值对是按照插入顺序排序
     * 	哈希表存储字符标志
     * 方法4 有序哈希表的效率最高
     */
    /** 1, 哈希表存储字符标志(或频数) */
    public static char hashsetFlag(String str) {
        char[] chars = str.toCharArray();
        Map<Character,Boolean> dict = new HashMap<Character,Boolean>();
        for (char c: chars) {
            dict.put(c, !dict.containsKey(c));
        }
        for (char c: chars) {   // 第二次遍历字符串
            if (dict.get(c)) {
                return c;
            }
        }
        return '\0';    // 默认空值
    }
    /** 2, 哈希表存储字符索引 */
    public static char hashsetIndex(String str) {
        Map<Character,Integer> dict = new HashMap<Character,Integer>();
        int n = str.length();
        for (int i = 0; i < n; i++) {
            char c = str.charAt(i);
            if (!dict.containsKey(c)) {
                dict.put(c, i);
            } else {
                dict.put(c, -1);
            }
        }
        // 第二次遍历是遍历哈希表
        int index = n;
        for (Map.Entry< Character,Integer> entry : dict.entrySet()) {
            int pos = entry.getValue();
            if (pos != -1 && pos < index) {
                index = pos;
            }
        }
        return index == n ? '\0' : str.charAt(index);    // 默认空值
    }
    /** 3, 哈希表存储字符标志, 队列顺序存储字符且延迟删除 */
    public static char hashsetAndQueue(String str) {
        Map<Character,Boolean> dict = new HashMap<Character,Boolean>();
        Queue<Character> queue = new LinkedList<Character>();
        int n =str.length();
        for (int i = 0; i < n; i++) {
            char c = str.charAt(i);
            if (!dict.containsKey(c)) {
                dict.put(c, true);
                queue.offer(c);
            } else {
                dict.put(c, false);
                while (!queue.isEmpty() && !dict.get(queue.peek())) {   // dict.get(queue.peek()) == false
                    queue.poll();
                }
            }
        }
        return queue.isEmpty() ? '\0' : queue.peek();    // 默认空值
    }
    /** 4, 有序哈希表 */
    public static char orderedHashset(String str) {
        char[] chars = str.toCharArray();
        HashMap<Character,Boolean> dict = new LinkedHashMap<Character,Boolean>();
        for (char c: chars) {
            dict.put(c, !dict.containsKey(c));
        }
        for (Map.Entry< Character,Boolean> entry : dict.entrySet()) {   // 第二次遍历是遍历有序哈希表
            if (entry.getValue()) {
                return entry.getKey();
            }
        }
        return '\0';    // 默认空值
    }

    /**
     * 用数组实现哈希表
     * @param CHARSET_SIZE  字符集大小
     * @param startChar     起始字符
     */
    public static char hashsetImplementedWithArray(String str, int CHARSET_SIZE, char startChar) {
        char[] chars = str.toCharArray();
        int[] frequency = new int[CHARSET_SIZE];
        for (char c: chars) {
            ++frequency[c - startChar];
        }
        for (char c: chars) {
            if (frequency[c - startChar] == 1) {
                return c;
            }
        }
        return '\0';    // 默认空值
    }
    public static char hashsetImplementedWithArray(String str, int CHARSET_SIZE) {
        // char默认最小字符
        char startChar = Character.MIN_VALUE;
        return hashsetImplementedWithArray(str, CHARSET_SIZE, startChar);
    }
    public static char hashsetImplementedWithArray(String str) {
        // 字符集大小: 默认char类型的大小
        final int CHARSET_SIZE = 65536;
        return hashsetImplementedWithArray(str, CHARSET_SIZE);
    }
}
