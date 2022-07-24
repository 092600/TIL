package 백준.단계별로_풀어보기.기본수학;

import java.util.Scanner;

public class Q1712 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        String[] input = stdIn.nextLine().split(" ");
        
        int a = Integer.parseInt(input[0]);
        int b = Integer.parseInt(input[1]);
        int c = Integer.parseInt(input[2]);

        if (c > b){
            int BreakEvenPoint = (a / (c-b)) + 1;
            System.out.println(BreakEvenPoint);
        } else {
            System.out.println(-1);
        }
    }
}
