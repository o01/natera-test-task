import lib.ifaces.IGraph;
import lib.impl.BFSearch;
import lib.impl.BFTraverse;
import lib.impl.Graph;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class BFTraverseTest {

    private IGraph<Integer> connectedGraph;
    private IGraph<Integer> disconnectedGraph;

    @Before
    public void init() {
        // connected graph initializing
        Graph.GraphBuilder<Integer> connectedGraphBuilder = Graph.GraphBuilder.<Integer>aGraph()
                .withPathFinder(new BFSearch<>())
                .withTraverseFunction(new BFTraverse<>())
                .undirected();

        IntStream.rangeClosed(0, 11)
                .boxed()
                .forEach(connectedGraphBuilder::withVertex);

        connectedGraphBuilder.withEdge(0, 1)
                .withEdge(0, 2)
                .withEdge(0, 6)
                .withEdge(1, 3)
                .withEdge(1, 4)
                .withEdge(2, 5)
                .withEdge(4, 6)
                .withEdge(6, 7)
                .withEdge(5, 7)
                .withEdge(7, 8)
                .withEdge(8, 9);
        connectedGraph = connectedGraphBuilder.build();

        // disconnected graph initializing
        Graph.GraphBuilder<Integer> disconnectedGraphBuilder = Graph.GraphBuilder.<Integer>aGraph()
                .withPathFinder(new BFSearch<>())
                .withTraverseFunction(new BFTraverse<>())
                .undirected();

        IntStream.rangeClosed(0, 9)
                .boxed()
                .forEach(disconnectedGraphBuilder::withVertex);

        disconnectedGraphBuilder.withEdge(0, 1)
                .withEdge(0, 2)
                .withEdge(1, 3)
                .withEdge(1, 4)
                .withEdge(2, 5)
                .withEdge(5, 6)
                .withEdge(7, 8)
                .withEdge(7, 9);
        disconnectedGraph = disconnectedGraphBuilder.build();
    }

    @Test
    public void traverseAndCountAllVerticesForConnectedGraph() {
        final int[] counter = {0};
        connectedGraph.traverse(integer -> counter[0]++);
        assertEquals(connectedGraph.getVertices().size(), counter[0]);
    }

    @Test
    public void traverseAndCountAllVerticesForDisconnectedGraph() {
        final int[] counter = {0};
        disconnectedGraph.traverse(integer -> counter[0]++);
        assertEquals(disconnectedGraph.getVertices().size(), counter[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tryToTraverseWithoutConsumerForConnectedGraph() {
        connectedGraph.traverse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tryToTraverseWithoutConsumerForDisconnectedGraph() {
        disconnectedGraph.traverse(null);
    }


}