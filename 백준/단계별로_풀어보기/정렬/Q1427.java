//문제
//배열을 정렬하는 것은 쉽다. 수가 주어지면, 그 수의 각 자리수를 내림차순으로 정렬해보자.
//
//입력
//첫째 줄에 정렬하려고 하는 수 N이 주어진다. N은 1,000,000,000보다 작거나 같은 자연수이다.
//
//출력
//첫째 줄에 자리수를 내림차순으로 정렬한 수를 출력한다.

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Q1427 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        String[] input = stdIn.nextLine().split("");

        List<Integer> lst = new ArrayList<Integer>();

        for (String i : input){
            lst.add(Integer.parseInt(i));
        }

        Collections.sort(lst, Collections.reverseOrder());


        for (Integer i : lst){
            System.out.print(i);
        }
    }
}
