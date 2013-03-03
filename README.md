# Trie in Java
Using trie to search words from dictionary with wild cards

# Build
  git clone https://github.com/parekhparth/Trie.git

  cd Trie

  mvn clean install
(above command runs the test and builds the 1.0-SNAPSHOT jar)

# Execute
after you build the jar, you can search the words using following:

Usage:

  java -cp Trie.jar com.parthparekh.algorithms.trie.WordsSearchTrie <absolute_path_to_wordlist_file> <wildcard_word_to_search>

(use '?' as wild card character)

for e.g. if you want to search all the words that satisfy the pattern a?????o???s and your wordlist file is under /tmp directory

  java -cp Trie.jar com.parthparekh.algorithms.trie.WordsSearchTrie /tmp/wordlist.txt a?????o???s

(you can download the wordlist from here => http://www.sil.org/linguistics/wordlists/english/)

# Trie
Above code uses trie to search for words. More inforamtion on trie can be found <a href='http://en.wikipedia.org/wiki/Trie'>here</a>

## Example

using word list from above link and searching for words using a?????o???s pattern outputs following:

Total 11 matching words found for: a?????o???s

1. albatrosses
2. antibiotics
3. apostrophes
4. anthologies
5. astronomers
6. allotropies
7. audiologies
8. antiphonies
9. accessories
10. antiprotons
11. astrologers

## Online tool

Above code can be tried online <a href='http://words-search.appspot.com/'>here</a>
