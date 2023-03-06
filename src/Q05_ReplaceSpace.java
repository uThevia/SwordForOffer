/**
 * 把字符串s中的每个空格替换成"%20"
 */
public class Q05_ReplaceSpace {

    // 空格转换的字符串
    public static final String REPLACE_SPACE = "%20";

    /**
     * Java中String不可变直接通过StringBuilder正序扫描替换
     */
    public static String byStringBuilder(String s) {
        StringBuilder res = new StringBuilder();
        for(char c : s.toCharArray()) {
            if (c == ' ') {
                res.append(REPLACE_SPACE);
            } else {
                res.append(c);
            }
        }
        return  res.toString();
    }

    /**
     * 统计个数并倒序替换 O(n)
     */
    public static String scanAndReverseReplace(String s) {
        // 转换成数组操作
        char[] oldString = s.toCharArray();
        char[] REPLACE_SPACE_STRING = REPLACE_SPACE.toCharArray();

        int count = 0;  //空格数
        for(char c : oldString) {
            if (c == ' ') {
                ++count;
            }
        }

        int replaceLength = REPLACE_SPACE_STRING.length;
        int oldLength = oldString.length;
        int newLength = oldLength + (replaceLength - 1) * count;
        char[] res = new char[newLength];
        for (int i = oldLength - 1, j = newLength - 1; i >= 0; i--, j--) {
            if (oldString[i] == ' '){
                for (int k = replaceLength - 1; k >= 0; k--) {
                    res[j--] = REPLACE_SPACE_STRING[k];
                }
                j++;    // 修正多移动了1位 上for与外套for的j--重复
            }
            else {
                res[j] = oldString[i];
            }
        }

        return String.valueOf(res);
        /**
         * char[].toString() // 调用Object.toString返回数组地址
         * Arrays.toString(char[]) // 输出字符数组
         * new String(char[]) // 等效 String.valueOf(char[]) 等效 String.join("", char[])
         */
    }

    public static void main(String[] args) {
        String s = "we are happy";
        System.out.println(s);
        System.out.println(byStringBuilder(s));
        System.out.println(scanAndReverseReplace(s));
    }
}
