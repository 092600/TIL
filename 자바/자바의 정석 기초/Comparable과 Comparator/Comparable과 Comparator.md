# 1. Comparable과 Comparator

이번에는 Comparable와 Comparator에 대해서 알아보도록 하겠습니다. 이야기에 들어가기 앞서 우선 Comparable과 Comparator가 코드로 어떻게 작성되어 있는지 확인해보겠습니다.

<br>

- Comparable 
```java
public interface Comparable<T> {

    public int compareTo(T o);

}
```

- Comparator
```java
@FunctionalInterface
public interface Comparator<T> {
    
    int compare(T o1, T o2);

    boolean equals(Object obj);

    default Comparator<T> reversed() {
        return Collections.reverseOrder(this);
    }
    
    ...

}
```

<br>

Comparable과 Comparator는 객체간 값 비교를 도와주는 인터페이스로 해당 인터페이스를 구현하는 객체를 만들고 각각 compareTo()와 compare() 메서드를 오버라이딩하여 사용할 수 있습니다.

---

<br>



## 1-1. 값 비교하기

<br>

- 원시타입의 값 비교하기
```java
public class Test(){
    public static void main(String[] args){
        int a = 4;
        int b = 5;

        if (a > b) {
            System.out.println("a > b");
        } else if (a < b) {
            System.out.println("a < b");
        } else {
            System.out.println("a == b");
        }
    }
}
```

<br>

위의 예처럼 값을 비교하는 가장 간단한 방법은 비교연산자를 사용하는 방법입니다. 하지만 이 방법은 비교하려는 값이 원시타입(primitive)인 경우에만 가능하며 객체의 경우 비교기준을 정해주지 않는 이상 비교연산자를 사용할 수 없습니다. 그렇기 때문에 객체간 비교는 객체의 멤버변수를 지정한 후 비교연산자를 사용하거나 Comparable과 Comparator 인터페이스를 구현한 클래스를 만들어 객체의 값을 비교해야합니다.

---

<br>
<br>

## 1-2. Car 객체 비교하기

그렇다면 이제 Comparable 인터페이스를 구현하는 Car 클래스를구현해보도록 하겠습니다.


```java
public class Test {
    public static void main(String[] args){
        Car c1 = new Car(10, 20); // price가 10이고 weight가 20인 Car
        Car c2 = new Car(5, 30); // price가 5이고 weight가 30인 Car

    }
}

class Car {
    private int price;
    private int weight;

    public Car(int price, int weight){
        this.price = price;
        this.weight = weight;
    }
}
```

<br>


위와같이 Car 클래스를 정의해보았습니다. Car 객체는 int 타입의 price와 int 타입의 weight를 가지며 Car 객체인 c1과 c2를 비교할 때는 두 객체를 비교할 기준을 만들어야 하며 이번 글에서는 price 값을 기준으로 Car 객체를 비교해보겠습니다. 객체를 비교할 때는 Comparable 또는 Comparator 를 구현한 클래스를 만들어야하는데 둘 중 어떤 인터페이스를 구현해야할까요 ? 당연히 상황에 따라 다릅니다. 우선 다시 한 번 인터페이스가 어떻게 작성되었는지 확인해보도록 하겠습니다.

<br>

- Comparable 
```java
public interface Comparable<T> {

    public int compareTo(T o);

}
```

<br>

- Comparator
```java
@FunctionalInterface
public interface Comparator<T> {
    
    int compare(T o1, T o2);

    boolean equals(Object obj);

    default Comparator<T> reversed() {
        return Collections.reverseOrder(this);
    }
    
    ...

}
```

<br>

위의 코드를 보면 Comparable 인터페이스는 compareTo 메서드를 정의하고 있고 Comparator 인터페이스는 compare 메서드를 정의하고 있다는 것을 확인할 수 있습니다. Comparable 인터페이스는 이름 그대로, 자신과 다른 객체를 비교할 때 사용하고 Comparator는 Comparator를 구현한 객체가 compare() 메서드의 매개변수로 들어온 두 객체를 비교할 때 사용합니다.

