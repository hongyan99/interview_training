package review.graph;

public class Edge {
	private final int source, dest, weight;

	public Edge(int source, int dest) {
		this(source, dest, 0);
	}

	public Edge(int source, int dest, int weight) {
		this.source = source;
		this.dest = dest;
		this.weight = weight;
	}

	public int getDest() {
		return dest;
	}

	public int getSource() {
		return source;
	}

	public int getWeight() {
		return weight;
	}
}
