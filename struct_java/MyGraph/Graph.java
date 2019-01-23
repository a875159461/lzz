public class Graph<V>{
    private Map<String, Vertex> vertexMap;

    private class Vertex{
        private V v;
        private List<Edge> edgeList;

        private int indegree;

        private int dist;
        private Vertex preNode;
    }

    private class Edge{
        private V start;
        private V end;
        private int weight;
    }

}