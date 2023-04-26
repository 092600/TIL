# 0. JVM / Execution Engine & Gabage Collector

- JVM 구조
![](https://velog.velcdn.com/images/092600/post/c6a7c295-1067-4afc-b3fc-846282011aab/image.png)

위는 JVM에 대해서 공부하며 여러번 보았던 JVM의 구조를 나타낸 이미지입니다. 지금까지 클래스 로더(Class Loader)와 Runtime Data Area에 대해서 간단하게 알아봤으며 아직 알아보지 않은 것은 실행 엔진(Execution Engine)과 가비지 컬렉터(Garbage Collector)가 무엇인지, 어떤 역할을 하는지 알아보도록 하겠습니다.

---

<br>

## 1. JVM의 Execution Engine

이전 클래스 로더에 대해서 공부할 때, 우리는 자바 소스파일(.java)을 자바 컴파일러가 컴파일한 결과물인 클래스 파일(.class)을 JVM의 클래스 로더가 읽어와 Runtime Data Area의 Method 영역에 적재해둔다는 것을 알고있습니다. 

Runtime Data Area의 Method 영역에 적재된 클래스들은, 바이트코드로 작성되어 있는 .class 파일, 기계어로 번역되어 실행되는데 바이트 코드들이 기계어로 번역되는 방법에는 여러가지가 존재합니다. 지금부터 어떤 방법들이 있는지 알아보도록 하겠습니다.

<br>

### 1-1. Interpreter 방식

자바가 특정 플랫폼에 종속적이지 않으며 이식성이 좋은 이유는 각 플랫폼에 맞는 인터프리터가 바이트 코드를 기계어로 번역하고 실행하기 때문입니다. 이러한 자바의 특징을 WORA(Write Once Read Anywhere)이라고 합니다. JVM의 인터프리터는 런타임 중 바이트 코드를 한 라인씩 읽고 기계어로 번역하여 실행합니다.

> **소스코드 전체를 기계어로 번역한 후 실행하는 컴파일 언어**와는 달리, **한줄씩 읽고 기계어로 번역하여 실행하는 인터프리터 언어는 컴파일언어에 비해 실행 속도가 느리다**

<br>

### 1-2. JIT(Just In Time) Compile 방식
 
Interpreter 방식을 사용하기 때문에 생기는 성능 문제를 해결하기위해서 자바는 JIT Compile 방식도 사용합니다. JIT Compile 방식은 자주 실행되는 바이트 코드를 런타임 중에 기계어로 컴파일하여 저장하여 사용하는 것으로 반복되는 코드를 기계어로 번역하여 저장하여 사용하기 때문에 인터프리터 방식만을 사용하는 것보다 성능이 좋아지는 효과를 얻을 수 있습니다.

---

<br>


## 2. JVM의 Garbage Collection

**자바는 프로그램 실행 중 메모리 관리를 위해서 갈비지 컬렉션(Garbage Collection)이라는 기능을 제공**합니다. 갈비지 컬렉션은 **JVM에서 더 이상 사용되지 않는 데이터가 할당되어있는 메모리를 해제시켜주는 장치**로 개발자는 메모리에 직접 접근하지 않고도 갈비지 컬렉션을 사용하여 메모리 관리를 할 수 있습니다.

- 갈비지 컬렉션 이해하기
```java
public class Main {
	public static void main(String[] args) {
    	 Car car = new Car("c1"); // 3번째 줄
         car = new Car("c2");  	  // 4번째 줄
    }
}

class Car {
	private String name;
    
	public Car(String name) {
    	this.name = name;
    }
}
```

위의 코드를 보고 갈비지 컬렉션에 대해서 좀 더 이야기해보기 전에 참조에 대해서 간단하게 알아보겠습니다. 참조되고 있는지에 대한 개념을 reachability라고 하며 유효한 참조를 reachable, 참조되지 않으면 unreachable이라고 합니다. 갈비지 컬렉션은 참조되고 있지 않은 상태를 의미하는 unreachable한 객체들을 garbage라고 인식하게 됩니다.

좀 더 쉬운 이해를 위해 위의 코드를 살펴보도록 하겠습니다. Car 클래스의 참조변수인 car는 "c1"이라는 name 값을 가지는 Car 인스턴스를 참조하다가 "c2"라는 name 값을 가지는 Car 인스턴스를 참조하게됩니다. 이 과정에서 name 값이 **"c1"인 객체가 reachable에서 unreachable 상태로 변하게 되었다** 라고 이야기 할 수 있습니다.

Heap 영역에 있는 객체들은 **Method Area, Stack, Native Stack**에서 참조되면 reachable 상태라고 판단되며 이렇게 **reachable 상태로 인식되게 만들어주는 JVM Runtime Area들은 root set**이라고 합니다. **reachable 상태의 객체가 참조하고 있는 객체 역시 reachable 하다고 말할 수 있습니다.** 하지만 이와는 반대로 이러한 root set에 의해 참조되고 있지 않은 객체들은 unreachable 하다고 말할 수 있으며, 이러한 unreachable 객체들은 GC의 대상이 됩니다.


> root set : Method Area, Stack Area, Native Stack Area

<br>


### 2-1. Garbage Collection 동작 과정

JVM의 Garbage Collection에는 여러가지 알고리즘이 존재하며, 알고리즘마다 동작방식이 다릅니다. 하지만 기본적으로 갈비지 컬렉션이 실행된다고 할 때, 아래의 2가지 공통적인 단계를 따릅니다.

- Garbage Collection 실행시 공통점
	1. Stop-The-World
	2. Mark and Sweep

<br>

#### 2-1-1. Stop-The-World

Stop-The-World는 이름에서도 알 수 있듯 세상이 멈춘다는 의미로 갈비지 컬렉션이 일어날 때 갈비지 컬렉션을 실행하는 스레드 외의 나머지 스레드는 정지한다는 것을 의미합니다. 이후 갈비지 컬렉션이 종료되면 나머지 스레드의 작업이 재개됩니다. 그렇기 떄문에 갈비지 컬렉션이 자주 실행되면 프로그램의 성능에 안좋은 영향을 미칠 수 있습니다.


#### 2-1-2. Mark and Sweep

Mark and Sweep은 사용되는 메모리와 사용되지 않는 메모리를 식별하는 작업을 의미하는 Mark와 Mark 단계에서 사용되지 않는 것으로 식별된 메모리를 해제하는 작업을 의미하는 Sweep이 합쳐진 것으로, 갈비지 컬렉션의 대상이 될 unreachable한 객체를 확인하고 메모리에서 삭제하는 것을 의미합니다.

