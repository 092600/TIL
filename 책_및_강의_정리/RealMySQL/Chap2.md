
# MySQL 설치 및 설정

MySQL을 설치하는 방법은 매우 여러가지가 있다.
Tar 또는 Zip으로 압축된 버전을 다운받는 방법, 리눅스 RPM을 통해 설치하는 방법 이외에도 여러가지 방법들이 있는데 RealMySQL에서는 가능하다면 리눅스의 RPM 혹은 운영체제별 인스톨러를 이용하는 것을 권장하고 있다.
RPM을 사용하거나 윈도우에서 윈도우용 MySQL 인스톨러를 사용하는 방법은 RealMySQL에 있으므로 나는 다른 방법을 사용해서 다운해볼 것이다.

## MySQL 버전 선택하기

MySQL 서버의 버전을 선택할 때 따로 고려해야할 사항이 없다면 가능한 최신 버전을 사용하는 것이 좋다.
또 기존 버전에서 새로운 메이저 버전으로 업그레이드 된 경우라면 최소 패치 버전이 15~20번 이상 릴리스된 버전을 선택하는 것이 안정적인 서비스를 운영하는데 좋다.

## MySQL 설치하기

본인은 M1 맥을 사용하고 있는데 M1 맥에서는 이미 MySQL을 다운해서 사용하고 있기때문에 UTM을 사용해서 22.04 LTS 우분투 데스크탑에서 MySQL을 다운해볼 것이다.


우선 우분투 가상머신에서 터미널을 열고 아래의 명령어를 입력해보자.

```bash
sudo apt update
sudo apt install mysql-server -y
```

위의 명령어를 입력하면 아래와 같이 MySQL 서버가 설치된다. 

![[책_및_강의_정리/RealMySQL/이미지/Chap2. 2.gif]]


위의 이미지처럼 MySQL 서버를 설치했다면 
MySQL 설정 파일인 /etc/mysql/mysql.conf.d의 my.cnf 이 생길 것이다. 이 파일에 MySQL 서버의 기본 설정들을 적을 수 있다. 또 /var/log/mysql의 error.log 파일에는 MySQL 서버 실행 중에 생기는 에러들을 확인할 수 있으며 /usr/lib/systemd/system의 mysql.service 파일이 생성되었을텐데 systemctl 유틸리티를 이용해서 MySQL을 실행하거나 종료할 수 있다. systemctl을 사용해서 MySQL 서버의 정보를 가져오고 실행시켜보자.

![[Obsidians_Multi_Uses/책_및_강의_정리/RealMySQL/이미지/Chap2. 2.gif]]

<br>

- 사용한 명령어

```bash
systemctl status mysql # MySQL 상태확인
systemctl stop mysql   # MySQL 종료
systemctl start mysql  # MySQL 실행
```

위의 이미지에서는 사용한 명령어들을 정리해봤다. 이 명령어를 사용해서 MySQL 서버를 시작하고 종료할 수 있지만 MySQL 배포판과 함께 제공되는 mysqld_safe 를 이용해서 MySQL 서버를 시작하고 종료할 수 있다.
이 스크립트를 이용하면 MySQL 설정 파일(my.cnf)의 mysqld_safe 섹션의 설정들을 참조해서 MySQL 서버를 시작하지만 systemd(systemctl)을 이용하는 경우에는 mysqld_safe 스크립트를 무시하게된다.
그렇기때문에 malloc-lib와 같은 시스템 설정을 적용하고자 한다면 mysqld_safe 스크립트를 이용해 MySQL 서버를 실행하도록 하자.

- 참고
mysqld_safe 위치 : /etc/mysqld_safe

## MySQL 실행하기

MySQL을 실행하기 전에 MySQL 서버가 켜져있는지 확인해보자.
MySQL 상태확인을 하려면 위에서 사용했던 systemctl status mysql 명령어를 사용해야한다. 만약 MySQL 서버가 꺼져있다면 systemctl start mysql 명령어를 이용해 MySQL 서버를 실행하자.


- 실행하기 전 확인하기
```bash
# MySQL 서버 상태 확인
systemctl status mysql

# MySQL 서버가 꺼져있다면
systemctl start mysql
sudo mysql -u root

# MySQL의 데이터베이스 확인하기
show databases;
```

![[Obsidians_Multi_Uses/책_및_강의_정리/RealMySQL/이미지/Chap2. 3.gif]]


위의 코드와 같이 입력했다면 위의 이미지처럼 mysql 프롬프트가 표시되고 'mysql>'로 표시된다.

