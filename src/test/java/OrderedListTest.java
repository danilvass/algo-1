import com.sun.tools.corba.se.idl.constExpr.Or;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderedListTest {

    @Test
    void test_compare_ints() {
        OrderedList<Integer> sut = createSUTInt(true);
        assertEquals(0, sut.compare(1, 1));
        assertEquals(0, sut.compare(2, 2));
        assertEquals(1, sut.compare(1, 0));
        assertEquals(-1, sut.compare(0, 1));
    }

    @Test
    void test_findNodeWithBinarySearch() {
        OrderedList<Integer> sut = createSUTInt(true);
        assertEquals(9, sut.count());

        sut.add(0);
        assertEquals(0, sut.head.value);
        sut.add(1);
        assertEquals(1, sut.head.next.value);
        sut.add(10);
        assertEquals(10, sut.tail.value);
        sut.add(99);
        assertEquals(99, sut.tail.value);
        sut.add(88);
        assertEquals(88, sut.tail.prev.value);

        assertEquals(14, sut.count());
    }

    @Test
    void test_find() {
        OrderedList<Integer> sut = new OrderedList<Integer>(true);
        sut.add(1);
        assertEquals(1, sut.find(1).value);

        sut = createSUTInt(true);
        assertNull(sut.find(0));

        assertEquals(1, sut.find(1).value);
        assertEquals(2, sut.find(1).next.value);

        assertEquals(2, sut.find(2).value);
        assertEquals(1, sut.find(2).prev.value);

        assertEquals(3, sut.find(3).value);
        assertEquals(4, sut.find(4).value);
        assertEquals(5, sut.find(5).value);
        assertEquals(6, sut.find(6).value);
        assertEquals(7, sut.find(7).value);
        assertEquals(8, sut.find(8).value);

        assertEquals(9, sut.find(9).value);
        assertNull(sut.find(9).next);

        assertNull(sut.find(10));
    }

    @Test
    void test_delete() {
        OrderedList<Integer> sut = createSUTInt(true);
        assertEquals(9, sut.count());
        sut.delete(10);
        sut.delete(0);
        assertEquals(9, sut.count());

        sut.delete(1);
        assertEquals(8, sut.count());
        assertEquals(2, sut.head.value);
        assertNull(sut.head.prev);

        sut.delete(9);
        assertEquals(7, sut.count());
        assertEquals(8, sut.tail.value);
        assertNull(sut.tail.next);

        sut.delete(4);
        assertEquals(6, sut.count());
    }

    @Test
    void test_desc_list() {
        OrderedList<Integer> sut = createSUTInt(false);
        assertEquals(9, sut.head.value);
        assertEquals(1, sut.tail.value);
    }

    @Test
    void test_desc_add() {
        OrderedList<Integer> sut = createSUTInt(false);
        assertEquals(9, sut.count());

        sut.add(10);
        assertEquals(10, sut.count());

        assertEquals(10, sut.head.value);
        assertEquals(9, sut.head.next.value);
        assertEquals(10, sut.head.next.prev.value);

        sut.add(0);
        assertEquals(11, sut.count());

        assertEquals(0, sut.tail.value);
        assertEquals(1, sut.tail.prev.value);
        assertEquals(0, sut.tail.prev.next.value);

        sut.add(9);
        assertEquals(12, sut.count());
    }

    @Test
    void test_desc_remove() {
        OrderedList<Integer> sut = createSUTInt(false);

        sut.delete(10);
        sut.delete(0);
        assertEquals(9, sut.count());

        sut.delete(9);
        assertEquals(8, sut.count());
        assertEquals(8, sut.head.value);
        assertNull(sut.head.prev);

        sut.delete(1);
        assertEquals(7, sut.count());
        assertEquals(2, sut.tail.value);
        assertNull(sut.tail.next);

        sut.delete(4);
        assertEquals(6, sut.count());

        sut.delete(2);
        sut.delete(3);
        sut.delete(4);
        sut.delete(5);
        sut.delete(6);
        sut.delete(7);
        sut.delete(8);
        assertEquals(0, sut.count());
    }

    @Test
    void test_desc_find() {
        OrderedList<Integer> sut = createSUTInt(false);
        assertNull(sut.find(0));

        assertEquals(1, sut.find(1).value);
        assertEquals(2, sut.find(1).prev.value);

        assertEquals(2, sut.find(2).value);
        assertEquals(1, sut.find(2).next.value);

        assertEquals(3, sut.find(3).value);
        assertEquals(4, sut.find(4).value);
        assertEquals(5, sut.find(5).value);
        assertEquals(6, sut.find(6).value);
        assertEquals(7, sut.find(7).value);
        assertEquals(8, sut.find(8).value);

        assertEquals(9, sut.find(9).value);
        assertNull(sut.find(9).prev);

        assertNull(sut.find(10));
    }

    @Test
    void test_clear() {
        OrderedList<Integer> sut = createSUTInt(false);
        sut.clear(true);
        sut.clear(false);
    }

    private OrderedList<Integer> createSUTIntEmpty(boolean asc) {
        return new OrderedList<>(asc);
    }

    private OrderedList<Integer> createSUTInt(boolean asc) {
        OrderedList<Integer> sut = new OrderedList<>(asc);

        sut.add(1);
        sut.add(2);
        sut.add(3);
        sut.add(4);
        sut.add(5);
        sut.add(6);
        sut.add(7);
        sut.add(8);
        sut.add(9);

        return sut;
    }

    private OrderedList<String> createSUTStr(boolean asc) {
        return new OrderedList<>(asc);
    }
}