package graph.functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.graph.GraphPathImpl;

/**
 * 
 * @author
 * 
 * @param <G>
 *            der Typ des Graph
 * @param <V>
 *            der Typ der Vertexe
 * @param <E>
 *            der Typ der Edges
 */
public class DijkstraFinder<G extends Graph<V, E>, V, E> {

	/**
	 * listener ist ein TraversalListener der nach den Events horcht die von
	 * einem GraphIterator emitiert werden
	 */
	private final TraversalListener<V, E> listener;

	public DijkstraFinder(TraversalListener<V, E> listener) {
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
	public GraphPath<V, E> apply(G graph, V start, V end) {

		if (!(graph.containsVertex(start) && graph.containsVertex(end))) {
			throw new IllegalArgumentException(
					"start or end ist not contain im graph");
		}

		if (start.equals(end)) {
			throw new IllegalArgumentException(
					"start and end should not be equal");
		}

		List<V> toProcess = new ArrayList<V>();
		Map<V, Double> distances = new HashMap<V, Double>();
		Map<V, V> predecessor = new HashMap<V, V>();

		// Initialisierung der "Tabelle"
		for (V vertex : graph.vertexSet()) {
			listener.vertexTraversed(null);
			toProcess.add(vertex);
			distances.put(vertex, start.equals(vertex) ? 0.0
					: Double.POSITIVE_INFINITY);
			predecessor.put(vertex, start.equals(vertex) ? vertex : null);
		}

		V index = start;
		while (!toProcess.isEmpty()) {

			// kleinsten holen
			double smallDist = Double.POSITIVE_INFINITY;
			// index sagt aus welche Vertex den kleinsten gewicht zum vorherigen
			// vertex hat.
			V currentVertex = toProcess.get(toProcess.indexOf(index));
			double currentDistance = distances.get(currentVertex);

			// alle Kanten durchgehen und die Kanten raussuchen für
			// ungerichteten Graph
			for (E edge : graph.edgeSet()) {

				listener.edgeTraversed(null);

				if (graph instanceof DirectedGraph) {
					if (graph.getEdgeSource(edge).equals(currentVertex)) {
						double targetDistance = currentDistance
								+ graph.getEdgeWeight(edge);
						V targetVertex = graph.getEdgeTarget(edge);


						// kante mit dem niedrigsten wert merken im jetzigen
						// durchlauf
						// falls die karte aber schon "besucht" war, dann nicht
						// mitzählen
						if (targetDistance < smallDist
								&& toProcess.contains(targetVertex)) {
							index = targetVertex;
							smallDist = targetDistance;
						}

						// neue Distanz speichern, falls die gespeicherte
						// Distanz größer war
						if (distances.get(targetVertex) > targetDistance) {
							distances.put(targetVertex, targetDistance);
							predecessor.put(targetVertex, currentVertex);
						}

					}
				} else {
					if (graph.getEdgeSource(edge).equals(currentVertex)
							|| graph.getEdgeTarget(edge).equals(currentVertex)) {
						double targetDistance = currentDistance
								+ graph.getEdgeWeight(edge);
						V targetVertex = graph.getEdgeTarget(edge);

						if (targetVertex.equals(currentVertex))
							targetVertex = graph.getEdgeSource(edge);

						// kante mit dem niedrigsten wert merken im jetzigen
						// durchlauf
						// falls die karte aber schon "besucht" war, dann nicht
						// mitzählen
						if (targetDistance < smallDist
								&& toProcess.contains(targetVertex)) {
							index = targetVertex;
							smallDist = targetDistance;
						}

						// neue Distanz speichern, falls die gespeicherte
						// Distanz größer war
						if (distances.get(targetVertex) > targetDistance) {
							distances.put(targetVertex, targetDistance);
							predecessor.put(targetVertex, currentVertex);
						}

					}
				}
			}

			// "Knoten als bearbeitet markieren"
			toProcess.remove(currentVertex);

			// falls keiner weitere nachbarn übrig ist
			// neuen nächsten knoten auswählen.
			if (index.equals(currentVertex) && !toProcess.isEmpty())
				index = toProcess.get(0);
		}

		// Pfad bauen
		V nextCurrentVertex = end;
		V currentVertex = nextCurrentVertex;
		double weight = 0;
		List<E> path = new ArrayList<E>();

		// predecessor.get(currentVertex);
		// falls ziel knoten nie erreicht wurde
		// wird eine leere liste zurück gegeben.
		if (predecessor.get(currentVertex) != null) {
			while (nextCurrentVertex != null
					&& !nextCurrentVertex.equals(start)) {
				currentVertex = nextCurrentVertex;
				nextCurrentVertex = predecessor.get(currentVertex);
				path.add(graph.getEdge(nextCurrentVertex, currentVertex));
			}

			Collections.reverse(path);
			weight = distances.get(end);
		}

		return new GraphPathImpl<V, E>(graph, start, end, path, weight);
	}
}
