public class MyLinkedQueue<E>{
    private Node<E> front;
    private Node<E> rear;
    private int currentSize;

    private static class Node<E>{
        public Node<E> next;
        public E data;

        public Node(E value, Node<E> n){
            data = value;
            next = n;
        }
    }

    public MyLinkedQueue(){
        front = null;
        rear = null;
        currentSize = 0;
    }

    public int size(){
        return currentSize;
    }

    public boolean isEmpty(){
        return currentSize == 0;
    }

    public void enque(E value){
        if(isEmpty()){
            Node<E> firstNode = new Node<>(value, null);
            rear = firstNode;
            front = firstNode;
            currentSize++;
        }
        else{
            Node<E> newNode = new Node<>(value, null);
            rear.next = newNode;
            rear = newNode;
            currentSize++;
        }
    }

    public E deque(){
        if(isEmpty())
            throw new java.util.NoSuchElementException(); //
        E result = front.data;
        if (front.next == null) // only 1 node
            front = rear = null;
        else
            front = front.next;
        currentSize--;
        return result;
    }

    public String toString( )
    {
            StringBuilder sb = new StringBuilder( "[ " );
            Node<E> p = front;
            // for( E x : this )
            //     sb.append( x + " " );
            while(p != null){
                sb.append( p.data + " " );
                p = p.next;
            }

            return new String( sb );
    }

    public static void main( String [ ] args )
    {
        MyLinkedQueue<Integer> lst = new MyLinkedQueue<>( );

        for( int i = 0; i < 10; i++ )
                lst.enque( i );
        for( int i = 20; i < 30; i++ )
                lst.enque( i );

        lst.deque();

        System.out.println( lst );
    }
}