package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		if(start.length()>26) {
			return transform2(words, start, stop);
		} else {
			return transform1(words, start, stop);
		}
	}
	
	private static String[] transform2(String[] words, String start, String stop) {
		Map<String, String> parentsMap = new HashMap<>();
		parentsMap.put(start, "");
		List<String> queue = new ArrayList<>();
		queue.add(start);
		while(!queue.isEmpty()) {
			String s = queue.remove(0);
			String target = s.equals(start) && s.equals(stop)? null : stop;
			List<String> neighbours = getNeighboursDictBased(words, s, target);
			if(neighbours.size()==1 && neighbours.get(0).equals(stop)) {
				return constructResult(parentsMap, s, start, stop);
			}
			
			neighbours.forEach(neighbour-> {
				if(parentsMap.get(neighbour)==null) {
					parentsMap.put(neighbour, s);
					queue.add(neighbour);
				}
			});
		}
		return new String[] {"-1"};
	}

	private static List<String> getNeighboursDictBased(String[] words, String s, String target) {
		List<String> neighbours = new ArrayList<>();
		for(int i = 0; i<words.length; i++) {
			int diffCount = 0;
			for(int k = 0; k<words[i].length()&&diffCount<2; k++) {
				if(words[i].charAt(k)!=s.charAt(k)) {
					diffCount++;
				}
				if(diffCount>1) {
					break;
				}
			}
			if(diffCount==1) {
				if(words[i].equals(target)) {
					return Arrays.asList(new String[] {target});
				}
				neighbours.add(words[i]);
			}
		}
		return neighbours;
	}

	private static String[] transform1(String[] words, String start, String stop) {
		Map<String, Boolean> wordsMap = new HashMap<>();
		for(int i = 0; i < words.length; i++) {
			wordsMap.put(words[i], Boolean.TRUE);
		}
		Map<String, String> parentsMap = new HashMap<>();
		parentsMap.put(start, "");
		// for each character in start, find all words in dictionary that has just that
		// character different, compare each such word with stop. If match, then found 
		// transformation, walk back the parent chain to construct the result array.
		List<String> queue = new ArrayList<>();
		queue.add(start);
		while(!queue.isEmpty()) {
			String s = queue.remove(0);
			String target = s.equals(start) && s.equals(stop)? null : stop;
			List<String> neighbours = getNeighboursCharBased(wordsMap, s, target);
			if(neighbours.size()==1 && neighbours.get(0).equals(stop)) {
				return constructResult(parentsMap, s, start, stop);
			}
			
			neighbours.forEach(neighbour-> {
				if(parentsMap.get(neighbour)==null) {
					parentsMap.put(neighbour, s);
					queue.add(neighbour);
				}
			});
		}
		return new String[] {"-1"};
    }
	
	private static String[] constructResult(Map<String, String> parentsMap, String last, String start, String stop) {
		List<String> all = new ArrayList<>();
		String child = last;
		while(!parentsMap.get(child).isEmpty()) {
			all.add(0, child);
			child = parentsMap.get(child);
		}
		all.add(0, start);
		all.add(stop);
		return all.toArray(new String[all.size()]);
	}
	
	private static List<String> getNeighboursCharBased(Map<String, Boolean> wordsMap, String source, String target) {
		List<String> neighbours = new ArrayList<>();
		for(int index = 0; index < source.length(); index++) {
			char[] charArray = source.toCharArray();
			for(int i = 0; i < chars.length; i++) {
				charArray[index]=chars[i];
				String newWord = String.valueOf(charArray);
				if(newWord.equals(target)) {
					return Arrays.asList(new String[] {target});
				}
				if(wordsMap.get(newWord)!=null) {
					neighbours.add(newWord);
				}
			}
		}
		return neighbours;
	}
}
