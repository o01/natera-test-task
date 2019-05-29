package lib.impl;

import lib.ifaces.IGraph;
import lib.ifaces.IPathFinder;
import lib.ifaces.ITraverse;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.*;
import java.util.function.Consumer;

public class Graph<T> implements IGraph<T> {

    private final Set<T> vertices = new HashSet<>();
    private final Map<T, Set<Pair<T, T>>> adjacentVertices = new HashMap<>();
    private final Boolean directed;
    private final IPathFinder<T> pathFinder;
    private final ITraverse<T> traverse;

    private Graph(IPathFinder<T> pathFinder, ITraverse<T> traverse, boolean isDirected) {

        if (pathFinder == null || traverse == null) {
            throw new IllegalArgumentException("pathFinder and traverse should not be null");
        }
        this.pathFinder = pathFinder;
        this.traverse = traverse;
        this.directed = isDirected;
    }

    public synchronized void addVertex(T vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex should not be null");
        }
        vertices.add(vertex);
    }

    public synchronized void addEdge(T sourceVertex, T targetVertex, T weight) {
        if (!vertices.contains(sourceVertex) || !vertices.contains(targetVertex)) {
            throw new IllegalArgumentException("One of vertices does not exist");
        }
        if (sourceVertex == targetVertex) {
            return;
        }
        addToAdjacentVertices(sourceVertex, targetVertex, weight);
        if (!directed) {
            addToAdjacentVertices(targetVertex, sourceVertex, weight);
        }
    }

    public synchronized List<T> getPath(T sourceVertex, T targetVertex) {
        return pathFinder.findPath(this, sourceVertex, targetVertex);
    }

    @Override
    public synchronized void traverse(Consumer<T> consumer) {
        if (consumer == null) {
            throw new IllegalArgumentException("Consumer should not be null");
        }
        traverse.traverse(this, consumer);
    }

    @Override
    public boolean isDirected() {
        return directed;
    }

    @Override
    public synchronized Map<T, Set<Pair<T, T>>> getAdjacentVertices() {
        return adjacentVertices;
    }

    @Override
    public synchronized Set<T> getVertices() {
        return vertices;
    }

    private void addToAdjacentVertices(T sourceVertex, T targetVertex, T weight) {
        adjacentVertices.computeIfAbsent(sourceVertex, k -> new HashSet<>())
                .add(new ImmutablePair<>(targetVertex, weight));
    }

    public static class GraphBuilder<T> {

        private final Set<T> vertices = new HashSet<>();
        private final Set<Triple<T, T, T>> edges = new HashSet<>();
        private Boolean isDirected;
        private IPathFinder<T> pathFinder;
        private ITraverse<T> traverse;

        public static <T> GraphBuilder<T> aGraph() {
            return new GraphBuilder<>();
        }

        public GraphBuilder<T> withVertex(T vertex) {
            vertices.add(vertex);
            return this;
        }

        public GraphBuilder<T> withEdge(T fromVertex, T toVertex) {
            edges.add(new ImmutableTriple(fromVertex, toVertex, 0));
            return this;
        }

        public GraphBuilder<T> withEdge(T fromVertex, T toVertex, T weight) {
            edges.add(new ImmutableTriple(fromVertex, toVertex, weight));
            return this;
        }

        public GraphBuilder<T> withPathFinder(IPathFinder<T> pathFinder) {
            this.pathFinder = pathFinder;
            return this;
        }

        public GraphBuilder<T> withTraverseFunction(ITraverse<T> traversal) {
            this.traverse = traversal;
            return this;
        }

        public GraphBuilder<T> directed() {
            this.isDirected = true;
            return this;
        }

        public GraphBuilder<T> undirected() {
            this.isDirected = false;
            return this;
        }

        public IGraph<T> build() {
            if (isDirected == null)
                throw new IllegalArgumentException("Edge type should be defined");
            IGraph<T> graph = new Graph<>(pathFinder, traverse, isDirected);
            vertices.forEach(graph::addVertex);
            edges.forEach(edge -> graph.addEdge(edge.getLeft(), edge.getMiddle(), edge.getRight()));
            return graph;
        }
    }

}
