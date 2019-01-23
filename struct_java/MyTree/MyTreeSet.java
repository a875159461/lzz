import java.util.*;
public class MyTreeSet<E extends Comparable<? super E>> implements Iterable<E>{
    private BinaryNode<E> root;
    int modCount = 0;

    private static class BinaryNode<E>{
        public BinaryNode<E> left;
        public BinaryNode<E> right;
        public BinaryNode<E> parent;
        public E element;

        public BinaryNode(E value){
            this(value, null, null, null);
        }

        public BinaryNode(E value, BinaryNode<E> lt, BinaryNode<E> rt, BinaryNode<E> pa){
            element = value;
            left = lt;
            right = rt;
            parent = pa;
        }
    }
     
    public Iterator<E> iterator(){
        return new MyTreeSetIterator();
    }

    private class MyTreeSetIterator implements Iterator<E>{
        private BinaryNode<E> current = findMin(root);
        private BinaryNode<E> previous;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;
        private boolean atEnd = false;

        public boolean hasNext(){
            return !atEnd;
        }

        public E next(){
            if(expectedModCount != modCount){
                throw new ConcurrentModificationException();
            }
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            E result = current.element;
            previous = current;

            if (current.right != null){
                current = findMin(current.right);
            }  
            else{
                BinaryNode<E> child = current;
                current = current.parent;

                while(current != null && current.left != child){
                    child = current;
                    current = current.parent;
                }
                if (current == null)
                    atEnd = true;
            } 
            okToRemove = true;
            return result;
        }

        public void remove(){
            if( modCount != expectedModCount )
                throw new ConcurrentModificationException( );
            if( !okToRemove )
                throw new IllegalStateException( );
            MyTreeSet.this.remove( previous.element );
            okToRemove = false;
        }
    }

    public MyTreeSet(){ 
        root = null; 
    }

    public boolean isEmpty(){
        return root == null;
    }

    public void makeEmpty(){
        modCount++;
        root = null;
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
        root = insert(value, root, null);
    }

    public void remove(E value){
        root = remove(value, root);
    }

    private boolean contains(E value, BinaryNode<E> t){
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

    private BinaryNode<E> findMin(BinaryNode<E> t){
        if(t == null)
            return null;
        else if(t.left == null)
            return t;
        else
            return findMin(t.left);
    }

    private BinaryNode<E> findMax(BinaryNode<E> t){
        if(t != null){
            while(t.right != null){
                t = t.right;
            }
        }
        return t;
    }

    private BinaryNode<E> insert(E value, BinaryNode<E> t, BinaryNode<E> pa){
        if(t == null){
            modCount++;
            return new BinaryNode<>(value, null, null, pa);
        }

        int compareResult = value.compareTo(t.element);
        if(compareResult == 0)
            ;
        else if(compareResult < 0)
            t.left = insert(value, t.left, t);
        else
            t.right = insert(value, t.right, t);
        return t;
    }

    private BinaryNode<E> remove(E value, BinaryNode<E> t){
        if(t == null)
            return t;   // Item not found; do nothing

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
            modCount++;
            BinaryNode<E> oneChild;
            oneChild = (t.left != null) ? t.left : t.right;
            oneChild.parent = t.parent;
            t = oneChild;
        }
        return t;
    }

    public void printTree(){
        if(isEmpty()){
            throw new NullPointerException();
        }
        printTree(root);
    }

    private void printTree(BinaryNode<E> t){
        if(t != null){
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    public static void main( String [ ] args )
    {
        MyTreeSet<Integer> t = new MyTreeSet<>( );

        System.out.println( "Checking... (no more output means success)" );
        t.insert(3);t.insert(1);t.insert(4);t.insert(6);t.insert(9);t.insert(2);t.insert(5);t.insert(7);
        // Iterator<Integer> it = t.iterator();
        for(Integer e:t)
        System.out.println(e);
    }
}