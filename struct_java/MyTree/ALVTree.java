import java.util.*;
public class ALVTree<E extends Comparable<? super E>>{
    private static final int ALLOWED_IMBALANCE = 1;
    private ALVNode<E> root;

    public ALVTree(){
        root = null;
    }

    public void makeEmpty(){
        root = null;
    }

    public boolean isEmpty(){
        return root == null;
    }

    private static class ALVNode<E>{
        public ALVNode<E> left;
        public ALVNode<E> right;
        public E element;
        public int height;

        public ALVNode(E value){
            this(value, null, null);
        }

        public ALVNode(E value, ALVNode<E> lt, ALVNode<E> rt){
            element = value;
            left = lt;
            right = rt;
            height = 0;
        }
    }

    public boolean contains(E value){
        return contains(value, root);
    }

    public E findMin(){
        if(isEmpty()){
            throw new NullPointerException();
        }
        return findMin(root).element;
    }

    public E findMax(){
        if(isEmpty()){
            throw new NullPointerException();
        }
        return findMax(root).element;
    }

    public void insert(E value){
        root = insert(value, root);
    }

    public void remove(E value){
        root = remove(value, root);
    }
    
    public void printTree(){
        if(isEmpty()){
            throw new NullPointerException();
        }
        printTree(root);
    }

    private boolean contains(E value, ALVNode<E> t){
        if(t == null)
            return false;
        int compareResult = value.compareTo(t.element);
        if(compareResult == 0)
            return true;
        else if (compareResult < 0)
            return contains(value, t.left);
        else
            return contains(value, t.right);
    }

    private ALVNode<E> findMin(ALVNode<E> t){
        if(t == null)
            return null;
        else if(t.left == null)
            return t;
        else
            return findMin(t.left);
    }

    private ALVNode<E> findMax(ALVNode<E> t){
        if(t != null){
            while(t.right != null){
                t = t.right;
            }
        }
        return t;
    }

    private ALVNode<E> insert(E value, ALVNode<E> t){
        if(t == null){
            return new ALVNode<>(value, null, null);
        }

        int compareResult = value.compareTo(t.element);
        if(compareResult == 0)
            ;
        else if(compareResult < 0)
            t.left = insert(value, t.left);
        else
            t.right = insert(value, t.right);
        return balance(t);
    }

    private ALVNode<E> remove(E value, ALVNode<E> t){
        if(t == null)
            return null;

        int compareResult = value.compareTo(t.element);
        if(compareResult < 0){
            t.left = remove(value, t.left);
        }
        else if(compareResult > 0){
            t.right = remove(value, t.right);
        }
        else if(t.right != null && t.left != null){
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        }
        else{
            t = (t.left != null) ? t.left : t.right;
        }
        return balance(t);
    }

    private void printTree(ALVNode<E> t){
        if(t != null){
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    public int height(ALVNode<E> t){
        return (t == null) ? -1 : t.height;
    }

    private ALVNode<E> balance(ALVNode<E> t){
        if(t == null)
            return t;
        if(height(t.left) - height(t.right) > ALLOWED_IMBALANCE){
            if(height(t.left.left) >= height(t.left.right))
                t = rotateWithLeftChild(t);
            else
                t = doubleWithLeftChild(t);
        }
        else if(height(t.right) - height(t.left) > ALLOWED_IMBALANCE){
            if(height(t.right.right) >= height(t.right.left))
                t = rotateWithRightChild(t);
            else
                t = doubleWithRightChild(t);
        }
        
        t.height = 1 + Math.max(height(t.left), height(t.right));
        return t;
    }

    public ALVNode<E> rotateWithLeftChild(ALVNode<E> k2){
        ALVNode<E> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), height(k2)) + 1;
        return k1;
    }

    public ALVNode<E> doubleWithLeftChild(ALVNode<E> k3){
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    public ALVNode<E> rotateWithRightChild(ALVNode<E> k1){
        ALVNode<E> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.left), height(k1)) + 1;
        return k2;
    }

    public ALVNode<E> doubleWithRightChild(ALVNode<E> k1){
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }

    public void checkBalance(){
        checkBalance(root);
    }

    private int checkBalance(ALVNode<E> t){
        if(t == null)
            return -1;
        if( t != null )
        {
            int hl = checkBalance( t.left );
            int hr = checkBalance( t.right );
            if( Math.abs( height( t.left ) - height( t.right ) ) > 1 ||
                    height( t.left ) != hl || height( t.right ) != hr )
                System.out.println( "OOPS!!" );
        }
        return height(t);
    }

    public static void main( String [ ] args )
    {
        ALVTree<Integer> t = new ALVTree<>( );
        final int SMALL = 40;
        final int NUMS = 1000000;  // must be even
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );

        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
        {
        //    System.out.println( "INSERT: " + i );
            t.insert( i );
            if( NUMS < SMALL )
                t.checkBalance( );
        }
        
        for( int i = 1; i < NUMS; i+= 2 )
        {
         //   System.out.println( "REMOVE: " + i );
            t.remove( i );
            if( NUMS < SMALL )
                t.checkBalance( );
        }
        if( NUMS < SMALL )
            t.printTree( );
        if( t.findMin( ) != 2 || t.findMax( ) != NUMS - 2 )
            System.out.println( "FindMin or FindMax error!" );

        for( int i = 2; i < NUMS; i+=2 )
             if( !t.contains( i ) )
                 System.out.println( "Find error1!" );

        for( int i = 1; i < NUMS; i+=2 )
        {
            if( t.contains( i ) )
                System.out.println( "Find error2!" );
        }
    }
}