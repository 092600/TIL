> 자바 ORM 표준 JPA 프로그래밍을 읽고 작성한 내용입니다.
>

<br>
<br>

# 객체지향 쿼리 언어

<br>

JPA는 복잡한 검색 조건을 사용해서 엔티티 객체를 조회할 수 있는 다양한 쿼리 기술을 지원한다.
이전에 간단하게 얘기를 했었던 JPQL(Java Persistence Query Language)은 가장 중요한 객체지향 쿼리 언어로
JPA를 다루는 개발자라면 필수로 공부해야 하는 기술이다.

<br>

EntityManager.find() 메서드를 사용하면 **식별자로 엔티티 하나를 조회할 수 있다.**
이렇게 조회한 엔티티에 객체 그래프 탐색을 사용하여 연관된 엔티티 또한 찾을 수 있으며 이 둘은 가장 단순한 검색 방법이다.
하지만 이 방법만으로는 많이 부족하기 떄문에 JPQL이 만들어졌다.

<br>

- 테이블이 아닌 객체를 대상으로 검색하는 객체지향 쿼리
- SQL을 추상화하여 특정 데이터베이스 SQL에 의존하지 않는다.

<br>

**SQL이 데이터베이스의 테이블을 대상으로 하는 데이터 중심의 쿼리라면 JPQL은 엔티티 객체를 대상으로 하는 객체지향 쿼리이며, JPA는 JPQL을 분석한 후 적절한 SQL을 만들어 데이터베이스를 조회한다. 그리고 조회한 결과로 엔티티 객체를 생성해서 반환한다.**

<br>
<br>

## JPA가 지원하는 다양한 검색 방법

<br>

### JPQL (Java Persistence Query Language)

JPQL은 엔티티 객체를 조회하는 객체지향 쿼리로 문법은 SQL과 비슷하고 ANSI 표준 SQL이 제공하는 기능을 유사하게 지원한다. 또한 JPQL은 SQL을 추상화하여 특정 데이터베이스에 의존하지 않는다.

<br>

### Criteria 쿼리

Criteria는 JPQL을 생성하는 빌더 클래스로 문자가 아닌 프로그래밍 코드로 JPQL을 작성할 수 있기 떄문에 문자로 JPQL을 작성했을 떄 오타가 있음에도 불구하고 컴파일에 성공한다는 문제를 해결할 수 있다. 하지만 매우 복잡하고 장황해 코드가 한눈에 들어오지 않는 단점이 있다.

<br>

### QueryDSL

QueryDSL도 Criteria처럼 JPQL 빌더 역할을 한다. 하지만 Criteria에 비해 단순하고 사용하기 쉽다는 장점이 있다.

<br>

### 네이티브 SQL

SQL을 직접 사용할 수 있는 기능을 JPA에서는 제공하는데 이걸 네이티브 SQL이라고 한다.

**JPQL을 사용해도 가끔 특정 데이터베이스에 의존하는 기능을 사용할 때가 있는데 이런 기능들은 전혀 표준화되어 있지 않기떄문에 JPQL에서 사용할 수 없다. 그리고 SQL은 지원하지만 JPQL에서는 지원하지 않는 기능도 있는데 이럴때 네이티브 SQL을 사용하면 된다.** 하지만 특정 데이터베이스에서만 지원하는 SQL을 사용할 때 데이터베이스를 변경하면 네이티브 SQL도 변경해야한다는 단점이 있다.


## JPQL

JPQL에 대해 자세하게 공부하기전에 JPQL이가 진 특징을 다시 한번 정리해보자.

<br>

- JPQL은 객체지향 쿼리 언어기 떄문에 테이블을 대상으로 쿼리하는 것이 아닌 엔티티 객체를 대상으로 쿼리한다.
- JPQL은 SQL을 추상화해서 특정 데이터베이스 SQL에 의존하지 않는다.
- JPQL은 결국 SQL로 변환된다.

<br>


## JPQL 기본 문법과 쿼리 API

JPQL 에서는 SQL과 비슷하게 SELECT, UPDATE, DELETE 문을 사용할 수 있다. 여기서 **UPDATE, DELETE 문은 벌크 연산이라고 한다.**

