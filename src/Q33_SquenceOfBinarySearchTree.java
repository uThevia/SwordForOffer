import java.util.Stack;

/**
 * 判断一个整数数组是否某二叉搜索树的后序遍历序列。
 * 假设输入的数组的任意两个数字都互不相同。
 */
public class Q33_SquenceOfBinarySearchTree {
    /**
     * 1. 递归分治法
     * 递归处理 [左,右,根]
     * */
    public static boolean recursion(int[] postOrder) {
        int n;
        if (postOrder == null) {
            return false;
        }
        // [不需要]
        if ((n = postOrder.length) == 0) {
            return true;
        }
        return recursionStep(postOrder, 0, n - 1);
    }

    private static boolean recursionStep(int[] postOrder, int left, int right) {
        // 越界: [不需要] 其实私有方法被局限情况调用不用检查越界问题
        if (left == -1 && right == 0) { // 特殊情况: [0,m-1]中[0,m-2]都是右子树, 左子递归传入[0,-1]; 都是左子树right不会越界
            return true;
        }
        if (left < 0 || left >= postOrder.length || right < 0 || right >= postOrder.length) {
            return false;
        }
        // 基例
        if (left >= right) {    // 空节点和叶结点 合法
            return true;
        }
        if (left + 1 == right) {
            return postOrder[left] < postOrder[right];
        }

        int root = postOrder[right];    // 根结点
        int partitialIndex = left;      // 切分结点: 左边是左子树, 右边(包括自身)是右子树; [left, right]

        while (partitialIndex < right && postOrder[partitialIndex] < root) {
            ++partitialIndex;
        }
        // 判断右子树合法性: 不存在比根结点小的值
        for (int i = partitialIndex; i < right; i++) {
            if (postOrder[partitialIndex] < root) {
                return false;
            }
        }

        return recursionStep(postOrder, left, partitialIndex - 1) && recursionStep(postOrder, partitialIndex, right - 1);
    }


    /** 2. 循环栈法 */
    public static boolean loopWithStack(int[] postorder) {
        Stack<Integer> stack = new Stack<>();
        // (降序结点)的父结点 = 序列后蜀min
        int father = Integer.MAX_VALUE; // 初始化为最大值
        // 逆向遍历后序序列
        for(int i = postorder.length - 1; i >= 0; i--) {
            // 判断父结点条件: 前结点必须小于父结点
            if(postorder[i] > father) {
                return false;
            }
            // 更新父结点: 当是降序结点时
            while(!stack.isEmpty() && postorder[i] < stack.peek()) {    // stack.peek() = p[i+1:n-1]
                father = stack.pop();
            }
            stack.push(postorder[i]);   // 缓存遍历过的结点
        }
        return true;
    }

    /**
     * 3. 构建法
     * 如果后序序列合法 iff 可以依据其构建一个二叉搜索树BST
     */
    // 逆向遍历的当前下标 待插入结点: 用于判断序列遍历完毕; 由于使用的方法有回溯所以必须定义为类的全局变量
    static int end;
    public static boolean build(int[] postorder) {
        if (postorder == null || postorder.length == 1) {
            return true;
        }
        // 初始化下标变量为数组最后下标
        end = postorder.length - 1;
        return buildStep(postorder, Integer.MIN_VALUE, Integer.MAX_VALUE); // 序列遍历完毕
    }
    /**
     * 判断可插入点 是否可插入当前结点
     * 根据后序序列逆向遍历建立一个二叉搜索树BST
     * 遍历顺序: 根右左
     * 插入1个结点就记录其右左子为可插入点,
     * 插入结点时从右到左依次判断树中所有可插入点, 插入失败则取消该点的标记(即标记为不可插入点)
     * 实现通过 全局变量end记录待插入的结点, 插入结点后递归调用本函数依次判断右,左子的可插入点, 当
     * 该结点的左右子都不能插入时通过递归的回溯从其父结点的左子继续递归判断
     * @param postorder
     * @param min   可插入点的最小值
     * @param max   可插入点的最大值
     */
    private static boolean buildStep(int[] postorder, int min, int max) {
        // 序列遍历完毕
        if (end < 0) {
            return true;//空了，返回
        }
        // 结点是尾结点
        int node = postorder[end];
        // 结点不合法: 小于可插入点最小值 或 大于可插入点最大值
        if (node < min || node > max) {     // 不能有=号
            return false;
        }
        // 删除尾结点
        end--;
        // 每插入1个结点就记录其右左为可插入点,
        // 插入结点时从右到左依次判断树中所有可插入点, 插入失败则取消该点的标记(即标记为不可插入点)
        // 右子左子有一个可以就行
        return buildStep(postorder, node, max)     // 判断右子的可插入点
            || buildStep(postorder, min, node);    // 判断左子的可插入点
    }


    public static void main(String[] args) {
        int[] postOrde = {1,3,2,6,5};
        System.out.println(recursion(postOrde));
        System.out.println(loopWithStack(postOrde));
        System.out.println(build(postOrde));

        int[] postOrdeNot = {1,6,3,2,5};
        System.out.println(recursion(postOrdeNot));
        System.out.println(loopWithStack(postOrdeNot));
        System.out.println(build(postOrdeNot));
    }
}
