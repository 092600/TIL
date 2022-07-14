> 본문의 내용은 HeadFirstDesignPattern의 책을 읽은 후 작성한 내용입니다.

어떻게 하면 객체지향적으로 프로그램을 설계할 수 있을까.
(물론 아직도 프로그램을 객체지향적으로 잘 설계하진 못하지만) 객체지향적으로 프로그램을 설계하기 전에 우리가 먼저 알아야 할 것은 우리는 언제든 다가올 변화에 대비하며 코드를 작성해야 한다는 것이다.

그러기 위해서 우리는 자주 변하는 부분과 변하지 않는 부분을 나누어서 코딩을 하고, 많이 사용하는 코드들은 캡슐화하고 또 재사용성을 높이려는 노력을 해야한다. 그렇게 한다면 코드는 유연해지고 확장성이 높아질 것이다. (말은 쉽다.)


## 디자인 패턴이란 ?
> 소프트웨어 디자인 패턴(software design pattern)은 소프트웨어 공학의 소프트웨어 디자인에서 특정 문맥에서 공통적으로 발생하는 문제에
> 대해 재사용 가능한 해결책이다. 소스나 기계 코드로 바로 전환될수 있는 완성된 디자인은 아니며,
> 다른 상황에 맞게 사용될 수 있는 문제들을 해결하는데에 쓰이는 서술이나 템플릿이다.
>
> 위키백과

### 디자인패턴의 원칙
> 바뀌는 부분은 따로 뽑아서 캡슐화한다. 그러면 나중에 바뀌지 않는 부분에는 영향을 미치지 않고 그 부분만 고치거나 확장할 수 있다.


## Chapter 01
예를들어서 여러 종류의 자동차를 코드로 작성해야 한다고 생각해보자.
여러 종류의 자동차라고 함은 매우 빠른 속도로 달릴 수 있는 슈퍼카 혹은 버스, 자동차 장난감, 자동차 인형 등이 포함될 수 있다. 여러 종류의 자동차를 객체지향적인 코드로 작성하려면 어떻게 해야할까 ?
가장 먼저 떠오르는 것은 아마도 클래스의 상속을 통해서 코드를 구현하는 것이지 않을까 싶다.

### 클래스의 상속을 통한 코드 작성
```java
public abstract class Car{
    public void display();
    public void drive();
}
```
여러 종류의 자동차를 코드로 구현하기 위해서 Car의 추상 클래스를 만들어봤다. 이 추상 클래스는 모습을 보여주는 display메서드와  달리는 기능을 하는 drive라는 추상 메서드를 가지고 있다.
이 추상클래스의 상속을 통해서 SuperCar, Bus, CarToy, CarDoll 클래스를 작성해보자.
자동차 인형인 CarDoll의 클래스를 작성하다보니 이 클래스에는 drive 메서드가 들어가면 안되는 것을 알았다.
하지만 Car라는 추상클래스의 상속을 통해서 달릴 수 없음에도 drive라는 메서드를 상속받고 말았다. 

모든 자식클래스가 달릴 수 있는 것이 아니라면 추상클래스의 상속을 통해서 drive 메서드를 구현하는 것은 알맞은 방법이 아니라는 것을 알았다.

그렇다면 이 문제(달릴 수 없음에도 drive 메서드를 상속받은 것)를 해결하기 위해서는 어떻게 해야할까 ?  
아마도 클래스의 상속 다음으로 떠오른 방법은 인터페이스를 통한 구현일 것이다.
인터페이스를 사용한 작성한 예제를 보도록하자


