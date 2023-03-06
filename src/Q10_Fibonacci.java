/**
 * 斐波那契数列
 */
public class Q10_Fibonacci {
    /** 递归 */
    public static long recursion(long n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return recursion(n - 1) + recursion(n - 2);
    }

    /** 循环 */
    public static long loop(long n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int fibNminusOne = 1;
        int fibNminusTwo = 0;
        int fibN = 0;
        for (int i = 2; i <= n; i++) {
            fibN = fibNminusOne + fibNminusTwo;
            fibNminusTwo = fibNminusOne;
            fibNminusOne = fibN;
        }
        return fibN;
    }

    /**
     * 矩阵
     * [F(n) F(n-1)]    [1  1 ]^(n-1)
     * |           |   =|     |
     * [F(n-1) F(n-2)]    [1  0 ]
     * */
    public static long matrix(long n) {
        return An(n-1)[0][0];       // 幂次n-1, f(n)=矩阵中第1个元素
    }

    /**
     * 二分法求矩阵幂A^n
     */
    public static long[][] An(long n) {
        final long[][] ZERO = { { 0, 0 }, { 0, 0 } };
        final long[][] UNIT = { { 1, 1 }, { 1, 0 } };

        long[][] matrix = {{},{}};
        if (n == 0) {
            matrix =  ZERO;
        }
        if (n == 1) {
            matrix = UNIT;
        }

        // 二分法求矩阵幂
        /* 也可位运算
        long bits = n ; // 剩余幂次
        while (bits > 1) { // 直到剩余幂次无需平方
            // 矩阵平方
            matrix = matrixSquare(matrix);
            bits >>= 1;
        }
        if (bits == 1) {
            matrix = matrixMultiply(matrix, UNIT);;
        }
        */
        long nums = (long) Math.log(n); // 二分法次数
        for (int i = 0; i < nums; i++) {
            matrix = matrixSquare(matrix);
        }
        if ((n & 1) == 0) { // n是奇数
            matrix = matrixMultiply(matrix, UNIT);;
        }
        return matrix;
    }
    /**
     * 矩阵相乘
     */
    public static long[][] matrixMultiply(long[][] A, long[][] B) {
        if (A == null || B == null || A.length == 0 || B.length == 0 ) {
            return null;
        }
        if (A[0].length != B.length){
            System.out.println("matrixMultiply:: 矩阵乘法维度不一致");
            return null;
        }

        int rows = A.length;
        int cols = B[0].length;
        long[][] C = new long[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                C[i][j] = 0;
                for (int k = 0; k < A[i].length; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }
    /** 矩阵平方 */
    public static long[][] matrixSquare(long[][] A) {
        return matrixMultiply(A, A);
    }


    /**
     * 公式
     * $f(n)=\frac{1}{\sqrt{5}}((\frac{1+\sqrt{5}}{2})^n-(\frac{1-\sqrt{5}}{2})^n)$
     * */
    public static long formula(long n) {
        return (long) (1 / Math.sqrt(5) * (Math.pow((1 + Math.sqrt(5) / 2), n) - Math.pow((1 - Math.sqrt(5) / 2), n)));
    }

}
