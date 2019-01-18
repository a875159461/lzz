import java.util.*;
class UnderflowException extends Exception { };
public class MyTreeSet2<E extends Comparable<? super E>> implements Iterable<E>
{
    
    private BinaryNode<E> root;
    private int modCount = 0;

    private static class BinaryNode<E>
    {
        E element;
        BinaryNode<E> left;
        BinaryNode<E> right;
        BinaryNode<E> next;
        BinaryNode<E> prev;
        BinaryNode( E theElement ){ 
            this( theElement, null, null, null, null ); 
        }

        BinaryNode( E theElement,BinaryNode<E> lt, BinaryNode<E> rt,
                BinaryNode<E> nt, BinaryNode<E> pv ){ 
            element = theElement; 
            left = lt; 
            right = rt; 
            next = nt; 
            prev = pv; 
        }
    }

    public java.util.Iterator<E> iterator()
    {
        return new MyTreeSet2Iterator( );
    }

    private class MyTreeSet2Iterator implements Iterator<E>
    {
        private BinaryNode<E> current = findMin(root);
        private BinaryNode<E> previous;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;
        private boolean atEnd = false;

        public boolean hasNext(){ 
            return !atEnd; 
        }

        public E next(){
            if( modCount != expectedModCount )
                throw new ConcurrentModificationException( );
            if( !hasNext( ) )
                throw new NoSuchElementException( );
        
            E result = current.element;

            previous = current;
            current = current.next;

            if (current == null)
                atEnd = true;
            okToRemove = true;
            return result;
        }

        public void remove(){
            if( modCount != expectedModCount )
                throw new ConcurrentModificationException( );
            if( !okToRemove )
                throw new IllegalStateException( );
            MyTreeSet2.this.remove( previous.element );
            okToRemove = false;
        }
    }

    public MyTreeSet2(){ 
        root = null; 
    }

    public void makeEmpty(){
        modCount++;
        root = null;
    }

    public boolean isEmpty()
    { 
        return root == null; 
    }

    public boolean contains( E x ){ 
        return contains( x, root ); 
    }

    public E findMin() throws UnderflowException{
        if ( isEmpty() )
            throw new UnderflowException();
        else
            return findMin( root ).element;
    }
    
    public E findMax() throws UnderflowException
    {
        if ( isEmpty() )
            throw new UnderflowException();
        else
            return findMax( root ).element;
    }

    public void insert( E x ){ 
        root = insert( x, root, null, null ); 
    }

    public void remove( E x ){ 
        root = remove( x, root ); 
    }

    public void printTree(){
        if ( isEmpty() )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    private void printTree( BinaryNode<E> t ){
        if ( t != null ){
            printTree( t.left );
            System.out.println( t.element );
            printTree( t.right );
        }
    }

    private boolean contains( E x, BinaryNode<E> t ){
        if ( t == null )
            return false;
        int compareResult = x.compareTo( t.element );

        if ( compareResult < 0)
            return contains( x, t.left );
        else if ( compareResult > 0)
            return contains( x, t.right );
        else
            return true; // match
    }

    private BinaryNode<E> findMin( BinaryNode<E> t )
    {
        if ( t == null )
            return null;
        else if ( t.left == null )
            return t;
        return findMin( t.left );
    }

    private BinaryNode<E> findMax( BinaryNode<E> t ){
        if ( t == null )
            return null;
        else if ( t.right == null )
            return t;
        return findMax( t.right );
    }

    private BinaryNode<E> insert( E x, BinaryNode<E> t, BinaryNode<E> nt, BinaryNode<E> pv ){
        if ( t == null )
        {
            modCount++;
            BinaryNode<E> newNode = new BinaryNode<E>( x, null, null, nt, pv);
            if (nt != null){
                nt.prev = newNode;
            }
            if (pv != null){
                pv.next = newNode;
            }
            return newNode;
        }
        int compareResult = x.compareTo( t.element );
        if ( compareResult < 0)
            t.left = insert( x, t.left, t, pv );
        else if ( compareResult > 0){
            t.right = insert( x, t.right, nt, t );
        }
        else
            ; // duplicate
        if(pv != null)
            System.out.println(pv.element);
        return t;
    }
    
    private BinaryNode<E> remove( E x, BinaryNode<E> t ){
        if ( t == null )
            return t; // not found
        int compareResult = x.compareTo( t.element );
        if ( compareResult < 0)
            t.left = remove( x, t.left );
        else if ( compareResult > 0)
            t.right = remove( x, t.right );
        else if ( t.left != null && t.right != null ){ // two children
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else{
            modCount++;
            t.prev.next = t.next; // update next and prev links
            t.next.prev = t.prev;
            t = ( t.left != null ) ? t.left : t.right;
        }
        return t;
    }

    public static void main( String [ ] args ){
        MyTreeSet2<Integer> t = new MyTreeSet2<>( );

        t.insert(9);t.insert(8);t.insert(7);t.insert(6);
        t.insert(4);t.insert(3);
        t.insert(2);t.insert(1);//t.insert(9);t.insert(2);t.insert(5);t.insert(7);
        // Iterator<Integer> it = t.iterator();
        // for(Integer e:t)
        // System.out.println(e);
    }
}