> 본문의 내용은 HeadFirstDesignPattern 책을 읽은 후 작성한 내용입니다.
>

# new 연산자를 사용하는 방법
> new 연산자를 사용하면 구상 클래스의 인스턴스가 만들어지는데 이것은 특정 구현을 사용하는 것으로 추후에 코드를 수정해야 할 가능성이 커져 유연성이 떨어진다. 그렇다면 어떻게 구상 클래스의 인스턴스를 생성하는 부분을 코드에서 분리(캡슐화)해서 확장에 열려있고 변경에 닫혀있는 코드를 작성할 수 있을까?


## 생각해보기
```java
public class UserFactory{
    public void createUser(String job){
        User user;

        if (job.equals("police")){
            user = new Police();
        } else if (job.equals("officer")){
            user = new Officer();
        } else if(job.equals("SoccerPlayer")) {
            user = new SoccerPlayer();
        }

        user.takeShower();
        user.wearingClothes();
    }
}
```
- Police와 Officer, SoccerPlayer는 User 인터페이스를 구현한 클래스이다.

위의 work는 인자로 받은 job을 바탕으로 구상 클래스의 인스턴스를 만들고 변수에 그 인스턴스를 대입한다.
그 후 해당 인스턴스에게 여러 메서드를 실행하도록 한다.

만약 User 인터페이스를 구현하는 클래스들의 개수가 더 늘어나게 되면 createUser의 메서드를 직접 고쳐야 한다.
메서드를 직접 고치는 것이 아닌 다른 방법이 없을까 ?

## 변하는 부분과 변하지 않는 부분
다른 방법을 생각해보기 전에 이전에 공부했던 내용을 생각해보도록 하자. 위의 코드에서 상황에 따라 변하는 부분과 변하지 않는 부분은 무엇일까 ?
변하는 부분은 변하는 job 인자에 따라 다른 객체를 user에 할당하는 부분이다. 변하지 않는 부분은 user.takeShower(); user.wearingClothes();다.
```java
// 변하는 부분
if (job.equals("police")){
    user = new Police();
} else if (job.equals("officer")){
    user = new Officer();
} else if(job.equals("SoccerPlayer")) {
    user = new SoccerPlayer();
}

// 변하지않는 부분
user.takeShower();
user.wearingClothes();
```

변하는 부분과 변하지 않는 부분으로 나뉜다면 코드에서 변하는 부분과 변하지 않는 부분을 나눠 캡슐화해야하지 않겠는가 ?
위의 메서드에서 변하는 부분과 변하지 않는 부분을 분리하여 캡슐화해보자

## 객체 생성 부분 캡슐화하기
우리는 위의 변하는 부분인 객체 생성 부분을 분리하여 캡슐화하기 위해 객체 생성 팩토리를 만들어보자.
팩토리란 객체 생성을 처리하는 클래스를 의미한다.

```java
public class SimpleUserFactory(){
    public User createUser(String job){
        User user = null;

        if (job.equals("police")){
            user = new Police();
        } else if (job.equals("officer")){
            user = new Officer();
        } else if(job.equals("SoccerPlayer")) {
            user = new SoccerPlayer();
        }

        return user;
    }
}
```
SimpleUserFactory 클래스는 User 객체 생성을 위한 클래스로 매개변수에 입력된 값에 맞는 User 객체를 
리턴해주는 createUser() 메서드를 가지고 있다. 이렇게 객체 생성 작업을 팩토리 클래스로 캡슐화해 놓으면 구현을 변경할 때 여기저기 고칠 필요 없이 팩토리 클래스 하나만 고치면 된다.

## '간단한 팩토리(Simple Factory)'의 정의
> 간단한 팩토리는 디자인 패턴이라기 보다는 프로그래밍에서 자주 쓰이는 관용구에 가깝다.

## 다양한 팩토리 만들기
팩토리를 통해서 객체를 생성하는 부분을 핵심 로직?들과 분리해서 코드 간 응집도는 높이고 결합력은 낮춰 좀 더 유지보수에 쉬운 코드를 작성할 수 있다.
그렇다면 한국 유저와 중국 유저, 일본 유저 생성을 분리할 수 있도록 팩토리를 여러 개 만들어 보도록 하자.

한국 유저를 관리하기 위한 KoreanUserFactory 와 중국 유저를 관리하기 위한 ChineseUserFactory,
일본 유저를 생성하기 위한 JapaneseUserFactory 같이 각 나라별 유저를 생성할 팩토리를 만들었다.

한국 유저와 중국, 일본 유저 모두 공통적으로 사용하는 멤버 변수나 메서드가 존재한다. 그렇기 때문에 User 추상 클래스를 만들어 상속을 해주었다.
그리고 유저에게 국가를 설정해주기 위해서 Korea, Japan, China 와 같은 인터페이스를 작성해주었다.

```java
// User
public abstract class User {
    private String country;
    private String job;

    public void takeShower(){
        System.out.println("꺠끗하게 씻는 중 입니다.");
    }
    public void wearingClothes(){
        System.out.println("깔끔하게 옷을 입는 중 입니다.");
    }
}

public interface China {
    String COUNTRY = "china";
}

public interface Japan {
    String COUNTRY = "japan";
}

public interface Korea {
    String COUNTRY = "korea";
}
```

