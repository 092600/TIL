package 백준.단계별로_풀어보기.기본수학;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Q10250 {
    public static void main(String[] args){
        Scanner stdIn =  new Scanner(System.in);

        int cnt, H, W, N;
        cnt = stdIn.nextInt();

        for(int i = 0; i < cnt; i++) {;
            H = stdIn.nextInt();
            W = stdIn.nextInt();
            N = stdIn.nextInt();

            if(N % H == 0) {
                System.out.println((H * 100) + (N / H));

            } else {
                System.out.println(((N % H) * 100) + ((N / H) + 1));
            }
        }

    }
}
