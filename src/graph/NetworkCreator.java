package graph;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;




import org.jgrapht.VertexFactory;
import org.jgrapht.WeightedGraph;
import org.jgrapht.generate.RandomGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import graph.Vertex;

public class NetworkCreator {

	public static void main(String[] args) throws Exception {
		File datei = null;
		int countVertex;
		int countEdges;
		int maxEdges;
		boolean ok = true;
		WeightedGraph<Vertex, DefaultEdge> graph;

		Scanner sc = new Scanner(System.in);
		while (ok) {
			System.out.print("Wie soll die Datei heißen: ");
			String name = sc.nextLine();
			System.out.print("Wieviele Graphen sollen erzeugt werden?");
			int amount =Integer.parseInt(sc.nextLine());
			int count = 0;
			System.out.print("Wie viele Knoten soll es haben?: ");
			countVertex = sc.nextInt();
			maxEdges = (countVertex * (countVertex - 1)) / 2;
			System.out.print("Wie viele Kanten soll es haben?(maximal " + maxEdges
					+ ") : ");
			countEdges = sc.nextInt();
			
			for(count=0;count<amount;count++){
				graph=null;
			datei = new File("src/graph/misc/"
					+ name+"_"+count+ ".gka");
			if (datei.exists()) {
				System.out.println("Datei existiert schon");
			} else {
				try {
					datei.createNewFile();
					System.out.println("Datei erzeugt");
				} catch (IOException e) {
					e.printStackTrace();
				}
				ok = false;
			}
		
		System.out.println("test");
		graph=buildGraph(countVertex, countEdges);
		graph=buildNetwork(graph);
		sc.close();
		GraphSave.graphWriter(graph, GraphType.DIRECTEDWEIGHTEDGRAPH, datei);

		System.out.println("Datei Beschrieben");
		try {
			GraphViz.vizIt(datei.toString().replace(".gka", ""), GkaGraphReaders.newDirectedWeightedReader().read(datei.getAbsolutePath()));
			System.out.println("Dot erzeugt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		}
	}

	public static WeightedGraph<Vertex, DefaultEdge> buildGraph(
			final int countVertex, int countEdges) {
		WeightedGraph<Vertex, DefaultEdge> graph = new SimpleWeightedGraph<Vertex, DefaultEdge>(
				DefaultWeightedEdge.class);
		RandomGraphGenerator<Vertex, DefaultEdge> randomGenerator = new RandomGraphGenerator<Vertex, DefaultEdge>(
				countVertex, countEdges);
		VertexFactory<Vertex> vertexFactory = new VertexFactory<Vertex>() {
			int i = 0;

			public Vertex createVertex() {
				i++;
				String iName = "v" + String.valueOf(i);
				return new Vertex(iName);
			}
		};
		randomGenerator.generateGraph(graph, vertexFactory, null);
		for (DefaultEdge edge : graph.edgeSet()) {
			int value = 0;
			value = (int) (Math.random() * 100);
			;
			graph.setEdgeWeight(edge, value);
		}
		return graph;
	}
	
	public static WeightedGraph<Vertex, DefaultEdge> buildNetwork(WeightedGraph<Vertex, DefaultEdge> graph) {

		Vertex q = new Vertex("q");
		Vertex s = new Vertex("s");
	graph.addVertex(q);
	graph.addVertex(s);
	for(Vertex vertex: graph.vertexSet()){
		if(vertex.equals(q) || vertex.equals(s)) continue;
		if(Math.random() <0.2){ 
			DefaultEdge edge = graph.addEdge(q, vertex);
			graph.setEdgeWeight(edge, (int) (Math.random() * 100));
		}
		if(Math.random() <0.2){ 
			DefaultEdge edge = graph.addEdge(vertex,s);
			graph.setEdgeWeight(edge, (int) (Math.random() * 100));
			}
	}
	return graph;
}
}
