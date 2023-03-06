import utils.ListNode;

/**
 * 链表中环的入口结点
 */
public class Q23_EntryNodeInListLoop {
    public static ListNode twoPointer(ListNode head) {
        if (null == head) {
            return null;
        }

        ListNode left = head;
        ListNode right = head;

        // 1. 确定是否有环
        // 2个指针从头出发分别以步长1,2前进 直到重复则有环 且重复结点一定在环内
        do {
            // 左 步长1
            left = left.next;   // 左无需判断空: 因为一定是右先到尾后空
            // 右 步长2
            if (right.next == null) {   // 无环
                return null;
            }else {
                right = right.next.next;
            }
        } while (right != null && left != right);   // 有环 且 重复

        if (right == null) {    // 无环
            return null;
        }
        // 有环
        // 2. 确定环的结点数k
        int k = 0;  // 环的结点数: right从重复点出发 k自然从0开始 重点是重复点
        // 1个指针从重复点出发直到回来经历的结点数
        do {
            right = right.next;
            ++k;
        } while (left != right);

        // 3. 确定入口结点
        // 2个指针从头间隔k出发 直到重复则为环的入口结点
        left = head;
        right = head;
        for (int i = 0; i < k; i++) {
            right = right.next;
        }
        while (left != right) { // 先判断是因为head可能就是环入口
            left = left.next;
            right = right.next;
        }
        return left;
    }
}
