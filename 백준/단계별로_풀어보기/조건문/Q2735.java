import java.util.*;

public class Q9498{
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int year = stdIn.nextInt();

        if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)){
            System.out.println(1);
        } else {
            System.out.println(0);
        }

    }
}