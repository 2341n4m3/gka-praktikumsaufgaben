package graph;

import graph.impls.f2.BreadthFirstIteratorFactory;
import graph.impls.f2.DirectedNeighbors;
import graph.impls.f2.UndirectedNeighbors;
import graph.impls.f3.NeighborChecker;
import graph.impls.f3.PathFinder;
import graph.impls.f3.SuccessorChecker;
import graph.impls.f4.EdgeListFactory;
import graph.impls.f4.GraphPathFactory;

import org.jgrapht.DirectedGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.event.TraversalListener;

/**
 * @author krystian
 * Enthaelt zwei statische Methoden um fuer gerichtete sowie ungerichtete Graphen mithilfe 
 * des BFS Algorithmus den kuerzesten Pfad von einem Knoten s zu einem Knoten t zu finden.
 */
public class PathFinders {

	private PathFinders() {
	}

	/**
	 * 
	 * Baut PathFinder fuer gerichtete Graphen zusammen
	 * @param listener
	 * @return new GraphPath
	 */
	public static <V, E> PathFinder<DirectedGraph<V, E>, V, E> newDirectedBreadthPathFinder(
			TraversalListener<V, E> listener) {

		SuccessorChecker<DirectedGraph<V, E>, V, E> successorCecker = new SuccessorChecker<>();

		EdgeListFactory<DirectedGraph<V, E>, V, E> edgeListFactory = new EdgeListFactory<>(
				successorCecker);

		GraphPathFactory<DirectedGraph<V, E>, V, E> graphPathFactory = new GraphPathFactory<>(
				edgeListFactory);

		BreadthFirstIteratorFactory<DirectedGraph<V, E>, V, E> iteratorFactory = new BreadthFirstIteratorFactory<>();

		DirectedNeighbors<DirectedGraph<V, E>, V, E> neighbors = new DirectedNeighbors<>();

		return new PathFinder<>(listener, graphPathFactory,
				iteratorFactory, neighbors);
	}

	/**
	 * 
	 * Baut PathFinder fuer ungerichtete Graphen zusammen
	 * @param listener
	 * @return
	 */
	public static <V, E> PathFinder<UndirectedGraph<V, E>, V, E> newUndirectedBreadthPathFinder(
			TraversalListener<V, E> listener) {

		NeighborChecker<UndirectedGraph<V, E>, V, E> neighborChecker = new NeighborChecker<>();

		EdgeListFactory<UndirectedGraph<V, E>, V, E> edgeListFactory = new EdgeListFactory<>(
				neighborChecker);

		GraphPathFactory<UndirectedGraph<V, E>, V, E> graphPathFactory = new GraphPathFactory<>(
				edgeListFactory);

		BreadthFirstIteratorFactory<UndirectedGraph<V, E>, V, E> iteratorFactory = new BreadthFirstIteratorFactory<>();

		UndirectedNeighbors<UndirectedGraph<V, E>, V, E> neighbors = new UndirectedNeighbors<>();

		return new PathFinder<>(listener, graphPathFactory,
				iteratorFactory, neighbors);
	}



}
