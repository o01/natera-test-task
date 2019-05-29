package lib.ifaces;

import java.util.function.Consumer;

public interface ITraverse<T> {

    void traverse(IGraph<T> graph, Consumer<T> consumer);

}
