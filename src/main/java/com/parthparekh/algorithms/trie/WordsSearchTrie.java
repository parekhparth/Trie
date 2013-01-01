package com.parthparekh.algorithms.trie;

import java.io.*;
import java.util.*;

/**
 * Implementation of Trie used for searching words from dictionary with wild card (?) characters
 * The difference in the implementation of this trie is that there are as many tries as the number of size of words
 * for e.g. words bank and banks will be in two different tries
 *
 * @author: Parth Parekh (parthparekh [at] gatech [dot] edu)
 */
public class WordsSearchTrie implements Trie {

    protected Map<Integer, TrieNode> trieMap;

    public WordsSearchTrie() {
        trieMap = new HashMap<Integer, TrieNode>();
    }

    @Override
    public boolean addString(String wordString) {
        if (wordString==null || wordString.isEmpty()) {
            return false;
        }
        wordString = wordString.toLowerCase();
        // initialize the root node for the trie with word length
        TrieNode rootNode = null;
        if(trieMap.get(wordString.length())==null) {
            rootNode = new TrieNode();
            trieMap.put(wordString.length(), rootNode);
        }
        else {
            rootNode = trieMap.get(wordString.length());
        }
        char[] wordCharArr = wordString.toCharArray();
        TrieNode node = rootNode;
        for (char charValue : wordCharArr) {
            node.addChild(charValue);
            node = node.getChild(charValue);
        }
        node.setFinalChar(true);
        return true;
    }

    @Override
    public Set<Character> getNextCharacters(String prefixString) {
        throw new UnsupportedOperationException(
                "remove string not yet supported for WordsSearchTrie implementation");
    }

    @Override
    public boolean removeString(String wordString) {
        throw new UnsupportedOperationException(
                "remove string not yet supported for WordsSearchTrie implementation");
    }

    @Override
    public boolean searchString(String wordString) {
        if (wordString.isEmpty()) {
            return false;
        }
        wordString = wordString.toLowerCase();
        TrieNode rootNode = null;
        if(trieMap.get(wordString.length())==null) {
            return false;
        }
        rootNode = trieMap.get(wordString.length());
        char[] wordCharArr = wordString.toCharArray();
        TrieNode node = rootNode;
        for (char charValue : wordCharArr) {
            node = node.getChild(charValue);
            if (node == null) {
                return false;
            }
        }
        if (node.isFinalChar()) {
            // return true only if current character is final character for the
            // string i.e. don't return true for subset strings
            return true;
        }
        return false;
    }

