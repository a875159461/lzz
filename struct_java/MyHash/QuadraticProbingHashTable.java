public class QuadraticProbingHashTable<E>{
    private static final int DEFAULT_TABLE_SIZE = 11;

    private HashEntry<E>[] array;
    private int currentSize; //isAct
    private int occupied; //noAct and isAct

    public QuadraticProbingHashTable(){
        this(DEFAULT_TABLE_SIZE);
    }

    public QuadraticProbingHashTable(int size){
        allocateArray(size);
        makeEmpty();
    }

    public void makeEmpty(){
        currentSize = 0;
        for(int i = 0; i < array.length; i++)
            array[i] = null;
    }

    private static class HashEntry<E>{
        public E element;
        public boolean isActive;

        public HashEntry(E e){
            this(e, true);
        }

        public HashEntry(E e, boolean b){
            element = e;
            isActive = b;
        }
    }

    // @SuppressWarnings("unchecked")
    public void allocateArray(int size){
        array = new HashEntry[nextPrime(size)];
    }

    public int findPos(E e){
        int currentPos = myHash(e);
        int offset = 1;

        while(array[currentPos] != null && !array[currentPos].element.equals(e)){
            currentPos += offset;
            offset += 2;
            if(currentPos >= array.length)
                currentPos = currentPos - array.length;
        }
        return currentPos;
    }

    public boolean isActive(int currentPos){
        return array[currentPos] != null && array[currentPos].isActive;
    }



    public int myHash(E e){
        int hashVal = e.hashCode();
        hashVal = hashVal % array.length;
        if(hashVal < 0)
            hashVal = hashVal + array.length;
        return hashVal;
    }

    // @SuppressWarnings("unchecked")
    public void rehash(){
        HashEntry<E>[] oldArray = array;


        allocateArray(array.length * 2);
        currentSize = 0;
        for(HashEntry<E> item: oldArray)
            if(item != null && item.isActive) // delete noActive
                insert(item.element);
    }

    

    public boolean contains(E e){
        int pos = findPos(e);
        return isActive(pos);
    }

    public boolean insert(E e){
        int pos = findPos(e);
        if(isActive(pos))
            return false;
        array[pos] = new HashEntry<>(e, true); //allocate the null
        currentSize++;
        if(currentSize > array.length / 2)
            rehash();
        return true;
    }

    public boolean remove(E e){
        int pos = findPos(e);
        if(isActive(pos)){
            array[pos].isActive = false;
            currentSize--;
            return true;
        }
        return false;    
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
        QuadraticProbingHashTable<String> H = new QuadraticProbingHashTable<>( );

        
        long startTime = System.currentTimeMillis( );
        
        final int NUMS = 2000000;
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );


        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            H.insert( ""+i );
        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            if( H.insert( ""+i ) )
                System.out.println( "OOPS!!! " + i );
        for( int i = 1; i < NUMS; i+= 2 )
            H.remove( ""+i );

        for( int i = 2; i < NUMS; i+=2 )
            if( !H.contains( ""+i ) )
                System.out.println( "Find fails " + i );

        for( int i = 1; i < NUMS; i+=2 )
        {
            if( H.contains( ""+i ) )
                System.out.println( "OOPS!!! " +  i  );
        }
        
        long endTime = System.currentTimeMillis( );
        
        System.out.println( "Elapsed time: " + (endTime - startTime) );
    }
}