## MySQL 서버에 접속하는 방법

이전에 MySQL 서버에 접속하는 방법을 사용할 수도 있지만 이번에는 MySQL 서버에 접속하기 위해 소켓을 이용하는 방법, TCP/IP를 통해 접속하는 방법에 대해서 알아보자.

- MySQL 서버에 접속하는 방법
```bash
# 1. 소켓을 이용해 MySQL서버에 접속하는 방법
mysql -u root -p --host=localhost --socket=/tmp/mysql.sock
mysql -u root -p --host=localhost --port=3306 

# 2. TCP/IP를 통해 MySQL서버에 접속하는 방법
mysql -u root -p --host=127.0.0.1 --port=3306 
```

만약 원격 호스트에 있는 MySQL 서버에 접속하려고 한다면 2번 방법을 사용해야한다.
이 때 호스트를 127.0.0.1로 명시하는 것과 localhost로 명시하는 것의 의미가 다른데 --host=localhost를 사용하면 항상 MySQL 서버에 소켓을 사용해서 접속하게된다.
이는 Unix domain socket을 이용하는 방식으로 TCP/IP를 통한 통신이 아니라 유닉스의 프로세스 간 통신(IPC : Inter Process Communication)의 일종이다.
TCP/IP 통신을 통해 MySQL 서버에 접속하고 싶다면 host값을 127.0.0.1로 입력하자.

마지막으로 우리가 MySQL 실행하기에서 MySQL 서버에 접속한 방식에 대해서 알아보자.
이 방식은 호스트의 기본값으로 localhost가 되어 소켓 파일을 사용하게 된다.
소켓 파일의 위치는 MySQL 서버의 설정 파일에서 읽어서 사용하는데 이 소켓 파일은 MySQL 서버를 재시작하지 않으면 다시 만들어낼 수 없기때문에 실수로 삭제하지않도록 주의하자.

## MySQL 서버의 접속 가능 여부 확인하기

MySQL에 접속하지 않고 MySQL에 접속이 가능한지만 알아보는 방법에 대해서 알아보자.
MySQL 서버의 접속 가능 여부를 알기 위해서 telnet 명령어를 사용하거나 nc(Netcat) 명령을 이용할 수 있다.

```bash
# telnet host port
telnet 127.0.0.1 3306

# nc host port
nc 127.0.0.1 3306
```

위처럼 명령어를 사용한다면 127.0.0.1의 호스트에 3306 포트가 응답가능한 상태인지 확인할 수 있다.
이 명령어에 대한 응답으로 MySQL 서버가 보내준 메시지를 확인할 수 있을 것이다. 

## MySQL 버전 업그레이드하기

MySQL 서버의 버전을 업그레이드하는 방법으로 아래의 두가지를 생각해볼 수 있다.

1. MySQL 서버의 데이터 파일을 그대로 두고 업그레이드하는 방법
2. mysqldump 도구 등을 이용해서 MySQL 서버의 데이터를 SQL 문장이나 텍스트로 덤프한 후, 새로 업그레이드된 버전의 MySQL 서버에서 덤프된 데이터를 적재하는 방법

위의 두가지 방법 중에서 1번 방법을 인플레이스 업그레이드(In-Place Upgrade)라고 하며 2번 방법을 논리적 업그레이드(Logical Upgrade) 방법이라고 한다.
인플레이스 업그레이드는 제약이 여러개 존재하지만 업그레이드 시간을 크게 단축시킬 수 있다.
하지만 논리적 업그레이드는 버전 간 제약 사항이 거의 없지만 업그레이드에 사용하는 시간이 많이 소요될 수 있다.

### 인플레이스 업그레이드 제약사항



업그레이드는 마이너 버전 간 업그레이드와 메이저 버전 간 업그레이드로 분류된다.
마이너 버전 간 업그레이드와 메이저 버전 간 업그레이드에 대해서 알아보기 전에 마이너 버전과 메이저 버전이 무엇인지 알아보자.

![[Obsidians_Multi_Uses/책_및_강의_정리/RealMySQL/이미지/Chap2. 5.png]]


위의 이미지를 보면 알 수 있듯이 내가 설치한 MySQL의 버전은 8.0.31 이다. 여기서 8.0 을 메이저 버전, .31 을 마이너 버전이라고 한다.

동일 메이저 버전에서 마이너 버전 간 업그레이드는 대부분 **데이터 파일**의 변경 없이 진행되며, 여러 버전을 건너뛰어서 업그레이드하는 것도 가능하다.
하지만 메이저 버전 간 업그레이드는 크고 작은 데이터 파일의 변경이 필요하기 때문에 반드시 직전 버전에서만 업그레이드가 가능하다.

