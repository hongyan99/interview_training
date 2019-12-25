package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Given a sorted dictionary of an alien language, you have to find the order of 
 * characters in that language.
 * 
 * @author hongyanli
 *
 */
public class CharacterOrder {
	public static void main(String[] args) {
		String[] words = new String[] {"oooh", "ooho", "oqho", "oqhh", "oqq"};
		System.out.println(findOrder(words));
	}
	
	private static String findOrder(String[] words) {
		Trie root = Trie.createRoot();
		for(int i = 0; i < words.length; i++) {
			root.addChild(words[i], 0);
		}
		
		StringBuilder builder = new StringBuilder();
		Map<Character, Integer> indexMap = new HashMap<>();
		Stack<Trie> stack = new Stack<>();
		stack.push(root);
		List<List<Character>> undetermined = new ArrayList<>();
		walkMerge(builder, indexMap, undetermined, stack);
		if(undetermined.size()>0) {
			Collections.sort(undetermined, new Comparator<List<Character>>() {
				@Override
				public int compare(List<Character> o1, List<Character> o2) {
					return o2.size()-o1.size();
				}
			});
			for(int k = 0; k<undetermined.size(); k++) {
				List<Character> list = undetermined.get(k);
				int insertPos = Integer.MAX_VALUE;
				for(int i = 0; i < list.size(); i++) {
					String cc = String.valueOf(list.get(i));
					if(builder.indexOf(cc)<0) {
						if(builder.length()<insertPos) {
							builder.append(cc);
							insertPos = builder.length()-1;
						} else {
							builder.insert(insertPos, cc);
						}
					}
				}
				if(list.size()==1) {
					String cc = String.valueOf(list.get(0));
					if(builder.indexOf(cc)<0) {
						builder.append(cc);
					}
				} else {
					
				}
			}
		}
		return builder.toString();
	}

	private static void walkMerge(StringBuilder builder, Map<Character, Integer> indexMap, 
			List<List<Character>> undetemined, Stack<Trie> stack) {
		if(stack.isEmpty()) {
			return;
		}
		
		Trie node = stack.pop();
		List<Trie> children = node.getChildren();
		char[] chars = toChars(children);
		merge(builder, indexMap, undetemined, chars);
		for(int i = 0; i < children.size(); i++) {
			stack.push(children.get(i));
		}
		walkMerge(builder, indexMap, undetemined, stack);
	}


	private static char[] toChars(List<Trie> children) {
		char[] returns = new char[children.size()];
		for(int i = 0; i < children.size(); i++) {
			returns[i] = children.get(i).getKey();
		}
		return returns;
	}


	private static class Trie {
		private final Character key;
		private Map<Character, Trie> map = new HashMap<>();
		private List<Trie> children = new ArrayList<>();
		
		public static Trie createRoot() {
			return new Trie();
		}

		private Trie() {
			this.key = null;
		}
		
		public Trie(String value, int index) {
			if(value!=null) {
				this.key = value.charAt(index);
				addChild(value, index+1);
			} else {
				this.key = null;
			}
		}
		
		
		private List<Trie> getChildren() {
			return children;
		}
		
		public void addChild(String value, int index) {
			if(value.length()>index) {
				Character key = value.charAt(index);
				Trie parent = map.get(key);
				if(parent==null) {
					Trie childTee = new Trie(value, index);
					map.put(childTee.getKey(), childTee);
					children.add(childTee);
				} else {
					parent.addChild(value, index+1);
				}
			}
		}

		public Character getKey() {
			return key;
		}
	}

	private static void merge(StringBuilder builder, Map<Character, Integer> indexMap, 
			List<List<Character>> undetermined, char[] chars) {
		if(builder.length()==0) {
			builder.append(chars);
			
			// Before we return, we need to rebuild the indexMap
			indexMap.clear();
			for(int i = 0; i<builder.length(); i++) {
				indexMap.put(builder.charAt(i), i);
			}
			return;
		}
		
		List<Character> remainder = new ArrayList<>();
		int insertionPos = -1;
		char insertionChar = 0;
		for(int j = chars.length-1; j >= 0; j--) {
			char c = chars[j];
			Integer i = indexMap.get(c);
			if(i==null) {
				if(insertionPos!=-1) {
					insertPreceedingChars(builder, undetermined, insertionPos, c);
					builder.insert(insertionPos, c);
				} else {
					// Note: the remainder contains the characters that we cannot determine
					// where to insert in the StringBuilder. So we store them until we have
					// enough info to know where to insert. Note that the stored are in the 
					// reverse order.
					remainder.add(c);
				}
			} else if(insertionPos>=0 && insertionPos<i) {
				builder.delete(i, i+1);
				builder.insert(insertionPos, c);
			} else {
				if(insertionChar!=0) {
					insertPreceedingChars(builder, undetermined, insertionPos, insertionChar);
				}
				insertionChar = c;
				insertionPos = i;
			}
		}
		
		// Before we return, we need to rebuild the indexMap
		indexMap.clear();
		for(int i = 0; i<builder.length(); i++) {
			indexMap.put(builder.charAt(i), i);
		}
		
		if(remainder.size()>0) {
			undetermined.add(remainder);
		}
	}

	private static void insertPreceedingChars(StringBuilder builder, List<List<Character>> undetermined,
			int insertionPos, char c) {
		for(int ii=0; ii<undetermined.size(); ii++) {
			List<Character> undlist = undetermined.get(ii);
			int iii=undlist.indexOf(c);
			if(iii>-1) {
				for(int jj=0; jj<iii; jj++) {
					String cc = String.valueOf(undlist.remove(0));
					int idx = builder.indexOf(cc);
					if(idx==-1) {
						builder.insert(insertionPos, cc);
					} else if(idx>insertionPos) {
						// we want to keep the lowest char, thus delete the char at 
						// larger index first and then insert at the smaller index
						builder.delete(idx, idx+1);
						builder.insert(insertionPos, cc);
					} else {
						// do nothing=throw away the duplicate char
					}
				}
			}
		}
	}
}
