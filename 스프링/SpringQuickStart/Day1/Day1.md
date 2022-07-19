> 본문의 내용은 SpringQuickStart 책을 읽은 후 작성한 내용입니다.
 
# Day01

---

## 프레임 워크란 ?

> 프레임 워크의 사전적 의미는 뼈대 혹은 틀로서 이 의미를 소프트웨어 관점에서 접근하면 아키텍처에 해당하는 골격 코드다.

애플리케이션을 개발할 떄, 애플리케이션의 구조를 결정하는 아키텍처에 해당하는 골격 코드를 프레임워크가 제공한다. 
프레임워크를 사용하는 개발자는 그 골격 코드에 맞춰서 코드를 작성하면 된다.

### 프레임워크의 장점
> 1. 구현시간이 빠르다.
> 2. 관리가 쉽다.
> 3. 개발자들의 역량을 획일화할 수 있다.
> 4. 검증된 아키텍처의 재사용과 일관성 유지할 수 있다.

## 스프링 프레임워크
> 스프링 프레임워크(영어: Spring Framework)는 자바 플랫폼을 위한 오픈 소스 애플리케이션 프레임워크로서 간단히 스프링(Spring)이라고도 한다. 
> 동적인 웹 사이트를 개발하기 위한 여러 가지 서비스를 제공하고 있다. 
> 대한민국 공공기관의 웹 서비스 개발 시 사용을 권장하고 있는 전자정부 표준프레임워크의 기반 기술로서 쓰이고 있다.
 
스프링 프레임워크 이전에 사용되었던 EJB(Enterprise Java Beans)와는 다르게 수 많은 디자인 패턴을 공부하거나 신경쓰지 않아도
되며 평범한 POJO(Plain Old Java Object)를 사용하면서도 EJB 에서만 가능했던 많은 일을 가능하도록 지원해준다.

### 스프링 프레임워크의 장점
> 1. 가볍다

스프링은 여러 개의 모듈로 구성되어 있으며, 각 모듈은 하나 이상의 JAR 파일로 구성되어 있다. 개발 시에 필요한 JAR 파일을
선택해서 개발에 사용하면 된다. 그리고 특별한 규칙이 없는 단순하고 가벼운 객체인 POJO의 형태로 객체를 관리하기 떄문에 EJB 객체를
관리하는 것보다 훨씬 가볍고 빠르다

> 2. 제어의 역행 (IOC : Inversion Of Control)

IOC를 사용하지 않으면 객체를 생성하거나 의존관계를 직접 자바코드로 작성해야한다. 하지만 이렇게 직접 자바 코드로 작성하면
코드간 결합도가 높아져서 다른 객체를 생성하거나 의존관계를 변경하고 싶을때 결합도가 높은 자바코드를 수정해야하기 때문에 유지보수에 어려운 측면이 있다.
하지만 IOC를 사용하면 객체 생성과 의존관계 설정을 자바코드가 아니라 컨테이너가 해주기 때문에 소스파일 안에 의존관계가 명시되지 않아
결합도가 떨어져서 유지보수에 유리하다.

> 3. 관점지향 프로그래밍 (AOP : Aspect Oriented Programming)

관점지향 프로그래밍은 애플리케이션의 핵심기능과 공통기능을 따로 분리해서 코드의 응집도를 높여주는 역할을 한다.
공통기능을 독립된 클래스로 분리하고 해당 코드를 직접 명시하지 않고 선언적으로 처리하여 적용한다.
 
> 4. 컨테이너 

컨테이너는 특정 객체의 관리(객체의 생성)와 객체 운용(IOC와 AOP)에 필요한 다양한 기능을 제공한다.

## 스프링 IoC
스프링 프레임워크는 관리할 클래스들이 등록된 XML 설정파일이 필요하다.
src/java/resource 소스 폴더 안에 Spring Bean Configuration File 을 만들어 XML 설정파일로 사용할 수 있다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">


