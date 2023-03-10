package datastructrue;

public class HashSet<T> {
    private final static int DEFAULT_CAPACITY = 16;
    private final static double DEFAULT_LOAD_FACTOR = 0.75;

    private Entry<T>[] buckets;
    private int size;
    private double loadFactor;

    public HashSet() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashSet(int capacity, double loadFactor) {
        this.buckets = new Entry[capacity];
        this.size = 0;
        this.loadFactor = loadFactor;
    }

    public boolean add(T element) {
        int index = getIndex(element);
        Entry<T> head = buckets[index];
        while (head != null) {
            if (head.value.equals(element)) {
                return false;
            }
            head = head.next;
        }
        Entry<T> newEntry = new Entry<>(element);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
        if (size >= buckets.length * loadFactor) {
            resize();
        }
        return true;
    }

    public boolean remove(T element) {
        int index = getIndex(element);
        Entry<T> head = buckets[index];
        Entry<T> prev = null;
        while (head != null) {
            if (head.value.equals(element)) {
                if (prev == null) {
                    buckets[index] = head.next;
                } else {
                    prev.next = head.next;
                }
                size--;
                return true;
            }
            prev = head;
            head = head.next;
        }
        return false;
    }

    public boolean contains(T element) {
        int index = getIndex(element);
        Entry<T> head = buckets[index];
        while (head != null) {
            if (head.value.equals(element)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    private int getIndex(T element) {
        int hash = element.hashCode();
        return (hash & 0x7FFFFFFF) % buckets.length;
    }

    private void resize() {
        int newCapacity = buckets.length * 2;
        Entry<T>[] newBuckets = new Entry[newCapacity];
        for (Entry<T> head : buckets) {
            while (head != null) {
                Entry<T> next = head.next;
                int newIndex = (head.value.hashCode() & 0x7FFFFFFF) % newCapacity;
                head.next = newBuckets[newIndex];
                newBuckets[newIndex] = head;
                head = next;
            }
        }
        buckets = newBuckets;
    }

    private static class Entry<T> {
        T value;
        Entry<T> next;

        Entry(T value) {
            this.value = value;
            this.next = null;
        }
    }


    public static void main(String[] args) {

    }
}

