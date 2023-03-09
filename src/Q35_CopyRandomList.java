import utils.RandomListNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 复制随机链表。
 * 随机链表中每个节点除了next指针还有一个random指针指向链表中的任意节点或null。
 */
public class Q35_CopyRandomList {
    /**
     * M1, 简单 O(n^2)
     */
    public static RandomListNode simple(RandomListNode head) {
        if (head == null) {
            return null;
        }

        RandomListNode headNew = new RandomListNode(head.val);

        RandomListNode nodeCopied = head.next;   // 被复制结点: 从非头开始
        RandomListNode nodePre = headNew;   // 前结点
        RandomListNode nodeNew;             // 新结点

        // 复制链表 next
        while (nodeCopied != null) {
            nodeNew = new RandomListNode(nodeCopied.val);
            nodePre.next = nodeNew;
            nodePre = nodeNew;
            nodeCopied = nodeCopied.next;
        }
        // 复制 random
        nodeCopied = head;
        nodeNew = headNew;
        while (nodeNew != null) {
            nodeNew.random = nodeCopied.random;
            nodeCopied = nodeCopied.next;
            nodeNew = nodeNew.next;
        }
        return headNew;
    }

    /**
     * M2, 哈希表 O(n), O(n)
     * 哈希表中存储复制的配对信息`<N,N'>`
     * 根据哈希表修改所有N'的random使原指向被复制链表结点N变为指向复制链表节点N'
     */
    public static RandomListNode hashtable(RandomListNode head) {

        RandomListNode headNew = new RandomListNode(head);

        RandomListNode nodeCopied = head.next;   // 被复制结点: 从非头开始
        RandomListNode nodePre = headNew;   // 前结点
        RandomListNode nodeNew;             // 新结点

        Map<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();
        map.put(head, headNew); // 头结点已处理需要放入

        // 复制链表 next
        while (nodeCopied != null) {
            nodeNew = new RandomListNode(nodeCopied);
            map.put(nodeCopied, nodeNew);
            nodePre.next = nodeNew;
            nodePre = nodeNew;
            nodeCopied = nodeCopied.next;
        }
        // 复制 random
        nodeCopied = head;
        nodeNew = headNew;
        while (nodeNew != null) {
            // 找到新node对应的原node
            nodeNew.random = (map.get(nodeCopied) == null) ? null : map.get(nodeCopied).random;
            nodeCopied = nodeCopied.next;
            nodeNew = nodeNew.next;
        }
        return headNew;
    }

    /**
     * M3, 原地复制 O(n)
     */
    public static RandomListNode inplaceCopy(RandomListNode head) {
        // 可以不判断输入空 但还是得在之后判断1次 且可读性更差
        if (head == null) {
            return null;
        }

        // 原地复制 把N’链接在N的后面
        //   完全复制N为N'(可不包括random), 修改 N.next = N'
        RandomListNode p = head;        // 被复制结点
        RandomListNode q;    // 新结点
        while (p != null) {
            q = new RandomListNode(p);  // 完全复制N’
            p.next = q;                 // 把N’链接在N的后面
            p = q.next;                 // 由于q是完全复制, q.next = 原链表的 p.next
        }
        RandomListNode headNew = head.next;
        // 修改N'的random
        // 	N'.random=n.random.next或空
        p = head;
        while (p != null) {
            q = p.next;
            q.random = (p.random == null) ? null : p.random.next; // random可空
            // 交替更新 先旧后新
            p = q.next;
        }

        // 修改N,N'的next
        // 	p,q指向N,N', p.next=q.next,p=p.next, q.next=p.next,q=q.next
        p = head;
        q = p.next;
        // 先更新旧1次使下循环内先新后旧 就不用在循环里判断q是尾结点p=null的情况
        p.next = q.next;
        p = p.next;
        while (p != null) {
            // 交替更新 先新后旧
            q.next = p.next;
            q = q.next;
            p.next = q.next;
            p = p.next;
        }

        return headNew;
    }

    public static void main(String[] args) {
        int n = 5;
        RandomListNode[] nodes = new RandomListNode[n];
        nodes[n - 1] = new RandomListNode(n - 1);
        for (int i = n - 2; i >= 0; i--) {
            nodes[i] = new RandomListNode(i, nodes[i + 1], null);
        }

        for (int i = n - 1; i >= 0; i--) {
            nodes[i].random = nodes[n - 1 - i];
        }
        nodes[1].random = nodes[1];
        nodes[3].random = null;

        RandomListNode head = nodes[0];

        System.out.println(RandomListNode.deepToString(head));
        System.out.println(RandomListNode.deepToString(simple(head)));
        System.out.println(RandomListNode.deepToString(hashtable(head)));
        System.out.println(RandomListNode.deepToString(inplaceCopy(head)));
    }

}
