import java.util.*;

public class Q10818{
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int n = stdIn.nextInt();
        int max; int min; int k;

        k = stdIn.nextInt();
        max = k; min = k;

        for (int i=0;i<n-1;i++){
            k = stdIn.nextInt();
            if (k > max)
                max = k;
            else if (k < min)
                min = k;
        }

        System.out.println(min+" "+max);
    }
}