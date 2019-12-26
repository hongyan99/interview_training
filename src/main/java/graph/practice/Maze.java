package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given a two-dimensional grid of size n * m that represents a maze-like area, 
 * a start cell and a goal cell, you have to find the shortest path from the start 
 * to the goal. You can go up, down, left or right from a cell, but not diagonally.
 * 
 * Each cell in the grid can be either land or water or door or key or start or 
 * goal. You can travel on any cells but water.
 * 
 * Every key and every door belongs to a particular “type”. For example a key of 
 * type ‘a’ can open a door of type ‘A’ but not a door of type ‘B’. There can be 
 * more than one key of a particular type in the maze, all keys of one type are 
 * equivalent. Similarly there can be more than one door of any type. You cannot 
 * travel through a door unless you have picked up a key of the corresponding 
 * type before. A key is picked up by visiting its cell. If you have picked up a 
 * certain type of key, you can then travel through any doors of that type any 
 * number of times.
 * 
 * It is allowed to revisit a cell.
 * 
 * @author hongyanli
 *
 */
public class Maze {
	private static final char WATER='#';
	private static final char LAND='.';
	private static final char START='@';
	private static final char GOAL='+';
	
	public static void main(String[] args) {
		String[] grid = new String[] {
				".dj##.f.j#efejj..@e#+G.c.",
				".hdI#.#aAghficDe#J.CGa.ba"
		};
		int[][] result = findShortestPath(grid);
		for(int i = 0; i < result.length; i++) {
			System.out.println(Arrays.toString(result[i]));
		}
		
	}
	
    static int[][] findShortestPath(String[] grid) {
    	// Because the fact that the keys held by the path determines whether the
    	// path can continue forward, we are really walking in a 3-D space (I call it
    	// the "Hyper Grid") rather than a 2-D one (the Grid), thus the visited map 
    	// should built for the cells in the Hyper Grid (the Node), that is, the same 
    	// grid cell that hold different keys are treated as different Nodes.
    	Map<Node, Node> parentMap = new HashMap<>();
    	
    	int[] start = findStart(grid);
    	List<Node> queue = new ArrayList<>();
    	Node rootNode = new Node(start, new KeyFlag());
    	queue.add(rootNode);
    	parentMap.put(rootNode, Node.ROOT);
    	while(!queue.isEmpty()) {
    		Node node = queue.remove(0);
        	List<Node> neighbours = findNeighbours(grid, node);
        	if(neighbours.size()==1 && neighbours.get(0).isTerminal()) {
        		Node child = neighbours.get(0);
        		parentMap.put(child, node);
        		return constructResult(parentMap, node, child);
        	}
        	
        	for(int i = 0; i<neighbours.size(); i++) {
        		Node child=neighbours.get(i);
        		if(parentMap.get(child)==null) {
        	    	parentMap.put(child, node);
	        		queue.add(child);
        		}
        	}
    	}
    	return null;
    }

    // Walk back the parent chain to construct the path
	private static int[][] constructResult(Map<Node, Node> priorCellMap, Node parent, Node goal) {
		List<int[]> result = new ArrayList<>();
		result.add(0, goal.getCoord());
		result.add(0, parent.getCoord());
		while(true) {
			parent = priorCellMap.get(parent);
			if(parent==Node.ROOT) {
				break;
			}
			
			result.add(0, parent.getCoord());
		}
		return result.toArray(new int[result.size()][2]);
	}

	// Logic that helps to find the neighboring Nodes.
	private static List<Node> findNeighbours(String[] grid, Node node) {
		int[] cell = node.getCoord();
		int[][] options = new int[4][2];
		options[0][0] = cell[0]-1;
		options[0][1] = cell[1];
		
		options[1][0] = cell[0];
		options[1][1] = cell[1]+1;
		
		options[2][0] = cell[0]+1;
		options[2][1] = cell[1];
		
		options[3][0] = cell[0];
		options[3][1] = cell[1]-1;
		
		List<Node> returns = new ArrayList<>();
		for(int i = 0; i < options.length; i++) {
			if(options[i][0]>=0 && options[i][0]<grid.length && options[i][1]>=0 && options[i][1]<grid[0].length()) {
				char c = grid[options[i][0]].charAt(options[i][1]);
				switch(c) {
				case GOAL:
					returns.clear();
					Node terminalNode = new Node(options[i], node.getFlag());
					terminalNode.terminate();
					returns.add(terminalNode);
					return returns;
				case WATER:
					break;
				case LAND:
				case START:
					returns.add(new Node(options[i], node.getFlag()));
					break;
				case 'a':
				case 'b':
				case 'c':
				case 'd':
				case 'e':
				case 'f':
				case 'g':
				case 'h':
				case 'i':
				case 'j':
					KeyFlag flag = node.getFlag().set(c); // new KeyFlag is generated
					returns.add(new Node(options[i], flag));
					break;
				case 'A':
				case 'B':
				case 'C':
				case 'D':
				case 'E':
				case 'F':
				case 'G':
				case 'H':
				case 'I':
				case 'J':
					if(node.getFlag().get(c)) {
						returns.add(new Node(options[i], node.getFlag()));
					}
					break;
				}
			}
		}
		return returns;
	}

	private static int[] findStart(String[] grid) {
		for(int i = 0; i<grid.length; i++) {
			int result = grid[i].indexOf(START);
			if(result>=0) {
				return new int[] {i, result};
			}
		}
		throw new IllegalArgumentException();
	}

	// This keeps track of the keys a Node holds. The hashCode and equals methods must be implemented
	//  so that it can support the same methods of the Node class.
	private static class KeyFlag {
		private BitSet flag = new BitSet(10);
		
		public KeyFlag set(char c) {
			if(flag.get(c-'a')) {
				return this;
			}
			KeyFlag newFlag = new KeyFlag();
			newFlag.flag = (BitSet) this.flag.clone();
			newFlag.flag.set(c-'a');
			return newFlag;
			
		}
		
		public boolean get(char c) {
			return flag.get(c-'A');
		}
		
		public int hashCode() {
			return flag.hashCode();
		}
		
		public boolean equals(Object other) {
			return other instanceof KeyFlag && flag.equals(((KeyFlag)other).flag);
		}
	}
	
	// Node represents the cells in the hyper grid. Such cell has attributes coordinates (x,y) and
	// keys (KeyFlag). THe hashCode and equals method are implemented so that we comparison can be
	// made to distinguish in between cells on different coordinates, or those that hold different
	// keys.
	private static class Node {
		private final int[] coord;
		private final KeyFlag flag;
		private boolean terminal;

		// A dummy root node so that we can mark the START node as been visited
		public static Node ROOT = new Node(new int[] {-1,-1}, null);
		
		public Node(int[] coord, KeyFlag flag) {
			this.coord = coord;
			this.flag = flag;
		}
		
		public int[] getCoord() {
			return coord;
		}

		public KeyFlag getFlag() {
			return flag;
		}

		public boolean isTerminal() {
			return terminal;
		}

		public void terminate() {
			this.terminal = true;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((flag == null) ? 0 : flag.hashCode());
			result = prime * result + coord[0];
			result = prime * result + coord[1];
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
			Node other = (Node) obj;
			if (flag == null) {
				if (other.flag != null)
					return false;
			} else if (!flag.equals(other.flag))
				return false;
			if (coord[0] != other.coord[0])
				return false;
			if (coord[1] != other.coord[1])
				return false;
			return true;
		}
	}
}
