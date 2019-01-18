import java.util.*;
public class SeparateChainingHashTable<E>{
    private static final int DEFAULT_TABLE_SIZE = 101;

    private List<E>[] theLists;
    private int currentSize;

    public SeparateChainingHashTable(){
        this(DEFAULT_TABLE_SIZE);
    }

    @SuppressWarnings("unchecked")
    public SeparateChainingHashTable(int size){
        theLists = new LinkedList[nextPrime(size)];
        for(int i = 0; i < theLists.length; i++){
            theLists[i] = new LinkedList<>();
        }
        currentSize = 0;
    }

    public void makeEmpty(){
        for(int i = 0; i < theLists.length; i++){
            theLists[i].clear();
        }
        currentSize = 0;
    }

    public boolean contains(E e){
        int index = myHash(e);
        return theLists[index].contains(e);
    }

    public void insert(E e){
        int index = myHash(e);
        if(!contains(e)){
            theLists[index].add(e);
            if(++currentSize > theLists.length)
                rehash();
        }
    }

    public void remove(E e){
        int index = myHash(e);
        if(contains(e)){
            currentSize--;
            theLists[index].remove(e);
        }
    }

    public int myHash(E e){
        int hashVal = e.hashCode();
        hashVal = hashVal % theLists.length;
        if(hashVal < 0)
            hashVal = hashVal + theLists.length;
        return hashVal;
    }

    @SuppressWarnings("unchecked")
    public void rehash(){
        List<E>[] oldLists = theLists;
        theLists = new List[nextPrime(theLists.length * 2)];
        for(int i = 0; i < theLists.length; i++){
            theLists[i] = new LinkedList<>();
        }

        currentSize = 0;
        for(List<E> list: oldLists)
            for(E item: list)
                insert(item);
    }

    private static int nextPrime(int n){
        if(n % 2 == 0)
            n++;
        for(; !isPrime(n);n += 2)
            ;
        return n;
    }

    private static boolean isPrime(int n){
        if(n == 2 || n ==3)
            return true;
        if(n == 1 || n % 2 == 0)
            return false;
        for(int i = 3; i * i <= n; i += 2){
            if(n % i == 0)
                return false;
        }
        return true;
    }




    public static void main( String [ ] args )
    {
        SeparateChainingHashTable<Integer> H = new SeparateChainingHashTable<>( );

        long startTime = System.currentTimeMillis( );
        
        final int NUMS = 2000000;
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );

        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            H.insert( i );
        for( int i = 1; i < NUMS; i+= 2 )
            H.remove( i );

        for( int i = 2; i < NUMS; i+=2 )
            if( !H.contains( i ) )
                System.out.println( "Find fails " + i );

        for( int i = 1; i < NUMS; i+=2 )
        {
            if( H.contains( i ) )
                System.out.println( "OOPS!!! " +  i  );
        }
        
        long endTime = System.currentTimeMillis( );
        
        System.out.println( "Elapsed time: " + (endTime - startTime) );
    }
}