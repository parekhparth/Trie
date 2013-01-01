package com.parthparekh.algorithms.trie;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


/**
 * Unit test for WordsSearchTrie
 * 
 * @author: Parth Parekh (parthparekh [at] gatech [dot] edu)
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
		Assert.assertTrue(wordsSearchTrie.searchString("parth"));
		Assert.assertFalse(wordsSearchTrie.searchString("blahblah"));
		Assert.assertFalse(wordsSearchTrie.searchString("par"));
		Assert.assertTrue(wordsSearchTrie.searchString("part"));
	}

    @Test
    public void trieWordsSizeTest() {
        // parth, parekh and part should go to three different tries
        Assert.assertEquals(3, wordsSearchTrie.trieMap.keySet().size());
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
        Assert.assertEquals(5, wordsSearchTrie.trieMap.keySet().size());
        Assert.assertTrue(wordsSearchTrie.searchString("abcde"));
    }

    @Test
    public void isValidStringTest() {
        Assert.assertTrue(wordSolverTrieTest.isValidString("parth"));
        Assert.assertFalse(wordSolverTrieTest.isValidString(" #$%#$%"));
        Assert.assertFalse(wordSolverTrieTest.isValidString(" ***"));
        Assert.assertTrue(wordSolverTrieTest.isValidString("p????"));
        Assert.assertTrue(wordSolverTrieTest.isValidString("?????"));
    }

    @Test
    public void findAllWordsTest() throws IOException {
        loadTrie();
        Assert.assertEquals(2, wordsSearchTrie.findAllWords("?c???").size());
        Assert.assertEquals(5, wordsSearchTrie.findAllWords("?????").size());
    }
}
