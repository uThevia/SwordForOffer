import java.util.Arrays;

/**
 * 构建乘积数组
 * 给定数组A[n], 生成数组B[n]使得B[i]等于A内元素除A[i]的乘积
 * 不能使用除法
 */
public class Q66_ConstuctProductArray {
    /**
     * 动态规划	O(n)
     * 令左连乘为L, 右连乘为R, 不包含当前元素
     */
    /** 用数组保存L,R	空间复杂度O(n) */
    public static int[] dp_array(int[] A) {
        int n = A.length;
        int[] B = new int[n];
        int[] L = new int[n];
        L[0] = 1;
        for (int i = 1; i < n; i++) {
            L[i] = L[i - 1] * A[i - 1];
        }
        int[] R = new int[n];
        R[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            R[i] = R[i + 1] * A[i + 1];
        }
        for (int i = 0; i < n; i++) {
            B[i] = L[i] * R[i];
        }
        return B;
    }
    /** 用变量保存L,R	空间复杂度O(1) */
    public static int[] dp_var(int[] A) {
        int n = A.length;
        int[] B = new int[n];
        B[0] = 1;	// L
        for (int i = 1; i < n; i++) {
            B[i] = A[i - 1] * B[i - 1];
        }
        int R = 1;
        for (int i = n - 1; i >= 0; i--) {
            B[i] = B[i] * R;
            R *= A[i];
        }
        return B;
    }
    
    /** 简化为1次循环 */
    public static int[] dp(int[] A) {
        int n = A.length;
        int[] B = new int[n];
        Arrays.fill(B, 1);
        int L = 1, R = 1;
        for(int i = 0; i < n; i++) {
            B[i] *= L;          // L[i]影响的B[i]
            B[n - 1 - i] *= R;  // R[i]影响的B[n-1-i]
            L *= A[i];          // L[i]
            R *= A[n - 1 - i];  // R[i]
        }
        return B;
    }
}
