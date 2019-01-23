package myheap;

import java.util.*;
public class BinaryHeap<E extends Comparable<? super E>>{
    private E[] array;
    private int currentSize;
    private static final int DEFAULT_CAPACITY = 10;

    private void enlargeArray(int newSize){
        E [] old = array;
        array = (E []) new Comparable[ newSize ];
        for( int i = 0; i < old.length; i++ )
            array[ i ] = old[ i ];  
    }

    public BinaryHeap(){
        this(DEFAULT_CAPACITY);
    }

    public BinaryHeap(int capacity){
        currentSize = 0;
        array = (E[]) new Comparable[capacity + 1];
    }

    public BinaryHeap(E[] items){
        currentSize = items.length;
        array = (E[]) new Comparable[(currentSize + 2) * 11 / 10];
        int i = 1;
        for(E e:items){
            array[i] = e;
            i++;
        }
        buildHeap();
    }

    public void makeEmpty(){
        currentSize = 0;
    }

    public boolean isEmpty(){
        return currentSize == 0;
    }

    public E findMin(){
        if( isEmpty( ) )
            throw new NullPointerException( );
        return array[ 1 ];
    }

    public void insert(E value){
        if(currentSize == array.length - 1)
            enlargeArray(array.length * 2 + 1);
        int hole = currentSize + 1;
        for(array[0] = value; value.compareTo(array[hole/2]) < 0;hole = hole / 2){
            array[hole] = array[hole/2];
        } 
        array[hole] = value;
        currentSize++;
    }

    public E deleteMin(){
        if( isEmpty( ) )
            throw new NullPointerException( );
        E result = array[1];
        array[1] = array[currentSize--];
        percolateDown(1);
        return result;
    }

    private void percolateDown(int hole){
        int child;
        E tmp = array[hole];
        for(;hole * 2 <= currentSize; hole = child){
            child = hole * 2;
            if(child != currentSize && array[child+1].compareTo(array[child]) < 0)
                child++;
            if(array[child].compareTo(tmp) < 0)
                array[hole] = array[child];
            else 
                break;
        }
        array[hole] = tmp;
    }

    public void buildHeap(){
        for(int i = currentSize / 2; i > 0; i--)
            percolateDown(i);
    }

    public static void main(String[] args){
        int numItems = 10000;
        BinaryHeap<Integer> h = new BinaryHeap<>( );
        int i = 37;

        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            h.insert( i );
        for( i = 1; i < numItems; i++ )
            if( h.deleteMin( ) != i )
                System.out.println( "Oops! " + i );
    }
}