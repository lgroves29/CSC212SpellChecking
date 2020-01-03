import edu.smith.cs.csc212.speller.CharTrie;
import org.junit.Assert;
import org.junit.Test;

public class CharTrieTest {
	public CharTrie testTrie() {
		CharTrie output = new CharTrie();
		output.insert("Bun");
		output.insert("Bus");
		output.insert("Hi");
		output.insert("Ant");
		return output;
	}
	
	@Test
	public void testCount() {
		CharTrie trie = testTrie();
		int count = trie.countNodes();
		Assert.assertEquals(10, count);
	}
	
}
