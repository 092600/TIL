import java.util.*;

public class Q3052{
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        HashMap map = new HashMap<Integer, Integer>();
        int k ;

        for (int i = 0;i<10; i++){
            k = stdIn.nextInt() % 42;
            if (map.containsKey(k)){
                map.put(k,(int) map.get(k)+1);
            } else {
                map.put(k,1);
            }
        }

        System.out.println(map.keySet().size());
    }
}