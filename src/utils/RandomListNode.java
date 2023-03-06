package utils;

/**
 * 随机链表
 */
public class RandomListNode {
    public int label;
    public RandomListNode next = null;
    public RandomListNode random = null;

    public RandomListNode() {}
    public RandomListNode(int label) {
        this.label = label;
    }
    public RandomListNode(int label, RandomListNode next, RandomListNode random) {
        this.label = label;
        this.next = next;
        this.random = random;
    }
}