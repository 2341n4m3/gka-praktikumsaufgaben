package graph.functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.GraphPathImpl;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

public class BreadthFirstSearch <V, E>
{
    

    private DirectedGraph<V, E> graph;

    public BreadthFirstSearch(DirectedGraph<V, E> graph)
    {
        this.graph = graph;
    }

    /**
     * @return the graph on which this algorithm operates
     */
    public Graph<V, E> getGraph()
    {
        return graph;
    }

 
    public GraphPath<V, E> getShortestPath(V start, V end)
    {
    	
		if (!(graph.containsVertex(start) && graph.containsVertex(end))) {
			throw new IllegalArgumentException(
					"start or end ist not contain im graph");
		}

		if (start.equals(end)) {
			throw new IllegalArgumentException(
					"start and end should not be equal");
		}
    	
		boolean finished = false;
		// Im vertexDistances werden die nummerierten Knoten gespeichert
		Map<V, Integer> vertexDistances = new HashMap<>();
		// Im needDistances sind alle direkten Nachbarn eines Knoten die
		// noch ihre minimale Entfernung zu dem Knoten benoetigen
		Set<V> needDistances = null;
		V currentVertex = null;

		// Auswahl des Iterator
		GraphIterator<V, E> iterator = new BreadthFirstIterator<V, E>(graph, start);

		// start wird mit 0 gekennzeichnet
		vertexDistances.put(start, 0);

		/**
		 * Schleife laeuft solange bis der Iterator nur noch einen naechsten
		 * Knoten hat und finish true ist also der Endknoten markiert wurde.
		 */
		while (iterator.hasNext() && !finished) {
			currentVertex = iterator.next();
			// gibt ein Set von Nachbarn des gerade angeschauten Vertex
			needDistances = new HashSet<>();
			needDistances.addAll(Graphs.successorListOf(graph, currentVertex));

			/**
			 * Schleife ueber die Nachbarn. Ist Nachbar nummeriert wird geprueft
			 * ob die neue Zahl kleiner als die jetzige Zahl ist. Wenn ja
			 * erhaelt der Knoten eine neue Nummerierung, sonst erhaelt es zum
			 * ersten mal eine Nummerierung.
			 */
			for (V each : needDistances) {
				int currentLength = vertexDistances.get(currentVertex);
				int newLength = currentLength + 1;
				if (vertexDistances.containsKey(each)) {
					if (newLength < vertexDistances.get(each)) {
						vertexDistances.put(each, newLength);
					}
				} else {
					vertexDistances.put(each, newLength);
				}
				finished = end.equals(currentVertex);
			}
		}
		// Aufruf von GraphPathFactory
		// Gibt ein Pfad zurueck der den kuerzestens Weg von Start- zu
		// Zielvertex repräsentiert.
		return constructPath(vertexDistances, start, end);
    }
    
	public GraphPath<V, E> constructPath(Map<V, Integer> lengths, V start,
			V end) {
		List<E> edgeList = findReverseEdgeList(lengths, start, end);

		double weight = 0;
		for (E eachEdge : edgeList) {
			weight += graph.getEdgeWeight(eachEdge);
		}

		Collections.reverse(edgeList);

		return new GraphPathImpl<V, E>(graph, start, end, edgeList, weight);
	}

	private List<E> findReverseEdgeList(Map<V, Integer> lengths,
			V start, V end) {
		List<E> reverseEdgeList = new ArrayList<>();
		int pathLength = 0;
		if (!lengths.containsKey(end))
			return new ArrayList<>();
		pathLength = lengths.get(end);
		return findReverseEdgeList(lengths, start, end, pathLength,
				reverseEdgeList);
	}
	
	private List<E> findReverseEdgeList(Map<V, Integer> lengths, V v0,
			V vi, int i, List<E> reverseEdgeList) {
		// Rekursionsabbruch
		// wenn i = 1 ist ist der aktuelle Vertex vi
		// der erste auf dem Weg zum Ziel des Weges
		// bzw. auf dem Rückweg der letzte, daher abbruch
		if (i == 1) {
			reverseEdgeList.add(graph.getEdge(v0, vi));
			return reverseEdgeList;
		} else {
			// Schleifen über die Vertex die nummeriert sind
			for (V u : lengths.keySet()) {
				// prüfen ob der Vertex u Nachbar des Vertex vi ist
				if (Graphs.successorListOf(graph, u).contains(vi)) {
					// falls ja prüfen ob die Nummerierung um 1 kleiner ist
					if (lengths.get(u) != i - 1) {
						continue;
					} else {
						// wenn ja, Kante zwichen den Vertex einfügen
						// und die gesuchte Nummerierung um 1 verkleinern
						// da der Vertex uns einen Schritt naeher an den
						// Startvertex bringt
						reverseEdgeList.add(graph.getEdge(u, vi));
						return findReverseEdgeList(lengths, v0, u,
								i - 1, reverseEdgeList);
					}
				}
			}
			return new ArrayList<>();
		}
	}

}