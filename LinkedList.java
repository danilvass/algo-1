import java.util.*;

public class LinkedList {
    public Node head;
    public Node tail;

    public LinkedList() {
        head = null;
        tail = null;
    }

    public void addInTail(Node item) {
        if (this.head == null)
            this.head = item;
        else
            this.tail.next = item;
        this.tail = item;
    }

    public Node find(int value) {
        Node node = this.head;
        while (node != null) {
            if (node.value == value)
                return node;
            node = node.next;
        }
        return null;
    }

    public ArrayList<Node> findAll(int _value) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        Node node = this.head;
        while (node != null) {
            if (node.value == _value)
                nodes.add(node);
            node = node.next;
        }
        return nodes;
    }

    public boolean remove(int _value) {
        Node previousNode = findPrevious(_value);
        if (previousNode != null) {
            Node deletingNode = previousNode.next;
            if (deletingNode != null) {
                if (deletingNode == tail)
                    tail = previousNode;
                previousNode.next = deletingNode.next;
                return true;
            }
        } else if (head != null && head.value == _value) {
            head = head.next;
            return true;
        }
        return false;
    }

    private Node findPrevious(int value) {
        Node node = this.head;
        while (node != null) {
            if (node.next != null && node.next.value == value)
                return node;
            node = node.next;
        }
        return null;
    }

    public void removeAll(int _value) {
        Node node = this.head;
        while (node != null) {
            if (node.value == _value) {
                this.head = node.next;
            } else if (node.next != null && node.next.value == _value) {
                if (node.next == tail) {
                    tail = node;
                } else {
                    node.next = node.next.next;
                }
            }
            node = node.next;
        }
        if (tail != null && tail.value == _value) {
            tail = null;
        }
    }

    public void clear() {
        Node node = this.head;
        while (node != null) {
            this.head = node.next;
            node = node.next;
        }
        this.tail = null;
    }

    public int count() {
        int _count = 0;
        Node node = this.head;
        while (node != null) {
            node = node.next;
            _count++;
        }
        return _count;
    }

    public void insertAfter(Node _nodeAfter, Node _nodeToInsert) {
        Node node = this.head;
        boolean inserted = false;
        while (node != null) {
            if (node == _nodeAfter) {
                if (node == tail)
                    this.tail = _nodeToInsert;
                Node next = node.next;
                node.next = _nodeToInsert;
                _nodeToInsert.next = next;
                inserted = true;
                break;
            }
            node = node.next;
        }
        if (!inserted) {
            Node oldHead = this.head;
            this.head = _nodeToInsert;
            _nodeToInsert.next = oldHead;
        }
    }

    public static LinkedList merge(LinkedList lhs, LinkedList rhs) {
        if (lhs.count() != rhs.count()) {
            return null;
        }

        LinkedList merged = new LinkedList();

        Node node1 = lhs.head;
        Node node2 = rhs.head;
        while (node1 != null) {
            merged.addInTail(new Node(node1.value + node2.value));
            node1 = node1.next;
            node2 = node2.next;
        }

        return merged;
    }

}

class Node
{
    public int value;
    public Node next;
    public Node(int _value)
    {
        value = _value;
        next = null;
    }
}

//MARK: - For now just simple class with throwable testing methods, but lets specify better way for this.
class Test {

    void test() throws Exception {
        Test test = new Test();
        test.testRemoveByValue();
        test.testRemoveAllValues();
        test.testClear();
        test.testFindAll();
        test.testCount();
        test.testInsertAfter();
        test.testMerge();
    }

    private void testRemoveByValue() throws Exception {
        LinkedList list = new LinkedList();
        list.addInTail(new Node(1));
        list.addInTail(new Node(2));
        list.addInTail(new Node(3));
        list.addInTail(new Node(3));
        list.addInTail(new Node(4));
        list.addInTail(new Node(5));

        assertTrue(list.remove(1), "Failed to remove existence value");
        assertTrue(list.remove(3), "Failed to remove existence value");
        assertTrue(list.remove(4), "Failed to remove existence value");
        assertTrue(list.remove(5), "Failed to remove existence value");
        assertFalse(list.remove(6), "Cannot remove non existence value");

        assertTrue(list.findAll(3).size() == 1, "Wrong number of expected elements after removing");
        assertTrue(list.findAll(4).isEmpty(), "Wrong number of expected elements after removing");
        assertTrue(list.findAll(5).isEmpty(), "Wrong number of expected elements after removing");

        assertTrue(list.head.value == 2, "Failed to delete tail");
        assertTrue(list.tail.value == 3, "Failed to delete tail");
    }

