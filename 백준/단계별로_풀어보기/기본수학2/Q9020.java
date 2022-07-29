import java.util.*;

public class Q9020{
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);


        int testSet = stdIn.nextInt();
        int n, k1, k2;
        k1 = k2 = 0;
        boolean[] prime = new boolean[10000];
        List list = new ArrayList();
        prime[0] = prime[1] = true;

        for (int i=2;i<5000;i++){
            for (int j=i*2;j<=prime.length-1;j+=i){
                prime[j] = true;
            }
        }

//        System.out.println(Arrays.toString(prime));
        for (int i=0;i<testSet;i++){
            n = stdIn.nextInt();
            k1 = k2 = n/2;
            for (int j=0; j<=(n)/2;j++){
                if ((prime[k1] == false) && (prime[k2] == false)){
                    list.add(k1+" "+k2);
                    break;
                } else {
                    k1 --; k2++;
                }
            }
        }
        for (int i=0;i<list.size(); i++){
            System.out.println(list.get(i));
        }
    }
}