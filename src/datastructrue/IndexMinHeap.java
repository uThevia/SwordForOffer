package datastructrue;

/**
 * 索引最小堆
 *
 * 使用了三个数组来维护索引堆，其中
 * data 数组存储元素的值，
 * indexes 数组存储元素在堆中的位置，
 * reverse 数组存储元素在 indexes 中的位置。具体来说，reverse[i] 表示元素 i 在 indexes 数组中的位置，若元素 i 不存在，则 reverse[i] 等于 0。
 *
 * 构造函数初始化数组并将 reverse 数组初始化为 0。
 * insert 方法将元素 item 插入到索引 i 的位置，通过 shiftUp 方法维护堆的性质。
 * extractMin 方法弹出堆顶元素，并通过 shiftDown 方法维护堆的性质。
 * extractMinIndex 方法弹出堆顶元素的索引，并通过 shiftDown 方法维护堆的性质。
 * getMin 方法返回堆顶元素的值。
 * isEmpty 方法检查堆是否为空。
 * contains 方法检查索引 i 是否在堆中。
 * getItem 方法返回索引 i 对应的元素的值。
 * change 方法将索引 i 对应的元素值修改为 newItem，并通过 shiftUp 和 shiftDown 方法维护堆的性质。
 *
 * shiftUp 方法用于维护向上的堆性质，即在插入元素时，将元素和其父节点比较，如果比父节点小则交换位置，并更新 reverse 数组。
 * shiftDown 方法用于维护向下的堆性质，即在弹出元素时，将元素和其左右子节点比较，如果比子节点大则交换位置，并更新 reverse 数组。
 * swap 方法用于交换 indexes 数组中的元素位置。
 */
public class IndexMinHeap {
    private int[] data;
    private int[] indexes;
    private int[] reverse;
    private int size;
    private int capacity;

    public IndexMinHeap(int capacity) {
        data = new int[capacity + 1];
        indexes = new int[capacity + 1];
        reverse = new int[capacity + 1];
        size = 0;
        this.capacity = capacity;
        for (int i = 0; i <= capacity; i++) {
            reverse[i] = 0;
        }
    }

    public void insert(int i, int item) {
        assert size + 1 <= capacity;
        assert i + 1 >= 1 && i + 1 <= capacity;

        i++;
        data[i] = item;
        indexes[size + 1] = i;
        reverse[i] = size + 1;
        size++;
        shiftUp(size);
    }

    public int extractMin() {
        assert size > 0;

        int min = data[indexes[1]];
        swap(indexes, 1, size);
        reverse[indexes[size]] = 0;
        size--;
        shiftDown(1);
        return min;
    }

    public int extractMinIndex() {
        assert size > 0;

        int minIndex = indexes[1] - 1;
        swap(indexes, 1, size);
        reverse[indexes[size]] = 0;
        size--;
        shiftDown(1);
        return minIndex;
    }

    public int getMin() {
        assert size > 0;

        return data[indexes[1]];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(int i) {
        assert i + 1 >= 1 && i + 1 <= capacity;

        return reverse[i + 1] != 0;
    }

    public int getItem(int i) {
        assert contains(i);

        return data[i + 1];
    }

    public void change(int i, int newItem) {
        assert contains(i);

        i++;
        data[i] = newItem;

        int j = reverse[i];
        shiftUp(j);
        shiftDown(j);
    }

    private void shiftUp(int k) {
        while (k > 1 && data[indexes[k / 2]] > data[indexes[k]]) {
            swap(indexes, k / 2, k);
            reverse[indexes[k / 2]] = k / 2;
            reverse[indexes[k]] = k;
            k /= 2;
        }
    }

    private void shiftDown(int k) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j + 1 <= size && data[indexes[j + 1]] < data[indexes[j]]) {
                j++;
            }
            if (data[indexes[k]] <= data[indexes[j]]) {
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

    public static void main(String[] args) {
        int[] arr = {5, 3, 6, 1, 8, 7, 2, 4};
        IndexMinHeap heap = new IndexMinHeap(arr.length);
        for (int i = 0; i < arr.length; i++) {
            heap.insert(i, arr[i]);
        }
        while (!heap.isEmpty()) {
            int index = heap.extractMinIndex();
            System.out.print(heap.getItem(index) + " ");
        }
        // Output: 1 2 3 4 5 6 7 8
    }
}
