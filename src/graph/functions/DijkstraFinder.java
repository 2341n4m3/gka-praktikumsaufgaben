package graph.functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.graph.GraphPathImpl;

/**
 * @param <V>
 *            der Typ der Vertexe
 * @param <E>
 *            der Typ der Edges
 */
public class DijkstraFinder<V, E> {

	/**
	 * listener ist ein TraversalListener der nach den Events horcht die von
	 * einem GraphIterator emitiert werden
	 */
	private final TraversalListener<V, E> listener;
    private Graph<V, E> graph;
	Map<V,Double> toProcess = new HashMap<V, Double>();
	Map<V, Double> distances = new HashMap<V, Double>();
	Map<V, V> predecessor = new HashMap<V, V>();

	public DijkstraFinder(Graph<V, E> graph,TraversalListener<V, E> listener) {
		this.graph = graph;
		this.listener = listener;
	}

	/**
	 * 
	 * @param graph
	 *            der Graph
	 * @param start
	 *            startvertex
	 * @param end
	 *            zielvertex
	 * 
	 * @return ein Graphpath
	 */
	public GraphPath<V, E> getShortestPath(V start, V end) {

		if (!(graph.containsVertex(start) && graph.containsVertex(end))) {
			throw new IllegalArgumentException(
					"start or end ist not contain im graph");
		}

		if (start.equals(end)) {
			throw new IllegalArgumentException(
					"start and end should not be equal");
		}


		// Initialisierung der "Tabelle"
		for (V vertex : graph.vertexSet()) {
			listener.vertexTraversed(null);
			toProcess.put(vertex, start.equals(vertex) ? 0.0
					: Double.POSITIVE_INFINITY);
			distances.put(vertex, start.equals(vertex) ? 0.0
					: Double.POSITIVE_INFINITY);
			predecessor.put(vertex, start.equals(vertex) ? vertex : null);
		}

		V index = start;
		while (!toProcess.isEmpty()) {

			// Index sagt aus welcher Knoten das kleinste Gewicht der noch nicht abgearbeiteten Knoten hat.
			V currentVertex = index;
			double currentDistance = distances.get(currentVertex);

			// alle Kanten durchgehen und die Nachbarkanten raussuchen
			for (E edge : graph.edgeSet()) {

				listener.edgeTraversed(null);

				if (graph instanceof DirectedGraph) {
					if (graph.getEdgeSource(edge).equals(currentVertex)) {
						double targetDistance = currentDistance
								+ graph.getEdgeWeight(edge);
						listener.vertexTraversed(null);
						V targetVertex = graph.getEdgeTarget(edge);


						// Neue Distanz und Vorgänger speichern, falls sie kleiner als die gespeicherte ist
						if (distances.get(targetVertex) > targetDistance) {
							toProcess.put(targetVertex, targetDistance);
							distances.put(targetVertex, targetDistance);
							predecessor.put(targetVertex, currentVertex);
						}

					}
				} else {
					if (graph.getEdgeSource(edge).equals(currentVertex)
							|| graph.getEdgeTarget(edge).equals(currentVertex)) {
						double targetDistance = currentDistance
								+ graph.getEdgeWeight(edge);
						listener.vertexTraversed(null);
						V targetVertex = graph.getEdgeTarget(edge);

						if (targetVertex.equals(currentVertex))
							targetVertex = graph.getEdgeSource(edge);



						// Neue Distanz und Vorgänger speichern, falls sie kleiner als die gespeicherte ist
						if (distances.get(targetVertex) > targetDistance) {
							toProcess.put(targetVertex, targetDistance);
							distances.put(targetVertex, targetDistance);
							predecessor.put(targetVertex, currentVertex);
						}

					}
				}
			}
			// "Knoten als bearbeitet markieren"
			toProcess.remove(currentVertex);

			// neuen Knoten mit kleinstem Gewicht in den noch nicht abgearbeiteten Knoten aussuchen
				index = Collections.min(toProcess.entrySet(),new Comparator<Map.Entry<V,Double>>() {

			        @Override
			        public int compare(Entry<V, Double> o1, Entry<V, Double> o2) {
			        	if(o1.getValue() < o2.getValue()) return -1;
			        	else if(o1.getValue() > o2.getValue()) return 1;
			        	else return 0;
			        }}).getKey();

				// wenn Endknoten markiert wurde Algorithmus beenden
			if(index.equals(end)) toProcess.clear();
		}

		// Pfad bauen
		V nextCurrentVertex = end;
		V currentVertex = nextCurrentVertex;
		double weight = 0;
		List<E> path = new ArrayList<E>();

		// falls ziel knoten nie erreicht wurde
		// wird eine leere liste zurück gegeben.
		if (predecessor.get(currentVertex) != null) {
			while (nextCurrentVertex != null
					&& !nextCurrentVertex.equals(start)) {
				currentVertex = nextCurrentVertex;
				nextCurrentVertex = predecessor.get(currentVertex);
				listener.edgeTraversed(null);
				path.add(graph.getEdge(nextCurrentVertex, currentVertex));
			}

			Collections.reverse(path);
			weight = distances.get(end);
		}

		return new GraphPathImpl<V, E>(graph, start, end, path, weight);
	}
}
