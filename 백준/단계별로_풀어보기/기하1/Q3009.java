//문제
//세 점이 주어졌을 때, 축에 평행한 직사각형을 만들기 위해서 필요한 네 번째 점을 찾는 프로그램을 작성하시오.
//
//입력
//세 점의 좌표가 한 줄에 하나씩 주어진다. 좌표는 1보다 크거나 같고, 1000보다 작거나 같은 정수이다.
//
//출력
//직사각형의 네 번째 점의 좌표를 출력한다.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Q3009 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        Map<Integer, Integer> xPosition = new HashMap<Integer, Integer>();
        Map<Integer, Integer> yPosition = new HashMap<Integer, Integer>();

        String[] tmp ;
        for (int i=0;i<3;i++){
            tmp = br.readLine().split(" ");
            xPosition.put(Integer.parseInt(tmp[0]),
                    xPosition.getOrDefault(Integer.parseInt(tmp[0]), 0)+1);
            yPosition.put(Integer.parseInt(tmp[1]),
                    yPosition.getOrDefault(Integer.parseInt(tmp[1]), 0)+1);
        }

        for (Integer x : xPosition.keySet()){
            if (xPosition.get(x) == 1){
                sb.append(x + " ");
            }
        }

        for (Integer y : yPosition.keySet()){
            if (yPosition.get(y) == 1){
                sb.append(y + " ");
            }
        }

        System.out.println(sb);
    }
}
