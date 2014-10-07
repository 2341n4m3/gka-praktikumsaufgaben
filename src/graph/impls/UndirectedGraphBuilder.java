package graph.impls;


import graph.Vertex;
import graph.util.GkaGraphBuilder;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Pseudograph;

public class UndirectedGraphBuilder
		extends
		AbstractBaseGraphBuilder<UndirectedGraph<Vertex, DefaultEdge>, Vertex, DefaultEdge>
		implements GkaGraphBuilder<UndirectedGraph<Vertex, DefaultEdge>> {

	public UndirectedGraphBuilder() {
		graph = new Pseudograph<Vertex, DefaultEdge>(DefaultEdge.class);
	}
	@Override
	public boolean readLine(String line) {
		line = line.replace(" ", "");
		line = line.replace(";","");
		String[] vertices = line.split("--");
		if (vertices.length == 2) {
			Vertex source = makeVertexFrom(vertices[0]);
			Vertex target = makeVertexFrom(vertices[1]);
			graph.addVertex(source);
			graph.addVertex(target);
			graph.addEdge(source, target);
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
