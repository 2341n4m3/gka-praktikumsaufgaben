package graph.impls.f3;



import org.jgrapht.Graph;
import org.jgrapht.Graphs;


public class NeighborChecker<G extends Graph<V,E>, V, E> implements NeighborOrSuccessorChecker<G, V, V, Boolean> {

	@Override
	public Boolean apply(G graph, V source, V target) {
		return Graphs.neighborListOf(graph, source).contains(target);
	}

}
