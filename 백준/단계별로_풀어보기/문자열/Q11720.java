import java.util.*;

public class Q11654{
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int n  = stdIn.nextInt();
        int sum = 0 ;

        String lst = stdIn.next();
        for (String k : lst.split("")){
            sum += Integer.parseInt(k);
        }
        System.out.println(sum);
    }
}