import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NativeDictionaryTest {

    @Test
    void test_init() {
        NativeDictionary<String> sut = createSUT();
        sut.put("1", "Test 1");
        sut.put("2", "Test 2");
        sut.put("3", "Test 3");
        sut.put("4", "Test 4");
        sut.put("5", "Test 5");
        sut.put("6", "Test 6");
        sut.put("7", "Test 7");
        sut.put("8", "Test 8");
        sut.put("9", "Test 9");
        sut.put("10", "Test 10");

        assertEquals("Test 1", sut.get("1"));
        assertEquals("Test 2", sut.get("2"));
        assertEquals("Test 3", sut.get("3"));
        assertEquals("Test 4", sut.get("4"));
        assertEquals("Test 5", sut.get("5"));
        assertEquals("Test 6", sut.get("6"));
        assertEquals("Test 7", sut.get("7"));
        assertEquals("Test 8", sut.get("8"));
        assertEquals("Test 9", sut.get("9"));
        assertEquals("Test 10", sut.get("10"));
    }

    private NativeDictionary<String> createSUT() {
        NativeDictionary<String> sut = new NativeDictionary<>(10, String.class);
        return sut;
    }
}