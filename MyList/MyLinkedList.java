import java.util.*;
public class MyLinkedList<E> implements Iterable<E>{
    private int theSize;
    private int modCount = 0;
    private Node<E> firstNode;
    private Node<E> lastNode;

    public MyLinkedList(){
        doClear();
    }
    
    private void clear(){
        doClear();
    }
    
    public void doClear(){
        firstNode = new Node<>( null, null, null );
        lastNode = new Node<>( null, firstNode, null );
        firstNode.next = lastNode;
        
        theSize = 0;
        modCount++;
    }
    
    public int size(){
        return theSize;
    }
    
    public boolean isEmpty(){
        return size() == 0;
    }

    public boolean add( E x ){
        add(size(), x);   
        return true;         
    }
    
    public void add( int index, E x ){
        addBefore(getNode(index, 0, size()), x);
    }
        
    private void addBefore( Node<E> p, E value){
        Node<E> newNode = new Node<>(value, p.prev, p);
        newNode.prev.next = newNode;
        p.prev = newNode;         
        theSize++;
        modCount++;
    }   
    
    public E get(int index){
        return getNode(index).data;
    }
        
    
    public E set(int index, E value){
        return set(getNode(index), value); 
    }
    
    public boolean contains(E value){
        Node<E> p = firstNode.next;
        while(p != lastNode && !(p.data.equals(value))){
            p = p.next;
        }
        return (p != lastNode);
    }

    public E remove(int index){
        return remove(getNode(index));
    }

    public void removeAll(Iterable<? extends E> items){
        E item, element;
        Iterator<? extends E> iterItems = items.iterator();
        while (iterItems.hasNext()){
            item = iterItems.next();
            Iterator<? extends E> iterList = iterator();
            while (iterList.hasNext()){
                element = iterList.next();
                if (element.equals(item))
                    iterList.remove();
            }
        }
    }

    private static class Node<E>{
        public Node( E d, Node<E> p, Node<E> n ){
            data = d; 
            prev = p; 
            next = n;
        }
        
        public E data;
        public Node<E> prev;
        public Node<E> next;
    }
    
    private E set(Node<E> p, E newVal){
        E oldVal = p.data;
        p.data = newVal;
        return oldVal;
    }

    private Node<E> getNode(int index){
        return getNode(index, 0, size() - 1);
    }
 
    private Node<E> getNode(int index, int lower, int upper){
        Node<E> p;
        
        if( index < lower || index > upper )
            throw new IndexOutOfBoundsException( "getNode index: " + index + "; size: " + size() );
            
        if( index < size() / 2 ){
            p = firstNode.next;
            for( int i = 0; i < index; i++ )
                p = p.next;            
        }
        else{
            p = lastNode;
            for( int i = size(); i > index; i-- )
                p = p.prev;
        } 
        
        return p;
    }
    
    private E remove( Node<E> p ){
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;
        
        return p.data;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder( "[ " );

        for( E x : this )
            sb.append( x + " " );
        sb.append( "]" );

        return new String( sb );
    }

    public ListIterator<E> iterator(){
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements ListIterator<E>{
        private Node<E> current = firstNode.next;
        private int expectedModCount = modCount;
        private boolean backwards = false;
        
        public boolean hasNext(){
            return current != lastNode;
        }

        public boolean hasPrevious(){ 
            return current.prev != lastNode; 
        }
        
        public E next(){
            if(modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if(!hasNext())
                throw new NoSuchElementException(); 
                   
            E result = current.data;
            current = current.next;
            backwards = false;
            return result;
        }
        
        public E previous(){
            if(modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if(!hasPrevious())
                throw new NoSuchElementException();
            current = current.prev;
            E result = current.data;
            backwards = true;
            return result;
        }
        
        public void remove(){
            if(modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if(!backwards){
                MyLinkedList.this.remove(current.prev);
                expectedModCount++;
            }
            else{
                MyLinkedList.this.remove(current);
                current = current.next;
                expectedModCount++;
            }      
        }

        public void set(E value){
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            MyLinkedList.this.set(current.next, value);
        }

        public void add(E value){
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            MyLinkedList.this.addBefore(current.next, value);
        }

        public int nextIndex(){
            throw new UnsupportedOperationException();
        }

        public int previousIndex(){
            throw new UnsupportedOperationException();
        }
    }

    public static void main( String [ ] args ){
        MyLinkedList<Integer> lst = new MyLinkedList<>();

        for( int i = 0; i < 10; i++ )
                lst.add( i );
        for( int i = 20; i < 30; i++ )
                lst.add( 0, i );
        System.out.println( lst );

        ListIterator<Integer> itr = lst.iterator();
        itr.next();
        itr.next();
        itr.next();
        itr.previous();
        // itr.remove();
        System.out.println(itr.next());
        System.out.println(lst);
    }
}
