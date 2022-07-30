import java.math.BigInteger;
import java.util.*;

public class Q10757 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        String n = stdIn.nextLine();
        
        BigInteger n1 = new BigInteger(n.split(" ")[0]);
        BigInteger n2 = new BigInteger(n.split(" ")[1]);
        BigInteger n3 = (BigInteger) n1.add(n2);
        
        System.out.println(n3);
    }
}