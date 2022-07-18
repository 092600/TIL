> 본문의 내용은 HeadFirstDesignPattern 책을 읽은 후 작성한 내용입니다.
 
# 옵저버 패턴이란 ?
> 옵저버 패턴(Observer Pattern)은 한 객체의 상태가 바뀌면 그 객체에 의존하는 다른 객체에게 연락이 가고
> 자동으로 내용이 갱신되는 방식으로 일대다(one-to-many) 의존성을 정의하는 패턴
 
옵저버 패턴에서 상태가 바뀌는 객체를 주제(Subject)라고 하며 주제의 상태가 바뀔때마다 연락을 받는 객체들을
옵저버(Observer)라고 한다. 한 주제는 여러 개의 옵저버을 가질 수 있어 일대다 의존성을 가진다고 말할 수 있으며
옵저버는 언제든 주제를 구독할 수 있으며 구독해제 할 수 있다.

옵저버 패턴은 여러 가지 방식으로 구현할 수 있지만 보통 주제 인터페이스와 옵저버 인터페이스가 들어있는 클래스 디자인으로
구현한다.

## 옵저버 패턴 구현해보기
옵저버 패턴을 필요로하는 것이 뭐가 있을까요 ? 저는 이 옵저버 패턴을 공부하면서 내 주위에 옵저버 패턴으로 되어있는게 뭐가 있을까 ?하고
생각을 해봤는데요 한가지 밖에 생각이 나지 않더라구요. 제가 생각한 것중에 옵저버 패턴을 가지고 있을 거라고 생각한 것은 바로 
축구 경기 정보 알림 ? (뭐라고 해야할지 모르겠음)이였습니다. 축구경기 중에 어떤 팀이 골을 넣으면 그 경기에 관심이 있는
즉, 그 경기를 구독한 사람이 스코어가 변경될때마다 바뀐 스코어에 대한 정보를 받게하는 것이였습니다.

축구경기 정보 알림을 코드로 구현해볼건데 축구경기 정보 알림이라는 것을 옵저버 패턴으로 구현하기 위해서는 어떤 것을 사용해야할지
먼저 생각해보도록 하겠습니다.

우선 게속해서 바뀌는 축구 경기의 정보를 가질 주제(Subject)가 필요할 것 같구요, 축구 경기의 정보에 관심이 있는 옵저버들이 필요할 것 같습니다.
이제 주제와 옵저버를 구현해보도록 하겠습니다.

### 주제와 옵저버의 인터페이스 구현하기
주제와 옵저버를 구현할 떄, 우리는 바로 주제와 옵저버에 관한 클래스를 작성할 수 있지만 좀 더 객체지향적으로 코드를 작성해보겠습니다.
```java
public interface Subject {
    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObserver();
}

public interface Observer {
    public void update(int ATeamScore, int BTeamScore);
}
```
이렇게 Subject라는 인터페이스와 Observer라는 인터페이스를 작성해봤습니다.
Subject라는 인터페이스는 자기자신을 구독하는 옵저버들을 관리하기위해서 Observer를 참조매개변수로 갖는 registerObserver라는 추상메서드와
언제든 구독해제를 할 수 있게끔 Observer를 참조매개변수로 갖는 removeObserver라는 추상메서드, 또 주제의 데이터가 변경되었을때마다 옵저버들에게 바뀐
정보를 보내줄 notifyObserver라는 추상메서드를 작성했습니다.

그리고 Observer라는 인터페이스는 바뀐 정보를 받아서 옵저버를 구현한 클래스의 정보를 바꿀 Update라는 추상메서드를 작성했습니다.

### 주제 인터페이스를 구현하는 SoccerScoreData 클래스 작성하기 (1)
```java
import java.util.ArrayList;
import java.util.List;

public class SoccerScoreData implements Subject{
    private List<Observer> observerList;
    private int ATeamScore = 0;
    private int BTeamScore = 0;

    public SoccerScoreData(){
        observerList = new ArrayList<Observer>();
    }

    public void registerObserver(Observer observer){
        observerList.add(observer);
    }

    public void removeObserver(Observer observer){
        observerList.remove(observer);
    }

    public void notifyObserver(){
        for (Observer observer : observerList)
            observer.update(ATeamScore, BTeamScore);

    }
}
```
SoccerScoreData라는 클래스(주제)는 Subject라는 인터페이스를 구현하는 클래스입니다.
> 1. private List<Observer> observerList; 
 
옵저버들이 구독할 경우 이 리스트의 요소로서 관리하도록 했습니다.
> 2. private int ATeamScore = 0; private int BTeamScore = 0;

SoccerScoreData는 ATeam과 BTeam의 경기에 대한 정보를 알려주는 주제이기 떄문에 ATeam과 BTeam의 Score를 저장하는 변수를 선언했습니다.

