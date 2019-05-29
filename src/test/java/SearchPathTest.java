import lib.ifaces.IGraph;
import lib.impl.BFSearch;
import lib.impl.BFTraverse;
import lib.impl.Graph;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SearchPathTest {

    private IGraph<Integer> directedGraph;
    private IGraph<Integer> undirectedGraph;

    @Before
    public void init() {

        // directed graph initializing
        Graph.GraphBuilder<Integer> directedGraphBuilder = Graph.GraphBuilder.<Integer>aGraph()
                .withPathFinder(new BFSearch<>())
                .withTraverseFunction(new BFTraverse<>())
                .directed();

        IntStream.rangeClosed(0, 12)
                .boxed()
                .forEach(directedGraphBuilder::withVertex);

        directedGraph = directedGraphBuilder
                .withEdge(0, 1)
                .withEdge(1, 2)
                .withEdge(2, 3)
                .withEdge(3, 4)
                .withEdge(4, 5)
                .withEdge(5, 6)
                .withEdge(6, 0)
                .withEdge(4, 7)
                .withEdge(7, 8)
                .withEdge(8, 9)
                .withEdge(9, 0)
                .withEdge(10, 11)
                .withEdge(11, 12)
                .build();

        // undirected graph initializing
        Graph.GraphBuilder<Integer> undirectedGraphBuilder = Graph.GraphBuilder.<Integer>aGraph()
                .withPathFinder(new BFSearch<>())
                .withTraverseFunction(new BFTraverse<>())
                .undirected();

        IntStream.rangeClosed(0, 12)
                .boxed()
                .forEach(undirectedGraphBuilder::withVertex);

        undirectedGraph = undirectedGraphBuilder
                .withEdge(0, 1)
                .withEdge(1, 2)
                .withEdge(2, 3)
                .withEdge(3, 4)
                .withEdge(4, 5)
                .withEdge(5, 6)
                .withEdge(6, 0)
                .withEdge(4, 7)
                .withEdge(7, 8)
                .withEdge(8, 9)
                .withEdge(9, 0)
                .withEdge(10, 11)
                .withEdge(11, 12)
                .build();
    }

    @Test
    public void findPathForUndirectedGraph() {
        List<Integer> path = undirectedGraph.getPath(2, 0);
        assertNotNull(path);
        assertEquals(Arrays.asList(2, 1, 0), path);
    }

    @Test(expected = IllegalStateException.class)
    public void pathDoesNotExistForUndirectedGraph() {
        undirectedGraph.getPath(2, 12);
    }

    @Test
    public void findPathForDirectedGraph() {
        List<Integer> path = directedGraph.getPath(2, 0);
        assertNotNull(path);
        assertEquals(Arrays.asList(2, 3, 4, 5, 6, 0), path);
    }

    @Test(expected = IllegalStateException.class)
    public void pathDoesNotExistForDirectedGraph() {
        directedGraph.getPath(2, 12);
    }
}