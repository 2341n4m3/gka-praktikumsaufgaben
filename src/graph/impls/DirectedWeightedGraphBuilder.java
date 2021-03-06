package graph.impls;

import graph.Vertex;
import graph.util.GkaGraphBuilder;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Liest aus .gka Dateien Zeile fuer Zeile den gerichteten gewichteten Graphen
 * aus.
 */
public class DirectedWeightedGraphBuilder
		extends
		AbstractBaseGraphBuilder<DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge>, Vertex, DefaultWeightedEdge>
		implements
		GkaGraphBuilder<DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge>> {

	public DirectedWeightedGraphBuilder() {
		graph = new DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);
	}

	public boolean readLine(String line) {
		// loescht die unrelevanten Zeichen aus der Textzeile
		line = line.replace(" ", "");
		line = line.replace(";", "");
		// erstellt aus den wichtigen Teilen einen String Array
		String[] vertices = line.split("->|:");
		DefaultWeightedEdge addedEdge = null;
		// je nach Elementanzahl werden die bestimmten Elemente verschieden
		// interpretiert und ein Graph erzeugt
		if (vertices.length == 3) {
			Vertex source = makeVertexFrom(vertices[0]);
			Vertex target = makeVertexFrom(vertices[1]);
			graph.addVertex(source);
			graph.addVertex(target);
			addedEdge = graph.addEdge(source, target);
			graph.setEdgeWeight(addedEdge, Double.parseDouble(vertices[2]));
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
