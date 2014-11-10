package graph.test;

import static graph.GkaGraphReaders.newDirectedWeightedReader;
import static org.junit.Assert.assertTrue;
import graph.Vertex;
import graph.functions.DijkstraFinder;
import graph.functions.FloydFinder;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

public class TestDijkstraVsFloydBIG {
	
	
	Graph<Vertex, DefaultWeightedEdge> directed;
	
	{
		try {
			directed =newDirectedWeightedReader().read(
							"src/graph/misc/graphbig.gka");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void test() {
		Vertex start = new Vertex("v1");
		Vertex end = new Vertex("v76");
		Counter<Vertex, DefaultWeightedEdge> dijkstraCounter = new Counter<Vertex, DefaultWeightedEdge>();
		DijkstraFinder< Vertex, DefaultWeightedEdge> dijkstraFinder =
				new DijkstraFinder<Vertex, DefaultWeightedEdge>(directed, dijkstraCounter);


		
		GraphPath<Vertex, DefaultWeightedEdge> dijkstraPath =
				dijkstraFinder.getShortestPath(start, end);
		
		System.out.println("Dijkstra");
		System.out.println(dijkstraCounter);
		dijkstraCounter.clear();
		
		System.out.println("directedPath: " + dijkstraPath);
		System.out.println("directedPath.getWeight(): " + dijkstraPath.getWeight());
		System.out.println("---------------------------");
		
		Counter<Vertex, DefaultWeightedEdge> floydCounter = new Counter<Vertex, DefaultWeightedEdge>();
		FloydFinder<Vertex, DefaultWeightedEdge> floydFinder = new FloydFinder<Vertex,DefaultWeightedEdge>(directed,floydCounter);
		GraphPath<Vertex, DefaultWeightedEdge> floydPath = floydFinder.getShortestPath(start,end);
		
		System.out.println("Floyd");
		System.out.println(floydCounter);
		floydCounter.clear();
		
		System.out.println("directedPath: " + floydPath);
		System.out.println("directedPath.getWeight(): " + floydPath.getWeight());
		System.out.println("---------------------------");
		
		assertTrue(equals(dijkstraPath,floydPath));
	}
	
	
	boolean equals(GraphPath<Vertex,DefaultWeightedEdge> gp1, GraphPath<Vertex,DefaultWeightedEdge> gp2){
		return gp1.getEdgeList().equals(gp2.getEdgeList());
		
	}

}
