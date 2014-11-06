package graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;


import graph.Vertex;

public class GraphSave {
	
	public static void GraphWriter(Graph<Vertex,DefaultEdge> graph, GraphType type, File datei){
		FileWriter writer;
		try {
			writer = new FileWriter(datei ,true);
			
			for (DefaultEdge edge : graph.edgeSet()) {
				String sourceName = graph.getEdgeSource(edge).getName();
				String targetName = graph.getEdgeTarget(edge).getName();
				if(type.equals(GraphType.DIRECTEDWEIGHTEDGRAPH)){
				    double weight = graph.getEdgeWeight(edge);
				    writer.write(sourceName + " -> " + targetName + " : " + weight +";\n");
				} else if(type.equals(GraphType.DIRECTEDGRAPH)) { 
					writer.write(sourceName + " -> " + targetName +";\n");
				    }
				    
				    else if(type.equals(GraphType.UNDIRECTEDGRAPH)) {
				    	writer.write(sourceName + " -- " + targetName + ";\n");
				    }
				 else if (type.equals(GraphType.UNDIRECTEDWEIGHTEDGRAPH)) {
					 double weight = graph.getEdgeWeight(edge);
					 writer.write(sourceName + " -- " + targetName + " : " + weight +";\n");
				}
			}
		       writer.flush();
		       writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}

}
