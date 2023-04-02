import utils.BinaryTreeNode;
import static java.lang.Math.max;
import static java.lang.Math.abs;
/**
 * 二叉树的深度
 *
 * 树的深度等于最长路径的长度
 * 路径为根结点到叶结点的结点序列
 */
public class Q55_TreeDepth {
    /**
     * getDepth()	O(n), O(n)
     * 二叉树的深度
     * 后续遍历
     */
    public static int getDepth(BinaryTreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + max(getDepth(root.left), getDepth(root.right));
    }

    /**
     * isBanlance()	O(n), O(n)
     * 判断二叉树是否平衡二叉树: 平衡二叉树的任意结点的左右子树深度差不超过1
     * 后续遍历 / 自底向上递归
     * 不调用函数计算高度, 而是将判断平衡改为后序遍历(因为获取深度是后序遍历) 并将获取深度的逻辑直接写在其中, 所有结点只遍历1次
     */
    public static boolean isBanlance(BinaryTreeNode root) {
        return isBanlanceRecursion(root) != -1; // 不合法高度-1
    }
    public static int isBanlanceRecursion(BinaryTreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = isBanlanceRecursion(root.left);
        int rightDepth = isBanlanceRecursion(root.right);
        // 剪枝: 不合法高度-1
        if (leftDepth == -1 || rightDepth == -1) {
            return -1;
        }
        // 合法高度 iff 左右子树深度差不超过1
        return abs(leftDepth - rightDepth) < 2 ? max(leftDepth, rightDepth) : -1;
    }
}
