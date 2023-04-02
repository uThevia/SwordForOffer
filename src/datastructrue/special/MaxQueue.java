package datastructrue.special;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 最大值队列
 *  最大值O(1)
 * 单调队列实现
 */
public class MaxQueue {
    /**
     * queue    队列用以存储原队列
     * deque    单调队列用以存储最大值
     */
    private Queue<Integer> queue;
    private Deque<Integer> deque;

    public MaxQueue() {
        queue = new LinkedList<Integer>();
        deque = new LinkedList<Integer>();
    }

    public int max() {
        if (deque.isEmpty()) {
            return Integer.MIN_VALUE;   // 非法最大值
        }
        // 最大值是单调队列队首
        return deque.peekFirst();
    }

    public void push(int value) {
        // 插入push: 单调队列依次取出比插入值更小的元素 直到遇到更大元素
        while (!deque.isEmpty() && deque.peekLast() < value) {
            deque.pollLast();
        }
        deque.offerLast(value);
        queue.offer(value);
    }

    public int pop() {
        if (queue.isEmpty()) {
            return Integer.MIN_VALUE;   // 非法退出值
        }
        int res = queue.poll();
        // 删除pop: 如果元素是单调队列队首就删队首
        if (res == deque.peekFirst()) {
            deque.pollFirst();
        }
        return res;
    }
}
