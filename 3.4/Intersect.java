import java.util.*;
public class Intersect{
    public static <E extends Comparable<? super E>> List<E> intersect( List<E> listOne, List<E> listTwo ){
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
                tmp2 = iterTwo.hasNext() ? iterTwo.next() : null;
            }
            else if(flag < 0){
                tmp1 = iterOne.hasNext() ? iterOne.next() : null;
            }
            else{
                result.add(tmp1);
                tmp1 = iterOne.hasNext() ? iterOne.next() : null;
                tmp2 = iterTwo.hasNext() ? iterTwo.next() : null;
            }
        }
        return result;
    }
}