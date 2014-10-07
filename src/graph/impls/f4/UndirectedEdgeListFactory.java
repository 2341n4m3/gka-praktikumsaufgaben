package graph.impls.f4;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;

/**
 * Die Klasse wird nicht benutzt
 * Sie repräsentiert den ursprünglichen versuch einer Edgelistfacotry
 * bevor der Aufruf von Graphs.neighborslist refaktorisiert wurde
 *
 * @param <G>
 * @param <V>
 * @param <E>
 */
//@formatter:off
public class UndirectedEdgeListFactory<G extends UndirectedGraph<V, E>, V, E> implements IEdgeListFactory<
	G,
	Map<V, Integer>,
	V,
	V,
	List<E>> {
//@formatter:on

	private List<E> findReverseEdgeList(G graph, Map<V, Integer> lengths,
			V start, V end) {
		List<E> reverseEdgeList = new ArrayList<>();
		int pathLength = lengths.get(end);
		return findReverseEdgeList(graph, lengths, start, end, pathLength,
				reverseEdgeList);
	}

	private List<E> findReverseEdgeList(G graph, Map<V, Integer> lengths,
			V v0, V vi, int i, List<E> reverseEdgeList) {
		if (i == 1) {
			reverseEdgeList.add(graph.getEdge(v0, vi));
			return reverseEdgeList;
		} else {
			for (V u : lengths.keySet()) {
				if (Graphs.neighborListOf(graph, u).contains(vi)) {
					if (lengths.get(u) != i - 1) {
						continue;
					} else {
						reverseEdgeList.add(graph.getEdge(u, vi));
						return findReverseEdgeList(graph, lengths, v0, u,
								i - 1, reverseEdgeList);
					}
				}
			}
			// should not be reacheable
			return null;
		}
	}

	@Override
	public List<E> apply(G a, Map<V, Integer> b, V c, V d) {
		return findReverseEdgeList(a, b, c, d);
	}

}
