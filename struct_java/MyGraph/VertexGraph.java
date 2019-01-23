import java.util.*;
import myheap.BinaryHeap;
import mydisj.*;

public class VertexGraph<V>{
    private Map<String, VNode> vertexMap;
    private int num_vnodes;
    public static final int ITERATOR_ISBFS = 0;
    public static final int ITERATOR_ISDFS = 1;

    public VertexGraph(){
        vertexMap = new HashMap<>();
        num_vnodes = 0;
    }

    public void addVNode(String name, V data){
        if(name != null && vertexMap.get(name) == null){
            VNode vnode = new VNode(data, null);
            System.out.println(vnode);
            vertexMap.put(name, vnode);
            num_vnodes++;
        }
        else{
            System.out.printf("Vertex %s already exists", name);
        }
    }

    public void addArcNode(String name, V end, int weight){  //add in the head 
        VNode vnode = vertexMap.get(name);  //get VNode
        if(vnode == null){
            System.out.printf("Error, can't find Vertex %s",name);
        }
        else{
            ArcNode first = vnode.first;
            ArcNode tmp = first;
            while(tmp != null){
                if(tmp.adjvex == end){
                    System.out.printf("edge already exists : %s", tmp);
                    return;
                }
                tmp = tmp.next;
            }
            ArcNode arcnode = new ArcNode(end, weight, first);
            System.out.println("add edge, src: " + vnode.data + ", " + arcnode);
            vnode.first = arcnode;
            VNode dest = getVNode(end); //indegree++
            dest.indegree++;
        }
    }

    public V rmVNode(String name){
        V v = vertexMap.get(name).data;
        vertexMap.remove(name);
        
        Collection<VNode> set = vertexMap.values(); //removeRelatedEdge
        for(VNode tmp: set)
            if(tmp.first != null)
                rmArcNode(tmp, v);
        return v;
    }

    public ArcNode rmArcNode(String name, V end){
        VNode vnode = vertexMap.get(name);
        return rmArcNode(vnode, end);
    }

    public void topsort() throws Exception{
        int[] store = new int[vertexMap.size()];
        int i = 0;
        for(VNode tmp: vertexMap.values()){
            store[i++] = tmp.indegree;
        }

        Queue<VNode> queue = new LinkedList<>();
        int count = 0; //vertexMap.values().size()

        for(VNode tmp: vertexMap.values()){
            if(tmp.indegree == 0){
                queue.offer(tmp);
            }
        }

        while(!queue.isEmpty()){
            VNode vnode = queue.poll();
            System.out.println(vnode);
            ArcNode arcnode = vnode.first;
            while(arcnode != null){
                VNode tmp = getVNode(arcnode.adjvex);
                if(--tmp.indegree == 0)
                    queue.offer(tmp);
                arcnode = arcnode.next;
            }
        }

        i = 0;
        for(VNode tmp: vertexMap.values()){
            tmp.indegree = store[i++];
        }

        if(count != num_vnodes)
            throw new Exception("Graph has circle");
    }

    public void unweighted(String name){
        VNode s = vertexMap.get(name);
        Queue<VNode> queue = new LinkedList<>();

        for(VNode v: vertexMap.values())
            v.dist = Integer.MAX_VALUE;

        s.dist = 0;
        queue.offer(s);

        while(!queue.isEmpty()){
            VNode v = queue.poll();
            for(ArcNode adj = v.first; adj != null; adj = adj.next){
                VNode w = getVNode(adj.adjvex);
                if(w.dist == Integer.MAX_VALUE){
                    w.dist = v.dist + 1;
                    w.preNode = v;
                    queue.offer(w);
                }
            }
        }
    }