추상 클래스인 유저 클래스를 상속받으며 국가별 인터페이스를 구현해줄 KoreanUser, JapanUser, ChinaUser 클래스를 작성했다.
```java
public class KoreanUser extends User implements Korea {

}

public class JapaneseUser extends User implements Japan {

}

public class ChineseUser extends User implements China {
    
}
```

또 User의 직업을 구현해줄 직업군을 인터페이스로 작성해주었고 해당 인터페이스를 구현하는 클래스를 작성했다.

```java
public interface Officer {
    String job = "officer";
}

public interface Police{
    String job = "police";
}

public interface SoccerPlayer {
    String job = "soccer player";
}
```

```java
public class KoreanOfficer extends KoreanUser implements Officer{
    
}

public class KoreanPolice extends KoreanUser implements Police{

}

...

public class ChineseSoccerPlayer extends ChineseUser implements SoccerPlayer{

}
```
이렇게 유저 객체 생성을 코드에서 분리할 수 있도록 지금까지 작성한 클래스와 인터페이스를 사용하여 각 국가별 유저 객체를 생성할 팩토리 클래스를 작성해보자.

```java
// UserFactory
public interface UserFactory {
    public User createUser(String job);

}

// KoreanFactory
public class KoreanUserFactory implements UserFactory, Korea {
    private String country = Korea.COUNTRY;
    public User createUser(String job){
        User user = null;

        if (job.equals("police")){
            user = new KoreanPolice();
        } else if (job.equals("officer")){
            user = new KoreanOfficer();
        } else if(job.equals("soccer player")) {
            user = new KoreanSoccerPlayer();
        } else {
            user = new KoreanDeadBeat();
        }

        user.setJob(job);
        user.setCountry(this.country);
        user.takeShower();
        user.wearingClothes();

        System.out.println();
        System.out.println("유저가 준비를 마치고 생성되었습니다.");
        System.out.println();

        return user;
    }

}

public class JapaneseUserFactory implements UserFactory, Japan {
    private String country = Japan.COUNTRY;

    public User createUser(String job){
        User user = null;

        if (job.equals("police")){
            user = new JapanesePolice();

        } else if (job.equals("officer")){
            user = new JapaneseOfficer();

        } else if(job.equals("soccer player")) {
            user = new JapaneseSoccerPlayer();

        } else {
            user = new JapaneseDeadBeat();

        }

        user.setJob(job);
        user.setCountry(this.country);
        user.takeShower();
        user.wearingClothes();

        System.out.println();
        System.out.println("유저가 준비를 마치고 생성되었습니다.");
        System.out.println();

        return user;
    }

}

...
```
팩토리의 createUser 메소드의 인자로 job이 들어가면 해당 job에 맞는 직업군의 유저 클래스가 리턴된다.
이렇게보면 아래의 메서드들이 존재하기때문에 객체 생성과 아래 메서드들이 분리되지 않은 것이 아닌가? 하는 생각이 든다.
아래 메서드들을 작성한 목적은 유저 클래스가 생성되기 전 꼭 거쳐줘야하는 작업이라는 의미에서 넣어준 것으로 객체 생성이라는 큰 틀 안에 들어간다고 생각해서 작성해주었다.

그렇다면 팩토리를 통해서 생성된 유저 객체를 사용해보고 싶다는 생각이 들지 않는가 ? 나는 한번 사용해보고 
싶다는 생각이 들어서 생성된 유저를 사용해줄 Simz 클래스를 작성해봤다.

```java

public class Simz {
    Scanner stdIn = new Scanner(System.in);

    public UserFactory selectUserFactory(String country){
        UserFactory userFactory = null;

        if ((country.equals("korea"))){
            userFactory = new KoreanUserFactory();
        } else if ((country.equals("japan"))){
            userFactory = new JapaneseUserFactory();
        } else if ((country.equals("china"))) {
            userFactory = new ChineseUserFactory();
        }

        return userFactory;
    }
    public User createUser(){
        stdIn.nextLine();

        System.out.println("나라를 입력하세요.");
        String country = stdIn.nextLine();

        System.out.println("직업을 입력하세요.");
        String job = stdIn.nextLine();

        UserFactory userFactory = selectUserFactory(country);
        userFactory.setCountry(country);

        return userFactory.createUser(job);
    }
}
```

Simz 클래스에서 createUser() 메서드를 사용한 것과 같이 객체 생성을 하는 부분을 selectUserFactory()메서드를 사용해서 나라에 맞는
유저 생성 팩토리를 리턴받아서 해당 팩토리의 createUser()메서드를 통한 유저 객체 리턴해주고 있다.

객체 생성 코드와 로직 코드의 분리를 통해서 코드 간 결합력을 낮추어 유지 보수에 용이하도록 코드를 작성했다.
이렇게 객체 생성을 팩토리 클래스에게 맡긴다면 좀 더 좋은 코드를 작성할 수 있을 것 같다.

> 위 코드들은 https://github.com/092600/TIL/tree/master/%EC%9E%90%EB%B0%94/HadFirstDesignPattern/chap04 에 정리되어 있습니다.