사실 Comparable과 Comparator 이름에서 느낌으로 알 수 있듯이 Comparable을 구현한 객체는 비교할 수 있는 객체가 되어 compareTo() 메서드의 매개변수로 들어온 객체와 자기 자신을 비교하며, Comparator 또한 이름에서 알 수 있듯이 Comparator를 구현한 객체는 객체를 비교할 수 있는 객체가 될 수 있습니다. 이제 본격적으로 임의의 Car 객체 2개를 비교하는 Comparable 인터페이스를 구현한 클래스를 만들어 보겠습니다.

---

## 1-2. Comparable 인터페이스를 구현하기

<br>

- Comparable을 구현한 Car 클래스
```java
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

	// compareTo 메서드 재정의
    @Override
    public int compareTo(Car o) {
        if ((this.price > o.price) || (this.price < o.price)){
            return this.price - o.price;
        } else {
            return 0;
        }
    }
}
```

<br>

위와 같이 Comparable을 구현하는 Car 클래스에 compareTo() 메서드를 재정의함으로써 Car 객체의 price 값을 기준으로 Car 객체 두 개를 비교할 수 있습니다. compareTo() 메서드를 재정의할 때, 위와같이 두 값의 차이를 직접적으로 반환해줘도 좋지만 대소 비교만을 나타낼 수 있도록 1, 0, -1을 반환해줘도 상관없습니다.

> 두 값의 차이를 반환할 때는 두 값의 차가 int 범위를 넘어간다면 Overflow나 UnderFolow가 발생할 수 있으니 주의하자 !

---

<br>

## Comparator 인터페이스를 구현한 Car 클래스

이번에는 Comparator 인터페이스를 구현하는 Car 클래스를 정의하고 Car 객체의 price 값을 기준으로 비교하도록 compare() 메서드를 오버라이딩해보겠습니다.

Comparable의 compareTo() 메서드와는 달리 Comparator의 compare() 메서드는 매개변수를 두개받으며 매개변수로 받은 두 객체의 값을 비교합니다. 메서드를 실행하는 객체는 객체 비교에 아무런 영향도 주지 못합니다. 말 그대로 Comporator 가 되는 것입니다.

<br>

- Comparator를 구현하는 Car 클래스
  ```java
    public class Test2 {
        public static void main(String[] args){
            Car c1 = new Car(10, 20);
            Car c2 = new Car(5, 30);
            Car c3 = new Car(15, 15);

            System.out.println(c1.compare(c1, c3));
            System.out.println(c1.compare(c2, c3));
        }
    }
    
    class Car implements Comparator<Car> {
        int price;
        int weight;

        public Car(int price, int weight) {
            this.price = price;
            this.weight = weight;
        }

        @Override
        public int compare(Car o1, Car o2) {
            if ((o1.price > o2.price) || (o1.price < o2.price)){
                return o1.price - o2.price;
            } else {
                return 0;
            }
        }
    }
  ```

- 실행결과
  ```
  -5
  -10
  ```

<br>

Comparable을 구현한 Car 클래스의 compareTo 메서드와 Comparator를 구현한 Car 클래스의 compare() 메서드의 내부 코드는 차이가 없습니다. 두 메서드 모두 비교하는 객체의 price 값의 차를 리턴해주고 있을 뿐입니다. Comparable을 구현하면 compareTo() 메서드를 실행하는 객체와 매개변수로 들어온 객체의 값을 비교하고, Comparator를 구현하면 compare() 메서드의 매개변수로 들어온 두 객체의 값을 비교한다는 점이 중요합니다. 사실 객체의 값 비교를 Comparator를 생성하고 compare() 메서드를 실행하는 객체는 불필요하다고 느낄 수 있는데 그럴땐 Car 클래스의 값을 비교해줄 Comparator를 구현하는 CarComparator 클래스를 생성해주거나 익명 클래스를 생성하는 것도 좋은 방법입니다.

---

<br>
<br>
<br>

> -  참고자료
> 	1 자바의 정석 기초 
> 	2 나 혼자 공부하는 자바 
> 	3 https://st-lab.tistory.com/243 