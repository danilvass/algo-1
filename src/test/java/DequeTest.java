import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DequeTest {

    @Test
    void test_init() {
        Deque<Integer> sut = createSUT();
        assertEquals(0, sut.size());
    }

    @Test
    void test_addRemoveFront() {
        Deque<Integer> sut = createSUT();
        sut.addFront(77);

        assertEquals(1, sut.size());
        assertEquals(77, sut.removeFront());
        assertNull(sut.removeFront());

        assertEquals(0, sut.size());
    }

    @Test
    void test_addRemoveTail() {
        Deque<Integer> sut = createSUT();
        sut.addTail(77);

        assertEquals(1, sut.size());
        assertEquals(77, sut.removeTail());
        assertNull(sut.removeTail());

        assertEquals(0, sut.size());
    }

    @Test
    void test_complex() {
        Deque<Integer> sut = createSUT();
        sut.addTail(77);
        sut.addTail(66);
        sut.addTail(55);

        assertEquals(3, sut.size());
        assertEquals(55, sut.removeTail());
        assertEquals(66, sut.removeTail());
        sut.addFront(88);
        sut.addFront(99);
        assertEquals(99, sut.removeFront());
        assertEquals(88, sut.removeFront());

        assertEquals(1, sut.size());

        assertEquals(77, sut.removeFront());
    }

    private Deque<Integer> createSUT() {
        Deque<Integer> deque = new Deque<>();
        return deque;
    }

}