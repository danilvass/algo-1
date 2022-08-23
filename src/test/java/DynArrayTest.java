import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class DynArrayTest {

    @org.junit.jupiter.api.Test
    void getItem() {
        DynArray<Integer> sut = createSUT(0);
        sut.append(10);
        assertEquals(sut.getItem(0), 10);

        sut.append(20);
        assertEquals(sut.getItem(0), 10);
        assertEquals(sut.getItem(1), 20);

        sut.append(30);
        assertEquals(sut.getItem(0), 10);
        assertEquals(sut.getItem(1), 20);
        assertEquals(sut.getItem(2), 30);

        Assertions.assertThrows(Exception.class, () -> {
            sut.getItem(3);
        });
    }

    @org.junit.jupiter.api.Test
    void append() {
        DynArray<Integer> sut = createSUT(0);
        assertEquals(sut.capacity, 16);
        assertEquals(sut.count, 0);

        sut.append(1);
        assertEquals(sut.capacity, 16);
        assertEquals(sut.count, 1);
        assertEquals(sut.getItem(0), 1);

        //Should NOT increase capacity if elements count after append is not bigger than current capacity
        for (int i = 2; i <= 16; i++) {
            sut.append(i);
            assertEquals(sut.capacity, 16);
            assertEquals(sut.count, i);
            assertEquals(sut.getItem(i - 1), i);
        }

        //Should increase capacity if elements count after append is not bigger than current capacity
        sut.append(17);
        assertEquals(sut.capacity, 32);
        assertEquals(sut.count, 17);
        assertEquals(sut.getItem(16), 17);
    }

    @org.junit.jupiter.api.Test
    void insert() {
        DynArray<Integer> sut = createSUT(0);
        assertEquals(sut.capacity, 16);
        assertEquals(sut.count, 0);

        //Inserting into empty array at 0 index should act like 'append'
        sut.insert(99, 0);
        assertEquals(sut.capacity, 16);
        assertEquals(sut.count, 1);
        assertEquals(sut.getItem(0), 99);

        //Inserting first 16 elements should NOT increase buffer capacity
        for (int i = 1; i < 16; i++) {
            sut.insert(i, i);
            assertEquals(sut.capacity, 16);
            assertEquals(sut.count, i + 1);
            assertEquals(sut.getItem(i), i);
        }

        //Inserting 17th element should INCREASE buffer capacity
        sut.insert(16, 16);
        assertEquals(sut.capacity, 32);
        assertEquals(sut.count, 17);
        assertEquals(sut.getItem(16), 16);

        //When incorrect index are passed to remove function, error should be thrown
        Assertions.assertThrows(Exception.class, () -> {
            sut.insert(1, 99);
        });

        Assertions.assertThrows(Exception.class, () -> {
            sut.insert(1, -1);
        });
    }

    @org.junit.jupiter.api.Test
    void remove() {
        DynArray<Integer> sut = createSUT(23);
        assertEquals(sut.capacity, 32);
        assertEquals(sut.count, 23);
        assertEquals(sut.getItem(22), 22);
        Assertions.assertThrows(Exception.class, () -> {
            sut.getItem(23);
        });

        //Current capacity is 32 and current count is 23;
        //after removing 23rd element count will be 22, which is NOT less than 21 (32 * 1.5)
        //so we should not decrease capacity
        sut.remove(22);
        assertEquals(sut.capacity, 32);
        assertEquals(sut.count, 22);
        assertEquals(sut.getItem(21), 21);
        Assertions.assertThrows(Exception.class, () -> {
            sut.getItem(22);
        });

        //Current capacity is 32 and current count is 22;
        //after removing 22nd element count will be 21, which is NOT less than 21 (32 * 1.5)
        //so we should not decrease capacity
        sut.remove(21);
        assertEquals(sut.capacity, 32);
        assertEquals(sut.count, 21);
        Assertions.assertThrows(Exception.class, () -> {
            sut.getItem(21);
        });

        //Current capacity is 32 and current count is 21;
        //after removing 21st element count will be 20, which is LESS than 21 (32 * 1.5)
        //so we should decrease capacity to 21 (32 * 1.5)
        sut.remove(20);
        assertEquals(sut.capacity, 21);
        assertEquals(sut.count, 20);
        Assertions.assertThrows(Exception.class, () -> {
            sut.getItem(20);
        });

        //Current capacity is 21 and current count is 20;
        //removing elements at positions: 19, 18, 17, 16;
        //comparing capacity with MAX(16, (21 * 1.5) == 14) -> 16)
        //since count in every iteration is not less than 16, capacity should NOT be decreased
        for (int i = 19; i >= 16 ; i--) {
            sut.remove(i);
            assertEquals(sut.capacity, 21);
            final int _i = i;
            Assertions.assertThrows(Exception.class, () -> {
                sut.getItem(_i);
            });
        }
        assertEquals(sut.capacity, 21);
        assertEquals(sut.count, 16);

        //Current capacity is 21 and current count is 16;
        //after removing 16th element count will be 15, which is LESS than 16 (min threshold)
        //so we should decrease capacity to 16
        sut.remove(15);
        assertEquals(sut.capacity, 16);
        assertEquals(sut.count, 15);

        //Removing all remaining elements; capacity should NOT be decreased.
        for (int i = 14; i >= 0; i--) {
            sut.remove(i);
            assertEquals(sut.capacity, 16);
            assertEquals(sut.count, i);
        }
        assertEquals(sut.capacity, 16);
        assertEquals(sut.count, 0);

        //When incorrect index are passed to remove function, error should be thrown
        Assertions.assertThrows(Exception.class, () -> {
            sut.remove(20);
        });
        Assertions.assertThrows(Exception.class, () -> {
            sut.remove(-1);
        });
    }

    private DynArray<Integer> createSUT(int numberOfElements) {
        DynArray<Integer> array = new DynArray<Integer>(Integer.class);
        for (int i = 0; i < numberOfElements; i++) {
            array.append(i);
        }
        return array;
    }

}