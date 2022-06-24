import java.util.*;

public class Q1065 {
   public static void main(String[] args){
      Scanner stdIn = new Scanner(System.in);
      int n = stdIn.nextInt();
      int cnt;
      if (n < 100){
         cnt = n;

         System.out.println(cnt);
      } else if (n <= 1000){
         cnt = 99;
         for (int i=100;i<=n;i++){
            String iStr = String.valueOf(i);
            String[] iStrList = iStr.split("");
            if (Integer.parseInt(iStrList[2]) - Integer.parseInt(iStrList[1])
                   == Integer.parseInt(iStrList[1]) - Integer.parseInt(iStrList[0])){
               cnt += 1;
            }
         }
         System.out.println(cnt);

      }
  }
}