### 인터페이스의 구현을 통한 코드 작성
```java
public interface Drivable {
    public void display();
    public void drive();
}

public class SuperCar implements Drivable{
    public void display();
    public void drive(){
        System.out.println("슈퍼카가 달립니다.");
    }
}
public class CarToy implements Drivable{
    public void display();
    public void drive(){
        System.out.println("장난감 자동차가 달립니다.");
    }
}
```
달릴 수 있는 자동차를 구현한 Drivable 인터페이스를 구현했다.
하지만 Drivable 인터페이스를 사용해서 코드를 작성하다보니 Drivable 인터페이스로 구현된 모든 서브 클래스의 drive 메서드를 모두 오버라이드 해줘야한다는 사실을 알았다.
만약 이대로 Drivable 인터페이스를 사용할 경우 작은 업데이트 사항이 있을 때(drive 메서드를 고쳐줘야 할 때)마다 Drivable 인터페이스로 구현된 모든 서브클래스들의 코드들을 전부 바꿔줘야 했다.
아마도 이렇게 인터페이스를 사용한 방식 또한 재사용성이 높고 유연하게 작성한 방식이 아닌 것 같다.

그렇다면 재사용성이 높고 유연한 코드로 여러 종류의 자동차를 구현하는 방법은 무엇일까 ?

### 문제를 해결하기 전
우리가 문제를 해결하는데 실마리가 되어줄 코드를 한 번 봐도록 하자.
이 예제는 인터페이스와 다형성을 사용한 예제이다.
```java
public interface Animal{
    public void walking();
}

public class Dog implements Animal{
    public void walking(){
        walk();
    }
    public void walk(){
        System.out.println("강아지가 걷는다.");
    }
}

public class Cat implements Animal{
    public void walking(){
        walk();
    }
    public void walk(){
        System.out.println("고양이가 걷는다.");
    }
}

public class AnimalEx{
    public static void main(String[] args){
        Animal animal = new Dog();
        Animal animal2 = new Cat();
        
        animal.walking();
        animal2.walking();
    }
}
```
실행결과
```java
강아지가 걷는다.
고양이가 걷는다.
```
Dog클래스와 Cat클래스가 인터페이스에 맞춰서 프로그래밍된 코드이다.
Dog클래스와 Cat클래스가 Animal이라는 인터페이스로 형변환되어 walking 메서드가 실행 시, 각 클래스의 walk 메서드를 실행하여 다른 결과가 나오는 것이다.
이 예제를 본 후, 어떻게 하면 drive라는 메서드를 달리지 못하는 자동차와 달릴 수 있는 자동차 모두 잘 사용할 수 있도록 코드를 작성 할 수 있을지 생각해보자.



### 문제해결하기
```java
public abstract class Car {
    public abstract void display();
}
public interface DriveBehavior {
    public void drive();
}

public class DrivableCar implements DriveBehavior{
    public void drive(){
        System.out.println("부릉부릉 !");
    }
}

public class UnDrivableCar implements DriveBehavior {
    public void drive(){
        System.out.println("달리지 못함.");
    }
}

public class SuperCar{
    DriveBehavior driveBehavior;
    
    public void drive(){
        driveBehavior = new DrivableCar();
        driveBehavior.drive();
    }

    public void display(){
        System.out.println("멋있는 자동차 슈퍼카의 모습");
    }
}

public class CarDoll extends Car{
    DriveBehavior driveBehavior;
    
    public void drive(){
        driveBehavior = new UnDrivableCar();
        driveBehavior.drive();
    }

    public void display(){
        System.out.println("멋있는 자동차 인형의 모습");
    }
}

public class DriveEx{
    public static void main(String[] args){
        CarDoll carDoll = new CarDoll();
        SuperCar superCar = new SuperCar();

        carDoll.drive();
        superCar.drive();
    }
}
```
실행결과
```java
달리지 못함.
부릉부릉 !
```

DriveEx에서 carDoll의 drive메서드가 실행될 때는 UnDrivableCar라는 클래스의 drive 메서드가 실행되고 superCar의 drive메서가 실행될 때는
DrivableCar의 drive 메서드가 실행되는 걸 볼 수 있다. 이렇게 자주 변하는 부분(drive)와 변하지 않는 부분(display)를 나눈 후 코드를 작성하면
유지보수에 강하고 코드 간 결합이 낮으며 유연성 있는 코드를 작성할 수 있다. ~~너무 재미있다 ㅎㅎ~~
