package datastructrue.special;

import static java.lang.Math.log;
import static java.lang.Math.max;
/**
 * 稀疏表ST
 * 稀疏表用来解决区间最值查询RMQ问题
 * 采用倍增的思想, 预处理(nlogn), 查询O(1)
 *
 * ST表能处理的运算: 符合结合律且可重复贡献的信息查询
 * 	最值, 最大公因数 最小公倍数, 位或 位与
 * **可重复贡献**: 二元运算f(x,y)满足 f(a,a)=a 。
 * 	可重复贡献的意义在于 可以对两个交集不为空的区间进行信息合并
 *
 * 预处理使用2维数组$f[a][b]$存储$\max\limits_{i\in[b,b+2^a-1]}(A_i)$
 * */
public class SparseTable {
    private int[] A;	// 数组
    private int[][] f;	// 预处理 存储分组
    private int[] log2OfI;	// 存储log2(i)
    /** 初始化 */
    public void SparseTable(int[] A) {
        // 保存原数据
        this.A = A;
        int n = A.length;
        int m = log2(n) + 1;	// log2(n)是分组数; +1因为第1行是原数据
        // 预处理
        // 1. 存储分组
        f = new int[m][n + 1];		// n+1是因为方便下标作为数值处理, 第0列不使用
        // 存储原数据: 第1行是原数据
        for (int i = 1; i <= n; ++i)
            f[0][i] = A[i-1];
        // 存储f[a][b]=max(A[b:b+2^a-1])
        for (int i = 1; i < m; ++i)
            for (int j = 1; j + (1 << i) - 1 <= n; ++j)		// 1<<i = 2^i
                f[i][j] = max(f[i - 1][j], f[i - 1][j + (1 << (i - 1))]);
        // 2. 存储log2(i)
        log2OfI = new int[n];
        for (int i = 2; i <= n; ++i)	// log2OfI[1]=0
            log2OfI[i] = log2OfI[i / 2] + 1;
    }
    /** 查询 */
    public int query(int l, int r) {
        int s = log2OfI[r - l + 1];
        return max(f[s][l], f[s][r - (1 << s) + 1]);
    }
    /** log2(x) */
    private static int log2(double x){
        return (int) (log(x) / log(2));
    }
}
