import java.util.*;

public class Q14681{
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int h = stdIn.nextInt();
        int m = stdIn.nextInt();

        if (m >= 45){
            m -= 45;
        } else {
            if (h == 0){
                h = 23;
                m += 15;
            } else {
                h -= 1;
                m += 15;
            }
        }
        System.out.println(h + " "+ m);
    }
}