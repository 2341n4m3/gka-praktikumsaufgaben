package graph.test;

import static graph.GkaGraphReaders.newDirectedReader;
import static org.junit.Assert.assertTrue;
import graph.Vertex;
import graph.util.GkaGraphReader;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;

public class TestGraphReading {

	@Test
	public void testGraph1() throws IOException {
		GkaGraphReader<DirectedGraph<Vertex, DefaultEdge>> reader =
				newDirectedReader();
		DirectedGraph<Vertex, DefaultEdge> graph =
				reader.read("D:/Studium/Semester 5/workspace/GKA_Praktikum_1/src/graph/misc/graph1.gka");
		check(graph);
	}



	private <G extends Graph<Vertex, DefaultEdge>> void check(G graph) {
		Set<Vertex> expectedVertices = new HashSet<Vertex>();
		Collections.addAll(expectedVertices, new Vertex("a"),
				new Vertex("b"), new Vertex("c"), new Vertex("d"),
				new Vertex("e"), new Vertex("f"), new Vertex("g"),
				new Vertex("h"), new Vertex("i"), new Vertex("j"),
				new Vertex("k"));

		assertTrue("alle erwarteten Knoten gefunde", graph.vertexSet()
				.containsAll(expectedVertices));
		assertTrue("aber eben auch nicht mehr als diese",
				expectedVertices.containsAll(graph.vertexSet()));

		checkContains(graph, "a", "b");
		checkContains(graph, "a", "c");
		checkContains(graph, "a", "h");
		checkContains(graph, "a", "k");
		checkContains(graph, "b", "b");
		checkContains(graph, "b", "j");
		checkContains(graph, "b", "k");
		checkContains(graph, "b", "i");
		checkContains(graph, "c", "a");
		checkContains(graph, "c", "d");
		checkContains(graph, "d", "a");
		checkContains(graph, "d", "e");
		checkContains(graph, "d", "k");
		checkContains(graph, "e", "b");
		checkContains(graph, "e", "c");
		checkContains(graph, "e", "e");
		checkContains(graph, "e", "f");
		checkContains(graph, "f", "c");
		checkContains(graph, "f", "g");
		checkContains(graph, "g", "g");
		checkContains(graph, "g", "e");
		checkContains(graph, "g", "b");
		checkContains(graph, "g", "d");
		checkContains(graph, "h", "b");
		checkContains(graph, "h", "c");
		checkContains(graph, "i", "a");
		checkContains(graph, "i", "c");
		checkContains(graph, "i", "i");
		checkContains(graph, "j", "k");
		checkContains(graph, "j", "c");
		checkContains(graph, "j", "a");
		checkContains(graph, "j", "b");
		checkContains(graph, "k", "c");
		checkContains(graph, "k", "g");
		checkContains(graph, "k", "d");
	}

	private void checkContains(Graph<Vertex, DefaultEdge> graph,
			String sourceVertex, String targetVertex) {
		assertTrue("Graph sollte Kante (" + sourceVertex + "," + targetVertex
				+ ") enthalten", graph.containsEdge(
				new Vertex(sourceVertex), new Vertex(targetVertex)));
	}
}
