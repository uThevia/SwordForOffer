import utils.BinaryTreeNode;

/**
 * 二叉树的镜像
 */
public class Q27_MirrorOfBinaryTree {
    /**
     * 后序遍历递归法
     */
    public static BinaryTreeNode recursion (BinaryTreeNode root) {
        if (root == null) {
            return null;
        }
        // 交换左右子
        BinaryTreeNode temp = root.left;
        root.left = recursion(root.right);
        root.right = recursion(root.left);
        return root;
    }

    /**
     * 前序遍历递归法
     */
    public static void recursionPre (BinaryTreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {   // 空节点或叶结点就不处理
            return;
        }
        // 交换左右子
        BinaryTreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        recursionPre(root.left);
        recursionPre(root.right);

        return;
    }
}
