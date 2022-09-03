> 자바 ORM 표준 JPA 프로그래밍을 읽고 작성한 내용입니다.
>
# JPA 06

## 다양한 연관관계 매핑

1. 다중성
   1. 다대일(@ManyToOne)
   2. 일대다(@OneToMany)
   3. 일대일(@OneToOne)
   4. 다대다(@ManyToMany)
   
2. 단방향, 양방향

3. 연관관계의 주인

엔티티의 연관관계를 매핑할 때는 위의 3가지를 고려해야한다.

테이블은 외래 키 하나로 조인을 사용해서 양방향으로 쿼리가 가능하므로 방향이라는 개념이 없지만 객체는 참조용 필드를 가지고 있는 객체만 연관된 객체를 조회할 수 있다.
연관관계의 주인을 정할 때는 외래 키를 가진 테이블과 매핑한 엔티티가 외래 키를 관리하는 것이 효율적이기 때문에 외래 키를 가진 엔티티를
연관관계의 주인으로 정하는게 좋다. 그리고 보통 다대일관계의 엔티티에서 다의 관계를 가진 엔티티를 연관관계의 주인으로 한다.
또 연관관계의 주인은 mappedBy 속성이 없다.

## 다대일 관계
> 데이터베이스 테이블의 일(1), 다(N) 관계에서 외래 키는 항상 다쪽에 있다. 따라서 객체 양방향 관계에서 연관관계의 주인은 항상 다쪽이다.
> 

![](../../../Desktop/다대일 양방향.png)

다대일 양방향의 객체 연관관계에서 실선이 연관관계의 주인이고 점선은 연관관계의 주인이 아니다.

- 양방향은 외래 키가 있는 쪽이 연관관계의 주인이다. 
일대다와 다대일 연관관계는 항상 다(N)에 외래 키가 있다. JPA는 외래 키를 관리할 때 연관관계의 주인만 사용한다.
주인이 아닌 객체는 조회를 위한 JPQL이나 객체 그래프를 탐색할 때 사용한다.
- 양방향 연관관계는 항상 서로를 참조해야 한다.

## 일대다 단방향 관계
일대다 관계는 다대일 관계의 반대 방향의 관계로 일대다 관계는 엔티티를 하나 이상 참조할 수 있으므로 자바 컬렉션인 Collection, List, Set, Map 중에 하나를
사용해야 한다.

![](../../../Desktop/JPA06 일대다 관계.png)

보통 자신이 매핑한 테이블이 외래 키를 관리하는데 일대다 관계에서 매핑은 반대쪽 테이블에 있는 외래 키를 관리한다.
외래 키는 항상 다쪽 테이블에 있지만 다 쪽인 member 엔티티에는 외래 키를 매핑할 수 있는 참조 필드가 없어 반대편 테이블이 외래 키를 관리하는 모습을 보인다.

```java
@Getter
@Setter
@Entity
public class Team{
    @Id
   @GemeratedValue
   @Column(name = "TEAM_ID")
   private Long id;
    
    private String name;
    
    @OneToMany
   @JoinColumn(name = "TEAM_ID")
   private List<Member> members = new ArrayList<Member>();
}
```
일대다 단방향 관계를 매핑할 때는 @JoinColumn 어노테이션을 명시해야 한다.
그렇지 않으면 JPA는 연결 테이블을 중간에 두고 연관관계를 관리하는 조인 테이블 전략을 기본으로 사용해서 매핑한다.

### 일대다 단방향 관계 단점

```java
public void test(){
    Member member1 = new Member("member1");
    Member member2 = new Member("member");
    
    Team team1 = new Team("team1");
    team1.getMembers().add(member1);
    team1.getMembers().add(member2);
    
    em.persist(member1); // INSERT INTO member("member1");
    em.persist(member2); // INSERT INTO member("member2");
    em.persist(team1);  // INSERT INTO team("team1"); UPDATE member1, member2
    
    transaction.commit();
}
```

일대다 관계에서는 외래 키가 다른 테이블에 있기 때문에 엔티티의 저장과 연관관계 처리를 INSERT SQL 한 번으로 끝낼 수 있지만, 다른 테이블에 외래 키가 있으면 연관
관계 처리를 위한 UPDATE SQL을 추가로 실행해야 한다.

