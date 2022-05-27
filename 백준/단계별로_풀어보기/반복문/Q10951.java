import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Q10951 {
    public static void main(String[] args) throws Exception {
        Scanner stdIn = new Scanner(System.in);

        while(stdIn.hasNextInt()){
            int a = stdIn.nextInt();
            int b = stdIn.nextInt();
            System.out.println( a + b);
        }
        stdIn.close();
    }
}