package edu.smith.cs.csc212.speller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * @author jfoley
 *
 */
public class BookExperiment {
	
	static List<String> loadTextFileToWords(String fileName) {
		List<String> words = new ArrayList<>();
		try (BufferedReader reader = WordSplitter.readUTF8File(fileName)) {
			reader.lines().forEach((String line) -> {
				for (String word : WordSplitter.splitTextToWords(line)) {
					words.add(word);
				}
			});	
		} catch (IOException e) {
			throw new RuntimeException("Error reading file: "+fileName, e);
		}
		
		return words;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> listOfWords = CheckSpelling.loadDictionary();
		List<String> wordsInFrankenstein = loadTextFileToWords("src/main/resources/frankenstein.txt");
		System.out.println("Frankenstein contains approximately: "+wordsInFrankenstein.size()+" words.");
		TreeSet<String> treeOfWords = new TreeSet<>(listOfWords);
		CheckSpelling.timeLookup(wordsInFrankenstein, treeOfWords);
		HashSet<String> hashOfWords = new HashSet<>(listOfWords);
		CheckSpelling.timeLookup(wordsInFrankenstein, hashOfWords);
		SortedStringListSet sortedListOfWords = new SortedStringListSet(listOfWords);
		CheckSpelling.timeLookup(wordsInFrankenstein, sortedListOfWords);
		CharTrie trie = new CharTrie();
		for (String w : listOfWords) {
			trie.insert(w);
		}
		CheckSpelling.timeLookup(wordsInFrankenstein, trie);
		LLHash hm100k = new LLHash(100000);
		for (String w : listOfWords) {
			hm100k.add(w);
		}
		CheckSpelling.timeLookup(wordsInFrankenstein, hm100k);
	}
	
}
