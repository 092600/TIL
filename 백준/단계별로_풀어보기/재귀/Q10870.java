import java.util.*;

public class Q10870 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int n = stdIn.nextInt();

        System.out.println(Q10870(n));
    }
    public static int Q10870(int n){
        if (n <= 1){
            return n;
        } else {
            return Q10870(n-1) + Q10870(n-2);
        }
    }
}