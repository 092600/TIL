> 본문의 내용은 SpringQuickStart 책을 읽은 후 작성한 내용입니다.

# Day2

--- 

> 비즈니스 컴포넌트 개발에서 가장 중요한 두 가지 원칙은 낮은 결합도와 높은 응집도를 유지하는 것이다.
> 낮은 결합도를 위해서 IoC를 사용하고 높은 응집도 위해서 AOP를 사용한다. 

애플리케이션의 메소드들은 핵심 비즈니스 로직과 예외, 트랜젝션, 로깅 등 부가적인 코드들로 구성된다.
핵심 비즈니스 로직은 몇 줄이 되지않는데 반해 예외, 로킹, 트랜젝션 처리 등과 같은 부가적인 코드들이 대부분을 차지한다.
이렇게 많은 양의 부가적인 코드들은 개발자의 개발 및 유지보수를 힘들게한다. 하지만 부가적인 코드들을 소홀히 할 수 없는데
이 코드들은 핵심 로직과 비슷하게 중요한 기능을 하기 때문이다.

그렇다고 이러한 코드들을 그대로 방치해두면 문제가 생긴다. 예를 보면서 생각해보도록 하겠습니다.
```JAVA
public class Test{
    public static void main(String[] args){
        System.out.println("핵심코드1");
        System.out.println("핵심코드2");
        Log.logging(); // 로그 기능을 하는 함수
        Exceptions.Excepting(); // 예외처리 기능을 하는 함수
        
        System.out.println("핵심코드3");
        System.out.println("핵심코드4");
        Log.logging(); // 로그 기능을 하는 함수
        Exceptions.Excepting(); // 예외처리 기능을 하는 함수
        
        System.out.println("핵심코드5");
        Log.logging(); // 로그 기능을 하는 함수
        Exceptions.Excepting(); // 예외처리 기능을 하는 함수
    }
}
```
위의 코드처럼 핵심 코드들을 마치고 로그기능을 하는 함수나 예외처리를 하는 함수들이 작동해야할 수 있다.
부가적인 코드들은 애플리케이션에 꼭 필요로 하는 코드들이지만 이렇게 반복적으로 작성된다면 코드를 보는데도 쉽지 않고
새로운 기능을 기발하거나 유지보수에도 좋지 않은 영향을 끼친다. 스프링 프레임워크는 AOP를 통해서 이러한 문제를 해결한다.


## AOP란 ?
AOP를 이해하기 위해선 우선 가장 중요한 개념이 바로 관심분리(Separation of Concerns)이다.
관심분리는 메솓마다 공통으로 등장하는 로깅이나 예외처리, 트랜젝션 처리 같은 코드들을 뜻하는 횡단관심과
사용자의 요청에 따라 실제로 수행되는 핵심 비즈니스 로직을 뜻하는 핵심관심으로 나뉘게 된다.

이 두 관심을 완벽하게 분리한할 수 있다면 구현하는 메소드들을 핵심 기능을 구현하는 로직들로만
구성할 수 있으므로 더욱 간결하고 응집도 높은 코드를 작성할 수 있다. 

하지만 기존의 OOP(Operating-Oriented Programming) 언어에서는 횡단 관심의 코드들을 완벽하게 독립적인 모듈로
분리하기 어렵다.

예를 보면서 생각해보도록 하자.
우리가 위에서 봤던 Log.logging(); 메서드를 Logging.writingLog() 메서드로 바꾸고 Exceptions.Excepting() 메서드를
Except.ExceptionProcessing() 메서드로 바꿔보도록 하자.
```JAVA
public class Test{
    public static void main(String[] args){
        System.out.println("핵심코드1");
        System.out.println("핵심코드2");
        Logging.writingLog(); // 바꾼 로그 기능을 하는 함수
        Except.ExceptionProcessing(); // 바꾼 예외처리 기능을 하는 함수
        
        System.out.println("핵심코드3");
        System.out.println("핵심코드4");
        Logging.writingLog(); // 바꾼  로그 기능을 하는 함수
        Except.ExceptionProcessing(); // 바꾼 예외처리 기능을 하는 함수
        
        System.out.println("핵심코드5");
        Logging.writingLog(); // 바꾼 로그 기능을 하는 함수
        Except.ExceptionProcessing(); // 바꾼  예외처리 기능을 하는 함수
    }
}
```

