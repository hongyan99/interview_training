package tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeSet;

public class CharacterOrder2 {
	public static void main(String[] args) {
		String[] words = new String[] {"baa", "abcd", "abca", "cab", "cad"};
		System.out.println(findOrder(words));
	}
	
	private static String findOrder(String[] words) {
		Map<Character, List<Character>> graph = new HashMap<>();
		Map<Character, Indegree> indegree = buildGraph(words, graph);
		return sort(graph, indegree);
	}

	private static String sort(Map<Character, List<Character>> graph, Map<Character, Indegree> indegree) {
		StringBuilder sb = new StringBuilder();
		TreeSet<Indegree> degrees = new TreeSet<>(indegree.values());
		while(!indegree.isEmpty()) {
			Indegree degree = degrees.iterator().next();
			if(degree.getDegree()>0) {
				throw new IllegalArgumentException("Cycle found!");
			}
			indegree.remove(degree.getKey());
			degrees.remove(degree);
			sb.append(degree.getKey());
			if(graph.isEmpty()) {
				break;
			}
			List<Character> adjList = graph.remove(degree.getKey());
			adjList.forEach(c-> {
				indegree.put(c, indegree.get(c).desc());
			});
			degrees = new TreeSet<>(indegree.values());
		}
		return sb.toString();
	}

	private static Map<Character, Indegree> buildGraph(String[] words, Map<Character, List<Character>> graph) {
		Trie root = Trie.createRoot();
		for(int i = 0; i < words.length; i++) {
			root.addChild(words[i], 0);
		}
		
		Map<Character, Indegree> indegree = new HashMap<>();
		Stack<Trie> stack = new Stack<>();
		stack.add(root);
		while(!stack.isEmpty()) {
			Trie node = stack.pop();
			List<Trie> children = node.getChildren();
			children.forEach(t->{
				checkInitIndegree(t.key, indegree);
			});
			for(int i = 0; i<children.size(); i++) {
				if(i>0) {
					Trie key = children.get(i-1);
					Trie value = children.get(i);
					List<Character> adjList = graph.getOrDefault(key.key, new ArrayList<>());
					if(!adjList.contains(value.key)) {
						adjList.add(value.key);
						indegree.put(value.key, indegree.get(value.key).inc());
					}
					graph.put(key.key, adjList);
				}
				stack.push(children.get(i));
			}
			if(graph.size()>=26) {
				break;
			}
		}
		return indegree;
	}
	
	private static void checkInitIndegree(Character c, Map<Character, Indegree> indegree) {
		Indegree degree = indegree.getOrDefault(c, new Indegree(c));
		if(degree.getDegree()==0) {
			indegree.put(c, degree);
		}
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
	
	private static class Indegree implements Comparable<Indegree> {

		private final Character key;
		private Integer degree = 0;
		public Indegree(Character key) {
			this.key = key;
		}
		
		public Indegree inc() {
			this.degree++;
			return this;
		}
		
		
		public Indegree desc() {
			this.degree--;
			return this;
		}


		public Integer getDegree() {
			return degree;
		}

		public Character getKey() {
			return key;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + degree;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Indegree other = (Indegree) obj;
			if (degree != other.degree)
				return false;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}

		@Override
		public int compareTo(Indegree other) {
			int result = degree.compareTo(other.degree);
			return result==0? key.compareTo(other.key) : result;
		}
	}
}
