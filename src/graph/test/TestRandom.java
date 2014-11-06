package graph.test;

import static graph.GkaGraphReaders.newUndirectedWeightedReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedPseudograph;
import org.junit.Test;

import graph.Vertex;
import graph.PathFinders;
import graph.test.Counter;
import graph.functions.DijkstraFinder;
/*
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.UnmodifiableDirectedGraph;
import org.jgrapht.graph.UnmodifiableUndirectedGraph;
*/

public class TestRandom {

	WeightedPseudograph<Vertex, DefaultWeightedEdge> undirected;
	Map<String, Integer> undirectedcounts = new HashMap<>();
	
	{
		try {
			undirected =newUndirectedWeightedReader().read(
							"src/graph/misc/UnitTestGraph.gka");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testDijkstra() throws IOException {
		
		Counter<Vertex, DefaultEdge> undirectedCounter = new Counter<Vertex, DefaultEdge>();
		DijkstraFinder<Graph<Vertex, DefaultEdge>, Vertex, DefaultEdge> undirectedFinder =
				PathFinders.newDijkstraPathFinder(undirectedCounter);


		
		GraphPath<Vertex, DefaultEdge> undirectedPath =
				undirectedFinder.apply((Graph)undirected, new Vertex("v14"), new Vertex("v20"));
		
		System.out.println("Dijkstra undirect");
		System.out.println(undirectedCounter);
		System.out.println(undirectedcounts);
		undirectedcounts.clear();
		undirectedCounter.clear();
		
		System.out.println("undirectedPath: " + undirectedPath);
		System.out.println("undirectedPath.getWeight(): " + undirectedPath.getWeight());
		System.out.println("---------------------------");
		
		

	}
}
