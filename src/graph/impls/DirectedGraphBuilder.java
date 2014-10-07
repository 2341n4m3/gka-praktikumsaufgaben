package graph.impls;


import graph.Vertex;
import graph.util.GkaGraphBuilder;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class DirectedGraphBuilder
		extends
		AbstractBaseGraphBuilder<DirectedGraph<Vertex, DefaultEdge>, Vertex, DefaultEdge>
		implements GkaGraphBuilder<DirectedGraph<Vertex, DefaultEdge>> {

	public DirectedGraphBuilder() {
		graph =
				new DefaultDirectedGraph<Vertex, DefaultEdge>(
						DefaultEdge.class);
	}


		@Override
		public boolean readLine(String line) {
			line = line.replace(" ", "");
			line = line.replace(";","");
			String[] vertices = line.split("->");
			if (vertices.length == 2) {
				Vertex source = makeVertexFrom(vertices[0]);
				Vertex target = makeVertexFrom(vertices[1]);
				graph.addVertex(source);
				graph.addVertex(target);
				graph.addEdge(source, target);
				return true;
			} else if (vertices.length == 1){
				Vertex source = makeVertexFrom(vertices[0]);
				Vertex target = makeVertexFrom(vertices[0]);
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
