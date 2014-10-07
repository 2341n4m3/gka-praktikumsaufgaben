package graph.impls.f2;

public interface IIteratorFactory<A, B, C> {
	C apply(A a, B b);
}
