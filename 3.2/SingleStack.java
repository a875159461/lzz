public class SingleStack<E>{
    private Node<E> top;
    private int currentSize;

    private class Node<E>{
        public Node<E> next;
        public E data;

        public Node(E value, Node<E> n){
            data = value;
            next = n;
        }
    }

    public SingleStack(){
        top = null;
        currentSize = 0;
    }

    public int size(){
        return currentSize;
    }

    public boolean isEmpty(){
        return currentSize == 0;
    }

    public void push(E value){
        Node<E> newNode= new Node<>(value, top);
        top = newNode;
        currentSize++;
    }

    public E pop(){
        if(isEmpty())
            throw new java.util.NoSuchElementException(); //
        E result = top.data;
        top = top.next;
        return result;
    }

    public void swap(){
        Node<E> pre = null;
        Node<E> p = top;
        Node<E> n = p.next;
        p.next = pre;
        while(n != null){
            pre = p;
            p = n;
            n = p.next; 
            p.next = pre;
        }
        top = p;
    }

    public String toString( )
    {
            StringBuilder sb = new StringBuilder( "[ " );
            Node<E> p = top;
            // for( E x : this )
            //     sb.append( x + " " );
            while(p != null){
                sb.append( p.data + " " );
                p = p.next;
            }
            sb.append( "]" );

            return new String( sb );
    }

    public static void main( String [ ] args )
    {
         SingleStack<Integer> lst = new  SingleStack<>( );

        for( int i = 0; i < 10; i++ )
                lst.push( i );
        for( int i = 20; i < 30; i++ )
                lst.push( i );

        lst.pop();
        lst.swap();
        System.out.println( lst );
    }
}