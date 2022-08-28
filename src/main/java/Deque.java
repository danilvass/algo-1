import java.util.*;

public class Deque<T> {

    private ArrayList<T> list;

    public Deque() {
        this.list = new ArrayList<T>();
    }

    public void addFront(T item) {
        list.add(0, item);
    }

    public void addTail(T item) {
        list.add(item);
    }

    public T removeFront() {
        if (list.size() == 0) { return null; }
        T first = list.get(0);
        list.remove(0);
        return first;
    }

    public T removeTail() {
        if (list.size() == 0) { return null; }
        int lastIndex = list.size() - 1;
        T last = list.get(lastIndex);
        list.remove(lastIndex);
        return last;
    }

    public int size() {
        return list.size();
    }
}