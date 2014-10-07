package graph.util;

import java.io.IOException;

import org.jgrapht.Graph;

/**
 * A Capability to read a Graph from a String.
 * @author vab
 *
 * @param <G> the Type of Graph this is reading
 */
public interface GkaGraphReader<G extends Graph<?, ?>> {

	/**
	 * reads a Graph from the String.
	 * <p>the String can be the actual Representation of the Graph
	 * or a Path or URL
	 * <p>if str is null read() will throw a NPE
	 * <p>read() will return null (even if a Graph was partially read) on errors during reading
	 * @param  str the source of the Graph
	 * @return     the newly created Graph or null if something went wrong
	 * @throws IOException because we dont want any more catch statements
	 */
	G read(String str) throws IOException;

}
