package 백준.단계별로_풀어보기.기본수학2;

import java.util.Arrays;
import java.util.Scanner;

public class Q1978 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int num = stdIn.nextInt();

        stdIn.nextLine();

        String[] nums;
        nums = stdIn.nextLine().split(" ");
        int cnt = 0;
        int decimalCnt = 0;

        int test ;
        for (int i=0;i<num;i++){
            test = Integer.parseInt(nums[i]);
            if (test > 1){
                for (int j=2;j<test;j++){
                    if (test % j == 0){
                        cnt ++;
                    }
                }
                if (cnt == 0){
                    decimalCnt ++;

                }
                cnt = 0;
            }
        }
        System.out.println(decimalCnt);
    }
}
