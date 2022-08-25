import java.util.*;

public class Queue<T>
{

    private ArrayList<T> list;

    public Queue()
    {
        this.list = new ArrayList<T>();
    }

    public void enqueue(T item)
    {
        list.add(item);
    }

    public T dequeue()
    {
        if (list.size() == 0) { return null; }
        T element = list.get(0);
        list.remove(0);
        return element;
    }

    public int size()
    {
        return list.size();
    }

    public void rotate(int n) {
        n = n % size();

        ArrayList<T> e = new ArrayList<>();
        for (int i = n + 1; i < size(); i++) {
            e.add(list.get(i));
        }

        int size = e.size();
        for (int i = 0; i < list.size() - size; i++) {
            e.add(list.get(i));
        }

        list = e;
    }

}