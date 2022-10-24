//문제
//N*M크기의 두 행렬 A와 B가 주어졌을 때, 두 행렬을 더하는 프로그램을 작성하시오.
//
//입력
//첫째 줄에 행렬의 크기 N 과 M이 주어진다. 둘째 줄부터 N개의 줄에 행렬 A의 원소 M개가 차례대로 주어진다. 이어서 N개의 줄에 행렬 B의 원소 M개가 차례대로 주어진다. N과 M은 100보다 작거나 같고, 행렬의 원소는 절댓값이 100보다 작거나 같은 정수이다.
//
//출력
//첫째 줄부터 N개의 줄에 행렬 A와 B를 더한 행렬을 출력한다. 행렬의 각 원소는 공백으로 구분한다.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Q2738 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        int[][] arr1 = new int[a][b];
        int[][] arr2 = new int[a][b];
        int[][] arr3 = new int[a][b];

        for (int i=0;i<a;i++){
            st = new StringTokenizer(br.readLine());
            for (int j=0;j<b;j++){
                arr1[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i=0;i<a;i++){
            st = new StringTokenizer(br.readLine());
            for (int j=0;j<b;j++){
                arr2[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i=0;i<a;i++){
            for (int j=0;j<b;j++){
                arr3[i][j] = arr1[i][j] + arr2[i][j];
            }
            for (int l=0;l<b;l++){
                System.out.print(arr3[i][l]+" ");
            }
            System.out.println();
        }
    }
}