<br>

### SELECT

<br>


- SELECT 문 사용 예시
    ```java
    SELECT m FROM Member AS m where m.username = 'Hello'
    ```

<br>

- JPQL 특징
    1. 엔티티와 속성은 대소문자를 구분한다. 

        위의 Member, username은 대소문자를 구분하는 반면에 SELECT, FROM, AS 와 같은 JPQL 키워드는 대소문자를 구분하지 않는다. 

    <br>

    2. 엔티티 이름을 사용한다.

        JPQL에서 사용한 Member는 클래스명이 아닌 엔티티 명이다. 엔티티 명을 지정하지 않으면 클래스명을 기본값으로 사용한다.

    <br>

    3. 별칭은 필수다.

        Member AS m을 보면 Member에 m이라는 별칭을 주었다. JPQL은 별칭을 필수로 사용해야하기 떄문에 다음과 같이 코드를 별칭없이 작성하면 잘못된 문법이라는 오류가 발생한다.

        ```java
        SELECT username FROM Member m // 잘못된 문법, AS는 생략 가능
        SELECT m.username FROM Member m // 옳은 문법, AS는 생략 가능
        ```
<br>

### TypeQuery, Query

작성한 JPQL을 실행하려면 쿼리 객체를 만들어야 한다. 여기서 쿼리 객체란 TypeQuery와 Query를 말하며 반환할 타입을 명확하게 지정할 수 있으면 TypeQuery, 명확하게 지정할 수 없으면 Query를 사용한다.

- TypeQuery 사용 예

    ```java
    TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);
    ```
    
    em.createQuery()의 두 번째 파라미터에 반환할 타입을 지정하면 TypeQuery를 반환하고 지정하지 않으면 Query를 반환한다. 위의 예시에서는 Member 엔티티를 조회하므로 조회 대상 타입이 명확하므로 TypeQuery를 사용할 수 있다.

- Query 사용 예

    ```java
    Query query = em.createQuery("SELECT m.username, m.age FROM Member m", Member.class);
    ```

    위의 예시에서는 String 타입의 username과 Integer 타입의 age이므로 조회 대상 타입이 명확하지 않다. 이처럼 반환할 타입이 명확하지 않을땐 Query 객체를 사용해야 한다.

    Query 객체sms SELECT 절의 조회 대상이 둘 이상이면 Object[]를 반환하고 SELECT 절의 조회 대상이 하나면 Object를 반환한다.


<br>
<br>

## 파라미터 바인딩

<br>

위치 기준 파라미터 바인딩만 지원하는 JDBC와는 달리 JPQL은 이름 기준 파라미터 바인딩과 위치 기준 파라미터 바인딩도 지원한다.

<br>

### 이름 기준 파라미터 바인딩

이름 기준 파라미터는 파라미터를 이름으로 구분하는 방법으로 이름 기준 파라미터는 앞에 :를 사용한다.

<br>

```java
String usernameParam = "User1";

TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m WHERE m.username = :username", Member.class);

query.setParameter("username", usernameParam);
List<Member> resultList = query.getResultList();
```

<br>

위의 예제에서는 :username이라는 이름 기준 파라미터를 정의하고 query.setParameter()를 통해 username이라는 이름으로 파라미터를 바인딩한다.


<br>

### 위치 기준 파라미터 바인딩

위치 기준 파라미터를 사용하려면 ? 다음위치 값을 주면 된다. 위치 값은 1부터 시작한다.

<br>

```java
List<Member> members = 
em.createQuery("SELECT m FROM Member m where m.username = ?1", Member.class)
    .setParameter(1, usernameParam)
    .getResultList();
```

<br>

위치 기준 파라미터보다는 이름 기준 파라미터 바인딩 방식을 사용하는 게 좀 더 명확하다.

<br>

## 프로젝션

SELECT 절에 조회할 대상을 지정하는 것을 프로젝션(Projection)이라 하고, [SELECT {프로젝션 대상} FROM]으로 대상을 선택한다. 프로젝션 대상은 엔티티, 엠베디드 타입, 스칼라 타입이 존재하며 스칼라 타입은 숫자, 문자 등 기본 데이터 타입을 의미한다.

