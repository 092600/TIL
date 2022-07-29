package 백준.단계별로_풀어보기.기본수학2;

import java.util.*;

public class Q4948 {

    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);

        int in, in2, k, j, cnt;
        cnt = k = 0;


        // boolean set
        boolean[] prime = new boolean[246913];
        List list = new ArrayList();
        // 소수가 아님 : true, 소수임 : false

        prime[0] = prime[1] = true;
        while (true) {
            in = stdIn.nextInt();
            in2 = in * 2;
            if (in == 0){
                break;
            } else {
                for (int i = 2; i <= in2; i++) {
                    for (j = i * 2; j <= in2; j += i) {
                        prime[j] = true;
                    }
                }

                for (int i = in + 1; i <= in2; i++) {
                    if (prime[i] == false) {
                        cnt++;
                    }
                }
                list.add(cnt);
                cnt = 0;
            }
        }

//        System.out.println(Arrays.toString(prime));
        for (int i=0;i<list.size(); i++){
            System.out.println(list.get(i));
        }
    }
}