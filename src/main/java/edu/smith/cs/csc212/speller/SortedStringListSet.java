package edu.smith.cs.csc212.speller;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This is an alternate implementation of a dictionary, based on a sorted list.
 * It often makes the most sense if the dictionary never changes (compared to a TreeMap).
 * You could write a delete, but it's tricky.
 * @author jfoley
 */
public class SortedStringListSet extends AbstractSet<String> {
	/**
	 * This is the sorted list of data.
	 */
	private List<String> data;
	
	/**
	 * This is the constructor: we take in data, copy and sort it (just to be sure).
	 * @param data the input list.
	 */
	public SortedStringListSet(List<String> data) {
		this.data = new ArrayList<>(data);
		Collections.sort(this.data);
	}

	/**
	 * So we can use it in a for-loop.
	 */
	@Override
	public Iterator<String> iterator() {
		return data.iterator();
	}
	
	/**
	 * This method takes an object because it was invented before Java 5.
	 */
	@Override
	public boolean contains(Object key) {
		return binarySearch((String) key, 0, this.data.size()) >= 0;
	}
	
	/**
	 * @param query  - the string to look for.
	 * @param start - the left-hand side of this search (inclusive)
	 * @param end - the right-hand side of this search (exclusive)
	 * @return the index found, OR negative if not found.
	 * based on the logic 
	 * https://ai.googleblog.com/2006/06/extra-extra-read-all-about-it-nearly.html
	 */
	
	private int binarySearch(String query, int start, int end) {
		int index = -1;
		while (start < end) {
			int middle = (end + start) >>> 1;
			/**
			 * stumbled on this because java suggested it when I started writing a 
			 * different method 
			 */
			if (this.data.get(middle).compareTo(query) < 0) {
				start = middle + 1;
			}
			else if (this.data.get(middle).compareTo(query) > 0) {
				end = middle - 1;
			}
			else {
				return middle;
			}
		}
		return index;
	}

	/**
	 * So we know how big this set is.
	 */
	@Override
	public int size() {
		return data.size();
	}

}
