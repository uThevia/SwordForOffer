import java.util.HashMap;
import java.util.Map;

public class Q20_NumericString {

    

    public static void main(String[] args) {
        String s;
        s = "  ";
        System.out.println(s + " = " + isNumberByFSM(s));
        s = "0123";
        System.out.println(s + " = " + isNumberByFSM(s));
        s = "+12.e+3";
        System.out.println(s + " = " + isNumberByFSM(s));
        s = "  -.12E-34   ";
        System.out.println(s + " = " + isNumberByFSM(s));
        s = "-12.34";
        System.out.println(s + " = " + isNumberByFSM(s));
        s = "-12.34a";
        System.out.println(s + " = " + isNumberByFSM(s));
        s = "1a3.14";
        System.out.println(s + " = " + isNumberByFSM(s));
        s = "12e+5.4";
        System.out.println(s + " = " + isNumberByFSM(s));
        s = "1.2.3";
        System.out.println(s + " = " + isNumberByFSM(s));
        s = "+2.-3";
        System.out.println(s + " = " + isNumberByFSM(s));
        s = "+-5";
        System.out.println(s + " = " + isNumberByFSM(s));
    }

    /**
     * M1 正则法
     * java 正则pattern 不需要起始和结尾的 /^ $/
     */
    public static boolean isNumberByRegex(String s) {
        String pattern = " *([+-]?\\d+\\.|[+-]?\\d+\\.\\d+|[+-]?\\.\\d+|[+-]?\\d+)([eE][+-]?\\d+)? *";
        return s.matches(pattern);
    }

    static int index = 0;   // 指向String的指针下标: 取值[0:n], 取n时表示扫描完毕 且s[index]存在越界问题
    /**
     * M2 扫描法
     * 判断字符串是否是数值
     * 数值格式为 A.B[eE]C
     * A, B, C 称为 整数, 小数, 指数部分
     * 其中 A,B至少有1个, [eE]C可省略;
     * A,C前可加[+-]: A,C是整数(有无符号都行), B是无符号整数
     */
    public static boolean isNumberByScan(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }

        final int n = s.length();
        index = 0;  // 重置下标
        boolean res = false;    // 结果

        /*
        以下扫描过程都需要判断越界问题: index != n
        由于以下每个部分之间的关系是and, 所以越界时不用直接返回: return res;
         */

        // 扫描过空格
        while (index != n && s.charAt(index) == ' ') { // 书中代码没有该项测试
            ++index;
        }
        // 扫描过整数部分 A
        res = scanInteger(s);
        // 小数部分: 如果出现'.' 则 A.B || A. || .B = A || .B
        if (index != n && s.charAt(index) == '.') {
            ++index;    // 扫描过小数点
            // 小数点前后的整数可以省略但不能都省略: 有整数部分 || 有.小数部分
            res = res || scanUnsignedInteger(s);    // A || .B
        }
        // 指数部分: 如果出现[eE] 则 [eE]\d+
        if (index != n && (s.charAt(index) == 'e' || s.charAt(index) == 'E')) {
            ++index;    // 扫描过指数符号e
            // e后必须是整数
            res = res && scanInteger(s);
        }
        // 跳过结尾的空格
        while (index != n && s.charAt(index) == ' ') {
            ++index;
        }

        // 结尾是否有多余字符
        res = res && index == n;    // 没有多余字符应该index指向结尾后一位

