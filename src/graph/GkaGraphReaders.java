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
 * Enthaelt statische Methoden zum Erzuegen der unterschiedlichen Graphentypen.
 */
public class GkaGraphReaders {
	private GkaGraphReaders() {
	}

	/**
	 * 
	 * @return Erzeugt einen gerichteten ungewichteten Graphen
	 */
	public static GkaGraphFileReader<DirectedGraph<Vertex, DefaultEdge>> newDirectedReader() {
		return new GkaGraphFileReader<DirectedGraph<Vertex, DefaultEdge>>(
				new DirectedGraphBuilder());
	}

	
	/**
	 * 
	 * @return Erzeugt einen gerichteten gewichteten Graphen
	 */
	public static GkaGraphFileReader<DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge>> newDirectedWeightedReader() {
		return new GkaGraphFileReader<DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge>>(
				new DirectedWeightedGraphBuilder());
	}

	/**
	 * 
	 * @return Erzeugt einen ungerichteten ungewichteten Graphen
	 */
	public static GkaGraphFileReader<UndirectedGraph<Vertex, DefaultEdge>> newUndirectedReader() {
		return new GkaGraphFileReader<UndirectedGraph<Vertex, DefaultEdge>>(
				new UndirectedGraphBuilder());
	}
	
	/**
	 * 
	 * @return Erzeugt einen ungerichteten gewichteten Graphen
	 */
	public static GkaGraphFileReader<WeightedPseudograph<Vertex, DefaultWeightedEdge>> newUndirectedWeightedReader() {
		return new GkaGraphFileReader<WeightedPseudograph<Vertex, DefaultWeightedEdge>>(
				new UndirectedWeightedGraphBuilder());
	}
}
