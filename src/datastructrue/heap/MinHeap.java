package datastructrue.heap;

public class MinHeap<Item extends Comparable<Item>> {

    private Item[] data;
    private int count;
    private int capacity;

    public MinHeap(int capacity) {
        this.data = (Item[]) new Comparable[capacity + 1];
        this.count = 0;
        this.capacity = capacity;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void insert(Item item) {
        if (count >= capacity) {
            return;
        }
        data[++count] = item;
        shiftUp(count);
    }

    public Item extractMin() {
        if (count == 0) {
            return null;
        }
        Item min = data[1];
        swap(data, 1, count--);
        shiftDown(1);
        return min;
    }

    private void shiftUp(int k) {
        while (k > 1 && data[k].compareTo(data[k / 2]) < 0) {
            swap(data, k, k / 2);
            k /= 2;
        }
    }

    private void shiftDown(int k) {
        while (2 * k <= count) {
            int j = 2 * k;
            if (j + 1 <= count && data[j + 1].compareTo(data[j]) < 0) {
                j++;
            }
            if (data[k].compareTo(data[j]) <= 0) {
                break;
            }
            swap(data, k, j);
            k = j;
        }
    }

    private void swap(Item[] arr, int i, int j) {
        Item temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        MinHeap<Integer> minHeap = new MinHeap<>(10);
        int[] arr = {1, 3, 2, 6, 5, 7, 8, 9, 0, 4};
        for (int i = 0; i < arr.length; i++) {
            minHeap.insert(arr[i]);
        }
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.extractMin() + " ");
        }

        /*
        0 1 2 3 4 5 6 7 8 9
         */
    }
}

