import java.util.*;
public class Test{
    public static void main(String[] args){
        ArrayList<ArrayList<String>> buckets = new ArrayList<ArrayList<String>>();
        Object[] array = buckets;
        array[0] = new ArrayList<Integer>(Arrays.asList(0,1));
        ArrayList<String> s = buckets[0];
        System.out.println(s.get(0).substring(0, 1));
    }
}