> 자바의 정석 기초와 나 혼자 공부하는 자바라는 책을 보고 공부한 후 작성한 내용입니다.

# 해쉬코드 (HashCode)
> 해쉬코드란 자바에서 객체를 식별하는 하나의 정수값을 말한다.
> Object의 hashCode() 메서드는 객체의 메모리 번지를 이용해 해쉬코드를 만들어 리턴하기 때문에 객체마다 다른 값을 가질 수 없는데
> 이러한 특징때문에 객체를 비교할 때 주로 사용한다.

```java
public class Object {
    ...
    
    @IntrinsicCandidate
    public native int hashCode();
}  
```

자바에서 객체를 정의하는 Object 클래스가 작성된 코드를 살펴보면 hashCode() 메서드가 어떻게 정의되어있는지 확인할 수 있다.

public native int hashCode() 에서 native는 자바가 아닌 언어로 구현한 후 자바에서 사용하려고 할 때 이용하는 키워드인데 보통 해쉬코드는 OS가 가지고 있는 C언어로 작성된 메서드를 자바에서 사용할 수 있다.


# equals()

```java
public class Object {
    ...

    public boolean equals(Object obj) {
        return (this == obj);
    }
}
```

Object 클래스에서는 hashCode()메서드 말고도 객체 비교를 할 수 있는 eqauls() 메서드 또한 정의되어있다. Object 클래스의 equals() 메서드는 비교 연산자인 == 와 동일한 결과를 리턴하는데 오로지 참조값(객체의 주소값)이 같은지 확인한다. 쉽게 말하면 두 객체가 동일한 객체인지 확인하는 것이다.

대게 자바를 사용할 때 두 객체가 동일한 객체인지 확인하기 위해서 equals() 메서드를 사용하는데 두 객체가 논리적으로 동일한 객체라면 true, 다른 객체라면 false를 리턴한다.

```java
public class test {
    public static void main(String[] args){
        Car c1 = new Car();
        Car c2 = new Car();
        Car c3 = c1;

        System.out.println("c1.equals(c2) = "+c1.equals(c2));
        System.out.println("c1.equals(c3) = "+c1.equals(c3));
        
        System.out.println("c1.hashCode() = "+c1.hashCode());
        System.out.println("c2.hashCode() = "+c2.hashCode());
        System.out.println("c3.hashCode() = "+c3.hashCode());
    }


class Car{
    private String name;
    private String color;

    public Car(){

    }

    public Car(String name){
        this.name = name;
    }

    public Car(String name, String color){
        this.name = name;
        this.color = color;
    }
}
```
실행결과
```
c1.equals(c2) = false
c1.equals(c3) = true
c1.hashCode() = 798154996
c2.hashCode() = 681842940
c3.hashCode() = 798154996
```

위의 예를보면 논리적으로 c1과 c3는 동일한 객체고 c2는 c1과 다른 객체라는 것을 알 수 있을 것이다. 그렇기 때문에 c1과 c2를 equals() 메서드를 사용해 비교했을 때 false가 나오는 것이고 c1과 c3를 equals() 메서드를 사용해 비교했을 때 true가 나오는 것이다.

또 hashCode()메서드를 사용한 값 또한 c1과 c3가 같음을 확인할 수 있는데 그 이유는 두 객체가 동일한 메모리 주소를 공유하고 있기 때문이다.

그렇다면 이제 name값과 color값을 넣어 Car 객체를 여러 개를 생성한 후에 equals() 메서드를 사용해보자. 

```java
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
```
실행결과
```
c4.equals(c5) = false
c4.equals(c6) = false
c4.hashCode() = 1421795058
c5.hashCode() = 1555009629
c6.hashCode() = 41359092

```

위 예제에서 주목해서 봐야할 부분은 name값과 color값이 c4와 c6가 같음에도 불구하고 equals() 메서드의 결과값이 false가 나왔다는 부분이다. 왜 이런 결과가 나온것일까 ?

그 이유를 알기위해서 Object 클래스가 equals() 메서드를 어떻게 정의했는지를 다시 한 번 볼 필요가 있을 것같다.

```java
public class Object {
    ...

    public boolean equals(Object obj) {
        return (this == obj);
    }
}
```
Object 클래스에서 equals() 메서드는 두 객체를 == 연산자만을 사용해서 비교하는 것을 볼 수 있다. 그렇기 때문에 c4와 c6의 비교 결과가 false가 나온 것이다.

name과 color 값이 같을때 true 값이 나오도록 하기위해서는 equals 메서드를 오버라이딩해줘야 한다. 우선 오버라이딩을 한 equals() 메서드를 확인해보자.

```java
class Car{
    private String name;
    private String color;

    public Car(){

    }

    public Car(String name){
        this.name = name;
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
}
```

위에서 오버라이딩한 equals() 메서드를 확인해보자. 우선 두 객체를 == 비교연산자를 사용해서 두 객체가 같다면 두 객체의 name과 color 값을 비교해서 같은지 확인하고 같다면 true를 반환하도록 코드를 작성했다. 이렇게 오버라이딩 한 후에 다시 Test2 코드를 실행시켜보자.