- 메이저 버전 및 마이너 버전 업그레이드 예
```bash
# 메이저 버전 업그레이드
1. 5.5 에서 5.6 으로 업그레이드 : 가능
2. 5.5 에서 5.7 으로 업그레이드 : 불가능

# 마이너 버전 업그레이드
1. 8.0.30 에서  8.0.31 로의 업그레이드 : 가능
2. 8.0.1 에서  8.0.30 로의 업그레이드 : 가능
```

만약 MySQL 서버의 5.1 버전에서 8.0으로 업그레이드해야하는 경우에는 5.1 버전과 8.0 버전 사이의 메이저 버전을 차례로 업그레이드 해야한다. 
하지만 이 과정은 매우 번거롭기때문에 메이저 버전을 두 버전 이상 업그레이드해야할 때 mysqldump 프로그앰으로 기존 MySQL 서버의 데이터를 백업한 후 8.0 버전의 MySQL 서버에 적재하는 논리적 업그레이드가 더 나은 방법이 될 수도 있다.

또 인플레이스 업그레이드 시 주의해야 할 점으로 메이저 버전 업그레이드가 특정 마이너 버전에서만 가능한 경우가 있다는 것인데 예를 들어, MySQL 5.7.8 에서 8.0 버전으로 바로 업그레이드 할 수 없다. 그 이유는 5.7.8 버전이 GA(General Availability) 버전, 오라클에서 안정성을 확인한 버전, 이 아니기 때문이다. 
그렇기 때문에 사용할 MySQL의 버전을 선택할 때 최소 GA은 지난 15번 ~ 20번 이상의 마이너 버전을 선택하는 것이 좋다.
**만약 인플레이스 업그레이드를 해야하는 경우라면 MySQL 서버의 매뉴얼을 정독한 후 진행하도록 하자.**


## MySQL 서버 설정하기

MySQL 서버는 단 하나의 설정 파일을 사용한다.
리눅스를 포함한 유닉스 계열에서는 my.cnf라는 이름을 사용하고, 윈도우 계열에서는 my.ini라는 이름을 사용한다.
하지만 이 설정파일의 위치가 고정되어 있는 것은 아닌데 MySQL 서버는 지정된 여러 개의 디렉터리를 순차적으로 탐색하면서 처음 발견된 my.cnf 파일을 사용하게 된다.

또 직접 MySQL을 컴파일 해서 설치한 경우에는 디렉터리가 다르게 설정될 수 있다. 
만약 어느 디렉터리에서 my.cnf 파일을 읽어와 사용하고 있는지 확인하고 싶다면 아래의 명렁어를 입력해보자.

1. mysql --help
2. mysql --verbose --help
3. sudo mysqladmin variables --help

명령어를 입력하고 나면 아래와 같은 텍스트가 나오는 것을 확인할 수 있다.

![[Obsidians_Multi_Uses/책_및_강의_정리/RealMySQL/이미지/Chap2. 6.png]]



나같은 경우에는 위의 이미지와 같이 /etc/my.cnf /etc/mysql/my.cnf ~/.my.cnf 와 같이 적혀있다.
이렇게 적혀있는 이유는 MySQL 서버가 한 개의 디렉터리가 아닌 여러 개의 디렉터리에서 설정 파일을 가져올 수 있다는 의미인데 이러한 특징은 사용자를 혼란스럽게 하는 부분이기도 하다.
만약 /etc 디렉터리와 /etc/mysql 디렉터리에 my.cnf 파일을 만든 경우었다면 MySQL 서버가 어떤 이렉터리의 my.cnf 파일을 참조했는지 알아내기가 쉽지않은데
이러한 경우에는 mysql --help나 위의 다른 명령어를 사용해 디렉터리의 우선순위를 확인해 어떤 my.cnf를 MySQL  서버가 우선적으로 사용하는지 확인하면된다.


## MySQL 시스템 변수 특징

MySQL 서버를 시작할 때 설정 파일의 내용을 읽어 메모리나 작동 방식을 초기화하고, 접속된 사용자를 제어하기 위한 값을 별도로 저장해 둔다.
MySQL 서버에서는 이렇게 저장된 값을 시스템 변수(System Variables)라고 하는데 각 시스템 변수는 MySQL 서버에 접속한 후 SHOW VARIABLES 또는 SHOW GLOBAL VARIABLES라는 명령으로 확인할 수 있다.

