package graph.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ConnectedZombies {
	public static void main(String[] args) {
		List<String> zombies = new ArrayList<>();
		zombies.add("1100");
		zombies.add("1110");
		zombies.add("0110");
		zombies.add("0001");
		System.out.println(zombieCluster(zombies));
	}
	
	public static int zombieCluster(List<String> zombies) {
		int components = 0;
		boolean[] visited = new boolean[zombies.size()];
		for(int i = 0; i < zombies.size(); i++) {
			if(!visited[i]) {
				components++;
				visited[i]=true;
				walk(zombies, visited, i);
			}
		}
        return components;
    }

	private static void walk(List<String> zombies, boolean[] visited, int i) {
		Stack<Integer> zombieIndices = new Stack<>();
		zombieIndices.push(i);
		while(!zombieIndices.isEmpty()) {
			int k = zombieIndices.pop();
			String row = zombies.get(k);
			for(int j = 0; j<zombies.size(); j++) {
				if(!visited[j]&&row.charAt(j)=='1') {
					visited[j]=true;
					zombieIndices.push(j);
				}
			}
		}
	}
}
