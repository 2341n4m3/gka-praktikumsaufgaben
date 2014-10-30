package graph.util;

import java.io.IOException;

import org.jgrapht.Graph;

/**
 * Liest einen Graphen aus einem String aus.
 *
 * @param <G>
 *            der Typ des zu lesenden Graphen
 */
public interface GkaGraphReader<G extends Graph<?, ?>> {

	/**
	 * Liest den Graphen aus einem String read() wirft null zurueck (auch wenn
	 * der Graph teilweise ausgelesen wurde) sobald ein Fehler auftritt
	 * 
	 * @param der
	 *            String aus dem der Graph ausgelesen werden soll
	 * @return der neu erzeugte Graph oder null wenn was schief gegangen ist
	 * @throws IOException
	 */
	G read(String str) throws IOException;

}
