import java.util.*;

class Node<T>
{
    public T value;
    public Node<T> next, prev;

    public Node(T _value)
    {
        value = _value;
        next = null;
        prev = null;
    }
}

public class OrderedList<T>
{
    public Node<T> head, tail;
    private boolean _ascending;

    private final int EQUAL_COMPARE = 0;

    public OrderedList(boolean asc)
    {
        head = null;
        tail = null;
        _ascending = asc;
    }

    private int nextCompareResult() {
        return _ascending ? 1 : -1;
    }

    private int prevCompareResult() {
        return _ascending ? -1 : 1;
    }

    public int compare(T v1, T v2)
    {
        if (v1 instanceof Number) {
            return ((Integer)v1).compareTo(((Integer)v2));
        }
        return 0;
    }

    public void add(T value)
    {
        if (head == null) {
            this.head = new Node<>(value);
            this.tail = this.head;
            return;
        }

        Node<T> newNode = new Node<>(value);
        Node<T> currentNode = findClosestNode(value);

        int compareResult = compare(currentNode.value, value);

        if (compareResult == EQUAL_COMPARE || compareResult == nextCompareResult()) {
            //Inserting new value before founded node
            if (currentNode == this.head) {
                insertToHead(value);
                return;
            }

            Node<T> prev = currentNode.prev;
            newNode.prev = prev;
            currentNode.prev = newNode;
            newNode.next = currentNode;
            if (prev != null) { prev.next = newNode; }
        } else {
            //Inserting new value after founded node

            if (currentNode == this.tail) {
                insertToTail(value);
                return;
            }

            Node<T> next = currentNode.next;
            newNode.next = next;
            currentNode.next = newNode;
            newNode.prev = currentNode;
            if (next != null) { next.prev = newNode; }
        }
    }

    public Node<T> find(T val)
    {
        Node<T> currentNode = findClosestNode(val);
        if (currentNode == null) {
            return null;
        }
        if (compare(currentNode.value, val) == EQUAL_COMPARE) {
            return currentNode;
        } else if (currentNode.next != null && compare(currentNode.next.value, val) == EQUAL_COMPARE) {
            return currentNode.next;
        } else if (currentNode.prev != null && compare(currentNode.prev.value, val) == EQUAL_COMPARE) {
            return currentNode.prev;
        }
        return null;
    }

    public void delete(T val)
    {
        Node<T> node = find(val);
        if (node == null) { return; }
        if (compare(node.value, val) != EQUAL_COMPARE) { return; }

        Node<T> next = node.next;
        Node<T> prev = node.prev;
        if (next != null && prev != null) {
            next.prev = prev;
            prev.next = next;
            return;
        }
        if (next != null) {
            next.prev = null;
            this.head = next;
            return;
        }
        if (prev != null) {
            prev.next = null;
            this.tail = prev;
            return;
        }
        this.head = null;
        this.tail = null;
    }

    public void clear(boolean asc)
    {
        _ascending = asc;
        ArrayList<Node<T>> list = getAll();
        clearAll();
        for (Node<T> element: list) {
            add(element.value);
        }
    }

    public int count()
    {
        int _count = 0;
        Node node = this.head;
        while (node != null) {
            node = node.next;
            _count++;
        }
        return _count;
    }

    ArrayList<Node<T>> getAll() {
        ArrayList<Node<T>> r = new ArrayList<Node<T>>();
        Node<T> node = head;
        while(node != null)
        {
            r.add(node);
            node = node.next;
        }
        return r;
    }

    //MARK: - processing helpers

    private void clearAll() {
        while(this.head != null) {
            this.head = this.head.next;
        }
        this.tail = null;
    }

    private Node<T> findMiddle(Node<T> head, boolean next, int size) {
        Node<T> _head = head;
        int i = 0;
        while (i != size) {
            if (next) {
                _head = _head.next;
            } else {
                _head = _head.prev;
            }
            i++;
        }
        return _head;
    }

    private Node<T> findClosestNode(T value) {
        if (compare(head.value, value) == EQUAL_COMPARE || compare(head.value, value) == nextCompareResult()) {
            return this.head;
        }

        if (this.tail != null && (compare(tail.value, value) == prevCompareResult() || compare(tail.value, value) == EQUAL_COMPARE)) {
            return this.tail;
        }

        int _count = this.count();
        _count = (int) Math.ceil((double)_count / 2.0);

        Node<T> mid = findMiddle(this.head, true, _count);
        Node<T> _notNullMid = mid;

        while(mid != null && _count > 1) {
            _count = (int) Math.ceil((double)_count / 2.0);

            if (compare(mid.value, value) == EQUAL_COMPARE) {
                return mid;
            } else if (compare(mid.value, value) == nextCompareResult()) {
                mid = findMiddle(mid, false, _count);
            } else {
                mid = findMiddle(mid, true, _count);
            }
            if (mid != null) { _notNullMid = mid; }
        }

        return _notNullMid;
    }

    private void insertToHead(T value) {
        Node<T> newHead = new Node<>(value);
        Node<T> oldHead = this.head;
        this.head = newHead;
        newHead.next = oldHead;
        oldHead.prev = newHead;
    }

    private void insertToTail(T value) {
        Node<T> newTail = new Node<>(value);
        Node<T> oldTail = this.tail;
        this.tail = newTail;
        newTail.prev = oldTail;
        oldTail.next = newTail;
    }

}
