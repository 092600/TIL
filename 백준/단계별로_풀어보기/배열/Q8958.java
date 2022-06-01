import java.util.*;

public class Q8958 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int n = stdIn.nextInt();
        int[] lst = new int[n];
        int sum = 0;
        String ks;
        stdIn.nextLine();

        for (int i = 0; i<n; i++){
            ks = stdIn.nextLine();

            for (String l : ks.split("X")){
                if (l.equals("")){
                    continue;
                } else {
                    sum += l.length() * (1+l.length()) / 2;
                }
            }
            lst[i] = sum;
            sum = 0;
        }

        for (int l : lst){
            System.out.println(l);
        }

    }
}