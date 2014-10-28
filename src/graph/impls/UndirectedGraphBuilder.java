package graph.impls;


import graph.Vertex;
import graph.util.GkaGraphBuilder;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Pseudograph;


/**
 * Liest aus .gka Dateien Zeile fuer Zeile den ungerichteten ungewichteten Graphen aus.
 */
public class UndirectedGraphBuilder
		extends
		AbstractBaseGraphBuilder<UndirectedGraph<Vertex, DefaultEdge>, Vertex, DefaultEdge>
		implements GkaGraphBuilder<UndirectedGraph<Vertex, DefaultEdge>> {

	public UndirectedGraphBuilder() {
		graph = new Pseudograph<Vertex, DefaultEdge>(DefaultEdge.class);
	}
	@Override
	public boolean readLine(String line) {
		//loescht die unrelevanten Zeichen aus der Textzeile
		line = line.replace(" ", "");
		line = line.replace(";","");
		//erstellt aus den wichtigen Teilen einen String Array
		String[] vertices = line.split("--");
		//je nach Elementanzahl werden die bestimmten Elemente verschieden interpretiert und ein Graph erzeugt
		if (vertices.length == 2) {
			Vertex source = makeVertexFrom(vertices[0]);
			Vertex target = makeVertexFrom(vertices[1]);
			graph.addVertex(source);
			graph.addVertex(target);
			graph.addEdge(source, target);
			return true;
		 } else if (vertices.length == 1){
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
