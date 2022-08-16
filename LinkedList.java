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
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.next;
            }
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
                if (node == head) {
                    this.head = node.next;
                }
                node = node.next;
            } else if (node.next != null && node.next.value == _value) {
                if (node.next == tail) {
                    tail = node;
                    node.next = node.next.next;
                } else {
                    node.next = node.next.next;
                }
            } else {
                node = node.next;
            }
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
            if (this.head == this.tail) {
                Node lastNode = _nodeToInsert;
                while (lastNode != null) {
                    this.tail = lastNode;
                    lastNode = lastNode.next;
                }
            }
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
