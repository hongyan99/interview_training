package review.graph;

public class WeightedEdge {
	private final int source, dest, value;
	private int distance;

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public WeightedEdge(int value, int source, int dest) {
		this.source = source;
		this.dest = dest;
		this.value = value;
		this.distance = value;
	}

	public int getSource() {
		return source;
	}

	public int getDest() {
		return dest;
	}

	public int getValue() {
		return value;
	}
}
