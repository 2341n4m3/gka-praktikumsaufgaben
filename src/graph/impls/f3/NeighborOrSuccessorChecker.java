package graph.impls.f3;


public interface NeighborOrSuccessorChecker<G, V, E, S> {

	S apply(G graph, V source, V target);
}
