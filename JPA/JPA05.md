> 자바 ORM 표준 JPA 프로그래밍을 읽고 작성한 내용입니다.
>
# JPA 05 
## 연관관계 매핑 기초
> 목표 : 어떻게 객체 연관관계와 테이블 연관관계를 매핑하는지 알아보자.
> 

객체는 참조(주소)를 통해 관계를 맺고 테이블은 외래 키를 사용해서 관계를 맺는다.


### 핵심 키워드
1. 방향<br>
    - 방향은 객체관계에만 존재하고 테이블 관계는 항상 양방향이다.
   
    단방향 관계는 회원 > 팀 > 회원 둘 중 한쪽만 참조하는 것을 말한다.<br>
   양방향 관계는 회원 > 팀, 팀 > 회원 양쪽 모두 서로 참조하는 것을 양방향 관계라고 한다.


2. 다중성
   - 다대일(N:1), 일대다(1:N), 일대일(1:1), 다대다(N:M) 다중성이 있다.<br>
   
   예를 들어 회원과 팀이 관계가 있을 때 여러 회원은 한 팀에 속하므로 회원과 팀은 다대다 관계다.<br>
   반대로 한 팀에 여러 회원이 소속될 수 있으므로 팀과 회원은 일대다 관계다.


3. 연관관계의 주인
    객체를 양방향 연관관계로 만들면 연관관계의 주인을 정해야한다.

## 단방향 연관관계
- 객체 연관관계
![](../../../Desktop/스크린샷 2022-08-17 17.22.51.png)
회원 객체는 member.team 필드로 팀 객체와 연관관계를 맺는다.

회원과 팀 객체는 단방향 관계로 회원은 member.team 필드를 통해서 팀을 알 수 있지만 반대로 팀은 회원을 알 수 없다. 예를들어 member > team 의 조회는 member.getTeam()으로
가능하지만 반대 방향인 team > member 를 접근하는 필드는 없다.

- 테이블 연관관계
![](../../../Desktop/스크린샷 2022-08-17 17.19.15.png)

회원 테이블은 team_id 외래 키로 테이블과 연관관계를 맺는다.

회원 테이블과 팀 테이블은 양방향 관계로 team_id 외래 키를 통해서 회원과 팀을 조인할 수 있고 반대로 팀과 회원도 조인할 수 있다.

### 객체 연관관계와 테이블 연관관계의 가장 큰 차이
참조를 통한 연관관계는 언제나 단방향으로 객체간에 연관관계를 양방향으로 만들고 싶으면 반대쪽에도 필드를 추가해서 참조를 보관해야 한다.
하지만 이건 엄밀히 말하면 양방향 연관관계가 아닌 서로 다른 방향의 단방향 연관관계 2개가 있는 것이다. 하지만 테이블은 외래 키 하나로 양방향 조인을 할 수 있다.


> #### 정리하기 
> 객체 
> 1. 참조를 사용하는 객체의 연관관계는 단방향이다.
> 2. 객체는 참조를 통해 연관관계를 맺고 양방향 관계를 갖기 위해선 서로 참조 보관이 이루어져야함 (단방향 2개)
>
> 테이블
> 1. 외래 키 하나로 양방향 관계 조인이 가능함

## 객체 연관관계

![](../../../Desktop/스크린샷 2022-08-17 17.36.14.png)

```java
// team1에 멤버 추가
Member member1 = new Member("member1", "m1");
Member member2 = new Member("member2", "m2");
Team team1 = new Team("team1", "t1");

// member1이 소속된 팀을 조회하기
member1.setTeam(team1);
member2.setTeam(team1);

Team findTeam = member1.getTeam();
```

작성된 코드를 보면 member1과 member2 객체가 team1에 소속되어 있는 것을 볼 수 있다.
객체는 Team findTeam = member.getTeam(); 이렇게 참조를 사용해서 연관관계를 탐색할 수 있으며 이것을 객체
그래프 탐색이라고 한다.

