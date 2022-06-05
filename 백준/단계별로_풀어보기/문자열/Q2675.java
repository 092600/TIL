import java.util.*;

public class Q2675 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int n  = stdIn.nextInt();

        stdIn.nextLine();

        for (int i=0; i<n;i++){
            String line = stdIn.nextLine();

            for (int k = 0;k<line.split(" ")[1].split("").length; k++){
                for (int j=0;j<Integer.parseInt(line.split(" ")[0]); j++){
                    System.out.print(line.split(" ")[1].split("")[k]);
                }
            }
            System.out.println();
        }
    }
}