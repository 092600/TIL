import java.util.Scanner;

public class Q1546{
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);

        int k; int n; int max; double sum = 0;
        k = stdIn.nextInt();

        int[] lst = new int[k];
        n = stdIn.nextInt();

        lst[0] = max = n;


        for (int i=1;i<k;i++){
            n = stdIn.nextInt();
            lst[i] = n;

            if (n > max)
                max = n;
        }

        for (int l : lst){
            sum += ((double) l / max ) * 100;
        }
        System.out.println((sum / k) * 100 / 100);
    }
}
