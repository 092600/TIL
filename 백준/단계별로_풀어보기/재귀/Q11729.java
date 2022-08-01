import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Q11729 {
    static int n;
    static int cnt = 0;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        Q11729(n, 1, 3);
        System.out.println(cnt);
        System.out.println(sb);
    }

    static void Q11729(int n, int from, int to) {
        if(n==0){
            return;
        } else {
            cnt ++;
            Q11729(n-1, from, 6-from-to);
            sb.append(from + " " + to + "\n");
            Q11729(n-1, 6-from-to, to);
        }
    }
    // Q11727(3, 1, 3) > 1. cnt ++ 2. Q11727(2, 1, 3) {1. cnt ++, 2. Q11727(1,1,3,) {1. cnt ++, 2. Q11727(0,1,3) 3. {sb.append(1+" "+3+"\n") } } }
}
