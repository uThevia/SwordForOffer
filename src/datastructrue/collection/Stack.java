package datastructrue.collection;

import java.util.NoSuchElementException;

public class Stack<T> {
    private Node<T> top;
    private int size;

    private static class Node<T> {
        T item;
        Node<T> next;

        Node(T item) {
            this.item = item;
            this.next = null;
        }
    }

    public Stack() {
        top = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void push(T item) {
        Node<T> newNode = new Node<T>(item);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            // throw new NoSuchElementException("Stack underflow");
            return null;
        }
        T item = top.item;
        top = top.next;
        size--;
        return item;
    }

    public T peek() {
        if (isEmpty()) {
            // throw new NoSuchElementException("Stack underflow");
            return null;
        }
        return top.item;
    }


    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<Integer>();

        // Test pushing and popping elements
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assert stack.pop() == 3;
        assert stack.pop() == 2;
        assert stack.pop() == 1;

        try {
            stack.pop();
            assert false; // Should not reach this line
        } catch (NoSuchElementException e) {
            // Expected exception
        }

        // Test peeking at the top element
        stack.push(4);
        stack.push(5);

        assert stack.peek() == 5;
        assert stack.peek() == 5; // Make sure peek doesn't remove element

        stack.pop();
        stack.pop();
        assert stack.peek() == null;

        // Test checking if stack is empty
        assert stack.isEmpty();

        stack.push(6);

        assert !stack.isEmpty();

        // Test getting the size of the stack
        assert stack.size() == 1;

        stack.push(7);
        stack.push(8);

        assert stack.size() == 3;

        stack.pop();

        assert stack.size() == 2;

        System.out.println("All tests passed!");
    }


}
