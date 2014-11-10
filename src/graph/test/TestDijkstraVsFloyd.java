package graph.test;

import static graph.GkaGraphReaders.newUndirectedWeightedReader;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedPseudograph;
import org.junit.Test;

import graph.Vertex;
import graph.test.Counter;
import graph.functions.DijkstraFinder;
import graph.functions.FloydFinder;


public class TestDijkstraVsFloyd {

	WeightedPseudograph<Vertex, DefaultWeightedEdge> ed;
	
	{
		try {
			ed =newUndirectedWeightedReader().read(
							"src/graph/misc/graph3.gka");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Test
	public void testDijkstra() throws IOException {
		
		Counter<Vertex, DefaultWeightedEdge> counter = new Counter<Vertex, DefaultWeightedEdge>();
		DijkstraFinder<Vertex, DefaultWeightedEdge> dijkstraFinder = new DijkstraFinder<Vertex, DefaultWeightedEdge>(ed, counter);
		FloydFinder<Vertex, DefaultWeightedEdge> floydFinder = new FloydFinder<Vertex, DefaultWeightedEdge>(ed, counter);


		
		GraphPath<Vertex, DefaultWeightedEdge> dijkstraPath =
				dijkstraFinder.getShortestPath( new Vertex("Husum"), new Vertex("Hamburg"));
		
		
		System.out.println("Dijkstra ");
		System.out.println(counter);
		counter.clear();
		
		System.out.println("dijkstraPath: " + dijkstraPath);
		System.out.println("dijkstraPath.getWeight(): " + dijkstraPath.getWeight());
		System.out.println("---------------------------");
		
		GraphPath<Vertex, DefaultWeightedEdge> floydPath =
				floydFinder.getShortestPath( new Vertex("Husum"), new Vertex("Hamburg"));
		
		System.out.println("Floyd ");
		System.out.println(counter);
		counter.clear();
		
		System.out.println("floydPath: " + floydPath);
		System.out.println("floydPath.getWeight(): " + floydPath.getWeight());
		System.out.println("---------------------------");
		
		assertTrue(equals(dijkstraPath,floydPath));
		
		dijkstraPath =
				dijkstraFinder.getShortestPath( new Vertex("Minden"), new Vertex("Hamburg"));
		
		
		System.out.println("Dijkstra ");
		System.out.println(counter);
		counter.clear();
		
		System.out.println("dijkstraPath: " + dijkstraPath);
		System.out.println("dijkstraPath.getWeight(): " + dijkstraPath.getWeight());
		System.out.println("---------------------------");
		
		floydPath =
				floydFinder.getShortestPath( new Vertex("Minden"), new Vertex("Hamburg"));
		
		
		System.out.println("Floyd ");
		System.out.println(counter);
		counter.clear();
		
		System.out.println("floydPath: " + floydPath);
		System.out.println("floydPath.getWeight(): " + floydPath.getWeight());
		System.out.println("---------------------------");
		
		assertTrue(equals(dijkstraPath,floydPath));
		
		dijkstraPath =
				dijkstraFinder.getShortestPath(new Vertex("Münster"), new Vertex("Hamburg"));
		
		System.out.println("Dijkstra ");
		System.out.println(counter);
		counter.clear();
		
		System.out.println("dijkstraPath: " + dijkstraPath);
		System.out.println("dijkstraPath.getWeight(): " + dijkstraPath.getWeight());
	
	
	floydPath =
			floydFinder.getShortestPath( new Vertex("Münster"), new Vertex("Hamburg"));
	
	System.out.println("Floyd ");
	System.out.println(counter);
	counter.clear();
	
	System.out.println("floydPath: " + floydPath);
	System.out.println("floydPath.getWeight(): " + floydPath.getWeight());
	System.out.println("---------------------------");
	
	assertTrue(equals(dijkstraPath,floydPath));
	
	}
	
	boolean equals(GraphPath<Vertex,DefaultWeightedEdge> gp1, GraphPath<Vertex,DefaultWeightedEdge> gp2){
		return gp1.getEdgeList().equals(gp2.getEdgeList());
		
	}
}
