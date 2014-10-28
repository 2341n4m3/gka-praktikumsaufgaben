package graph.test;

import static graph.GkaGraphReaders.newDirectedReader;
import static graph.GkaGraphReaders.newUndirectedReader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import graph.PathFinders;
import graph.Vertex;
import graph.impls.f3.PathFinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;



public class TestGraphSearching {

	DirectedGraph<Vertex, DefaultEdge> directed;
	UndirectedGraph<Vertex, DefaultEdge> undirected;


	{
		try {
			directed = newDirectedReader().read("C:/Users/krystian/git/GKA_Praktikum_1/src/graph/misc/graph1.gka");
			undirected =newUndirectedReader().read("C:/Users/krystian/git/GKA_Praktikum_1/src/graph/misc/graph2.gka");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBreadth() {
		// Zählt mit wie oft wir über knoten und Kanten gegangen sind
		Counter<Vertex,DefaultEdge> directedCounter = new Counter<Vertex, DefaultEdge>();

		PathFinder<DirectedGraph<Vertex, DefaultEdge>, Vertex, DefaultEdge> directedFinder =
				PathFinders.newDirectedBreadthPathFinder(directedCounter);
		Counter<Vertex,DefaultEdge> undirectedCounter = new Counter<Vertex, DefaultEdge>();
		
		PathFinder<UndirectedGraph<Vertex, DefaultEdge>, Vertex, DefaultEdge> undirectedFinder =
				PathFinders.newUndirectedBreadthPathFinder(undirectedCounter);

		GraphPath<Vertex, DefaultEdge> directedPath =
				directedFinder.apply(directed, new Vertex("a"), new Vertex("g"));
		System.out.println("breadth direct");
		System.out.println(directedCounter);
		System.out.println(directedPath);
		System.out.println(directedPath.getEdgeList().size());
		
		GraphPath<Vertex, DefaultEdge> undirectedPath =
				undirectedFinder.apply(undirected, new Vertex("a"), new Vertex("h"));
		System.out.println("breadth undirect");
		System.out.println(undirectedCounter);
		System.out.println(undirectedPath);
		System.out.println(undirectedPath.getEdgeList().size());

		checkPaths(directedPath, undirectedPath);
	}


	private void checkPaths(GraphPath<Vertex, DefaultEdge> directedPath,
			GraphPath<Vertex, DefaultEdge> undirectedPath) {
		List<String> directedVertexList = new ArrayList<String>();
		Collections.addAll(directedVertexList, "a", "k", "g");
		List<String> undirectedVertexList = new ArrayList<String>();
		Collections.addAll(undirectedVertexList, "a", "b", "h");


		check(directedPath, directedVertexList);
		check(undirectedPath, undirectedVertexList);

	}

	private <E> void check(GraphPath<Vertex, E> path,
			List<String> vertices) {
		Iterator<String> vertexIterator = vertices.iterator();
		Iterator<E> edgeIterator = path.getEdgeList().iterator();

		String expectedStartVertex = vertexIterator.next();
		assertEquals("checke ersten Knoten", expectedStartVertex,
				path.getStartVertex().getName());

		String currentExpectedVertex = null;
		String currentActualVertex = null;
		Graph<Vertex, E> graph = path.getGraph();
		
		while (vertexIterator.hasNext() && edgeIterator.hasNext()) {
			currentExpectedVertex = vertexIterator.next();
			E dg = edgeIterator.next();
			currentActualVertex = graph.getEdgeTarget(dg).getName();
			if (graph instanceof UndirectedGraph
					&& !(currentExpectedVertex.equals(currentActualVertex)))
				currentActualVertex = graph.getEdgeSource(dg).getName();

			assertEquals("checke Knoten des Wegs", currentExpectedVertex,
					currentActualVertex);
			
		}

		assertTrue("es sollten keine weiteren Kanten gefunden worden sein",
				!edgeIterator.hasNext());
		assertTrue("es sollten keine weiteren Knoten erwartet werden",
				!vertexIterator.hasNext());

	}

}
