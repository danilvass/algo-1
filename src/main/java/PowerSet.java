import java.lang.reflect.Array;
import java.util.ArrayList;

public class PowerSet
{

    private static class Entry {
        private ArrayList<String> values;

        Entry(String value) {
            this.values = new ArrayList<>();
            this.values.add(value);
        }

        void add(String value) {
            values.add(value);
        }

        void remove(int index) {
            values.remove(index);
        }
    }

    private static class EntryPosition {
        boolean contains;
        int index;
        int offset;

        EntryPosition(boolean contains, int index, int offset) {
            this.contains = contains;
            this.index = index;
            this.offset = offset;
        }
    }

    private final Entry[] entries;

    private int size;
    private final int ENTRIES_SIZE = 10000;

    public PowerSet()
    {
        size = 0;
        entries = new Entry[ENTRIES_SIZE];
    }

    public PowerSet(ArrayList<String> values)
    {
        size = 0;
        entries = new Entry[ENTRIES_SIZE];
        for (String value: values) {
            put(value);
        }
    }

    public ArrayList<String> values() {
        ArrayList<String> list = new ArrayList<>();
        for (Entry entry: entries) {
            if (entry == null) { continue; }
            list.addAll(entry.values);
        }
        return list;
    }

    public int size() {
        return size;
    }

    private int index(String key) {
        int hash = (key.hashCode()) % ENTRIES_SIZE;
        return Math.abs(hash);
    }

    private EntryPosition positionFor(String value)
    {
        int index = index(value);
        Entry entry = entries[index];
        if (entry == null) {
            return new EntryPosition(false, index, 0);
        }

        int offset = 0;
        for (; offset < entry.values.size(); offset++) {

            if (entry.values.get(offset).equals(value)) {
                return new EntryPosition(true, index, offset);
            }
        }

        return new EntryPosition(false, index, offset);
    }

    public void put(String value)
    {
        EntryPosition position = positionFor(value);
        if (position.contains) { return; }

        size++;

        Entry entry = entries[position.index];
        if (entry == null) {
            entries[position.index] = new Entry(value);
        } else {
            entry.add(value);
        }
    }

    public boolean get(String value)
    {
        return positionFor(value).contains;
    }

    public boolean remove(String value)
    {
        EntryPosition position = positionFor(value);
        if (!position.contains) { return false; }

        size--;

        Entry entry = entries[position.index];
        entry.remove(position.offset);

        return true;
    }

    public PowerSet intersection(PowerSet set2)
    {
        PowerSet intersected = new PowerSet();

        for (Entry entry: entries) {
            if (entry == null) { continue; }

            for (String value: entry.values) {
                if (set2.get(value)) {
                    intersected.put(value);
                }
            }
        }

        return intersected;
    }

    public PowerSet union(PowerSet set2)
    {
        PowerSet unionSet = new PowerSet(values());

        for (String value: set2.values()) {
            unionSet.put(value);
        }
        return unionSet;
    }

    public PowerSet difference(PowerSet set2)
    {
        PowerSet diff = new PowerSet(values());
        for (String value: set2.values()) {
            if (diff.get(value)) {
                diff.remove(value);
            } else {
                diff.put(value);
            }
        }
        return diff;
    }

    public boolean isSubset(PowerSet set2)
    {
        for (String value: set2.values()) {
            if (!get(value)) { return false; }
        }
        return true;
    }
}
