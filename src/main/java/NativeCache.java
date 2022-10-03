import java.util.ArrayList;

public class NativeCache<T> {

    public final int size;

    private final EntryList<T>[] entries;
    private int count = 0;

    static class Entry<T> {
        String key;
        T value;
        int hits;

        public Entry(String key, T value, int hits) {
            this.key = key;
            this.value = value;
            this.hits = hits;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key='" + key + '\'' +
                    ", value=" + value +
                    ", hits=" + hits +
                    '}';
        }
    }

    //MARK: - Internal models

    private static class EntryList<T> {
        ArrayList<Entry<T>> values;

        EntryList() {
            this.values = new ArrayList<>();
        }

        void add(String key, T value) {
            values.add(new Entry<T>(key, value, 0));
        }

        void update(int index, String key, T value, int hits) {
            values.set(index, new Entry<T>(key, value, hits));
        }

        void remove(int index) {
            values.remove(index);
        }
    }

    private static class EntryPosition {
        final boolean exists;
        final int index;
        final int offset;

        EntryPosition(boolean exists, int index, int offset) {
            this.exists = exists;
            this.index = index;
            this.offset = offset;
        }
    }

    public NativeCache(int size) {
        this.size = size;
        this.entries = new EntryList[size];
        for (int i = 0; i < size; i++) {
            entries[i] = new EntryList();
        }
    }

    //MARK: - Public API

    public int getCount() {
        return count;
    }

    //Returns cache entry without incrementing hits
    public Entry<T> inspectEntry(String key) {
        EntryPosition entryInfo = positionFor(key);

        if (!entryInfo.exists) {
            return null;
        }

        return entries[entryInfo.index].values.get(entryInfo.offset);
    }

    //Returns cache entry and increments hits
    public Entry<T> getEntry(String key) {
        Entry<T> entry = inspectEntry(key);

        if (entry == null) {
            return null;
        }

        entry.hits++;

        return entry;
    }

    //Returns cache value and increments hits
    public T getValue(String key) {
        Entry<T> entry = getEntry(key);

        if (entry == null) {
            return null;
        }

        return entry.value;
    }

    public void put(String key, T value) {
        if (count == size) {
            removeMinHitsElement();
            count--;
        }

        EntryPosition entryInfo = positionFor(key);

        EntryList<T> entryList = entries[entryInfo.index];
        if (entryInfo.exists) {
            entryList.update(entryInfo.offset, key, value, entryList.values.get(entryInfo.offset).hits);
        } else {
            count++;
            entryList.add(key, value);
        }
    }

    public boolean remove(String value) {
        EntryPosition entryInfo = positionFor(value);

        if (!entryInfo.exists) {
            return false;
        }

        count--;

        EntryList<T> entryList = entries[entryInfo.index];
        entryList.remove(entryInfo.offset);

        return true;
    }

    public ArrayList<Entry<T>> values() {
        ArrayList<Entry<T>> list = new ArrayList<>();
        for (EntryList entryList: entries) {
            list.addAll(entryList.values);
        }
        return list;
    }

    //MARK: - Processing

    private int index(String key) {
        int hash = (key.hashCode()) % size;
        return Math.abs(hash);
    }

    private EntryPosition positionFor(String key) {
        int index = index(key);
        EntryList<T> entryList = entries[index];

        int offset = 0;
        for (; offset < entryList.values.size(); offset++) {

            if (entryList.values.get(offset).key.equals(key)) {
                return new EntryPosition(true, index, offset);
            }
        }

        return new EntryPosition(false, index, offset);
    }

    //TODO: Think about more performant searching
    private void removeMinHitsElement() {
        int minIndex = 0;
        int minOffset = 0;
        int minHits = Integer.MAX_VALUE;
        for (int i = 0; i < entries.length; i++) {
            for (int j = 0; j < entries[i].values.size(); j++) {
                if (entries[i].values.get(j).hits < minHits) {
                    minIndex = i;
                    minOffset = j;
                    minHits = entries[i].values.get(j).hits;
                }
            }
        }

        entries[minIndex].values.remove(minOffset);
    }

}
