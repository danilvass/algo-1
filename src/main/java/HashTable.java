public class HashTable
{
    public int size;
    public int step;
    public String [] slots;

    private int count = 0;

    public HashTable(int sz, int stp)
    {
        size = sz;
        step = stp;
        slots = new String[size];
        for(int i=0; i<size; i++) slots[i] = null;
    }

    public int hashFun(String value)
    {
        int sum = 0;
        for (Character ch: value.toCharArray()) {
            sum += ch;
        }
        return sum % size;
    }

    public int seekSlot(String value)
    {
        if (count == size) { return -1; }
        int maybeIndex = (hashFun(value)) % size;
        for (int i = 0; i < size; i++) {
            if (slots[maybeIndex] == null) {
                return maybeIndex;
            }
            maybeIndex = (maybeIndex + step) % size;
        }
        return -1;
    }

    public int put(String value)
    {
        int index = seekSlot(value);
        if (index == -1) { return -1; }
        slots[index] = value;
        count++;
        return index;
    }

    public int find(String value)
    {
        int hashFunValue = hashFun(value);
        for (int i = 0; i < size; i++) {
            int maybeIndex = (hashFunValue + i) % size;
            if (slots[maybeIndex] != null && slots[maybeIndex].equals(value)) {
                return maybeIndex;
            }
        }
        return -1;
    }

}