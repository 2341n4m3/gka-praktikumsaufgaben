package graph.impls.f3;


import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;

/**
 * Enthaelt nur eine Methode apply() welche 
 * prueft ob target ein Nachfolger von source im Graphen graph ist.
 *
 * @param <G>
 * @param <V>
 * @param <E>
 */
public class SuccessorChecker<G extends DirectedGraph<V,E>, V, E> implements NeighborOrSuccessorChecker<G, V, V, Boolean> {

	@Override
	public Boolean apply(G graph, V source, V target) {
		return Graphs.successorListOf(graph, source).contains(target);
	}

}
