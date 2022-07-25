package 백준.단계별로_풀어보기.기본수학;

import java.util.Scanner;
class Q2292 {
    public static void main(String[] args){
        int n, tmp;

        Scanner stdIn = new Scanner(System.in);
        int input = stdIn.nextInt();

        if (input == 1){
            System.out.println(1);

        }else {

            n = 0;
            tmp = 1 ;

            while(true){
                if ((((n*6)+2 <= input)) && (input <= (((n+tmp) * 6)+1))){
                    System.out.println(tmp + 1);
                    break;
                } // Inner If

                n += tmp;
                tmp += 1;
            } // while
        } // Outer If

    } // main
}