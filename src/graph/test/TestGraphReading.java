package graph.test;

import static graph.GkaGraphReaders.*;
import static org.junit.Assert.assertTrue;
import graph.Vertex;
import graph.util.GkaGraphReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedPseudograph;
import org.junit.Test;

public class TestGraphReading {

	@Test
	public void testGraph1() throws IOException {
		GkaGraphReader<DirectedGraph<Vertex, DefaultEdge>> reader = newDirectedReader();
		DirectedGraph<Vertex, DefaultEdge> graph = reader
				.read("src/graph/misc/graph1.gka");
		check(graph);
	}

	@Test
	public void testGraph2() throws IOException {
		GkaGraphReader<WeightedPseudograph<Vertex, DefaultWeightedEdge>> reader = newUndirectedWeightedReader();
		WeightedPseudograph<Vertex, DefaultWeightedEdge> graph = reader
				.read("src/graph/misc/graph5.gka");
		check2(graph);
	}

	private <G extends WeightedPseudograph<Vertex, DefaultWeightedEdge>> void check2(
			G graph) {
		Set<Vertex> expectedVertices = new HashSet<Vertex>();
		Collections.addAll(expectedVertices, new Vertex("v1"),
				new Vertex("v2"), new Vertex("v3"), new Vertex("v4"),
				new Vertex("v5"), new Vertex("v6"), new Vertex("v7"));

		assertTrue("alle erwarteten Knoten gefunde", graph.vertexSet()
				.containsAll(expectedVertices));
		assertTrue("aber eben auch nicht mehr als diese",
				expectedVertices.containsAll(graph.vertexSet()));

		checkContains2(graph, "v1", "v2", 5D);
		checkContains2(graph, "v1", "v3", 7D);
		checkContains2(graph, "v1", "v4", 5D);
		checkContains2(graph, "v1", "v5", 3D);
		checkContains2(graph, "v1", "v6", 2D);
		checkContains2(graph, "v1", "v7", 6D);
		checkContains2(graph, "v2", "v3", 4D);
		checkContains2(graph, "v2", "v4", 1D);
		checkContains2(graph, "v2", "v5", 8D);
		checkContains2(graph, "v2", "v6", 3D);
		checkContains2(graph, "v2", "v7", 5D);
		checkContains2(graph, "v3", "v4", 3D);
		checkContains2(graph, "v3", "v5", 4D);
		checkContains2(graph, "v3", "v6", 7D);
		checkContains2(graph, "v3", "v7", 1D);
		checkContains2(graph, "v4", "v5", 7D);
		checkContains2(graph, "v4", "v6", 4D);
		checkContains2(graph, "v4", "v7", 4D);
		checkContains2(graph, "v5", "v6", 5D);
		checkContains2(graph, "v5", "v7", 3D);
		checkContains2(graph, "v6", "v7", 8D);
	}

	private <G extends Graph<Vertex, DefaultEdge>> void check(G graph) {
		Set<Vertex> expectedVertices = new HashSet<Vertex>();
		Collections.addAll(expectedVertices, new Vertex("a"), new Vertex("b"),
				new Vertex("c"), new Vertex("d"), new Vertex("e"), new Vertex(
						"f"), new Vertex("g"), new Vertex("h"),
				new Vertex("i"), new Vertex("j"), new Vertex("k"));

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
				+ ") enthalten", graph.containsEdge(new Vertex(sourceVertex),
				new Vertex(targetVertex)));
	}

	private void checkContains2(
			WeightedPseudograph<Vertex, DefaultWeightedEdge> graph,
			String sourceVertex, String targetVertex, Double weight) {

		assertTrue("Graph sollte Kante (" + sourceVertex + "," + targetVertex
				+ ") enthalten", graph.containsEdge(new Vertex(sourceVertex),
				new Vertex(targetVertex)));
		assertTrue("Graph sollte Kante mit" + sourceVertex + "," + targetVertex
				+ "," + weight + ") enthalten",
				graph.getEdgeWeight(graph.getEdge(new Vertex(sourceVertex),
						new Vertex(targetVertex))) == weight);
	}
}
