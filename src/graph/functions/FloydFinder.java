package graph.functions;

import java.util.*;

import org.jgrapht.*;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.graph.*;


public class FloydFinder<V, E>
{
    

    private Graph<V, E> graph;
    private List<V> vertices;

    private double [][] d = null;
    private int [][] b = null;
    private TraversalListener<V,E> listener;

    public FloydFinder(Graph<V, E> graph,TraversalListener<V,E> listener)
    {
        this.graph = graph;
        this.listener = listener;
        this.vertices = new ArrayList<V>(graph.vertexSet());
    }

    /**
     * @return the graph on which this algorithm operates
     */
    public Graph<V, E> getGraph()
    {
        return graph;
    }

    /**
     * Initializes and calculates the matrix
     */
    private void lazyCalculateMatrix()
    {
        int n = vertices.size();

        // initialize the backtrace matrix
        b = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(b[i], -1);
        }

        // initialize matrix, every two vertices without an edge
        d = new double[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(d[i], Double.POSITIVE_INFINITY);
        }

        // initialize matrix, showing on themselves
        for (int i = 0; i < n; i++) {
            d[i][i] = 0.0;
        }

        // initialize matrix, setting up all the distances between two vertices with an edge
        Set<E> edges = graph.edgeSet();

        for (E edge : edges) {
            listener.edgeTraversed(null);
            listener.vertexTraversed(null);
            V v1 = graph.getEdgeSource(edge);
            listener.vertexTraversed(null);
            V v2 = graph.getEdgeTarget(edge);

            int v_1 = vertices.indexOf(v1);
            int v_2 = vertices.indexOf(v2);

            d[v_1][v_2] = graph.getEdgeWeight(edge);
            if (!(graph instanceof DirectedGraph<?, ?>)) {
                d[v_2][v_1] = graph.getEdgeWeight(edge);
            }
        }

        // Floyd Warshall Algorithm
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                for (int k = 0; k < n; k++) {
                    double ij_jk = d[i][j] + d[j][k];
                    if (ij_jk < d[i][k]) {
                        d[i][k] = ij_jk;
                        b[i][k] = j;
                        if(d[i][i] < 0) throw new IllegalArgumentException("The Graph has a negative cycle so that the Algorithm can't calculate the path correctly");
                    }
                }
            }
        }
    }

  
    private void shortestPathRecur(List<E> edges, int v_a, int v_b)
    {
        int k = b[v_a][v_b];
        
        //if there is not another vertex between those two in the path add the edge 
        //to the path otherwise search the two paths with vertex k in between
        if (k == -1) {
        	listener.edgeTraversed(null);
            E edge = graph.getEdge(vertices.get(v_a), vertices.get(v_b));
            if (edge != null) {
                edges.add(edge);
            }
        } else {
            shortestPathRecur(edges, v_a, k);
            shortestPathRecur(edges, k, v_b);
        }
    }

    /**
     * Get the shortest path between two vertices. 
     * @param a From vertice
     * @param b To vertice
     *
     * @return the path, or null if none found
     */
    public GraphPath<V, E> getShortestPath(V a, V b)
    {
    	
		if (!(graph.containsVertex(a) && graph.containsVertex(b))) {
			throw new IllegalArgumentException(
					"start or end ist not contain im graph");
		}

		if (a.equals(b)) {
			throw new IllegalArgumentException(
					"start and end should not be equal");
		}
    	
    	lazyCalculateMatrix();
        int v_a = vertices.indexOf(a);
        int v_b = vertices.indexOf(b);

        //recursively build path
        List<E> edges = new ArrayList<E>();
        shortestPathRecur(edges, v_a, v_b);

        // no path, return null
        if (edges.size() < 1) {
            return null;
        }

        //add up paths weight
        double weight = 0.0;
        for (E e : edges) {
            weight += graph.getEdgeWeight(e);
        }

        GraphPathImpl<V, E> path =
            new GraphPathImpl<V, E>(graph, a, b, edges, weight);

        return path;
    }


}