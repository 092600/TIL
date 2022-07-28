package 백준.단계별로_풀어보기.기본수학2;

import java.util.*;

public class Q11653 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);

        int in = stdIn.nextInt();
        int dividedNum = 2;

        List list = new ArrayList();

        while (in != 1){
             if (in % dividedNum == 0){
                 list.add(dividedNum);
                 in /= dividedNum;
             } else {
                 dividedNum += 1;
             }
        }

        for (int i=0; i<list.size(); i++){
            System.out.println(list.get(i));
        }
    }
}
