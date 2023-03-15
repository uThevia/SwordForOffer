package datastructrue.heap;


import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * 最大二叉堆
 *
 * heapifyUp 和 heapifyDown 方法用于维护堆属性。
 * heapifyUp 在元素插入堆后调用，确保其位置正确，
 * heapifyDown 在取出最大元素后调用，恢复堆属性。
 *
 * parent, leftChild, rightChild 是辅助方法，用于计算给定元素的父, 左子, 右子元素的索引
 *
 * add(E e)：将元素 e 添加到堆中。
 * findMax()：返回堆中的最大元素。
 * extractMax()：删除并返回堆中的最大元素。
 * siftUp(int k)：将索引为 k 的元素上移，以维护最大堆的性质。
 * siftDown(int k)：将索引为 k 的元素下移，以维护最大堆的性质。
 * swap(int i, int j)：交换索引为 i 和 j 的两个元素。
 */
public class MaxHeap<E extends Comparable<E>> {
    private ArrayList<E> data;

    public MaxHeap() {
        this.data = new ArrayList<>();
    }

    public MaxHeap(int capacity) {
        this.data = new ArrayList<>(capacity);
    }

    public MaxHeap(E[] arr) {
        this.data = new ArrayList<>(Arrays.asList(arr));
        for (int i = parent(arr.length - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public void add(E e) {
        data.add(e);
        siftUp(data.size() - 1);
    }

    public E findMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        return data.get(0);
    }

    public E extractMax() {
        E max = findMax();
        swap(0, data.size() - 1);
        data.remove(data.size() - 1);
        siftDown(0);
        return max;
    }

    private void siftUp(int k) {
        while (k > 0 && data.get(parent(k)).compareTo(data.get(k)) < 0) {
            swap(k, parent(k));
            k = parent(k);
        }
    }

    private void siftDown(int k) {
        while (leftChild(k) < data.size()) {
            int j = leftChild(k);
            if (j + 1 < data.size() && data.get(j + 1).compareTo(data.get(j)) > 0) {
                j = rightChild(k);
            }
            if (data.get(k).compareTo(data.get(j)) >= 0) {
                break;
            }
            swap(k, j);
            k = j;
        }
    }

    private void swap(int i, int j) {
        E temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }

    private int parent(int index) {
        if (index == 0) {
            throw new IllegalArgumentException("Index 0 doesn't have parent.");
        }
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return index * 2 + 1;
    }

    private int rightChild(int index) {
        return index * 2 + 2;
    }

    public static void main(String[] args) {
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        maxHeap.add(3);
        maxHeap.add(2);
        maxHeap.add(1);
        maxHeap.add(4);
        System.out.println(maxHeap.findMax()); // Output: 4
        System.out.println(maxHeap.extractMax()); // Output: 4
        System.out.println(maxHeap.extractMax()); // Output: 3
        System.out.println(maxHeap.extractMax()); // Output: 2
        System.out.println(maxHeap.extractMax()); // Output: 1
    }
}

