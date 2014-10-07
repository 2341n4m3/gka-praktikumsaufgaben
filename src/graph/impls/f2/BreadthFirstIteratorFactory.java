package graph.impls.f2;



import org.jgrapht.Graph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

public class BreadthFirstIteratorFactory<G extends Graph<V, E>, V, E>
		implements IIteratorFactory<G, V, GraphIterator<V, E>> {

	@Override
	public GraphIterator<V, E> apply(G a, V b) {
		return new BreadthFirstIterator<V, E>(a, b);
	}

}