</beans>
```
Spring Bean Configration File을 만들면 만든 파일 안에 기본적인 세팅이 되어있는 것을 확인할 수 있다.
<beans> 엘리먼트 안에 <bean> 엘리먼트를 만들어 생성할 객체를 적어주면 된다.
#### com.springbook.biz.Cls1.java
```java
public class Cls1{
    public Cls1(){
        System.out.println("cls1 생성");   
    }
}
```
#### ApplicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="cls1" class="com.springbook.biz.Cls1"></bean>
</beans>
```
<bean> 엘리먼트 안에 여러 속성값을 적어줄 수 있다. 우선 위에 적혀있는 <bean> 엘리먼트를 보도록 하자.
<bean>엘리먼트의 class 속성값에 내가 사용할 클래스가 작성된 위치를 적어줘야한다. 만약 클래스의 위치가 다르다면 에러가 난다.
그리고 우리는 id값으로 class 속성값에 적은 클래스를 어떤 이름으로 사용할 것인지 설정할 수 있다.

위의 설정은 이대로 마친 상태에서 컨테이너를 구동해보도록 하자.

#### ContainerText.java
```java
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

class ContainerText{
    public static void main(String[] args){
        AbstractApplicationContext container = 
                            new GenericXmlApplicationContext("ApplicationContext");
        
        Cls1 cls1 = container.getBean("cls1");
    }
}
```
실행결과
```java
cls1 생성
```
AbstractApplicationContext container = new GenericXmlApplicationContext("ApplicationContext");
라는 코드부터 보자. GenericXmlApplicationContext 는 스프링 컨테이너 중 하나인데 "ApplicationContext.xml"이라는 파일을
읽은 후 GenericXmlApplicationContext 컨테이너 만들어 container 에 컨테이너를 저장?한다고 생각하자.

또 Cls1 cls1 = container.getBean("cls1");코드가 있는데 이 코드는 ApplicationContext.xml에서 cls1이라는 아이디를 가진 클래스를
기본생성자로 생성해서 cls1에 그 객체를 저장한다는 코드이다.

스프링 컨테이너의 동작 순서는 아래와 같다.
1. ApplicationContext 설정 파일을 로딩하여 컨테이너를 구동한다.
2. <bean> 엘리먼트에 등록된 cls1 객체를 생성한다.
3. getBean() 메서드로 cls1의 이름을 가진 객체를 요청한다.
4. cls1 객체를 반환한다.

이렇게 컨테이너는 설정 파일을 로딩하여 구동되며 <bean> 엘리먼트에 적혀있는 객체들을 생성하고 요청하면 반환해준다.
컨테이너는 우리가 직접 객체를 관리하지 않게 해주기 때문에 유지보수를 편리하게 해준다.

### 스프링 컨테이너의 종류
스프링은 BeanFactory와 이를 상속한 ApplicationContext 두 가지 컨테이너를 제공한다.
BeanFactory는 스프링 설정 파일에 등록된 <bean> 객체를 생성하고 관리하는 기본적인 역할만을 제공한다. 그리고
컨테이너가 구동될 떄 객체를 바로 생성하는 것이 아니라 클라이언트가 요청할 때 생성하는 지연 로딩(Lazy-Loading) 방식을 사용한다.

ApplicationContext 는 BeanFactory 가 제공하는 기본적인 객체 관리 기능 뿐만 아니라 트랜젝션 관리를 비롯한 다양한 기능을 제공한다.
그리고 BeanFactory 와는 다르게 컨테이너가 구동될 때 <bean>에 등록된 객체를 바로 생성하는 즉시 로딩(pre-loading) 방식을 사용한다.

자주 사용하는 ApplicationContext 종류
1. GenericXmlApplicationContext // XML 설정 파일을 로딩하여 구동하는 컨테이너
2. XmlWebApplicationContext // 웹 기반의 스프링 애플리케이션을 개발할 때 사용하는 컨테이너

