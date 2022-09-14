//문제
//수직선 위에 N개의 좌표 X1, X2, ..., XN이 있다. 이 좌표에 좌표 압축을 적용하려고 한다.
//
//Xi를 좌표 압축한 결과 X'i의 값은 Xi > Xj를 만족하는 서로 다른 좌표의 개수와 같아야 한다.
//
//X1, X2, ..., XN에 좌표 압축을 적용한 결과 X'1, X'2, ..., X'N를 출력해보자.
//
//입력
//첫째 줄에 N이 주어진다.
//
//둘째 줄에는 공백 한 칸으로 구분된 X1, X2, ..., XN이 주어진다.
//
//출력
//첫째 줄에 X'1, X'2, ..., X'N을 공백 한 칸으로 구분해서 출력한다.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Q18870 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(br.readLine());
        int[] inputs = new int[n];
        int[] tmpList = new int[n];

        String[] inputString = br.readLine().split(" ");

        for (int i=0;i<n;i++){
            inputs[i] = Integer.parseInt(inputString[i]);
            tmpList[i] = Integer.parseInt(inputString[i]);
        }

        Arrays.sort(tmpList);

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int cnt = 0;

        for (Integer i : tmpList){
            if (!map.containsKey(i)){
                map.put(i, cnt);
                cnt += 1;
            }
        }

        for (Integer i : inputs){
            sb.append(map.get(i)+" ");
        }
        System.out.println(sb);


    }
}
