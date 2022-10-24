> ## 참고자료
> 
> 1 자바의 정석 기초 <br>
> 2 나 혼자 공부하는 자바 <br>
> 3 https://st-lab.tistory.com/243 <br>
> 4 

<br>
<br>

# Comparable & Comparator

백준 정렬 문제나 리스트 안의 요소들을 정렬할 때 어려움을 겪는 이유는 Comparable와 Comparator 에 대해서 잘 이해하지 못했기 때문이라고 생각한다. 그렇기 때문에 이번에는 Comparable와 Comparator와 정렬에 대해서 알아보도록 하자.

우선 Comparable과 Comparator이 자바에서 어떻게 코드로 작성되어 있는지 확인해보자.

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

Comparable과 Comparator가 정의된 코드들에서 주목해야할 것은 Comparable과 Comparator 모두 인터페이스라는 점이다. 이 두 인터페이스을 정렬에 사용하려면 이 인터페이스를 구현하는 객체를 만들어야한다.

두 인터페이스를 구현하는 클래스에서 대게 우리가 오버라이딩해줘야 하는 메서드는 compareTo() 혹은 compare()일 것이다.

<br>
<br>
<br>


## 값 비교하기

<br>


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

우리는 위의 예처럼 값을 비교하기 위해서 비교연산자를 주로 사용해왔다. 대게 이런 경우에는 비교하려는 값이 원시타입(primitive)이였기 때문에 가능했다. 하지만 비교하려는 값이 객체라면 어떻게 비교해야할까 ? 예를들어서 자동차 객체에 속성으로 가격, 무게, 몸체의 길이 등등의 값이 있다고했을 때 이 객체를 비교연산자를 사용한다면 비교연산자는 어떤 값으로 두 객체를 비교하는것일까? 사실 객체는 비교연산자를 통해서 비교할 수가 없다. 그 이유는 객체를 비교할 기준이 명확하지 않기 때문이다. 그렇기 때문에 자바에서 객체간 비교(정렬)을 할 때 Comparable 또는 Comparator를 구현한 객체를 통해서 비교할 수 있다.

<br>
<br>

## Car 객체 비교하기

<br>

그렇다면 이제 Comparable 인터페이스를 구현하는 Car 클래스를 생성해서 Car 객체 간 비교를 하는 코드를 작성해보자.

<br>


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


우선 Car 클래스를 정의해봤다. Car 객체는 int 타입의 price와 int 타입의 weight를 가진다. Car 객체인 c1과 c2를 비교하려고 할 때 우리는 두 객체를 비교할 기준을 만들어야 한다. 우선은 임의로 price를 기준으로 비교한다고 가정하고 코드를 작성해보자. 비교하는 방법은 여러가지가 있다. if 문을 사용해서 비교할 수도 있고 Car 클래스를 Comparable  혹은 Comparator 인터페이스를 구현하도록 코드를 작성하면 된다. if 문에 대해서 공부하고 싶어하는 것이 아니기 때문에 인터페이스를 구현해서 비교하는 방법을 사용해보자.

<br>

그렇다면 Comparable 와 Comparator 중 어떤 인터페이스를 구현해야할까 ? 어떤 인터페이스를 구현해야하는지는 상황에 따라 다르다. 두 인터페이스가 작성된 코드를 다시 한 번 보도록 하자.

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

Comparable 인터페이스는 compareTo 메서드를 정의하고 있고 Comparator 인터페이스는 compare 메서드를 정의하고 있다. 메서드의 이름에서 사용해야하는 상황이 대략 표현되어 있는데 Comparable은 자신과 다른 객체를 비교할 때 사용하고 Comparator는 compare() 메서드의 매개변수로 들어온 두 객체를 비교할 때 사용한다. 

<br>