<br>

### 엔티티 프로젝션

<br>

```java
SELECT m FROM Member m      // 회원
SELECT m.team FROM Member m // 팀
```

<br>

위의 예에서는 처음은 회원을 조회했고 두 번째는 회원과 연관된 팀을 조회했는데 둘 다 엔티티를 프로젝션 대상으로 사용했다.
이렇게 조회한 엔티티는 영속성 컨텍스트에서 관리된다.

<br>

### 임베디드 타입 프로젝션

JPQL에서 임베디드 타입은 엔티티와 거의 비슷하게 사용되지만, **임베디드 타입은 조회의 시작점이 될 수 없다는 제약이 있다.**

<br>

```java
String query = "SELECT a FROM Address a";           // 잘못된 문법
String query = "SELECT o.address FROM Order o";     // 옳은 문법
```

<br>

임베디드 타입은 엔티티 타입이 아닌 값 타입으로 이렇게 직접 조회한 임베디드 타입은 영속성 컨텍스트에서 관리되지 않는다.

<br>

### 스칼라 타입 프로젝션

숫자, 문자, 날짜와 같은 기본 데이터 타입들을 스칼라 타입이라고 하며 아래와 같은 방식으로 조회할 수 있다.

<br>

```java
List<String> usernames = em.createQuery("SELECT username FROM Member m", String.class).getResultList();

SELECT DISTINCT username FROM Member m // 중복 제거
```

<br>

### 여러 값 조회하기

엔티티를 대상으로 조회하면 편리하기는 하지만 꼭 필요한 데이터들만 선택하여 조회해야 할 떄도 있다. 프로젝션에 여러 값을 선택하면 TypeQuery가 아닌 Query를 사용해야 한다.

<br>

```java
List<Object[]> resultList = em.createQuery("SELECT m.username, m.age FROM Member m").getResultList(); // 여러 개의 스칼라 타입 값 조회하기

List<Object[]> resultList = em.createQuery("SELECT o.product, o.orderAmount FROM Order o").getResultList(); // 여러 개의 엔티티 타입 값 조회하기
```

<br>
<br>

### NEW 명령어

위에서는 Object[]를 반환받았지만 실제로는 Object[]를 직접적으로 사용하지않고 임의의 DTO 객체로 변환하여 사용한다. 이럴 때는 Object[]로 반환받은 후 DTO 객체로 변환하는 것이 아닌 DTO 객체로 반환받는 방법을 사용하는 것이 좋다.

<br>

```java
TypedQuery<UserDTO> query = em.createQuery("SELECT new jpabook.jpq1.UserDTO(m.username, m.age) FROM Member m", UserDTO.class);

List<UserDTO> resultList = query.getResultList();
```

<br>

SELECT 다음에 new 명령어를 사용하면 반환받을 클래스를 지정할 수 있으며 이 클래스의 생성자에 JPQL 조회 결과를 넘겨줄 수 있다. 그리고 NEW 명령어를 사용한 클래스로 TypeQuery를 사용할 수 있어 객체 변환 작업을 줄일 수 있다.

하지만 new 명령어를 사용하기 위해서는 아래의 두 가지를 주의해야한다.

<br>

1. **패키지 명을 포함한 전체 클래스 명을 입력해야 한다.**
2. **순서와 타입이 일치하는 생성자가 필요하다.**

<br>
<br>

## 페이징 API

데이터베이스마다 페이징을 처리하는 SQL 문법이 다르기 때문에 JPA는 페이징을 두 API로 추상화했다.

<br>

- setFirstResult(int startPosition) : 조회 시작 위치(0부터 시작한다)
- setMaxResult(int maxResult) : 조회할 데이터 수

<br>

```java
TypedQuery<Member> query = em.createQuert("SELECT m FROM Member m ORDER BY m.username DESC", Member.class);

query.setFirstResult(10);
query.setMaxResult(30);
query.getResultList();
```

<br>

