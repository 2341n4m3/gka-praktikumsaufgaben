package graph.functions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.traverse.GraphIterator;

/**
 * Ein PathFinder ist eine Funktion die aus einem Graph und zwei Vertexen einen
 * GraphPath findet PathFinder sind dazu gedachteinen kürzesten Pfad im Graph zu
 * finden.
 * 
 * Der PathFinder selbst findet nur die Längen der Wege zu Vertexes vom
 * Startvertex aus gesehen danach wird die resultierende Map an die Pathfactory
 * weitergereicht. Zum Iterieren wird ein GraphIterator benutzt. Die
 * Nachbarn/Nachfolger des aktuellen Vertex werden über die findNeighbors
 * Funktion ermittelt Der Listener wird dem Iterator übergeben um daraus
 * Zugriffe auf den Graphen ableiten zu können
 *
 * @param <G>
 *            der Typ des Graph
 * @param <V>
 *            der Typ der Vertexe
 * @param <E>
 *            der Typ der Edges
 */
public class PathFinder<G extends Graph<V, E>, V, E> {

	/**
	 * listener ist ein TraversalListener der nach den Events horcht die von
	 * einem GraphIterator erzeugt werden
	 */
	private final TraversalListener<V, E> listener;

	/**
	 * pathFactory ist eine Funktion die aus einem Graph einer Map und 2
	 * Vertexen einen GraphPath erstellt der Graph ist der Graph durch den der
	 * Pfad läuft die Map hat als Key Vertexes, die Values dazu sind die Längen
	 * vom Startvertex aus gesehen der erste Vertex ist der Anfang des Weges der
	 * zweite Vertex ist das Ende des Weges der GraphPath ist die Repräsentation
	 * des Weges
	 */
	private final IEdgeListFactory<? super G, ? super Map<V, Integer>, ? super V, ? super V, ? extends GraphPath<V, E>> pathFactory;

	/**
	 * iteratorFactory ist eine Funktion die aus einem Graph und einem Vertex
	 * einen GraphIterator erstellt mit Iterator kann man die Iteration steuern
	 */
	private final IIteratorFactory<? super G, ? super V, ? extends GraphIterator<V, E>> iteratorFactory;

	/**
	 * findToLenghten ist eine Funktion die aus einem Graph und einem Vertex
	 * eine Vertexmenge erstellt. Die Elemente der Menge sind Vertexe für die
	 * ggf. eine Länge gesetzt werden muss
	 */
	private final INeighbors<? super G, ? super V, ? extends Set<V>> findNeighbors;

	public PathFinder(
			TraversalListener<V, E> listener,
			IEdgeListFactory<? super G, ? super Map<V, Integer>, ? super V, ? super V, ? extends GraphPath<V, E>> pathFactory,
			IIteratorFactory<? super G, ? super V, ? extends GraphIterator<V, E>> iteratorFactory,
			INeighbors<? super G, ? super V, ? extends Set<V>> findNeighbors) {
		this.listener = listener;
		this.pathFactory = pathFactory;
		this.iteratorFactory = iteratorFactory;
		this.findNeighbors = findNeighbors;
	}

	/**
	 * Kennzeichnung/Nummerierung der Knoten
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
		GraphIterator<V, E> iterator = iteratorFactory.apply(graph, start);

		iterator.addTraversalListener(listener);
		// start wird mit 0 gekennzeichnet
		vertexDistances.put(start, 0);

		/**
		 * Schleife laeuft solange bis der Iterator nur noch einen naechsten
		 * Knoten hat und finish true ist also der Endknoten markiert wurde.
		 */
		while (iterator.hasNext() && !finished) {
			currentVertex = iterator.next();
			// gibt ein Set von Nachbarn des gerade angeschauten Vertex
			needDistances = findNeighbors.apply(graph, currentVertex);

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
		return pathFactory.apply(graph, vertexDistances, start, end);
	}
}
