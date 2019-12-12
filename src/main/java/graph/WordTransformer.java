package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * You are given a dictionary of words named words, and two strings named start and stop. 
 * All given strings have equal length. Dictionary words are not in any particular order, 
 * there may be duplicates, too.
 * 
 * You need to transform string start to string stop using given dictionary words. 
 * In each transformation, you can only change one character of the current string. 
 * e.g. "abc" -> "abd" is a valid transformation, because only one character 'c' is 
 * changed to 'd', but, "abc" -> "axy" is not a valid transformation, because two 
 * characters are changed, character 'b' is changed to 'x' and character 'c' is changed 
 * to 'y'.
 */
public class WordTransformer {
	private static char[] chars = {'a','b','c','d','e','f','g',
			'h','i','j','k','l','m','n','o',
			'p','q','r','s','t','u','v','w',
			'x','y','z'};
	public static void main(String[] args) {
		System.out.println(Arrays.toString(transform(new String[] {"cat", "hat", "bad", "had"}, "bat", "had")));
		System.out.println(Arrays.toString(transform(new String[0], "bbb", "bbc")));
		System.out.println(Arrays.toString(transform(new String[0], "zzzz", "zzzz")));
		System.out.println(Arrays.toString(transform(new String[] {"cccw", "accc", "accw"}, "cccc", "cccc")));
		System.out.println(Arrays.toString(transform(new String[] {"ggggnn", "gggnnn", "gggggn", "ggnnnn", "gnnnnn"}, "gggggg", "nnnnnn")));
	}
	
	private static String[] transform(String[] words, String start, String stop) {
		Set<String> wordsSet = new HashSet<>(Arrays.asList(words));
		Map<String, String> parentsMap = new HashMap<>();
		// for each character in start, find all words in dictionary that has just that
		// character different, compare each such word with stop. If match, then found 
		// transformation, walk back the parent chain to construct the result array.
		List<Source> queue = new ArrayList<>();
		queue.add(new Source(start, 0));
		while(!queue.isEmpty()) {
			Source s = queue.remove(0);
			for(int i = s.index; i < s.source.length(); i++) {
				String target = s.source.equals(start) && s.source.equals(stop)? null : stop;
				List<String> neighbours = getNeighboursAt(wordsSet, s.source, target, i);
				if(neighbours.size()==1 && neighbours.get(0).equals(stop)) {
					return constructResult(parentsMap, s.source, start, stop);
				}
				
				neighbours.forEach(neighbour-> {
					if(parentsMap.get(neighbour)==null) {
						parentsMap.put(neighbour, s.source);
						queue.add(new Source(neighbour, 0));
					}
				});
			}
		}
		return new String[] {"-1"};
    }
	
	private static String[] constructResult(Map<String, String> parentsMap, String last, String start, String stop) {
		List<String> all = new ArrayList<>();
		String child = last;
		while(parentsMap.get(child)!=null) {
			all.add(0, child);
			child = parentsMap.get(child);
		}
		all.add(0, start);
		all.add(stop);
		return all.toArray(new String[all.size()]);
	}
	
	private static List<String> getNeighboursAt(Set<String> wordsSet, String source, String target, int index) {
		char[] charArray = source.toCharArray();
		List<String> neighbours = new ArrayList<>();
		for(int i = 0; i < chars.length; i++) {
			charArray[index]=chars[i];
			String newWord = String.valueOf(charArray);
			if(newWord.equals(target)) {
				return Arrays.asList(new String[] {target});
			}
			if(wordsSet.contains(newWord)) {
				neighbours.add(newWord);
			}
		}
		return neighbours;
	}
	
	private static class Source {
		String source;
		int index;
		Source(String source, int index) {
			this.source = source;
			this.index = index;
		}
	}
}
