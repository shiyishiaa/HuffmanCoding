package HuffmanTree;

public class huffmanFreq {
    private int size;
    private String[] key;
    private int[] freq;

    /**
     * 默认初始化
     */
    public huffmanFreq() {
        this.size = 0;
        this.freq = new int[0];
        this.key = new String[0];
    }

    /**
     * 获取表的尺寸
     *
     * @return 表尺寸
     */
    public int getSize() {
        return this.size;
    }

    /**
     * 复制字符频数映射表
     *
     * @param freq 源表
     * @return 复制结果
     */
    public static huffmanFreq copy(huffmanFreq freq) {
        if (freq == null) return null;
        huffmanFreq newFreq = new huffmanFreq();
        newFreq.size = freq.getSize();
        newFreq.freq = freq.getFreq();
        newFreq.key = freq.getKey();
        return newFreq;
    }

    /**
     * 获取频数数组
     *
     * @return 频数数组
     */
    public int[] getFreq() {
        return this.freq;
    }

    /**
     * 获取键数组
     *
     * @return 键数组
     */
    public String[] getKey() {
        return this.key;
    }

    /**
     * 用下标方式读取频数
     *
     * @param posi 位置
     * @return 频数
     */
    public int getFreq(int posi) {
        if (posi <= this.size)
            return this.freq[posi - 1];
        else
            return -1;
    }

    /**
     * 用下标方式读取键
     *
     * @param posi 位置
     * @return 键
     */
    public String getKey(int posi) {
        return this.key[posi - 1];
    }

    /**
     * 在特定位置插入元素，不保证有序
     *
     * @param key  键
     * @param freq 频数
     * @param posi 位置
     */
    public void insertAt(String key, int freq, int posi) {
        String[] newKey = new String[this.size + 1];
        int[] newFreq = new int[this.size + 1];
        if (posi >= this.size) {
            // extend original key
            System.arraycopy(this.key, 0, newKey, 0, this.size);
            newKey[this.size] = key;
            // extend original frequency
            System.arraycopy(this.freq, 0, newFreq, 0, this.size);
            newFreq[this.size] = freq;
        } else {
            // extend original key
            System.arraycopy(this.key, 0, newKey, 0, posi);
            newKey[posi] = key;
            System.arraycopy(this.key, posi, newKey, posi + 1, this.size - posi);
            this.key = newKey;
            // extend original frequency
            System.arraycopy(this.freq, 0, newFreq, 0, posi);
            newFreq[posi] = freq;
            System.arraycopy(this.freq, posi, newFreq, posi + 1, this.size - posi);
        }
        //reassign huffmanTree.huffmanFreq
        this.key = newKey;
        this.freq = newFreq;
        //update size
        ++this.size;
    }

    /**
     * 插入映射并保持有序(插入在同频数字符前)
     *
     * @param key  键
     * @param freq 频数
     */
    @SuppressWarnings("unused")
    public void insertRugged(String key, int freq) {
        //if key already exists. add freq
        for (int i = 0; i < this.key.length; i++) {
            if (this.key[i].equals(key)) {
                this.freq[i] += freq;
                sortLess();
                return;
            }
        }
        //else insertRugged according to the frequency (less)
        if (this.freq.length == 0) {
            insertAt(key, freq, 1);
        } else {
            for (int posi = 1; posi <= this.size; posi++) {
                if (freq <= this.getFreq(posi)) {
                    insertAt(key, freq, posi - 1);
                    return;
                }
            }
            insertAt(key, freq, this.size);
        }
    }

    /**
     * 插入映射并保持有序(插入在同频数字符后)
     *
     * @param key  键
     * @param freq 频数
     */
    @SuppressWarnings("unused")
    public void insertAverage(String key, int freq) {
        //if key already exists. add freq
        for (int i = 0; i < this.key.length; i++) {
            if (this.key[i].equals(key)) {
                this.freq[i] += freq;
                sortLess();
                return;
            }
        }
        //else insertRugged according to the frequency (less)
        if (this.freq.length == 0) {
            insertAt(key, freq, 1);
        } else {
            for (int posi = 1; posi <= this.size; posi++) {
                if (freq < this.getFreq(posi)) {
                    insertAt(key, freq, posi - 1);
                    return;
                }
            }
            insertAt(key, freq, this.size);
        }
    }

    /**
     * 只添加键，频数默认加一(Rugged方式)
     *
     * @param key 键
     */
    @SuppressWarnings("unused")
    public void insertRugged(String key) {
        insertRugged(key, 1);
    }

    /**
     * 只添加键，频数默认加一(Average方式)
     *
     * @param key 键
     */
    @SuppressWarnings("unused")
    public void insertAverage(String key) {
        insertAverage(key, 1);
    }

    /**
     * 查找键的位置
     *
     * @param key 键
     * @return 键位置，未找到返回-1
     */
    public int contain(String key) {
        for (int i = 0; i < this.size; i++) {
            if (this.key[i].equals(key))
                return i + 1;
        }
        return -1;
    }

    /**
     * 交换两对映射的位置，对应关系不变
     *
     * @param posiA A映射的位置
     * @param posiB B映射的位置
     */
    public void swap(int posiA, int posiB) {
        if (posiA <= this.size && posiB <= this.size && posiA > 0 && posiB > 0) {
            try {
                String newKey = this.key[posiA - 1];
                this.key[posiA - 1] = this.key[posiB - 1];
                this.key[posiB - 1] = newKey;

                int newFreq = this.freq[posiA - 1];
                this.freq[posiA - 1] = this.freq[posiB - 1];
                this.freq[posiB - 1] = newFreq;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("位置输入错误！");
            }
        }
    }

    /**
     * 把频数表按频数从小到大排列
     */
    public void sortLess() {
        for (int outer = 1; outer <= this.size; outer++) {
            for (int inner = outer; inner <= this.size; inner++) {
                if (this.freq[inner - 1] < this.freq[outer - 1])
                    swap(inner, outer);
            }
        }
    }

    /**
     * 在特定范围删除元素，不保证有序
     *
     * @param startPosi 起始位置
     * @param endPosi   结束位置
     */
    public void removeAmong(int startPosi, int endPosi) {
        int start = Math.min(startPosi, endPosi), end = Math.max(startPosi, endPosi);
        if (start > this.size || end <= 0) return;
        else if (end > this.size) end = this.size;
        else if (start <= 0) start = 1;

        int range = end - start + 1;
        String[] newKey = new String[this.size - range];
        int[] newFreq = new int[this.size - range];
        // shrink original key
        System.arraycopy(this.key, 0, newKey, 0, start - 1);
        System.arraycopy(this.key, end, newKey, start - 1, this.size - start - range + 1);
        // shrink original frequency
        System.arraycopy(this.freq, 0, newFreq, 0, start - 1);
        System.arraycopy(this.freq, end, newFreq, start - 1, this.size - start - range + 1);
        //reassign huffmanTree.huffmanFreq
        this.key = newKey;
        this.freq = newFreq;
        //update size
        this.size -= range;
    }
}
