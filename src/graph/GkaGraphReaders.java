package graph;


import graph.impls.DirectedGraphBuilder;
import graph.impls.DirectedWeightedGraphBuilder;
import graph.impls.GkaGraphFileReader;
import graph.impls.UndirectedGraphBuilder;
import graph.impls.UndirectedWeightedGraphBuilder;

import org.jgrapht.DirectedGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedPseudograph;


/**
 * creates a new Graph
 */
public class GkaGraphReaders {
	private GkaGraphReaders() {
	}

	/**
	 * 
	 * @return create a new DirectedGraph without "attributiert" and "gewichtet"
	 */
	public static GkaGraphFileReader<DirectedGraph<Vertex, DefaultEdge>> newDirectedReader() {
		return new GkaGraphFileReader<DirectedGraph<Vertex, DefaultEdge>>(
				new DirectedGraphBuilder());
	}

	
	/**
	 * 
	 * @return create a new DirectedWeightedMultigraph with "gewichtet" and  without "attributiert"
	 */
	public static GkaGraphFileReader<DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge>> newDirectedWeightedReader() {
		return new GkaGraphFileReader<DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge>>(
				new DirectedWeightedGraphBuilder());
	}

	/**
	 * 
	 * @return create a new UndirectedGraph without "attributiert" and "gewichtet"
	 */
	public static GkaGraphFileReader<UndirectedGraph<Vertex, DefaultEdge>> newUndirectedReader() {
		return new GkaGraphFileReader<UndirectedGraph<Vertex, DefaultEdge>>(
				new UndirectedGraphBuilder());
	}
	
	/**
	 * 
	 * @return create a new WeightedPseudograph with "gewichtet" and without "attributiert"
	 */
	public static GkaGraphFileReader<WeightedPseudograph<Vertex, DefaultWeightedEdge>> newUndirectedWeightedReader() {
		return new GkaGraphFileReader<WeightedPseudograph<Vertex, DefaultWeightedEdge>>(
				new UndirectedWeightedGraphBuilder());
	}
}
