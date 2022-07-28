package 백준.단계별로_풀어보기.기본수학2;

import java.util.*;

public class Q1929 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);

        int in1 = stdIn.nextInt();
        int in2 = stdIn.nextInt();

        int divideCnt = 0;

        // in1 ~ in2 > i
        for (int i=in1; i<=in2; i++) {
            for (int j=2; j<=Math.sqrt(i);j++){
                if (i%j == 0) {
                    divideCnt += 1;
                    break;
                }
            }
            if (divideCnt == 0){
                if (i==1){
                    continue;
                } else {
                    System.out.println(i);
                }
            } else {
                divideCnt = 0;
            }

        }
    }
}