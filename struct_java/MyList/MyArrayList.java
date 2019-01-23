import java.util.*;
public class MyArrayList<E> implements Iterable<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private int currentSize = 0;
    private E[] contents;

    public MyArrayList(){
        doclear();
    }

    public MyArrayList(int size){
        currentSize = 0;
        ensureCapacity(size);
    }

    public void clear(){
        doclear();
    }

    public void doclear(){
        currentSize = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public void ensureCapacity(int newCapacity){
        if(newCapacity < currentSize)
            return ;
        E[] old = contents;
        contents = (E[]) new Object[newCapacity];
        for(int i = 0; i < currentSize; i++){
            contents[i] = old[i];
        }
        modCount++;
    }

    public void set(int index, E e){
        contents[index] = e;
    }

    public E get(int index){
        E result = contents[index];
        return result;
    }

    public int size(){
        return currentSize;
    }

    public void trimToSize(){
        ensureCapacity(currentSize);
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public void add(E value){
        add(currentSize, value);
    }

    public void add(int index, E value){
        if(currentSize == contents.length)
            ensureCapacity(currentSize * 2 + 1);
        for(int i = currentSize; i > index; i--){
            contents[i] = contents[i-1];
        }
        contents[index] = value;
        currentSize++;
        modCount++;
    }

    public E remove(int index){
        E result = contents[index];
        for(int i = index; i < currentSize - 1; i++){
            contents[i] = contents[i+1];
        }
        currentSize--;
        modCount++;
        return result;
    }

    public void addAll(Iterable<? extends E> items){
        for(E e:items){
            add(e);
        }
    }

    public Iterator<E> iterator(){
        return new ArrayListIterator();
    }

    public ListIterator<E> listIterator(){
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements ListIterator<E>{
        private int current = 0;
        private boolean backwards = false;

        public boolean hasNext(){
            return current < currentSize;
        }

        public E next(){
            if(expectedCount != modCount){
                throw new ConcurrentModificationException();
            if( !hasNext( ) ) 
                throw new NoSuchElementException( ); 
            backwards = false; 
            E result = contents[current];
            current++;
            return result;
        }

        public void remove(){
            if(expectedCount != modCount){
                throw new ConcurrentModificationException();
            if( backwards ){
                MyArrayList.this.remove(current); 
                current--;
            }
            else{
                current--;
                MyArrayList.this.remove(current);
            }
            expectedCount++;
        }

        public boolean hasPrevious(){
            return current > 0;
        }

        public E previous(){
            if(expectedCount != modCount){
                throw new ConcurrentModificationException();
            if( !hasPrevious( ) ) 
                throw new java.util.NoSuchElementException( ); 
            backwards = true; 
            current--;
            E result = contents[current];
            return result;
        }

        public void add(E e){
            if(expectedCount != modCount){
                throw new ConcurrentModificationException();
            MyArrayList.this.add(current, e);
            current++;
            expectedCount++;
        }

        public void set(E e){
            if(expectedCount != modCount){
                throw new ConcurrentModificationException();
            MyArrayList.this.set(current, e);
        }

        public int nextIndex(){
            return current;
        }

        public int previousIndex(){
            // throw new UnsupportedOperationException();
            return current - 1;
        }

        
    }

    public String toString( )
    {
            StringBuilder sb = new StringBuilder( "[ " );

            for( E x : this )
                sb.append( x + " " );
            sb.append( "]" );

            return new String( sb );
    }

    public static void main( String [ ] args )
    {
        MyArrayList<Integer> lst = new MyArrayList<>( );

        for( int i = 0; i < 10; i++ )
                lst.add( i );
        // for( int i = 20; i < 30; i++ )
        //         lst.add( 0, i );
        // lst.addAll(Arrays.asList(20, 21, 22, 23, 24, 25, 26, 27));
        // lst.remove( 0 );
        // lst.remove( lst.size( ) - 1 );
        ListIterator<Integer> iter = lst.listIterator();
        iter.next();
        // iter.next();
        // iter.previous();
        iter.remove();
        Integer tmp = iter.next();
        System.out.println(tmp);
    }
}