## \<bean> 엘리먼트

스프링 설정 파일에 클래스를 등록하려면 <bean> 엘리먼트를 사용한다.
위에서 처럼 <bean> 엘리먼트는 id와 class 속성을 사용할 수 있는데 id속성은 생략이 가능하지만 class 속성은 필수로 적어야한다.
class 속성은 생성할 클래스의 정확한 패키지경로와 클래스이름을 지정해야한다. (자동완성기능을 사용하자.)
id 속성은 컨테이너에게 <bean> 객체를 요청할 때 사용한다. 그렇기 떄문에 유일해야하며 id속성값에 해당하는 문자열은 자바의 식별자 작성 규칙을
따라야 하며 카멜 표기법을 사용한다.

#### 자바 식별자 작성 규칙
1. 숫자로 시작하면 안된다.
2. 공백을 포함하면 안된다.
3. 특수기호를 사용하면 안된다.

#### 카멜 표기법
두 단어가 연결될 때 연결되는 단어의 첫 문자를 대문자로 해주는 것 
```java
ProgrammingComputer (O)
Programmingcomputer (X)
```

### <bean>엘리먼트의 init-method 속성
스프링 컨테이너는 스프링 설정 파일에 등록된 클래스 객체를 생성할 때, 기본 생성자로 호출한다. 그렇기 떄문에 객체를 생성한 후
멤버변수 초기화 작업이 필요한 경우에 쓰는 속성이다.

위에서 작성한 코드들을 가지고 왔다.
#### com.springbook.biz.Cls1.java
```java
public class Cls1{
    public int num; 
    public Cls1(){
        System.out.println("cls1 생성");   
    }
    public void initMethod(){
        num = 3;
    }
}
```
#### ApplicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="cls1" class="com.springbook.biz.Cls1" init-method="initMethod"></bean>
</beans>
```
#### ContainerText.java
```java
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

class ContainerText{
    public static void main(String[] args){
        AbstractApplicationContext container = 
                            new GenericXmlApplicationContext("ApplicationContext");
        
        Cls1 cls1 = container.getBean("cls1");
        System.out.println(cls1.num);
    }
}
```
실행결과
```java
cls1 생성
3
```
위처럼 init-method 속성을 사용하게 되면 객체를 생성한 후 init-method 속성으로 지정된 메서드를 호출한다.
이 메서드를 통해서 멤버변수에 대한 초기화 작업을 처리하면된다.

### <bean>엘리먼트의 destroy-method 속성
destroy-method 속성은 객체를 삭제하기 직전에 호출될 메서드를 지정하는 속성이다. 

#### com.springbook.biz.Cls1.java
```java
public class Cls1{
    public int num; 
    public Cls1(){
        System.out.println("cls1 생성");   
    }
    
    public void destroyMethod(){
        System.out.println("객체 삭제 직전에 호출된 함수");
    }
}
```
#### ApplicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <bean id="cls1" class="com.springbook.biz.Cls1" destroy-method="destroyMethod"></bean>
</beans>
```
#### ContainerText.java
```java
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

class ContainerText{
    public static void main(String[] args){
        AbstractApplicationContext container = 
                            new GenericXmlApplicationContext("ApplicationContext");
        
        Cls1 cls1 = container.getBean("cls1");
        System.out.println(cls1.num);
    }
}
```
실행결과
```java
cls1 생성
객체 삭제 직전에 호출된 함수
```
이렇게 객체 삭제 직전에 호출되는 함수는 <bean> 엘리먼트의 destroy-method 속성을 통해서 설정할 수 있다.


