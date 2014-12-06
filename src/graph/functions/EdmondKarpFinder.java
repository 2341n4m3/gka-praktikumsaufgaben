package graph.functions;




import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class EdmondKarpFinder<V,E>
{

    protected DefaultDirectedWeightedGraph<V, DefaultWeightedEdge> graph;
    protected Map<DefaultWeightedEdge, Double> flow;
    protected V quelle;
    protected V senke;
    protected Map<V, Marker> marked;
    private Queue<E> queue;

    public EdmondKarpFinder(DefaultDirectedWeightedGraph<V, DefaultWeightedEdge> graph)
    {
        this.graph = graph;
        this.queue = new LinkedList<E>();
    }

    public Double getMaximumFlow(V q, V s)
    {
    	
    	if (!(graph.containsVertex(q) && graph.containsVertex(s))) {
			throw new IllegalArgumentException(
					"source or target ist not contained in graph");
		}

		if (q.equals(s)) {
			throw new IllegalArgumentException(
					"source and target should not be equal");
		}
    	
    	
        this.quelle = q;
        this.senke = s;

        initialize();

        while (true)
        {
            calculateNextVertexQueue();

            if (this.queue.isEmpty())
            {
                break;
            }
            inspectAndMark();
            if (isVertexMarked(this.senke))
            {
                widenFlow();
                resetForNextRun();
            }
        }

        return calculateFlow();
    }

    protected void inspectAndMark()
    {
    	DefaultWeightedEdge edge = (DefaultWeightedEdge) queue.poll();
        V current_vertex = graph.getEdgeSource(edge);
        Marker current_marker = marked.get(current_vertex);
        current_marker.setInspected();


            V target = graph.getEdgeTarget(edge);
            edge = graph.getEdge(current_vertex, target);
            if (!isVertexMarked(target) && hasSufficientCapacity(edge))
            {
                Marker marker = new Marker(current_vertex,
                        Math.min(current_marker.getIncrement(), graph.getEdgeWeight(edge) - flow.get(edge)));
                marker.setForward();
                marked.put(target, marker);
            }


//        for (DefaultWeightedEdge edge : graph.incomingEdgesOf(current_vertex))
//        {
//            V source = graph.getEdgeSource(edge);
//            if (!isVertexMarked(source) && flow.get(edge) > 0)
//            {
//                Marker marker = new Marker(current_vertex, Math.min(current_marker.getIncrement(), flow.get(edge)));
//                marker.setBackward();
//                marked.put(source, marker);
//            }
//        }
    }
    
    protected boolean hasSufficientCapacity(DefaultWeightedEdge edge)
    {
        return flow.get(edge) < graph.getEdgeWeight(edge);
    }

    protected boolean isVertexMarked(V vertex)
    {
        return marked.get(vertex) != null;
    }
    
    protected void widenFlow()
    {
        V vertex = senke;
        Marker s_marker = marked.get(senke);

        
        while (!vertex.equals(quelle))
        {
            Marker marker = marked.get(vertex);
            V predecessor = marker.getPredecessor();

            
            if (marker.isForward())
            {
                DefaultWeightedEdge edge = graph.getEdge(predecessor, vertex);
                flow.put(edge, flow.get(edge) + s_marker.getIncrement());
            } else
            {
                DefaultWeightedEdge edge = graph.getEdge(predecessor, vertex);
                flow.put(edge, flow.get(edge) - s_marker.getIncrement());
            }

            vertex = predecessor;
        }

    }
    
    protected double calculateFlow()
    {
        Set<V> q_set = new HashSet<V>(this.marked.keySet());

        double outgoing_value = 0;
        for (V vertex : q_set)
        {
            for (DefaultWeightedEdge e : graph.outgoingEdgesOf(vertex))
            {
                if (!q_set.contains(graph.getEdgeTarget(e)))
                {
                    outgoing_value += flow.get(e);
                }
            }
        }
        double incoming_value = 0;
        for (V vertex : q_set)
        {
            for (DefaultWeightedEdge e : graph.incomingEdgesOf(vertex))
            {
                if (!q_set.contains(graph.getEdgeSource(e)))
                {
                    incoming_value += flow.get(e);
                }
            }
        }

        return outgoing_value - incoming_value;
    }
    

    private void calculateNextVertexQueue()
    {
        if (this.queue.isEmpty())
        {
            DirectedGraph<V, E> converted_graph = getConvertedGraph();
    		BreadthFirstSearch<V, E> bfs = new BreadthFirstSearch<V,E>(converted_graph);
            queue.addAll(bfs.getShortestPath(this.quelle, this.senke).getEdgeList());
        }
    }

    private DirectedGraph<V,E> getConvertedGraph()
    {
        @SuppressWarnings({ "rawtypes", "unchecked" })
		DefaultDirectedWeightedGraph<V,E> converted = new DefaultDirectedWeightedGraph(DefaultWeightedEdge.class);

        for (V vertex : this.graph.vertexSet())
        {
            converted.addVertex(vertex);
        }

        for (DefaultWeightedEdge edge : this.graph.edgeSet())
        {
            if (hasSufficientCapacity(edge))
            {
                V source = this.graph.getEdgeSource(edge);
                V target = this.graph.getEdgeTarget(edge);
                converted.addEdge(source, target);
                
                converted.setEdgeWeight(converted.getEdge(source, target), this.graph.getEdgeWeight(edge) - this.flow.get(edge));

                if (this.flow.get(edge) > 0)
                {
                    DefaultWeightedEdge backward = (DefaultWeightedEdge) converted.addEdge(target, source);
                    this.graph.setEdgeWeight(backward, this.flow.get(edge));
                }
            }
        }

        return converted;
    }

    protected void initialize()
    {
        flow = new HashMap<DefaultWeightedEdge, Double>();
        for (DefaultWeightedEdge e : graph.edgeSet())
        {
            flow.put(e, 0.0);
        }

        resetForNextRun();
    }
    
    protected void resetForNextRun()
    {
        marked = new HashMap<V, Marker>();
        marked.put(quelle, new Marker());
    }
    
    public Map<DefaultWeightedEdge,Double> getRiver(){
    	return flow;
    }
    
    protected void afterResetHook()
    {
        this.queue.clear();
    }
    
    class Marker
    {

        private V predecessor;
        private double increment;
        private boolean inspected;
        private boolean forward;

        public Marker()
        {
            this(null, Double.POSITIVE_INFINITY);
        }

        public Marker(V predecessor, Double increment)
        {
            this.predecessor = predecessor;
            this.increment = increment;
            this.inspected = false;
        }
        
        public V getPredecessor()
        {
            return predecessor;
        }
        
        public double getIncrement()
        {
            return increment;
        }

        public boolean isForward()
        {
            return forward == true;
        }

        public boolean isBackward()
        {
            return !isForward();
        }

        public void setInspected()
        {
            inspected = true;
        }

        public boolean isInspected()
        {
            return inspected;
        }

        public void setForward()
        {
            forward = true;
        }

        public void setBackward()
        {
            forward = false;
        }
    }
}