package utils;

/**
 * 带指父结点的二叉树
 */
public final class PBinaryTreeNode extends BinaryTreeNode{
    public int val;
    public PBinaryTreeNode left;
    public PBinaryTreeNode right;
    public PBinaryTreeNode parent;

    /**
     * 构造
     */
    public PBinaryTreeNode() { }
    public PBinaryTreeNode(int val) { this.val = val; }
    public PBinaryTreeNode(int val, PBinaryTreeNode left, PBinaryTreeNode right, PBinaryTreeNode parent) { this.val = val; this.left = left; this.right = right; this.parent = parent;}

}
