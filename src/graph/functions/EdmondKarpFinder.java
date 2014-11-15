package graph.functions;


import graph.functions.DijkstraFinder;
import graph.test.Counter;

import java.util.LinkedList;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class EdmondKarpFinder<V,E> extends FordFulkersonFinder<V,E>
{

    private Queue<V> queue;

    public EdmondKarpFinder(DefaultDirectedWeightedGraph<V, DefaultWeightedEdge> g)
    {
        super(g);
        this.queue = new LinkedList();
    }

    @Override
    public Double getMaximumFlow(V q, V s)
    {
        this.q = q;
        this.s = s;

        initialize();

        while (true)
        {
            calculateNextVertexQueue();

            if (this.queue.isEmpty())
            {
                break;
            }

            inspectAndMark();

            if (isVertexMarked(this.s))
            {
                widenFlow();
                resetForNextRun();
            }
        }

        return calculateFlow();
    }

    @Override
    protected V getNextUninspectedVertex()
    {
        return this.queue.poll();
    }

    private void calculateNextVertexQueue()
    {
        if (this.queue.isEmpty())
        {
            Graph converted_graph = getConvertedGraph();
            Counter count = new Counter();
            DijkstraFinder d = new DijkstraFinder(converted_graph, count);
            System.out.println(d.getShortestPath(this.q, this.s));
            this.queue.addAll(d.getShortestPath(this.q, this.s).getEdgeList());
        }
    }

    private Graph getConvertedGraph()
    {
        DefaultDirectedWeightedGraph converted = new DefaultDirectedWeightedGraph(DefaultWeightedEdge.class);

        for (V vertex : this.g.vertexSet())
        {
            converted.addVertex(vertex);
        }

        for (DefaultWeightedEdge edge : this.g.edgeSet())
        {
            if (hasSufficientCapacity(edge))
            {
                V source = this.g.getEdgeSource(edge);
                V target = this.g.getEdgeTarget(edge);

                DefaultWeightedEdge forward = (DefaultWeightedEdge) converted.addEdge(source, target);
                this.g.setEdgeWeight(forward, this.g.getEdgeWeight(edge) - this.f.get(edge));

                if (this.f.get(edge) > 0)
                {
                    DefaultWeightedEdge backward = (DefaultWeightedEdge) converted.addEdge(target, source);
                    this.g.setEdgeWeight(backward, this.f.get(edge));
                }
            }
        }

        return converted;
    }

    protected void afterResetHook()
    {
        this.queue.clear();
    }
}