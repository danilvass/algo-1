public class BloomFilter {
    public int filter_len;

    private int bitSet;

    public BloomFilter(int f_len) {
        filter_len = f_len;
        bitSet = 0;
    }

    public int getBitSet() {
        return bitSet;
    }

    private int calculateHashSum(String str1, int random) {
        int res = 1;
        for (int i = 0; i < str1.length(); i++) {
            int code = (int) str1.charAt(i);
            res = ((res * random) + code) % filter_len;
        }
        return res;
    }

    public int hash1(String str1) {
        return calculateHashSum(str1, 17);
    }

    public int hash2(String str1) {
        return calculateHashSum(str1, 223);
    }

    public void add(String str1) {
        int index1 = hash1(str1);
        int index2 = hash2(str1);

        bitSet = setKthBit(bitSet, index1);
        bitSet = setKthBit(bitSet, index2);
    }

    public boolean isValue(String str1) {
        int index1 = hash1(str1);
        int index2 = hash2(str1);

        return getBit(index1, bitSet) || getBit(index2, bitSet);
    }

    private int setKthBit(int n, int k) {
        return ((1 << k) | n);
    }

    private int unsetKthBit(int n, int k) {
        return n & ~(1 << k);
    }

    private boolean getBit(int position, int bitSet) {
        return ((bitSet >> position) & 1) == 1;
    }

}
