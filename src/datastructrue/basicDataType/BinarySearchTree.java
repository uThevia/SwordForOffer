package datastructrue.basicDataType;

/**
 * 二叉搜索树
 */
public class BinarySearchTree {
    // 定义二叉树节点类
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    private TreeNode root; // 二叉树的根节点

    // 向二叉树中插入一个节点
    public void insert(int val) {
        root = insertNode(root, val);
    }

    // 递归实现向二叉树中插入一个节点
    private TreeNode insertNode(TreeNode node, int val) {
        if (node == null) { // 如果节点为空，则创建一个新节点
            node = new TreeNode(val);
            return node;
        }
        if (val < node.val) { // 如果插入值小于节点值，则插入到左子树中
            node.left = insertNode(node.left, val);
        } else if (val > node.val) { // 如果插入值大于节点值，则插入到右子树中
            node.right = insertNode(node.right, val);
        }
        return node;
    }

    // 在二叉树中查找一个节点是否存在
    public boolean search(int val) {
        return searchNode(root, val);
    }

    // 递归实现在二叉树中查找一个节点是否存在
    private boolean searchNode(TreeNode node, int val) {
        if (node == null) { // 如果节点为空，则节点不存在
            return false;
        }
        if (val == node.val) { // 如果节点值等于查找值，则节点存在
            return true;
        } else if (val < node.val) { // 如果查找值小于节点值，则继续在左子树中查找
            return searchNode(node.left, val);
        } else { // 如果查找值大于节点值，则继续在右子树中查找
            return searchNode(node.right, val);
        }
    }

    // 在二叉树中删除一个节点
    public void delete(int val) {
        root = deleteNode(root, val);
    }

    // 递归实现在二叉树中删除一个节点
    private TreeNode deleteNode(TreeNode node, int val) {
        if (node == null) { // 如果节点为空，则节点不存在，无需删除
            return null;
        }
        if (val < node.val) { // 如果要删除的值小于节点值，则继续在左子树中删除
            node.left = deleteNode(node.left, val);
        } else if (val > node.val) { // 如果要删除的值大于节点值，则继续在右子树中删除
            node.right = deleteNode(node.right, val);
        } else { // 如果要删除的值等于节点值，则找到要删除的节点
            if (node.left == null) { // 如果要删除的节点没有左子树，则直接返回右子树
                return node.right;
            } else if (node.right == null) { // 如果要删除的节点没有右子树，则直接返回左子树
                return node.left;
            }

            // 如果要删除的节点有左右子树，则找到右子树中最小的节点来替换要删除的节点
            TreeNode minNode = findMinNode(node.right);
            node.val = minNode.val; // 用右子树中最小的节点值替换要删除的节点值
            node.right = deleteNode(node.right, minNode.val); // 删除右子树中最小的节点
        }
        return node;
    }

    // 查找以某个节点为根的子树中最小的节点
    private TreeNode findMinNode(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // 测试程序
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(1);
        bst.insert(4);
        bst.insert(6);
        bst.insert(8);
        System.out.println(bst.search(5)); // 输出 true
        System.out.println(bst.search(2)); // 输出 false
        bst.delete(5);
        System.out.println(bst.search(5)); // 输出 false
    }
}
