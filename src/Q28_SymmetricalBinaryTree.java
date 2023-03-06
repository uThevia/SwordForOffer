import utils.BinaryTreeNode;

/**
 * 判断一棵二叉树是不是对称的
 */
public class Q28_SymmetricalBinaryTree {
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

}