## 테이블 연관관계
```mysql
// team1에 멤버 추가
INSERT INTO TEAM(TEAM_ID, NAME) VALUES('team1', 't1');
INSERT INTO MEMBER(MEMBER_ID, TEAM_ID, USERNAME) VALUES('member1', 'team1', 'm1');
INSERT INTO MEMBER(MEMBER_ID, TEAM_ID, USERNAME) VALUES('member2', 'team1', 'm2');

// member1이 소속된 팀을 조회하기
SELECT T.*
FROM MEMBER M
    JOIN TEAM T ON M.TEAM_ID = T.TEAM_ID
WHERE M.MEMBER_ID = 'member1';
```
테이블 간 연관관계를 만들어주기 위해서 외래 키를 만들어주었고 값을 member 데이터를 추가할 때 외래 키인 TEAM_ID를 같이 추가해주었다.
이렇게 해서 멤버와 팀 테이블 안의 데이터 간 연관관계를 만들어줌으로써 JOIN을 사용해 양방향으로 조회할 수 있게 되었다.

## 객체 관계 매핑
```java
// Member Entity
@Getter
@Setter
@Entity
public class Member{
    @Id
    @Column(name = "MEMBER_ID")
    private String id;
    
    private String username;
    
    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;
}

// Team Entity
@Getter
@Setter
@Entity
public class Team{  
    @Id
    @Column(name = "TEAM_ID")
    private String id;

    private String name;
}
```

1. @ManyToOne
   다대일(N:1) 관계의 매핑 정보로 연관관계를 매핑할 때 다중성을 나타내는 어노테이션을 필수로 작성해야 한다.
   #### @ManyToOne 속성 값
      1. optional : false로 설정하면 연관된 엔티티가 항상 있어야한다.
      2. targetEntity : 연관된 엔티티의 타입 정보를 설정한다.
         <br>예)
         ```java
         // targetEntity 설정 사용하지 않은 경우
         @OneToMany
         private List<Member> members;

         // targetEntity 설정 사용한 경우         
         @OnetoMany(targetEntity=Member.class)
         private List members; 
         ```
2. @JoinColumn
   조인 컬럼은 외래 키를 매핑할 때 사용한다.
   name 속성에는 매핑할 외래 키 이름을 지정한다. 이 어노테이션은 생략가능하다.
   생략했을 경우에는 **"필드명_참조하는 테이블의 컬럼명"** 외래키를 사용한다.


### 조회하기
연관관계가 있는 엔티티를 조회하는 방법은 크게 2가지가 있다.
1. 객체 그래프 탐색(객체 연관관계를 사용한 조회)
2. 객체 지향 쿼리 사용(JPQL)

#### 1. 객체 그래프 탐색 방식으로 엔티티 조회하기
```java
Member member = em.find(Member.class, "member1");
Team team = member.getTeam();
```
이렇게 객체를 통해서 연관된 엔티티를 조회하는 것을 객체 그래프 탐색이라고 한다.

#### 2. 객체 지향 쿼리 (JPQL)을 사용해서 조회하기
```java
String jpql = "select m from Member m join m.team t where "+"t.name=:teamName";
List<Member> resultList = em.createQuery(jpql, Member.class).setParameter("teamName", "팀1").getResultList();

for (Member member : resutList){
    System.out.println(member.getUsername());    
}
```

:teamName은 :로 시작하는 파라미터를 바인딩 받는 방법이다. .setParameter("teamName", "팀1") 이렇게 teamName에 팀1이라는 문자열을 받아서
쿼리를 실행하게 해준다.

### 연관관계 수정하기하기
```java
Member member = em.find(Member.class, "member1");
member.setTeam(team2);
```
연관관계를 수정하려면 엔티티를 받아와서 set 메서드를 통해 엔티티를 수정한다.
이렇게 엔티티를 수정하게 되면 1차 캐시에 수정된 엔티티가 저장되고 SQL 쓰기 지연 저장소에 수정 쿼리가 저장되게 된다.

이후 플러시될 때 해당 엔티티가 영속상태에 있다면 SQL 쓰기 지연 저장소에 저장된 쿼리를 통해 데이터베이스가 수정된다.

### 연관관계 제거하기
```java
Member member = em.find(Member.class, "member1");
member.setTeam(null);
```
setTeam()에 null값을 인자로 넣음으로써 데이터의 연관관계가 제거되게 된다.

### 연관된 엔티티 삭제하기
만약 연관된 엔티티를 삭제하려면 기존에 있던 연관관계를 먼저 제거하고 삭제해야 한다.
그렇지 않으면 외래 키 제약조건으로 인해, 데이터베이스에 오류가 발생한다.

```java
member1.setTeam(null);
member2.setTeam(null);

em.remove(team);
```

