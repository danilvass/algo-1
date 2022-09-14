import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NativeCacheTest {

    @Test
    void test_simpleCases() {
        NativeCache<String> sut = createSUT(5);
        assertNull(sut.getValue("1"));
        sut.put("1", "value 1");
        assertEquals(1, sut.getCount());
        assertEquals("value 1", sut.getValue("1"));
        sut.getValue("1");
        sut.getValue("1");
        sut.getValue("1");

        sut.put("2", "value 2");
        assertEquals("value 2", sut.getValue("2"));
        assertEquals(2, sut.getCount());

        sut.put("3", "value 3");
        assertEquals("value 3", sut.getValue("3"));
        assertEquals(3, sut.getCount());

        sut.put("4", "value 4");
        assertEquals("value 4", sut.getValue("4"));
        assertEquals(4, sut.getCount());

        sut.put("5", "value 5");
        assertEquals(5, sut.getCount());


        sut.put("6", "value 6");
        assertEquals(5, sut.getCount());
    }

    @Test
    void test_hitsIncrementingAndRemoving() {
        NativeCache<String> sut = createSUT(5);
        for (int i = 0; i < 5; i++) {
            sut.put("1", "value 1");
        }
        assertEquals(1, sut.getCount());

        for (int i = 2; i <= 5; i++) {
            sut.put(String.valueOf(i), "value " + i);
        }
        assertEquals(5, sut.getCount());

        for (int i = 1; i <= 5; i++) {
            sut.getValue(String.valueOf(i));
        }

        for (int i = 1; i <= 5; i++) {
            assertEquals(1, sut.inspectEntry(String.valueOf(i)).hits);
        }

        sut.getValue("1");
        sut.getValue("1");
        sut.getValue("1");

        sut.getValue("2");
        sut.getValue("2");

        sut.getValue("5");
        sut.getValue("5");

        sut.getValue("3");

        assertEquals(4, sut.inspectEntry("1").hits);
        assertEquals(3, sut.inspectEntry("2").hits);
        assertEquals(3, sut.inspectEntry("5").hits);
        assertEquals(2, sut.inspectEntry("3").hits);
        assertEquals(1, sut.inspectEntry("4").hits);

        sut.put("6", "value 6");
        assertNull(sut.getValue("4")); //Should remove 4 because of min hits
    }

    @Test
    void test_removing() {
        NativeCache<String> sut = createSUT(5);
        sut.remove("1");

        sut.put("1", "value 1");
        sut.remove("2");
        assertEquals(1, sut.getCount());
        sut.remove("1");
        assertEquals(0, sut.getCount());
    }

    private NativeCache<String> createSUT(int size) {
        return new NativeCache<>(size);
    }

}
