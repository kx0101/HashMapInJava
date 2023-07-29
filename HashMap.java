public class HashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    private Entry<K, V>[] table;
    private int size;

    public HashMap() {
        table = new Entry[DEFAULT_CAPACITY];
        size = 0;
    }

    private static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not allowed");
        }

        if ((double) size / table.length >= LOAD_FACTOR_THRESHOLD) {
            resizeAndRehash();
        }

        int index = hash(key);
        Entry<K, V> newEntry = new Entry<>(key, value);

        if (table[index] == null) {
            table[index] = newEntry;
        } else {
            Entry<K, V> current = table[index];

            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }

                current = current.next;
            }
          
            current.next = newEntry;
        }

        size++;
    }

    public V get(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
          
            current = current.next;
        }

        return null;
    }

    public boolean containsKey(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
          
            current = current.next;
        }

        return false;
    }

    public void remove(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];
        Entry<K, V> prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }

                size--;
                return;
            }
          
            prev = current;
            current = current.next;
        }
    }

    private void resizeAndRehash() {
        int newCapacity = table.length * 2;
        Entry<K, V>[] newTable = new Entry[newCapacity];

        for (Entry<K, V> entry : table) {
            while (entry != null) {
                Entry<K, V> next = entry.next;
                int newIndex = Math.abs(entry.key.hashCode() % newCapacity);
              
                entry.next = newTable[newIndex];
                newTable[newIndex] = entry;
                entry = next;
            }
        }

        table = newTable;
    }

    public static void main(String[] args) {
        HashMap<String, Integer> hashMap = new HashMap<>();

        hashMap.put("apple", 1);
        hashMap.put("banana", 2);
        hashMap.put("orange", 3);
        hashMap.put("kiwi", 4);
        hashMap.put("grape", 5);

        System.out.println("HashMap size: " + hashMap.size());

        System.out.println("Value of 'banana': " + hashMap.get("banana"));
        System.out.println("Value of 'kiwi': " + hashMap.get("kiwi"));
        System.out.println("Value of 'watermelon': " + hashMap.get("watermelon"));

        System.out.println("Contains key 'orange': " + hashMap.containsKey("orange"));
        System.out.println("Contains key 'watermelon': " + hashMap.containsKey("watermelon"));

        hashMap.remove("kiwi");

        System.out.println("HashMap size after removal: " + hashMap.size());
    }
}
