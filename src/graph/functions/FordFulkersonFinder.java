package graph.functions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class FordFulkersonFinder<V, E> {

	protected DefaultDirectedWeightedGraph<V, DefaultWeightedEdge> graph;
	protected Map<DefaultWeightedEdge, Double> flow;
	protected V quelle;
	protected V senke;
	protected Map<V, Marker> marked;

	public FordFulkersonFinder(
			DefaultDirectedWeightedGraph<V, DefaultWeightedEdge> g) {
		this.graph = g;
	}

	/**
	 * @param q
	 * @param s
	 * @return gibt den maximalen Fluss von der Quelle bis zur Senke durch das Netzwerk zurueck
	 */
	public Double getMaximumFlow(V q, V s) {

		if (!(graph.containsVertex(q) && graph.containsVertex(s))) {
			throw new IllegalArgumentException(
					"source or target ist not contained in graph");
		}

		if (q.equals(s)) {
			throw new IllegalArgumentException(
					"source and target should not be equal");
		}

		this.quelle = q;
		this.senke = s;

		// jede Kante wird in flow mit dem Wert 0 hinzugefuegt und die fuer die
		// Quelle
		// wird ein Marker mit Vorgaenger null und als Inkrement voerst
		// unendlich initialisiert
		initialize();

		// solange nicht alle markierten Knoten auch als inspiziert
		// gekennzeichnet wurden
		while (!areAllMarkedEdgesInspected()) {
			// inspiziere einen beliebigen naechsten Knoten
			inspectAndMark();
			// wenn inspizierter Knoten die Senke ist erweiter den Fluss und
			// setze alle Markierungen zurueck
			if (isVertexMarked(this.senke)) {
				widenFlow();
				resetForNextRun();
			}
		}

		return calculateFlow();
	}

	/**
	 * @return boolean ob alle markierten Knoten auch inspiziert sind
	 */
	protected boolean areAllMarkedEdgesInspected() {
		for (Marker m : marked.values()) {
			if (!m.isInspected()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * inspiziert einen beliebigen uninspizierten aber markierten Knoten, 
	 * indem er alle unmarkierten nachbarknoten markiert 
	 * (den Vorgaengerknoten und das Inkrement setzt)
	 */
	protected void inspectAndMark() {
		V current_vertex = getNextUninspectedVertex();
		Marker current_marker = marked.get(current_vertex);
		current_marker.setInspected();

		for (DefaultWeightedEdge edge : graph.outgoingEdgesOf(current_vertex)) {
			V target = graph.getEdgeTarget(edge);
			if (!isVertexMarked(target) && hasSufficientCapacity(edge)) {
				Marker marker = new Marker(current_vertex, Math.min(
						current_marker.getIncrement(),
						graph.getEdgeWeight(edge) - flow.get(edge)));
				marker.setForward();
				marked.put(target, marker);
			}
		}

		for (DefaultWeightedEdge edge : graph.incomingEdgesOf(current_vertex)) {
			V source = graph.getEdgeSource(edge);
			if (!isVertexMarked(source) && flow.get(edge) > 0) {
				Marker marker = new Marker(current_vertex, Math.min(
						current_marker.getIncrement(), flow.get(edge)));
				marker.setBackward();
				marked.put(source, marker);
			}
		}
	}

	/**
	 * @return den naechsten markierten aber noch uninspizierten Knoten
	 */
	protected V getNextUninspectedVertex() {
		for (V vertex : marked.keySet()) {
			if (!marked.get(vertex).isInspected()) {
				return vertex;
			}
		}

		return null;
	}

	/**
	 * @param edge
	 * @return prueft ob noch mehr durch die Kante fliessen kann als im Fluss bereits festgelegt
	 */
	protected boolean hasSufficientCapacity(DefaultWeightedEdge edge) {
		return flow.get(edge) < graph.getEdgeWeight(edge);
	}

	/**
	 * @param vertex
	 * @return prueft ob Knoten markiert ist
	 */
	protected boolean isVertexMarked(V vertex) {
		return marked.get(vertex) != null;
	}

	/**
	 * erweitert den Fluss, indem er von der Senkenmarkierung ueber 
	 * die Vorgaenger rueckwaerts bis zur Quelle je nachdem ob die 
	 * Kante eine Vorwaerts- oder Rueckwaertskante war das Inkrement 
	 * der entsprechenden Kante im Fluss hinzufuegt oder abzieht.
	 */
	protected void widenFlow() {
		V vertex = senke;
		Marker s_marker = marked.get(senke);

			while (!vertex.equals(quelle)) {
				Marker marker = marked.get(vertex);
				V predecessor = marker.getPredecessor();

					if (marker.isForward()) {
						DefaultWeightedEdge edge = graph.getEdge(predecessor,
								vertex);
						flow.put(edge, flow.get(edge) + s_marker.getIncrement());
					} else {
						DefaultWeightedEdge edge = graph.getEdge(vertex,
								predecessor);
						flow.put(edge, flow.get(edge) - s_marker.getIncrement());
					}

				vertex = predecessor;
			}

	}

	protected double calculateFlow() {
		Set<V> q_set = new HashSet<V>(this.marked.keySet());

		double outgoing_value = 0;
		for (V vertex : q_set) {
			for (DefaultWeightedEdge e : graph.outgoingEdgesOf(vertex)) {
				if (!q_set.contains(graph.getEdgeTarget(e))) {
					outgoing_value += flow.get(e);
				}
			}
		}

		double incoming_value = 0;
		for (V vertex : q_set) {
			for (DefaultWeightedEdge e : graph.incomingEdgesOf(vertex)) {
				if (!q_set.contains(graph.getEdgeSource(e))) {
					incoming_value += flow.get(e);
				}
			}
		}

		return outgoing_value - incoming_value;
	}

	/**
	 * Fuegt dem Fluss alle Kanten mit dem Gewicht 0 hinzu und setzt fuer 
	 * die Quelle eine Markierung mit Vorgaenger = null und Inkrement = unendlich
	 */
	protected void initialize() {
		flow = new HashMap<DefaultWeightedEdge, Double>();
		for (DefaultWeightedEdge e : graph.edgeSet()) {
			flow.put(e, 0.0);
		}

		resetForNextRun();
	}

	/**
	 * setzt fuer die Quelle eine Markierung mit Vorgaenger = null und Inkrement = unendlich
	 */
	protected void resetForNextRun() {
		marked = new HashMap<V, Marker>();
		marked.put(quelle, new Marker());
	}

	/**
	 * @return gibt den Fluss zurueck
	 */
	public Map<DefaultWeightedEdge, Double> getRiver() {
		return flow;
	}

	/**
	 * innere Hilfsklasse um die Knoten mit mehr Metadaten anreichern zu koennen (Vorgaenger,Inkrement...)
	 */
	class Marker {

		private V predecessor;
		private double increment;
		private boolean inspected;
		private boolean forward;

		public Marker() {
			this(null, Double.POSITIVE_INFINITY);
		}

		public Marker(V predecessor, Double increment) {
			this.predecessor = predecessor;
			this.increment = increment;
			this.inspected = false;
		}

		public V getPredecessor() {
			return predecessor;
		}

		public double getIncrement() {
			return increment;
		}

		public boolean isForward() {
			return forward == true;
		}

		public boolean isBackward() {
			return !isForward();
		}

		public void setInspected() {
			inspected = true;
		}

		public boolean isInspected() {
			return inspected;
		}

		public void setForward() {
			forward = true;
		}

		public void setBackward() {
			forward = false;
		}
	}

}
