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

    private final int EQUAL_COMPARE_RESULT = 0;
    private final int GREATER_COMPARE_RESULT = 1;
    private final int LESS_COMPARE_RESULT = -1;

    public OrderedList(boolean asc)
    {
        head = null;
        tail = null;
        _ascending = asc;
    }

    private int nextCompareResult() {
        return _ascending ? GREATER_COMPARE_RESULT : LESS_COMPARE_RESULT;
    }

    private int prevCompareResult() {
        return _ascending ? LESS_COMPARE_RESULT : GREATER_COMPARE_RESULT;
    }

    public int compare(T v1, T v2)
    {
        if (v1 instanceof Number) {
            return ((Integer)v1).compareTo(((Integer)v2));
        } else if  (v1 instanceof String) {
            String v1WithTrimmedCharacters = ((String) v1).trim();
            String v2WithTrimmedCharacters = ((String) v2).trim();
            return v1WithTrimmedCharacters.compareTo(v2WithTrimmedCharacters);
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

        Node<T> nodeToInsert = new Node<>(value);
        Node<T> closestToInsertedNode = findClosestNode(value);

        int compareResult = compare(closestToInsertedNode.value, value);

        if (compareResult == EQUAL_COMPARE_RESULT || compareResult == nextCompareResult()) {
            //Inserting new value before founded node
            if (closestToInsertedNode == this.head) {
                insertToHead(value);
                return;
            }

            Node<T> prevNodeFromClosest = closestToInsertedNode.prev;
            nodeToInsert.prev = prevNodeFromClosest;
            closestToInsertedNode.prev = nodeToInsert;
            nodeToInsert.next = closestToInsertedNode;
            if (prevNodeFromClosest != null) { prevNodeFromClosest.next = nodeToInsert; }
        } else {
            //Inserting new value after founded node

            if (closestToInsertedNode == this.tail) {
                insertToTail(value);
                return;
            }

            Node<T> nextNodeToClosest = closestToInsertedNode.next;
            nodeToInsert.next = nextNodeToClosest;
            closestToInsertedNode.next = nodeToInsert;
            nodeToInsert.prev = closestToInsertedNode;
            if (nextNodeToClosest != null) { nextNodeToClosest.prev = nodeToInsert; }
        }
    }

    public Node<T> find(T val)
    {
        Node<T> closestNode = findClosestNode(val);
        if (closestNode == null) {
            return null;
        }
        if (compare(closestNode.value, val) == EQUAL_COMPARE_RESULT) {
            return closestNode;
        } else if (closestNode.next != null && compare(closestNode.next.value, val) == EQUAL_COMPARE_RESULT) {
            return closestNode.next;
        } else if (closestNode.prev != null && compare(closestNode.prev.value, val) == EQUAL_COMPARE_RESULT) {
            return closestNode.prev;
        }
        return null;
    }

    public void delete(T val)
    {
        Node<T> nodeToDelete = find(val);
        if (nodeToDelete == null) { return; }
        if (compare(nodeToDelete.value, val) != EQUAL_COMPARE_RESULT) { return; }

        Node<T> next = nodeToDelete.next;
        Node<T> prev = nodeToDelete.prev;
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
        clearAll();
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
        if (head == null) { return null; }

        if (compare(head.value, value) == EQUAL_COMPARE_RESULT || compare(head.value, value) == nextCompareResult()) {
            return this.head;
        }

        if (this.tail != null && (compare(tail.value, value) == prevCompareResult() || compare(tail.value, value) == EQUAL_COMPARE_RESULT)) {
            return this.tail;
        }

        int halfListSize = this.count();
        halfListSize = (int) Math.ceil((double)halfListSize / 2.0);

        Node<T> mid = findMiddle(this.head, true, halfListSize);
        Node<T> closestNode = mid;

        while(mid != null && halfListSize > 1) {
            halfListSize = (int) Math.ceil((double)halfListSize / 2.0);

            if (compare(mid.value, value) == EQUAL_COMPARE_RESULT) {
                return mid;
            } else if (compare(mid.value, value) == nextCompareResult()) {
                mid = findMiddle(mid, false, halfListSize);
            } else {
                mid = findMiddle(mid, true, halfListSize);
            }
            if (mid != null) { closestNode = mid; }
        }

        return closestNode;
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
