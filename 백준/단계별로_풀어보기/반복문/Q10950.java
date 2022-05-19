package 백준.단계별로_풀어보기;

import java.util.*;

public class Q10950 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);


        int a; int b;
        int n = stdIn.nextInt();

        int[] lst = new int[n];

        for (int i=0;i<n;i++) {
            a = stdIn.nextInt();
            b = stdIn.nextInt();

            lst[i] = a+b;
        }

        for (int i : lst ){
            System.out.println(i);
        }

    }
}