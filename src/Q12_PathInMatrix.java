import java.util.List;
import java.util.Stack;

/**
 * 矩阵中的路径
 * 给定一个mxn二维字符矩阵board和一个字符串word。仅当word存在于网格中返回true。
 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中"相邻"单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
 */
public class Q12_PathInMatrix {

    /**
     * 回溯法: 深度优先 + 剪枝回溯
     * @param board 矩阵
     * @param word  单词
     */
    public static boolean backtracking(char[][] board, char[] word) {
        int m ;
        int n ;
        int wordLength;
        // 检查输入
        if (null == board || 0 == (m = board.length) || 0 == (n = board[0].length)){
            System.out.println("backtracking:: 输入board不合法");
            return false;
        }
        if (null == word || 0 == (wordLength = word.length)){
            System.out.println("backtracking:: 输入word不合法");
            return false;
        }


        int pathLength = 0; // 路径长度
        boolean[][] isInPath = new boolean[m][n];    // 路径标志

        // 遍历矩阵每个点为起始点
        for (int row = 0; row < m; row++){
            for (int col = 0; col < n; col++){
                if (stepByRecursion(board, m, n, word, wordLength, pathLength, isInPath, row, col))
                    return true;    // 逻辑判断找到一条就行
            }
        }

        return false;
    }

    /**
     * 递归检查可能空间树的每个结点: 当前结点符合且存在符合的子结点
     * @param board
     * @param m board行数
     * @param n board列数
     * @param word
     * @param wordLength word长度
     * @param pathLength 路径长度
     * @param isInPath   路径标志
     * @param row       当前点行号
     * @param col       当前点列号
     * @return
     */
    private static boolean stepByRecursion(char[][] board, int m, int n, char[] word, int wordLength, int pathLength, boolean[][] isInPath, int row, int col){
        if (wordLength == pathLength)      // 搜索完成
            return true;
        if (row < 0 || row >= m || col < 0 || col >= n ) // 点越界矩阵
            return false;

        // 当前点是否符合
        boolean isNow = (!isInPath[row][col]) && board[row][col] == word[pathLength];  // 未经历过 且 单字符相同
        if (!isNow)     // 当前点不符合直接返回false
            return false;

        // 将当前点加入路径
        ++pathLength;
        isInPath[row][col] = true;

        // 递归找下一个点
        // 是否存在符合的下个点
        boolean hasNext =
                stepByRecursion(board, m, n, word, wordLength, pathLength, isInPath, row - 1, col)       // 上
                || stepByRecursion(board, m, n, word, wordLength, pathLength, isInPath, row + 1, col)    // 下
                || stepByRecursion(board, m, n, word, wordLength, pathLength, isInPath, row, col - 1)     // 左
                || stepByRecursion(board, m, n, word, wordLength, pathLength, isInPath, row, col + 1)     // 右
        ;

        // 回溯
        if (!hasNext) { // 不存在符合的下个点
            // 将当前点删除
            --pathLength;
            isInPath[row][col] = false;

            // 不存在符合的下个点返回false
            return false;
        }

        // 只有当前点符合且存在符合的下个点才返回true
        /*
        // 存在路径
        boolean hasPath = isNow && hasNext;
        return hasPath;
         */
        return true;
    }