member 엔티티는 team엔티티를 모르기 때문에 member1과 member2를 저장할 때 team에 관한 정보는 저장되지 않는다.
대신 team 엔티티를 저장할 때 members의 참조값을 확인해서 member 테이블에 있는 외래 키를 업데이트 한다.

일대다 단방향 매핑을 사용하면 엔티티를 매핑한 테이블이 아닌 다른 테이블의 외래 키를 관리해야 한다. 이것은 성능 문제도 있지만 관리도 부담스럽기 때문에 일대다 단방향 매핑이 아닌
다대일 양방향 매핑을 사용하는 것이 좋다. 다대일 양방향 매핑은 관리해야 하는 외래 키가 본인 테이블에 있어 일대다 단방향 매핑과 같은 문제가 발생하지 않는다.

## 일대다 양방향 관계
일대다 양방향 매핑은 존재하지 않는다 대신 다대일 양방향 매핑을 사용해야 한다. 그렇다고 일대다 양방향 매핑이 완전히 불가능한 것은 아니다.
일대다 단방향 매핑 반대편에 같은 외래 키를 사용하는 다대일 단방향 매핑을 읽기 전용으로 하나 추가하면 된다.

```java
@Entity
public class Team{
    @Id
   @GeneratedValue
   @Column(name="TEAM_ID")
   private Long id;
    
    private String name;
    
    @OneToMany
   @JoinColumn(name="TEAM_ID")
   private List<Member> members = new ArrayList<Member>();
}
@Entity
public class Member {
   @Id
   @GeneratedValue
   @Column(name="TEAM_ID")
   private Long id;

   private String username;
   
   @ManyToOne
   @JoinColumn(name="TEAM_ID", insertable=false, updatable=false)
   private Team team;
}
```

일대다 단방향 매핑 반대편에 다대일 단방향 매핑을 추가했다. 이때 일대다 단방향 매핑과 같은 TEAM_ID 외래 키 컬럼을 매핑했다. 이렇게 되면
둘 다 같은 키를 관리하므로 문제가 발생할 수 있지만 다대일 쪽을 insertable=false, updatable=false로 설정해서 읽기만 가능하게 했다.

이 방법은 일대다 양방향 매핑이라기보다는 일대다 단방향 매핑 반대편에 다대일 단방향 매핑을 읽기 전용으로 추가해서 일대다 양방향처럼 보이도록 하는 방법이기 때문에
일대다 단방향 매핑이 가지는 단점을 그대로 가진다.

## 일대일 관계
### 일대일 관계 핵심
1. 일대일 관계는 그 반대도 일대일 관계다
2. 일대일 관계에서는 주 테이블이나 대상 테이블 둘 중 어느 곳이나 외래 키를 가질 수 있다.
3. 주 테이블에 외래 키
   1. 외래 키를 객체 참조와 비슷하게 사용할 수 있어서 객체지향 개발자들이 선호한다.
   2. 주 테이블이 외래 키를 가지고 있어 주 테이블만 확인해도 대상 테이블과 연관관계가 있는지 알 수 있다.

4. 대상 테이블에 외래 키
   1. 테이블 관계를 일대일에서 일대다로 변경할 때 테이블 구조를 그대로 유지할 수 있다.

```java
@Entity
public class Member {
    @Id @GeneratedValue
   @Column(name="MEMBER_ID")
   private Long id;
    
    @OneToOne
   @JoinColumn(name="LOCKER_ID")
   private Locker locker;
}
@Entity
public class Member {
   @Id @GeneratedValue
   @Column(name="LOCKER_ID")
   private Long id;
   
   private String name;

   @OneToOne(mappedBy="locker")
   private Member member;
}
```
양방향이므로 연관관계의 주인을 정해야 한다. Member 테이블이 외래 키를 가지고 있으므로 Member 엔티티에 있는 Member.locker가 연관관계의 주인이다.
따라서 반대 매핑인 사물함의 Locker.member는 mappedBy를 선언해서 연관관계의 주인이 아니라고 설정해야 한다.




