package edu.smith.cs.csc212.speller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * Do your work for the Fake Dataset part here.
 * 
 * @author jfoley
 *
 */
public class FakeDatasetExperiment {
	/**
	 * Maybe this will be a nice helper method. How do you "ruin" a correctly
	 * spelled word?
	 * 
	 * @param realWord - a word from the dictionary, perhaps chosen at random.
	 * @return an incorrectly-spelled word. Maybe you deleted a letter or added one?
	 */
	public static String makeFakeWord(String realWord) {
		int length = realWord.length();
		String fakeWord = realWord.substring(1,length);
		return fakeWord;
	}

	/**
	 * Create a list of words that contains some dictionary words in proportion to
	 * some non-dictionary words.
	 * 
	 * @param yesWords    - the words in the dictionary.
	 * @param numSamples  - the number of total words to select.
	 * @param fractionYes - the fraction of words that are in the dictionary -- with
	 *                    0.5, half would be spelled correctly and half would be
	 *                    incorrect.
	 * @return a new list where size is numSamples.
	 */
	public static List<String> createMixedDataset(List<String> yesWords, int numSamples, double fractionYes) {
		// Hint to the ArrayList that it will need to grow to numSamples size:
		List<String> output = new ArrayList<>(numSamples);
		int numReal = (int) (numSamples * fractionYes);
		for (int i = 0; i < numReal; i ++) {
			output.add(yesWords.get(i));
		}
		for (int i = numReal; i < numSamples; i ++) {
			output.add(makeFakeWord(yesWords.get(i)));
		}
		
		return output;
	}

	/**
	 * This is **an** entry point of this assignment.
	 * 
	 * @param args - unused command-line arguments.
	 */
	public static void main(String[] args) {
		// --- Load the dictionary.
		List<String> listOfWords = CheckSpelling.loadDictionary();

		// --- Create a bunch of data structures for testing:
		TreeSet<String> treeOfWords = new TreeSet<>(listOfWords);
		HashSet<String> hashOfWords = new HashSet<>(listOfWords);
		SortedStringListSet bsl = new SortedStringListSet(listOfWords);
		CharTrie trie = new CharTrie();
		for (String w : listOfWords) {
			trie.insert(w);
		}
		LLHash hm100k = new LLHash(100000);
		for (String w : listOfWords) {
			hm100k.add(w);
		}
		
//		 --- OK, so that was a biased experiment (the answer to every question was yes!).
//		 Let's try 10% yesses.
		for (int i = 0; i < 10; i++) {
			// --- Create a dataset of mixed hits and misses with p=i/10.0
			List<String> hitsAndMisses = createMixedDataset(listOfWords, 10_000, i / 10.0);

			System.out.println("i="+i);
			// --- Time the data structures.
			CheckSpelling.timeLookup(hitsAndMisses, treeOfWords);
			CheckSpelling.timeLookup(hitsAndMisses, hashOfWords);
			CheckSpelling.timeLookup(hitsAndMisses, bsl);
			CheckSpelling.timeLookup(hitsAndMisses, trie);
			CheckSpelling.timeLookup(hitsAndMisses, hm100k);
		}
		for (double percentage = 0.0; percentage < 1.0; percentage += .1) {
			List<String> mixedWords = createMixedDataset(listOfWords, 100, percentage);
			System.out.println("The perentage of correct words is " + percentage*100);
			CheckSpelling.timeLookup(mixedWords, treeOfWords);	
			CheckSpelling.timeLookup(mixedWords, hashOfWords);	
			CheckSpelling.timeLookup(mixedWords, bsl);	
			CheckSpelling.timeLookup(mixedWords, trie);	
			CheckSpelling.timeLookup(mixedWords, hm100k);	
			
		}
		
		
		
	}
}
