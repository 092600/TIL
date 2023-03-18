
# 1. JVM(Java Virtual Machine)이란 ?

자바가 OS에 종속적이지 않을 수 있었던 이유는 자바 애플리케이션과 OS 사이에 자바 파일을 실행해주는 JVM이 존재하기 때문입니다. 자바 소스파일(.java)을 실행하려면 .java 파일을 JVM이 이해할 수 있는 바이트코드로 변환해주어야 하며 변환해주는 작업을 자바 컴파일러(Java Compiler)가 수행합니다.

> 바이트 코드란 ?
> VM(Virtual Machine, 가상머신)이 이해할 수 있는 언어로, 자바 바이트 코드는 자바 가상 머신(Java Virtual Machine)만 설치되어 있으면 어떠한 운영체제에서라도 실행될 수 있습니다.

## 1-1. JVM 구조

- JVM 구조 이미지
![](https://velog.velcdn.com/images/092600/post/ba828552-c510-4274-ae74-b0f93cb00677/image.png)

<br>

## 1-2. 클래스 로더(Class Loader)란 ?



클래스 로더는 런타임(RunTime) 시점에 JVM이 운영체제로부터 할당받은 메모리 영역인 Runtime Data Area에 생성된 클래스 파일들을 동적으로 로드하는 역할을 합니다.

- 클래스 로더 시스템
![](https://velog.velcdn.com/images/092600/post/cba39f17-8491-4192-a1a5-afd71d3b2f11/image.png)

클래스 로더는 로딩, 링크, 초기화의 순서로 진행되며 **.class 파일을 읽고, 해당 내용을 바이너리 데이터로 만들고 메소드 영역에 저장하는 "로딩"** 작업과 3단계로 진행나뉘어 **"링킹"** 작업, 마지막으로 **"초기화 작업"**을 진행합니다

> 로딩 과정에서는 .class 파일이 JVM 스펙에 맞는지 확인하고 Java Version을 확인한다.



<br>

## 1-2-1. 클래스 로더의 로딩(Loading)

### 1-2-1-1. **Bootstrap Class Loader** / Native C로 구현됨

JVM 시작 시 가장 먼저 실행되는 클래스 로더로, **JVM을 구동시키기 위한 가장 필수적인 라이브러리의 클래스들을 JVM에 로드합니다.** 

- **Java 8**
jre/lib/rt.jar 및 기타 핵심 라이브러리와 같은 JDK 내부 클래스를 로드한다.

- **Java 9 이후**
rt.jar이 모듈화되어 java.base와 같은 중요한 모듈의 클래스 로딩만 다루게 되었다.


<br>


### 1-2-1-2. **Extensions Class Loader** / Java로 구현됨

Extensions Class Loader는 Bootstrap Class Loader 다음의 우선순위를 가지는 클래스 로더로 확장 자바 클래스들을 로드합니다.

표준 핵심 Java Class의 라이브러리들을 JVM에 탑재 하는 역할을 하고 있으며, 이 라이브러리들은 $JAVA_HOME/jre/lib/ext 에 있습니다.

- **JAVA 8**
${JAVA_HOME}/jre/lib/ext 에 있는 클래스 파일들을 로드한다.

- **JAVA 9**
Platform Class Loader로 변경되었으며 URLClassLoader가 아닌 Internal 클래스

<br>

### 1-2-1-3. **Application Class Loader** / Java로 구현됨

Application Class Loader는 Classpath 에 있는 클래스들을 로딩하며, 개발자들이 자바 코드로 작성한 클래스 파일들을 JVM에 로딩하는 역할을 하고 있습니다. 

- **Java 8**
자바 8버전에서는 Application ClassLoader 라고 부른다.

- **Java 9** 
자바 9버전부터는 System ClassLoader라고 부른다

<br>
<br>
<br>

## 1-2-2. 클래스 로더의 3가지 원칙 

<br>

### 1-2-2-1. 위임 원칙(Delegation Principle)

클래스 로더의 위임 원칙은 클래스 로딩이 필요한 경우, 부모 클래스 로더의 방향으로 클래스 로딩을 위임하는 것입니다. 아래는 개발자가 직접 작성한 Internal 클래스를 로딩하는 과정을 나타낸 이미지입니다.

- 위임 법칙 예시
![](https://velog.velcdn.com/images/092600/post/1c6572b9-2354-4f4b-b0ef-0da81529be31/image.png)

- Internal 클래스를 로딩하는 과정
1. ClassLoaderRunner는 자신을 로딩한 애플리케이션 클래스로더에게 Internal 클래스 로딩을 요청합니다.
2. 클래스 로딩 요청을 받은 애플리케이션 클래스로더는 Internal 클래스를 직접 로딩하지 않고 상위 클래스로더인 익스텐션 클래스로더에게 요청을 위임합니다.
3. 애플리케이션 클래스로더에게 요청을 위임받은 익스텐션 클래스로더 또한 자신보다 상위 클래스로더인 부트스트랩 클래스로더에게 요청을 위임합니다.
4. 부트스트랩 클래스로더는 rt.jar 에서 Internal 클래스를 찾고, rt.jar 에 해당 클래스가 존재한다면 로딩 후 반환합니다.
5. 부트스트랩 클래스로더가 Internal 클래스를 찾지 못했을 경우, 익스텐션 클래스로더는 /jre/lib/ext 폴더나 java.ext.dirs 환경 변수로 지정된 폴더에서 Internal 클래스를 찾고, 해당 클래스가 존재한다면 로딩 후 반환합니다.
6. 익스텐션 클래스로더도 Internal 클래스를 찾지 못한 경우, 애플리케이션 클래스로더는 classpath에서 Internal 클래스를 찾고 존재한다면 반환 없으면 ClassNotFoundException이 발생합니다.

<br>

### 1-2-2-2. 가시범위 원칙(Visibility Principle)

가시 범위 원칙은 하위 클래스로더는 상위클래스로더가 로드한 클래스를 볼 수 있지만, 반대로 상위 클래스로더는 하위 클래스로더가 로드한 클래스를 알수 없도록 하는 법칙으로 **해당 원칙이 존재하지 않으면 클래스 로더간의 상/하위 개념이 사라지기 때문에 이를 방지하기 위해 꼭 지켜져야하는 원칙**입니다.

<br>

### 1-2-2-3. 유일성 원칙(Uniqueness Principle)

유일성 원칙은 하위 클래스로더가 로드한 클래스를 상위 클래스로더가 다시 로드하지 않아야한다는 원칙으로 하나의 클래스는 단 한번만 로드되어야 합니다.

<br>

---

<br>

## 1-2-3. 클래스 로더의 링킹(Linking)

로딩된 클래스는 Vertify -> Prepare -> Resolve 의 3단계의 링킹과정을 거치게됩니다.

### 1-2-3-1. Vertify

클래스 로더가 읽은 클래스의 바이너리 데이터가 유효한 것인지 확인한다.

### 1-2-3-2. Prepare

클래스의 static 변수나 기본값에 필요한 메모리 공간을 준비한다.

### 1-2-3-3. Resolve

사용하는 환경에 따라 동작 유무가 정해지며, 심볼릭 메모리 레퍼런스를 메소드 영역에 있는 실제 레퍼런스로 교체한다.

<br>

--- 



## 1-2-4. 클래스 로더의 초기화(Initialization)

클래스 초기화는 클래스 로딩 시점에 함께 수행되며, static 블록과 static 멤버 변수의 값을 할당하는 것을 의미합니다. 

<br>

---


<br>
<br>
<br>
<br>

# 0. 직접 확인해보기

## - 클래스 로드 해보기

- 클래스 로드 코드
```
> java -classpath "클래스 파일 위치" -verbose:class "클래스명"
```

- 클래스 로드 화면
![](https://velog.velcdn.com/images/092600/post/93bb498a-940e-4586-8201-a43a64694266/image.gif)

--- 

<br>
<br>

## - /jre/lib/rt.jar 파일 확인해보기

- 작성 코드
  ```
  cd $JAVA_HOME/jre/lib/
  vim /rt.jar
  ```

<br>

- 코드 실행 화면
	![](https://velog.velcdn.com/images/092600/post/cddeb4f0-ae32-4e28-9b3d-ad05966ddcb8/image.gif)

--- 

<br>
<br>

## - /jre/lib/ext 안의 파일 확인하기

- 작성 코드
  ```
  cd $JAVA_HOME/jre/lib/ext
  ```

<br>

- 코드 실행 화면
	![](https://velog.velcdn.com/images/092600/post/7e2ae0ad-e6f8-4963-9bd4-8923bdf901f9/image.gif)

--- 
<br>
<br>


    
> 참고 URL
1. https://tecoble.techcourse.co.kr/post/2021-07-15-jvm-classloader/
2. https://homoefficio.github.io/2018/10/13/Java-%ED%81%B4%EB%9E%98%EC%8A%A4%EB%A1%9C%EB%8D%94-%ED%9B%91%EC%96%B4%EB%B3%B4%EA%B8%B0/
3.https://docs.oracle.com/javase/9/docs/api/java/lang/ClassLoader.html#builtinLoaders
4. https://docs.oracle.com/javase/9/migrate/toc.htm#JSMIG-GUID-D867DCCC-CEB5-4AFA-9D11-9C62B7A3FAB1
5. https://inpa.tistory.com/entry/JAVA-%E2%98%95-%ED%81%B4%EB%9E%98%EC%8A%A4%EB%8A%94-%EC%96%B8%EC%A0%9C-%EB%A9%94%EB%AA%A8%EB%A6%AC%EC%97%90-%EB%A1%9C%EB%94%A9-%EC%B4%88%EA%B8%B0%ED%99%94-%EB%90%98%EB%8A%94%EA%B0%80-%E2%9D%93#thankYou