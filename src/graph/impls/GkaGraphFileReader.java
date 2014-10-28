package graph.impls;


import graph.util.GkaGraphBuilder;
import graph.util.GkaGraphReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jgrapht.Graph;

/**
 * Liest aus einem String einen Graphen aus.
 *
 * @param <G> der Typ des zu lesenden Graphen
 */
public class GkaGraphFileReader<G extends Graph<?, ?>> implements
		GkaGraphReader<G> {

	private final GkaGraphBuilder<G> builder;

	public GkaGraphFileReader(GkaGraphBuilder<G> builder) {
		this.builder = builder;
	}

	
	/**
	 * Liest aus einem String einen Graphen aus
	 * read() wirft null zurueck (auch wenn der Graph teilweise ausgelesen wurde) sobald ein Fehler auftritt
	 * @param  str Repräsentation des Graphen
	 * @return der neu erzeugte Graph oder null wenn was schief gegangen ist
	 * @throws IOException 
	 */
	@Override
	public G read(String path) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path),"windows-1256"));
			String currentline = br.readLine();
			while (currentline != null) {
				if (!currentline.equals("")){
					boolean lineWasOk = builder.readLine(currentline);
					if (!lineWasOk) {
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
