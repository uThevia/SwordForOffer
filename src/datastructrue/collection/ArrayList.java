package datastructrue.collection;

public class ArrayList<T> {
    private static final int INITIAL_CAPACITY = 10;
    private T[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        data = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int capacity) {
        T[] newData = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    public void add(T item) {
        if (size == data.length) {
            resize(2 * data.length);
        }
        data[size] = item;
        size++;
    }

    public void add(int index, T item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == data.length) {
            resize(2 * data.length);
        }
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = item;
        size++;
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T item = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        if (size > 0 && size == data.length / 4) {
            resize(data.length / 2);
        }
        return item;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return data[index];
    }

    public void set(int index, T item) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        data[index] = item;
    }

    public boolean contains(T item) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(item)) {
                return true;
            }
        }
        return false;
    }




    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();

        // Test adding elements
        list.add(1);
        list.add(2);
        list.add(3);

        assert list.get(0) == 1;
        assert list.get(1) == 2;
        assert list.get(2) == 3;

        // Test removing elements
        list.remove(1);

        assert list.get(0) == 1;
        assert list.get(1) == 3;

        try {
            list.get(2);
            assert false; // Should not reach this line
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }

        // Test setting elements
        list.set(1, 4);

        assert list.get(1) == 4;

        // Test checking if list contains an element
        assert list.contains(1);
        assert !list.contains(2);

        // Test getting the size of the list
        assert list.size() == 2;

        list.add(5);

        assert list.size() == 3;

        System.out.println("All tests passed!");
    }

}
