package graph;

import graph.functions.BreadthFirstIteratorFactory;
import graph.functions.DirectedNeighbors;
import graph.functions.UndirectedNeighbors;
import graph.functions.NeighborChecker;
import graph.functions.PathFinder;
import graph.functions.SuccessorChecker;
import graph.functions.EdgeListFactory;
import graph.functions.GraphPathFactory;

import org.jgrapht.DirectedGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.WeightedPseudograph;

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
	
	public static <V, E> PathFinder<DefaultDirectedWeightedGraph<V, E>, V, E> newDirectedWeightedBreadthPathFinder(
			TraversalListener<V, E> listener) {

		SuccessorChecker<DefaultDirectedWeightedGraph<V, E>, V, E> neighborChecker = new SuccessorChecker<>();

		EdgeListFactory<DefaultDirectedWeightedGraph<V, E>, V, E> edgeListFactory = new EdgeListFactory<>(
				neighborChecker);

		GraphPathFactory<DefaultDirectedWeightedGraph<V, E>, V, E> graphPathFactory = new GraphPathFactory<>(
				edgeListFactory);

		BreadthFirstIteratorFactory<DefaultDirectedWeightedGraph<V, E>, V, E> iteratorFactory = new BreadthFirstIteratorFactory<>();

		DirectedNeighbors<DefaultDirectedWeightedGraph<V, E>, V, E> neighbors = new DirectedNeighbors<>();

		return new PathFinder<>(listener, graphPathFactory,
				iteratorFactory, neighbors);
	}
	
	public static <V, E> PathFinder<WeightedPseudograph<V, E>, V, E> newUndirectedWeightedBreadthPathFinder(
			TraversalListener<V, E> listener) {

		NeighborChecker<WeightedPseudograph<V, E>, V, E> neighborChecker = new NeighborChecker<>();

		EdgeListFactory<WeightedPseudograph<V, E>, V, E> edgeListFactory = new EdgeListFactory<>(
				neighborChecker);

		GraphPathFactory<WeightedPseudograph<V, E>, V, E> graphPathFactory = new GraphPathFactory<>(
				edgeListFactory);

		BreadthFirstIteratorFactory<WeightedPseudograph<V, E>, V, E> iteratorFactory = new BreadthFirstIteratorFactory<>();

		UndirectedNeighbors<WeightedPseudograph<V, E>, V, E> neighbors = new UndirectedNeighbors<>();

		return new PathFinder<>(listener, graphPathFactory,
				iteratorFactory, neighbors);
	}



}
