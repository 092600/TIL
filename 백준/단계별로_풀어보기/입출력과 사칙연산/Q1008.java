package 백준.단계별로_풀어보기;

import java.util.*;

public class Q1001 {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        String inp = s.nextLine();
        String[] splitInp = inp.split(" ");
        double a = Integer.parseInt(splitInp[0]);
        double b = Integer.parseInt(splitInp[1]);

        System.out.println(a/b);
    }
}