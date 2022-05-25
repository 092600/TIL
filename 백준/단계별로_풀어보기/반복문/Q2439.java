package 백준.단계별로_풀어보기;

import java.util.*;

public class Q2439 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int a = stdIn.nextInt();
        int k = a;
        for (int i=1; i<=a; i++){
            for (int j = 1; j<=a;j++){
                if (k>j){
                    System.out.print(" ");
                } else {
                    System.out.print("*");
                }
            }
            k--;
            System.out.println();
        }
    }
}