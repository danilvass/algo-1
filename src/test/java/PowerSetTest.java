import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PowerSetTest {

    @Test
    void test_basicMethods() {
        PowerSet sut = createSUT();
        assertEquals(0, sut.size());

        assertFalse(sut.get(""));
        assertFalse(sut.get("1"));
        assertFalse(sut.get("-1"));

        assertFalse(sut.remove("1"));
        assertFalse(sut.remove("1"));
        assertFalse(sut.remove("1"));

        sut.put("1");
        sut.put("1");
        sut.put("1");

        assertEquals(1, sut.size());
        assertTrue(sut.get("1"));

        assertTrue(sut.remove("1"));
        assertEquals(0, sut.size());
        assertFalse(sut.get("1"));

        for (int i = 0; i < 20000; i++) {
            assertFalse(sut.get(String.valueOf(i)));
            sut.put(String.valueOf(i));
            assertTrue(sut.get(String.valueOf(i)));
        }

        assertEquals(20000, sut.size());

        for (int i = 19999; i >= 0; i--) {
            String v = String.valueOf(i);
            assertTrue(sut.get(v));
            assertTrue(sut.remove(v));
            assertFalse(sut.remove(v));
            assertFalse(sut.get(v));
        }
        assertEquals(0, sut.size());
    }

    @Test
    void test_intersection() {
        PowerSet sut = createSUT();
        assertEquals(0, sut.intersection(new PowerSet()).size());

        for (int i = 0; i < 10000; i++) {
            sut.put(String.valueOf(i));
        }

        PowerSet toIntersect = createSUT();
        for (int i = 5000; i < 10000; i++) {
            toIntersect.put(String.valueOf(i));
        }

        PowerSet result = sut.intersection(toIntersect);
        assertEquals(5000, result.size());

        for (int i = 0; i < 5000; i++) {
            assertFalse(result.get(String.valueOf(i)));
        }
        for (int i = 5000; i < 10000; i++) {
            assertTrue(result.get(String.valueOf(i)));
        }

        assertEquals(5000, result.intersection(result).size());
    }

    @Test
    void test_union() {
        PowerSet sut = createSUT();
        PowerSet secondSut = createSUT();
        assertEquals(0, sut.union(secondSut).size());

        for (int i = 0; i < 10000; i++) {
            sut.put(String.valueOf(i));
        }

        assertEquals(10000, sut.union(secondSut).size());

        for (int i = 10000; i < 20000; i++) {
            secondSut.put(String.valueOf(i));
        }
        PowerSet union = sut.union(secondSut);
        assertEquals(20000, union.size());

        for (int i = 0; i < 20000; i++) {
            assertTrue(union.get(String.valueOf(i)));
        }
    }

    @Test
    void test_diff() {
        PowerSet sut = createSUT();
        PowerSet secondSut = createSUT();
        assertEquals(0, sut.difference(secondSut).size());

        for (int i = 0; i < 10000; i++) {
            sut.put(String.valueOf(i));
        }
        assertEquals(10000, sut.difference(secondSut).size());

        for (int i = 10000; i < 20000; i++) {
            secondSut.put(String.valueOf(i));
        }

        PowerSet diff = sut.difference(secondSut);

        assertEquals(20000, diff.size());

        for (int i = 0; i < 20000; i++) {
            assertTrue(diff.get(String.valueOf(i)));
        }
    }

    @Test
    void test_isSubset() {
        PowerSet sut = createSUT();
        PowerSet secondSut = createSUT();

        for (int i = 0; i < 10000; i++) {
            sut.put(String.valueOf(i));
        }
        for (int i = 5000; i < 10000; i++) {
            secondSut.put(String.valueOf(i));
        }

        assertTrue(sut.isSubset(secondSut));
        secondSut.put("10000");
        assertFalse(sut.isSubset(secondSut));
    }

    private PowerSet createSUT() {
        return new PowerSet();
    }

}