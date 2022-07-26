package 백준.단계별로_풀어보기.기본수학;

import java.util.Scanner;

public class Q2775 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();

        for (int i = 0; i < num; i++) {
            int k = sc.nextInt();
            int n = sc.nextInt();
            System.out.println(solution(k, n));

        }
    }
    private static int solution(int k, int n){
        if (n == 1)
            return 1;
        else if(k==0){
            return n;
        } else {
            return solution(k-1, n) + solution(k, n-1);
        }
    }

}