## 양방향 연관관계

![](../../../Desktop/양방향 연관관계.png)
회원에서 팀으로만 접근하는 것이 아니라 팀에서 회원, 회원에서 팀으로 접근하는 접근할 수 있도록 코드를 수정해보자

```java
// Member Entity
@Getter
@Setter
@Entity
public class Member{
    @Id
    @Column(name = "MEMBER_ID")
    private String id;
    
    private String username;
    
    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;
}

// Team Entity
@Getter
@Setter
@Entity
public class Team{  
    @Id
    @Column(name = "TEAM_ID")
    private String id;

    private String name;
    
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<Member>();
}
```
팀과 회원은 일대다 관계이기 때문에 팀 엔티티에 컬렉션인 List<Member> members 를 추가했다.
그리고 일대다 관계를 매핑하기 위해서 @OneToMany 매핑 정보를 사용했다. mappedBy 속성은 양방향 매핑일 때 사용하는데 반대쪽 매핑의 필드 이름을
값으로 주면 된다.

- 일대다 컬렉션 조회
```java
Team team = em.find(Team.class, "team1");
List<Member> members = team.getMembers();

for (Member member : members){
    System.out.println(member.getUsername());
}
```

객체에는 양방향 연관관계라는 것이 없어 단방향 연관관계 2개를 애플리케이션 로직으로 묶어 양방향 연관관계처럼 사용한다.
하지만 데이터베이스 테이블은 외래 키 하나로 양쪽이 서로 조인하여 사용할 수 있다. 

엔티티를 단방향으로 매핑하면 참조를 하나만 사용하므로 이 참조로 외래 키를 관리하면 된다.
하지만 객체가 양방향 연관관계를 가지려면 단방향 연관관계를 2개 가져야 하기 때문에 연관관계를
관리하는 포인트가 2곳으로 늘어난다.

엔티티를 양방향 연관관계로 설정하면 객체의 참조는 둘인데 외래 키는 하나다. 따라서 둘 사이에 차이가 발생한다. 그렇다면 둘 중 어떤 관계를 사용해서 외래 키를 관리해야 할까?

이러한 차이로 인해서 JPA에서는 두 객체 연관관계 중 하나를 정해서 테이블의 외래 키를 관리해야 하는데 이것을 연관관계의 주인이라고 한다.

## 양방향 매핑의 규칙 : 연관관계의 주인
### 중요 ! 연관관계의 주인은 외래 키가 있는 곳
> - 연관관계의 주인을 정한다는 것은 외래 키 관리자를 선택하는 것이다.

**양방향 연관관계 매핑 시 두 연관관계 중 하나를 연관관계의 주인으로 정해야 한다**라는 규칙이 있다.
연관관계의 주인만이 데이터베이스 연곤관계와 매핑되고 외래 키를 관리할 수 있다. 반면에 주인이 아닌 쪽은 읽기만 할 수 있다.
어떤 연관관계를 주인으로 정할 지는 mappedBy 속성을 사용하면 된다.

주인은 mappedBy 속성을 사용하지 않고 주인이 아니라면 mappedBy 속성을 사용해서 속성의 값으로 연관관계의 주인을 정해야 한다.

그렇다면 Member.team과 Team.members 중 어떤 것을 연관관계의 주인으로 정해야할까 ?
만약 Member.team를 연관관계의 주인으로 정한다면 자기 테이블에 있는 외래 키를 관리하면 된다.
하지만 Team.members를 연관관계의 주인으로 설정한다면 회원 테이블에 있는 외래 키를 관리해야 한다.

자신의 테이블 안에 있는 외래 키가 아닌 다른 테이블에 있는 외래 키를 관리하는 것은 어렵기 때문에 외래 키를 가지고 있는 테이블을
연관관계의 주인으로 정해야 한다.

### 양방향 연관관계 정리
> 1. 연관관계의 주인만 데이터베이스 연관관계와 매핑되고 외래 키를 관리할 수 있다.
> 2. 주인이 아닌 반대편은 읽기만 가능하고 외래 키를 변경하지 못한다.
> 3. 데이터베이스 테이블의 다대일, 일대다 관계에서 항상 다 쪽이 외래 키를 가진다.<br>
>    다 쪽이 항상 연관관계의 주인이 되므로 mappedBy 속성을 설정할 수 없다. 그렇기 때문에 @ManyToOne에는 mapeedBy속성이 없다.


