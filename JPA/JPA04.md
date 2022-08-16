> 자바 ORM 표준 JPA 프로그래밍을 읽고 작성한 내용입니다.
>
# JPA 04

## 엔티티 매핑하기
테이플과 엔티티를 정확히 매핑하기 위해서 매핑 어노테이션을 사용한다.
매핑 어노테이션은 
1. 객체와 테이블 매핑 : @Entity, @Table 
2. 기본 키 매핑 : @Id
3. 필드와 컬럼 매핑 : @Column
4. 연관관계 매핑 : @ManyToOne, @JoinColumn

## @Entity
JPA에서 테이블과 매핑할 클래스는 @Entity 어노테이션을 필수로 붙여야한다. 
@Entity가 붙은 클래스는 JPA가 관리하는 것으로 엔티티라고 부른다. 

### @Entity 어노테이션 주의할 점
1. 기본 생성자는 필수다.
2. final 클래스, enum, interface, inner 클래스에는 사용할 수 없다.
3. 저장할 필드에 final을 사용하면 안된다.

JPA가 엔티티 객체를 생성할 때 기본 생성자를 사용하므로 이 생성자는 반드시 있어야 한다.

## @Table
@Table 어노테이션은 엔티티와 매핑할 테이블을 지정한다. 생략하면 매핑한 엔티티 이름을 테이블 이름으로 사용한다.

### @Table 속성
1. name : 매핑할 테이블 이름 
2. catalog : catalog 기능이 있는 데이터베이스에서 catalog를 매핑한다.
3. schema : schema 기능이 있는 뎅터베이스에서 schema를 매핑한다.
4. uniqueConstraints : DDL 생성 시 유니크 제약조건을 만든다. 
   2개 이상의 복합 유니크 제약조건도 만들 수 있다.


```java
@Entity
@Table
public class Member{    
    @Id 
    @Column
    private String id;
    
    @Column(name = "NAME")
    private String username;
    
    private Integer age;
    
    @Enumerated(EnumType.String)
    private RoleType roleType;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
   
    @Lob
    private String description;

}

public enum RoleType {
     ADMIN, USER
}
```

1. roleType : 자바의 enum을 사용해서 회원의 타입을 구분하는데 이렇게 enum을 사용하려면 @Enumberated 어노테이션으로 매핑해야한다.
2. createdDate, lastModifiedDate : 자바의 날짜 타입은 @Temporal을 사용해서 매핑해야한다.
3. description : 회원을 설명하는 필드는 길이 제한이 없다. 따라서 데이터 베이스의 VARCHAR 타입 대신에 CLOB 타입으로 저장해야한다.
@Lob 을 사용하면 CLOB, BLOB 타입을 매핑할 수 있다.


## JPA의 테이블 자동생성 기능
JPA는 어노테이션으로 작성된 매핑정보와 데이터베이스 방언을 사용해서 데이터베이스 스키마를 생성한다.
- application.properties에 작성하는 방법
```properties
spring.jpa.hibernate.ddl-auto=create
```

- application.yml에 작성하는 방법
```yml
spring:
  jpa:
    hibernate:
      ddl-auto: create
```

### ddl-auto 속성
1. create : 기존 테이블을 삭제하고 새로 생성한다.
2. create-drop : create 속성에 추가로 애플리케이션을 종료할 때 생성한 DDL을 제거한다.
3. update : 데이터베이스 테이블과 엔티티 매핑정보를 비교해서 변경사항만 수정한다.
4. validate : 데이터베이스 테이블과 엔티티 매핑정보를 비교해서 차이가 있으면 경고를 남기고 애플리케이션을 실행하지 않는다.
5. none : 자동 생성 기능을 사용하지 않음 

운영서버에서 DDL을 수정하는 옵션은 절대 사용하면 안된다. 오직 개발 서버나 개발 단게에서만 사용해야하는데 이 옵션들은 운영 중인 데이터베이스의 테이블이나 컬럼을 삭제할 수 있다.

