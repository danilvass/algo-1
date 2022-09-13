import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BloomFilterTest {

    @Test
    void test_bitmask() {
        BloomFilter sut = createSUT();
        String[] values = new String[]{
                "0123456789", "1234567890", "2345678901",
                "3456789012", "4567890123", "5678901234",
                "6789012345", "7890123456", "8901234567",
                "9012345678"
        };

        for (String value: values) {
            sut.add(value);
            assertTrue(sut.isValue(value));
        }
    }

    private BloomFilter createSUT() {
        return new BloomFilter(32);
    }

}
