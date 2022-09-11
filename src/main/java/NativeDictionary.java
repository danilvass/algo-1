import java.lang.reflect.Array;

class NativeDictionary<T>
{

    class Entry<T> {
        String key;
        T value;
        Entry next;

        public Entry(String key, T value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public int size;
    public Entry [] slots;

    private int count = 0;

    public NativeDictionary(int sz, Class clazz)
    {
        size = sz;
        slots = new Entry[size];
    }

    public int hashFun(String key)
    {
        return (key.hashCode()) % size;
    }

    public boolean isKey(String key)
    {
        int index = hashFun(key);
        Entry<T> entry = slots[index];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    public void put(String key, T value)
    {
        int index = hashFun(key);
        if (slots[index] == null) {
            slots[index] = new Entry(key, value, null);
            return;
        }

        Entry<T> entry = slots[index];
        while (entry.next != null) {
            entry = entry.next;
        }
        entry.next = new Entry(key, value, null);
    }

    public T get(String key)
    {
        int index = hashFun(key);
        Entry<T> entry = slots[index];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

}