### <bean>엘리먼트의 lazy-init 속성
스프링 프레임워크가 제공하는 컨테이너 중에서 ApplicationContext 는 즉시 로딩 방식으로 객체를 생성했다.
하지만 모든 객체를 즉시 로딩 방식으로 생성한다면 메모리 공간이 가득 차서 시스템에 부담을 주거나 성능 문제가 발생할 수 있기 때문에 
알맞은 시점에 객체를 생성하고 삭제하는 것이 좋다. ApplicationContext 에서 객체를 지연 로딩 방식으로 생성할 수 있도록
해주는 것이 <bean> 엘리먼트의 lazy-init 속성이다. 객체를 지연 로딩 방식으로 사용하는지 예제를 보면서 공부해보자. 

#### com.springbook.biz.Cls1.java
```java
public class Cls1{
    public int num; 
    public Cls1(){
        System.out.println("cls1 생성");   
    }
    public void initMethod(){
        num = 3;
    }
    
    public void destroyMethod(){
        System.out.println("객체 삭제 직전에 호출된 함수");
    }
}
```
#### ApplicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <bean id="cls1" class="com.springbook.biz.Cls1" init-method="initMethod" lazy-init="true"></bean>
</beans>
```
#### ContainerText.java
```java
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

class ContainerText{
    public static void main(String[] args){
        AbstractApplicationContext container = 
                            new GenericXmlApplicationContext("ApplicationContext");
        
        Cls1 cls1 = container.getBean("cls1");
        System.out.println(cls1.num);
    }
}
```
실행결과
```java
cls1 생성
3
```

위처럼 객체를 지연 로딩 방식으로 생성하기 위해선 <bean> 엘리먼트에 lazy-init="true" 속성을 적어주면 된다.
lazy-init 속성의 기본 값은 false로 적어주지 않는다면 즉시 로딩 방식으로 객체가 생성된다.

### <bean>엘리먼트의 scope 속성
우리는 프로그램을 작성하다보면 한 객체만을 생성해서 사용하는 경우와 같이 여러 객체를 생성해서 사용하지 않아도 되는 경우에는
scope 속성을 사용해서 컨테이너에게 단 하나의 객체만을 생성하도록 할 수 있다. 

#### com.springbook.biz.Cls1.java
```java
public class Cls1{
    public int num; 
    public Cls1(){
        System.out.println("cls1 생성");   
    }
    
    public void destroyMethod(){
        System.out.println("객체 삭제 직전에 호출된 함수");
    }
}
```
#### ApplicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <bean id="cls1" class="com.springbook.biz.Cls1" scope="prototype"></bean>
</beans>
```
#### ContainerText.java
```java
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

class ContainerText{
    public static void main(String[] args){
        AbstractApplicationContext container = 
                            new GenericXmlApplicationContext("ApplicationContext");
        
        Cls1 cls1 = container.getBean("cls1");
        Cls1 cls1 = container.getBean("cls1");
        Cls1 cls1 = container.getBean("cls1");
    }
}
```
실행결과
```java
cls1 생성
cls1 생성
cls1 생성
```

scope의 속성은 prototype과 singleton이 있다. scope의 기본 속성값은 "singleton"이다.
singleton 속성은 단 하나의 객체만을 생성하게하고 prototype 속성은 여러 개의 객체를 생성하게 한다. 
생성하는 객체의 특성에 맞춰서 <bean> 엘리먼트의 scope 속성을 설정해주도록 하자.   

## 의존성 주입

### 의존성 관계란 ?
```java
public class Computer{
    private Mouse mouse;
    private KeyBoard keyBoard;
    private Moniter moniter;
    
    public void click(){
        mouse = new AppleMouse();
        mouse.click();
    }
}
```

윈도우 컴퓨터를 정상적으로 사용하려면 마우스가 있어야한다. 그렇기 떄문에 컴퓨터 클래스의 click() 메서드는 AppleMouse 클래스의 click()
메서드를 호출함으로서 정삭적으로 작동한다.

위의 컴퓨터와 마우스같이 코드들은 서로 의존관계를 가질수 있다. 하지만 이 의존관계들이 소스파일 안에 자바코드로 작성되어 있다면
나중에 AppleMouse가 아닌 SamsumgMouse로 클래스를 바꿔야 할 때 자바코드들을 전부 바꿔줘야해서 유지보수가 어려워 진다.

