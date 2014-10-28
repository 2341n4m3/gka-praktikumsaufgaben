package graph.impls.f4;



import graph.impls.f3.NeighborOrSuccessorChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;

/**
 * EdgeListFactory ist eine Funktion die aus einem Graph, einer Map und 2 Vertexen(Start-/ZielVertex)
	 * den kürzesten Weg zeigt.
 * @author 
 *
 * @param <G> der Typ des Graph
 * @param <V> der Typ der Vertexe
 * @param <E> der Typ der Edges
 */
//@formatter:off
public class EdgeListFactory<G extends Graph<V, E>, V, E> implements IEdgeListFactory<
	G,
	Map<V, Integer>,
	V,
	V,
	List<E>> {

	/**
	 * checkSuccessorOrNeighbor ist eine Funktion die aus zwei Vertexen prüft
	 * ob die Nachbaren sind bzw. bei Direkten Graph ob der zweite Vertex der
	 * Nachfolger vom ersten Vertex ist.
	 */
	private final NeighborOrSuccessorChecker<
	? super G,
	? super V,
	? super V,
	? extends Boolean> checkSuccessorOrNeighbor;
//@formatter:on

	public EdgeListFactory(
			NeighborOrSuccessorChecker<? super G, ? super V, ? super V, ? extends Boolean> checkSuccessorOrNeighbor) {
		this.checkSuccessorOrNeighbor = checkSuccessorOrNeighbor;
	}
    /**
     * findReverseEdgeList holt sich zuerst die Nummerierung des Zielvertexes.
     * Falls der Zielvertexes keine Nummerierung hat,
     * ist es nicht möglich vom Startvertex zum Zielvertex zu kommen.
     * 
     * @param graph der Graph
     * @param lengths Nummerierung der Knoten
     * @param start Startvertex
     * @param end Zielvertex
     * @return eine Liste der den Kürzesten weg gibt.
     */
	private List<E> findReverseEdgeList(G graph, Map<V, Integer> lengths,
			V start, V end) {
		List<E> reverseEdgeList = new ArrayList<>();
		int pathLength = 0;
		if (!lengths.containsKey(end))
			return new ArrayList<>(); 
		pathLength = lengths.get(end);
		return findReverseEdgeList(graph, lengths, start, end, pathLength,
				reverseEdgeList);
	}
	/**
	 * Rückverfolgungs Funktion vom Zielvertex zum Startvertex. 
	 * 
	 * @param graph der Graph
	 * @param lengths Nummerierung der Knoten
	 * @param v0 Startvertex
	 * @param vi Zielvertex/Vertex was grade ist.
	 * @param i Weglänge bis zum Startknoten.
	 * @param reverseEdgeList Liste der den Kürzesten weg speichert.
	 * @return eine Liste der den Kürzesten weg gibt.
	 */
	private List<E> findReverseEdgeList(G graph, Map<V, Integer> lengths, V v0,
			V vi, int i, List<E> reverseEdgeList) {
		//recursionsabbruch
		//wenn i = 1 ist ist der aktuelle Vertex vi
		//der erste auf dem Weg zum Ziel des Weges
		//bzw. auf dem Rückweg der letzte, daher abbruch
		if (i == 1) {
			reverseEdgeList.add(graph.getEdge(v0, vi));
			return reverseEdgeList;
		} else {
			//Schleifen über die Vertex die nummeriert sind
			for (V u : lengths.keySet()) {
				//prüfen ob der Vertex u Nachbar mit dem Vertex vi ist
				if (checkSuccessorOrNeighbor.apply(graph, u, vi)) {
					//falls ja prüfen es die Nummerierung um ein kleiner ist
					if (lengths.get(u) != i - 1) {
						continue;
					} else {
						//wenn ja, Kante zwichen den Vertex einfügen
						//und eine Nummerierung um 1 decrementiern
						//da der Vertex uns einen Schritt näher an den Startvertex bringt
						reverseEdgeList.add(graph.getEdge(u, vi));
						return findReverseEdgeList(graph, lengths, v0, u,
								i - 1, reverseEdgeList);
					}
				}
			}
			return new ArrayList<>();
		}
	}

	@Override
	public List<E> apply(G a, Map<V, Integer> b, V c, V d) {
		return findReverseEdgeList(a, b, c, d);
	}

}
