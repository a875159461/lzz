

public class VertexGraph<V>{
    Map<String, Vertex> vertexMap;
    private class Vertex{
        private V v;
        private Edge<V> next;

        public Vertex(V v, Edge<V> next){
            this.v = v;
            this.next = next;
        }
        
        
    }

    private class Edge<V>{

    }
}