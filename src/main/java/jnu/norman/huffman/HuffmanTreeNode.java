package jnu.norman.huffman;

import java.util.ArrayDeque;


public class HuffmanTreeNode {
    private HuffmanTreeNode left;
    private HuffmanTreeNode right;
    private String str;

    /**
     * Huffman节点默认初始化
     */
    private HuffmanTreeNode() {
    }

    /**
     * Huffman节点用字符串初始化
     *
     * @param str 字符串
     */
    public HuffmanTreeNode(String str) {
        this.str = str;
        left = right = new HuffmanTreeNode();
    }

    /**
     * 迭代方式中序遍历，并获得Huffman树（String形式）
     *
     * @param node 遍历的节点
     * @return Huffman树（String形式）
     */
    public static String traverseInStack(HuffmanTreeNode node) {
        if (node == null) return null;
        var treeString = new StringBuilder();
        var pointer = node;
        var stack = new ArrayDeque<HuffmanTreeNode>();
        while (!stack.isEmpty() || pointer != null) {
            if (pointer != null) {
                stack.push(pointer);
                pointer = pointer.left;
            } else {
                pointer = stack.pop();
                treeString.append(pointer.str);
                pointer = pointer.right;
            }
        }
        return treeString.toString();
    }

    /**
     * 把字符频数映射表转化为Huffman树
     *
     * @param freq 字符频数映射表
     * @return Huffman树的根节点
     */
    public static HuffmanTreeNode convertHuffmanTree(HuffmanFreq freq, InsertMode mode) {
        if (freq == null) return null;
        //把键存入节点数组中
        var nodeFamily = new HuffmanTreeNode[freq.getSize()];
        for (var posi = 1; posi <= nodeFamily.length; posi++) {
            nodeFamily[posi - 1] = new HuffmanTreeNode(freq.getKey(posi));
        }
        var newFreq = HuffmanFreq.copy(freq);
        //迭代法生成Huffman树
        while (nodeFamily.length != 1) {
            var newNode = new HuffmanTreeNode(nodeFamily[0].getStr() + nodeFamily[1].getStr());
            newNode.setLeft(nodeFamily[0]);
            newNode.setRight(nodeFamily[1]);

            if (mode == InsertMode.RUGGED) {
                newFreq.insertRugged(
                        newFreq.getKey(1) + newFreq.getKey(2),
                        newFreq.getFreq(1) + newFreq.getFreq(2));
            } else if (mode == InsertMode.AVERAGE) {
                newFreq.insertAverage(
                        newFreq.getKey(1) + newFreq.getKey(2),
                        newFreq.getFreq(1) + newFreq.getFreq(2));
            }
            newFreq.removeAmong(1, 2);

            var posi = newFreq.contain(newNode.getStr());

            var newNodeFamily = new HuffmanTreeNode[nodeFamily.length - 1];
            System.arraycopy(nodeFamily, 2, newNodeFamily, 0, posi - 1);
            newNodeFamily[posi - 1] = newNode;
            System.arraycopy(nodeFamily, posi + 1, newNodeFamily, posi, newNodeFamily.length - posi);
            nodeFamily = newNodeFamily;
        }
        return nodeFamily[0];
    }

    /**
     * 设置节点左分支
     *
     * @param left 左分支
     */
    public void setLeft(HuffmanTreeNode left) {
        this.left = left;
    }

    /**
     * 设置节点右分支
     *
     * @param right 左分支
     */
    public void setRight(HuffmanTreeNode right) {
        this.right = right;
    }

    /**
     * 获取节点字符串
     *
     * @return 字符串
     */
    public String getStr() {
        return this.str;
    }

    /**
     * 判断节点是否为空
     *
     * @return 结果
     */
    public boolean hasData() {
        return this.str != null;
    }

    public enum InsertMode {
        AVERAGE, RUGGED
    }
}