> 3. ```java
>    public SoccerScoreData(){
>        observerList = new ArrayList<Observer>();
>    }
>    ```

SoccerScoreData의 생성자로 객체가 생성될 때, Observer만을 요소로 갖는 ArrayList를 observerList에 저장하는 코드입니다.

> 4. ```java
>    // 주제를 옵저버가 구독할 수 있도록 하는 코드
>    public void registerObserver(Observer observer){
>        observerList.add(observer);
>    }
>   
>    // 주제를 옵저버가 구독해제 수 있도록 하는 코드
>    public void removeObserver(Observer observer){
>        observerList.remove(observer);
>    }
>
>    // 주제의 바뀐 데이터를 옵저버에게 알려주는 코드
>    public void notifyObserver(){
>        for (Observer observer : observerList)
>            observer.update(ATeamScore, BTeamScore);
>    }
>    ```
    
위는 Subject 인터페이스의 추상메서드들을 오버라이딩한 메서드들입니다.
주제에 옵저버들을 구독할 수 있도록 하고 구독해제할 수 있도록 하고 변경된 데이트를 옵저버가 알 수 있도록 하는 메서드입니다. 


### 주제 인터페이스를 구현하는 SoccerScoreData 클래스 작성하기 (2)
```java
import java.util.ArrayList;
import java.util.List;

public class SoccerScoreData implements Subject{
    private List<Observer> observerList;
    private int ATeamScore = 0;
    private int BTeamScore = 0;

    public SoccerScoreData(){
        observerList = new ArrayList<Observer>();
    }
    
    public void registerObserver(Observer observer){
        observerList.add(observer);
    }

    public void removeObserver(Observer observer){
        observerList.remove(observer);
    }

    public void notifyObserver(){
        for (Observer observer : observerList)
            observer.update(ATeamScore, BTeamScore);

    }

    public void ScoreChanged(){
        notifyObserver();
    }
    
    public void setScore(int ATeamScore, int BTeamScore){
        this.ATeamScore = ATeamScore;
        this.BTeamScore = BTeamScore;
        ScoreChanged();
    }
}
```
추가한 메서드는 ScoreChanged와 setScore 입니다.
setScore 메서드는 바뀐 정보를 주제에 업데이트할 수 있게하고 업데이트 후 주제의 정보가 바뀌었음을 ScoreChanged 메서드를 통해서 옵저버들에게 알리는 기능을 합니다.

### 옵저버 인터페이스와 주제의 바뀐 정보를 보여줄 Display 인터페이스 작성하기

```java
public interface Observer {
    public void update(int ATeamScore, int BTeamScore);
}

public interface Display {
    public void display();
}
```
Observer와 Display는 간단합니다. Observer는 해당 인터페이스를 구현하는 객체의 정보를 업데이트해줄 update 메서드만을 정의하고 있습니다.
Display 인터페이스도 마찬가지로 해당 인터페이스를 구현하는 객체의 정보를 보여줄 display 라는 메서드만을 정의하고 있습니다.

### 옵저버 인터페이스를 구현하는 옵저버 객체 생성하기 (1)
```java
public class User1 implements Observer, Display{
    private int ATeamScore = 0;
    private int BTeamScore = 0;
    private Subject subject;
    
    public User1(Subject subject){
        this.subject = subject;
        subject.registerObserver(this);
    }

    public void update(int ATeamScore, int BTeamScore){
        this.ATeamScore = ATeamScore;
        this.BTeamScore = BwayTeamScore;
        display();
    }
    public void display(){
        System.out.println("현재 스코어는 ATeam : "+ATeamScore+" VS BTeam : "+BTeamScore);
    }
}

```
이번엔 SoccerScoreData라는 주제를 구독할 옵저버 코드를 작성했습니다.
우리는 SoccerScoreData만을 구독할 목적이 아니라 Subject의 역할을 하는 객체들을 언제든 자유롭게 구독할 수 있도록 하기 위해서
User1의 생성자의 매개변수로 Subject의 인터페이스를 구현하는 어떠한 클래스도 받을 수 있도록 코드를 작성했습니다.


### 잘 작동하는지 확인하기
```java
public class SoccerGameScoreAlertMachine {
    public static void main(String[] args){
        SoccerScoreData soccerScoreData = new SoccerScoreData();

        User1 aTeamVSBTeamDisplayElement =
                new User1(soccerScoreData);


        soccerScoreData.setScore(1, 0);
    }
}
```
"현재 스코어는 ATeam : 1 VS BTeam : 0"이라고 잘 출력하는 것을 확인하실 수 있으실겁니다.
이렇게 옵저버 패턴을 사용할 수 있습니다. 이번에 작성한 코드는 주제가 옵저버에게 정보를 전달할수만 있고 옵저버가 주제에게 정보를 가져갈 수 없습니다.
옵저버에서 지금 당장 데이터가 필요없을수도 있음에도 불구하고 말이죠. 옵저버가 필요할 때마다 주제에게서 정보를 받아갈 수 있다면 좀 더 효율적이지 않을까하는 생각이 들었습니다.
왜냐하면 주제에서 정보가 바뀔때마다 지금 당장 데이터가 필요없을 옵저버에게까지 데이터가 전달되기 떄문이죠. 자원낭비가 아닐까하는 ?
다음에 관련해서 공부를 해봐야겠습니다.


