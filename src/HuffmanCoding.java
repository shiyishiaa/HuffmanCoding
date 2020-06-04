import HuffmanTree.*;

import java.util.*;

enum HuffmanOffset {
    initial(0), up(-1), down(1);

    private final int value;

    HuffmanOffset(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}

public class HuffmanCoding {
    public static void main(String[] args) {
        boolean isFirst = true;
        while (true) {
            String str;
            if (args.length != 0 && isFirst)
                str = args[0];
            else {
                System.out.println("Input String:");
                Scanner sc = new Scanner(System.in);
                str = sc.nextLine();
            }
            huffmanCoding(str);
            System.out.println("Continue?(Y/N)");
            Scanner Continue = new Scanner(System.in);
            if ("N".equals(Continue.nextLine()))
                break;
            else
                isFirst = false;
        }
    }

    /**
     * 计算字符串中各个字符的频数
     *
     * @param str 字符串
     * @return 字符频数映射表
     */
    public static huffmanFreq charFreq(String str) {
        if (str == null) return null;
        huffmanFreq charHuffmanFreq = new huffmanFreq();
        while (!str.isEmpty()) {
            charHuffmanFreq.insertRugged(str.substring(0, 1));
            str = str.substring(1);
        }
        return charHuffmanFreq;
    }

    /**
     * 把Huffman树转化为Huffman映射表
     *
     * @param flow 树（String形式）
     * @return Huffman映射表
     */
    public static Map<String, String> flowToCode(String flow) {
        if (flow == null) return null;
        Map<String, String> codeMap = new HashMap<>();
        String[] keys = flow.substring(4, flow.length() - 4).split("null");
        for (String key : keys) codeMap.put(key, "");
        int rootIndex = findLongest(keys);
        coding(keys, codeMap, HuffmanOffset.initial.value(), keys[rootIndex]);
        for (String key : keys) if (key.length() != 1) codeMap.remove(key);
        return codeMap;
    }

    /**
     * 返回字符串数组中首个最长字符串的下标和长度
     * 没有找到就返回-1
     *
     * @param strings 字符串数组
     * @return 下标和长度
     */
    public static int findLongest(String[] strings) {
        if (strings == null) return -1;
        int index = 0, length = 0;
        for (int i = 1; i < strings.length; i++) {
            if (strings[i].length() > length) {
                length = strings[i].length();
                index = i;
            }
        }
        return index;
    }

    /**
     * 对Huffman数组进行编码
     *
     * @param strings           Huffman树组
     * @param map               储存映射表
     * @param offset            偏移值，初始恒为HUFFMAN_INITIAL_OFFSET（0）
     * @param lastLongestString 上次最长的字符串
     */
    private static void coding(String[] strings, Map<String, String> map, int offset, String lastLongestString) {
        if (strings == null) return;
        if (strings.length == 1 && strings[0].equals(lastLongestString)) {
            map.put(strings[0], Math.random() > 0.5 ? "1" : "0");
            return;
        }
        // 查找字符串出现位置
        int longestIndex = findLongest(strings);
        // 根据偏移量来判断新的节点的编码
        switch (offset) {
            case 0 -> map.put(strings[longestIndex],
                    "");
            case -1 -> map.put(strings[longestIndex],
                    map.get(lastLongestString) +
                            "1");
            case 1 -> map.put(strings[longestIndex],
                    map.get(lastLongestString) +
                            "0");
        }
        // 该节点不是单字节，则把原字符串数组按最长位置分开成两部分，分别递归
        if (strings.length != 1) {
            // 上段数组和下段数组
            String[] up = new String[longestIndex],
                    down = new String[strings.length - longestIndex - 1];
            // 复制原数组内容
            System.arraycopy(strings, 0, up, 0, up.length);
            System.arraycopy(strings, longestIndex + 1, down, 0, down.length);
            // 递归
            coding(up, map, HuffmanOffset.up.value(), strings[longestIndex]);
            coding(down, map, HuffmanOffset.down.value(), strings[longestIndex]);
        }
    }

    /**
     * 打印Huffman映射
     *
     * @param map Huffman映射
     */
    public static void printHuffmanMap(Map<String, String> map) {
        if (map == null) System.out.println("Empty Map");
        assert map != null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("\t" + entry.getKey() + "\t→\t" + entry.getValue());
        }
    }

    /**
     * 将字符串查表转换为Huffman编码
     *
     * @param str 字符串
     * @param map Huffman映射
     * @return Huffman编码
     */
    public static String huffmanCoding(String str, Map<String, String> map) {
        if (str == null) return "WRONG STRING";
        String huffmanStr = "";
        while (str.length() > 0) {
            if (!map.getOrDefault(str.substring(0, 1), "NOT_FOUND").
                    equals("NOT_FOUND")) {
                huffmanStr += map.get(str.substring(0, 1));
                str = str.substring(1);
            } else return "WRONG MAP";
        }
        return huffmanStr;
    }

    /**
     * 将字符串转化为Huffman编码并打印
     *
     * @param str 字符串
     * @return Huffman编码
     */
    @SuppressWarnings("UnusedReturnValue")
    public static String huffmanCoding(String str) {
        huffmanFreq charFreq = charFreq(str);
        huffmanTree charTree = new huffmanTree(charFreq);
        String treeString = huffmanTree.traverseIn_Stack(charTree.getRoot());
        Map<String, String> code = flowToCode(treeString);
        printHuffmanMap(code);
        System.out.println("Original string: " + str);
        System.out.println("Huffman coding: " + huffmanCoding(str, code));
        return huffmanCoding(str, code);
    }
}