import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"unchecked", "unused"})
public class huffmanFreq<T> {
    private int _size = 0;
    private T[] _key = (T[]) new Object[0];
    private int[] _freq = new int[0];

    /**
     * 默认初始化
     */
    public huffmanFreq() {
    }

    /**
     * 只有一个映射的初始化
     *
     * @param key  键
     * @param freq 频数
     */
    public huffmanFreq(T key, int freq) {
        insert(key, freq);
    }

    /**
     * 用数组方式初始化
     *
     * @param key  键组
     * @param freq 频数组
     */
    public huffmanFreq(T @NotNull [] key, int[] freq) {
        if (key.length == 0 || freq.length == 0) return;
        if (key.length == freq.length) {
            _size = key.length;
            _key = key;
            _freq = freq;
            sortLess();
        } else {
            System.out.println("Unequal length array.");
        }
    }

    /**
     * 获取表的尺寸
     *
     * @return 表尺寸
     */
    public int getSize() {
        return _size;
    }

    /**
     * 按键读取频数
     */
    public int getFreq(T key) {
        for (int i = 0; i < _key.length; i++) {
            if (_key[i] == key) {
                return _freq[i];
            }
        }
        return -1;
    }

    /**
     * 用下标方式读取频数
     *
     * @param posi 位置
     * @return 频数
     */
    public int getFreq(int posi) {
        if (posi <= _size)
            return this._freq[posi - 1];
        else
            return -1;
    }

    /**
     * 在特定位置插入元素，不保证有序
     *
     * @param key  键
     * @param freq 频数
     * @param posi 位置
     */
    public void insertAt(T key, int freq, int posi) {
        T[] newKey = (T[]) new Object[_size + 1];
        int[] newFreq = new int[_size + 1];
        if (posi >= _size) {
            // extend original key
            System.arraycopy(_key, 0, newKey, 0, _size);
            newKey[_size] = key;
            // extend original frequency
            System.arraycopy(_freq, 0, newFreq, 0, _size);
            newFreq[_size] = freq;
        } else {
            // extend original key
            System.arraycopy(_key, 0, newKey, 0, posi);
            newKey[posi] = key;
            System.arraycopy(_key, posi, newKey, posi + 1, _size - posi);
            _key = newKey;
            // extend original frequency
            System.arraycopy(_freq, 0, newFreq, 0, posi);
            newFreq[posi] = freq;
            System.arraycopy(_freq, posi, newFreq, posi + 1, _size - posi);
        }
        //reassign huffmanFreq
        _key = newKey;
        _freq = newFreq;
        //update size
        ++_size;
    }

    /**
     * 插入映射并保持有序
     *
     * @param key  键
     * @param freq 频数
     */
    public void insert(T key, int freq) {
        //if key already exists. add freq
        for (int i = 0; i < _key.length; i++) {
            if (_key[i] == key) {
                _freq[i] += freq;
                sortLess();
                return;
            }
        }
        //else insert according to the frequency (less)
        if (_freq.length == 0) {
            insertAt(key, freq, 1);
        } else {
            for (int posi = 1; posi <= _size; posi++) {
                if (freq < this.getFreq(posi)) {
                    insertAt(key, freq, posi - 1);
                    return;
                }
            }
            insertAt(key, freq, _size);
        }
    }

    /**
     * 只添加键，频数默认加一
     *
     * @param key 键
     */
    public void insert(T key) {
        insert(key, 1);
    }

    /**
     * 交换两对映射的位置，对应关系不变
     *
     * @param posiA A映射的位置
     * @param posiB B映射的位置
     */
    public void swap(int posiA, int posiB) {
        if (posiA <= _size && posiB <= _size && posiA > 0 && posiB > 0) {
            try {
                T tempKey = _key[posiA - 1];
                _key[posiA - 1] = _key[posiB - 1];
                _key[posiB - 1] = tempKey;

                int tempFreq = _freq[posiA - 1];
                _freq[posiA - 1] = _freq[posiB - 1];
                _freq[posiB - 1] = tempFreq;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("位置输入错误！");
            }
        }
    }

    /**
     * 把频数表按频数从小到大排列
     */
    public void sortLess() {
        for (int outer = 1; outer <= _size; outer++) {
            for (int inner = outer; inner <= _size; inner++) {
                if (_freq[inner - 1] < _freq[outer - 1])
                    swap(inner, outer);
            }
        }
    }

    /**
     * 把频数表按频数从大到小排列
     */
    public void sortGreater() {
        for (int outer = 1; outer <= _size; outer++) {
            for (int inner = outer; inner <= _size; inner++) {
                if (_freq[inner - 1] > _freq[outer - 1])
                    swap(inner, outer);
            }
        }
    }

    /**
     * 打印频数表
     */
    public void print() {
        for (int i = 0; i < _size; i++) {
            for (int upper = 0; upper < _size * 3; upper++)
                System.out.print("_");
            System.out.print("\n");
            System.out.println("|\t" + _key[i] + "\t|\t" + _freq[i] + "\t|");
        }
        for (int upper = 0; upper < _size * 3; upper++)
            System.out.print("_");
    }
}
