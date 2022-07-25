import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Q2869{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int A = Integer.parseInt(st.nextToken()); // 낮에 올라가는 높이
        int B = Integer.parseInt(st.nextToken()); // 밤에 미끄러지는 높이
        int V = Integer.parseInt(st.nextToken()); // 정상 높이

        // -- Path 1 --
        int day = (V - A) / (A - B);
        double N =  (double) (V - A) % (A - B);

        if (N == 0){
            System.out.println(day+1);
        } else {
            System.out.println(day+2);
        } // if

        // -- Path 1 End --

        // -- Path 2 --
        // int day = (int) Math.ceil((double)(V - A) / (A - B)) + 1;

        // System.out.println(day);

        // -- Path 2 End --

    } // main
}

//import java.util.Scanner;
//        import java.util.Arrays;
//
//public class Q2869UseWhile{
//    public static void main(String[] args){
//        Scanner stdIn = new Scanner(System.in);
//        String[] inputList = stdIn.nextLine().split(" ");
//
//        int A = Integer.parseInt(inputList[0]);
//        int B = Integer.parseInt(inputList[1]);
//        int V = Integer.parseInt(inputList[2]);
//
//        int day = 1;
//
//
//
//        while (true){
//            V -= A;
//
//            if (V <= 0){
//                System.out.println(day);
//                break;
//            } else {
//                V += B;
//                day += 1;
//            }// if
//
//        }// while
//
//    }
//}