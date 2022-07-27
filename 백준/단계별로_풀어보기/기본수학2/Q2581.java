package 백준.단계별로_풀어보기.기본수학2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Q2581 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int min = Integer.parseInt(br.readLine());
        int max = Integer.parseInt(br.readLine());


        int minDecimal = max; int sum = 0;

        for (int i=min; i<=max;i++){
            boolean isDecimal = true;

            if(i==1) isDecimal = false;

            ForIsDecimal:
            for (int j=2;j<i;j++){
                if (i % j == 0){
                    isDecimal = false;
                    break ForIsDecimal;
                }
            }

            if (isDecimal){
                if (minDecimal > i)
                    minDecimal = i;
                sum += i;
            }
        }
        if (sum==0){
            System.out.println(-1);
        } else {
            System.out.println(sum);
            System.out.println(minDecimal);
        }
    }
}
