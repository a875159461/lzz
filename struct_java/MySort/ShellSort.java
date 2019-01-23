public class ShellSort{
    public static <E extends Comparable<? super E>>
    void shellSort(E[] a){
        int j ;
        for(int gap = a.length / 2; gap > 0; gap /= 2){

            for(int i = gap; i < a.length; i++){
                E tmp = a[i];
                for(j = i; j >= gap && tmp.compareTo(a[j - gap]) < 0; j -= gap)
                    a[j] = a[j - gap];
                a[j] = tmp;
            }
        }
    }
}