import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Q10871 {
    public static void main(String[] args) throws Exception {
        Scanner stdIn = new Scanner(System.in);
        int tmp;
        int k = 0;
        int n = stdIn.nextInt();
        int num = stdIn.nextInt();

        int[] lst = new int[n];

        for (int i=0;i<n;i++){
            tmp = stdIn.nextInt();
            if (num > tmp){
                lst[k] = tmp;
                k++;
            }
        }

        for (int l : lst){
            if (l!=0){
                System.out.print(l+" ");
            }
        }

    }
}