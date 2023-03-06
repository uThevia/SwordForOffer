import utils.BinaryTreeNode;

/**
 * 判断一棵二叉树是不是对称的
 */
public class Q28_SymmetricalBinaryTree {
    /**
     * 递归法 O(n),O(n)
     * 前序遍历和对称的前序遍历 递归判断
     */
    public static boolean rescursion (BinaryTreeNode root) {
        return rescursionstep(root, root);
    }
    private static boolean rescursionstep(BinaryTreeNode root, BinaryTreeNode symmetricalRoot) {
        if (root == null && symmetricalRoot == null) {  // 空结点对称
            return true;
        }
        if (root == null || symmetricalRoot == null) {  // 空与非空不对称
            return false;
        }
        return root.val == symmetricalRoot.val && rescursionstep(root.left, symmetricalRoot.right) && rescursionstep(root.right, symmetricalRoot.left);
    }

    /* 原版
    public static boolean rescursion (BinaryTreeNode root) {
        if (root == null) { // 空树对称
            return true;
        }
        return rescursionstep(root, root);
    }

    private static boolean rescursionstep(BinaryTreeNode root, BinaryTreeNode symmetricalRoot) {
        if (root == null && symmetricalRoot == null) {  // 空结点对称
            return true;
        }
        if (root == null || symmetricalRoot == null) {  // 空与非空不对称
            return false;
        }

        boolean res;
        res = (root.val == symmetricalRoot.val);
        if (res) {
            res = res && rescursionstep(root.left, symmetricalRoot.right);
            res = res && rescursionstep(root.right, symmetricalRoot.left);
        }
        return res;
    }
     */

}