    public void dijkstra(String name){
        VNode s = vertexMap.get(name);
        BinaryHeap<VNode> heap = new BinaryHeap<>();
        for(VNode v: vertexMap.values()){
            v.dist = Integer.MAX_VALUE;
            heap.insert(v);
        }
        s.dist = 0;

        while(!heap.isEmpty()){
            VNode v = heap.deleteMin();
            for(ArcNode adj = v.first; adj != null; adj = adj.next){
                VNode w = getVNode(adj.adjvex);
                if(w.dist > v.dist + adj.weight && v.dist != Integer.MAX_VALUE){ //
                    w.dist = v.dist + adj.weight;
                    w.preNode = v;
                }
            }  //update dist, and rebuild the heap
            heap.buildHeap();
        }
    }

    public void prim(String name){
        VNode s = vertexMap.get(name);
        BinaryHeap<VNode> heap = new BinaryHeap<>();
        for(VNode v: vertexMap.values()){
            v.dist = Integer.MAX_VALUE;
            heap.insert(v);
        }
        s.dist = 0;

        while(!heap.isEmpty()){
            VNode v = heap.deleteMin();
            for(ArcNode adj = v.first; adj != null; adj = adj.next){
                VNode w = getVNode(adj.adjvex);
                if(w.dist > adj.weight){ //
                    w.dist = adj.weight;
                    w.preNode = v;
                }
            }  //update dist, and rebuild the heap
            heap.buildHeap();
        }
    }



    // public void countIndegree(){
    //     for(VNode vnode:vertexMap.values()){
    //         vnode.indegree = 0;
    //     }
    //     for(VNode vnode:vertexMap.values()){
    //         ArcNode arcnode = vnode.first;
    //         while(arcnode != null){
    //             VNode tmp = getVNode(arcnode.adjvex);
    //             tmp.indegree++;
    //             arcnode = arcnode.next;
    //         }
    //     }
    // }

    public void neighbors(String name){
        VNode vnode = vertexMap.get(name);
        ArcNode tmp = vnode.first;
        String s = "";
        while(tmp != null){
            s += tmp + "; ";
            tmp = tmp.next;
        }
        System.out.println("Vertex \"" + name + "\" neighbors: " + s);
    }

    public void printVNode(){
        for(VNode tmp:vertexMap.values()){
            System.out.println(tmp);
        }
    }

    public Iterator<V> iterator(int type, String rootName){
        if(type == ITERATOR_ISBFS)
            return new BFSIterator(rootName);
        return new DFSIterator(rootName); 
    }

    
    private VNode getVNode(V v){
        Collection<VNode> set = vertexMap.values();
        for(VNode tmp: set){
            if(tmp.data == v)
                return tmp;
        }
        return null;
    }

    private ArcNode rmArcNode(VNode vnode, V end){
        ArcNode pre = vnode.first;
        ArcNode p = pre.next;
        if(vnode.first.adjvex == end){
            vnode.first = vnode.first.next;
            System.out.println("remove edge, src: " + vnode.data + ", dest: " + end);
        }
        else{
            while(p != null){
                if(end.equals(p.adjvex)){
                    pre.next = p.next;
                    System.out.println("remove edge, src: " + vnode.data + ", dest: " + end);
                    break;
                }
                pre = p;
                p = p.next;
            }
        }
        
        return p;
    }
    
    private boolean inList(V v, Iterator<V> it){
        while(it.hasNext()){
            V tmp = it.next();
            if(v.equals(tmp))
                return true;
        }
        return false;
    }

    private class DFSIterator implements Iterator<V>{
        private List<V> visitedVertex = null;
        private LinkedList<V> stack = null; // push:addFirst pop:removeFirst

        private DFSIterator(String rootName){
            visitedVertex = new LinkedList<>();
            stack = new LinkedList<>();
            V root = vertexMap.get(rootName).data;
            stack.addFirst(root);
        }

        public boolean hasNext(){
            if(stack.size() > 0)
                return true;
            return false;
        }

