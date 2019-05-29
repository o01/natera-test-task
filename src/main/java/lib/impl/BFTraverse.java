package lib.impl;

import lib.ifaces.IGraph;
import lib.ifaces.ITraverse;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.Consumer;

import static java.util.Collections.emptySet;

public class BFTraverse<T> implements ITraverse<T> {

    @Override
    public void traverse(IGraph<T> graph, Consumer<T> consumer) {
        Queue<T> queue = new LinkedList<>();
        Set<T> visited = new HashSet<>();
        Map<T, Set<Pair<T, T>>> adjacentVertices = graph.getAdjacentVertices();
        Optional<T> root = findUnvisitedVertex(graph, visited);
        root.ifPresent(queue::add);
        while (!queue.isEmpty()) {
            T vertex = queue.poll();
            if (!visited.contains(vertex)) {
                visit(vertex, consumer, queue, visited);
            }
            for (Pair<T, T> v : adjacentVertices.getOrDefault(vertex, emptySet())) {
                if (!visited.contains(v.getLeft())) {
                    visit(v.getLeft(), consumer, queue, visited);
                }
            }
            if (queue.isEmpty()) {
                Optional<T> unvisitedVertex = findUnvisitedVertex(graph, visited);
                unvisitedVertex.ifPresent(queue::add);
            }
        }
    }

    private void visit(T vertex, Consumer<T> consumer, Queue<T> queue, Set<T> visited) {
        visited.add(vertex);
        queue.add(vertex);
        consumer.accept(vertex);
    }

    private Optional<T> findUnvisitedVertex(IGraph<T> graph, Set<T> visited) {
        return graph.getVertices().stream()
                .filter(vertex -> !visited.contains(vertex))
                .findFirst();
    }
}
