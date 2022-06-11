import java.util.*;

public class Q1152{
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        String in = stdIn.nextLine();

        int cnt = 0;
        for (String k : in.split(" ")){
            if (k.equals("")){
                continue;
            } else {
                cnt ++;
            }
        }
        System.out.println(cnt);
    }
}