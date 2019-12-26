package dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * You are given a dictionary set dictionary that contains dictionaryCount distinct words and another string txt. Your 
 * task is to count the possible number of the word-breaks of the txt string in such a way that all the word occur in 
 * a continuous manner in the original txt string and all these words exist in our dictionary set dictionary.
 * 
 * Return an integer denoting all different possible word-break arrangements for the txt string. This integer could be 
 * large so output it modulo 10^9 + 7.
 * 
 * @author hongyanli
 *
 */
public class WordBreakCounter {
	public static void main(String[] args) {
		List<String> dictionary = constructDict("kick", "start", "kickstart", "is", "awe", "some", "awesome");
		System.out.println(wordBreakCount(dictionary, "kickstartisawesome"));
	}
	
    private static List<String> constructDict(String... words) {
		return Arrays.asList(words);
	}

	public static int wordBreakCount(List<String> dictionary, String txt) {
    	Map<Integer, Long> mem = new HashMap<Integer, Long>();
    	TrieNode root = TrieNode.ROOT;
    	dictionary.forEach(word-> {
    		insertTrie(word);
    	});
		return (int) wordBreakCount(root, 0, txt.toCharArray(), mem);
    }

	private static long wordBreakCount(TrieNode root, int index, char[] txt, Map<Integer, Long> mem) {
		if(mem.get(index)!=null) {
			return mem.get(index);
		}
		if(index==txt.length) {
			return 1;
		}
		
		long count = 0;
		TrieNode node = root;
		for(int i = index; i < txt.length; i++) {
			node = node.nextNode(txt[i]);
			if(node==null) {
				break;
			}
			if(node.isWordEnd()) {
				count += wordBreakCount(root, i+1, txt, mem);
			}
			
		}

		mem.put(index, count);
		return count;
	}
	
	// One simpler solution is to loop through the words in the dictionary while using DP. Here we are
	// using Trie since it is more efficient specially and also, when the dictionary is very large, it
	// also has better time complexity. Some test case in the IKWeb exercise uses very large dictionary
	// and text string, and thus we have to use Trie, or some test cases won't pass because they are
	// too slow.
	private static class TrieNode {
		private boolean isWordEnd;
		private final Map<Character, TrieNode> children = new HashMap<>();
		
		public static final TrieNode ROOT = new TrieNode();
		
		public TrieNode nextNode(char c) {
			return children.get(c);
		}
		
		public boolean isWordEnd()  {
			return isWordEnd;
		}
	}

    public static void insertTrie(String word) {
        // To insert word in the trie
        TrieNode head = TrieNode.ROOT;
        for(char c: word.toCharArray()) {
            if(head.children.containsKey(c)) {
                // If child of current trie node have data matching with the character
                // then we will make that child as current node
                head = head.children.get(c);
            } else {
                // If current trie does not have any child matching to the character
                // then we create new child and make that as current node
                TrieNode new_node = new TrieNode();
                head.children.put(c, new_node);
                head = new_node;
            }
        }
        head.isWordEnd = true;
    }

}
