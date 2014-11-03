package graph;

import graph.functions.PathFinder;
import graph.test.Counter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedPseudograph;

import static graph.GkaGraphReaders.*;
import static graph.GraphViz.vizIt;
import graph.GraphType;

public class GkaGraphAPI {
	public static void main(String[] args) throws Exception {
		while (true) {

			BufferedReader console = new BufferedReader(new InputStreamReader(
					System.in));
			System.out
					.print("Geben Sie den Graphennamen ein der eingelesen werden soll: ");
			String zeile = null;
			GraphType type = null;
			String datei = null;
			try {
				zeile = console.readLine();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(datei = "src/graph/misc/" + zeile
								+ ".gka"), "windows-1256"));
				String currentline = br.readLine();
				if (currentline.contains("--")) {
					if (currentline.contains(":")) {
						type = GraphType.UNDIRECTEDWEIGHTEDGRAPH;
						vizIt("src/graph/misc/" + zeile,
								newUndirectedWeightedReader().read(
										"src/graph/misc/" + zeile + ".gka"));
					} else {
						type = GraphType.UNDIRECTEDGRAPH;
						vizIt("src/graph/misc/" + zeile, newUndirectedReader()
								.read("src/graph/misc/" + zeile + ".gka"));
					}
				} else if (currentline.contains("->")) {
					if (currentline.contains(":")) {
						type = GraphType.DIRECTEDWEIGHTEDGRAPH;
						vizIt("src/graph/misc/" + zeile,
								newDirectedWeightedReader().read(
										"src/graph/misc/" + zeile + ".gka"));
					} else {
						type = GraphType.DIRECTEDGRAPH;
						vizIt("src/graph/misc/" + zeile, newDirectedReader()
								.read("src/graph/misc/" + zeile + ".gka"));
					}
				} else {
					br.close();
					throw new Exception("no Graphtype recognised");
				}

				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out
					.print("Möchten Sie den kürzesten Weg zw. zwei Knoten ermitteln? y/n : ");
			zeile = null;
			try {
				zeile = console.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (zeile.equals("y")) {
				System.out
						.print("Welcher Algorithmus soll verwendet werden, was ist der Start und Targetknoten? BFS s t : ");
				zeile = null;
				try {
					zeile = console.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (zeile.split(" ")[0].equals("BFS")) {
					switch (type) {
					case UNDIRECTEDWEIGHTEDGRAPH:
						Counter<Vertex, DefaultWeightedEdge> undirectedWeightedCounter = new Counter<Vertex, DefaultWeightedEdge>();

						PathFinder<WeightedPseudograph<Vertex, DefaultWeightedEdge>, Vertex, DefaultWeightedEdge> undirectedWeightedFinder = PathFinders
								.newUndirectedWeightedBreadthPathFinder(undirectedWeightedCounter);

						GraphPath<Vertex, DefaultWeightedEdge> undirectedWeightedPath = undirectedWeightedFinder
								.apply(newUndirectedWeightedReader()
										.read(datei),
										new Vertex(zeile.split(" ")[1]),
										new Vertex(zeile.split(" ")[2]));
						System.out.println(undirectedWeightedCounter);
						System.out.println(undirectedWeightedPath);
						System.out.println(undirectedWeightedPath.getWeight());
						break;
					case UNDIRECTEDGRAPH:
						Counter<Vertex, DefaultEdge> undirectedCounter = new Counter<Vertex, DefaultEdge>();

						PathFinder<UndirectedGraph<Vertex, DefaultEdge>, Vertex, DefaultEdge> undirectedFinder = PathFinders
								.newUndirectedBreadthPathFinder(undirectedCounter);

						GraphPath<Vertex, DefaultEdge> undirectedPath = undirectedFinder
								.apply(newUndirectedReader().read(datei),
										new Vertex(zeile.split(" ")[1]),
										new Vertex(zeile.split(" ")[2]));
						System.out.println(undirectedCounter);
						System.out.println(undirectedPath);
						System.out.println(undirectedPath.getWeight());
						break;
					case DIRECTEDWEIGHTEDGRAPH:
						Counter<Vertex, DefaultWeightedEdge> directedWeightedCounter = new Counter<Vertex, DefaultWeightedEdge>();

						PathFinder<DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge>, Vertex, DefaultWeightedEdge> directedWeightedFinder = PathFinders
								.newDirectedWeightedBreadthPathFinder(directedWeightedCounter);

						GraphPath<Vertex, DefaultWeightedEdge> directedWeightedPath = directedWeightedFinder
								.apply(newDirectedWeightedReader().read(datei),
										new Vertex(zeile.split(" ")[1]),
										new Vertex(zeile.split(" ")[2]));
						System.out.println(directedWeightedCounter);
						System.out.println(directedWeightedPath);
						System.out.println(directedWeightedPath.getWeight());
						break;
					case DIRECTEDGRAPH:
						Counter<Vertex, DefaultEdge> directedCounter = new Counter<Vertex, DefaultEdge>();

						PathFinder<DirectedGraph<Vertex, DefaultEdge>, Vertex, DefaultEdge> directedFinder = PathFinders
								.newDirectedBreadthPathFinder(directedCounter);

						GraphPath<Vertex, DefaultEdge> directedPath = directedFinder
								.apply(newDirectedReader().read(datei),
										new Vertex(zeile.split(" ")[1]),
										new Vertex(zeile.split(" ")[2]));
						System.out.println(directedCounter);
						System.out.println(directedPath);
						System.out.println(directedPath.getWeight());
						break;
					default:
						throw new Exception("no GraphType recognised");
					}
				}
			}
		}
	}
}
