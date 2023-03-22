# 1. 객체 비교

자바에서는 객체를 생성하여 사용합니다. 그렇다면 생성한 객체들이 같은 객체인지 어떻게 판단할 수 있을까요 ? 객체들간의 비교는 비교 연산자인 "=="를 사용하여 비교할 수 있습니다. 아래의 예를 보시겠습니다.

<br>

- 객체 비교 예시
  ```java
  public class Main {
      public static void main(String[] args){
          Car c1 = new Car("c1", "red");
          Car c2 = new Car("c2", "blue");

          System.out.println(c1 == c2); // 6번째 줄
      }
  }

  class Car {
      private String name;
      private String color;

      public Car(String name, String color) {
          this.name = name;
          this.color = color;
      }
  }
  ```
  
위의 Main 클래스를 실행하면 두 개의 다른 Car 객체를 생성하고 우리는 당연하게도 이 두 객체가 다른 것을 알고있기 때문에 "System.out.println(c1 == c2);"의 결과로 false가 나올 것이라고 예상합니다. 그렇다면 다르게 예를 들어보겠습니다.

<br>


- 객체 비교 예시 2
  ```java
  public class Main {
      public static void main(String[] args){
          Car c1 = new Car("c1", "red");
          Car c2 = new Car("c1", "red");

          System.out.println(c1 == c2); // 6번째 줄
      }
  }

  class Car {
      .. 생략 ..
  }
  ```

위의 예시는 "System.out.println(c1 == c2);"는 true를 출력할까요 false를 출력할까요 ? 당연히 false를 출력합니다. c1과 c2 두 객체는 다른 메모리 주소에 저장되어 있기 때문입니다. 그렇다면 마지막 예시를 보겠습니다.
 
 
 <br>


- 객체 비교 예시 3
  ```java
  public class Main {
      public static void main(String[] args){
          Car c1 = new Car("c1", "red");
          Car c2 = c1;

          System.out.println(c1 == c2); // 6번째 줄
      }
  }

  class Car {
      .. 생략 ..
  }
  ```
  
이번에 "System.out.println(c1 == c2);"의 결과로 true가 출력되는 이유는 c2 는 c1을 참조하는 객체로 같은 메모리 주소를 가지기 때문에 true를 반환합니다.
  
사실 비교연산자인 "=="는 두 객체의 동일성을 비교하는 객체로 Car 객체의 name 값이나 color 값이 동일한지 확인하는 것이 아니라 객체가 저장된 메모리 주소의 값이 같다면 true를 출력합니다. 

---
<br>

<br>


## 1-1. 동등성비교와 equals()

지금까지는 비교연산자인 "==" 를 사용해서 두 객체가 동일한 메모리 주소에 가지는지, 즉 동일성을 비교했습니다. 그렇다면 두 객체가 값이 같은지, 객체 값의 동등성(Equality)을 확인하려면 어떻게 해야할까요 ? equals 메서드를 오버라이딩하여 사용하면 됩니다.

equals() 메서드를 아래와 같이 정의되어 있습니다.

- equals() 메서드
  ```java
  public boolean equals(Object obj) {
      return (this == obj);
  }
  ```



equals를 재정의하지 않으면 비교연산자인 "=="를 사용해 두 객체의 동일성을 비교하고, 비교한 결과를 리턴합니다. 하지만 객체 값의 동등성을 비교하려면 equals() 메서드를 오버라이딩해주어야 하며, 오버라이딩 전에 String 객체를 비교한 아래의 예시를 보도록 하겠습니다.

<br>

- equals() 객체 비교 예시
  ```java
  public class Main {
      public static void main(String[] args){
          String str1 = "STRING";
          String str2 = "STRING2";
          String str3 = "STRING";
        
          System.out.println(str1.equals(str2));
          System.out.println(str1.equals(str3));
      }
  }

  ```
  
- 실행결과
  ```
  false   
   true  
  ```


위의 예에서 Object 클래스에 정의된 equals() 메서드를 사용했다면 false가 나와야하는데 어떻게 true가 출력됬는지 String 클래스가 정의되어있는 소스코드를 보면서 확인해보겠습니다.


<br>


- String 클래스에 equals() 메서드
  ```java
  public boolean equals(Object anObject) {
      if (this == anObject) {
          return true;
      }

      return (anObject instanceof String aString)
                  && (!COMPACT_STRINGS || this.coder == aString.coder)
                  && StringLatin1.equals(value, aString.value);
  }
  ```
  
String 클래스는 위와 같이 두 객체가 다른 메모리 주소에 저장되어 있는 경우 값이 같은 경우에는 참(true), 다른 경우에는 거짓(false)을 반환하도록 메서드를 재정의하고 있습니다. 그렇기 때문에 str1.equals(str3)는 true를 반환한 것이죠. 이렇듯 객체를 정의하고 두 객체의 값이 같은지, 즉 두 객체의 동등성을 비교해주기 위해선 equals 메서드를 재정의해주어야 합니다. 재정의하지 않은 경우 동일성만을 판단해줄 뿐이죠


## 1-2. hashCode()

hashCode() 메서드에 대해서 이야기하기 전에 예 몇가지를 보고 가겠습니다.

- Car 객체의 equals() 오버라이딩하기
  ```java
public class Main {
	public static void main(String[] args) {
		Car c1 = new Car("c1", "red");
		Car c2 = new Car("c2", "blue");
		Car c3 = new Car("c1", "red");

		System.out.println(c1 == c2); 
		System.out.println(c1.equals(c2)); 

		System.out.println(c1 == c3); 
		System.out.println(c1.equals(c3)); 
	}
}

  class Car {
      private String name;
      private String color;

      public Car(String name, String color) {
          this.name = name;
          this.color = color;
      }
      @Override
      public boolean equals(Object obj) {
          if (this == obj) {
              return false;
          }

          Car car = (Car) obj;
          return (this.name == car.name) && (this.color == car.color);
      }
  }
  ```

