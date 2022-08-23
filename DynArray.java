import java.lang.reflect.Array;

public class DynArray<T> {
    public T[] array;
    public int count;
    public int capacity;
    Class clazz;

    private final double MINIMUM_CAPACITY;
    private final double INCREASE_BUFFER_RATIO;
    private final double DECREASE_BUFFER_RATIO;

    public DynArray(Class clz) {
        clazz = clz;
        count = 0;
        capacity = 16;
        MINIMUM_CAPACITY = 16;
        INCREASE_BUFFER_RATIO = 2;
        DECREASE_BUFFER_RATIO = 1.5;
        makeArray(capacity);
    }

    public void makeArray(int new_capacity) {
        if (array == null) {
            array = (T[]) Array.newInstance(this.clazz, new_capacity);
        } else {
            T[] values = array;
            array = (T[]) Array.newInstance(this.clazz, new_capacity);
            System.arraycopy(values, 0, array, 0, capacity);
        }
    }

    public T getItem(int index) {
        assertIndexInBounds(index, false);
        return array[index];
    }

    public void append(T itm) {
        extendBufferSizeIfNeeded();
        array[count] = itm;
        count++;
    }

    public void insert(T itm, int index) {
        assertIndexInBounds(index, true);
        extendBufferSizeIfNeeded();
        T[] newArray = (T[]) Array.newInstance(this.clazz, capacity);
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = itm;
        System.arraycopy(array, index, newArray, index + 1, capacity - index - 1);
        array = newArray;
        count++;
    }

    public void remove(int index) {
        assertIndexInBounds(index, false);
        shrinkBufferSizeIfNeeded();
        T[] newArray = (T[]) Array.newInstance(this.clazz, capacity);
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, capacity - index - 1);
        array = newArray;
        count--;
    }

    private boolean canAddIntoBuffer() {
        return capacity > count;
    }

    private void extendBufferSizeIfNeeded() {
        if (canAddIntoBuffer()) {
            return;
        }
        int newCapacity = (int) (capacity * INCREASE_BUFFER_RATIO);
        makeArray(newCapacity);
        capacity = newCapacity;
    }

    private void shrinkBufferSizeIfNeeded() {
        int lowerBound = (int) (capacity / DECREASE_BUFFER_RATIO);
        int decreasedCapacity = (int) Math.max(lowerBound, MINIMUM_CAPACITY);
        if (count - 1 >= decreasedCapacity || decreasedCapacity == capacity) {
            return;
        }
        capacity = decreasedCapacity;
        makeArray(decreasedCapacity);
    }

    private void assertIndexInBounds(int index, boolean containsCount) {
        if (index < 0) {
            Exception ex = new Exception("Cannot use negative index");
            throw new RuntimeException(ex);
        } else if (containsCount && index > count) {
            Exception ex = new Exception("DynArray index: " + index + " out of bounds: " + count);
            throw new RuntimeException(ex);
        } else if (!containsCount && index >= count) {
            Exception ex = new Exception("DynArray index: " + index + " out of bounds: " + count);
            throw new RuntimeException(ex);
        }
    }

}