### 이름 매핑 전략
자바에서는 보통 단어와 단어를구분할 때 관례상 카멜 표기법을 주로 사용한다. 하지만 데이터베이스는 관례상 언더스코어를 주로 사용한다.
```java
in java 
> roleType

in Database
> role_type
```

## 제약조건 추가하기
### @Column
@Colum 어노테이션에 제약조건을 추가해보자
null 허용 여부를 설정하려면 @Column의 nullable 속성을 true 혹은 false로 변경해주면 된다. nullable의 기본값은 true다.
또 길이 제한을 설정해주려면 length 속성을 작성해주면 된다.
```java
@Column(name = "NAME", nullable = true, length = 10)
```

### @Table
@Table에 유니크 제약조건을 추가해보자.
```java
@Table(name="Table", uniqueConstraints = {@UniqueConstraint(
        name = "NAME_AGE_UNIQUE",
        columnNames = {"NAME", "AGE"})})
public class TableName (
```
테이블에 유니크 제한 조건을 설정해주려면 @Table의 uniqueConstraint 값을 변경해주면 된다.
이런 기능들은 단지 DDL을 자동 생성할 때만 사용되고 JPA의 실행 로직에는 영향을 주지 않는다.

스키마 자동 생성 기능을 사용하지 않고 직접 DDL을 작성한다면 사용할 이유가 없지만 이 기능을 사용하면 
엔티티만 보고도 제약 조건을 파악할 수 있다는 장점이 있다.


## 기본 키 매핑
기본 키는 Primary Key라고도 불리며 관계형 데이터베이스에서 조(레코드)의 식별자로 이용하기에 가장 적합한 것을 관계 (테이블)마다 단 한 설계자에 의해 선택, 
정의된 후보 키를 말한다. 

### 기본 키 조건
1. null값을 허용하지 않는다.
2. 유일해야 한다.
3. 변해선 안 된다.

### 기본 키 전략
- 자연 키(Natural Key)
   - 비즈니스에 의미가 있는 키
   - 예 : 주민등록번호, 이메일, 전화번호
- 대리 키(Surrogate Key)
  - 비즈니스와 관련 없는 임의로 만들어진 키, 대체 키로도 불림
  - 예 : 오라클 시퀀스, auto_increment, 키생성 테이블 

데이터 베이스마다 기본 키를 생성하는 방식이 서로 다르므로 이 문제를 해결하기 위해서 JPA는 여러 전략을 제공한다.

1. 직접 할당 : 기본 키를 애플리케이션에서 직접 할당한다.

   
2. 자동 생성 : 대리 키 사용 방식 
   - IDENTITY : 기본 키 생성을 데이터베이스에 위임한다
   - SEQUENCE : 데이터베이스 시퀀스를 사용해서 기본 키를 할당한다. 
   - TABLE : 키 생성 테이블을 사용한다.
   - AUTO 전략 : 선택한 데이터베이스 방언에 따라 IDENTITY, SEQUENCE, TABLE 전략 중 하나를 자동으로 선택한다.

### IDENTITY 전략
IDENTITY 전략은 AUTO_INCREMENT를 사영하는 것처럼 데이터베이스에 값을 저장하고 나서야 기본 키 값을 구할 수 있을 때 사용한다.
개발자가 엔티티에 직접 식별자를 할당하면 @Id 어노테이션만 있으면 되지만 식별자가 생성되는 경우에는
@GeneratedValue 어노테이션을 사용하고 식별자 생성 전략을 선택해야 한다.

IDENTITY 전략을 사용하려면 @GeneratedValue의 strategy 속성 값을 GenerationType.IDENTITY로 지정하면 된다.
IDENTITY를 사용하면 JPA는 기본 키 값을 얻어오기 위해 데이터베이스를 추가로 조회한다.

데이터가 데이터베이스에 INSERT 된 후에 기본 키 값을 조회할 수 있다. 따라서 엔티티에 식별자 값을 할당하려면 JPA는 추가로 데이터베이스를 조회해야한다.
JDBC#에 추가된 Statement.getGeneratedKeys()를 사용하면 데이터를 저장하면서 동시에 생성된 기본 키 값도 얻어 올 수 있다.

