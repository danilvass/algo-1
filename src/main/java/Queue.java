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
        list.add(0, item);
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

}