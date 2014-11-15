package graph.test;

import static graph.GkaGraphReaders.newDirectedWeightedReader;
import static org.junit.Assert.*;
import graph.Vertex;
import graph.functions.EdmondKarpFinder;
import graph.functions.FordFulkersonFinder;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

public class TestFordVsEdmond {

	DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge> graph;
	
	{
		try {
			graph =newDirectedWeightedReader().read(
							"src/graph/misc/graph4directed.gka");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void maxFlow() {
		FordFulkersonFinder<Vertex, DefaultWeightedEdge> fordFinder = new FordFulkersonFinder<Vertex,DefaultWeightedEdge>(graph);
		double fordFlow = fordFinder.getMaximumFlow(new Vertex("q"), new Vertex("s"));
		System.out.println(fordFlow);
		System.out.println("River: "+fordFinder.getRiver());
		EdmondKarpFinder<Vertex, DefaultWeightedEdge> edmondFinder = new EdmondKarpFinder<Vertex,DefaultWeightedEdge>(graph);
		double edmondFlow = edmondFinder.getMaximumFlow(new Vertex("q"), new Vertex("s"));
		System.out.println(edmondFlow);
		assertTrue(fordFlow==edmondFlow);
	}

}
