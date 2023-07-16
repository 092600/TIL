# 의존한다?

A 객체가 B 객체에 의존한다. 즉 A 객체와 B 객체가 의존관계가 있다는 것은 B 객체의 변경사항이 A 객체에 영향을 미친다는 것을 의미하며 반대로 의존하지 않는다는 것은 객체의 변경사항이 다른 객체에 영향을 주지 않는 다는 것을 의미한다.

> 의존관계는 방향성이 존재한다.

의존관계를 설정할 때 특정 클래스를 지정하여 설정해주는 것이 아닌 인터페이스를 사용함으로써 느슨한 결합, 결합도가 낮은 코드를 작성할 수 있다.

- 특정 클래스와 의존관계를 갖는 것
	```java
	public class Home {
		private Tv tv;
	}
	
	public class Tv {
	
	}
	```

- 인터페이스를 사용한 의존관계 설정
```java
public class Home {
	private Tv tv;
	// private Tv tv = new LgTv(); 
	// private Tv tv = new SamsungTv();

}

public interface Tv {
	...
}

public class SamsungTv implements Tv {
	...
}

public class LgTv implements Tv {
	...
}
```

<br>
## 의존성 주입의 개념

의존관계 주입은 인터페이스를 사이에 두어 클래스 레벨에서의 의존관계가 고정되지 않게 하고, 런타임 시 의존할 오브젝트와의 관계를 다이나믹하게 주입해주는 것이다. 그렇기 때문에 인터페이스를 사용하지 않은 경우 엄밀하게 말하자면 온전한 DI라고는 할 수 없으나, 스프링의 DI 관점에서는 객체의 생성과 관계설정에 대한 제어권한을 오브젝트에서 제거하고 외부로 위임했다는 점에서 IoC 라는 개념을 포괄하기 때문에 인터페이스를 사용하지 않고 클래스를 사용하더라도 DI의 기본을 따르고 있다고 볼 수 있다. 또한 인터페이스가 없이 클래스간 결합되어 있다는 것은 그 두 클래스가 강하게 결합되어 있다는 것을 의미한다.


## 1. 런타임 의존관계

위에서와 같이 코드로 의존관계를 설정하는 것과 달리 런타임 시에 오브젝트 사이에서 만들어지는 의존관계가 만들어지는 경우도 존재하며 이러한 의존관계를 런타임 의존관계라고 한다. **설계 시점의 의존관계가 실체화된 것이라고 볼 수 있다.**

위의 예에서 Tv 인터페이스를 구현한 SamsungTv와 LgTv 중 특정 클래스를 사용할 것이라고 미리 정해놓을 수 있지만 실제로 어떠한 클래스를 사용할 지 코드에서는 드러내놓지 않으며, 런타임 시점에 의존관계를 맺도록 설정할 수 있다. 

> 실제 사용대상인 오브젝트를 의존 오브젝트라고 한다.


## 2. 의존관계 주입 조건

1. 런타임 시점의 의존관계가 코드에 드러나지 않는다. (인터페이스에 의존하고 있음)
2. 런타임 시점의 의존관계는 컨테이너나 팩토리 같은 제 3의 존재가 결정한다.
3. 의존관계는 사용할 오브젝트에 대한 레퍼런스를 외부에서 제공해줌으로써 만들어진다. (ex. IoC 컨테이너) 

### 2-1. 의존관계 주입 코드

```java
public class Home {
	private Tv tv;

	public Home(Tv tv) {
		this.tv = tv;
	}
}
```

위의 코드에서는 Home 클래스의 생성자를 통해서 Home 객체가 사용할 Tv 객체의 초기화가 이루어지고 있으며, DI 컨테이너가 메서드(생성자)를 통해서 Home 객체에게 Tv 인터페이스를 구현한 클래스를 주입해주는 것과 같다는 의미로 의존관계 주입이라고 부른다.

이렇게 DI (Dependency Injection)은 사용할 오브젝트에 대한 선택 및 생성 제어권을 외부로 넘겨 수동적으로 주입받은 오브젝트를 사용한다는 점에서 IoC 개념에 잘 들어맞는다.


## 3. 의존관계를 주입하는 방법

### 3-1. 메서드 사용

#### Setter 메서드 사용

```java
public class Home {
	private Tv tv;

	public void setTv(Tv tv) {
		this.tv = tv;
	}
}
```

#### 생성자 사용
```java
public class Home {
	private Tv tv;

	public Home(Tv tv) {
		this.tv = tv;
	}
}
```



### 3-2. XML 사용

@Configuration 어노테이션과 @Bean 어노테이션을 사용하는 방법은 의존관계가 변경될 때마다 코드를 수정해주고 수정된 클래스를 컴파일 해주어야한다는 단점이 존재한다. 하지만 XML을 사용하면 자바 코드를 수정하거나 별도의 컴파일 과정없이 의존관계 수정이 가능하다.

> 어노테이션을 사용한 의존성 주입과 XML 을 사용한 의존성 주입 
> - @Configuration : <beans>
>  - @Bean : <bean>

- XML을 사용한 빈 등록 예시
	```xml
	<beans>
		<bean class="my.spring.domain.Home" id="home">
			<property name="tv" ref="tv"/>
		</bean>
		<bean class=my.spring.domain.SamsungTv id="tv"></bean>
	</beans>
	```

### 3-3. GenericXmlApplicationContext vs ClassPathXmlApplicationContext

> - GenericXmlApplicationContext는 항상 루트에서부터 시작하는 클래스패스를 기준으로 xml 파일을 찾는다.
> - ClassPathXmlApplicationContext는 Class 위치를 기준으로 xml파일을 찾는다.




