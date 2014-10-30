package graph.functions;

/**
 * Interface fuer die DirectedNeighbors und UndirectedNeighbors Klassen.
 *
 * @param <A>
 * @param <B>
 * @param <C>
 */
public interface INeighbors<A, B, C> {

	C apply(A a, B b);

}
