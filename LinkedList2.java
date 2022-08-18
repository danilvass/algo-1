import java.util.*;

public class LinkedList2
{
    public Node head;
    public Node tail;

    public LinkedList2()
    {
        head = null;
        tail = null;
    }

    public void addInTail(Node _item)
    {
        if (head == null) {
            this.head = _item;
            this.head.next = null;
            this.head.prev = null;
        } else {
            this.tail.next = _item;
            _item.prev = tail;
        }
        this.tail = _item;
    }

    public Node find(int _value)
    {
        Node node = this.head;
        while (node != null) {
            if (node.value == _value)
                return node;
            node = node.next;
        }
        return null;
    }

    public ArrayList<Node> findAll(int _value)
    {
        ArrayList<Node> nodes = new ArrayList<Node>();
        Node node = this.head;
        while (node != null) {
            if (node.value == _value)
                nodes.add(node);
            node = node.next;
        }
        return nodes;
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

    public boolean remove(int _value)
    {
        Node node = this.head;

        while (node != null) {
            if (node.value == _value) {
                if (node.prev != null) {
                    node.prev.next = node.next;
                    if (node.next != null)
                        node.next.prev = node.prev;
                } else {
                    this.head = node.next;
                    if (this.head != null)
                        this.head.prev = null;
                }

                if (node == tail) {
                    Node lastNode = node.prev;
                    this.tail = lastNode;
                    while (lastNode != null) {
                        this.tail = lastNode;
                        lastNode = lastNode.next;
                    }
                }
                return true;
            }
            node = node.next;
        }

        return false;
    }

    public void removeAll(int _value)
    {
        Node node = this.head;

        while (node != null) {
            if (node.value == _value) {
                if (node.prev != null) {
                    node.prev.next = node.next;
                    if (node.next != null)
                        node.next.prev = node.prev;
                } else {
                    this.head = node.next;
                    if (this.head != null)
                        this.head.prev = null;
                }

                if (node == tail) {
                    Node lastNode = node.prev;
                    this.tail = lastNode;
                    while (lastNode != null) {
                        this.tail = lastNode;
                        lastNode = lastNode.next;
                    }
                }

            }
            node = node.next;
        }
    }

    public void clear()
    {
        Node node = this.head;
        while (node != null) {
            this.head = node.next;
            node = node.next;
        }
        this.tail = null;
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

    public void insertAfter(Node _nodeAfter, Node _nodeToInsert)
    {
        Node node = this.head;
        boolean inserted = false;

        while (node != null) {
            if (node == _nodeAfter) {
                if (node == tail) {
                    this.tail = _nodeToInsert;
                }
                Node next = node.next;
                node.next = _nodeToInsert;
                _nodeToInsert.prev = node;
                if(next != null) {
                    Node lastNode = _nodeToInsert;
                    while (lastNode != null) {
                        next.prev = lastNode;
                        lastNode = lastNode.next;
                    }
                }
                _nodeToInsert.next = next;
                inserted = true;
                break;
            }
            node = node.next;
        }
        if (!inserted) {
            insertInHead(_nodeToInsert);
        }
    }

    public void insertInHead(Node _nodeToInsert)
    {
        Node oldHead = this.head;
        if (this.head == this.tail) {
            Node lastNode = _nodeToInsert;
            while (lastNode != null) {
                this.tail = lastNode;
                this.tail.prev = lastNode.prev;
                lastNode = lastNode.next;
            }
        }
        this.head = _nodeToInsert;
        if (oldHead != null) {
            _nodeToInsert.next = oldHead;
            oldHead.prev = _nodeToInsert;
        }
    }

}

class Node
{
    public int value;
    public Node next;
    public Node prev;

    public Node(int _value)
    {
        value = _value;
        next = null;
        prev = null;
    }
}
