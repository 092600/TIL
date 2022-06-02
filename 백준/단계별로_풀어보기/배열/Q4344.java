import java.util.*;

public class Q4344{
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);

        int[] arr;
        int testSet = stdIn.nextInt();

        stdIn.nextLine();

        for (int i = 0; i<testSet; i++){
            int n = stdIn.nextInt();
            arr = new int[n];

            double sum = 0;

            for (int j=0; j<n; j++){
                int score = stdIn.nextInt();
                arr[j] = score;
                sum += score;
            }

            double avg = (sum / n);
            double cnt = 0;

            for (int j=0; j<n;j++){
                if (arr[j] > avg){
                    cnt ++;
                }
            }
            System.out.printf("%.3f%%\n",(cnt/n)*100);

        }

        stdIn.close();
    }
}