## 양방향 연관관계의 주의점
> #### 주인이 아닌 곳은 데이터를 읽을수만 있기 때문에 양방향 연관관계의 주인에 값을 입력하고 주인이 아닌 곳에는 입력하지 않는다.
> 
<br>

```java
Member member1 = new Member("member1", "m1");
em.persist(member1);

Member member2 = new Member("member2", "m2");
em.persist(member2);

Team team1 = new Team("team1", "t1");
team1.getMembers().add(member1);
team1.getMembers().add(member2);

em.persist(team1);
```

m1과 m2를 저장하고 팀의 컬렉션에 담은 후 팀을 저장한 후. 데이터베이스에서 회원 테이블을 조회하면 
<table>
    <tr>
        <td>MEMBER_ID</td>
        <td>USERNAME</td>
        <td>TEAM_ID</td>
    </tr>
    <tr>
        <td>member1</td>
        <td>m1</td>
        <td>null</td>
    </tr>
    <tr>
        <td>member1</td>
        <td>m2</td>
        <td>null</td>
    </tr>
</table>

이렇게 TEAM_ID의 값이 null인 것을 볼 수 있다.

그 이유는 연관관계의 주인이 아닌 Team.members에만 값을 저장했기 떄문이다.
연관관계의 주인만이 외래 키의 값을 변경할 수 있기 때문에 연관관계의 주인이 아닌 Team은 값을 변경하지 못한다.

## 객체까지 고려한 양방향 연관관계
```java
public void test(){
    Team team1 = new Team("team1", "t1");
    Member member1 = new Member("member1", "m1");
    Member member2 = new Member("member2", "m2");
    
    member1.setTeam(team1);
    member2.setTeam(team1);

    List<Member> members = team1.getMembers();
    System.out.println("members.size = "+ members.size());
}
```
실행결과
```java
members.size = 0
```
Member.team에만 연관관계를 설정하고 반대방향은 연관관계를 설정하지 않았기 때문에 members.size가 0이 나온 것을 볼 수 있다.
그렇기 때문에 양방향은 양쪽 모두 관계를 설정해야 한다.
```java
team1.getMembers().add(members1);
```

- 양쪽 모두 관계를 설정한 코드
```java
public void test(){
    Team team1 = new Team("team1", "t1");
    Member member1 = new Member("member1", "m1");
    Member member2 = new Member("member2", "m2");
    
    member1.setTeam(team1);
    team1.getMembers().add(members1);
    
    member2.setTeam(team1);
    team1.getMembers().add(members2);
    
    List<Member> members = team1.getMembers();
    System.out.println("members.size = "+ members.size());
}
```
실행결과
```java
members.size = 2
```

이렇게 객체까지 고려한다면 양쪽 모두 관계를 맺어야한다.

## 연관관계 편의 메서드
![](../../../Desktop/주의 1 2.png)

<br>

```java
member1.setTeam(team1);
team1.getMembers().add(members1);
```
위처럼 각각 호출하다보면 실수로 둘 중 하나만 호출해서 양방향이 깨질 수 있기 때문에 두 코드를 하나인 것 처럼 사용하는 것이 안전하다.

```java
public class Member{
    private Team team;
    
    public void setTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
}
```

<br>

![](../../../Desktop/주의 1.png)
위의 코드는 teamA에서 teamB로 변경할 때 teamA > member1의 관계를 제거하지 않아 버그가 발생할 수 있다.
그렇기 때문에 Member 클래스의 setTeam() 메서드를 수정해서 코드를 리팩토링해야한다.

```java
public class Member{
    private Team team;
    
    public void setTeam(Team team){
        if (this.team != null){
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }
}
```

객체에서 양방향 연관관계를 사용하려면 로직을 견고하게 작성해야 한다.

# 정리하기
> 1. 객체와 테이블의 연관관계는 차이는?
> 2. 객체 그래프 탐색이란 ?
> 3. mappedBy 속성은?
> 4. ManyToOne에 mappedBy 속성이 없는 이유는 ?
> 5. 연관관계가 있는 엔티티를 조회하는 방법은 ?
> 6. 연관관계를 조회, 수정, 제거, 삭제하는 방법은 ?
> 7. 양방향 연관관계를 만드는 방법은 ?
> 8. 연관관계의 주인은 어떤 엔티티에 만들어야하는가 ?