package graph.test;

import static graph.GkaGraphReaders.newUndirectedWeightedReader;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedPseudograph;
import org.junit.Test;

import graph.util.GkaGraphReader;
import graph.GraphCreator;
import graph.GraphSave;
import graph.GraphType;
import graph.Vertex;

public class TestGraphSave {
	

	@Test
	public void testSave() throws IOException {
		int vertexAmount = 5;
		int edgeAmount = 5;
		WeightedGraph<Vertex, DefaultEdge> g1 = GraphCreator.buildGraph(vertexAmount, edgeAmount);
		File datei = new File("src/graph/misc/testSave.gka");
		GraphSave.graphWriter(g1,GraphType.UNDIRECTEDWEIGHTEDGRAPH, datei);
		
		GkaGraphReader<WeightedPseudograph<Vertex, DefaultWeightedEdge>> reader =
				newUndirectedWeightedReader();
		WeightedPseudograph<Vertex, DefaultWeightedEdge> graph =
				reader.read("src/graph/misc/testSave.gka");
		for(DefaultEdge edge : graph.edgeSet() ){
			assertFalse("Fehler bei Kante " + edge.toString(), g1.containsEdge(edge));	
		}
		datei.delete();
	}

	

}
