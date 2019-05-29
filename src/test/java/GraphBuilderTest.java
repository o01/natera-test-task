import lib.ifaces.IGraph;
import lib.impl.BFSearch;
import lib.impl.BFTraverse;
import lib.impl.Graph;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GraphBuilderTest {

    private Graph.GraphBuilder<Integer> builder;

    @Before
    public void init() {

        builder = Graph.GraphBuilder.aGraph();
        IntStream.rangeClosed(0, 9)
                .boxed()
                .forEach(i -> builder.withVertex(i));
        builder.withEdge(0, 1, 1)
                .withEdge(0, 2, 2)
                .withEdge(0, 6, 3)
                .withEdge(1, 3, 4)
                .withEdge(1, 4, 5)
                .withEdge(2, 5, 6)
                .withEdge(4, 6, 7)
                .withEdge(6, 7, 8)
                .withEdge(5, 7, 9)
                .withEdge(7, 8, 10)
                .withEdge(8, 9, 11);
    }

    @Test(expected = IllegalArgumentException.class)
    public void directionIsNotDefined() {
        builder.build();
    }

    @Test
    public void allTheParametersArePassed() {
        IGraph<Integer> graph = builder.undirected()
                .withPathFinder(new BFSearch<>())
                .withTraverseFunction(new BFTraverse<>())
                .build();
        assertEquals(10, graph.getVertices().size());
        assertEquals(new HashSet<>(Arrays.asList(Pair.of(1,1), Pair.of(2,2), Pair.of(6,3))), graph.getAdjacentVertices().get(0));

        List<Integer> path = graph.getPath(9, 4);
        assertNotNull(path);
        assertEquals(Arrays.asList(9, 8, 7, 6, 4), path);
    }

}