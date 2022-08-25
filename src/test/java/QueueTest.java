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

}