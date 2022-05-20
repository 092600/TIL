import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Q25304 {
    public static void main(String[] args) throws Exception {
        Scanner stdIn = new Scanner(System.in);
        int result = stdIn.nextInt();
        int cnt = stdIn.nextInt();

        int sum = 0;

        int product; int amount;

        for (int i=0; i<cnt; i++){
            product = stdIn.nextInt();
            amount = stdIn.nextInt();

            sum += product * amount;
        }

        if (result == sum)
            System.out.println("Yes");
        else
            System.out.println("No");
    }
}