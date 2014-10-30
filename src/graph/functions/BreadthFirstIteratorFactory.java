package graph.functions;

import org.jgrapht.Graph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

/**
 * Enthaelt nur eine Funktion apply() die zu einem Graphen einen BFS Iterator
 * zurueckgibt.
 *
 * @param <G>
 * @param <V>
 * @param <E>
 */
public class BreadthFirstIteratorFactory<G extends Graph<V, E>, V, E>
		implements IIteratorFactory<G, V, GraphIterator<V, E>> {

	@Override
	public GraphIterator<V, E> apply(G a, V b) {
		return new BreadthFirstIterator<V, E>(a, b);
	}

}