- 주의
> 엔티티가 영속 상태가 되려면 식별자가 반드시 필요하다. <br>
IDENTITY 식별자 생성 전략은 엔티티를 데이터베이스에 저장해야 식별자를 구할 수 있으므로 em.persist()를 호출하는 즉시 INSERT SQL이 데이터베이스에 전달되고
생성된 기본 키를 가져와 엔티티를 식별자로 사용한다.


### SEQUENCE 전략
데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트로 SEQUENCE 전략은 이 시퀀스를 사용해서 기본 키를 생성한다.
이 전략은 시퀀스를 지원하는 오라클, PostgreSQL, DB2, H2 데이터베이스에서 사용할 수 있다.

## 필드와 컬럼 매핑

<table>
  <tr>
    <td rowspan="5">내용</td>
    <td>@Column</td>
    <td>컬럼을 매핑한다.</td>
  </tr>
  <tr>
    <td>@Enumerated</td>
    <td>자바의 enum 타입을 매핑한다.</td>
  </tr>
  <tr>
    <td>@Tempora</td>
    <td>날짜 타입을 매핑한다.</td>
  </tr>
   <tr>
    <td>@Lob </td>
    <td>BLOB, CLOB 타입을 매핑한다.</td>
  </tr>
   <tr>
    <td>@Transient </td>
    <td>특정 필드를 데이터베이스에 매핑하지 않는다.</td>
  </tr>
   <tr>
    <td>기타</td>
    <td>@Access</td>
    <td>JPA가 엔티티에 접근하는 방식을 지정한다.</td>
  </tr>
</table>


## @Column 주의
> ### 자바의 기본 타입에 @Column을 사용하면 nullable = false 로 지정하는 것이 안전하다 !
@Column을 생략하면 대부분 @Column 속성의 기본값이 적용된다. 자바의 기본타입일 때는 nullable 속성에 예외가 있다.
int, char과 같은 자바의 기본 타입에는 null 값을 입력할 수 없다. 하지만 객체 타입일 때는 null 값이 허용된다.

만약 자바의 기본 타입으로 컬럼을 DDL로 생성할 때는 not null 제약 조건을 추가하는 것이 안전하다.

## @Enumerated
> ### @Enumerated 어노테이션을 사용할 때 EnumType.String을 사용하는 것을 권장한다.
자바의 enum 타입을 매핑할 때 사용되는 어노테이션이다.

1. EnumType.ORDINAL은 enum에 정의된 순서대로 0, 1 등의 정수 값이 데이터베이스에 저장된다.
2. EnumType.String 은 enum 이름 그대로 문자로 데이터베이스에 저장한다.

ORDINAL으로 설정했을 경우 enum의 값이 변경하면 이미 저장된 enum의 순서를 변경할 수 없기 때문에 주의해야한다.
그렇기 때문에 이러한 문제가 발생하지 않도록 **EnumType.String을 사용하는 것을 권장한다.**

## @Temporal
@Temporal 어노테이션은 날짜 타입을 매핑할 때 사용한다. 자바의 Date 타입에는 년원일 시분초가 있지만 데이터베이스에는 date(날짜),
time(시간), timestamp(날짜와 시간)라는 세 가지 타입이 별도로 존재한다.

@Temporal을 생략하면 자바의 Date와 가장 유사한 timestamp로 정의된다. 하지만 timestamp 대신에 datetime을 예약어로 사용하는 데이터베이스도 있는데
데이터베이스 방언 덕분에 애플리케이션 코드는 변경하지 않아도 된다.

> datetime : MySQL <br>
> timestamp : H2, 오라클, PostgreSQL
> 

## @Access
JPA가 엔티티 데이터에 접근하는 방식을 지정할 때 사용한다.

1. 필드 접근 : AccessType.FIELD로 지정한다. 필드에 직접 접근하는데 필드 접근 권한이 private이어도 접근할 수 있다.
2. 프로퍼티 접근 : AccessType.PROPERTY로 지정한다. 접근자(Getter)를 사용한다.