위의 예제를 실행하면 시작은 11부터 시작해 총 20 거의 데이터를 조회한다. 데이터베이스마다 다른 페이징 처리를 같은 API로 처리할 수 있는 것은 데이터베이스 방언 덕분이다.

<br>

### MySQL Dialect

```mysql
SELECT
    M.ID AS ID,
    M.AGE AS AGE,
    M.TEAM_ID AS TEAM_ID,
    M.NAME AS NAME
FROM
    MEMBER M
ORDER BY
    M.NAME DESC LIMIT ?, ?
```


<br>
<br>

## JPQL 조인

JPQL도 조인을 지원하는데 SQL 조인과는 기능은 같고 문법만 조금 다르다.

<br>

### 내부 조인

내부 조인 INNER JOIN을 사용하며 여기서 INNER는 생략할 수 있다.

<br>

```java
String teamName = "팀A";
String query = "SELECT m FROM Member m INNER JOIN m.team t " + "WHERE t.name = :teamName";

List<Member> members = em.createQuery(query, Member.class)
                        .setParameter("teamName", teamName)
                        .getResultList();
```

<br>

위의 JPQL로 생성된 내부 조인 SQL은 아래와 같다.

<br>

```mysql
SELECT
    M.ID AS ID,
    M.AGE AS AGE,
    M.TEAM_ID AS TEAM_ID,
    M.NAME AS NAME
FROM
    MEMBER M INNER JOIN TEAM T ON M.TEAM_ID=T.ID
WHER
    T.NAME = ?
```

<br>

JPQL 내부 조인 구문을 보면 SQL의 조인과 약간 다른 것을 확인 할 수 있는데 JPQL 조인과 가장 큰 특징은 연관 필드를 사용한다는 것이다.
여기서 m.team은 연관 필드로 연관 필드는 다른 엔티티와 연관관계를 가지기 위해 사용하는 필드를 말한다.

<br>

- FROM Member m: 회원을 선택하고 m 이라는 별칭을 주었다.
- Member m JOIN m.team t : 회원이 가지고 있는 연관 필드로 팀과 조인한다. 조인한 팀에는 t 라는 별칭을 주었다.

<br>

혹시라도 JPQL 조인을 SQL 조인처럼 사용하면 문법 오류가 발생한다. 꼭 JPQL의 JOIN 명령어 뒤에는 조인할 객체의 연관 필드를 사용하도록 하자.


<br>
<br>


### 외부 조인

JPQL의 외부 조인은 아래와 같이 사용한다.

<br>

```java
SELECT m 
FROM Member m LEFT [OUTER]  JOIN m.team t
```

<br>

외부 조인은 기능상 SQL의 외부 조인과 같으며 OUTER는 생략 가능해서 보통 LEFT JOIN으로 사용한다. 위와 같은 JPQL을 실행하면 다음 SQLd 실행된다.

<br>

```mysql
SELECT 
    M.ID AS ID,
    M.AGE AS AGE,
    M.TEAM_ID AS TEAM_ID,
    M.NAME AS NAME
FROM 
    MEMBER M LEFT OUTER JOIN TEAM T ON M.TEAM_ID=T.ID
WHERE
    T.NAME = ?
```

<br>
<br>
<br>


### 컬렉션 조인

**일대다 관계나 다대다 관계처럼 컬렉션을 사용하는 곳에 조인하는 것을 컬렉션 조인이라 한다.**

<br>

- [회원 > 팀]으로의 조인은 다대일 조인이면서 단일 값 연관 필드(m.team)를 사용한다.
- [팀 > 회원]은 반대로 일대다 조인이면서 컬렉션 값 연관 필드(m.members)를 사용한다.

<br>

```JAVA
SELECT t, m FROM LEFT JOIN t.members m
```


<br>

### 세타 조인

WHERE 절을 사용해서 세타 조인을 할 수 있다. **세타 조인은 내부 조인만 지원한다.**

<br>

- Member의 username과 Team의 name값이 같은 사람 수 구하기 

<br>

```java
SELECT count(m) FROM Member m, Team t WHERE m.username = t.name
```

<br>
<br>


### JOIN ON 절

