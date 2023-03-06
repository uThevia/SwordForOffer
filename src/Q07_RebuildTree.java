import utils.BinaryTreeNode;

/**
 * 根据二叉树的前序遍历和中序遍历重建二叉树
 */
public class Q07_RebuildTree {

    /**
     * 递归分治
     * 前序第一个是根结点 在中序中找出根结点则左边是左子树右边是右子树
     *
     * @param preorder 前序遍历
     * @param inorder 中序遍历
     * @return 根结点
     */
    public static BinaryTreeNode rebuildTree(int[] preorder, int[] inorder) {
        // 检查输入
        if (preorder == null || inorder == null || preorder.length != inorder.length) {   // 长度=0不会有错
            System.out.println("rebuildTree:: 先序和中序序列长度不一致");
            return null;
        }
        BinaryTreeNode root = rebuildTree(preorder, inorder, 0, preorder.length-1, 0, inorder.length-1);
        return root;
    }

    private static BinaryTreeNode rebuildTree(int[] preorder, int[] inorder, int preLeft, int preRight, int inLeft, int inRight) {
        //检查输入
        if (preRight-preLeft != inRight-inLeft) {
            System.out.println("rebuildTree:: 递归中 先序和中序序列长度不一致");
            return null;
        }

        // 递归基本情况
        if (preLeft > preRight || inLeft > inRight)     // 结点为空
        {
            return null;
        }

        int rootVal = preorder[preLeft];
        BinaryTreeNode root = new BinaryTreeNode(rootVal);

        // 在中序遍历中找到根结点
        int rootIndex = inLeft;  // 中序中根结点下标
        while(inorder[rootIndex] != rootVal && rootIndex <= inRight) {
            ++rootIndex;
        }
        if (rootIndex > inRight) {    // 输入有误 中序中找不到根结点
            System.out.println("rebuildTree:: 递归中 中序中找不到根结点");
            return null;
        }
        int leftLength = rootIndex - inLeft;    // 左子树结点数
        root.left = rebuildTree(preorder, inorder, preLeft + 1, preLeft + leftLength, inLeft, rootIndex - 1);
        root.right = rebuildTree(preorder, inorder, preLeft + 1 + leftLength, preRight, rootIndex + 1, inRight);
        return root;
    }

    public static void main(String[] args) {
        int[] pre={1,2,4,7,3,5,6,8};
        int[] in = {4,7,2,1,5,3,8,6};

        BinaryTreeNode root = rebuildTree(pre,in);
        //验证
        System.out.println(BinaryTreeNode.preOrder(root).toString());
        System.out.println(BinaryTreeNode.inOrder(root).toString());
    }
}
