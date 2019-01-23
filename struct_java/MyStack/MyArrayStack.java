public class MyArrayStack<E> implements Iterable<E>{
    private static final int DEFAULT_CAPACITY = 10;
    private E[] contents;
    private int top;
    
    public MyArrayStack(){
        doclear();
    }

    public MyArrayStack(int size){
        top = -1;
        changeCapacity(size);
    }

    public void clear(){
        doclear();
    }

    
    public void doclear(){
        top = -1;
        changeCapacity(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public void changeCapacity(int newCapacity){
        E[] old = contents;
        contents = (E[]) new Object[newCapacity];
        for(int i = 0; i <= top; i++){
            contents[i] = old[i];
        }

    }

    public int size(){
        return top + 1;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public void push(E e){
        if(top == (contents.length -1)){
            changeCapacity(contents.length * 2);
        }
        top++;
        contents[top] = e;
    }

    public E pop(){
        if(top == -1){
            throw new java.util.NoSuchElementException();
        }
        E result = contents[top];
        top--;
        return result;
    }

    public E peek(){
        if(top == -1){
            throw new java.util.NoSuchElementException();
        }
        return contents[top];
    }

    public int search(E e){  //e does exist in MyArrayStack
        int i = top;
        for(; i >= 0; i--){
            if(contents[i] == e)
                break;
        }
        return i;
    }

    public java.util.Iterator<E> iterator(){
        return new ArrayStackIterator();
    }

    private class ArrayStackIterator implements java.util.Iterator<E>{
        int current = top;
        public boolean hasNext(){
            return current >= 0;
        }

        public E next(){
            E result = contents[current];
            current--;
            return result;
        }

        public void remove(){
            MyArrayStack.this.pop();
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
        MyArrayStack<Integer> lst = new MyArrayStack<>( );

        for( int i = 0; i < 10; i++ )
                lst.push( i );
        for( int i = 20; i < 30; i++ )
                lst.push( i );

        lst.pop();

        System.out.println( lst );
    }
    
}