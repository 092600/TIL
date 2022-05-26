import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Q10952 {
    public static void main(String[] args) throws Exception {
        Scanner stdIn = new Scanner(System.in);
        int a; int b;

        List arr = new ArrayList<Integer>();

        do {
            a = stdIn.nextInt();
            b = stdIn.nextInt();
            arr.add(a+b);
        } while ((a != 0) && (b != 0));

        for (int i=0;i<arr.size();i++){
            if ((int) arr.get(i) != 0){
                System.out.println(arr.get(i));
            }
        }
    }
}