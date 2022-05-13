import java.util.*;

public class Q2588 {
    public static void main(String[] args){
        Scanner stdin = new Scanner(System.in);
        int A = stdin.nextInt();
        String B = stdin.next();

        stdin.close();

        System.out.println(A * (B.charAt(2) - '0'));
        System.out.println(A * (B.charAt(1) - '0'));
        System.out.println(A * (B.charAt(0) - '0'));
        System.out.println(A * Integer.parseInt(B));
    }
}