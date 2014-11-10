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

public class GraphCreator {

	public static void main(String[] args) throws Exception {
		File datei = null;
		int countVertex;
		int countEdges;
		int maxEdges;
		boolean ok = true;
		WeightedGraph<Vertex, DefaultEdge> graph;

		Scanner sc = new Scanner(System.in);
		while (ok) {
			System.out.print("Wie soll Datei Name heißen: ");
			datei = new File("src/graph/misc/"
					+ sc.nextLine() + ".gka");
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
		}
		System.out.print("Wie viele Knoten soll es haben?: ");
		countVertex = sc.nextInt();
		maxEdges = (countVertex * (countVertex - 1)) / 2;
		System.out.print("Wie viele Kanten soll es haben?(maximal " + maxEdges
				+ ") : ");
		countEdges = sc.nextInt();
		
		System.out.println("Welcher Graphentyp? (1=DWG,2=UWG,3=DG,4=UG)");
		int typestr = sc.nextInt();
		graph=buildGraph(countVertex, countEdges);
		sc.close();
		switch (typestr) {
		case 1: GraphSave.GraphWriter(graph, GraphType.DIRECTEDWEIGHTEDGRAPH, datei);
			break;
		case 2: GraphSave.GraphWriter(graph, GraphType.UNDIRECTEDWEIGHTEDGRAPH, datei);
			break;
		case 3: GraphSave.GraphWriter(graph, GraphType.DIRECTEDGRAPH, datei);
			break;
		case 4: GraphSave.GraphWriter(graph, GraphType.UNDIRECTEDGRAPH, datei);
		default: throw new Exception("unsupported GraphType");
		}
		System.out.println("Datei Beschrieben");
		try {
			GraphViz.vizIt(datei.toString().replace(".gka", ""), graph);
			System.out.println("Dot erzeugt");
		} catch (IOException e) {
			e.printStackTrace();
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
			value = (int) (Math.random() * (25 - 5) + 5);
			;
			graph.setEdgeWeight(edge, value);
		}
		return graph;
	}
}
