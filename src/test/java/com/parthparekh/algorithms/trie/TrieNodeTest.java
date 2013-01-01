package com.parthparekh.algorithms.trie;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for TrieNode
 * 
 * @author: Parth Parekh (parthparekh [at] gatech [dot] edu)
 **/
public class TrieNodeTest {
	
	private TrieNode trieNode;
	
	@Before
	public void setUp() {
		trieNode = new TrieNode();
	}

    @Test
    public void rootNodeCharValueTest() {
        Assert.assertEquals('\u0000', trieNode.getCharValue());
    }
	
	@Test
	public void addChildTest() {
		Assert.assertTrue(trieNode.addChild('p'));
		Assert.assertFalse(trieNode.addChild('p'));		
	}
	
	@Test
	public void removeChildTest() {
		// reset TrieNode
		setUp();
		Assert.assertTrue(trieNode.addChild('p'));
		Assert.assertTrue(trieNode.removeChild('p'));
		Assert.assertFalse(trieNode.removeChild('p'));
	}
	
	@Test
	public void retrieveChildTest() {
		// reset TrieNode
		setUp();
		Assert.assertTrue(trieNode.addChild('p'));
		Assert.assertNotNull(trieNode.getChild('p'));		
	}
	
	@Test
	public void retrieveChildrenTest() {
		// reset TrieNode
		setUp();
		Assert.assertTrue(trieNode.addChild('p'));
		Assert.assertTrue(trieNode.addChild('a'));
		Assert.assertNotNull(trieNode.getChildrenValues());
		Assert.assertEquals(2, trieNode.getChildrenValues().size());
	}

    @Test
   	public void retrieveChildrenNodesTest() {
   		// reset TrieNode
   		setUp();
   		Assert.assertTrue(trieNode.addChild('p'));
   		Assert.assertTrue(trieNode.addChild('a'));
        Assert.assertTrue(trieNode.addChild('r'));
   		Assert.assertNotNull(trieNode.getChildrenNodes());
   		Assert.assertEquals(3, trieNode.getChildrenNodes().size());
        System.out.println("Nodes are: " + trieNode.getChildrenNodes());
   	}

	
	@Test
	public void depthTest() {
		// reset TrieNode
		setUp();
		Assert.assertTrue(trieNode.addChild('p'));
		TrieNode childNode = trieNode.getChild('p');
		Assert.assertNotNull(childNode);
		Assert.assertEquals(1, childNode.getDepth());
		Assert.assertTrue(childNode.addChild('a'));
		TrieNode grandChildNode = childNode.getChild('a');
		Assert.assertNotNull(grandChildNode);
		Assert.assertEquals(2, grandChildNode.getDepth());
		Assert.assertTrue(grandChildNode.addChild('r'));
		TrieNode greatGrandChildNode = grandChildNode.getChild('r');
		Assert.assertNotNull(greatGrandChildNode);
		Assert.assertEquals(3, greatGrandChildNode.getDepth());
	}
	
	@Test
	public void addMultiLevelChildTest() {
		// reset TrieNode
		setUp();
		Assert.assertTrue(trieNode.addChild('p'));
		TrieNode childNode = trieNode.getChild('p');
		Assert.assertNotNull(childNode);
		Assert.assertTrue(childNode.addChild('a'));
		TrieNode grandChildNode = childNode.getChild('a');
		Assert.assertNotNull(grandChildNode);
		Assert.assertTrue(grandChildNode.addChild('r'));
		TrieNode greatGrandChildNode = grandChildNode.getChild('r');
		Assert.assertNotNull(greatGrandChildNode);
	}
	
	@Test
	public void addMultiChildTest() {
		// reset TrieNode
		setUp();
		Assert.assertTrue(trieNode.addChild('p'));
		Assert.assertTrue(trieNode.getChild('p').addChild('a'));
		Assert.assertTrue(trieNode.getChild('p').getChild('a').addChild('r'));
		Assert.assertTrue(trieNode.getChild('p').getChild('a').getChild('r').addChild('t'));
		Assert.assertTrue(trieNode.getChild('p').getChild('a').getChild('r').getChild('t').addChild('h'));
		
		Assert.assertTrue(trieNode.getChild('p').getChild('a').getChild('r').addChild('e'));
		Assert.assertTrue(trieNode.getChild('p').getChild('a').getChild('r').getChild('e').addChild('k'));
		Assert.assertTrue(trieNode.getChild('p').getChild('a').getChild('r').getChild('e').getChild('k').addChild('h'));
	}
}
