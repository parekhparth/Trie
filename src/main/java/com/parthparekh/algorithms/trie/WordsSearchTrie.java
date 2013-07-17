package com.parthparekh.algorithms.trie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of Trie used for searching words from dictionary with wild card (? and *) characters
 *
 * @author: Parth Parekh (parthparekh [at] gatech [dot] edu)
 */
public class WordsSearchTrie implements Trie {
    protected TrieNode trieRoot;
    public WordsSearchTrie() {
        trieRoot = new TrieNode();
    }

    @Override
    public boolean addString(String wordString) {
        if (wordString==null || wordString.isEmpty()) {
            return false;
        }
        wordString = wordString.toLowerCase();
        char[] wordCharArr = wordString.toCharArray();
        TrieNode tempRoot = trieRoot;
        for (char charValue : wordCharArr) {
            tempRoot.addChild(charValue);
            tempRoot = tempRoot.getChild(charValue);
        }
        tempRoot.setFinalChar(true);
        return true;
    }

    @Override
    public char[] getNextCharacters(String prefixString) {
        throw new UnsupportedOperationException(
                "remove string not yet supported for WordsSearchTrie implementation");
    }

    @Override
    public boolean removeString(String wordString) {
        throw new UnsupportedOperationException(
                "remove string not yet supported for WordsSearchTrie implementation");
    }

    @Override
    public boolean hasString(String wordString) {
        if (wordString.isEmpty()) {
            return false;
        }
        wordString = wordString.toLowerCase();
        TrieNode node = trieRoot;
        char[] wordCharArr = wordString.toCharArray();
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
            @SuppressWarnings("resource")
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
    // i.e. only consisting of '?', '*' and alphanumeric characters
    protected boolean isValidString(String wordString) {
        if (wordString == null || wordString.isEmpty()) {
            return false;
        }
        String newWordString = wordString.replaceAll("[^*?0-9A-Za-z]", "");
        return wordString.trim().length() == newWordString.trim().length() ? true : false;
    }

    // replace multiple asterisk with single one
    protected String preProcessWord(String wordString) {
        if (wordString == null || wordString.isEmpty() || !isAsterixPresent(wordString)) {
            return wordString;
        }
        StringBuffer buf = new StringBuffer();
        boolean firstAsteriskFlag = false;
        for(char ch : wordString.toCharArray()) {
        	if(ch == '*') {
        		// only add for first '*'
        		if(!firstAsteriskFlag) {
        			buf.append(ch);
        			firstAsteriskFlag = true;
        		}
        	}
        	else {
        		buf.append(ch);
        		firstAsteriskFlag = false;
        	}
        }
        return buf.toString();
    }
    
    // checks if asterisk is present
    protected boolean isAsterixPresent(String wordString) {
        if (wordString == null || wordString.isEmpty()) {
            return false;
        }
        return wordString.contains("*") ? true : false;
    }

    
    /*
     * search all the words that satisfy the wordString format
     *
     * @param wordString string of characters including wild card character '?'
     * @return returns set of all strings that satisfy the wordString format, null otherwise
     */
    public Set<String> searchWords(String wordString) {
        if (!isValidString(wordString)) {
            return null;
        }        
        wordString = wordString.toLowerCase();
        wordString = preProcessWord(wordString);
        Set<String> finalSet = searchWords(trieRoot, wordString.toCharArray(), 0, null);
        return finalSet;
    }
    
    // recursive function to search words from the trie
    protected Set<String> searchWords(TrieNode curNode, char[] wordArray, int curIndex, StringBuffer wordFormed) {
    	if(curNode==null || wordArray==null || curIndex<0) {
    		return null;
    	}
    	
    	if(curNode.isFinalChar() && curIndex>=wordArray.length && wordFormed!=null && wordFormed.length()!=0) {
    		return Collections.singleton(wordFormed.toString());
    	}
    	    	
    	if(curIndex >= wordArray.length) {
    		return null;
    	}
    	
    	if(wordFormed == null) {
    		wordFormed = new StringBuffer();
    	}
    	
    	Set<String> wordSet = new HashSet<String>();
    	char curChar = wordArray[curIndex];
    	if(curChar == '?') {
    		Set<TrieNode> childrenNodes = curNode.getChildrenNodes();
    		if(childrenNodes!=null) {
    			for(TrieNode node : childrenNodes) {
    				StringBuffer wordFormedClone = new StringBuffer(wordFormed);
    				wordSet = addToSetIfNotNull(wordSet,
    						searchWords(node, wordArray, curIndex+1, wordFormedClone.append(node.getCharValue())));
    			}
    			return wordSet;
    		}
    	} else if(curChar == '*') {
    		StringBuffer wordFormedClone = new StringBuffer(wordFormed);
    		wordSet = addToSetIfNotNull(wordSet,
					searchWords(curNode, wordArray, curIndex+1, wordFormedClone));
    		Set<TrieNode> childrenNodes = curNode.getChildrenNodes();
    		if(childrenNodes != null) {
    			for(TrieNode node : childrenNodes) {
    				StringBuffer wordFormedCloneNew = new StringBuffer(wordFormed);
    				wordSet = addToSetIfNotNull(wordSet,
    						searchWords(node, wordArray, curIndex, wordFormedCloneNew.append(node.getCharValue())));
    			}
    			return wordSet;
    		}
    		else if(curNode.isFinalChar() && curIndex==(wordArray.length-1)) {
        		return Collections.singleton(wordFormed.toString());
    		}
    	} else {
    		assert Character.isLetter(curChar);
    		TrieNode node = curNode.getChild(curChar);
    		if(node != null) {
    			wordSet = addToSetIfNotNull(wordSet,
    					searchWords(node, wordArray, curIndex+1, wordFormed.append(node.getCharValue())));
    			return wordSet;
    		}
    	}
    	
    	return null;
    }
    
    // adds set to the wordSet only if it is not null
    private Set<String> addToSetIfNotNull(Set<String> wordSet, Set<String> setToAdd) {
    	assert wordSet!=null;
    	if(setToAdd!=null && !setToAdd.isEmpty()) {
    		wordSet.addAll(setToAdd);
    	}
	return wordSet;
    }
    
    /*
     * prints usage instructions
     */
    private static void usage() {
        System.out.println("Usage:");
        System.out.println("\tjava -cp Trie.jar com.parthparekh.algorithms.trie.WordsSearchTrie " +
                                    "<absolute_path_to_wordlist_file> <wildcard_word_to_search>");
        System.out.println("\t(use '?' for one character and '*' for zero or more, as wildcards)");
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

        Set<String> validWords = wordsSearchTrie.searchWords(word);
        System.out.println("Total " + validWords.size() + " matching words found for: " + word);
        System.out.println();
        int count = 0;
        for (String setWord : validWords) {
            System.out.println("" + ++count + ". " + setWord);
        }
        System.out.println();
    }
}
