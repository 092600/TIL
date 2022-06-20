import java.util.*;

public class Q2941 {
    public static void main(String[] args){
        Map<String, String> map1 = Map.of(
                "lj", ".",
                "nj", ".",
                "dz=", "."
        );
        Map<String, String> map2 = Map.of(
                "c=",  ".",
                "c-",  ".",
                "d-", ".",
                "s=", ".",
                "z=", "."
        );

        Scanner stdIn = new Scanner(System.in);
        String in = stdIn.nextLine();

        for (String key : map1.keySet()){
            if (in.indexOf(key) != -1){
                in = in.replace(key, map1.get(key));
            }

        }
        for (String key : map2.keySet()){
            if (in.indexOf(key) != -1){
                in = in.replace(key, map2.get(key));
            }

        }
        System.out.println(in.length());
    }
}