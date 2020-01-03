package edu.smith.cs.csc212.speller;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * This class contains experimentation code.
 * @author jfoley
 *
 */
public class CheckSpelling {
	/**
	 * Read all lines from the UNIX dictionary.
	 * @return a list of words!
	 */
	public static List<String> loadDictionary() {
		long start = System.nanoTime();
		// Read all lines from a file:
		// This file has one word per line.
		List<String> words = WordSplitter.readUTF8File("src/main/resources/words")
				.lines()
				.collect(Collectors.toList());
		long end = System.nanoTime();
		double time = (end - start) / 1e9;
		System.out.println("Loaded " + words.size() + " entries in " + time +" seconds.");
		return words;
	}
	
	/**
	 * This method looks for all the words in a dictionary.
	 * @param words - the "queries"
	 * @param dictionary - the data structure.
	 */
	public static void timeLookup(List<String> words, Collection<String> dictionary) {
		long startLookup = System.nanoTime();
		
		int found = 0;
		for (String w : words) {
			if (dictionary.contains(w)) {
				found++;
			}

		}
		
		long endLookup = System.nanoTime();
		double fractionFound = found / (double) words.size();
		double timeSpentPerItem = (endLookup - startLookup) / ((double) words.size());
		int nsPerItem = (int) timeSpentPerItem;
		System.out.println("  "+dictionary.getClass().getSimpleName()+": Lookup of items found="+fractionFound+" time="+nsPerItem+" ns/item");
	}
	
	/**
	 * This is **an** entry point of this assignment.
	 * @param args - unused command-line arguments.
	 */
	public static void main(String[] args) {
		// --- Load the dictionary.
		List<String> listOfWords = loadDictionary();
		
		// --- Create a bunch of data structures for testing:
//		long startTree = System.nanoTime();
//		TreeSet<String> treeOfWords = new TreeSet<>(listOfWords);
//		long endTree = System.nanoTime();
//		long startHash = System.nanoTime();
//		HashSet<String> hashOfWords = new HashSet<>(listOfWords);
//		long endHash = System.nanoTime();
//		long startSorted = System.nanoTime();
//		SortedStringListSet bsl = new SortedStringListSet(listOfWords);
//		long endSorted = System.nanoTime();
//		long startTrie = System.nanoTime();
//		CharTrie trie = new CharTrie();
//		for (String w : listOfWords) {
//			trie.insert(w);
//		}
//		long endTrie = System.nanoTime();
		long startLL = System.nanoTime();
		LLHash hm100k = new LLHash(100);
		for (String w : listOfWords) {
			hm100k.add(w);
		}
		long endLL = System.nanoTime();
		
//		long startEmptyTree = System.nanoTime();
//		TreeSet<String> tree2 = new TreeSet<>();
//		for (String w : listOfWords) {
//			tree2.add(w);
//		}
//		long endEmptyTree = System.nanoTime();
		
//		long startEmptyHash = System.nanoTime();
//		TreeSet<String> hash2 = new TreeSet<>();
//		for (String w : listOfWords) {
//			hash2.add(w);
//		}
//		long endEmptyHash = System.nanoTime();
		
//		long treeInsertion = endTree - startTree;
//		long hashInsertion = endHash - startHash;
//		long sortedInsertion = endSorted - startSorted;
//		long trieInsertion = endTrie - startTrie;
		long llInsertion = endLL - startLL;
//		long treeSlow = endEmptyTree - startEmptyTree;
//		long hashSlow = endEmptyHash -startEmptyHash;
//		
//		// --- Make sure that every word in the dictionary is in the dictionary:
//		//     This feels rather silly, but we're outputting timing information!
//		timeLookup(listOfWords, treeOfWords);
//		timeLookup(listOfWords, hashOfWords);
//		timeLookup(listOfWords, bsl);
//		timeLookup(listOfWords, trie);
		timeLookup(listOfWords, hm100k);
//		
		
		
	
		// --- print statistics about the data structures:
		//System.out.println("Count-Nodes: "+trie.countNodes());
		System.out.println("Count-Items: "+hm100k.size());

		System.out.println("Count-Collisions[100k]: "+hm100k.countCollisions());
		System.out.println("Count-Used-Buckets[100k]: "+hm100k.countUsedBuckets());
		System.out.println("Load-Factor[100k]: "+hm100k.countUsedBuckets() / 100000.0);

		
		System.out.println("log_2 of listOfWords.size(): "+listOfWords.size());
		
		//System.out.println("Done!");
		//System.out.println("inserting to tree takes " +treeInsertion+" nanoseconds");
		//System.out.println("inserting to hash takes " +hashInsertion+" nanoseconds");
		//System.out.println("inserting to sorted list takes " +sortedInsertion+" nanoseconds");
		//System.out.println("inserting to charTrie takes " +trieInsertion+" nanoseconds");
		System.out.println("inserting to LLHash takes " +llInsertion+" nanoseconds");
		//System.out.println("inserting to Hash one by one takes " +treeSlow+" nanoseconds");
		//System.out.println("inserting to Tree one by one takes " +hashSlow+" nanoseconds");
	}
}
