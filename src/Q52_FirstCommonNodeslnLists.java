import utils.ListNode;

/**
 * 找到两个链表的首个公共节点
 */
public class Q52_FirstCommonNodeslnLists {
    /**
     * M 双指针	O(m+n),O(1)
     * 2个指针pa,pb分别从链表A,B的头开始,
     * 每一步更新双指针指向下个结点,
     * 若为空则分别指向另个链表的头,
     * 直到指向同个节点或都为空
     */
    public static ListNode twoPinters(ListNode headA, ListNode headB) {
        ListNode pa = headA;
        ListNode pb = headB;
        while (pa != pb) {
            pa = (pa == null) ? headB : pa.next;
            pb = (pb == null) ? headA : pb.next;
        }
        return pa;
    }
}
