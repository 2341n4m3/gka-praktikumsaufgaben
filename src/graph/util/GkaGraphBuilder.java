package graph.util;

import org.jgrapht.Graph;

/**
 * A capability to read a graphrepresentation line by line.
 * 
 * @author vab
 * 
 * @param <G>
 *            the Type of Graph to be built
 */
public interface GkaGraphBuilder<G extends Graph<?, ?>> {

	/**
	 * reads the next line possibly changing the content of the Graph returned
	 * by getGraph()
	 * 
	 * @param line
	 *            the line to be "parsed"
	 * @return false if the ErrorState needs to be checked
	 */
	boolean readLine(String line);

	/**
	 * get the Graph created by this Builder
	 * @return the Graph
	 */
	G getGraph();

}