우리는 이 문제를 스프링 프레임워크의 컨테이너를 사용해서 의존관계를 설정해줌으로써 해결할 수 있다.


### 스프링 의존성 주입 방법(Inversion of Dependency)
1. Dependency Lookup
2. Dependency Injection
   1. 생성자(Constructor) 인젝션(Injection)
   2. 세터(Setter) 인젝션(Injection)
    

스프링의 의존성 주입 방법으로는 Dependency Lookup과 Dependency Injection이 있다.
Dependency Lookup은 위에서 사용했던 생성한 객체를 검색해서 사용하는 방식이다. 이 방식은 실제 웹 애플리케이션 개발 과정에서는
사용하지 않고 대부분 애플리케이션 개발에는 Dependency Injection 방식을 사용한다고 한다.

Dependency Injection은 컨테이너가 직접 객체들 사이의 의존관계를 처리하는 것을 말한다. Dependency Injection은
세터(Setter) 인젝션(Injection)과 생성자(Constructor) 인젝션(Injection)로 나뉜다.

### 생성자(Constructor) 인젝션(Injection)
의존성 관계를 설명하면서 사용했던 컴퓨터 클래스를 가져와봤다.

```java
public class Computer{
    private Mouse mouse;
    private KeyBoard keyBoard;
    private Moniter moniter;
    
    public void click(Mouse mouse){
        this.mouse = mouse;
        mouse.click();
    }
}
```
우리는 전에 코드를 이런식으로 바꿔서 좀 더 유지보수 용이하도록 작성할 수 있다. 그렇다면 컨테이너에서도 이렇게 사용할 수 있지 않을까 ?
우리가 컨테이너에서 객체를 생성할 때는 기본생성자로 생성할 수 밖에 없다고 알고있었다. 하지만 객체를 생성하는 생성자에게 매개변수를 넘겨줄 수 있는데 그것을
공부해 보자.

#### com.springbook.biz.Computer
```java
public class Computer{
    private Mouse mouse;
    private int price;
    public Computer(){
        System.out.println("Computer 생성");   
    }

    public Computer(Mouse mouse, int price){
        this.mouse = mouse;
        this.price = price;
        System.out.println("마우스달린 "+price+"만원 컴퓨터 생성");
    }
}
```

#### com.springbook.biz.Mouse
```java
public interface Mouse{
    public void click();
}
```

#### com.springbook.biz.AppleMouse
```java
public class AppleMouse implements Mouse{
    public AppleMouse(){
        System.out.println("AppleMouse 객체가 생성됩니다.");
    }
    public void click(){
        System.out.println("AppleMouse로 클릭합니다.");
    }    
}
```

#### ApplicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <bean id="computer" class="com.springbook.biz.Computer">
       <constructor-arg ref="AppleMouse"></constructor-arg>
       <constructor-arg value="1300000"></constructor-arg>
    </bean>
   <bean id="AppleMouse" class="com.springbook.biz.AppleMouse"></bean>
</beans>
```

#### ContainerTest.java
```java
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

