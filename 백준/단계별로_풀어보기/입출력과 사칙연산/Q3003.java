import java.util.*;

public class Q3003 {
    public static void main(String[] args){
        Scanner stdin = new Scanner(System.in);
        String inp = stdin.nextLine();
        String[] inpList = inp.split(" ");
        int[] lst = {1, 1, 2, 2, 2, 8};

        for (int i = 0 ; i<lst.length; i++){
            if (lst[i] != Integer.parseInt(inpList[i])){
                System.out.print(lst[i] - Integer.parseInt(inpList[i]) + " ");
            } else {
                System.out.print(0 + " ");
            }
        }
    }
}