import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Q1110 {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);

        int N = in.nextInt();
        in.close();

        int cnt = 0;
        int copy = N;

        while (true) {
            N = ((N % 10) * 10) + (((N / 10) + (N % 10)) % 10);
            cnt++;

            if (copy == N) {
                break;
            }
        }
        System.out.println(cnt);
    }
}