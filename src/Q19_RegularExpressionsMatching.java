/**
 * 正则表达式
 * 模式包含'.', '*'
 * 整个字符匹配
 */
public class Q19_RegularExpressionsMatching {
    /**
     * 动态规划
     * `'. '`表示任意1个字符 遇到直接将字符串和模式都前进1位即可
     * `'*'`表示前个字符重复任意次 相当于状态机的自环
     * 设s尾字符t, `*,+,?`前字符为c
     * 只存在2种可能: s匹配`c*` 或 `c*`多余
     * s删t(当t=c), 或 p删`c*`
     */
    /**
     * -------------------- 循环法 --------------------
     */
    public static boolean match(String string, String pattern) {
        if (string == null || null == pattern) {
            return false;
        }

        int n = string.length();
        int m = pattern.length();
        boolean[][] f = new boolean[n][m];  // f[n][m] 表示是否匹配前n长的string和前m长的pattern

        final char STAR = '*';

        // 空正则: f[i][0] = (0 == i); // 空正则仅当空字符串匹配
        f[0][0] = true;
        // f[+][0] = false;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < m; j++) {   // 只需考虑非空正则
                if (STAR != pattern.charAt(j - 1)) {        // 处理非'*'
                    if (isCharMatch(string, pattern, i - 1, j - 1))    // 输入i,j是字符串(字符数组)的下标; 越界在isCharMatch中处理
                    {
                        f[i][j] = f[i - 1][j - 1];
                    }
                } else {                                      // 处理'*'
                    // pattern尾部至少2位: "c*"
                    // 以下2种情况的关系是 or :
                    // 忽略"c*"
                    f[i][j] = f[i][j - 2];   // f[i][j] || f[i][j - 2];
                    // 匹配"c*": string尾减少1个c
                    if (isCharMatch(string, pattern, i - 1, j - 2)) {  // string至少有1位才能删尾; 越界在isCharMatch中处理
                        f[i][j] = f[i][j] || f[i - 1][j];
                    }
                }
            }
        }

        return f[n][m];
    }

    /**
     * 是否匹配 string[i], pattern[j]
     * 已考虑越界
     *
     * @param string
     * @param pattern
     * @param i       string[i]
     * @param j       pattern[j]
     */
    public static boolean isCharMatch(String string, String pattern, int i, int j) {
        if (string == null || null == pattern) {
            return false;
        }
        if (i < 0 || i >= string.length() || j < 0 || j >= pattern.length()) {
            return false;
        }

        // '.*'代表任意字符出现任意次 不能忽略
        final char POINT = '.';
        return POINT == pattern.charAt(j) || string.charAt(i) == pattern.charAt(j);
    }


    /** -------------------- 递归法 -------------------- */
    /**
     * 递归整串
     * 仅当字串空且模式串形如"c*" * k(包括空模式串)返回true
     */
    public static boolean matchByRecursion(String string, String pattern) {
        int n = string.length();
        int m = pattern.length();

        final char STAR = '*';

        // 区分字符串空与否
        if (n == 0) {   // # 空字符串
            // 空字符串匹配 仅有下标奇数位均为'*'
            if (m % 2 != 0)  // 长度为奇数必不匹配
            {
                return false;
            } else {  // 长度为偶数: 包括空正则串
                // 遍历奇数位 判断为'*'
                for (int i = 1; i < m; i += 2) {
                    if (pattern.charAt(i) != STAR) {
                        return false;
                    }
                }
                // 仅当字串空且模式串形如"c*" * k(包括空模式串)返回true
                return true;
            }
        } else {    // # 非空字符串
            // 区分正则串空与否
            if (m == 0) { // 空正则串
                return false;
            } else {      // 非空正则串
                // pattern[1]分是'*'和不是'*'
                if (m < 2 || pattern.charAt(1) != STAR) {   // 正则串不足2位 or 后一位不是'*'
                    // 匹配 string[0], pattern[0]
                    if (isCharMatch(string, pattern, 0, 0)) {
                        return matchByRecursion(string.substring(1), pattern.substring(1));
                    }
                    return false;
                } else {           // 后一位是'*'
                    // 如果string[0]和pattern[0]匹配
                    if (isCharMatch(string, pattern, 0, 0))
                        // 用"c*" || 不用"c*"
                    {
                        return matchByRecursion(string.substring(1), pattern) || matchByRecursion(string, pattern.substring(2));
                    }

                    // 如果string[0]和pattern[0]不匹配 直接忽略"c*"
                    return matchByRecursion(string, pattern.substring(2));
                }
            }
        }
    }

    /**
     * 递归下标法
     */
    public static boolean matchByRecursionWithStep(String string, String pattern) {
        if (string == null || null == pattern) {
            return false;
        }

        return matchByRecursionStep(string, pattern, 0, 0);
    }
    /**
     * 匹配 string[i:], pattern[j:]
     */
    private static boolean matchByRecursionStep(String string, String pattern, int i, int j) {
        int n = string.length();
        int m = pattern.length();

        if (i < 0 || i >= n || j < 0 || j >= m) // 越界
        {
            return false;
        }

        // 子串长度
        n = string.length() - i;
        m = pattern.length() - j;

        final char STAR = '*';

        // 区分字符串空与否
        if (n == 0) {   // # 空字符串
            // 空字符串匹配 仅有下标奇数位均为'*'
            if (m % 2 != 0)  // 长度为奇数必不匹配
            {
                return false;
            } else {  // 长度为偶数: 包括空正则串
                // 遍历奇数位 判断为'*'
                for (int k = 1; k < m; k += 2) {
                    if (pattern.charAt(k) != STAR) {
                        return false;
                    }
                }
                // 仅当字串空且模式串形如"c*" * k(包括空模式串)返回true
                return true;
            }
        } else {    // # 非空字符串
            // 区分正则串空与否
            if (m == 0) // 空正则串
            {
                return false;
            } else {      // 非空正则串
                // pattern[1]分是'*'和不是'*'
                if (m < 2 || pattern.charAt(j + 1) != STAR) {   // 正则串不足2位 or 后一位不是'*'
                    // 匹配 string[0], pattern[0]
                    if (isCharMatch(string, pattern, i, j)) {
                        return matchByRecursionStep(string, pattern, i + 1, j + 1);
                    }
                    return false;
                } else {           // 后一位是'*'
                    // 如果string[0]和pattern[0]匹配
                    if (isCharMatch(string, pattern, i, j))
                        // 用"c*" || 不用"c*"
                    {
                        return matchByRecursionStep(string, pattern, i + 1, j) || matchByRecursionStep(string, pattern, i, j + 2);
                    }
                    // 如果string[0]和pattern[0]不匹配 直接忽略"c*"
                    return matchByRecursionStep(string, pattern, i, j + 2);
                }
            }
        }
    }

}