<br>

위와 같이 Car 클래스의 equals() 메서드를 재정의하여 객체의 name 값과 color 값이 같을 경우 참을 반환하고, 하나라도 다른 경우에는 거짓을 반환하도록 했습니다.

하지만 위와같이 equals() 메서드만 변경하면 위와같이 비교연산자 == 사용하면 false이지만 equals를 사용하면 true가 나오는 실행결과의 예상이 어려워질 수 있습니다. 비교연산자 "==" 를 사용할 때 뿐만아니라 자료구조인 Set을 사용할 때도 결과를 예상하기 어려워지죠

<br>


- 자료구조 Set 사용 예시
  ```java
  public class Main {
      public static void main(String[] args) {
          Car c1 = new Car("c1", "red");
          Car c2 = new Car("c1", "red");
          
          System.out.println(c1.equals(c2));

          Set<Car> cars = new HashSet<>();
          cars.add(c1);
          cars.add(c2);

          System.out.println(cars.size());
      }
  }

  class Car {
      .. 생략 ..
  }
  ```

- 실행결과
  ```
  true
  2
  ```
  
  
  <br>


Set은 객체가 중복되는지 확인할 때 Hash Table을 사용합니다. 이 Hash Table은 어떤 데이터가 존재하는지 확인하기 위해서 해싱 알고리즘을 사용하며, 이 해싱 알고리즘에 사용되는 데이터가 바로 hashCode 값입니다. **hashCode란 메모리의 주소값을 정수로 변환한 값을 말하며 같은 주소값을 같은 객체는 hashCode 값이 동일해야 합니다.** 주소값을 정수 값으로 변환한 값이 hashCode 값이기 때문입니다.

<br>

---
> 같은 메모리 주소를 같은 객체는 hashCode 값이 같다.

---

<br>

Car 객체인 c1과 c2는 다른 메모리 주소에 저장되어 있기 때문에 해쉬 코드 값이 다릅니다. 하지만 equals(Object)가 두 객체를 같다고 판단했으면, 두 객체의 hashCode 값은 항상 같아야 합니다. 그렇기 때문에 equals() 메서드를 오버라이딩 해주었다면 hashCode() 메서드도 오버라이딩 해주어야 합니다. 이번에는 hashCode() 메서드를 오버라이딩 하지 않았을 때 객체 c1과 c2의 hashCode 값을 확인해보도록 하겠습니다.

<br>

- Car 객체인 c1과 c2의 hashCode 값 확인 예제
  ```java
  public class Main {
        public static void main(String[] args) {
            Car c1 = new Car("c1", "red");
            Car c2 = new Car("c1", "red");

            System.out.println(c1.hashCode());
            System.out.println(c2.hashCode());

        }
    }

    class Car {
        .. 생략 ..
    }
  ```

- 실행 결과
  ```
  1933863327
  112810359
  ```

<br>


hashCode 값은 Object 클래스에 정의되어 있는 hashCode() 메서드를 통해 확인할 수 있습니다. hashCode() 메서드는 아래와 같이 정의되어 있으며 여기에서의 natvie 키워드는 해당 코드가 C나 C++로 작성되어 있다는 뜻입니다. 

<br>


- Object 클래스의 hashCode() 정의
  ```java
  public class Object {

      public native int hashCode();

      .. 생략 ..
  }
  ```

그렇다면 이제 equals() 메서드가 참을 반환할 때, 같은 hashCode 값을 갖도록 Car 클래스에서 hashCode() 메서드를 재정의해주도록 하겠습니다.

<br>

- Car 클래스 hashCode 메서드 재정의
  ```java
  class Car {

      .. 생략 ..

      @Override
      public int hashCode() {
          return Objects.hash(this.name, this.color);
      }

  }
  ```

Objects.hash() 메서드를 통해 Car 객체의 name 값과 color 값을 해싱하여 반환하도록 Car 클래스에 hashCode() 메서드를 오버라이딩해줬습니다. 이제 name 값과 color 값이 같은 Car 객체들은 같은 해시코드 값을 가질까요 ?

<br>


- hashCode() 테스트
  ```java
  public class hashCodeNEquals {
      public static void main(String[] args) {
          Car c1 = new Car("c1", "red");
          Car c2 = new Car("c1", "red");
          
          System.out.println(c1.hashCode());
          System.out.println(c2.hashCode());
      }
  }
  ```

- 실행 결과
  ```
  210404
  210404
  ```


<br>

hashCode() 메서드를 재정의하여 같은 멤버변수 값을 갖는 Car 객체는 같은 hashCode 값을 갖게 되었습니다. 마지막으로 Set 자료구조에 name 값과 color 값이 같은 두 객체를 넣어보고 두 객체를 동일한 객체로 판단하는지 확인해보고 마치겠습니다.

<br>

- 마지막 테스트
  ```java
  public class hashCodeNEquals {
      public static void main(String[] args) {
          Car c1 = new Car("c1", "red");
          Car c2 = new Car("c1", "red");

          Set<Car> cars = new HashSet<>();
          cars.add(c1);
          cars.add(c2);

          System.out.println(cars.size());
      }
  }
  ```

- 실행결과
  ```
  1
  ```
  
  
  <br>
  
  <br>
  
  
# 0. 정리

1. 비교연산자 "=="는 객체가 저장된 메모리 주소가 같은지 확인한다 (동일성 확인)

2. 객체를 equals() 메서드로 비교한다는 것은 객체의 값들이 같은지 알고싶은 것이다.

3. equals 메서드를 재정의 해줄때는 hashCode 메서드도 꼭 재정의해주어야 한다.