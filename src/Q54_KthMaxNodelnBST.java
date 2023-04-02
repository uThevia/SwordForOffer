import utils.BinaryTreeNode;

/**
 * 二叉搜索树的第k大结点
 */
public class Q54_KthMaxNodelnBST {
    /**
     * 二叉搜索树的中序遍历为递增序列
     * 中序遍历倒序(右根左)为递减序列
     *
     * 对称的中序遍历 O(n), O(n)
     * 中序遍历倒序的第k个结点即为结果
     * 提前返回
     */
    public static int symmetricInOrder(BinaryTreeNode root, int k) {
        int[] res = new int[1]; // 结果
        symmetricInOrder(root, k, res);
        return res[0];
    }
    /**
     * @param root  当前结点
     * @param k     未遍历结点中的第k大结点 = 原k - 已遍历结点数
     * @param res   结果
     */
    private static void symmetricInOrder(BinaryTreeNode root, int k, int[] res) {
        if (root == null) {
            return;
        }
        // 对称的中序遍历
        symmetricInOrder(root.right, k, res);
        if (k == 0) {   // 提前返回
            return;
        }
        // 遍历当前结点
        --k;
        if (k == 0) {   // 当前结点是第k大结点
            res[0] = root.val;
        }
        symmetricInOrder(root.left, k, res);
    }
}
