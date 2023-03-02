package similar;

import java.util.ArrayList;

/**
 * 八皇后问题
 * 回溯法
 */
public class EightQueen {
    public static void main(String[] args) {
        int N = 8;
        System.out.println(new EightQueen().placeQueen(N));

    }

    /**
     * 求解N皇后问题
     * 给出所有解
     */
    private ArrayList<ArrayList<Integer>> placeQueen(int N) {
//        // 解的数量
//        int solutionNum = 0;
        // 所有解
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<ArrayList<Integer>>();
        // 一组解: 由于每行只有1个皇后, 只需存储每行皇后的y
        ArrayList<Integer> list = new ArrayList<Integer>();

        // 第一个皇后的y
        int first_y = 0;
        // 下个皇后的y的搜索起始值
        int next_y = 0;


        Queen queen;

        while (N != first_y) {
            // 已有皇后数
            int queenNum = list.size();

            // 结果为空 添加第一个皇后
            if (queenNum == 0) {
                queen = new Queen(0, first_y);
                list.add(queen.y);
                ++first_y;    // 下次为空需要从下个列开始求解
                next_y = 0;   // 重置下行
                continue;
            }

            // 已求得解
            if (queenNum == N) {
//                ++solutionNum;
                solutions.add(new ArrayList<Integer>(list));    // 必须是新建或复制(ArrayList<Integer>) list.clone 而不能是引用

                // 寻找下一组解
                next_y = list.get(queenNum - 1) + 1;    // 记录下个皇后的y
                list.remove(queenNum - 1);  // 退出最后一个皇后
                continue;
            }

            // 求下个皇后
            // 回溯: 当前行不存在合法新皇后
            int new_y = setY(N, list, next_y);
            if (-1 == new_y) {
                next_y = list.get(queenNum - 1) + 1;
                list.remove(queenNum - 1);
            } else {    // 当前行存在合法新皇后
                // 添加新皇后
                list.add(new_y);
                // 寻找下行皇后
                next_y = 0; // 下行需要重置起始y
            }
        }

        return solutions;
    }


    /**
     * 寻找下个皇后的合法位置
     * 用于剪枝
     * @param N
     * @param list 结果
     * @param start_y    新皇后的列下标搜索起始值
     * @return 合法新皇后的下标; -1 表示不存在合法新皇后
     */
    public int setY(int N, ArrayList<Integer> list, int start_y) {
        // start_y >= N 会返回-1

        // 新皇后所在行 = 已有皇后数 = 当前行
        int new_x = list.size();

        Queen new_queen;    // 新皇后

        boolean isNewQueenLegal = false;   // 新皇后合法标记
        Queen queen;        // 已有皇后
        // 遍历当前行的所有列
        for (int col = start_y; col < N; col++) {
            new_queen = new Queen(new_x, col);

            // 判断新皇后合法性
            isNewQueenLegal = true;
            for (int row = 0; row < new_x; row++) {
                queen = new Queen(row, list.get(row));
                // 新皇后不合法
                if (!(queen.isSafe(new_queen))) {
                    isNewQueenLegal = false;
                    break;
                }
            }
            // 找到合法新皇后
            if (isNewQueenLegal) {
                return new_queen.y;
            }
        }

        // 不存在合法新皇后
        return -1;
    }

    /**
     * 皇后类
     */
    class Queen {
        /**
         * 坐标
         */
        int x;
        int y;

        public Queen() {
        }

        public Queen(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * 判断皇后位置安全与否
         *
         * @param queen
         */
        public boolean isSafe(Queen queen) {
            return !(
                    this.x == queen.x                           // 同行
                    || this.y == queen.y                        // 同行
                    || this.x + this.y == queen.x + queen.y     // 同副对角线
                    || this.x - this.y == queen.x - queen.y     // 同主对角线
            );
        }
    }


}
