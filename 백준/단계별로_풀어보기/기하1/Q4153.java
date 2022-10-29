import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Q4153 {
    public static void main(String[] args) throws IOException {
        Scanner stdIn = new Scanner(System.in);

        int a, b, c;
        int[] arr = new int[3];
        while (true){
            arr[0]=stdIn.nextInt();
            arr[1]=stdIn.nextInt();
            arr[2]=stdIn.nextInt();

            if ((arr[0]==0) && (arr[1]==0) && (arr[2] == 0)){
                break;
            }

            Arrays.sort(arr);
            a = arr[0] * arr[0];
            b = arr[1] * arr[1];
            c = arr[2] * arr[2];

            if (a + b == c){
                System.out.println("right");
            } else {
                System.out.println("wrong");
            }
        }
    }
}
