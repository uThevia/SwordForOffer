import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Q17 {

    /**
     * 获取
     */
    public static int[] getNumbers(int n) {
        final char[] BITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}; // 数字字符集

        List<String> res = new ArrayList<>();    // 结果
        char[] number = new char[n];        // 单个数: 最多n位

        // 依次产生长为i位的数字: i=1:n
        for (int i = 1; i <= n; i++)
            getDfs(0, i, BITS, number, res);

        // 将结果从List<StringBuilder>转为int[]
        int[] resInt = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            resInt[i] = Integer.parseInt(res.get(i));
        }
        return resInt;
    }
    /**
     * 生成长度为len的数字 从左往右正在确定第x位
     * 深度优先 递归回溯
     * @param x = [0:len-1] 取len是终止
     * @param len
     * @param BITS 数字字符集
     * @param number  单个数
     * @param res  结果
     */
    private static void getDfs(int x, int len, char[] BITS, char[] number, List<String> res) {
        if (x == len) {   // 终止条件: 已经生成长度len的数字 正在在经历第len+1位
            res.add(String.valueOf(number).substring(0, len));    // 添加数字: char[]转为string并取前len位
            return;
        }

        // 首位不能是0: 仅需在给第一位赋值时跳过0从1开始
        int startBit = (x == 0) ? 1 : 0;

        for (int i = startBit; i < BITS.length; i++) {
            number[x] = BITS[i];                   // 添加当前位
            getDfs(x + 1, len, BITS, number, res);                    // 添加下1位
            // number[x] = '';    // 回溯: 删除最后位
        }
    }

    /**
     * 打印
     * 实际上 数组,集合的最大长度也是int的数值范围 Integer.MAX_VALUE
     * 真正的避免大数越界问题应该是直接打印
     * @param n
     */
    public static void printNumbers(int n) {
        final char[] BITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}; // 数字字符集

        char[] number = new char[n];        // 单个数: 最多n位
        // 依次打印长为1位的数字: 单独处理以避免输出0
        for (int j = 1; j < BITS.length; j++)
            System.out.print(BITS[j] + ",");    // 必须是","而不是 char与char或char与int的运算(所有数学运算符)会将char变为int运算
        // 依次打印长为i位的数字: i=2:n
        for (int i = 2; i <= n; i++) {
            printDfs(0, i, BITS, number);   // 分数打印
        }
    }
    /**
     * 打印长度为len的数字 从左往右正在确定第x位
     * 深度优先 递归回溯
     * 每个数打印一次  dfs无法做到每位打印一次 所以还是存在大数限制(数的十进制长度不能超过int范围)
     * @param x = [0:len-1] 取len是终止
     * @param len
     * @param BITS 数字字符集
     * @param number  单个数
     */
    private static void printDfs(int x, int len, char[] BITS, char[] number) {
        if (x == len) {   // 终止条件: 已经生成长度len的数字 正在在经历第len+1位
            System.out.print(String.valueOf(number).substring(0, len) + ",");    // 打印单个数: char[]转为string并取前len位
                // 不想最后多打印一个','可以多加一个参数i并做 len == n && i == BITS.length 判断是否全部生成完毕
            return;
        }

        // 首位不能是0: 仅需在给第一位赋值时跳过0从1开始
        int startBit = (x == 0) ? 1 : 0;

        for (int i = startBit; i < BITS.length; i++) {
            number[x] = BITS[i];                   // 添加当前位
            printDfs(x + 1, len, BITS, number);                    // 添加下1位
            // 回溯
        }
    }




    public static void main(String[] args) {
        int n = 2;
        // System.out.println(Arrays.toString(getNumbers(n)));
        printNumbers(n);
    }
}
