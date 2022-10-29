//문제
//한수는 지금 (x, y)에 있다. 직사각형은 각 변이 좌표축에 평행하고,
// 왼쪽 아래 꼭짓점은 (0, 0), 오른쪽 위 꼭짓점은 (w, h)에 있다.
// 직사각형의 경계선까지 가는 거리의 최솟값을 구하는 프로그램을 작성하시오.
//
//입력
//첫째 줄에 x, y, w, h가 주어진다.
//
//출력
//첫째 줄에 문제의 정답을 출력한다.

import java.util.Scanner;

public class Q1004 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);

        int n =stdIn.nextInt();

        int x1, y1, x2, y2; int cnt;
        boolean lineInC1;
        boolean lineInC2;
        int[] result = new int[n];

        for (int i=0;i<n;i++){
            x1 = stdIn.nextInt(); y1 = stdIn.nextInt();
            x2 = stdIn.nextInt(); y2 = stdIn.nextInt();
            cnt = 0;

            int t = stdIn.nextInt();
            for (int j=0;j<t;j++){
                lineInC1 = false; lineInC2 = false;

                int a = stdIn.nextInt();
                int b = stdIn.nextInt();
                int r = stdIn.nextInt();

                if (Math.pow(x1-a, 2) + Math.pow(y1-b, 2) > Math.pow(r,2)){
                    lineInC1 = true;
                }

                if (Math.pow(x2-a, 2) + Math.pow(y2-b, 2) > Math.pow(r,2)){
                    lineInC2 = true;
                }

                if (lineInC1 & lineInC2){
                    continue;
                } else if (lineInC1 ^ lineInC2){
                    cnt++;
                }

            }

            result[i] = cnt;
        }

        for (Integer r : result){
         System.out.println(r);
        }
    }
}
