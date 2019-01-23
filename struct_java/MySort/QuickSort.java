public class quickSort{
    private static final int CUTOFF = 10;

    public static <E extends Comparable<? super E>>
    void quickSort(E[] a){
        quickSort(a, 0, a.length - 1);
    }

    
    public static <E extends Comparable<? super E>>
    void quickSelect(E [] a, int k)
    {
        quickSelect(a, 0, a.length - 1, k);
    }

    private static <E extends Comparable<? super E>>
    void quickSort(E [] a, int left, int right)
    {
        if(left + CUTOFF <= right)
        {
            E pivot = median3(a, left, right);

                // Begin partitioning
            int i = left, j = right - 1;
            for(; ;){
                while(a[++i].compareTo(pivot) < 0)
                    ;
                while(a[--j].compareTo(pivot) > 0)
                    ;
                if(i < j)
                    swapReferences(a, i, j);
                else
                    break;
            }

            swapReferences(a, i, right - 1);   // Restore pivot

            quickSort(a, left, i - 1);    // Sort small elements
            quickSort(a, i + 1, right);   // Sort large elements
        }
        else  // Do an insertion sort on the subarray
            insertionSort(a, left, right);
    }

    private static <E extends Comparable<? super E>>
    void quickSelect(E[] a, int left, int right, int k)
    {
        if(left + CUTOFF <= right)
        {
            E pivot = median3(a, left, right);
                // Begin partitioning
            int i = left, j = right - 1;
            for(; ;){
                while(a[++i].compareTo(pivot) < 0)
                    ;
                while(a[--j].compareTo(pivot) > 0)
                    ;
                if(i < j)
                    swapReferences(a, i, j);
                else
                    break;
            }
            swapReferences(a, i, right - 1);   // Restore pivot

            if(k <= i)
                quickSelect(a, left, i - 1, k);
            else if(k > i + 1)
                quickSelect(a, i + 1, right, k);
        }
        else  // Do an insertion sort on the subarray
            insertionSort(a, left, right);
    }

    private static <E extends Comparable<? super E>>
    void insertionSort(E [] a, int left, int right)
    {
        for(int p = left + 1; p <= right; p++)
        {
            E tmp = a[p];
            int j;
            for(j = p; j > left && tmp.compareTo(a[j - 1]) < 0; j--)
                a[j] = a[j - 1];
            a[j] = tmp;
        }
    }

    private static <E extends Comparable<? super E>>
    E median3(E[] a, int left, int right){
        int mid = (left + right) / 2;
        if(a[mid].compareTo(a[left]) < 0)
            swapReferences(a, left, mid);
        if(a[right].compareTo(a[left]) < 0)
            swapReferences(a, left, right);
        if(a[right].compareTo(a[mid]) < 0)
            swapReferences(a, mid, right);
        // Place pivot at position right - 1
        swapReferences(a, mid, right - 1);
        return a[right - 1];
    }

    private static <E> void swapReferences(E[] a, int t1, int t2)
    {
        E tmp = a[t1];
        a[t1] = a[t2];
        a[t2] = tmp;
    }
}