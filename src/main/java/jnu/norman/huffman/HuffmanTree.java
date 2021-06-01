package jnu.norman.huffman;

public class HuffmanTree {
    private final HuffmanTreeNode root;

    /**
     * 用字符频数映射表初始话Huffman树
     *
     * @param freq 字符频数映射表
     */
    public HuffmanTree(HuffmanFreq freq) {
        this.root = HuffmanTreeNode.convertHuffmanTree(freq, HuffmanTreeNode.InsertMode.RUGGED);
    }

    /**
     * 获取根节点
     *
     * @return 根节点
     */
    public HuffmanTreeNode getRoot() {
        return this.root;
    }

    /**
     * 迭代方式中序遍历，并获得Huffman树（String形式）
     *
     * @param node 树的节点
     * @return Huffman树（String形式）
     */
    public static String traverseInStack(HuffmanTreeNode node) {
        return HuffmanTreeNode.traverseInStack(node);
    }
}
