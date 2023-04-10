import utils.BinaryTreeNode;

/**
 * 二叉树的最近公共祖先
 */
public class Q68_LowestCommonAncestor {
    /**
     * 二叉搜索树的最近公共祖先
     */
    public static BinaryTreeNode lowestCommonAncestorForSearchTree(BinaryTreeNode root, BinaryTreeNode p, BinaryTreeNode q) {
        while(root != null ) {
            if(root.val < p.val && root.val < q.val) {          // p,q都在右子树
                root = root.right;
            }else if (root.val > p.val && root.val > q.val) {   // p,q都在左子树
                root = root.left;
            } else {
                break;
            }
        }
        return root;
    }
    
    /**
     * 二叉树的最近公共祖先
     */
    public static BinaryTreeNode lowestCommonAncestor(BinaryTreeNode root, BinaryTreeNode p, BinaryTreeNode q) {
        if (root == null || root == p || root == q) {return root;}
        BinaryTreeNode leftRes = lowestCommonAncestor(root.left, p, q);
        BinaryTreeNode rightRes = lowestCommonAncestor(root.right, p, q);
        return leftRes == null ? rightRes : (rightRes == null ? null : root);
    }
}
