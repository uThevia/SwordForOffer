/**
 * 字符串转整数
 * 丢弃首部空格, 获取尽可能长的整数部分(包括符号位±), 省略之后多余字符
 * 非法输入: 首个非空格字符(包含空)不是整数字符时返回0
 * 越界处理: 整数数值超过int范围则返回MAX_VALUE
 */
public class Q67_StringTolnt {
    public static int stringTolnt(String s) {
        final int ILLEGAL_RESULT = 0;
        int n = s.length();
        int i = 0;  // 当前位
        // 跳过首部空格
        final char SPACE = ' ';
        while (i < n && s.charAt(++i) == SPACE);
        if (i == n) {   // 只有空字符
            return ILLEGAL_RESULT;
        }
        // 获取符号位
        int sign = 1;
        final char SIGN_NEGATIVE = '-';
        final char SIGN_POSITIVE = '+';
        if (s.charAt(i) == SIGN_NEGATIVE) {
            sign = -1;
            i++;
        } else if (s.charAt(i) == SIGN_POSITIVE) {
            sign = 1;
            i++;
        }
        // 获取数值部分
        int numeral = 0;
        final int BOUNDARY = Integer.MAX_VALUE / 10;    // 差1位越界的边界值
        final char ZERO = '0';
        final char NINE = '9';
        int j = i;
        for (j = i; j < n; j++) {
            char c = s.charAt(j);
            if (c < ZERO || c > NINE) break;
            // 越界处理: res * 10
            if (numeral > BOUNDARY || (numeral == BOUNDARY && c > '7')) {   // 新位是8或9
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            numeral = numeral * 10 + (c - ZERO);
        }
        if (j == i) {   // 没有数值部分
            return ILLEGAL_RESULT;
        }
        return sign * numeral;
    }
}
