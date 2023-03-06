import utils.BinaryTreeNode;

/**
 * 树的子结构
 * 输入两棵二叉树A和B，判断B是不是A的子结构。
 * 约定空树不是任意一个树的子结构
 * B是A的子结构仅当A中有出现和B相同的结构和节点值。
 */
public class Q26_SubstructureInTree {
    /**
     * 递归法
     * 在A中前序遍历寻找与B相同值的结点R
     * @param target A
     * @param term B
     */
    public static boolean recursion(BinaryTreeNode target, BinaryTreeNode term) {
        boolean res = false;
        // 寻找对象树和寻找项树为空都返回空: A = null || B = null -> null
        // 前序遍历寻找与B相同值的结点R
        if (target != null && term != null) {
            if (target.val == term.val) {
                res = recursionStep(target, term);
            }
            if (!res)   // 结果为true就直接返回, 仅为false才继续寻找
            {
                res = recursion(target.left, term);
            }
            if (!res)   // 结果为true就直接返回, 仅为false才继续寻找
            {
                res = recursion(target.right, term);
            }
        }
        return res;
    }

    /**
     * R是否是B的同为根结点的超集树
     * 前序遍历递归判断左右
     * @param root R
     * @param term B
     * @return
     */
    public static boolean recursionStep(BinaryTreeNode root, BinaryTreeNode term) {
        boolean res = false;
        if (term == null)   // 查找项可以为空: B是R的同根子集树
        {
            res = true;
        } else if (root == null) {
            res = false;
        } else if (root.val != term.val) {
            res = false;
        } else {
            res = recursionStep(root.left, term.left) && recursionStep(root.right, term.right); // 递归查找左右
        }
        return res;
    }

}
