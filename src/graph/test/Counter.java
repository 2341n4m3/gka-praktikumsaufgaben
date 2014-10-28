package graph.test;

import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;


/**
 * Zaehlt beim Traversieren ueber einen Graphen mittels Iterator 
 * unter anderem wie haeufig ein Knoten oder eine Kante durchlaufen wurde
 *
 * @param <V>
 * @param <E>
 */
public class Counter<V,E> implements TraversalListener<V, E>{

	public int connectedComponentFinished;
	public int connectedComponentStarted;
	public int edgeTraversed;
	public int vertexTraversed;
	public int vertexFinished;

	@Override
	public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
		connectedComponentFinished++;
	}

	@Override
	public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
		connectedComponentStarted++;
	}

	@Override
	public void edgeTraversed(EdgeTraversalEvent<V, E> e) {
		edgeTraversed++;
	}

	@Override
	public void vertexTraversed(VertexTraversalEvent<V> e) {
		vertexTraversed++;
	}

	@Override
	public void vertexFinished(VertexTraversalEvent<V> e) {
		vertexFinished++;
	}

	@Override
	public String toString() {
		return "PerfCount [edgeTraversed="
				+ edgeTraversed + ", vertexTraversed=" + vertexTraversed
				+  "]";
	}
	
	public void clear(){
		connectedComponentFinished = 0;
		connectedComponentStarted = 0;
		edgeTraversed = 0;
		vertexTraversed = 0;
		vertexFinished = 0;
	}

}
