import utils.ListNode;

/**
 * 输入两个增序链表，合并链表并保持增序。
 */
public class Q25_MergeTwoSortedLists {
    /** 循环法 */
    public static ListNode mergeByLoop(ListNode head1, ListNode head2) {
        ListNode head = null;       // 记录下头结点
        ListNode node = null;       // 待插入结点
        ListNode tail = null;   // 尾结点

        // 双指针 指向2个链表待插入结点
        ListNode node1 = head1;
        ListNode node2 = head2;

        // 2个链表非空: 不断插入
        while (node1 != null && node2 != null) {
            // 比较双指针确定待插入结点
            if (node1.val < node2.val) {
                node = node1;
                node1 = node1.next;
            } else {
                node = node2;
                node2 = node2.next;
            }

            // 插入结点
            if (head == null) {
                head = node;
            } else {
                tail.next = node;
            }
            // 更新尾结点
            tail = node;
        }

        // 一个链表为空: 直接循环插入另一个链表
        if (node1 == null) {
            // 2个链表都为空只有在输入2个头都空时才能取到 也不会影响结果 head=null
            while (node2 != null) {
                node = node2;
                node2 = node2.next;

                // 插入结点
                if (head == null) {
                    head = node;
                } else {
                    tail.next = node;
                }
                // 更新尾结点
                tail = node;
            }
        }else {
            while (node1 != null) {
                node = node1;
                node1 = node1.next;

                // 插入结点
                if (head == null) {
                    head = node;
                } else {
                    tail.next = node;
                }
                // 更新尾结点
                tail = node;
            }
        }


        return head;
    }

    /** 递归法 */
    public static ListNode mergeByRecursion(ListNode head1, ListNode head2) {
        // 基例
        if (null == head1) {
            return head2;
        }
        if (null == head2) {
            return  head1;
        }

        ListNode head = null;
        if (head1.val < head2.val) {
            head = head1;   // 处理完链表1的头
            head.next = mergeByRecursion(head1.next, head2);    // 处理链表1的子链表
        } else {
            head = head2;   // 处理完链表1的头
            head.next = mergeByRecursion(head, head2.next);    // 处理链表1的子链表
        }

        return head;
    }
}
