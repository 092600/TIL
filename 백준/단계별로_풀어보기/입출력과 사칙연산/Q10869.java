package 백준.단계별로_풀어보기;

import java.util.*;

class Q10869 {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        String inp = s.nextLine();
        String[] splitInp = inp.split(" ");
        int a = Integer.parseInt(splitInp[0]);
        int b = Integer.parseInt(splitInp[1]);

        System.out.println(a+b);
        System.out.println(a-b);
        System.out.println(a*b);
        System.out.println(a/b);
        System.out.println(a%b);
    }
}