이렇게 기존에 쓰던 로그처리 메서드나 예외처리 메서드를 새로운 메서드로 교체하려면 이 메서드가 사용된 모든 부분을 새로운 메서드로
바꿔줘야한다. 이렇게 작성된 코드들의 양이 많다면 실수로 몇 개의 메서드를 바꾸지 못할 가능성도 생긴다. 코드 간의 결합력이 강해서
생기는 어쩔수 없는 문제인 것이다. 스프링 프레임워크는 이러한 횡단관심을 완벽하게 분리할 수 있도록 도와준다.

### # AOP 들어가기 전
AOP를 구현하는 방법은 XML 파일을 통해 구현하는 방법과 어노테이션을 통해서 구현하는 방법으로 나뉘어진다.
두 가지 방법을 모두 사용해서 유연하게 AOP를 구현하는게 좋지만 여기서는 XML 파일을 통해서 구현하는 방법만을 적도록
하겠다.

### AOP 용어

1. **조인포인트(Joinpoint)**

조인포인트는 클라이언트가 호출하는 모든 비즈니스 메소드이다.
2. **포인트컷(Pointcut)**

조인포인트 중에서 사용자가 사용할 메소드들을 포인트컷이라고 한다. 쉽게 말하면 필터링된 조인 포인트이다.
3. **어드바이스(Advise)**

어드바이스란 횡단 관심에 해당하는 공통 기능의 코드를 의미하며 독립된 클래스의 메소드로 작성된다.
4. **위빙(Weaving)**

포인트 컷으로 지정한 핵심 관심 메소드가 호출될 떄, 어드바이스에 해당하는 횡단 관심 메소드가 삽입되는 과정을 의미한다.
5. **애스팩트(Aspect)**

AOP의 핵심으로 애스팩트는 포인트컷과 어드바이스의 결합으로서, 어떤 포인트컷 메소드에 어떤 어드바이스 메소드를 실행할지 결정한다.


## AOP를 사용하기
AOP를 적용하기 위해선 프로젝트에 있는 pom.xml 파일에서 AOP관련 라이브러리를 추가하기위한 수정과정을 거쳐야한다.
아래의 설정은 내가 AOP를 사용하기 위해서 작성한 코드다.

pom.xml에 <dependency> 엘리먼트 안에 아래의 코드들을 작성해 주면 된다.
```xml
<dependency>	
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjrt</artifactId>
    <version>${org.aspectj-version}</version>
</dependency>
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.8.8</version>
</dependency>
```

pom.xml 파일에 위의 코드들을 작성한 후, 스프링 설정 파일(ApplicationContext.xml)에서 namespace 탭을 클릭한 후
AOP 네임스페이스를 추가하도록 하자.

우리는 XML 파일을 통한 AOP를 작성하는 방법에 대해서만 공부할 것이다. 그렇다면 AOP를 사용하기 위해서 XML 파일을 수정하러 가보자.
나는 ApplicationContext라는 이름의 xml 파일을 사용한다.

ApplicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:aop="http://www.springframework.org/schema/aop"
        xmlns:p="http://www.springframework.org/schema/p"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.3.xsd">
    
