public class MergeSort{
    public static <E extends Comparable<? super E>>
    void mergeSort(E[] a){
        E[] tmpArray = (E[]) new Comparable[a.length];
        mergeSort(a, tmpArray, 0, a.length -1);
    }

    private static <E extends Comparable<? super E>>
    void mergeSort(E[] a, E[] tmpArray, int left, int right){
        if(left < right){
            int mid = (left + right) / 2;
            mergeSort(a, tmpArray, left, mid);
            mergeSort(a, tmpArray, mid + 1, right);
            merge(a, tmpArray, left, mid + 1, right);
        }
    }

    private static <E extends Comparable<? super E>>
    void merge(E[] a, E[] tmpArray, int left, int mid, int right){
        int i = left;
        int j = mid;
        int k = left;
        while(i < mid && j <= right){
            if(a[i].compareTo(a[j]) < 0){
                tmpArray[k] = a[i];
                k++;
                i++;
            }
            else{
                tmpArray[k] = a[j];
                k++;
                j++;
            }
        }
        while(i < mid){
            tmpArray[k] = a[i];
            k++;
            i++;
        }
        while(j <= right){
            tmpArray[k] = a[j];
            k++;
            j++;
        }

        for(i = left; i <= right; i++){
            a[i] = tmpArray[i];
        }
    }
}