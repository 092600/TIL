import java.util.*;

public class Q14681{
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int h = stdIn.nextInt();
        int m = stdIn.nextInt();
        int plus = stdIn.nextInt();

        int plus_h = plus/60;
        int plus_m = plus%60;

        h += plus_h;
        m += plus_m;

        if (m >= 60){
            h += m / 60;
            m = m % 60;
        }

        h %= 24;
        System.out.println(h+" "+m);
    }
}