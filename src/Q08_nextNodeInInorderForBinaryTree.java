import utils.PBinaryTreeNode;

/**
 * 二叉树的下—个结点 在中序序列中
 */
public class Q08_nextNodeInInorderForBinaryTree {
    /**
     * 给定二叉树一个结点 找出在中序遍历序列中的下个结点
     * @param node 当前结点
     * @return
     */
    public static PBinaryTreeNode nextNodeInInorder(PBinaryTreeNode node){
        if (node == null)   // 检查输入
        {
            return null;
        }
        if (node.right != null)     // 当前结点若有右节点则就是下个结点
        {
            return node.right;
        }
        // 当前结点若没有右节点
        // 不断遍历父结点直到当前结点是父结点的左子结点
        PBinaryTreeNode parent = node.parent;
        while (parent != null && parent.left != node) { // 直到结点是父结点的左子结点
            node = parent;
            parent = node.parent;
        }
        if (parent == null)     // 若不存在这样的父结点说明当前结点是中序序列的最后一个结点
        {
            return null;
        }
        // 下个结点是父结点的右子结点
        return parent.right;    // parent.right == null 返回正确
    }
}
