package graph.util;

import org.jgrapht.Graph;

/**
 * Ermoeglicht das Erzeugen eines Graphen aus einer .gka Datei Zeile fuer Zeile.
 * 
 * @param <G>
 *            zu erzeugender Graphentyp
 */
public interface GkaGraphBuilder<G extends Graph<?, ?>> {

	/**
	 * Liest die Zeile der Stringrepräsentation des Graphen und fuegt
	 * gegebenenfalls Knoten/Kanten hinzu
	 * 
	 * @param line
	 *            die zu parsende Zeile
	 * @return false wenn die Zeile nicht interpretiert werden kann sonst true
	 */
	boolean readLine(String line);

	/**
	 * Gibt den durch den Builder erzeugten Graphen zurueck
	 * 
	 * @return the Graph
	 */
	G getGraph();

}
