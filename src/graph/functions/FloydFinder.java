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
    private int [][] backtrace = null;
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
     * Calculates the matrix of all shortest paths, but does not populate the
     * paths map.
     */
    private void lazyCalculateMatrix()
    {
        if (d != null) {
            // already done
            return;
        }

        int n = vertices.size();

        // init the backtrace matrix
        backtrace = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(backtrace[i], -1);
        }

        // initialize matrix, 0
        d = new double[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(d[i], Double.POSITIVE_INFINITY);
        }

        // initialize matrix, 1
        for (int i = 0; i < n; i++) {
            d[i][i] = 0.0;
        }

        // initialize matrix, 2
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

        // run fw alg
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                for (int k = 0; k < n; k++) {
                    double ij_jk = d[i][j] + d[j][k];
                    if (ij_jk < d[i][k]) {
                        d[i][k] = ij_jk;
                        backtrace[i][k] = j;
                    }
                }
            }
        }
    }

  
    private void shortestPathRecur(List<E> edges, int v_a, int v_b)
    {
        int k = backtrace[v_a][v_b];
        if (k == -1) {
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
     * Get the shortest path between two vertices. Note: The paths are
     * calculated using a recursive algorithm. It *will* give problems on paths
     * longer than the stack allows.
     *
     * @param a From vertice
     * @param b To vertice
     *
     * @return the path, or null if none found
     */
    public GraphPath<V, E> getShortestPath(V a, V b)
    {
    	lazyCalculateMatrix();
        int v_a = vertices.indexOf(a);
        int v_b = vertices.indexOf(b);

        List<E> edges = new ArrayList<E>();
        shortestPathRecur(edges, v_a, v_b);

        // no path, return null
        if (edges.size() < 1) {
            return null;
        }

        double weight = 0.;
        for (E e : edges) {
            weight += graph.getEdgeWeight(e);
        }

        GraphPathImpl<V, E> path =
            new GraphPathImpl<V, E>(graph, a, b, edges, weight);

        return path;
    }


}