JPA 2.1부터 조인할 때 ON 절을 지원한다. **ON 절을 사용하면 조인 대상을 필터링하고 조인할 수 있으며 내부 조인의 ON 절은 WHERE 절을 사용할 때와 결과가 같으므로 보통 ON 절은 외부 조인에서만 사용한다.**

<br>

- 모든 회원을 조회하면서 회원과 연관된 팀도 조회하는 예

<br>

```java
// JPQL
SELECT m, t FROM Member m LEFT JOIN m.team on t.name = 'A'


// SQL
SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.TEAM_ID = t.id and t.name = 'A';
```

<br>
<br>


### 페치 조인

페치 조인은 SQL에서 이야기 하는 조인의 종류는 아니고 JPQL에서 성능 최적화를 위해 제공하는 기능이다. 페치 조인은 연관된 엔티티나 컬렉션을 한 번에 같이 조회하는 기능으로 join fetch 명령어로 사용할 수 있다.

<br>

#### 엔티티 페치 조인

페치 조인을 사용해 회원 엔티티를 조회하면서 연관된 팀 엔티티도 함께 조회하는 JPQL을 봐보자.

<br>

```Java
// JPQL
SELECT m FROM Member m JOIN FETCH m.team

// MySQL
SELECT
    M.*, T.*
FROM MEMBER M
INNER JOIN TEAM T ON M.TEAM_ID=T.ID
```

<br>

위처럼 JOIN FETCH 를 사용하면 연관된 엔티티나 컬렉션을 함께 조회하는데 여기서는 회원과(m)과 팀(m.team)을 함께 조회한다.

일반적인 조인과는 다르게 m.team 뒤에 별칭이 없는데 페치 조인에서는 별칭을 사용할 수 없기 때문이다.

또한 엔티티 페치 조인 JPQL에서 SELECT m 으로 회원 엔티티만 선택했음에도 불구하고 실행된 엔티티를 보면 SELECT M.*, T.*로 회원과 연관된 팀도 함께 조회된 것을 확인할 수 있다.

**이렇게 회원과 팀을 한번에 검색하기 떄문에 회원과 팀을 지연 로딩으로 설정했어도 팀 엔티티는 프록시가 아닌 실제 엔티티다.
연관된 팀을 사용해도 지연로딩이 일어나지 않는다.** 그리고 프록시가 아닌 실제 엔티티이므로 회원 엔티티가 영속성 컨텍스트에서 분리되어 준영속 상태가 되어도 연관된 팀을 조회할 수 있다.


<br>
<br>

#### 컬렉션 페치 조인

<br>

```java
// JPQL
SELECT t
FROM TEAM t JOIN FETCH t.members
WHERE t.name = '팀A'

// SQL
SELECT
    T.*, M.*
FROM TEAM T
INNER JOIN MEMBER M ON T.ID = M.TEAM_ID
WHERE T.NAME = '팀A'
```

<br>

컬렉션을 페치 조인한 JPQL에서 SELECT t로 팀만 선택했는데 실행된 SQL을 보면 T.*, M.* 로 팀과 연관된 회원도 함께 조회한 것을 확인할 수 있다.


<br>
<br>

#### 페치 조인과 일반 조인 차이

만약 페치 조인을 사용하지 않고 조인만 사용한다면 어떻게 될까 ?

<br>

```java
// JPQL
SELECT t
FROM TEAM t JOIN t.members m
WHERE t.name = '팀A'

// SQL
SELECT 
    T.*
FROM TEAM T
INNER JOIN MEMBER M ON T.ID = M.TEAM_ID
WHERE T.NAME = '팀A'
```

<br>

위의 예처럼 JPQL 에서 팀과 회원을 조인했다고 회원 컬렉션도 함께 조회할 것으로 기대해서는 안된다. 실행된 SQL의 SELECT 절을 보면 팀만 조회하고 조인했던 회원은 전혀 조회하지 않는 것을 볼 수 있다.

JPQL은 결과를 반환할 때 연관관계까지 고려하지 않으며 단지 SELECT 절에 지정한 엔티티만 조회할 뿐이다. 그렇기 때문에 팀 엔티티만 조회하고 연관된 회원 컬렉션은 조회하지 않는다.

