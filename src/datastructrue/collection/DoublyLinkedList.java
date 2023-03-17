package datastructrue.collection;

public class DoublyLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }

        public T getData() {
            return data;
        }
    }

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public void insertAtHead(T data) {
        Node<T> newNode = new Node<T>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
    }

    public void insertAtTail(T data) {
        Node<T> newNode = new Node<T>(data);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public boolean remove(T data) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.data.equals(data)) {
                if (currentNode == head) {
                    head = head.next;
                    if (head != null) {
                        head.prev = null;
                    } else {
                        tail = null;
                    }
                } else if (currentNode == tail) {
                    tail = tail.prev;
                    if (tail != null) {
                        tail.next = null;
                    } else {
                        head = null;
                    }
                } else {
                    currentNode.prev.next = currentNode.next;
                    currentNode.next.prev = currentNode.prev;
                }
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    public void printList() {
        Node<T> currentNode = head;
        while (currentNode != null) {
            System.out.print(currentNode.data + " ");
            currentNode = currentNode.next;
        }
        System.out.println();
    }

    public T get(int index) {
        Node<T> currentNode = head;
        int currentIndex = 0;
        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.next;
            currentIndex++;
        }
        if (currentNode != null) {
            return currentNode.getData();
        }
        return null;
    }

    public void set(int index, T data) {
        Node<T> currentNode = head;
        int currentIndex = 0;
        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.next;
            currentIndex++;
        }
        if (currentNode != null) {
            currentNode.data = data;
        }
    }

    public static void main(String[] args) {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insertAtHead(3);
        list.insertAtHead(2);
        list.insertAtHead(1);
        list.insertAtTail(4);
        System.out.print("Original list: ");
        list.printList();
        boolean removed = list.remove(2);
        if (removed) {
            System.out.print("List after removing 2: ");
            list.printList();
        } else {
            System.out.println("Failed to remove 2 from list.");
        }

        System.out.println("Value at index 1: " + list.get(1));
        list.set(1, 5);
        System.out.println("List after setting value at index 1 to 5: ");
        list.printList();
    }
}
