import java.util.*;
public class BinaryTree<E>{
    private BinaryNode<E> root;

    public BinaryTree(){
        root = null;
    }

    
    public void makeEmpty(){
        root = null;
    }

    public void isEmpty(){
        return root = null;
    }

    private static class BinaryNode<E>{
        public BinaryNode<E> left;
        public BinaryNode<E> right;
        public E element;

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
            throw new UnderflowException();
        }
        return findMin(root).data;
    }

    public E findMax(){
        if(isEmpty()){
            throw new UnderflowException();
        }
        return findMax(root).data;
    }

    public void insert(E value){
        root = insert(value, root);
    }

    public void remove(E value){
        root = remove(value, root);
    }
    
    public void printTree(){

    }

    private boolean contains(E value, BinaryNode<E> t){

    }

    private BinaryNode<E> findMax(BinaryNode<E> t){

    }

    private BinaryNode<E> findMin(BinaryNode<E> t){
        
    }

    private BinaryNode<E> insert(E value, BinaryNode<E> t){

    }

    private BinaryNode<E> remove(E value, BinaryNode<E> t){

    }

    private void printTree(BinaryNode<E> t){

    }
}