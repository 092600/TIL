# 열거 타입

열거 타입은 한정된 값인 열거 상수(Enumeration Constant) 중 하나의 상수를 저장하는 타입이다. 열거 타입 또한 참조 타입이다.

- 열거 타입 사용 예시
	```java
	public enum Week {  
	    MON, TUE, WED, THU, FRI, SAT, SUN;  
	}
	```

	위와 같이 열거 타입을 선언하고 열거 상수를 선언할 수 있다. 열거 상수는 모두 대문자로 작성한다. 

## 열거 타입 사용 예시

### valueOf()

- Enum.valueOf() 사용 예시
	```java
	System.out.println(Week.valueOf("MON"));
	System.out.println(Week.valueOf("HELLO")); // Week Enum에 정의되지 않은 열거 상수라 Error 발생
	```

	valueOf()의 인자로 들어온 문자열 리터럴과 같은 열거 상수를 반환한다.

### values()

- Enum.valueOf() 사용 예시
	```java
	Week[] days = Week.values(); // Enum Week 의 열겨 상수로 이루어진 배열 생성
	```

	values 는 Enum의 열거 상수로 이루어진 배열을 반환한다.
### name()

- name() 사용 예시
	```java
	System.out.println(Week.MON.name());
	```

	Enum의 열거 상수의 name 값을 반환한다.

### ordinal()

- ordinal() 사용 예시
	```java
	System.out.println(Week.MON.ordinal());
	```

	ordinal() 메서드는 열거 상수의 ordinal 값을 반환한다. ordinal 값은 열거 상수의 순서에 따라 정해지는 정수 값으로 배열의 인덱스 값과 같이 0부터 시작하여 열거 상수 개수 -1까지 존재한다. ordinal 값은 열거 상수의 위치에 따라서 달라지기 때문에 사용하는 것에 주의해야한다.