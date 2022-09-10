// 문제
// N개의 수가 주어졌을 때, 이를 오름차순으로 정렬하는 프로그램을 작성하시오.
//
// 입력
// 첫째 줄에 수의 개수 N(1 ≤ N ≤ 1,000)이 주어진다. 둘째 줄부터 N개의 줄에는 수 주어진다. 이 수는 절댓값이 1,000보다 작거나 같은 정수이다. 수는 중복되지 않는다.
//
// 출력
// 첫째 줄부터 N개의 줄에 오름차순으로 정렬한 결과를 한 줄에 하나씩 출력한다.

import java.util.*;

// 삽입 정렬
public class Q2750 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);

        int n, tmp;
        n = stdIn.nextInt();
        int[] lst = new int[n];

        for (int i=0; i<n;i++){
            lst[i] = stdIn.nextInt();
        }

        for (int i=0; i<n; i++){
            for (int j=i+1; j<n;j++){
                if (lst[i] > lst[j]){
                    tmp = lst[i];
                    lst[i] = lst[j];
                    lst[j] = tmp;
                }
            }
        }


        for (Integer i : lst){
            System.out.println(i);
        }

    }
}
