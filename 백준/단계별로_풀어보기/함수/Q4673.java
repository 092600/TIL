import java.util.*;

public class Q4673 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        List lst = new ArrayList<Integer>();
        for (int i = 1; i<=10000; i++){
            String tmpStr = String.valueOf(i);
            int tmp = i;

            for (String t : tmpStr.split("")){
                tmp += Integer.parseInt(t);
            }

            lst.add(tmp);
        }

        for (int i=1;i<=10000; i++){
            if (!(lst.contains(i))){
                System.out.println(i);
            }
        }

    }
}

