public class huffmanCoding {
    public static void main(String[] args) {
        String originString = args[0];
        huffmanFreq<Character> charFreq = charFreq(originString);
        charFreq.print();
    }

    static huffmanFreq<Character> charFreq(String str) {
        String tempString = str;
        huffmanFreq<Character> charHuffmanFreq = new huffmanFreq<Character>();
        while (!tempString.isEmpty()) {
            charHuffmanFreq.insert(tempString.charAt(0));
            tempString = tempString.substring(1);
        }
        return charHuffmanFreq;
    }
}