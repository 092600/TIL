package 백준.단계별로_풀어보기;

import java.util.*;

public class Q8393 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int a = stdIn.nextInt();
        int sum = 0;
        for (int i=1;i<=a;i++){
            sum += i;
        }
        System.out.println(sum);
    }
}