package HuffmanTree;

import java.util.Stack;

public class huffmanTreeNode {
    private huffmanTreeNode left, right;
    private String str;

    /**
     * Huffman节点默认初始化
     */
    public huffmanTreeNode() {

    }

    /**
     * Huffman节点用字符串初始化
     *
     * @param str 字符串
     */
    public huffmanTreeNode(String str) {
        this.str = str;
        left = right = new huffmanTreeNode();
    }

    /**
     * 设置节点左分支
     *
     * @param left 左分支
     */
    public void setLeft(huffmanTreeNode left) {
        this.left = left;
    }

    /**
     * 设置节点右分支
     *
     * @param right 左分支
     */
    public void setRight(huffmanTreeNode right) {
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
        return !(this.str == null);
    }

    /**
     * 迭代方式中序遍历，并获得Huffman树（String形式）
     *
     * @param node 遍历的节点
     * @return Huffman树（String形式）
     */
    public static String traverseIn_Stack(huffmanTreeNode node) {
        if (!node.hasData()) return null;
        StringBuilder treeString = new StringBuilder();
        huffmanTreeNode cur = node;
        Stack<huffmanTreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                treeString.append(cur.str);
                cur = cur.right;
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
    public static huffmanTreeNode convertHuffmanTree(huffmanFreq freq) {
        if (freq == null) return null;
        //把键存入节点数组中
        huffmanTreeNode[] nodeFamily = new huffmanTreeNode[freq.getSize()];
        for (int posi = 1; posi <= nodeFamily.length; posi++) {
            nodeFamily[posi - 1] = new huffmanTreeNode(freq.getKey(posi));
        }
        huffmanFreq newFreq = huffmanFreq.copy(freq);
        //迭代法生成Huffman树
        while (!(nodeFamily.length == 1)) {
            huffmanTreeNode newNode = new huffmanTreeNode(nodeFamily[0].getStr() + nodeFamily[1].getStr());
            newNode.setLeft(nodeFamily[0]);
            newNode.setRight(nodeFamily[1]);

            newFreq.insert(newFreq.getKey(1) + newFreq.getKey(2),
                    newFreq.getFreq(1) + newFreq.getFreq(2));
            newFreq.removeAmong(1, 2);

            int posi = newFreq.contain(newNode.getStr());

            huffmanTreeNode[] newNodeFamily = new huffmanTreeNode[nodeFamily.length - 1];
            System.arraycopy(nodeFamily, 2, newNodeFamily, 0, posi - 1);
            newNodeFamily[posi - 1] = newNode;
            System.arraycopy(nodeFamily, posi + 1, newNodeFamily, posi, newNodeFamily.length - posi);
            nodeFamily = newNodeFamily;
        }
        return nodeFamily[0];
    }
}