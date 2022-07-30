import java.util.*;

public class Q10872{
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int n = stdIn.nextInt();

        System.out.println(factorial(n));
    }

    public static int factorial(int n){
        if ((n == 1) || (n == 0)){
            return 1;
        } else {
            return n * factorial(n-1);
        }
    }
}