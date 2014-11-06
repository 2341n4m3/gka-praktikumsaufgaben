package graph.test;

import static graph.GkaGraphReaders.newUndirectedReader;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Pseudograph;
import org.junit.Test;

import graph.util.GkaGraphReader;
import graph.GraphSave;
import graph.GraphType;
import graph.Vertex;

public class TestGraphSave {
	
	UndirectedGraph<Vertex, DefaultEdge> g1 = 
			new Pseudograph<Vertex, DefaultEdge>(DefaultEdge.class);
	


	@Test
	public void testSave() throws IOException {
		buildGraph(g1, 5);
		File datei = new File("src/graph/misc/testSave.gka");
		GraphSave.GraphWriter(g1,GraphType.UNDIRECTEDGRAPH, datei);
		
		GkaGraphReader<UndirectedGraph<Vertex, DefaultEdge>> reader =
				newUndirectedReader();
		UndirectedGraph<Vertex, DefaultEdge> graph =
				reader.read("src/graph/misc/testSave.gka");
		for(DefaultEdge edge : graph.edgeSet() ){
			assertFalse("Fehler bei Kante " + edge.toString(), g1.containsEdge(edge));	
		}
		
	}

	

	private void buildGraph(Graph<Vertex,DefaultEdge> graph, int n) {
		CompleteGraphGenerator<Vertex, DefaultEdge> completeGenerator = 
			new CompleteGraphGenerator<Vertex, DefaultEdge>(n);
		VertexFactory<Vertex> vertexFactory = new VertexFactory<Vertex>() {
			private int i = 0;
			public Vertex createVertex() {
				i++;
				String iName = String.valueOf(i);
				return new Vertex(iName);
			}
		};
		completeGenerator.generateGraph(graph, vertexFactory, null);
	}
	

}
