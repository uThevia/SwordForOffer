package utils;

/**
 * 单向链表
 */
public final class ListNode {
    public ListNode next;
    public int val;

    public ListNode() {}
    public ListNode(int val) { this.val = val; }
    public ListNode(int val, ListNode next) { this.val = val; this.next = next;}
    public ListNode(ListNode node) { this.val = node.val; this.next = node.next;}

    @Override
    public String toString() {
        return Integer.toString(val);
    }
}
