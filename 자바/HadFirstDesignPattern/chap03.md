> 본문의 내용은 HeadFirstDesignPattern 책을 읽은 후 작성한 내용입니다.
 
# OCP (Open-Closed Principle)
> 코드는 변경에는 닫혀있고 확장에는 열려있어야한다. 

# 데코레이터 패턴
여러 객체들의 결합을 통해 기능을 유연하게 확장할 수 있는 디자인 패턴으로 기본적인 기능을 하는 클래스(wrappered Object)를 하나 만들어 준 후 
추가할 기능에 맞는 객체(wrapper Object)를 추가해서 기능들을 편리하게 추가할 수 있다.

실행 중에 객체에게 행동을 추가하는 것이 가능하며 기존 코드를 사용해 기능을 확장해나갈 수 있다는 점에서 좋지만
기능을 추가해줄 wrapper Object를 만드는 과정에서 너무 많은 클래스가 추가되고 사용되면 코드가 복잡해질 수 있다는 단점이 있다.


## 데코레이터 패턴 예

### 라면 추상 클래스
```java
public abstract class Ramen {
String Description = "";

    public abstract int cost();
    
    public String getDescription(){
        return Description;
    };
}
```
### 인스턴트 라면 클래스
```java
public class InstantRamen extends Ramen {
    

    public InstantRamen() {
        this.Description = "인스턴트 라면";
    }

    public int cost(){
        return 3500;
    }

    @Override
    public String getDescription() {
        return Description;
    }

}

```

### 돈카츠 라면 클래스
```java

public class TonkatsuRamen extends Ramen {
    

    public TonkatsuRamen() {
        this.Description = "톤카츠 라면";
    }

    public int cost(){
        return 6500;
    }

    @Override
    public String getDescription() {
        return Description;
    }

}

``` 

### 토핑 추상 클래스
```java
public abstract class RamenTopping extends Ramen{
    Ramen ramen;
    public abstract String getDescription();
}
```

### 계란 클래스
```java
public class Egg extends RamenTopping{
    Ramen ramen;
    
    public Egg(Ramen ramen){
        this.ramen = ramen;
    }

    @Override
    public String getDescription(){
        return "계란 추가 " + ramen.getDescription();
    };

    public int cost(){
        return ramen.cost() + 500;
    }
}
```
### 치즈 클래스
```java

public class Cheese extends RamenTopping{
    Ramen ramen;
    
    public Cheese(Ramen ramen){
        this.ramen = ramen;
    }

    @Override
    public String getDescription(){
        return "치즈 추가 " + ramen.getDescription();
    };

    public int cost(){
        return ramen.cost() + 700;
    }
}
```

라면 추상 클래스의 상속을 받은 인스턴트 라면과 돈카츠 라면은 라면의 Description 값을 초기화해주고 라면의 가격을 라턴해줄 cost()함수를 구체화하는 역할을 합니다.
그리고 토핑 클래스는 토핑이 추가되면 토핑이 추가되었음을 알 수 있도록 라면의 Description을 변경해주고 라면의 cost 함수에 토핑의 가격을 추가하도록 합니다.

추상클래스인 라면의 형식에 맞추도록 코드가 작성되었고 각 클래스 안에 라면 객체를 가지도록 객체 구성을 이용하고 있습니다.


## 위의 클래스들로 데코레이션 활용
```java
import java.util.Random;
import java.util.Scanner;

public class RamenShop {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int choice;
        int toppingChoice;
        int LastChoice;
        Ramen ramen;
        
        Outer:
        while (true){
            System.out.println("-------------------메뉴-------------------");
            
            RamenChoice:
            while (true){
                System.out.print("1. 인스턴트 라면, 2. 돈카츠 라면  입력 > ");
                choice = stdIn.nextInt();
                switch (choice){
                    case 1:
                        ramen = new InstantRamen();
                        break RamenChoice;
                    case 2:
                        ramen = new TonkatsuRamen();
                        break RamenChoice;
                }   
            }
            System.out.println("선택하신 라면은 " + ramen.getDescription()+"입니다.");
            
            System.out.print("토핑을 추가하시겠습니까? (네 : 1, 아니오 : 2)");
            choice = stdIn.nextInt();

            if (choice == 2){
                System.out.println(ramen.cost()+"원 "+ramen.getDescription()+" 주문이 완료되었습니다.");
                break Outer;
            }
            else if (choice == 1){
                AppendTopping:
                while (true){
                    System.out.println("-------------------토핑-------------------");
                    System.out.print("1. 계란 추가, 2. 치즈 추가, 3. 토핑 추가 그만하기 > ");
                    toppingChoice = stdIn.nextInt();
                    System.out.println();
                    switch (toppingChoice){
                        case 1:
                            ramen = new Egg(ramen);
                            break;
                        case 2:
                            ramen = new Cheese(ramen);
                            break;
                        case 3:
                            System.out.println("주문하신 라면이 "+ramen.getDescription()+"이 맞으신가요?");
                            while (true){
                                System.out.print("맞으시다면 1번, 아니라면 2번을 눌러주세요. > ");
                                LastChoice = stdIn.nextInt();
                                if (LastChoice == 1){
                                    System.out.println(ramen.cost()+"원 "+ramen.getDescription()+" 주문이 완료되었습니다.");
                                    break Outer;
                                }
                                else if (LastChoice == 2){
                                    System.out.println("초기 화면으로 넘어갑니다.");
                                    break AppendTopping;
                                } 
                            }
                    } // toppingChoice
                } // AppendTopping
            } // c   hoice if
            

            
        } // Outer
        
    } // Main
}
```