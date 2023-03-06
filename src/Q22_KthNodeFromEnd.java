import utils.ListNode;

/**
 * 链表中倒数第k个节点
 */
public class Q22_KthNodeFromEnd {
    public static ListNode twoPointer(ListNode head, int k) {
        if (null == head || k < 1) {    // 输入不合法: 可删除 null == head
            return null;
        }

        ListNode left = null;   // behind
        ListNode right = head;  // ahead

        // 左右间隔k
        while (right != null && k > 0) {
            right = right.next;
            --k;
        }

        if (k == 0) {       // 链表长度充足 >=k
            left = head;
            while (right != null) {
                right = right.next;
                left = left.next;
            }
        }

        return left;

        /* 与上相同
        // 左右间隔k
        for (int i = 0; i < k; i++) {
            if (right == null)      // 链表长度不足 必须先判断: head可能时空, 右移k次为空即指向尾后空是合法的应该返回head
                return null;
            right = right.next;
        }

        left = head;
        while (right != null) {
            right = right.next;
            left = left.next;
        }

        return left;
         */
    }
}
