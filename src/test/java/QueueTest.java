import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueueTest {

    @Test
    void test_queue() {
        Queue<Integer> q = new Queue<>();
        assertNull(q.dequeue());

        assertEquals(0, q.size());

        q.enqueue(1);
        assertEquals(1, q.size());
        assertEquals(1, q.dequeue());
        assertEquals(0, q.size());

        q.enqueue(2);
        q.enqueue(3);
        assertEquals(2, q.size());
        assertEquals(3, q.dequeue());
        assertEquals(2, q.dequeue());
        assertNull(q.dequeue());
    }

    @Test
    void test_rotation() {
        Queue<Integer> q = new Queue<>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);

        q.rotate(1);
        assertEquals(2, q.dequeue());
        assertEquals(1, q.dequeue());
        assertEquals(3, q.dequeue());

        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);

        q.rotate(2);
        assertEquals(3, q.dequeue());
        assertEquals(2, q.dequeue());
        assertEquals(1, q.dequeue());
        assertEquals(5, q.dequeue());
        assertEquals(4, q.dequeue());

    }

}