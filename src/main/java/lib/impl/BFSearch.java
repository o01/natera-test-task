package lib.impl;

import lib.ifaces.IGraph;
import lib.ifaces.IPathFinder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static java.util.Collections.emptySet;

public class BFSearch<T> implements IPathFinder<T> {

    @Override
    public List<T> findPath(IGraph<T> graph, T sourceVertex, T targetVertex) {
        Map<T, T> reversedPath = findAllPaths(graph.getAdjacentVertices(), sourceVertex);
        if (!reversedPath.containsKey(targetVertex)) {
            throw new IllegalStateException("No path found between " + sourceVertex + " and " + targetVertex);
        }
        return backtracePath(sourceVertex, targetVertex, reversedPath);
    }

    private List<T> backtracePath(T sourceVertex, T targetVertex, Map<T, T> reversedPath) {
        List<T> path = new LinkedList<>();
        T vertex = targetVertex;
        while (vertex != null) {
            path.add(0, vertex);
            if (vertex.equals(sourceVertex)) {
                break;
            }
            vertex = reversedPath.get(vertex);
        }
        return path;
    }

    private Map<T, T> findAllPaths(Map<T, Set<Pair<T, T>>> adjacentVertices, T sourceVertex) {
        Queue<T> queue = new LinkedList<>();
        Set<T> visited = new HashSet<>();
        Map<T, T> allPaths = new HashMap<>();
        queue.add(sourceVertex);

        while (queue.size() != 0) {
            T vertex = queue.poll();
            adjacentVertices.getOrDefault(vertex, emptySet())
                    .stream()
                    .filter(v -> !visited.contains(v.getLeft()))
                    .forEach(v -> {
                        allPaths.put(v.getLeft(), vertex);
                        visited.add(v.getLeft());
                        queue.add(v.getLeft());
                    });
        }
        return allPaths;
    }
}
