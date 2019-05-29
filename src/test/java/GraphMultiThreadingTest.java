import lib.ifaces.IGraph;
import lib.impl.BFSearch;
import lib.impl.BFTraverse;
import lib.impl.Graph;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CyclicBarrier;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class GraphMultiThreadingTest {

    private IGraph<Integer> simpleGraph;
    private CyclicBarrier cyclicBarrier;
    private static final int PART_SIZE = 5;

    @Before
    public void init() {

        Graph.GraphBuilder<Integer> builder = Graph.GraphBuilder.<Integer>aGraph()
                .withPathFinder(new BFSearch<>())
                .withTraverseFunction(new BFTraverse<>())
                .undirected();
        for (int i = 0; i < 12; i++) {
            builder.withVertex(i);
        }
        builder.withEdge(0, 1)
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
        simpleGraph = builder.build();
        cyclicBarrier = new CyclicBarrier(PART_SIZE);
    }


    @Test
    public void checkMultiThreadedTraverse() throws InterruptedException {

        final int[] counter = {0};

        final int traverseCount = PART_SIZE * 2;

        Consumer consumer = (Consumer<Integer>) i -> counter[0]++;

        IntStream.range(0, traverseCount)
                .forEach(i -> new Thread(new Traverse(consumer)).start());

        // waiting for all threads to finish
        Thread.sleep(3000);

        assertEquals(simpleGraph.getVertices().size() * traverseCount, counter[0]);

    }

    class Traverse implements Runnable {
        private Consumer consumer;

        Traverse(Consumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void run() {
            try {
                cyclicBarrier.await();
                //noinspection unchecked
                simpleGraph.traverse(consumer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}