public class MyArrayQueue<E>{
    private static final int DEFAULT_CAPACITY = 10;

    private E[] contents;
    private int front;
    private int rear;
    private int currentSize;

    public MyArrayQueue(){
        front = 0;
        rear = -1;
        currentSize = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    public MyArrayQueue(int size){
        front = 0;
        rear = -1;
        currentSize = 0;
        ensureCapacity(size);
    }

    @SuppressWarnings("unchecked")
    public void ensureCapacity(int size){
        E[] old = contents;
        contents = (E[]) new Object[size];
        if(!isEmpty()){
            int j = 0;
            for(int i = front; i % old.length != rear; i++, j++ ){
                contents[j] = old[i];
            }
            contents[j] = old[rear];
            front = 0;
            rear = j;
        }
        else{
            front = 0;
            rear = -1;
        }
    }

    public boolean isEmpty(){
        return currentSize == 0;
    }

    public int size(){
        return currentSize;
    }

    public void enque(E value){
        if(size() == contents.length)
            ensureCapacity(currentSize * 2 + 1);
        rear = (rear + 1) % contents.length;
        contents[rear] = value;
        currentSize++;
    }
    public E deque(){
        E result;
        if(isEmpty())
            throw new java.util.NoSuchElementException(); //
        else
            result = contents[front];
        front = (front + 1) % contents.length;
        currentSize--;
        return result;
    }

    public String toString( )
    {
            StringBuilder sb = new StringBuilder( "[ " );

            // for( E x : this )
            //     sb.append( x + " " );
            for(int i = front; (i % contents.length) != rear; i++){
                sb.append( contents[i] + " " );
            }
            sb.append( contents[rear] + " " + "]" );

            return new String( sb );
    }

    public static void main( String [ ] args )
    {
        MyArrayQueue<Integer> lst = new MyArrayQueue<>( );

        for( int i = 0; i < 10; i++ )
                lst.enque( i );
        lst.deque();
        for( int i = 20; i < 30; i++ )
                lst.enque( i );

        // lst.deque();

        System.out.println( lst );
    }
    
}