        return res;
    }

    /**
     * 判断并扫描过整数
     * 整数格式为 [+|-]B, 其中B为无符号整数
     */
    private static boolean scanInteger(String s) {
        // 扫描过 [+|-]
        if (index != s.length() && (s.charAt(index) == '+' || s.charAt(index) == '-')) {
            ++index;
        }
        return scanUnsignedInteger(s);
    }

    /**
     * 判断并扫描过无符号整数
     */
    private static boolean scanUnsignedInteger(String s) {
        int before = index; // 起始位置
        // 扫描过 数字 0-9
        while (index != s.length() && s.charAt(index) >= '0' && s.charAt(index) <= '9') {
            ++index;
        }
        // 有扫描过才返回 true: index != before
        return index != before;
    }

    /**
     * M3 状态机法
     * 有限状态自动机 finite-state machine, FSM
     */
    public static boolean isNumberByFSM(String s) {
        initTransfer();

        int length = s.length();
        State state = State.STATE_INITIAL;

        for (int i = 0; i < length; i++) {
            CharType type = toCharType(s.charAt(i));
            if (!transfer.get(state).containsKey(type)) {
                return false;
            } else {
                state = transfer.get(state).get(type);
            }
        }
        return state == State.STATE_INTEGER || state == State.STATE_POINT || state == State.STATE_FRACTION || state == State.STATE_EXP_NUMBER || state == State.STATE_END;
    }

    //
    /**
     * 转移规则: 转移规则: 状态 -字符类型-> 状态
     */
    static Map<State, Map<CharType, State>> transfer = new HashMap<State, Map<CharType, State>>();
    /**
     * 初始化转移规则
     */
    public static Map<State, Map<CharType, State>> initTransfer(){
        Map<CharType, State> initialMap = new HashMap<CharType, State>() {{     // 初状态
            put(CharType.CHAR_SPACE, State.STATE_INITIAL);
            put(CharType.CHAR_NUMBER, State.STATE_INTEGER);
            put(CharType.CHAR_POINT, State.STATE_POINT_WITHOUT_INT);
            put(CharType.CHAR_SIGN, State.STATE_INT_SIGN);
        }};
        transfer.put(State.STATE_INITIAL, initialMap);
        Map<CharType, State> intSignMap = new HashMap<CharType, State>() {{     // 状态(整数部分)符号 +-
            put(CharType.CHAR_NUMBER, State.STATE_INTEGER);
            put(CharType.CHAR_POINT, State.STATE_POINT_WITHOUT_INT);
        }};
        transfer.put(State.STATE_INT_SIGN, intSignMap);
        Map<CharType, State> integerMap = new HashMap<CharType, State>() {{     // 状态整数部分A
            put(CharType.CHAR_NUMBER, State.STATE_INTEGER);
            put(CharType.CHAR_EXP, State.STATE_EXP);
            put(CharType.CHAR_POINT, State.STATE_POINT);
            put(CharType.CHAR_SPACE, State.STATE_END);
        }};
        transfer.put(State.STATE_INTEGER, integerMap);
        Map<CharType, State> pointMap = new HashMap<CharType, State>() {{        // 状态(有整数部分的)小数点A.
            put(CharType.CHAR_NUMBER, State.STATE_FRACTION);
            put(CharType.CHAR_EXP, State.STATE_EXP);
            put(CharType.CHAR_SPACE, State.STATE_END);
        }};
        transfer.put(State.STATE_POINT, pointMap);
        Map<CharType, State> pointWithoutIntMap = new HashMap<CharType, State>() {{     // 状态无整数部分的小数点.
            put(CharType.CHAR_NUMBER, State.STATE_FRACTION);
        }};
        transfer.put(State.STATE_POINT_WITHOUT_INT, pointWithoutIntMap);
        Map<CharType, State> fractionMap = new HashMap<CharType, State>() {{      // 状态小数部分
            put(CharType.CHAR_NUMBER, State.STATE_FRACTION);
            put(CharType.CHAR_EXP, State.STATE_EXP);
            put(CharType.CHAR_SPACE, State.STATE_END);
        }};
        transfer.put(State.STATE_FRACTION, fractionMap);
        Map<CharType, State> expMap = new HashMap<CharType, State>() {{          // 状态指数字母 eE
            put(CharType.CHAR_NUMBER, State.STATE_EXP_NUMBER);
            put(CharType.CHAR_SIGN, State.STATE_EXP_SIGN);
        }};
        transfer.put(State.STATE_EXP, expMap);
        Map<CharType, State> expSignMap = new HashMap<CharType, State>() {{       // 状态指数符号 +-
            put(CharType.CHAR_NUMBER, State.STATE_EXP_NUMBER);
        }};
        transfer.put(State.STATE_EXP_SIGN, expSignMap);
        Map<CharType, State> expNumberMap = new HashMap<CharType, State>() {{     // 状态指数部分 C
            put(CharType.CHAR_NUMBER, State.STATE_EXP_NUMBER);
            put(CharType.CHAR_SPACE, State.STATE_END);
        }};
        transfer.put(State.STATE_EXP_NUMBER, expNumberMap);
        Map<CharType, State> endMap = new HashMap<CharType, State>() {{          // 状态结束
            put(CharType.CHAR_SPACE, State.STATE_END);
        }};
        transfer.put(State.STATE_END, endMap);
        return transfer;
    }


    /**
     * 识别字符类型
     */
    public static CharType toCharType(char ch) {
        if (ch >= '0' && ch <= '9') {
            return CharType.CHAR_NUMBER;
        }
        switch (ch) {
            case 'e':
            case 'E':
                return CharType.CHAR_EXP;
            case '.':
                return CharType.CHAR_POINT;
            case '+':
            case '-':
                return CharType.CHAR_SIGN;
            case ' ':
                return CharType.CHAR_SPACE;
            default:
                return CharType.CHAR_ILLEGAL;
        }
        /*
        return switch (ch) {
            case 'e', 'E' -> CharType.CHAR_EXP;
            case '.' -> CharType.CHAR_POINT;
            case '+', '-' -> CharType.CHAR_SIGN;
            case ' ' -> CharType.CHAR_SPACE;
            default -> CharType.CHAR_ILLEGAL;
        };
         */
    }

    /**
     * 状态
     */
    public enum State {
        STATE_INITIAL,
        STATE_INT_SIGN,
        STATE_INTEGER,
        STATE_POINT,
        STATE_POINT_WITHOUT_INT,
        STATE_FRACTION,
        STATE_EXP,
        STATE_EXP_SIGN,
        STATE_EXP_NUMBER,
        STATE_END
    }

    /**
     * 字符类型
     */
    public enum CharType {
        CHAR_NUMBER,
        CHAR_EXP,
        CHAR_POINT,
        CHAR_SIGN,
        CHAR_SPACE,
        CHAR_ILLEGAL
    }
}
