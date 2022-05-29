import java.util.*;

public class Q2562{
    public static void main(String[] args) throws Exception {
        Scanner stdIn = new Scanner(System.in);
        List lst = new ArrayList<Integer>() ;
        int k; int max;

        k = stdIn.nextInt();
        lst.add(k);
        max = k ;

        for (int i = 1; i < 9; i++) {
            k = stdIn.nextInt();
            lst.add(k);

            if (k > max)
                max = k;
        }

        System.out.println(max);
        System.out.println(lst.indexOf(max)+1);

    }
}