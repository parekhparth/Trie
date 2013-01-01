package com.parthparekh.algorithms.trie;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * A node implementation in a simple trie (http://en.wikipedia.org/wiki/Trie)
 * Note: this implementation is not thread safe
 * 
 * @author: Parth Parekh (parthparekh [at] gatech [dot] edu)
 **/
public class TrieNode {
	// to be set by Trie implementor
	private boolean isFinalChar;
	private char charValue;
	private int depth;
	private Map<Character, TrieNode> childrenMap;

	// creates empty root trie node
	public TrieNode() {
	}

	// this should be called by addChild only
	private TrieNode(char charValue) {
		this.charValue = charValue;
		childrenMap = null;
	}

	/**
	 * adds child to the existing Trie node, if there is no child with given character value present already
	 * 
	 * @param charValue
	 * @return returns true if the add is successful, false if there was already a child with that charValue
	 */
	public boolean addChild(char charValue) {
		// only create children when you're adding first child
		if (childrenMap == null) {
			childrenMap = new TreeMap<Character, TrieNode>();
		}
		Character charValueObject = Character.valueOf(charValue);
		if (childrenMap.containsKey(charValueObject)) {
			return false;
		}
		childrenMap.put(charValueObject, new TrieNode(charValue).setDepth(this.depth+1));
		return true;
	}

	/**
	 * removes child from the existing Trie node
	 * 
	 * @param charValue
	 * @return returns true if the remove was successful, false if there was no child found with that charValue
	 */
	public boolean removeChild(char charValue) {
		// return false if there are no children or children does not contain
		// the character to be removed
		Character charValueObject = Character.valueOf(charValue);
		if (childrenMap == null || !childrenMap.containsKey(charValueObject)) {
			return false;
		}
		childrenMap.remove(charValueObject);
		return true;
	}

	/**
	 * returns the child TrieNode if it exists
	 * 
	 * @param charValue
	 * @return returns TrieNode object for child if it exists, null otherwise
	 */
	public TrieNode getChild(char charValue) {
		// return null if there are no children
		if (childrenMap == null) {
			return null;
		}
		return childrenMap.get(Character.valueOf(charValue));
	}

	/**
	 * returns Set of all the children char values of current TrieNode
	 * 
	 * @return returns Set of all the Character objects if it exists, null otherwise
	 */
	public Set<Character> getChildrenValues() {
		// return null if there are no children
		if (childrenMap == null) {
			return null;
		}
		return childrenMap.keySet();
	}

    /**
   	 * returns Set of all the children nodes of current TrieNode
   	 *
   	 * @return returns Set of all the TrieNode objects if it exists, null otherwise
   	 */
   	public Set<TrieNode> getChildrenNodes() {
   		// return null if there are no children
   		if (childrenMap == null) {
   			return null;
   		}
        Set<TrieNode> trieNodes = new HashSet<TrieNode>();
        for (Character childChar : childrenMap.keySet()) {
            trieNodes.add(getChild(childChar));
        }
   		return trieNodes;
   	}

	public char getCharValue() {
		return charValue;
	}

	public boolean isFinalChar() {
		return isFinalChar;
	}

	public void setFinalChar(boolean isFinalChar) {
		this.isFinalChar = isFinalChar;
	}

	public int getDepth() {
		return depth;
	}

	public TrieNode setDepth(int depth) {
		this.depth = depth;
		return this;
	}

	@Override
	public int hashCode() {
		// need to think of something better
		return charValue + (childrenMap!=null ? childrenMap.hashCode() : 0);
	}

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		toString.append("nodeValue: " + charValue + "; isFinalChar: "
				+ isFinalChar + "; depth: " + depth + "; children: ");
		if (childrenMap != null) {
			return toString.append(childrenMap.keySet().toString()).toString();
		}
		return toString.append("not yet").toString();
	}
}
