package datastructrue;

/**
 * 索引最大堆
 * @param <E>   item
 */
public class IndexMaxHeap<E extends Comparable<E>> {
    private E[] data;           // 最大堆中的数据
    private int[] indexes;         // 索引数组，indexes[x] 表示索引为 x 的元素在 data 数组中的下标
    private int[] reverse;         // 反向索引，reverse[i] 表示索引 i 在 indexes 数组中的下标
    private int count;             // 当前堆中元素个数
    private int capacity;          // 堆的容量
    private int shift = 1;         // 偏移量，将索引从 1 开始

    public IndexMaxHeap(int capacity) {
        data = (E[]) new Comparable[capacity + 1];
        indexes = new int[capacity + 1];
        reverse = new int[capacity + 1];
        count = 0;
        this.capacity = capacity;
        for (int i = 0; i <= capacity; i++) {
            reverse[i] = 0;        // 反向索引初始值为 0
        }
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void insert(int i, E item) {
        assert count + 1 <= capacity;
        assert i + shift >= 1 && i + shift <= capacity;

        i += shift;     // 加上偏移量
        data[i] = item;
        indexes[count + 1] = i;
        reverse[i] = count + 1;
        count++;
        shiftUp(count);
    }

    public E extractMax() {
        assert count > 0;

        E ret = data[indexes[1]];
        swap(indexes, 1, count);
        reverse[indexes[1]] = 1;
        reverse[indexes[count]] = 0;
        count--;
        shiftDown(1);
        return ret;
    }

    public int extractMaxIndex() {
        assert count > 0;

        int ret = indexes[1] - shift;   // 减去偏移量
        swap(indexes, 1, count);
        reverse[indexes[1]] = 1;
        reverse[indexes[count]] = 0;
        count--;
        shiftDown(1);
        return ret;
    }

    public E getItem(int i) {
        assert i + shift >= 1 && i + shift <= capacity;

        return data[i + shift];
    }

    public void change(int i, E newItem) {
        assert i + shift >= 1 && i + shift <= capacity;

        i += shift;
        data[i] = newItem;
        int j = reverse[i];
        shiftUp(j);
        shiftDown(j);
    }

    private void shiftUp(int k) {
        while (k > 1 && data[indexes[k]].compareTo(data[indexes[k / 2]]) > 0) {
            swap(indexes, k, k / 2);
            reverse[indexes[k]] = k;
            reverse[indexes[k / 2]] = k / 2;
            k /= 2;
        }
    }

    private void shiftDown(int k) {
        while (2 * k <= count) {
            int j = 2 * k;
            if (j + 1 <= count && data[indexes[j + 1]].compareTo(data[indexes[j]]) > 0) {
                j++;
            }
            if (data[indexes[k]].compareTo(data[indexes[j]]) >= 0) {
                break;
            }
            swap(indexes, k, j);
            reverse[indexes[k]] = k;
            reverse[indexes[j]] = j;
            k = j;
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void swap(E[] arr, int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        IndexMaxHeap<Integer> indexMaxHeap = new IndexMaxHeap<>(10);
        int[] arr = {1, 3, 2, 6, 5, 7, 8, 9, 0, 4};
        for (int i = 0; i < arr.length; i++) {
            indexMaxHeap.insert(i, arr[i]);
        }
        while (!indexMaxHeap.isEmpty()) {
            int index = indexMaxHeap.extractMaxIndex();
            System.out.print(index + " ");
        }

        /*
        7 6 5 3 4 9 1 2 0 8
         */
    }

}

