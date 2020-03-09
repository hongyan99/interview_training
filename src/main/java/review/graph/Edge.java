package review.graph;

public class Edge {
	private final int source, dest;

	public Edge(int source, int dest) {
		this.source = source;
		this.dest = dest;
	}

	public int getSource() {
		return source;
	}

	public int getDest() {
		return dest;
	}
}
