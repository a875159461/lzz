public class MyLinkedStack<E> implements Iterable<E>{
    private Node<E> firstNode;
    private Node<E> lastNode;
    private int top;

    private static class Node<E>{
        public E data;
        public Node<E> prev;
        public Node<E> next;

        public Node(E value, Node<E> p, Node<E> n){
            data = value;
            prev = p;
            next = n;
        }
    }

    public MyLinkedStack(){
        firstNode = new Node<>(null, null, null);
        lastNode = new Node<>(null, firstNode, null);
        firstNode.next = lastNode;
        top = -1;
    }

    public boolean isEmpty(){
        return top == -1;
    }

    public int size(){
        return top + 1;
    }

    public void push(E e){
        Node<E> p = new Node<>(e, firstNode, firstNode.next);
        firstNode.next.prev = p;
        firstNode.next = p;
        top++;
    }

    public E pop(){
        Node<E> p = firstNode.next;
        E result = p.data;
        p.next.prev = firstNode;
        firstNode.next =  p.next;
        return result;
    }

    public E peek(){
        return firstNode.next.data;
    }

    public java.util.Iterator<E> iterator(){
        return new LinkedStackIterator();
    }

    public class LinkedStackIterator implements java.util.Iterator<E>{
        Node<E> current = firstNode.next;

        public boolean hasNext(){
            return current != lastNode;
        }

        public E next(){
            E result = current.data;
            current = current.next;
            return result;
        }
    }

    public String toString( )
    {
            StringBuilder sb = new StringBuilder( "[ " );

            for( E x : this )
                sb.append( x + " " );
            // for(int i = top; i >= 0; i--){
            //     sb.append( contents[i] + " " );
            // }
            sb.append( "]" );

            return new String( sb );
    }

    public static void main( String [ ] args )
    {
        MyLinkedStack<Integer> lst = new MyLinkedStack<>( );

        for( int i = 0; i < 10; i++ )
                lst.push( i );
        for( int i = 20; i < 30; i++ )
                lst.push( i );

        lst.pop();

        System.out.println( lst );
    }
}