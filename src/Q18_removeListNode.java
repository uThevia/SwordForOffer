import utils.ListNode;

public class Q18_removeListNode {

    /**
     * 链表删除节点
     * head的后继无node 复制删除法不出错,遍历法需要考虑
     */
    public static ListNode remove(ListNode head, ListNode node) {
        // 检查输入
        if (null == head) {
            return null;
        }
        if (null == node) {
            return head;
        }

        if (head == node) {     // 删头
            return head.next;
        }

        if (null == node.next) {   // node是尾结点
            // node是尾结点的正常情况
            // 遍历法 O(n)
            ListNode previous = head;
            while (null != previous && previous.next != node) { // 找到结点 或 找不到结点
                previous = previous.next;
            }
            if (null != previous) {
                previous.next = null;
            }
        }
        else {  // 正常情况 node的后继非空
            // 复制删除法 O(1)
            node.val = node.next.val;
            node.next = node.next.next;
        }
        return head;
    }
}
