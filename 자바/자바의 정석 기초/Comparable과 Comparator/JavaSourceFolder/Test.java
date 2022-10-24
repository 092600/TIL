package JavaSourceFolder;

public class Test {
    public static void main(String[] args){
        Car c1 = new Car(10, 20);
        Car c2 = new Car(5, 30);

    }
}

class Car implements Comparable<Car>{
    private int price;
    private int weight;

    public Car(int price, int weight){
        this.price = price;
        this.weight = weight;
    }

    @Override
    public int compareTo(Car o) {
        if ((this.price > o.price) || (this.price < o.price)){
            return this.price - o.price;
        } else {
            return 0;
        }
    }
}

