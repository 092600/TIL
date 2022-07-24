package 백준.단계별로_풀어보기;

import java.util.Scanner;

public class Q1193 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int num = stdIn.nextInt();

        int line, tmp, topNum, bottomNum, diff;
        line = tmp = 0;


        while (!(num <= line) && (line-tmp) <= num){
            tmp += 1;
            line += tmp;
        }
        diff = line - num;
        
        if (tmp%2 == 0){
            
            topNum = tmp-diff;
            bottomNum = 1+diff;
        } else {
            bottomNum = tmp - diff;
            topNum = 1 + diff;
        }
        System.out.println(topNum +"/"+bottomNum);
    }
}
