package graph.test;

import static graph.GkaGraphReaders.newDirectedWeightedReader;
import static org.junit.Assert.*;

import java.io.IOException;

import graph.Vertex;
import graph.functions.EdmondKarpFinder;
import graph.functions.FordFulkersonFinder;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

public class TestFordVsEdmond {

	DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge> graph;
	DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge> big_graph;
	DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge> test;
	FordFulkersonFinder<Vertex, DefaultWeightedEdge> fordFinder;
	EdmondKarpFinder<Vertex, DefaultWeightedEdge> edmondFinder;
	
	{
		try {
			graph =newDirectedWeightedReader().read(
							"src/graph/misc/graph4directed.gka");
			big_graph =newDirectedWeightedReader().read(
					"src/graph/misc/BigNet_3_DiGr_50_0.gka");
			test = newDirectedWeightedReader().read(
					"src/graph/misc/BigNet_3_DiGr_800_1.gka");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void maxFlow() throws IOException {
	
		
		flowFordAndEdmond(graph);
		System.out.println("River: "+fordFinder.getRiver());
		System.out.println("River: "+edmondFinder.getRiver());
		flowFordAndEdmond(big_graph);
		//flowFordAndEdmond(test);
		
		for(int i = 0; i<10;i++){
			flowFordAndEdmond(newDirectedWeightedReader().read(
					"src/graph/misc/BigNet_3_DiGr_800_"+i+".gka"));
		}
		
		for(int i = 0; i<10;i++){
			flowFordAndEdmond(newDirectedWeightedReader().read(
					"src/graph/misc/BigNet_3_DiGr_2500_"+i+".gka"));
		}
		

		
	}
	
	void flowFordAndEdmond(DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge> graph){
		fordFinder = new FordFulkersonFinder<Vertex,DefaultWeightedEdge>(graph);
		long before = System.nanoTime();
		double fordFlow = fordFinder.getMaximumFlow(new Vertex("q"), new Vertex("s"));
		long after = System.nanoTime();
		double durationMS = (after-before)/1e9;
		System.out.println("Zeit Ford Alg: "+durationMS+" ms");
		System.out.println("Flow Amount: "+ fordFlow);
		edmondFinder = new EdmondKarpFinder<Vertex,DefaultWeightedEdge>(graph);
		before = System.nanoTime();
		double edmondFlow = edmondFinder.getMaximumFlow(new Vertex("q"), new Vertex("s"));
		after = System.nanoTime();
		durationMS = (after-before)/1e9;
		System.out.println("Zeit Edmond Alg: "+durationMS+" ms");
		System.out.println("Flow Amount: "+ edmondFlow);
		assertTrue(fordFlow==edmondFlow);
	}
}
