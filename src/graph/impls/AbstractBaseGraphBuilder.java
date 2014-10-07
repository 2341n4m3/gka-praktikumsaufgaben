package graph.impls;


import graph.util.GkaGraphBuilder;

import org.jgrapht.Graph;
/**
 * Ein abstrakter GraphBuilder mit einem wechselnden verhalten
 * das als BuilderBehavior implementiert wird
 * reset ist abstract weil es vom Typ des tatsächlich zu befüllenden Graph abhängt
 * makeVertex ist abstrakt weil es vom Typ des zu lesenden Vertex abhängt
 * 
 * Idee ist das Builder in einem bestimmten Zustand sind in dem sie
 * Eingaben erwarten, die Sie dazu veranlassen den Zustand zu wechseln
 * und andere Eingaben zu erwarten.
 * 
 * z.B. staretet der DirectedGraphBuilder im Zustand das er erwartet
 * #gerichtet in readLine übergeben zu bekommen.
 * Trifft das ein erwartet er Zeichenketten die ungefähr so aussehen: "aasad,asrgg"
 * Erfüllt jemals ein readLine aufruf nicht die erwartungen wechselt er in einen
 * Fehlerzustand aus den an ihn mit reset() befreien müsste.
 *
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
	
	abstract protected V makeVertexFrom(String str);

}
