import java.util.*;

public class Q10809 {
    public static void main(String[] args) throws Exception {
        Scanner stdIn = new Scanner(System.in);
        String in = stdIn.nextLine();
        String[] lst = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p"
                ,"q","r","s","t","u","v","w","x","y","z"};

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (String l : lst){
            map.put(l, -1);
        }

        for (int i=0; i<in.split("").length; i++){
            if (map.get(in.split("")[i]) == -1){
                map.put(in.split("")[i], i);
            } else {
                continue;
            }
        }
        for (String m : map.keySet()){
            System.out.print(map.get(m)+" ");
        }
    }
}