만약 회원 컬렉션을 지연 로딩으러 설정하면 프록시나 아직 초기화하지 않은 컬렉션 래퍼를 반환하며 즉시 로딩으로 설정했을 떄는 회원 컬렉션을 즉시 로딩하기 위해 쿼리를 한 번 더 실행한다.

<br>
<br>

#### 페치 조인의 특징과 한계

페치 조인을 사용하면 SQL 한 번으로 연관된 엔티티들을 함께 조회할 수 있어서 SQL 호출 횟수를 줄여 성능을 최적화할 수 있다.

이처럼 엔티티에 직접 적요하는 로딩 전략은 애플리케이션 전체에 영향을 미치기 때문에 글로벌 로딩 전략이라고 부르며 페치 조인은 글로벌 로딩 전략보다 우선한다. 예를 들어 글로벌 로딩 전략을 지연 로딩으로 설정해도 JPQL에서 페치 조인을 사용하면 페치 조인을 적용해서 함께 조회한다.

최적화를 위해 글로벌 로딩 전략을 즉시 로딩으로 설정하면 애플리케이션 전체에서 항상 즉시 로딩이 일어나며 많이 사용하지 않는 엔티티까지 자주 로딩하기 때문에 오히려 성능이 나빠지는 결과를 얻을 수 있다. 그렇기 떄문에 글로벌 로딩 전략은 될 수 있으면 지연 로딩을 사용하고 최적화가 필요하면 페치 조인을 적용하는 것이 효과적이다.

페치 조인의 특징에 대해 마지막으로 정리해보자.

<br>

- 페치 조인 대상에는 별칭을 줄 수 없다.

    <br>

    - JPA 표준에서는 지원하지 않지만 하이버네이트를 포함한 몇몇 구현체들은 페치 조인에 별칭을 지원한다. 하지만 별칭을 잘못사용하면 연관된 데이터 수가 달라져 데이터 무결성이 꺠질 수 있으므로 조심해야 한다. 

<br>

- 둘 이상의 컬렉션을 페치할 수 없다.
    <br>

    - 구현체에 따라 되기도 하지만 컬렉션 * 컬렉션의 카테시안 곲으로 만들어지므로 주의해야 한다.
    
    <br>

- 컬렉션을 페치 조인하면 페이징 API(setFirstResult, setMaxResults)를 사용할 수 없다.

    - 컬렉션(일대다)이 아닌 단일 값 연관 필드(일대일, 다대일)들은 페치 조인을 사용해도 페이징 API를 사용할 수 있다.

<br>
<br>
<br>


## 경로 표현식

### 경로 표현식 용어 정리

경로 표현식을 이해하려면 우선 다음 용어들을 알아야 한다.

<br>

- 상태 필드 : 단순히 값을 저장하기 위한 필드

- 연관 필드 : 연관관계를 위한 필드, 임베디드 타입 포함
    - 단일 값 연관 필드 : @ManyToOne, @OneToOne, 대상이 엔티티

    - 컬렉션 값 연관 필드 : @OneToMany, @ManyToMany, 대상이 컬렉션

<br>

상대 필드는 단순히 값을 저장하는 필드를 말하며, 연관 필드는 객체 사이의 연관 관계를 맺기 위해 사용하는 필드다.

<br>
<br>


### 경로 표현식과 특징

JPQL에서 경로 표현식을 사용해서 경로 탐색을 하려면 다음 3가지 경로에 따라 어떤 특성이 있는지 이해해야 한다.

<br>

- **상태 필드 경로** : 경로 탐색의 끝이다. 더는 탐색할 수 없다.

- **단일 값 연관 경로** : **묵시적으로 내부 조인이 일어난다.** 단일 값 연관 경로는 계속 탐색할 수 있다.

- **컬렉션 값 연관 경로** : **묵시적으로 내부 조인이 일어난다.** 더는 탐색할 수 없으다. 단 FROM 절에서 조인을 통해 별칭을 얻으면 별칭으로 탐색할 수 있다.


<br>

#### 상대 필드 경로 탐색