    private void testRemoveAllValues() throws Exception {
        LinkedList list = new LinkedList();

        list.addInTail(new Node(1));
        list.addInTail(new Node(2));
        list.addInTail(new Node(3));
        list.addInTail(new Node(3));
        list.addInTail(new Node(4));
        list.addInTail(new Node(5));

        list.removeAll(1);
        assertTrue(list.findAll(1).isEmpty(), "Elements were not deleted");
        assertTrue(list.head.value == 2, "Failed to delete head");

        list.removeAll(3);
        assertTrue(list.findAll(3).isEmpty(), "Elements were not deleted");

        list.removeAll(99);
        assertTrue(list.findAll(99).isEmpty(), "Elements were not deleted");

        list.removeAll(5);
        assertTrue(list.findAll(5).isEmpty(), "Elements were not deleted");
        assertTrue(list.tail.value == 4, "Failed to delete tail");
    }

    private void testClear() throws Exception {
        LinkedList list = new LinkedList();

        list.addInTail(new Node(1));
        list.addInTail(new Node(2));
        list.addInTail(new Node(3));
        list.addInTail(new Node(3));
        list.addInTail(new Node(4));
        list.addInTail(new Node(5));

        list.clear();
        assertTrue(list.count() == 0, "Elements were not deleted");
        assertTrue(list.head == null, "Elements were not deleted");
        assertTrue(list.tail == null, "Elements were not deleted");
    }

    private void testFindAll() throws Exception {
        LinkedList list = new LinkedList();

        list.addInTail(new Node(1));
        list.addInTail(new Node(2));
        list.addInTail(new Node(3));
        list.addInTail(new Node(3));
        list.addInTail(new Node(4));
        list.addInTail(new Node(5));

        assertTrue(list.findAll(0).isEmpty(), "Wrong number of found items");
        assertTrue(list.findAll(1).size() == 1, "Wrong number of found items");
        assertTrue(list.findAll(3).size() == 2, "Wrong number of found items");
        assertTrue(list.findAll(5).size() == 1, "Wrong number of found items");
    }

    private void testCount() throws Exception {
        LinkedList list = new LinkedList();

        assertTrue(list.count() == 0, "Initial list cannot have non zero count");

        list.addInTail(new Node(1));
        list.addInTail(new Node(2));
        list.addInTail(new Node(3));
        list.addInTail(new Node(3));
        list.addInTail(new Node(4));
        list.addInTail(new Node(5));

        assertTrue(list.count() == 6, "Wrong number of elements");
    }

    private void testInsertAfter() throws Exception {
        LinkedList list = new LinkedList();
        Node nodeInList = new Node(3);
        Node nodeAfterInList = new Node(4);

        list.addInTail(new Node(1));
        list.addInTail(new Node(2));
        list.addInTail(nodeInList);
        list.addInTail(nodeAfterInList);

        Node nodeToInsert = new Node(99);
        list.insertAfter(nodeInList, nodeToInsert);

        Node foundNode = list.find(nodeInList.value);
        assertTrue(foundNode.next == nodeToInsert, "Founded node next element isn't expected node");
        assertTrue(nodeToInsert.next == nodeAfterInList, "Inserted node next element isn't expected node");
        assertTrue(list.tail == nodeAfterInList, "Inserted node next element isn't expected node");

        Node nodeToInsertAtTail = new Node(100);
        list.insertAfter(nodeAfterInList, nodeToInsertAtTail);
        assertTrue(list.tail == nodeToInsertAtTail, "Inserted node next element isn't expected node");
    }

    private void testMerge() throws Exception {
        LinkedList list = new LinkedList();
        list.addInTail(new Node(1));
        list.addInTail(new Node(2));
        list.addInTail(new Node(3));

        LinkedList list2 = new LinkedList();
        LinkedList mergedNull = LinkedList.merge(list, list2);

        assertTrue(mergedNull == null, "Cannot merge non equal sized lists");

        LinkedList list3 = new LinkedList();
        list3.addInTail(new Node(1));
        list3.addInTail(new Node(2));
        list3.addInTail(new Node(3));

        LinkedList mergedValues = LinkedList.merge(list, list3);
        assertTrue(mergedValues.count() == 3, "Cannot merge non equal sized lists");
        assertTrue(mergedValues.head.value == list.head.value + list3.head.value, "Cannot merge non equal sized lists");
        assertTrue(mergedValues.head.next.value == list.head.next.value + list3.head.next.value, "Cannot merge non equal sized lists");
        assertTrue(mergedValues.tail.value == list.tail.value + list3.tail.value, "Cannot merge non equal sized lists");
    }

    //MARK: - Helpers
    private void assertTrue(boolean condition, String failedMessage) throws Exception {
        if (!condition) {
            throw new Exception(failedMessage);
        }
    }

    private void assertFalse(boolean condition, String failedMessage) throws Exception {
        if (condition) {
            throw new Exception(failedMessage);
        }
    }

}
