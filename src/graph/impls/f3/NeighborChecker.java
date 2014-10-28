package graph.impls.f3;



import org.jgrapht.Graph;
import org.jgrapht.Graphs;


/**
 * Enthaelt nur eine Methode apply() welche 
 * prueft ob target ein Nachbar von source im Graphen graph ist.
 *
 * @param <G>
 * @param <V>
 * @param <E>
 */
public class NeighborChecker<G extends Graph<V,E>, V, E> implements NeighborOrSuccessorChecker<G, V, V, Boolean> {

	@Override
	public Boolean apply(G graph, V source, V target) {
		return Graphs.neighborListOf(graph, source).contains(target);
	}

}
