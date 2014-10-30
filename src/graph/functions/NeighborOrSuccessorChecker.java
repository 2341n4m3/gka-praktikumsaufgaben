package graph.functions;

/**
 * Interface fuer die SuccessorChecker und NeighborChecker Klassen.
 *
 * @param <G>
 * @param <V>
 * @param <E>
 * @param <S>
 */
public interface NeighborOrSuccessorChecker<G, V, E, S> {

	S apply(G graph, V source, V target);
}
