package graph.impls.f4;

/**
 * Eine Funktion mit 3 Argumenten
*
* @param <A>
* @param <B>
* @param <C>
* @param <D>
* @param <E>
*/
public interface IEdgeListFactory<A, B, C, D, E> {
	E apply(A a, B b, C c, D d);
}
