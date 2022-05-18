import java.util.*;

public class Q14681{
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int d1 = stdIn.nextInt();
        int d2 = stdIn.nextInt();
        int d3 = stdIn.nextInt();

        int max = (d1 > d2) ? d1 : d2;
        max = (max > d3) ? max : d3;

        if ((d1 == d2) && (d1 == d3)){
            System.out.println(d1 * 1000 + 10000);
        } else if ((d1 == d2) || (d2 == d3) || (d1 == d3)){
            if (d1 == d2)
                System.out.println(d1 * 100 + 1000);
            else if (d1 == d3)
                System.out.println(d1 * 100);
            else
                System.out.println(d2 * 100);
        } else {
            System.out.println(max * 100);
        }
    }
}