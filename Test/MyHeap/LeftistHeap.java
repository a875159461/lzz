public class LeftistHeap<E extends Comparable<? super E>>{
    private Node<E> root;

    public LeftistHeap(){
        root = null;
    }

    public void merge(LeftistHeap<E> rts){
        if(this == rts)
            return;
        root = merge(root, rts.root);
        rts.root = null;
    }

    public void insert(E e){
        root = merge( new Node<>( e ), root );
    }

    public E findMin(){
        if( isEmpty( ) )
            throw new NullPointerException( );
        return root.element;
    }

    public E deleteMin(){
        if( isEmpty( ) )
            throw new NullPointerException( );

        E minItem = root.element;
        root = merge( root.left, root.right );

        return minItem;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public void makeEmpty(){
        root = null;
    }

    private static class Node<E>{
        public Node<E> left;
        public Node<E> right;
        public E element;
        int npl;

        public Node(E value){
            this(value, null, null);
        }

        public Node(E value, Node<E> lt, Node<E> rt){
            element = value;
            left = lt;
            right = rt;
            npl = 0;
        }
    }

    private Node<E> merge(Node<E> h1,Node<E> h2){
        if(h1 == null)
            return h2;
        if(h2 == null)
            return h1;
        if(h1.element.compareTo(h2.element) < 0)
            return merge1(h1, h2);
        else
            return merge1(h2, h1);
    }

    private Node<E> merge1(Node<E> h1,Node<E> h2){
        if(h1.left == null)
            h1.left = h2;
        else{
            h1.right = merge(h1.right, h2);
            if(h1.left.npl < h1.right.npl)
                swapChildren(h1);
            h1.npl = h1.right.npl + 1;
        }
        return h1;
    }

    private void swapChildren(Node<E> t){
        Node<E> tmp = t.left;
        t.left = t.right;
        t.right = tmp;
    }

    public static void main( String [ ] args )
    {
        int numItems = 100;
        LeftistHeap<Integer> h  = new LeftistHeap<>( );
        LeftistHeap<Integer> h1 = new LeftistHeap<>( );
        int i = 37;

        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            if( i % 2 == 0 )
                h1.insert( i );
            else
                h.insert( i );

        h.merge( h1 );
        for( i = 1; i < numItems; i++ )
            if( h.deleteMin( ) != i )
                System.out.println( "Oops! " + i );
    }
} 