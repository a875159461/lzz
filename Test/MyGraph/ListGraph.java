import java.util.*;
interface Graph<V>{
    public static final int ITERATOR_ISBFS = 0;

    public int add(V v);

    public void add(Edge<V> e);

    public V remove(V v);

    public Edge<V> remove(Edge<V> e);

    public V get(int index);

    public Edge<V> get(int src, int dest);

    public Iterator<V> iterator(int type, V root);

    public void convertDAG();
}

class Edge<V>{
    private V src;
    private V dest;
    private double weight;

    public Edge(V src, V dest){
        this(src, dest, 0);
    }

    public Edge(V src, V dest, double weight){
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    public V getSource(){
        return src;
    }

    public V getDest(){
        return dest;
    }

    public double getWeight(){
        return weight;
    }

    public String toString(){
        String ret = String.format("src : %s, dest : %s, weight : %s", src, dest, weight);
        return ret;
    }
}


public class ListGraph<V> implements Graph<V>{
    public ListGraph(){
        adList = new LinkedList<>();
    }

    @Override
    public int add(V v){
        int index = -1;
        if(v != null && getVertexList(v) == null){
            AdList tmp = new AdList(v);
            adList.add(tmp);
            index = adList.indexOf(tmp);
        }
        else{
            System.out.printf("Vertex %s already exists",v);
        }
        return index;
    }

    @Override 
    public void add(Edge<V> e){
        AdList tg = getVertexList(e.getSource());
        if(tg != null)
            tg.addEdge(e);
        else
            System.out.printf("Error, can't find Vertex %s",e.getSource());
    }

    @Override
    public V remove(V v){
        V ret = null;

        AdList tmp = removeVertexList(v);
        if(tmp != null){
            ret = tmp.v;
        }

        removeRelatedEdge(v);
        return ret;
    }

    @Override
    public Edge<V> remove(Edge<V> e){
        Edge<V> ret = null;
        if(e != null){
            AdList vlist = getVertexList(e.getSource());
            if(vlist != null)
                ret = vlist.removeEdge(e.getDest());
        }
        return ret;
    }

    @Override
    public V get(int index){
        V ret = null;
        if(index >= 0 && index < adList.size()) {
            AdList vlist = adList.get(index);
            if(vlist != null) {
                ret = vlist.v;
                System.out.printf("get , index : %s , v : %s", index, ret);
            }
        }
        return ret;
    }

    @Override
    public Edge<V> get(int srcIndex, int destIndex){
        Edge<V> ret = null;
        V s = get(srcIndex);
        V d = get(destIndex);
        if(s != null && d != null) {
            AdList vlist = getVertexList(s);
            if(vlist != null) {
                ret = vlist.getEdge(d);
            }
        }
        return ret;
    }

    @Override
    public Iterator<V> iterator(int type, V root){
        if(ITERATOR_ISBFS == 1)
            return new BFSIterator(root);
        return new BFSIterator(root); //
    }

    @Override
    public void convertDAG(){

    }


    private AdList getVertexList(V v){
        AdList ret = null;
        if(v != null){
            for(AdList vlist: adList){
                if(v.equals(vlist.v)){
                    ret = vlist;
                    break;
                }
            }
        }
        return ret;
    }

    private AdList removeVertexList(V v){
        AdList ret = null;
        if(v != null){
            for(AdList vlist: adList){
                if(v.equals(vlist.v)){
                    System.out.printf("remove Vertex and its'List %s", v);
                    ret = vlist;
                    adList.remove(vlist);
                    break;
                }
            }
        }
        return ret;
    }

    private void removeRelatedEdge(V v){
        if(v != null){
            for(AdList vlist:adList)
                vlist.removeEdge(v);
        }
    }

    private class BFSIterator implements Iterator<V>{
        private List<V> visitedVertex = null;
        private Queue<V> queue = null;

        private BFSIterator(V root){
            visitedVertex = new LinkedList<>();
            queue = new LinkedList<>();

            queue.offer(root);
        }

        public boolean hasNext(){
            if(queue.size() > 0)
                return true;
            return false;
        }

        public V next(){
            V v = queue.poll();
            AdList vlist = getVertexList(v);
            if(vlist != null){
                List<Edge<V>> list = vlist.edgeList;
                for(Edge<V> edge: list){
                    V dest = edge.getDest();
                    if(!inList(dest, visitedVertex.iterator()) && !inList(dest, queue.iterator()))
                        queue.offer(dest);
                }
            }
            visitedVertex.add(v);
            return v;
        }

        public void remove(){

        }
    }

    private boolean inList(V v, Iterator<V> it){
        while(it.hasNext()){
            V tmp = it.next();
            if(v.equals(tmp))
                return true;
        }
        return false;
    }

    private LinkedList<AdList> adList;

    private class AdList{
        private V v;
        private List<Edge<V>> edgeList;

        public AdList(V v){
            this.v = v;
            this.edgeList = new LinkedList<Edge<V>>();
            System.out.printf("AdList");
        }

        public String toString(){
            String ret = String.format("v : %s, list len : %s", v, edgeList.size()); //
            return ret;
        }

        public void addEdge(Edge<V> e){
            System.out.printf("add edge : %s", e);
            if(getEdge(e.getDest()) == null)
                edgeList.add(e);
            else
                System.out.printf("edge already exists : %s",e);
        }

        public Edge<V> getEdge(V dest){
            Edge<V> ret = null;
            for(Edge<V> e: edgeList){
                if(dest.equals(e.getDest())) //
                    ret = e;
                    break;
            }
            return ret;
        }

        public Edge<V> removeEdge(V dest){
            Edge<V> ret = null;
            for(Edge<V> edge : edgeList){
                if(dest.equals(edge.getDest())){ //
                    ret = edge;
                    edgeList.remove(edge);
                    break;
                }
            }
            return ret;
        }
    }

    public static void main(String[] args){
        Graph<String> mDG = new ListGraph<String>();
        System.out.println("===============add v=================");
        
        mDG.add("1"); mDG.add("2"); mDG.add("3"); mDG.add("4");
        mDG.add("5"); mDG.add("6"); mDG.add("7"); mDG.add("8");

        System.out.println("===============add edge=================");
        
        mDG.add(new Edge<String>("1", "2")); mDG.add(new Edge<String>("1", "3"));
        mDG.add(new Edge<String>("2", "4")); mDG.add(new Edge<String>("2", "5"));
        mDG.add(new Edge<String>("3", "6")); mDG.add(new Edge<String>("3", "7"));
        mDG.add(new Edge<String>("4", "8")); mDG.add(new Edge<String>("8", "5"));
        mDG.add(new Edge<String>("6", "7"));
        
        System.out.println("===============test travelling=================");
        
        Iterator<String> it = mDG.iterator(Graph.ITERATOR_ISBFS, "1");
        while(it.hasNext()) {
            String s = it.next();
            System.out.printf("next : %s", s);
        }
        
        System.out.println("===============test travelling2=================");
        
        it = mDG.iterator(Graph.ITERATOR_ISBFS, "2");
        while(it.hasNext()) {
            String s = it.next();
            System.out.printf("next : %s", s);
        }
        
        System.out.println("===============test others=================");
        
        mDG.get(0);
        
        System.out.println(mDG.get(0, 1));
        
        mDG.remove("6");
        
        mDG.remove(new Edge<String>("3", "7"));
        it = mDG.iterator(Graph.ITERATOR_ISBFS, "1");
        while(it.hasNext()) {
            String s = it.next();
            System.out.printf("next : %s", s);
        }
    }
}
