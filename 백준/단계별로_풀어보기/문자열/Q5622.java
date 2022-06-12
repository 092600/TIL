import java.util.*;

public class Q10809 {
    public static void main(String[] args) throws Exception {
        Scanner stdIn = new Scanner(System.in);
        String in = stdIn.nextLine().toUpperCase();


        int t = 0;
        for (String k : in.split("")) {
            switch (k) {
                case "A":
                case "B":
                case "C":
                    t += 3;
                    break;

                case "D":
                case "E":
                case "F":
                    t += 4;
                    break;

                case "G":
                case "H":
                case "I":
                    t += 5;
                    break;

                case "J":
                case "K":
                case "L":
                    t += 6;
                    break;

                case "M":
                case "N":
                case "O":
                    t += 7;
                    break;

                case "P":
                case "Q":
                case "R":
                case "S":
                    t += 8;
                    break;

                case "T":
                case "U":
                case "V":
                    t += 9;
                    break;

                case "W":
                case "X":
                case "Y":
                case "Z":
                    t += 10;
                    break;
            }

        }
        System.out.println(t);
    }
}