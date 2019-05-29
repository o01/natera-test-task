package lib.ifaces;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public interface IGraph<T> {

    void addVertex(T vertex);

    void addEdge(T sourceVertex, T targetVertex, T weight);

    List<T> getPath(T sourceVertex, T targetVertex);

    boolean isDirected();

    void traverse(Consumer<T> consumer);

    Map<T, Set<Pair<T, T>>> getAdjacentVertices();

    Set<T> getVertices();
}
