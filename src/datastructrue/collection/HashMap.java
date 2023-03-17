package datastructrue.collection;

import java.util.ArrayList;

/**
 * 在这个实现中，我们使用一个数组来存储哈希表的桶，每个桶都是一个 ArrayList，用于存储相同哈希值的键值对。在 put 方法中，我们通过调用 getBucketIndex 方法计算键的哈希值并确定其在哪个桶中，然后遍历该桶中的所有键值对。如果找到了具有相同键的条目，则更新其值。如果没有找到具有相同键的条目，则将新的键值对添加到该桶中。如果哈希表的大小超过了桶的数量的两倍，则调整哈希表的大小。
 * 在 get 方法中，我们也使用 getBucketIndex 方法来确定存储键值对的桶，并遍历该桶中的所有条目，以查找具有相同键的条目。如果找到了该条目，则返回其值，否则返回 null。
 * 在 containsKey 方法中，我们执行与 get 方法类似的操作，但是只需检查是否存在具有相同键的条目，而不需要返回其值。
 * 在 remove 方法中，我们首先通过 getBucketIndex 方法确定存储键值对的桶，并遍历该桶中的所有条目，以查找具有相同键的条目。如果找到了该条目，则从桶中删除它，并减少哈希表的大小。
 * 最后，我们还提供了一个 size 方法，用于返回哈希表中键值对的数量。
 *
 * 需要注意的是，这只是一个非常简单的示例实现，仅用于展示哈希表的基本原理和实现。在实际应用中，我们需要考虑更多的因素，如哈希函数的质量、哈希表的负载因子、冲突解决策略等等。
 */
public class HashMap<K, V> {

    private ArrayList<Entry<K, V>>[] buckets;
    private int size;

    private static class Entry<K, V> {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    public HashMap() {
        this.buckets = new ArrayList[10];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        this.size = 0;
    }

    public void put(K key, V value) {
        int index = getBucketIndex(key);
        ArrayList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }

        bucket.add(new Entry<K, V>(key, value));
        size++;

        if (size > buckets.length * 2) {
            resize();
        }
    }

    public V get(K key) {
        int index = getBucketIndex(key);
        ArrayList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }

        return null;
    }

    public boolean containsKey(K key) {
        int index = getBucketIndex(key);
        ArrayList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    public void remove(K key) {
        int index = getBucketIndex(key);
        ArrayList<Entry<K, V>> bucket = buckets[index];

        for (int i = 0; i < bucket.size(); i++) {
            Entry<K, V> entry = bucket.get(i);
            if (entry.getKey().equals(key)) {
                bucket.remove(i);
                size--;
                return;
            }
        }
    }

    public int size() {
        return size;
    }

    private int getBucketIndex(K key) {
        int hashCode = key.hashCode();
        int index = hashCode % buckets.length;
        if (index < 0) {
            index += buckets.length;
        }
        return index;
    }

    private void resize() {
        ArrayList<Entry<K, V>>[] oldBuckets = buckets;
        buckets = new ArrayList[oldBuckets.length * 2];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        size = 0;

        for (ArrayList<Entry<K, V>> bucket : oldBuckets) {
            for (Entry<K, V> entry : bucket) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();

        // 添加键值对
        map.put("John", 25);
        map.put("Tom", 30);
        map.put("Lucy", 28);

        // 测试 get 方法
        assert map.get("John") == 25;
        assert map.get("Tom") == 30;
        assert map.get("Lucy") == 28;
        assert map.get("Peter") == null;

        // 测试 containsKey 方法
        assert map.containsKey("John") == true;
        assert map.containsKey("Peter") == false;

        // 测试 remove 方法
        map.remove("Tom");
        assert map.containsKey("Tom") == false;

        // 测试 size 方法
        assert map.size() == 2;

        System.out.println("All tests passed!");
    }

}

