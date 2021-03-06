package graph.functions;

/**
 * Interface fuer alle Graphiteratoren.
 *
 * @param <A>
 * @param <B>
 * @param <C>
 */
public interface IIteratorFactory<A, B, C> {
	C apply(A a, B b);
}
