package lib.ifaces;

import java.util.List;

public interface IPathFinder<T> {

    List<T> findPath(IGraph<T> graph, T fromVertex, T toVertex);

}
