package graph.impls;


import graph.util.GkaGraphBuilder;

import org.jgrapht.Graph;
/**
 * Ein abstrakter GraphBuilder.
 * makeVertexFrom ist abstrakt, da die Implementierung vom Typen des zu lesenden Vertex abhängt z.b. attributierte Vertex
 * readLine ist abstrakt, da die Implementierung von dem zu lesenden Graphentypen abhaengt
 * @param <G>
 * @param <V>
 * @param <E>
 */
public abstract class AbstractBaseGraphBuilder<G extends Graph<V, ? extends E>, V, E>
		implements GkaGraphBuilder<G> {

	protected G graph = null;

	@Override
	public G getGraph() {
		return graph;
	}
	
	abstract public boolean readLine(String str);
	
	abstract protected V makeVertexFrom(String str);

}
