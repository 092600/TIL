package 백준.단계별로_풀어보기.기본수학;

import java.util.Scanner;

public class Q2839 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int sugar = stdIn.nextInt();
        int sugarD3 = 0;

        if (sugar%5 == 0){
            System.out.println(sugar/5);
        } else {
            while (sugar >= 0){
                sugar -= 3;
                sugarD3 += 1;

                if (sugar % 5 == 0) {
                    System.out.println(sugarD3 + sugar / 5);
                    break;
                } else if (sugar == 0){
                    System.out.println(sugarD3);
                    break;
                }
            }

            if (sugar < 0){
                System.out.println(-1);
            }
        }

    }
}