    /*
     * loads the words from wordlist file into trie;
     * it assumes the wordlist file contains words delimited by newline
     *
     * @param filePath absolute file path of the wordlist file
     */
    public void loadTrie(String filePath) throws IOException {

        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("wordlist file path invalid");
        }

        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String word;
            while ((word = reader.readLine()) != null) {
                assert word != null;
                word = word.replaceAll("\\s", "").toLowerCase();
                addString(word);
            }
        }
        catch (IOException ioException) {
  			throw ioException;
        }
    }

    // checks if string passed is a valid string for word solver trie
    // i.e. only consisting of '?' and alphanumeric characters
    protected boolean isValidString(String wordString) {
        if (wordString == null || wordString.isEmpty()) {
            return false;
        }
        wordString = wordString.replaceAll("[^?0-9A-Za-z]", "");
        return wordString.length() > 0 ? true : false;
    }

    /*
     * finds all the words that satisfy the wordString format
     *
     * @param wordString string of characters including wild card character '?'
     * @return returns set of all strings that satisfy the wordString format, null otherwise
     */
    public Set<String> findAllWords(String wordString) {
        if (!isValidString(wordString)) {
            return null;
        }
        wordString = wordString.toLowerCase();
        TrieNode rootNode = null;
        if(trieMap.get(wordString.length())==null) {
            return null;
        }
        rootNode = trieMap.get(wordString.length());
        char[] wordChars = wordString.toCharArray();
        Map<TrieNode, StringBuffer> trieNodeStringBufferMap = new HashMap<TrieNode, StringBuffer>();
        for(int index=0; index<wordChars.length; index++) {
            char charValue = wordChars[index];
            trieNodeStringBufferMap = updateTrieNodeMap(index, charValue, rootNode, trieNodeStringBufferMap);
        }

        Set<String> wordSet = new HashSet<String>();
        for (StringBuffer stringBuffer : trieNodeStringBufferMap.values()) {
            wordSet.add(stringBuffer.toString());
        }

        return wordSet;
    }

    /*
     * updates the trieNodeStringBufferMap with the current trieNode information
     * and updates ths StringBuffer with the words formed
     */
    private Map<TrieNode, StringBuffer> updateTrieNodeMap(int index, char charValue, TrieNode rootNode,
                                                          Map<TrieNode,StringBuffer> trieNodeStringBufferMap) {
        assert trieNodeStringBufferMap!=null;
        assert rootNode!=null;
        if (index == 0) {
            // assuming the node is rootNode
            assert rootNode.getCharValue()=='\u0000';
            Set<TrieNode> trieChildNodes = getNodeChildren(charValue, rootNode);
            for (TrieNode node : trieChildNodes){
                StringBuffer stringBuffer = new StringBuffer();
                trieNodeStringBufferMap.put(node, stringBuffer.append(node.getCharValue()));
            }
            return trieNodeStringBufferMap;
        }

        Map<TrieNode, StringBuffer> newTrieNodeStringBufferMap = new HashMap<TrieNode, StringBuffer>();
        for (TrieNode node : trieNodeStringBufferMap.keySet()) {
            Set<TrieNode> trieChildNodes = getNodeChildren(charValue, node);
            if (trieChildNodes == null) {
                continue;
            }
            StringBuffer stringBuffer = trieNodeStringBufferMap.get(node);
            for (TrieNode childNode : trieChildNodes) {
                StringBuffer newStringBuffer = new StringBuffer();
                newStringBuffer.append(stringBuffer);
                newStringBuffer.append(childNode.getCharValue());
                newTrieNodeStringBufferMap.put(childNode, newStringBuffer);
            }
        }

        return newTrieNodeStringBufferMap;
    }

    /*
     * retrieves all the children for charValue
     * if charValue is '?' it retrieves all the children
     */
    private Set<TrieNode> getNodeChildren(char charValue, TrieNode node) {
        assert node!=null;
        if (charValue == '?') {
            return node.getChildrenNodes();
        }
        Set<TrieNode> trieNodes = new HashSet<TrieNode>();
        TrieNode childNode = node.getChild(charValue);
        if (childNode == null) {
            return null;
        }
        trieNodes.add(childNode);
        return trieNodes;
    }

    /*
     * prints usage instructions
     */
    private static void usage() {

        System.out.println("Usage:");
        System.out.println("\tjava -cp Trie.jar com.parthparekh.algorithms.trie.WordsSearchTrie " +
                                    "<absolute_path_to_wordlist_file> <wildcard_word_to_search>");
        System.out.println("\t(use '?' as wild card character)");
        System.out.println("");
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            usage();
            System.exit(1);
        }
        String wordlistPath = args[0];
        assert !wordlistPath.isEmpty();
        WordsSearchTrie wordsSearchTrie = new WordsSearchTrie();
        wordsSearchTrie.loadTrie(wordlistPath);

        String word = args[1];
        assert !word.isEmpty();

        Set<String> validWords = wordsSearchTrie.findAllWords(word);
        System.out.println("Total " + validWords.size() + " matching words found for: " + word);
        System.out.println();
        int count = 0;
        for (String setWord : validWords) {
            System.out.println("" + ++count + ". " + setWord);
        }
        System.out.println();
    }
}
