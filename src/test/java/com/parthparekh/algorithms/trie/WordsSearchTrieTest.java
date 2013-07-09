package com.parthparekh.algorithms.trie;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for WordsSearchTrie
 * 
 * @author: Parth Parekh
 **/
public class WordsSearchTrieTest extends WordsSearchTrie {
	
	private WordsSearchTrie wordsSearchTrie;
    private WordsSearchTrieTest wordSolverTrieTest;
	
	@Before
	public void setUp() {
		wordsSearchTrie = new WordsSearchTrie();
        wordSolverTrieTest = new WordsSearchTrieTest();

        wordsSearchTrie.addString("parth");
        wordsSearchTrie.addString("parekh");
        wordsSearchTrie.addString("part");
    }

	@Test
	public void searchStringTest() {
		Assert.assertTrue(wordsSearchTrie.hasString("parth"));
		Assert.assertFalse(wordsSearchTrie.hasString("blahblah"));
		Assert.assertFalse(wordsSearchTrie.hasString("par"));
		Assert.assertTrue(wordsSearchTrie.hasString("part"));
	}

    private void loadTrie() throws IOException {
        String executionPath = System.getProperty("user.dir");
        String fileSeparator = System.getProperty("file.separator");
        String wordListPath = executionPath + fileSeparator + "wordlist" + fileSeparator + "testwordlist.txt";
        wordsSearchTrie.loadTrie(wordListPath);
    }

    @Test
    public void loadTrieTest() throws IOException {
        loadTrie();
        Assert.assertTrue(wordsSearchTrie.hasString("abcde"));
    }

    @Test
    public void isValidStringTest() {
        Assert.assertTrue(wordSolverTrieTest.isValidString("parth"));
        Assert.assertFalse(wordSolverTrieTest.isValidString(" #$%#$%"));
        Assert.assertFalse(wordSolverTrieTest.isValidString(" +++"));
        Assert.assertTrue(wordSolverTrieTest.isValidString("p????"));
        Assert.assertTrue(wordSolverTrieTest.isValidString("?????"));
    }

    @Test
    public void preProcessStringTest() {
    	Assert.assertEquals("parth", wordSolverTrieTest.preProcessWord("parth"));
    	Assert.assertEquals("p*h", wordSolverTrieTest.preProcessWord("p*h"));
        Assert.assertEquals("p*h", wordSolverTrieTest.preProcessWord("p***h"));
        Assert.assertEquals("p*", wordSolverTrieTest.preProcessWord("p***"));
        Assert.assertEquals("*p", wordSolverTrieTest.preProcessWord("**p"));
        Assert.assertEquals("*", wordSolverTrieTest.preProcessWord("****"));
        Assert.assertEquals("pa?*", wordSolverTrieTest.preProcessWord("pa?*"));
        Assert.assertEquals("pa*?", wordSolverTrieTest.preProcessWord("pa*?"));
    }
    
    @Test
    public void searchWordsWithSingleCharacterWildCard() throws IOException {
        loadTrie();
        Assert.assertEquals(4, wordsSearchTrie.searchWords("a??d").size());
        Assert.assertEquals(2, wordsSearchTrie.searchWords("?c???").size());
        Assert.assertEquals(5, wordsSearchTrie.searchWords("?????").size());
        Assert.assertEquals(3, wordsSearchTrie.searchWords("?").size());
    }
    
    @Test
    public void searchFullWords() throws IOException {
        loadTrie();
        Assert.assertEquals(1, wordsSearchTrie.searchWords("a").size());
        Assert.assertEquals(1, wordsSearchTrie.searchWords("abc").size());
        Assert.assertEquals(1, wordsSearchTrie.searchWords("parth").size());
    }
    
    @Test
    public void searchWordsWithAsteriskWildCard() throws IOException {
        loadTrie();
        Assert.assertEquals(5, wordsSearchTrie.searchWords("a*d").size());
        Assert.assertEquals(8, wordsSearchTrie.searchWords("a*").size());
        Assert.assertEquals(7, wordsSearchTrie.searchWords("*d").size());
        Assert.assertEquals(24, wordsSearchTrie.searchWords("*").size());
        Assert.assertEquals(2, wordsSearchTrie.searchWords("e*ba").size());
        Assert.assertEquals(1, wordsSearchTrie.searchWords("w*xyz*xyz*w").size());
        Assert.assertEquals(1, wordsSearchTrie.searchWords("w*xyz*xyz*k").size());
    }
    
    @Test
    public void searchWordsWithMixWildCard() throws IOException {
        loadTrie();
        Assert.assertEquals(2, wordsSearchTrie.searchWords("w*xyz*xyz*?").size());
        Assert.assertEquals(21, wordsSearchTrie.searchWords("??*?").size());
        Assert.assertEquals(3, wordsSearchTrie.searchWords("e*?a").size());
    }

}
