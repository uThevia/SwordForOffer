/**
 * 递增矩阵查找元素是否存在
 */
public class Q04_FindNumberInTwoDArray {

    /**
     * Z字形查找
     * O(m+n)
     */
    public static boolean zigzag(int[][] matrix, int num) {
        int m = matrix.length;      // 行数
        int n = matrix[0].length;   // 列数

        // 当前坐标 (row, column)
        // 从右上角开始
        int row = 0;
        int column = n - 1;

        int temp = 0;
        while (row < m && column >= 0) {   // 未出界
            temp = matrix[row][column];
            if (temp == num)
                return true;
            else if (temp < num)
                row += 1;
            else
                column -= 1;
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = {{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
        int num = 6;
        System.out.println(zigzag(matrix, num));
    }
}
