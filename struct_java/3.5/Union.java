import java.util.*;
public class Union{
    public static <E extends Comparable<? super E>> List<E> union( List<E> listOne, List<E> listTwo ){
        Iterator<E> iterOne = listOne.iterator();
        Iterator<E> iterTwo = listTwo.iterator();
        List<E> result = new ArrayList<>();
        E tmp1 = null;
        E tmp2 = null;
        if(iterOne.hasNext() && iterTwo.hasNext()){
            tmp1 = iterOne.next();
            tmp2 = iterTwo.next();
        }

        while(tmp1 != null && tmp2 != null){ 
            int flag = tmp1.compareTo(tmp2);
            if(flag > 0){
                result.add(tmp2);
                tmp2 = iterTwo.hasNext() ? iterTwo.next() : null;
            }
            else if(flag < 0){
                result.add(tmp1);
                tmp1 = iterOne.hasNext() ? iterOne.next() : null;
            }
            else{
                result.add(tmp1);
                result.add(tmp2);
                tmp1 = iterOne.hasNext() ? iterOne.next() : null;
                tmp2 = iterTwo.hasNext() ? iterTwo.next() : null;
            }
        }

        if(tmp1 != null){
            result.add(tmp1);
            while(iterOne.hasNext())
                result.add(tmp1);
        }

        if(tmp2 != null){
            result.add(tmp2);
            while(iterTwo.hasNext())
                result.add(tmp2);
        }

        return result;
    }

    public static void main(String[] args){
        List<Integer> list =union(Arrays.asList(1,3), Arrays.asList(2,4));
        for(Integer i:list){
            System.out.println(i);
        }
    }
}