class ContainerTest{
    public static void main(String[] args){
        AbstractApplicationContext container = 
                            new GenericXmlApplicationContext("ApplicationContext");
        
        Computer computer = container.getBean("computer");
        computer.click();
    }
}
```
실행결과
```java
AppleMouse 객체가 생성됩니다.
마우스달린 1300000만원 컴퓨터 생성
AppleMouse로 클릭합니다.
```
 <bean id="computer" class="com.springbook.biz.Computer">
    <constructor-arg ref="AppleMouse"></constructor-arg>
 </bean>
우선 이 코드부터 확인해보자. 이전과는 다르게 <bean> 엘리먼트 안에 <constructor-arg> 엘리먼트가 있는 것을 확인할 수 있다.
우리는 <constructor-arg> 엘리먼트를 사용해서 <bean> 엘리먼트의 생성자에게 기본형 매개변수나 참조형 매개변수 모두 줄 수 있다.
기본형 매개변수는 <constructor-arg>에 value라는 속성으로 줄 수 있고 참조형 매개변수는 <constructor-arg>에 ref속성으로 줄수가 있다.

위에 예제는 <constructor-arg>에 ref속성으로 AppleMouse를 적어 Computer의 생성자의 매개변수로 AppleMouse라는 객체를 넘겨준것이다.
또 <constructor-arg>의 value속성으로 1300000의 값을 Computer의 매개변수로 넘거주었다. 만약 Computer의 생성자가 기본 생성자밖에 없다면
에러가 발생할 것이다. 그 점을 주의하도록 하자.

#### 코드 진행 순서
1. xml 파일을 로딩하여 컨테이너가 실행
2. <bean> 엘리먼트의 객체들이 생성하기 위해서 <bean> 엘레먼트 확인
3. computer 객체를 생성하려면 AppleMouse 객체를 우선적으로 생성해야하기 때문에 Apple Mouse 객체 생성
4. AppleMouse 와 함께 1300000의 int 값을 매개변수로 넘겨 computer 객체 생성


### 세터(Setter) 인젝션(Injection)
의존성 주입의 다른 방법으로는 세터 인젝션이 있다. 생성자 인젝션이 생성자를 이용해서 의존성을 처리하는 것과는 달리 Setter
인젝션은 Setter 메소드를 호출하여 의존성 주입을 처리한다. 생성자 인젝션과 Setter 인젝션 모두 멤버변수를 원하는 값으로 설정하는
것을 목적으로 한다.

#### com.springbook.biz.Computer
```java
public class Computer{
    private Mouse mouse;
    private int price;
    public Computer(){
        System.out.println("Computer 생성");   
    }

    public void setMouse(Mouse mouse){
        this.mouse = mouse;
    }
    
    public void setPrice(int price){
        this.price = price;
   }
}
```

#### com.springbook.biz.Mouse
```java
public interface Mouse{
    public void click();
}
```

#### com.springbook.biz.AppleMouse
```java
public class AppleMouse implements Mouse{
    public AppleMouse(){
        System.out.println("AppleMouse 객체가 생성됩니다.");
    }
    public void click(){
        System.out.println("AppleMouse로 클릭합니다.");
    }    
}
```

#### ApplicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <bean id="computer" class="com.springbook.biz.Computer">
        <property name="mouse" ref="AppleMouse"></property>
       <property name="price" value="1300000"></property>
    </bean>
   <bean id="AppleMouse" class="com.springbook.biz.AppleMouse"></bean>
</beans>
```

#### ContainerTest.java
```java
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

class ContainerTest{
    public static void main(String[] args){
        AbstractApplicationContext container = 
                            new GenericXmlApplicationContext("ApplicationContext");
        
        Computer computer = container.getBean("computer");
        computer.click();
    }
}
```
실행결과
```java
AppleMouse 객체가 생성됩니다.
마우스달린 1300000만원 컴퓨터 생성
AppleMouse로 클릭합니다.
```

Setter 인젝션을 사용하려면 <property> 엘리먼트를 사용해야 하며 name속성값이 호출하고자 하는 메소드의 이름이다.
name값의 첫 문자를 대문자로 바꾸고 "set"을 앞에 붙인 것이 호출하는 메소드의 이름이다.

<property> 엘리먼트의 ref 속성은 생성자 인젝션과 같이 객체를 인자로 넘길 때 사용하고 value 속성은 기본형 데이터를 인자로
넘길 때 사용한다.

## 어노테이션 기반 의존관계 설정
의존관계를 설정하는 방법은 XML 파일에 의존관계를 적어주는 방법도 있지만 어노테이션을 사용해서 의존관계를 설정할 수 있다.
어노테이션 기반 의존관계 설정방법은 기회가 되면 적도록 하겠습니다.