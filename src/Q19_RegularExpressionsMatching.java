/**
 * 正则表达式 包含'.', '*'
 * 整个字符匹配
 */
public class Q19_RegularExpressionsMatching {
    public static boolean match(String string, String pattern) {
        if (string == null || null == pattern)
            return false;

        int n = string.length();
        int m = pattern.length();
        boolean[][] f = new boolean[n][m];  // f[n]f[m] 表示是否匹配前n长的string和前m长的string

        final char POINT = '.';
        final char STAR = '*';

        char stringJSub1;
        char pattenJSub1;
        char pattenJSub2;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 空正则, 非空正则
                if (0 == j){    // 空正则
                    f[i][j] = (0 == i); // 空正则时仅当空字符串时匹配
                }
                else {
                    stringJSub1 = string.charAt(j - 1);
                    pattenJSub1 = pattern.charAt(j - 1);
                    if (STAR != pattern.charAt(j - 1)) {
                        if (i > 0 && (POINT == pattenJSub1 || stringJSub1 == pattenJSub1))
                            f[i][j] = f[i - 1][j - 1];
                    }
                    else {
                        // 忽略'*'
                        if (j >= 2)
                            f[i][j] = f[i][j] || f[i][j - 2];

                        // string尾部减少1个'*'指示字符
                        pattenJSub2 = pattern.charAt(j - 2);
                        if (i >= 1 && j >= 2 && (POINT == pattenJSub1 || stringJSub1 == pattenJSub2)) {     // '.*'代表任意字符出现任意次 不能忽略
                            f[i][j] = f[i][j] || f[i - 1][j];
                        }
                    }

                }
            }
        }

        return f[n][m];
    }


//    public static boolean matchStep(String string, String pattern){
//
//    }


}
