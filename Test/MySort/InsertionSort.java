public class InsertionSort{
    public static <E extends Comparable<? super E>>
    void insertionSort(E[] a){
        int j;

        for(int p = 1; p < a.length; p++ )
        {
            E tmp = a[ p ];
            for(j = p; j > 0 && tmp.compareTo( a[j - 1 ]) < 0; j--)
                a[j] = a[j - 1];
            a[j] = tmp;
        }
    }
}