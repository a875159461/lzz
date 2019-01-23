import java.util.*;
public class BinarySearchTree<E extends Comparable<? super E>>{
    private BinaryNode<E> root;

    public BinarySearchTree(){
        root = null;
    }

    public void makeEmpty(){
        root = null;
    }

    public boolean isEmpty(){
        return root == null;
    }

    private static class BinaryNode<E>{
        public BinaryNode<E> left;
        public BinaryNode<E> right;
        public E element;

        public BinaryNode(E value){
            this(value, null, null);
        }

        public BinaryNode(E value, BinaryNode<E> lt, BinaryNode<E> rt){
            element = value;
            left = lt;
            right = rt;
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

    private BinaryNode<E> insert(E value, BinaryNode<E> t){
        if(t == null){
            return new BinaryNode<>(value, null, null);
        }

        int compareResult = value.compareTo(t.element);
        if(compareResult == 0)
            ;
        else if(compareResult < 0)
            t.left = insert(value, t.left);
        else
            t.right = insert(value, t.right);
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
            t = (t.left != null) ? t.left : t.right;
        }
        return t;
    }

    public void printTree(){
        if(isEmpty()){
            throw new NullPointerException();
        }
        printTreeInLevelOrder(root);
    }

    private void printTree(BinaryNode<E> t){
        if(t != null){
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    private void printTreeInLevelOrder(BinaryNode<E> t){
        Queue<BinaryNode<E>> queue = new LinkedList<>();
        BinaryNode<E> tmp = t;
        while(tmp != null){
            System.out.println(tmp.element + ", ");
            if(tmp.left != null)
                queue.add(tmp.left);
            if(tmp.right != null)
                queue.add(tmp.right);
            tmp = queue.poll();
        }
    }

    public int height(BinaryNode<E> t){
        if(t == null)
            return -1;
        return 1 + Math.max(height(t.left), height(t.right));
    }

    public int countNodes(){
        return countNodes(root);
    }

    public int countLeaves(){
        return countLeaves(root);
    }

    public int countFull(){
        return countFull(root);
    }

    private int countNodes(BinaryNode<E> t){
        if(t == null){
            return 0;
        }
        return 1 + countNodes(t.left) + countNodes(t.right);
    }

    private int countLeaves(BinaryNode<E> t){
        if(t == null)
            return 0;
        else if(t.right == null && t.left == null)
            return 1;
        return countLeaves(t.left) + countLeaves(t.right);
    }

    private int countFull(BinaryNode<E> t){
        if(t == null)
            return 0;
        int tIsFull = (t.right != null && t.left != null) ? 1 : 0;
        return tIsFull + countFull(t.left) + countFull(t.right); //include self
    }

    public static void main( String [ ] args )
    {
        BinarySearchTree<Integer> t = new BinarySearchTree<>( );
        final int NUMS = 4000;
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );
        t.insert(3);t.insert(1);t.insert(4);t.insert(6);t.insert(9);t.insert(2);t.insert(5);t.insert(7);
        
        System.out.println(" " + t.countNodes() + " " + t.countLeaves() + " " + t.countFull());
        // for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
        //     t.insert( i );

        // for( int i = 1; i < NUMS; i+= 2 )
        //     t.remove( i );

        // if( NUMS < 40 )
        //     t.printTree( );
        // if( t.findMin( ) != 2 || t.findMax( ) != NUMS - 2 )
        //     System.out.println( "FindMin or FindMax error!" );

        // for( int i = 2; i < NUMS; i+=2 )
        //      if( !t.contains( i ) )
        //          System.out.println( "Find error1!" );

        // for( int i = 1; i < NUMS; i+=2 )
        // {
        //     if( t.contains( i ) )
        //         System.out.println( "Find error2!" );
        // }
    }
}


    // * 二叉树中第K层结点的个数(根位于第0层)
    // public int k_nodes(int k){
    //     if(k < 0)
    //         return 0;
    //     return k_nodes(root, k);
    // }
    // private int k_nodes(BinaryNode<T> root, int k){
    //     if(root == null)
    //         return 0;
    //     if(k == 0)
    //         return 1;//根结点
    //     else
    //         return k_nodes(root.left, k-1) + k_nodes(root.right, k-1);
    // }


    //  * 求解node1 和 node2 的最低公共父结点
    // public BinaryNode<T> commonNode(BinaryNode<T> node1, BinaryNode<T> node2, BinaryNode<T> root){
    //     if(root == null)
    //         return null;
    //     if(node1.element == root.element || node2.element == root.element)
    //         return root;
    //     /*
    //      * 若 left==null, node1,node2 都不在 root.left子树中
    //      * 若right==null,node1,node2 都不在root.right子树中
    //      */
    //     BinaryNode<T> left = commonNode(node1, node2, root.left);
    //     BinaryNode<T> right = commonNode(node1, node2, root.right);
        
    //     if(left != null && right != null)
    //         return root;
    //     return left == null ? right : left;
    // }