이 변수들의 정보는 https://dev.mysql.com/doc/refman/8.0/en/server-system-variable-reference.html 에서 찾아볼 수있다.

![[Obsidians_Multi_Uses/책_및_강의_정리/RealMySQL/이미지/Chap2. 7.png]]
<br>

각 변수의 Cmd-Line, Option File, System var, Var Scope, Dynamic 속성을 가지는데 이 속성 값의 의미에 대해서 알아보자.

1. **Cmd-Line**

MySQL 서버의 명령형 인자로 설정될 수 있는지를 나타낸다. 이 값이 Yes라면 명령형 인자로 이 시스템 변수의 값을 변경할 수 있다.

2. **Option File**

MySQL의 설정 파일인 my.cnf로 제어할 수 있는지를 나타낸다. 

3. **System var** 

시스템 변수인지 아닌지를 나타낸다. 옛날에 MySQL 서버의 설정 파일을 작성할 때 여러 사람의 손을 거치면서 하이픈("-")이나 언더스코어("_")로 변수명이 설정되어 변수들의 이름이 통일성이 없었는데 MySQL 8.0 부터 모든 시스템 변수들이 "_"를 구분자로 사용하도록 변경되었다.
하지만 명령형 인자로 사용 가능한 설정들은 "_" 가 아닌 "-"를 구분자로 사용한다.

4. **Var Scope** 

시스템 변수의 적용 범위를 나타낸다.
이 시스템 변수가 영향을 미치는 곳이 MySQL 서버 전체인지(글로벌 변수) 혹은 서버와 클라이언트 간의 커넥션만인지(세션 변수) 구분한다. 하지만 세션과 글로벌 범위에 모두 적용되는 시스템 변수도 존재하므로 주의해야한다.

5. **Dynamic** 

시스템 변수가 동적인지 정적인지 구분하는 변수이다.


## 글로벌 범위 시스템 변수와 세션 범위 시스템 변수

글로벌 범위의 시스템 변수와 세션 범위 시스템 변수는 MySQL 서버에 접속할 때 기본으로 부여하는 옵션의 기본값을 제어하는데 사용된다.
**일반적으로 세션별로 적용되는 시스템 변수의 경우 글로벌 변수뿐만 아니라 세션 변수에도 동시에 존재한다. 이 경우 Var Scope에 Both라고 표기된다.**

글로벌 범위 시스템 변수는 MySQL 서버 인스턴스에서 전체적으로 영향을 미치는 시스템 변수를 의미하며 주로 MySQL 서버 자체에 관련된 설정일 때가 많다.

- 글로벌 범위 시스템 변수 2가지 예

1. InnoDB 버퍼 풀 크기
2. MyISAM의 키 캐시 크기

MySQL에서 각 클라이언트가 처음에 접속하면 기본적으로 부여하는 기본 값을 가지고 있는데 따로 그 값을 변경하지 않은 경우에는 기본 값을 그대로 사용하고 클라이언트의 필요에 따라 개별 커넥션 단위로 값을 변경해서 사용한다.

- 세션 범위 시스템 변수 2가지 예

1. autocommit 변수
2. error_count 변수

세션 범위의 시스템 변수 중 MySQL 서버 설정 파일에 명시해 초기화할 수 있는 변수는 대부분 범위가 "Both"라고 명시돼 있다.


## 정적 변수와 동적 변수

MySQL 변수는 MySQL 서버가 동작 중에 변경 가능한지에 따라 동적 변수와 정적 변수로 구분된다.
동적 변수는 SET 명령어를 통해 값을 변경할 수 있는 것과 달리 정적 변수는 SET 명령어를 통해 값을 변경할 수 없다.
SET 명령어를 통해 변수의 값을 변경하는 경우는 클라이언트의 MySQL 서버 인스턴스에만 적용되므로 영구적이지 않은데 만약 영구적으로 변경하고 싶다면 MySQL 서버 설정을 적어둔 my.cnf 파일을 변경하거나 SET PERSIST 명령어를 사용해야한다. SET PERSIST 명령어를 통해 변경한 경우에는 my.cnf가 아닌 별도의 파일에 변경사항이 저장된다.

또 SET 명령어가 아닌 my.cnf 을 통해 변수의 값을 변경하는 경우에는 영구적이지만 변경사항이 바로 서버에 반영되지 않을 수 있다. 그 이유는 MySQL 서버가 실행될 때 my.cnf 설정을 읽은 후에 메모리에 값을 저장해 사용하는데 my.cnf를 변경한다고 해서 MySQL 서버가 현재 사용하는 값이 저장되어있는 메모리에 값이 변경되는 것이 아니기 때문이다.