사실 Comparable과 Comparator 이름에서도 유추할 수 있다. Comparable을 구현한 객체는 비교할 수 있는 객체가 되어 다른 객체가 매개변수로 들어왔을때 자신과 그 매개변수를 비교할 수 있도록 한다. Comparator 또한 이름에서 알 수 있듯이 이 인터페이스를 구현한 객체는 객체를 비교할 수 있는 객체가 될 수 있다.

<br>

이번에는 Car 객체와 임의의 Car 객체를 비교하고 싶기때문에 Comparable 인터페이스를 구현하는 방식을 사용해보자.

## Comparable 인터페이스를 구현한 Car 객체 간 값 비교

<br>

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

이렇게 코드를 작성함으로써 Car는 비교가능한 객체가 되어 compareTo 메서드의 매개변수로 들어온 Car 객체와 price 값을 비교할 수 있게된다. 여기서 주목해야할 점은 compareTo() 메서드가 값을 리턴하는 방식인데 두 객체의 price 값의 차를 리턴해주고 있다. 두 값의 차이를 직접적으로 반환해줘도 괜찮지만 두 객체의 price 대소 비교를 나타내도록 1, 0, -1를 반환해줘도 상관없다. **만약 두 값의 차를 반환해주도록 코드를 작성했다면 두 값의 차가 int 범위 외의 값이라면 Overflow 혹은 UnderFlow가 발생할 수 있으니 주의하도록 하자.**

<br>
<br>
<br>

## Comparator 인터페이스를 구현한 Car2 객체 간 값 비교

<br>

이제 Comparator 인터페이스를 구현하는 Car2 클래스를 정의하고 Car2의 price 값을 비교해줄 compare() 메서드를 오버라이딩하도록 하자.

<br>

Comparable의 compareTo() 메서드와는 달리 Comparator의 compare() 메서드의 매개변수로 들어오는 객체들의 price 값 대소비교에 compare() 메서드를 실행하는 객체는 아무런 영향을 주지 못한다는 것에 주의하며 아래의 코드를 보도록하자.

<br>

```java
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

```
실행결과
```
-5
-10
```

<br>

Comparable을 구현한 예와 이번 예에서 메서드의 내부 코드는 차이가 없다. 그렇기 때문에 두 값의 차를 리턴해주고 있는 것을 볼 수 있다. 여기서 주목해야 할 점은 compare() 메서드를 실행하는 객체는 compare() 메서드의 매개변수로 들어온 객체의 값 비교에 어떠한 영향도 주지 못하고있다는 점이다. 그렇다면 값 비교함에 있어 메서드 실행 객체는 불필요하다고 느낄 수 있다. 그렇다면 이 문제, 값 비교에 있어 메서드 실행 객체가 존재해야 함, 를 어떤 식으로 해결할 수 있을까 ? compare 메서드를 static 메서드로 변경해도 좋을 것 같다. 하지만 Comparator 인터페이스를 구현했기 때문에 static으로 변경할 수 없다. 그렇다면 Comparator 인터페이스를 구현한 객체를 static Object로 만들어서 객체 간 값 비교에 사용하면 되지 않을까 ?

<br>


우선 이렇게 방향을 잡고 코드를 작성해보자.

<br>
<br>


## Comparator 문제 해결하기

```java
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
```

<br>

나는 객체 비교에 불필요한 객체가 사용된다는 문제를 해결하기 위해서 Comparator 를 구현하는 정적 클래스를 만들었다. 정적 클래스를 만듦에 있어 익명 클래스에 관한 개념도 들어갔는데 공부가 필요하다면 따로 익명 클래스에 대해서 공부해보도록 하자.

Car3 클래스의 price 값 비교를 위한 정적 클래스를 만들었다. 정적 클래스로 만들었기때문에 Car3의 price 값을 비교하기 위해서 불필요하게 객체를 생성하거나 할 필요가 없다. 또 클래스간 역할분담?에도 좋다고 생각하기 때문에 좀 더 좋은 방법이 아닐까 생각한다.

