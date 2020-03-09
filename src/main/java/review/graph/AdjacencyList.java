package review.graph;

import java.util.List;

@FunctionalInterface
public interface AdjacencyList<N> {
	List<N> getList(N node);
}
