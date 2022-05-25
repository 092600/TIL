package 백준.단계별로_풀어보기;

import java.util.*;

public class Q2438 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int a = stdIn.nextInt();

        for (int i=1; i<=a; i++){
            for (int j=1; j<=i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }

    }
}