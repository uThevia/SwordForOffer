import utils.BinaryTreeNode;

/**
 * 将二叉搜索树保持顺序转换成循环双向链表
 */
public class Q36_BinarySearchTreeToDoublyList {

    static BinaryTreeNode head; // 循环双向链表的头结点
    static BinaryTreeNode previous; // 中序遍历的前个结点
    public static BinaryTreeNode dfsInOrder(BinaryTreeNode root) {
        if (root == null) {
            return null;
        }
        /*
        // 重置静态全局变量
        head = null;
        previous = null;
         */
        dfs(root);
        // 使链表循环: 更新头尾; 此时 tail=pre
        head.left = previous;
        previous.right = head;
        return head;
    }

    /**
     * 深度优先 中序遍历
     */
    public static void dfs(BinaryTreeNode current) {
        if (current == null) {
            return;
        }
        // 中序遍历顺序: 左根右
        dfs(current.left);
        if (previous == null) {     // 没有赋值过previous 则遍历到最左叶
            head = current;     // head=最左叶
            // head.left = null;
        }else {     // 一般情况
            previous.right = current;
            current.left = previous;
        }
        previous = current;     // 当遍历到(current=)最右叶时, previous=tail
        dfs(current.right);
    }

    public static void main(String[] args) {

    }
}