## 옵저버 패턴 공부 후 끄적끄적 만들어 본 코드
### Subejct와 Observer 인터페이스 작성

```java
public interface Subject {
    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObserver();

    public void display();
}

public interface Observer {
    public void update(int ATeamScore, int BTeamScore);
}
```

### 주제 인터페이스를 구현하는 SoccerScoreData 클래스
```java
import java.util.ArrayList;
import java.util.List;

public class SoccerScoreData implements Subject{
    private List<Observer> observerList;
    private int HomeTeamScore = 0;
    private int AwayTeamScore = 0;
    private String HomeTeamName;
    private String AwayTeamName;

    public void registerObserver(Observer observer){
        observerList.add(observer);
    }

    public void removeObserver(Observer observer){
        observerList.remove(observer);
    }

    public void notifyObserver(){
        for (Observer observer : observerList)
            observer.update(HomeTeamScore, AwayTeamScore);

    }

    public void ScoreChanged(){
        notifyObserver();
    }
    public SoccerScoreData(String HomeTeamName, String AwayTeamName){
        observerList = new ArrayList<Observer>();
        this.HomeTeamName = HomeTeamName;
        this.AwayTeamName = AwayTeamName;
    }

    public void setScore(int HomeTeamScore, int AwayTeamScore){
        this.HomeTeamScore = HomeTeamScore;
        this.AwayTeamScore = AwayTeamScore;
        ScoreChanged();
    }

    public void display(){
        System.out.println("현재 스코어는 "+HomeTeamName+"Team : "+HomeTeamScore+" VS "+AwayTeamName+" : "+ AwayTeamScore);
    }
}
```

### Observer 인터페이스를 구현하는 User1 클래스
```java
public class User1 implements Observer{
    private int HomeTeamScore = 0;
    private int AwayTeamScore = 0;
    private Subject subject;
    public User1(Subject subject){
        this.subject = subject;
        subject.registerObserver(this);
    }

    public void update(int HomeTeamScore, int AwayTeamScore){
        this.HomeTeamScore = HomeTeamScore;
        this.AwayTeamScore = AwayTeamScore;
        display();
    }
    public void display(){
        System.out.println(this.subject);
        this.subject.display();
    }
}
```

### 코드 테스트
```java
import java.util.Scanner;

public class SoccerGameScoreAlertMachine {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int choice;
        String HomeTeamName;
        String AwayTeamName;
        int HomeTeamScore = 0;
        int AwayTeamScore = 0;

        System.out.println("-- 축구경기 데이터 작성 프로그램 --");

        System.out.print("Home팀의 이름을 입력해주세요 : ");
        HomeTeamName = stdIn.nextLine();

        System.out.print("Away팀의 이름을 입력해주세요 : ");
        AwayTeamName = stdIn.nextLine();

        SoccerScoreData soccerScoreData = new SoccerScoreData(HomeTeamName, AwayTeamName);

        User1 aTeamVSBTeamDisplayElement =
                new User1(soccerScoreData);


        while (true){
            System.out.print("스코어 변경 : 1, 경기 종료 : 0 , 입력 > ");
            choice = stdIn.nextInt();

            if (choice == 1){
                System.out.print("변경할 Home팀의 점수을 입력해주세요 : ");
                HomeTeamScore = stdIn.nextInt();

                System.out.print("변경할 Away팀의 이름을 입력해주세요 : ");
                AwayTeamScore = stdIn.nextInt();

                soccerScoreData.setScore(HomeTeamScore, AwayTeamScore);
            } else if(choice == 0){
                System.out.println(HomeTeamName+"과 "+AwayTeamName+"의 경기는 "+HomeTeamScore+" : "+AwayTeamScore+"로 종료되었습니다.");
                break;
            } else {
                System.out.println("다시 입력해주세요.");
                continue;
            }
        }

    }
}

```
만약 SoccerScoreData라는 주제에서 데이터를 관리하는 모든 축구경기에 대한 정보를 받아보고 싶은 옵저버가 있을 경우,
이렇게 코드를 작성한다면 주제가 간단하게 팀 이름과 스코어에 관한 데이터만 바꿔주면서 옵저버에게 데이터를 줄 수 있지 않을까 ?
하는 생각에 작성해 봤습니다. 이제 그만 적을래 ..