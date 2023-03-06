import utils.ListNode;

/**
 * 反转链表
 */
public class Q24_ReverseList {
    public static ListNode reverse(ListNode head) {
        if (null == head)   // 空链表
        {
            return null;
        }
        /* 不需要
        if (null == head.next)  // 单节点
            return head;
         */

        ListNode node = head;
        ListNode previous = null;

        // 迭代修改 node.next = node.previous 直到尾结点
        ListNode next;   // 保存后继结点的指向
        while ((next = node.next) != null) { // 直到尾结点
            node.next = previous;

            node = next;
            previous = node;
        }

        return node;
    }

    /** 极简写法 但是循环多判断 复杂度更高 */
    public static ListNode reverse2(ListNode head) {
        ListNode tail = null;
        ListNode node = head;
        ListNode previous = null;

        // 迭代修改 node.next = node.previous 直到尾结点
        while (node != null) { // 直到尾结点
            ListNode next = node.next;   // 保存后继结点的指向
            if (next == null) {
                tail = node;
            }

            node.next = previous;

            node = next;
            previous = node;
        }

        return tail;    // 反转链表的头节点是原尾结点
    }
}
