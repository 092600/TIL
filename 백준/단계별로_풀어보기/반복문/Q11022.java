package 백준.단계별로_풀어보기;

import java.util.*;

public class Q25304 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int n = stdIn.nextInt();
        String[] lst = new String[n];
        int a; int b;

        for (int i=0;i<n;i++){
            a = stdIn.nextInt();
            b = stdIn.nextInt();

            lst[i] = "Case #"+(i+1)+": "+a+" + "+b+" = "+(int) (a+b);
        }

        for(int i=0;i<n;i++){
            System.out.println(lst[i]);
        }
    }
}