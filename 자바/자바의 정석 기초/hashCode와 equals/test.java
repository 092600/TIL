import java.util.Objects;

public class test {
    public static void main(String[] args){
        Car c1 = new Car();
        Car c2 = new Car();
        Car c3 = c1;

        System.out.println(c1.equals(c2));
        System.out.println(c1.equals(c3));

        System.out.println(c1.hashCode());
        System.out.println(c2.hashCode());
        System.out.println(c3.hashCode());
    }
}

class Car{
    private String name;
    private String color;

    public Car(){

    }

    public Car(String name){
        this.name = name;
        this.color = "black";
    }

    public Car(String name, String color){
        this.name = name;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(name, car.name) && Objects.equals(color, car.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color);
    }
}
