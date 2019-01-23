public class HeapSort{
    private static int leftChild(int i){
        return 2 * i + 1;
    }

    private static <E extends Comparable<? super E>>
    void percDown(E[] a, int i, int n){
        int child;
        E tmp;

        for(tmp = a[i]; leftChild(i) < n; i = child){
            child = leftChild(i);
            if(child != n -1 && a[child].compareTo(a[child + 1]) < 0)
                child++;
            if(tmp.compareTo(a[child]) < 0)
                a[i] = a[child];
            else
                break;
        }
        a[i] = tmp;
    }

    public static <E extends Comparable<? super E>>
    void heapSort(E[] a){
        for(int i = a.length / 2 - 1; i >= 0; i--){
            percDown(a, i ,a.length);
        }

        for(int i = a.length - 1; i > 0 ; i--){
            swapReferences(a, 0 ,i);
            percDown(a, 0, i);
        }
    }

    public static <E> void swapReferences(E[] a, int t1, int t2 )
    {
        E tmp = a[t1];
        a[t1] = a[t2];
        a[t2] = tmp;
    }
}