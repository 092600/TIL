public class Test2 {
    public static void main(String[] args){
        Car c4 = new Car("a", "red");
        Car c5 = new Car("b", "red");
        Car c6 = new Car("a", "red");

        System.out.println("c4.equals(c5) = "+ c4.equals(c5));
        System.out.println("c4.equals(c6) = "+ c4.equals(c6));

        System.out.println("c4.hashCode() = "+ c4.hashCode());
        System.out.println("c5.hashCode() = "+ c5.hashCode());
        System.out.println("c6.hashCode() = "+ c6.hashCode());
    }
}