    public static void main(String[] args) {
        char[][] board = {
                {'a', 'b', 'c', 'e'}
                ,{'s', 'f', 'c', 's'}
                ,{'a', 'd', 'e', 'e'}
        };
        char[][] board2 = {
                {'a', 'b', 'd', 'e'}
                ,{'s', 'f', 'c', 's'}
                ,{'a', 'd', 'e', 'e'}
        };
        char[] word = "abcced".toCharArray();
        System.out.println(backtracking(board, word));
        System.out.println(backtracking(board2, word));
    }

//     想输出结果 有点难 没写完
//    private static void stepByRecursion(char[][] board, int m, int n, char[] word, int wordLength, int pathLength, boolean[][] isInPath, int row, int col, List<Point> path){
//        if (wordLength == pathLength)      // 搜索完成
//            return true;
//        if (row < 0 || row >= m || col < 0 || col >= n ) // 点越界矩阵
//            return false;
//
//        // 当前点是否符合
//        boolean isNow = (!isInPath[row][col]) && board[row][col] == word[pathLength];  // 未经历过 且 单字符相同
//        if (!isNow)     // 当前点不符合直接返回false
//            return false;
//
//        // 将当前点加入路径
//        ++pathLength;
//        isInPath[row][col] = true;
//
//        // 递归找下一个点
//        // 是否存在符合的下个点
//        boolean hasNext =
//                stepByRecursion(board, m, n, word, wordLength, pathLength, isInPath, row - 1, col)       // 上
//                        || stepByRecursion(board, m, n, word, wordLength, pathLength, isInPath, row + 1, col)    // 下
//                        || stepByRecursion(board, m, n, word, wordLength, pathLength, isInPath, row, col - 1)     // 左
//                        || stepByRecursion(board, m, n, word, wordLength, pathLength, isInPath, row, col + 1)     // 右
//                ;
//
//        // 回溯
//        if (!hasNext) { // 不存在符合的下个点
//            // 将当前点删除
//            --pathLength;
//            isInPath[row][col] = false;
//
//            // 不存在符合的下个点返回false
//            return false;
//        }
//
//        // 只有当前点符合且存在符合的下个点才返回true
//        /*
//        // 存在路径
//        boolean hasPath = isNow && hasNext;
//        return hasPath;
//         */
//        return true;
//    }
//
//
//    /**
//     * 坐标 (x, y)
//     */
//    public static class Point{
//        public static final int DIRECTION_NUM = 4;
//        public static final int TOP = 0;
//        public static final int DOWN = 1;
//        public static final int LEFT = 2;
//        public static final int RIGHT = 3;
//
//        public int x;
//        public int y;
//        /**
//         * 方向的访问标志逻辑数组
//         * 顺序: 上, 下, 左, 右
//         */
//        public boolean[] visited = new boolean[DIRECTION_NUM];
//
//        public Point() {}
//
//        public Point(int x, int y) {
//            this.x = x;
//            this.y = y;
//        }
//
//        public String toString() {
//            return  "(" + x + ", " + y + ")";  // getClass().getName()
//        }
//
//        public Point getTopPoint(){
//            return new Point(x - 1, y);
//        }
//
//        public Point getDownPoint(){
//            return new Point(x + 1, y);
//        }
//
//        public Point getLeftPoint(){
//            return new Point(x , y - 1);
//        }
//
//        public Point getRightPoint(){
//            return new Point(x, y + 1);
//        }
//
//        public boolean checkPointInBoundary(int m, int n) {
//            return x >= 0 && x < m && y >=0 && y < n;
//        }
//
//        public boolean checkPointInPath(boolean[][] isInPath) {
//            return isInPath[x][y];
//        }
//    }
//
//    private static boolean stepByStack(char[][] board, char[] word, int row, int col) {
//        int m = board.length;
//        int n = board[0].length;
//        int wordLength = word.length;
//
//        if (row < 0 || row >= m || col < 0 || col >= n ) // 起始点越界矩阵
//            return false;
//
//        int pathLength = 0;
//        boolean[][] isInPath = new boolean[m][n];
//
//        Stack<Point> stack = new Stack<>();
//        Point point = new Point(row, col);
//        stack.push(point);
//
//        Point temp;
//        while (wordLength != pathLength) {
//            temp = stack.pop();
//            // 当前点是否符合
//            boolean isNow = (!isInPath[row][col]) && board[row][col] == word[pathLength];  // 未经历过 且 单字符相同
//            if (!isNow) {     // 当前点不符合
//                --pathLength;
//                isInPath[row][col] = false;
//            }
//            else {  // 当前点符合
//
//            }
//
//
//        }
//
//
//
//
//        // 将当前点加入路径
//        ++pathLength;
//        isInPath[row][col] = true;
//
//        // 递归找下一个点
//        // 是否存在符合的下个点
//        boolean hasNext =
//                stepByRecursion(board, m, n, word, wordLength, pathLength, isInPath, row - 1, col)       // 上
//                        || stepByRecursion(board, m, n, word, wordLength, pathLength, isInPath, row + 1, col)    // 下
//                        || stepByRecursion(board, m, n, word, wordLength, pathLength, isInPath, row, col - 1)     // 左
//                        || stepByRecursion(board, m, n, word, wordLength, pathLength, isInPath, row, col + 1)     // 右
//                ;
//
//        // 回溯
//        if (!hasNext) { // 不存在符合的下个点
//            // 将当前点删除
//            --pathLength;
//            isInPath[row][col] = false;
//
//            // 不存在符合的下个点返回false
//            return false;
//        }
//
//        // 只有当前点符合且存在符合的下个点才返回true
//        /*
//        // 存在路径
//        boolean hasPath = isNow && hasNext;
//        return hasPath;
//         */
//        return true;
//    }

}
