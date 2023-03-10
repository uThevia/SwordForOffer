package datastructrue;

import java.util.NoSuchElementException;

public class LinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addFirst(E item) {
        Node<E> newNode = new Node<>(item);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    public void addLast(E item) {
        Node<E> newNode = new Node<>(item);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public E removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        E item = head.item;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return item;
    }

    public E removeLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }
        E item = tail.item;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            Node<E> current = head;
            while (current.next != tail) {
                current = current.next;
            }
            current.next = null;
            tail = current;
        }
        size--;
        return item;
    }

    public E getFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        return head.item;
    }

    public E getLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }
        return tail.item;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E item) {
            this.item = item;
            this.next = null;
        }
    }

    public boolean contains(E item) {
        Node<E> current = head;
        while (current != null) {
            if (current.item.equals(item)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void set(int index, E item) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.item = item;
    }


    
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();

        assert list.isEmpty();

        list.addFirst(1);
        list.addFirst(2);
        list.addLast(3);

        assert list.size() == 3;
        assert list.getFirst() == 2;
        assert list.getLast() == 3;

        int removedElement = list.removeFirst();
        assert removedElement == 2;
        assert list.size() == 2;
        assert list.getFirst() == 1;

        removedElement = list.removeLast();
        assert removedElement == 3;
        assert list.size() == 1;
        assert list.getLast() == 1;

        list.addLast(4);
        assert list.size() == 2;
        assert list.getLast() == 4;

        assert list.contains(1);
        assert list.contains(4);
        assert !list.contains(2);

        System.out.println("All tests passed!");
    }

}
