package 백준.단계별로_풀어보기;

import java.util.*;

public class Q2739 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int a = stdIn.nextInt();

        for (int i = 1; i<=9; i++){
            System.out.println(a+" * "+i+" = "+a*i);
        }
    }
}