```java
public class Test2 {
    public static void main(String[] args){
        Car c4 = new Car("a", "red");
        Car c5 = new Car("b", "red");
        Car c6 = new Car("a", "red");

        System.out.println("c4.equals(c5) = "+ c4.equals(c5));
        System.out.println("c4.equals(c6) = "+ c4.equals(c6));
    }
}
```

실행결과
```
c4.equals(c5) = false
c4.equals(c6) = true
c4.hashCode() = 1421795058
c5.hashCode() = 1555009629
c6.hashCode() = 41359092
```

두 객체의 hashCode값이 다름에도 equals() 메서드는 c4와 c6가 같다라고 판단한 것을 볼 수 있다. 그렇다면 이제 우리의 눈을 넓혀줄 예제를 보도록하자.

```java
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
```
실행결과
```
c1.equals(c2) = true
lst.size() = 2
c3.equals(c4) = true
carSet.size() = 2
c3.hashCode() = 1421795058
c4.hashCode() = 1555009629
```

위의 예제에서 주목해야할 부분은 중복된 객체는 들어가지 않는 Set의 size가 2가 나왔다는 점이다. 분명 equals를 통해서 두 객체가 같다라는 결과가 나왔는데 어떻게 된 일일까? 그 이유는 바로 hashCode에 있다. 객체의 hashCode의 값을 보면 c3와 c4가 다른 것을 확인할 수 있는데 Set객체가 hashCode 이 두 개체의 hashCode값이 다르기때문에 다른 객체라고 판단했기때문에 set에 c3와 c4가 들어가 size가 2가 된 것이다.

hashCode와 equals를 함께 재정의하지 않으면 위처럼 코드가 예상과 다르게 작동하는 문제가 자주 발생한다. 정확하게 말하면 hash값을 사용하는 컬렉션을 사용할 때 문제가 발생한다.

이렇게 컬렉션에서 코드가 예상과 다르게 작동하는 이유는 컬렉션은 hashCode의 메서드의 리턴 값이 일치하고 equals 메서드의 리턴 값이 true여야 논리적으로 같은 객체라고 판단하기때문인데 위에서 equals() 메서드는 오버라이딩해줬지만 hashCode는 오버라이딩해주지 않았기 때문에 두 객체의 hashCode값이 달랐고 달랐기 때문에 컬렉션이 다른 객체라고 판단한 것이다.

그렇다면 어떻게 hashCode를 재정의해야할까 ?

# hashCode 재정의하기

```java
class Car{
    private String name;
    private String color;

    public Car(){

    }

    public Car(String name){
        this.name = name;
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
```

위와 같이 Car의 name과 color 값을 Objects의 hash() 메서드로 해쉬한 값을 Car클래스의 hashCode() 메서드가 리턴하도록 오버라이딩 해주면 name과 color 값이 같은 Car 객체는 같은 hashCode 값을 가지게된다.

이제 hashCode() 메서드도 오버라이딩 해줬으니 다시 Test4를 실행해보자.

```java
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
```
실행결과
```
c1.equals(c2) = true
lst.size() = 2
c3.equals(c4) = true
carSet.size() = 1
c3.hashCode() = 93822847
c4.hashCode() = 93822847
```

이제 Car 객체를 요소로 가지는 carSet의 size가 1이 나오는 것을 확인할 수 있다. 이렇게 컬력센을 사용할 때 객체의 hashCode()와 equals()를 오버라이딩 해줘야지 예상한 것과 같은 결과를 얻을 수 있다.

equals() 메서드는 여러 객체들을 비교할 때 속도면에서 정수를 비교하는 것보다 느리고 효율적이지 않기때문에 hashCode()를 오버라이딩한 후에 사용하는 것이 좋다. 또 컬렉션 안에 객체를 저장할 때 결과 값이 예상한 것과 다르다면 객체의 hashCode()와 equals()를 상황에 맞게 재정의해주었는지 다시 한 번 생각해보도록 하자.

# [참고] 32Bit JVM과 64Bit JVM에서의 hashCode

```java
public class Object {
    ...
    
    @IntrinsicCandidate
    public native int hashCode();
}  
```

Object 객체에서 hashCode() 메서드를 재정의할 때 리턴값이 int인 것을 확인할 수 있다. 이는 64Bit JVM보다 32Bit JVM에서 자바를 먼저 사용했기때문인데 64Bit JVM에서 hashCode() 메서드를 사용할 때 hashCode값이 중복이 될 수 있음을 의미한다.

64Bit JVM는 주소값이 8Bytes 값인 Long 타입을 사용하는 것이 좋지만 기존에 사용하던 hashCode() 메서드와의 통일성?때문에 int 타입을 아직 사용한다. 그렇기때문에 64Bit JVM에서 주소 값이 8Bytes이기때문에 Long타입의 값을 int 타입으로 변경하는 과정에서 중복이 발생할 수 있다.


