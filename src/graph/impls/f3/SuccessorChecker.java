package graph.impls.f3;


import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;


public class SuccessorChecker<G extends DirectedGraph<V,E>, V, E> implements NeighborOrSuccessorChecker<G, V, V, Boolean> {

	@Override
	public Boolean apply(G graph, V source, V target) {
		return Graphs.successorListOf(graph, source).contains(target);
	}

}
