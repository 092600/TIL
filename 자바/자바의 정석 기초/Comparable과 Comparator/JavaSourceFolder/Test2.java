package JavaSourceFolder;

import java.util.Comparator;

public class Test2 {
    public static void main(String[] args){
        Car2 c1 = new Car2(10, 20);
        Car2 c2 = new Car2(5, 30);
        Car2 c3 = new Car2(15, 15);


        System.out.println(c1.compare(c1, c3));
        System.out.println(c1.compare(c2, c3));
    }
}

class Car2 implements Comparator<Car2> {
    int price;
    int weight;

    public Car2(int price, int weight){
        this.price = price;
        this.weight = weight;
    }

    @Override
    public int compare(Car2 o1, Car2 o2) {
        if ((o1.price > o2.price) || (o1.price < o2.price)){
            return o1.price - o2.price;
        } else {
            return 0;
        }
    }

}
