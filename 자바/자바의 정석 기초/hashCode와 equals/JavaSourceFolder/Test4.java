import java.util.*;

public class Test4 {
    public static void main(String[] args){
        List<Car> lst = new ArrayList<Car>();
        Car c1 = new Car("a");
        Car c2 = new Car("a");

        lst.add(c1);
        lst.add(c2);

        System.out.println("c1.equals(c2) = "+c1.equals(c2));
        System.out.println("lst.size() = "+lst.size());

        Set<Car> carSet = new HashSet<Car>();
        Car c3 = new Car("a");
        Car c4 = new Car("a");

        carSet.add(c3);
        carSet.add(c4);

        System.out.println("c3.equals(c4) = "+c3.equals(c4));
        System.out.println("carSet.size() = "+carSet.size());

        System.out.println("c3.hashCode() = "+c3.hashCode());
        System.out.println("c4.hashCode() = "+c4.hashCode());
    }
}
