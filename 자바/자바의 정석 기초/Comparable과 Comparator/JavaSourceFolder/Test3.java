package JavaSourceFolder;

import java.util.Comparator;

public class Test3 {
    public static void main(String[] args){
        Car3 c1 = new Car3(10, 20);
        Car3 c2 = new Car3(5, 30);
        Car3 c3 = new Car3(15, 15);


        System.out.println(carComparator.compare(c1, c3));
        System.out.println(carComparator.compare(c2, c3));
    }

    public static Comparator<Car3> carComparator = new Comparator<Car3>() {
        @Override
        public int compare(Car3 o1, Car3 o2) {
            if ((o1.price > o2.price) || (o1.price < o2.price)){
                return o1.price - o2.price;
            } else {
                return 0;
            }
        }
    };
}

class Car3 {
    int price;
    int weight;

    public Car3(int price, int weight){
        this.price = price;
        this.weight = weight;
    }

}
