import java.util.Stack;

/**
 * 逆序打印链表
 */
public class Q06_ReversePrint {
    public static class ListNode {
        int val = 0;
        ListNode next = null;

        public ListNode() {}
        public ListNode(int val) { this.val = val; }
    }

    /**
     * 栈
     */
    public static void byStack(ListNode listNode) {
        Stack<ListNode> stack = new Stack<ListNode>();

        while (listNode != null) {
            stack.push(listNode);
            listNode = listNode.next;
        }

        while (!stack.empty()) {
            System.out.println(stack.pop().val);
        }
    }

    /**
     * 递归
     */
    public static void byRecursive(ListNode listNode) {
        if (listNode == null)
            return;
        byRecursive(listNode.next);
        System.out.println(listNode.val);
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        ListNode tail = head;
        for (int i = 0; i < 10; i++){
            ListNode temp = new ListNode(i + 1);
            tail.next = temp;
            tail = temp;
        }

        byStack(head);
        byRecursive(head);

    }
}
