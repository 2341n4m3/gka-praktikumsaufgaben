package graph.test;

import static graph.GkaGraphReaders.newDirectedReader;
import static graph.GkaGraphReaders.newDirectedWeightedReader;
import static graph.GkaGraphReaders.newUndirectedReader;
import static graph.GkaGraphReaders.newUndirectedWeightedReader;
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
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedPseudograph;
import org.junit.Test;



public class TestGraphSearching {

	DirectedGraph<Vertex, DefaultEdge> directed;
	UndirectedGraph<Vertex, DefaultEdge> undirected;
	DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge> directedWeighted;
	WeightedPseudograph<Vertex, DefaultWeightedEdge> undirectedWeighted;


	{
		try {
			directed = newDirectedReader().read("D:/Studium/Semester 5/workspace/GKA_Praktikum_1/src/graph/misc/graph1.gka");
			undirected =newUndirectedReader().read("D:/Studium/Semester 5/workspace/GKA_Praktikum_1/src/graph/misc/graph2.gka");		
			directedWeighted = newDirectedWeightedReader().read("D:/Studium/Semester 5/workspace/GKA_Praktikum_1/src/graph/misc/owngraph1.gka");
			undirectedWeighted = newUndirectedWeightedReader().read("D:/Studium/Semester 5/workspace/GKA_Praktikum_1/src/graph/misc/graph3.gka");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBreadth() {
		PerfCount<Vertex,DefaultEdge> directedCounter = new PerfCount<Vertex, DefaultEdge>();

		PathFinder<DirectedGraph<Vertex, DefaultEdge>, Vertex, DefaultEdge> directedFinder =
				PathFinders.newDirectedBreadthPathFinder(directedCounter);
		PerfCount<Vertex,DefaultEdge> undirectedCounter = new PerfCount<Vertex, DefaultEdge>();
		
		PathFinder<UndirectedGraph<Vertex, DefaultEdge>, Vertex, DefaultEdge> undirectedFinder =
				PathFinders.newUndirectedBreadthPathFinder(undirectedCounter);
		
		PerfCount<Vertex,DefaultWeightedEdge> directedWeightedCounter = new PerfCount<Vertex, DefaultWeightedEdge>();
		PathFinder<DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge>, Vertex, DefaultWeightedEdge> directedWeightedFinder =
				PathFinders.newDirectedWeightedBreadthPathFinder(directedWeightedCounter);
		
		PerfCount<Vertex,DefaultWeightedEdge> undirectedWeightedCounter = new PerfCount<Vertex, DefaultWeightedEdge>();
		PathFinder<WeightedPseudograph<Vertex, DefaultWeightedEdge>, Vertex, DefaultWeightedEdge> undirectedWeightedFinder =
				PathFinders.newUndirectedWeightedBreadthPathFinder(undirectedWeightedCounter);

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
		
		GraphPath<Vertex, DefaultWeightedEdge> directedWeightedPath =
				directedWeightedFinder.apply(directedWeighted, new Vertex("Bremen"), new Vertex("Rotenburg"));
		System.out.println("breadth undirect");
		System.out.println(directedWeightedCounter);
		System.out.println(directedWeightedPath);
		System.out.println(directedWeightedPath.getEdgeList().size());
		
		GraphPath<Vertex, DefaultWeightedEdge> undirectedWeightedPath =
				undirectedWeightedFinder.apply(undirectedWeighted, new Vertex("Bremen"), new Vertex("Rotenburg"));
		System.out.println("breadth undirect");
		System.out.println(undirectedWeightedCounter);
		System.out.println(undirectedWeightedPath);
		System.out.println(undirectedWeightedPath.getEdgeList().size());
		

		checkPaths(directedPath, undirectedPath, directedWeightedPath, undirectedWeightedPath);
	}


	private void checkPaths(GraphPath<Vertex, DefaultEdge> directedPath,
			GraphPath<Vertex, DefaultEdge> undirectedPath, GraphPath<Vertex, DefaultWeightedEdge> directedWeightedPath, GraphPath<Vertex, DefaultWeightedEdge> undirectedWeightedPath) {
		List<String> directedVertexList = new ArrayList<String>();
		Collections.addAll(directedVertexList, "a", "k", "g");
		List<String> undirectedVertexList = new ArrayList<String>();
		Collections.addAll(undirectedVertexList, "a", "b", "h");
		List<String> directedWeightedVertexList = new ArrayList<String>();
		Collections.addAll(directedWeightedVertexList, "Bremen", "Bremerhaven", "Rotenburg");
		List<String> undirectedWeightedVertexList = new ArrayList<String>();
		Collections.addAll(undirectedWeightedVertexList, "Bremen", "Bremerhaven", "Rotenburg");

		check(directedPath, directedVertexList);
		check(undirectedPath, undirectedVertexList);
		check(directedWeightedPath, directedWeightedVertexList);
		check(undirectedWeightedPath, undirectedWeightedVertexList);
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
