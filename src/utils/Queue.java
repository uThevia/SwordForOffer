package utils;

import java.util.NoSuchElementException;

public class Queue<T> {
    /**
     * 队列需要保存
     * 首结点, 尾结点, 大小
     */
    private Node<T> first;
    private Node<T> last;
    private int size;

    /**
     * 结点
     */
    private static class Node<T> {
        T item;
        Node<T> next;

        Node(T item) {
            this.item = item;
            this.next = null;
        }
    }

    public Queue() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(T item) {
        Node<T> oldLast = last;
        last = new Node<T>(item);
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            // throw new NoSuchElementException("Queue underflow");
            return null;
        }
        T item = first.item;
        first = first.next;
        size--;
        if (isEmpty()) {
            last = null;
        }
        return item;
    }

    public T peek() {
        if (isEmpty()) {
            // throw new NoSuchElementException("Queue underflow");
            return null;
        }
        return first.item;
    }


    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<Integer>();

        // Test enqueue and dequeue operations
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assert queue.dequeue() == 1;
        assert queue.dequeue() == 2;
        assert queue.dequeue() == 3;

        try {
            queue.dequeue();
            assert false; // Should not reach this line
        } catch (NoSuchElementException e) {
            // Expected exception
        }

        // Test peeking at the front element
        queue.enqueue(4);
        queue.enqueue(5);

        assert queue.peek() == 4;
        assert queue.peek() == 4; // Make sure peek doesn't remove element

        queue.dequeue();
        queue.dequeue();
        assert queue.peek() == null;

        // Test checking if queue is empty
        assert queue.isEmpty();

        queue.enqueue(6);

        assert !queue.isEmpty();

        // Test getting the size of the queue
        assert queue.size() == 1;

        queue.enqueue(7);
        queue.enqueue(8);

        assert queue.size() == 3;

        queue.dequeue();

        assert queue.size() == 2;

        System.out.println("All tests passed!");
    }
}

