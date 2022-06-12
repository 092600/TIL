import java.util.*;

public class Q1152{
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int a = stdIn.nextInt();
        int b = stdIn.nextInt();

        String tmp_a = String.valueOf(a);
        String tmp_b = String.valueOf(b);

        String rst_a = "";
        String rst_b = "";


        for (String k : tmp_a.split("")){
            rst_a= k+rst_a;
        }

        for (String k : tmp_b.split("")){
            rst_b= k+rst_b;
        }

        if ( Integer.valueOf(rst_a) > Integer.valueOf(rst_b)) {
            System.out.println(Integer.valueOf(rst_a));
        } else {
            System.out.println(Integer.valueOf(rst_b));
        }
    }
}