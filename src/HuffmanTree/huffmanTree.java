package HuffmanTree;

public class huffmanTree {
    private final huffmanTreeNode root;

    /**
     * 用字符频数映射表初始话Huffman树
     *
     * @param freq 字符频数映射表
     */
    public huffmanTree(huffmanFreq freq) {
        this.root = huffmanTreeNode.convertHuffmanTree(freq, insertMode.RUGGED);
    }

    /**
     * 获取根节点
     *
     * @return 根节点
     */
    public huffmanTreeNode getRoot() {
        return this.root;
    }

    /**
     * 迭代方式中序遍历，并获得Huffman树（String形式）
     *
     * @param node 树的节点
     * @return Huffman树（String形式）
     */
    public static String traverseIn_Stack(huffmanTreeNode node) {
        return huffmanTreeNode.traverseIn_Stack(node);
    }
}