</beans>
```

### < aop:config > 엘리먼트
AOP 설정에서 < aop:config > 는 루트엘리먼트다. < aop:config >는 여러 번 사용할 수 있으며 < aop:config > 하위에는
< aop:poingcut > 같은 엘리먼트나 < aop:aspect >와 같은 엘리먼트를 작성하면 된다.

### < aop:pointcut > 엘리먼트
< aop:pointcut > 엘리먼트는 < aop:config > 안에서 포인트컷을 지정하기 위해서 사용되는 엘리먼트다. 이 엘리먼트의 속성으로는 유일한 아이디값을
부여해서 각 포인트컷을 구분할 수 있다.

### 포인트컷 표현식
#### 1. 리턴타입 지정

> * : 모든 리턴타입 허용
> 
> void : 리턴타입이 void인 메소드 선택
>
> !void : 리턴타입이 void가 아닌 메소드 선택

#### 2. 패키지 지정
> com.springbook.biz
> 
: 정확하게 com.springbook.biz 패키지만 선택  
 
> com.springbook.biz.. 

: com.springbook.biz 패키지로 시작하는 모든 패키지 선택

> com.springbook.biz..test
 
: com.springbook.biz 패키지로 시작하면서 마지막 패키지 이름이 test로 끝나는 패키지 선택

#### 3. 클래스 지정
> ClassName

: 정확하게 ClassName의 클래스만 선택
> *test

: 클래스 이름이 test로 끝나는 클래스 선택 
> ClassName+

: ClassName의 클래스로부터 + 가 붙으면 파생된 모든 자식 클래스 선택, 인터페이스 뒤에 + 가 붙으면 해당 인터페이스를 구현한 모든 클래스 선택 

#### 4. 메소드 지정

> *(..) : 가장 기본 설정으로 모든 메소드 선택
> 
> get*(..) : 메소드 이름이 get으로 시작하는 모든 메서드 선택

#### 5. 매개변수 지정

> (..)

: 매개변수의 개수와 타입에 제한이 없음
> (*) 

: 반드시 1개의 매개변수를 가지는 메소드만 선택
 
> (com.springbook.biz.test)
 
: 매개변수로 test를 가지는 메소드만 선택, 이떄 클래스의 패키지 경로가 들어가야한다.
 
> (!com.springbook.biz.test)
 
: 매개변수로 test를 가지지않는 메소드만 선택, 이떄 클래스의 패키지 경로가 들어가야한다.

> (Integer, ..) 

: 한 개 이상의 매개변수를 가지되, 첫번째 매개변수가  Integer 타입인 한 개 메소드만 선택 
> (Integer, *)

: 두 개 이상의 매개변수를 가지되, 첫번째 매개변수가 Integer 타입인 한 개 메소드만 선택

### < aop:aspect > 엘리먼트
< aop:aspect >는 애스팩트를 설정하는 엘리먼트로 핵심 관심에 해당하는 포인트컷 메소드와 횡단 관심에 해당하는 어드바이스 메소드를 결합
하기 위해서 사용한다. < aop:aspect >는 어드바이스 객체의 아이디값이나 메소드 이름을 알지 못하는 경우에는 사용할 수 없다.  

### < aop:advise > 엘리먼트
< aop:advise >는 트랜잭션 설정 같은 몇몇 특수한 경우에서 사용하는 엘리먼트다. < aop:aspect > 엘리먼트는 어드바이스의 아이디
와 메소드 이름을 알아야한다. 그렇기 떄문에 어드바이스의 아이디 값이나 메소드 이름을 알지 못하는 경우에는 < aop:advise > 엘리먼트를 사용한다.


### 어드바이스 동작 시점 
> 1. **Before**  : 비즈니스 메소드 실행 전 동작
> 2. **After**
>    1. After Returning : 비즈니스 메소드가 성공적으로 리턴되면 동작
>    2. After Throwing : 비즈니스 메소드가 실행 중 예외가 발생했을 떄 동작
>    3. After : 비즈니스 메소드가 실행된 후, 무조건 실행
> 3. **Around** : 메소드 호출 자체를 가로채 비즈니스 메소드 실행 전후에 처리할 로직을 삽입할 수 있음

