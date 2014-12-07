package graph.functions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class EdmondKarpFinder<V, E> {

	protected DefaultDirectedWeightedGraph<V, DefaultWeightedEdge> graph;
	protected DefaultDirectedWeightedGraph<V, DefaultWeightedEdge> working_graph;
	protected Map<DefaultWeightedEdge, Double> flow;
	protected V quelle;
	protected V senke;
	protected Map<V, Marker> marked;
	private Queue<E> queue;

	@SuppressWarnings("unchecked")
	public EdmondKarpFinder(
			DefaultDirectedWeightedGraph<V, DefaultWeightedEdge> graph) {
		this.graph = graph;
		working_graph = (DefaultDirectedWeightedGraph<V, DefaultWeightedEdge>) graph
				.clone();
		this.queue = new LinkedList<E>();
	}

	/**
	 * @param q
	 * @param s
	 * @return die maximale Flussstärke
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

		// fuege dem Fluss alle Kanten mit Anfangskapazitaet 0 hinzu und
		// markier die Quelle mit Vorgaenger = null und Inkrement = unendlich
		initialize();

		// solange ein augmentierender Pfad gefunden wird
		// inspiziere die entsprechenden Knoten und erweiter den Fluß
		while (true) {
			calculateNextVertexQueue();

			if (this.queue.isEmpty()) {
				break;
			}
			inspectAndMark();
			if (isVertexMarked(this.senke)) {
				widenFlow();
				resetForNextRun();
			}
		}

		return calculateFlow();
	}

	/**
	 * inspiziert den naechsten noch nicht inspizierten aber zum erweiternden
	 * Pfad gehoerenden Knoten und markiert den nachfolgenden Knoten nachdem
	 * sein Inkrement berechnet wurde.
	 */
	protected void inspectAndMark() {
		DefaultWeightedEdge edge = (DefaultWeightedEdge) queue.poll();
		V current_vertex = graph.getEdgeSource(edge);
		Marker current_marker = marked.get(current_vertex);

		current_marker.setInspected();

		V target = graph.getEdgeTarget(edge);
		edge = graph.getEdge(current_vertex, target);
		Marker marker = new Marker(current_vertex, Math.min(
				current_marker.getIncrement(),
				graph.getEdgeWeight(edge) - flow.get(edge)));
		marked.put(target, marker);

	}

	/**
	 * @param vertex
	 * @return prueft ob Knoten markiert ist
	 */
	protected boolean isVertexMarked(V vertex) {
		return marked.get(vertex) != null;
	}

	/**
	 * erweitert den Fluss, indem er von der Senkenmarkierung ueber die
	 * Vorgaenger rueckwaerts bis zur Quelle geht und das Inkrement der
	 * entsprechenden Kante im Fluss hinzufuegt oder abzieht. Außerdem wird die
	 * Kante falls sie bereits die volle Kapazitaet erreicht hat aus dem Graphen
	 * entfernt. Damit der BFS sie beim naechsten Schritt nicht noch einmal
	 * passiert.
	 */
	protected void widenFlow() {
		V vertex = senke;
		Marker s_marker = marked.get(senke);

		while (!vertex.equals(quelle)) {
			Marker marker = marked.get(vertex);
			V predecessor = marker.getPredecessor();

			DefaultWeightedEdge edge = graph.getEdge(predecessor, vertex);
			flow.put(edge, flow.get(edge) + s_marker.getIncrement());
			if (flow.get(edge) == (graph.getEdgeWeight(edge))) {
				graph.removeEdge(edge);
			}

			vertex = predecessor;
		}

	}

	/**
	 * @return addiert die Kapazitaeten aller von der Quelle ausgehenden Kanten zusammen.
	 */
	private double calculateFlow() {

		double outgoing_value = 0;
		for (Map.Entry<DefaultWeightedEdge, Double> e : flow.entrySet()) {
			if (working_graph.getEdgeSource(e.getKey()).equals(quelle)) {
				outgoing_value += e.getValue();
			}
		}

		return outgoing_value;
	}

	/**
	 * der naechste augmentierende Pfad wird ueber BFS berechnet
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void calculateNextVertexQueue() {
		if (this.queue.isEmpty()) {
			BreadthFirstSearch<V, E> bfs = new BreadthFirstSearch<V, E>(
					(DirectedGraph) graph);
			queue.addAll(bfs.getShortestPath(this.quelle, this.senke)
					.getEdgeList());
		}
	}

	protected void initialize() {
		flow = new HashMap<DefaultWeightedEdge, Double>();
		for (DefaultWeightedEdge e : graph.edgeSet()) {
			flow.put(e, 0.0);
		}

		resetForNextRun();
	}

	protected void resetForNextRun() {
		marked = new HashMap<V, Marker>();
		marked.put(quelle, new Marker());
	}

	public Map<DefaultWeightedEdge, Double> getRiver() {
		return flow;
	}

	protected void afterResetHook() {
		this.queue.clear();
	}

	class Marker {

		private V predecessor;
		private double increment;
		private boolean inspected;

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

		public void setInspected() {
			inspected = true;
		}

		public boolean isInspected() {
			return inspected;
		}

	}
}