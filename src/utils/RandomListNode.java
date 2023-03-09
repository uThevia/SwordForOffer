package utils;

/**
 * 随机链表
 */
public class RandomListNode {
    public int val;
    public RandomListNode next = null;
    public RandomListNode random = null;

    public RandomListNode() {}
    public RandomListNode(int val) {
        this.val = val;
    }
    public RandomListNode(int val, RandomListNode next, RandomListNode random) {
        this.val = val;
        this.next = next;
        this.random = random;
    }
    public RandomListNode(RandomListNode node) {
        this.val = node.val;
        this.next = node.next;
        this.random = node.random;
    }

    public RandomListNode(int val, RandomListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }

    /**
     * 打印整个链表
     * 链表结点值数组
     * 链表结点的random结点的值数组
     * @return
     */
    public static String deepToString(RandomListNode head) {
        StringBuilder result = new StringBuilder();
        StringBuilder randomString = new StringBuilder();
        result.append("[");
        randomString.append("[");
        if (head != null) {
            RandomListNode node = head;
            result.append(node.val);
            randomString.append((node.random == null) ? -1 : node.random.val);

            while ((node = node.next) != null) {
                result.append(",");
                result.append(node.val);
                randomString.append(",");
                randomString.append((node.random == null) ? -1 : node.random.val);
            }
        }
        result.append("]");
        randomString.append("]");
        return result.toString() + "\n" + randomString.toString();
    }
}