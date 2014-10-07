package graph.impls;


import graph.util.GkaGraphBuilder;
import graph.util.GkaGraphReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jgrapht.Graph;

/**
 * Read a Graph from a String.
 *
 * @param <G> the Type of Graph this is reading
 */
public class GkaGraphFileReader<G extends Graph<?, ?>> implements
		GkaGraphReader<G> {

	private final GkaGraphBuilder<G> builder;

	public GkaGraphFileReader(GkaGraphBuilder<G> builder) {
		this.builder = builder;
	}

	
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
	@Override
	public G read(String path) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path),"windows-1256"));
			String currentline = br.readLine();
			while (currentline != null) {
				if (!currentline.equals("")){
				//	currentline=currentline.replaceAll(" ", "");
					boolean lineWasOk = builder.readLine(currentline);
					if (!lineWasOk) {
//						System.err.println("Error in File reading: "
//								+ builder.getErrorMessage());
						return null;
					} else {
						currentline = br.readLine();
					}
				}else {
					currentline = br.readLine();
				}
			}
			return builder.getGraph();
		} finally {
			br.close();
		}
	}

}