### SET PERSIST

동적 시스템 변수의 경우 SET 혹은 SET PERSIST 명령어를 통해 값을 변경할 수 있다.
동적 시스템 변수의 경우 MySQL 서버에서 SET GLOBAL 명령으로 변경하면 즉시 MySQL 서버에 반영된다.
예를 들어, MySQL 서버의 max_connections 시스템 변수가 있는데 이 시스템 변수는 MySQL 서버로 접속할 수 있는 최대 커넥션 수를 제한하는 동적 변수다.
MySQL 서버에 커넥션을 많이 사용 중이라면 최대 연결 가능 커넥션의 개수를 더 늘리기 위해서 아래의 코드로 MySQL 서버의 시스템 변수를 즉시 변경할 수 있다.

```bash
SET GLOBAL max_connections=5000;
```

하지만 이렇게 SET GLOBAL을 통해서 변경한 값은 영구적이지 않다.
그렇기 때문에 MySQL 8.0 버전부터 SET PERSIST 명령어가 추가되었다. 이 명령어를 통해 값을 변경하게되면 변경사항이 mysqld-auto.cnf 파일에 저장되게 되는데 다음 MySQL 서버를 실행할 때, my.cnf 파일를 조회한 후에 mysqld-auto.cnf 파일도 같이 조회한 후 시스템 변수를 적용한다.

```bash
SET PERSIST max_connections=4000;
```

하지만 SET PERSIST 명령은 세션 변수에 적용되지 않으며, SET PERSIST 명령으로 시스템 변수를 변경하면 MySQL 서버는 자동으로 GLOBAL 시스템 변수의 변경으로 인식하고 변경한다. 
현재 실행 중인 MySQL에는 변경 내용을 적용하지 않고 다음 재시작을 위해 mysqld-auto.cnf 파일에만 변경 내용을 기록하고 싶다면 SET PERSIST ONLY 명령을 사용하면 된다.

```bash
SET PERSIST ONLY max_connections=3000;
```

SET PERSIST ONLY는 정적 시스템 변수를 영구적으로 변경하고자 할 때도 사용할 수 있는데 정적 시스템 변수는 MySQL 서버가 실행되어 있을 때 변경할 수 없다. 하지만 SET PERSIST ONLY는 mysqld-auto.cnf에 변경사항을 적고 다음 실행 때 적용하기 때문에 정적 변수의 값을 변경할 때 사용할 수 있다.

### RESET PERSIST


```bash
# 특정 변수 값 삭제
## max_connections mysqld-auto.cnf의 수정 사항 삭제
RESET PERSIST max_connections;
## max_connections 값이 존재한다면 mysqld-auto.cnf의 수정 사항 삭제
RESET PERSIST IF EXISTS max_connections;

# mysqld-auto.cnf의 모든 수정 사항 삭제
RESET PERSIST;
```

또 mysqld-auto.cnf에 변경사항을 지우다가 에러가 발생해 MySQL 서버를 실행하지 못하는 경우가 생길 수 있기 때문에 mysqld-auto.cnf 파일을 직접 고치는 것은 그렇게 좋은 방법이 아니라고 생각한다.
그렇기 때문에 만약 mysql-auto.cnf에 있는 변경사항을 수정하고 싶을 때는 RESET PERSIST 명령어를 사용하도록 하자.


# 번외

MySQL 도큐먼트에서 제공하는 대표 샘플 DB인 employees를 다운받고 설치해보자.

1. MySQL employees 다운받기
URL : https://github.com/datacharmer/test_db, 해당 URL로 들어가 파일을 다운로드를 받자.

2. 다운받은 파일의 압축을 해제한다.
3. 프롬프트 창 혹은 터미널에서 해당 파일위치로 이동한다.
4. mysql -u root -p <employees.sql
    ```bash
    ❯ mysql -u root -p <employees.sql                                                                         ─╯
    Enter password:
    INFO
    CREATING DATABASE STRUCTURE
    INFO
    storage engine: InnoDB
    INFO
    LOADING departments
    INFO
    LOADING employees
    INFO
    LOADING dept_emp
    INFO
    LOADING dept_manager
    INFO
    LOADING titles
    INFO
    LOADING salaries
    data_load_time_diff
    00:00:19 
    ```
    위와 같이나왔다면 성공!


