package graph;

import graph.GkaGraphReaders;
import graph.Vertex;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.EdgeNameProvider;
import org.jgrapht.ext.VertexNameProvider;

public class GraphViz {
	
	private static String replaceName(String name){
		name=name.replaceAll("ü", "ue");
		name=name.replaceAll("ö", "oe");
		name=name.replaceAll("ä", "ae");
		return name;
	}

	public static <V, E> void vizIt(String path, final Graph<Vertex, E> graph)
			throws IOException {
		DOTExporter<Vertex, E> export =
				new DOTExporter<>(new VertexNameProvider<Vertex>() {

					@Override
					public String getVertexName(Vertex vertex) {
						String name = vertex.toString();
                        name = replaceName(name);
						return name;
					}
				}, new VertexNameProvider<Vertex>() {

					@Override
					public String getVertexName(Vertex vertex) {
						String name = vertex.toString();
						name = replaceName(name);
						return vertex.toString();
					}
				}, 
						  new EdgeNameProvider<E>() {
						  
						  @Override 
						  public String getEdgeName(E edge) { 
						 if(graph instanceof WeightedGraph){
						  double weight = graph.getEdgeWeight(edge);
						  return String.valueOf(weight);
						 }
						 return "";
							  } }

						 );

		export.export(new BufferedWriter(new FileWriter(path + ".dot")), graph);
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		vizIt("src/graph/misc/graph1",
				GkaGraphReaders.newDirectedReader().read("src/graph/misc/graph1.gka"));
		vizIt("src/graph/misc/graph2",
				GkaGraphReaders.newUndirectedReader().read("src/graph/misc/graph2.gka"));
		vizIt("src/graph/misc/graph3",
				GkaGraphReaders.newUndirectedWeightedReader().read("src/graph/misc/graph3.gka"));
		vizIt("src/graph/misc/graph4",
				GkaGraphReaders.newUndirectedWeightedReader().read("src/graph/misc/graph4.gka"));
		vizIt("src/graph/misc/graph5",
				GkaGraphReaders.newUndirectedWeightedReader().read("src/graph/misc/graph5.gka"));
		vizIt("src/graph/misc/graph6",
				GkaGraphReaders.newDirectedReader().read("src/graph/misc/graph6.gka"));
		vizIt("src/graph/misc/graph7",
				GkaGraphReaders.newUndirectedWeightedReader().read("src/graph/misc/graph7.gka"));
		vizIt("src/graph/misc/graph8",
				GkaGraphReaders.newUndirectedWeightedReader().read("src/graph/misc/graph8.gka"));
	}

}