        public V next(){
            V v = stack.removeFirst();
            VNode vnode = getVNode(v);
            if(vnode != null){
                for(ArcNode tmp = vnode.first; tmp != null; tmp = tmp.next){
                    V dest = tmp.adjvex;
                    if(!inList(dest, visitedVertex.iterator()) && !inList(dest, stack.iterator()))
                        stack.addFirst(dest);
                }
            }
            visitedVertex.add(v);
            return v;
        }

        public void remove(){

        }
    }

    private class BFSIterator implements Iterator<V>{
        private List<V> visitedVertex = null;
        private Queue<V> queue = null;

        private BFSIterator(String rootName){
            visitedVertex = new LinkedList<>();
            queue = new LinkedList<>();

            V root = vertexMap.get(rootName).data;
            queue.offer(root);
        }

        public boolean hasNext(){
            if(queue.size() > 0)
                return true;
            return false;
        }

        public V next(){
            V v = queue.poll();
            VNode vnode = getVNode(v);
            if(vnode != null){
                for(ArcNode tmp = vnode.first; tmp != null; tmp = tmp.next){
                    V dest = tmp.adjvex;
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

    private class VNode implements Comparable<VNode>{
        private V data; 
        private ArcNode first; 

        private int indegree; 

        private int dist;
        private VNode preNode;

        public VNode(V data, ArcNode first){
            this.data = data;
            this.first = first;
            this.indegree = 0;
        }

        public String toString(){
            V s = null;
            if(preNode != null){
                s = preNode.data;
            }
            return String.format("vertex %s : indegree %d, dist %d, preNode %s", data, indegree, dist, s);
        }

        @Override
        public int compareTo(VNode v){
            if(this.dist > v.dist)
                return 1;
            else if(this.dist < v.dist)
                return -1;
            return 0;
        }
    }

    private class ArcNode{
        private V adjvex;    
        // private int edgeInfo;    
        private int weight;        
        private ArcNode next;          

        public ArcNode(V adjvex, int weight, ArcNode next){
            this.adjvex = adjvex;
            this.weight = weight;
            this.next = next;
        }

        public String toString(){
            return String.format("dest: %s, weight: %d", adjvex, weight);
        } 
    }

    public static void main(String[] args){
        VertexGraph<String> mDG = new VertexGraph<String>();
        System.out.println("===============add v=================");
        
        mDG.addVNode("one", "1"); mDG.addVNode("two", "2"); mDG.addVNode("three", "3"); mDG.addVNode("four", "4");
        mDG.addVNode("five", "5"); mDG.addVNode("six", "6"); mDG.addVNode("seven", "7"); mDG.addVNode("eight", "8");

        System.out.println("===============add edge=================");
        
        mDG.addArcNode("one", "2", 1); mDG.addArcNode("one", "3", 1);
        mDG.addArcNode("two", "4", 1); mDG.addArcNode("two", "5", 1);
        mDG.addArcNode("three", "7", 2); mDG.addArcNode("three", "6", 1);
        mDG.addArcNode("four", "8", 1); mDG.addArcNode("eight", "5", 1);
        mDG.addArcNode("six", "7", 1);
        
        System.out.println("===============test travelling=================");
        
        Iterator<String> it = mDG.iterator(VertexGraph.ITERATOR_ISBFS, "one");
        while(it.hasNext()) {
            String s = it.next();
            System.out.printf("next: %s, ", s);
        }
        
        System.out.println("\n===============test travelling2=================");
        
        it = mDG.iterator(VertexGraph.ITERATOR_ISBFS, "two");
        while(it.hasNext()) {
            String s = it.next();
            System.out.printf("next: %s, ", s);
        }
        
        System.out.println("\n===============test others=================");        
        // mDG.rmVNode("six");
        // mDG.rmArcNode("three", "7");
        
        // try{mDG.topsort();}
        // catch(Exception e){}
        // mDG.dijkstra("one");
        mDG.prim("one");
        mDG.printVNode();
    }
}