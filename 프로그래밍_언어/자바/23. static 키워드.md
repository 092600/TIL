# static 키워드

한 클래스를 작성하면 클래스로부터 여러 개의 인스턴스를 만들 수 있다. 하지만 이렇게 여러 개의 인스턴스를 만드는 것이 과연 효율적일까 ? 당연히 아닐수도 있다. 어떠한 상황에서 효율적인지를 알기 위해서는 static 키워드에 대해서 우선적으로 알고 있어야 한다.

static 키워드를 사용하지 않고 아래와 같이 작성한 클래스의 필드나 메서드는 클래스의 인스턴스를 만든 경우에만 해당 인스턴스를 통해 필드 또는 메서드에 접근할 수 있다. 이처럼 static 키워드를 사용하지 않은 필드나 메서드를 "인스턴스 필드" 또는 "인스턴스 메서드"라고 한다.

- 인스턴스 필드 / 메서드  예시
	```java
	public class Student {
		String name;
		int age;
	}
	```

	하지만 인스턴스를 만들 필요가 없는 경우에는 static 키워드를 사용하여 정적 필드 또는 정적 메서드로 만들 수 있다.

	static 키워드란 객체를 생성하지 않고도 변수나 메서드를 사용할 수 있게 해주는데 이는 서버의 자원을 효율적으로 사용할 수 있도록 도와준다. 이해를 돕기위해 아래와 같이 School 클래스를 작성해보자.

- static 키워드 예시
	```java
	public class School {
		static String name = "송곡 고등학교";
	
		Student[] students = new Student[100];
	}
	```

	위와 같이 School 클래스를 static 키워드를 사용하여 String 타입의 name 필드를 작성한 경우, School 클래스의 인스턴스를 만들지 않고도 School 클래스의 name 필드에 접근할 수 있다. 이는 static 키워드를 사용해서 name 필드를 정적 필드로 만들어 주었기 가능한 일이다.

<br>

- School 클래스의 name 필드 출력하기
	```java
	System.out.println(School.name); // 송곡 고등학교
	```

	static 키워드는 필드 뿐만 아니라 메서드를 작성할 때도 사용할 수 있으며 메서드에 static 키워드를 사용하여 메서드를 정적 메서드로 정의한 경우, 인스턴스를 생성하지 않고도 해당 클래스의 메서드를 사용할 수 있다.

> **static 키워드를 사용한 정적 필드와 정적 메서드**는 클래스 로더가 클래스를 로딩하고 **메서드 메모리 영역에 적재할 때 클래스 별로 관리한다. **


## 클래스에 static 키워드 사용하여 정적 클래스 만들기

static 키워드는 클래스의 필드나 메서드 뿐 아니라 클래스를 정의할 때도 사용할 수 있다. 우선 아래의 예를 확인해보자.

- 정적 클래스
	```java
	public static class Calculator {

		static madeCompany = "Samsung";

		static int add(int a, int b) {
			return a + b;
		}
		
		static int minus(int a, int b) {
			return a - b;
		}
	}
	```

- Calculator 클래스 사용 예시
	```java
	Calculator.madeCompany; // Samsung
	Calculator.add(3, 5);
	Calculator.minus(3, 5);
	```


	위의 Calculator 클래스와 같이 static 키워드를 사용하여 정적 클래스를 만든 경우에는 해당 클래스의 필드와 메서드는 항상 정적 필드, 정적 메서드로 정의되어야 한다. 인스턴스를 생성하여 필드나 메서드, 인스턴스 필드나 메서드로 정의할 경우 정적 클래스로 만든 이유가 없어지기 때문이다.



	











