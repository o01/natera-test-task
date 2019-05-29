Simple graph lib
=============================

Supports 2 types of graphs - directed and undirected with 3 operations:

**addVertex** - adds vertex to the graph
```java
   void addVertex(T vertex);
   ```
**addEdge** - adds edge to the graph
```java
   void addEdge(T sourceVertex, T targetVertex, T weight);
   ```
**getPath** - returns a list of edges between 2 vertices (path doesn’t have to be optimal)
```java
   List<T> getPath(T sourceVertex, T targetVertex);
   ```
   
Library provides: 

**Weighted edges support**

**Traverse function** that will take a user defined function and apply it on every vertex of the graph
```java
   void traverse(Consumer<T> consumer);
   ```
**Thread safety for graph operations**

Quick start
=============================

Simple graph lib comes with a gradle based structure, so you need to use **Gradle wrapper** or **gradle-4.10.3**.

### Example of usage
```java

private Graph.GraphBuilder<Integer> builder = Graph.GraphBuilder.aGraph();

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
                
IGraph<Integer> graph = builder.undirected()
                .withPathFinder(new BFSearch<>())
                .withTraverseFunction(new BFTraverse<>())
                .build();
                
List<Integer> path = graph.getPath(9, 4);

final int[] counter = {0};
graph.traverse(integer -> counter[0]++);

```
