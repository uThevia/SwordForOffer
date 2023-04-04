import java.util.Arrays;

import static java.lang.Math.max;
/**
 * n个骰子的点数
 * n个骰子的点数和为s，输入n输出s的所有可能值的概率。
 * 返回类型是double[], double[i]为点数和的集合的第i个元素
 */
public class Q60_DicesProbability {
    /**
     * 动态规划	O(n^2)
     * 令f(n,s)为n个骰子和为s的概率
     * 逆向dp
     * f(n,s) = \frac{1}{6}\sum_{i=1}^6 f(n-1,s-i)
     * 正向dp
     * f(n+1,s+i) += \frac{1}{6}f(n,s), \quad i=1:6
     */
    /** 逆向dp */
    private static final int SIX = 6;   // 骰子点数个数 POINT_NUM
    public static double[] dp_backward(int n) {
        // 骰子点数从1-6变为0-5: 骰子和值与索引匹配, 每行dp结果索引都从0开始
        final int FIVE = SIX - 1;           // 变换后骰子点数最大值
        final double UNIT = 1.0 / SIX;      // 单位概率 1/6
        int length = FIVE * n + 1;          // s(n)=[0:5n]
        // 结果, 保存dp表上一行结果
        double[] res = new double[length];
        // 基例: 第1个骰子
        for (int j = 0; j < SIX; j++) {
            res[j] = UNIT;
        }
        for (int i = 2; i <= n; i++) {                  // i为第i个骰子: i=2:n
            for (int j = FIVE * i; j >= 0; j--) {      // j为前i个骰子的和: j=s(i)=[0,5i]; 倒序更新因为数组保存dp表上一行结果
                // f(i,j) = \frac{1}{6} \sum_{k=j-5}^j f(i-1,k)
                double sum = 0;
                for (int k = max(0, j - FIVE); k <= j; k++) {       // k为影响f(i,j)的f(i-1,k)下标: k=j-5:j; max(O,)保证不越界; 正序因为只读, 且简化越界判断
                    sum += UNIT * res[k];
                }
                res[j] = sum;
            }
        }
        return res;
    }
    /** 正向dp */
    public static double[] dp_forward(int n) {
        // 骰子点数从1-6变为0-5: 骰子和值与索引匹配, 每行dp结果索引都从0开始
        final int FIVE = SIX - 1;           // 变换后骰子点数最大值
        final double UNIT = 1.0 / SIX;      // 单位概率 1/6
        double[] res = new double[SIX];
        Arrays.fill(res, UNIT);
        for (int i = 2; i <= n; i++) {               // i为第i个骰子: i=2:n
            double[] temp = new double[FIVE * i + 1];
            for (int j = 0; j < res.length; j++) {    // j为前i-1个骰子的和: j=s(i)=[0,5(i-1)]
                // f(i,j+k) += \frac{1}{6} f(i-1,j), \quad k=0:5
                for (int k = 0; k < SIX; k++) {     // k为新和与旧和的差值: k=0:5
                    temp[j + k] += UNIT * res[j];
                }
            }
            res = temp;
        }
        return res;
    }

    public static void main(String[] args) {
        int n = 2;
        System.out.println(Arrays.toString(dp_backward(n)));
        System.out.println(Arrays.toString(dp_forward(n)));
    }
}
