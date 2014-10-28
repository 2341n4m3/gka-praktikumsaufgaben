package graph.util;

import java.io.IOException;

import org.jgrapht.Graph;

/**
 * Liest einen Graphen aus einem String aus.
 *
 * @param <G> der Typ des zu lesenden Graphen
 */
public interface GkaGraphReader<G extends Graph<?, ?>> {

	/**
	 * Liest den Graphen aus einem String
	 * <p>read() will return null (even if a Graph was partially read) on errors during reading
	 * @param  der String aus dem der Graph ausgelesen werden soll
	 * @return     der neu erzeugte Graph oder null wenn was schief gegangen ist
	 * @throws IOException 
	 */
	G read(String str) throws IOException;

}
