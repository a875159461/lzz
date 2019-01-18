import java.util.*;
public class BinomialQueue<E extends Comparable<? super E>>{
    private static final int DEFAULT_TREES = 1;

    private int currentSize;                // # items in priority queue
    private Node<E> [] theTrees;  // An array of tree roots

    public BinomialQueue(){
        theTrees = new Node[DEFAULT_TREES];
        makeEmpty( );
    }

    public BinomialQueue(E item){
        currentSize = 1;
        theTrees = new Node[1];
        theTrees[0] = new Node<>(item, null, null);
    }

    public void merge(BinomialQueue<E> rhs){
        if( this == rhs )    // Avoid aliasing problems
            return;

        currentSize += rhs.currentSize;
        
        if( currentSize > capacity( ) )
        {
            int newNumTrees = Math.max( theTrees.length, rhs.theTrees.length ) + 1;
            expandTheTrees( newNumTrees );
        }

        Node<E> carry = null;
        for( int i = 0, j = 1; j <= currentSize; i++, j *= 2 )
        {
            Node<E> t1 = theTrees[ i ];
            Node<E> t2 = i < rhs.theTrees.length ? rhs.theTrees[ i ] : null;

            int whichCase = t1 == null ? 0 : 1;
            whichCase += t2 == null ? 0 : 2;
            whichCase += carry == null ? 0 : 4;

            switch( whichCase )
            {
              case 0: /* No trees */
              case 1: /* Only this */
                break;
              case 2: /* Only rhs */
                theTrees[ i ] = t2;
                rhs.theTrees[ i ] = null;
                break;
              case 4: /* Only carry */
                theTrees[ i ] = carry;
                carry = null;
                break;
              case 3: /* this and rhs */
                carry = combineTrees( t1, t2 );
                theTrees[ i ] = rhs.theTrees[ i ] = null;
                break;
              case 5: /* this and carry */
                carry = combineTrees( t1, carry );
                theTrees[ i ] = null;
                break;
              case 6: /* rhs and carry */
                carry = combineTrees( t2, carry );
                rhs.theTrees[ i ] = null;
                break;
              case 7: /* All three */
                theTrees[ i ] = carry;
                carry = combineTrees( t1, t2 );
                rhs.theTrees[ i ] = null;
                break;
            }
        }

        for( int k = 0; k < rhs.theTrees.length; k++ )
            rhs.theTrees[ k ] = null;
        rhs.currentSize = 0;
    }

    public void insert(E value){
        merge(new BinomialQueue<E>(value));
    }

    public E findMin(){
        if( isEmpty( ) )
            throw new NullPointerException( );

        return theTrees[findMinIndex()].element;
    }

    public E deleteMin(){
        if( isEmpty( ) )
            throw new NullPointerException( );

        int minIndex = findMinIndex( );
        E minItem = theTrees[ minIndex ].element;

        Node<E> deletedTree = theTrees[ minIndex ].leftChild;

        // Construct H''
        BinomialQueue<E> deletedQueue = new BinomialQueue<>( );
        deletedQueue.expandTheTrees( minIndex );
        
        deletedQueue.currentSize = ( 1 << minIndex ) - 1;
        for( int j = minIndex - 1; j >= 0; j-- )
        {
            deletedQueue.theTrees[ j ] = deletedTree;
            deletedTree = deletedTree.nextSibling;
            deletedQueue.theTrees[ j ].nextSibling = null;
        }

        // Construct H'
        theTrees[ minIndex ] = null;
        currentSize -= deletedQueue.currentSize + 1;

        merge( deletedQueue );
        
        return minItem;
    }

    public boolean isEmpty(){
        return currentSize == 0;
    }

    public void makeEmpty(){
        currentSize = 0;
        for( int i = 0; i < theTrees.length; i++ )
            theTrees[ i ] = null;
    }

    private static class Node<E>{
        private E element;
        private Node<E> leftChild;
        private Node<E> nextSibling;

        public Node(E value){
            this(value, null, null);
        }

        public Node(E value, Node<E> lt, Node<E> nt){
            element = value;
            leftChild = lt;
            nextSibling = nt;
        }
    }

    private void expandTheTrees(int newNumTrees){
        Node<E> [ ] old = theTrees;
        int oldNumTrees = theTrees.length;

        theTrees = new Node[ newNumTrees ];
        for( int i = 0; i < Math.min( oldNumTrees, newNumTrees ); i++ )
            theTrees[ i ] = old[ i ];
        for( int i = oldNumTrees; i < newNumTrees; i++ )
            theTrees[ i ] = null;
    }

    private Node<E> combineTrees(Node<E> t1, Node<E> t2){
        if(t1.element.compareTo(t2.element) > 0 )
            return combineTrees( t2, t1 );
        t2.nextSibling = t1.leftChild;
        t1.leftChild = t2;
        return t1;
    }

    private int capacity(){
        return (1 << theTrees.length) - 1;
    }

    private int findMinIndex(){
        int i;
        int minIndex;

        for( i = 0; theTrees[ i ] == null; i++ )
            ;

        for( minIndex = i; i < theTrees.length; i++ )
            if( theTrees[ i ] != null &&
                theTrees[ i ].element.compareTo( theTrees[ minIndex ].element ) < 0 )
                minIndex = i;

        return minIndex;
    }

    public static void main(String[] args){
        int numItems = 10000;
        BinomialQueue<Integer> h  = new BinomialQueue<>( );
        BinomialQueue<Integer> h1 = new BinomialQueue<>( );
        int i = 37;

        System.out.println( "Starting check." );

        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            if( i % 2 == 0 )
                h1.insert( i );
            else
                h.insert( i );

        h.merge( h1 );
        for( i = 1; i < numItems; i++ )
            if( h.deleteMin( ) != i )
                System.out.println( "Oops! " + i );
 
        System.out.println( "Check done." );
    }
}