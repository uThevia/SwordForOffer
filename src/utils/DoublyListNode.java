package utils;

/**
 * 双向链表结点
 */
public class DoublyListNode {
    public int val;
    public DoublyListNode left;
    public DoublyListNode right;

    public DoublyListNode() {}
    public DoublyListNode(int val) {
        this.val = val;
    }
    public DoublyListNode(int val, DoublyListNode left, DoublyListNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
