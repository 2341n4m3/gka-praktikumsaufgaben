package graph.impls;

import graph.Vertex;
import graph.util.GkaGraphBuilder;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedPseudograph;

/**
 * Liest aus .gka Dateien Zeile fuer Zeile den ungerichteten gewichteten Graphen
 * aus.
 */
public class UndirectedWeightedGraphBuilder
		extends
		AbstractBaseGraphBuilder<WeightedPseudograph<Vertex, DefaultWeightedEdge>, Vertex, DefaultEdge>
		implements
		GkaGraphBuilder<WeightedPseudograph<Vertex, DefaultWeightedEdge>> {

	public UndirectedWeightedGraphBuilder() {
		graph = new WeightedPseudograph<Vertex, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);
	}

	@Override
	public boolean readLine(String line) {
		// loescht die unrelevanten Zeichen aus der Textzeile
		line = line.replace(" ", "");
		line = line.replace(";", "");
		// erstellt aus den wichtigen Teilen einen String Array
		String[] vertices = line.split("--|:");
		DefaultWeightedEdge addedEdge = null;
		// je nach Elementanzahl werden die bestimmten Elemente verschieden
		// interpretiert und ein Graph erzeugt
		if (vertices.length >=2) {
			Vertex source = makeVertexFrom(vertices[0]);
			Vertex target = makeVertexFrom(vertices[1]);
			graph.addVertex(source);
			graph.addVertex(target);
			addedEdge = graph.addEdge(source, target);
			if(vertices.length>2){
				graph.setEdgeWeight(addedEdge, Double.parseDouble(vertices[2]));
				}
			return true;
		} else if (vertices.length == 2) {
			Vertex source = makeVertexFrom(vertices[0]);
			Vertex target = makeVertexFrom(vertices[1]);
			graph.addVertex(source);
			graph.addVertex(target);
			addedEdge = graph.addEdge(source, target);
			return true;
		} else if (vertices.length == 1) {
			Vertex source = makeVertexFrom(vertices[0]);
			graph.addVertex(source);
			return true;

		} else {
			return false;
		}
	};

	@Override
	protected Vertex makeVertexFrom(String str) {
		return new Vertex(str);
	}

}