JPQL의 m.username과 m.age는 상태 필드 경로 탐색이다.

<br>

```java
// JPQL
SELECT m.username, m.age FROM Member m

// SQL
SELECT m.name, m.age
FROM Member m
```

<br>
<br>

#### 단일 값 연관 경로 탐색

<br>

- 단일 값 연관 경로 탐색 예1

<br>

```java
// JPQL
SELECT o.member FROM Order o

// SQL
SELECT m.*
FROM Orders o
    INNER JOIN Member m on o.member_id = m.id
```

<br>

위의 JPQL을 보면 o.member를 통해 주문에서 회원으로 단일 값 연관 필드로 경로 탐색을 했다. **단일 값 연관 필드로 경로 탐색을 하면 SQL에서 내부 조인이 일어나는데 이것을 묵시적 조인**이라고 한다. 참고로 **묵시적 조인은 모두 내부 조인**이다. **외부 조인은 명시적으로 JOIN 키워드를 사용해야한다.**


<br>

- **명시적 조인** : JOIN을 직접 쓰는 것

    ```java
    SELECT m FROM Member m JOIN m.team t
    ```

<br>

- **묵시적 조인** : 경로 표현식에 의해 묵시적으로 조인이 일어나는 것, 내부 조인 INNER JOIN만 할 수 있다.

    ```java
    SELECT m.team FROM Member m
    ```

<br>

- 단일 값 연관 경로 탐색 예2

```java
// JPQL
SELECT o.member.team
FROM Order o
WHERE o.product.name = 'productA' AND o.address.city = 'JINJU'

// SQL
SELECT t.*
FROM Order o
INNER JOIN Memeber m on o.member_id = m.id
INNER JOIN Team t on m.team_id = t.id
INNER JOIN Product p on o.product_id = p.id
WHERE p.name = 'productA' AND o.city='JINJU'
```

<br>

실행된 SQL을 보면 총 3번의 조인이 발생했다. 위의 o.address 처럼 임베디드 타입에 접근하는 것도 단일 값 연관 경로 탐색이지만 주문 테이블에 이미 포함되어 있으므로 조인이 발생하지 않는다.


<br>
<br>

#### 컬렉션 값 연관 경로 탐색

JPQL을 다루면서 많이 하는 실수 중 하나는 컬렉션 값에서 경로 탐색을 시도하는 것이다.

<br>

```java
SELECT t.members FROM TEAM t // 성공
SELECT t.members.username FROM TEAM t // 실패
```

<br>

t.members처럼 컬렉션까지는 경로 탐색이 가능하다. 하지만 **t.members.username처럼 컬렉션에서 경로 탐색을 시작하는 것은 허락되지 않는다.** 만약 컬렉션에서 경로 탐색을 하고 싶다면 다음 코드처럼 조인을 사용해서 새로운 별칭을 획득해야 한다.

<br>

```java
SELECT m.username FROM Team t join t.members m
```

<br>

join t.members 으로 컬렉션에 새로운 별칭을 얻었다. 이제 별칭 m부터 다시 경로 탐색을 할 수 있다.


<br>
<br>

### 경로 탐색을 사용한 묵시적 조인시 주의사항

경로 탐색을 사용하면 묵시적 조인이 발생해서 SQL에 내부 조인이 일어날 수 있다. 이때 주의사항은 아래와 같다.

<br>

- **항상 내부 조인이다.**


- **컬렉션은 경로 탐색의 끝이다.** 컬렉션에서 경로 탐색을 하려면 명시적으로 조인해서 별칭을 얻어야 한다.

- 경로 탐색은 주로 SELECT, WHERE 절(다른 곳에서도 사용됨)에서 사용하지만 묵시적 조인으로 인해 SQL의 FROM 절에 영향을 준다.

<br>

명시적 조인에 비해 묵시적 조인은 조인이 일어나는 상황을 한눈에 파악하기 어렵다.
그렇기 떄문에 단순하고 성능에 이슈가 없다면 크게 문제가되지 않지만 성능이 중요하다면 분석하기 쉽도록 묵시적 조인보다는 명시적 조인을 사용하자.

