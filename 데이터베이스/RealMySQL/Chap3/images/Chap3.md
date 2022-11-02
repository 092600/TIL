
# 사용자 및 권한 관리

MySQL 의 사용자 계정은 단순히 사용자의 아이디뿐 아니라 사용자가 어느 IP에서 접속하고 있는지도 확인하며
MySQL 8.0버전부터는 권한을 묶어서 관리하는 역할의 개념도 도입됐기 때문에 각 사용자의 권한으로 미리 준비된 권한 세트를 부여하는 것도 가능하다.
이렇듯 MySQL에서 사용자 계정을 생성하는 방법이나 각 계정의 권한을 설정하는 방법은 다른 DBMS와는 차이가 있다.
이번에는 MySQL에서 계정의 식별 방식, 권한, 역할에 대해 알아보자.

## MySQL에서 사용자 식별

위에서도 살짝 말했듯이 MySQL 은 사용자의 계정뿐 아니라 사용자의 접속 지점(호스트와 IP 주소)도 계정의 일부가 된다.
그렇기 때문에 MySQL의 계정을 언급할 때는 항상 아이디와 호스트를 함께 명시해야 한다.

- MySQL 계정 언급 예시

```bash
# sim 계정은 127.0.0.1에서만 사용할 수 있음
'sim'@'127.0.0.1'
```

만약 모든 외부 컴퓨터에서 접속이 가능한 사용자 계정을 생성하고 싶다면 호스트 부분을 '%'로 대체하면 된다.

- MySQL의 계정의 호스트를 %로 사용한 예

```bash
# 모든 호스트에서의 사용가능한 계정인 sim
'sim'@'%'
```

이렇게 MySQL에서 계정은 호스트와 아이디 모두 설정을 해줘야한다. 만약 같은 아이디를 가진 계정이 두 개 이상 존재한다면 어떻게 될까 ?

- 같은 아이디를 가진 계정이 두 개 이상인 예
```bash
'sim'@'127.0.0.1'  # 1번 예
'sim'@'%'          # 2번 예
```

예를 들어 위와 같은 계정이 MySQL 서버에 존재할 때 sim 계정에 로그인을 시도하면 어떤 아이디에 로그인을 시도한다고 판단할까 ?
MySQL 서버는 항상 호스트의 범위가 가장 작은 것을 항상 먼저 선택한다. 당연하게도 1번이 2번보다 호스트 범위가 작기때문에 MySQL서버는 입력받은 정보로 1번 계정에 로그인하려고 할 것이다.
그리고 127.0.0.1과 정보가 일치하지 않는다면 2번 아이디와 일치하는지 확인하지 않고 로그인 실패처리를 할 것이다.
그렇기 때문에 사용자 계정을 생성할 때 같은 아이디로 여러 개의 계정을 생성하지 않도록 주의하자.

## 사용자 계정 관리

MySQL 8.0 버전부터 계정은 SYSTEM_USER 권한을 가지고 있느냐에 따라서 시스템 계정(System Account)과 일반 계정(Regular Account)으로 구분된다.
여기서 시스템 계정은 데이터베이스 서버 관리자를 위한 계정이고 일반 계정은 응용 프로그램이나 개발자를 위한 계정으로 생각하자.
시스템 계정은 시스템 계정과 일반 계정 관리(생성, 삭제, 변경)를 할 수 있지만 일반 계정은 시스템 계정을 관리할 수 없다. 또 아래와 같이 데이터베이스 서버 관리와 관련된 중요 작업은
시스템 계정으로만 수행할 수 있다.

1. 계정 관리 ( 계정 생성 및 삭제, 그리고 계정의 권한 부여 및 제거)
2. 다른 세션 ( Connection ) 또는 그 세션에서 실행 중인 쿼리를 강제 종료
3. 스토어드 프로그램 생성 시 DEFINER를 타 사용자로 설정

### MySQL 에 내장되어 있는 계정

MySQL에는 내장되어 있는 계정이 존재한다. 이 계정들은 각기 다른 목적으로 사용되는 계정으로 삭제하지 않도록 주의하자.

1. 'mysql.sys'@'localhost' 

MySQL 8.0부터 기본적으로 내장된 sys 스키마의 객체들의 DEFINER로 사용되는 계정

2. 'mysql.session'@'localhost'

MySQL 플러그인이 서버로 접근할 때 사용되는 계정

3. 'mysql.infoschema'@'localhost'

information_schema에 정의된 뷰의 DEFINER로 사용되는 계정

## 계정 생성

MySQL 5.7 버전까지는 GRANT 명령으로 권한 부여와 동시에 계정 생성이 가능했다.
하지만 MySQL 8.0 버전부터는 CREATE USER 명령어로 계정 생성을 하고, 권한 부여는 GRANT 명령으로 구분해 사용하도록 변경되었다.
그렇다면 CREATE USER 명령어로 계정을 생성하는 방법에 대해서 알아보자.

- 계정 생성 예시
```mysql
CREATE USER 'user'@'%'
    IDENTIFIED WITH 'mysql_native_password' BY 'PASSWORD'
    REQUIRE NONE
    PASSWORD EXPIRE INTERVAL 30 DAY
    ACCOUNT UNLOCK
    PASSWORD HISTORY DEFAULT
    PASSWORD REUSE INTERVAL DEFAULT
    PASSWORD REQUIRE CURRENT DEFAULT;  
```

계정 생성 예시보다 계정을 생성할 때 사용할 수 있는 옵션들이 많지만 위이 예시에서 사용한 옵션만 공부하고 넘어가자.

### IDENTIFIED WITH
이 옵션을 통해 사용자의 인증 방식과 비밀번호를 설정한다. IDENTIFIED WITH 뒤에는 반드시 인증 방식(인증 플러그인의 이름)을 명시해야하는데 MySQL의 기본 인증 방식을 사용하고자 한다면 IDENTIFIED BY 'PASSWORD' 형식으로 명시해야한다.
MySQL에서 플로그인 형태로 제공하는 인증 방식중 2가지만 알고가자.

1. Native Pluggable Authentication

이 방식은 MySQL 5.7 버전까지 사용되던 방식으로 비밀번호에 대한 해시값(SHA-1)을 저장해두고 클라이언트가 보낸 값과 해시값이 일치하는지 확인하는 인증 방식이다.

2. Caching SHA-2 Pluggable Authentication

이 방식은 MySQL 5.6 버전에 도입되고 MySQL 8.0 버전에서는 조금 더 보완된 인증 방식으로, 암호화 해시값 생성을 위해 SHA-2(256 비트) 알고리즘을 사용하며
암호화에 내부적으로 Salt 값을 사용해, 수천 번의 해시 계산을 수행해 결과를 만들어내기 때문에 동일한 키 값에도 다른 밸류값을 출력한다.
수천 번의 해시 계산 때문에 시간이 많이 소요된다는 점을 보완하기 위해서 MySQL 서버는 해시 결과값을 메모리에 캐시해서 사용하게 된다.
이 인증 방식을 사용하려면 SSL/TLS 또는 RSA 키페어를 반드시 사용해야 하는데, 이를 위해 클라이언트에서 접속할 때 SSL 옵션을 활성화해야한다.

MySQL 5.7버전까지는 Native Pluggable Authentication이 기본 인증 방식으로 사용됐지만 8.0부터 Caching SHA-2 Pluggable Authentication이 기본 방식으로 바뀌었다.
하지만 Caching SHA-2 Pluggable Authentication 방식은 SSL/TLS 또는 RSA 키페어를 반드시 사용해야하기때문에 기존의 MySQL 5.7버전까지의 연결 방식과는 다른 방식으로 접속해야 한다.
그래서 기존 버전과의 호환성을 위해 Caching SHA-2 Pluggable Authentication을 기본 인증 방식으로 계정을 생성하거나 Caching SHA-2 Pluggable Authentication 에서 Native Pluggable Authentication 로 기본 인증 방식을 변경해야 할 수 있다.
만약 기본 인증 방식을 변경하고 싶다면 아래의 코드를 입력하면 된다.
```bash
# 1. SET 
SET GLOBAL default_authentication_plugin="mysql_native_password";

# 2. SET PERSIST ONLY ( default_authentication_plugin : 정적 시스템 변수 )
SET PERSIST ONLY GLOBAL default_authentication_plugin="mysql_native_password";
```

### REQUIRE

MySQL 서버에 접속할 때 암호화된 SSL/TLS 채널을 사용할지 여부를 설정한다.
만약 별도로 설정하지 않는다면 비암호화 채널로 연결하게 된다.
하지만 REQUIRE 옵션을 설정하지 않았다고 하더라도 Caching SHA-2 Pluggable Authentication 인증 방식을 사용하면 암호화된 채널만으로 MySQL 서버에 접속할 수 있게된다.

### PASSWORD EXPIRE

비밀번호 유효 기간을 설정하는 옵션으로 별도로 설정하지 않으면 default_password_lifetime 시스템 변수에 저장된 기간으로 유효 기간이 설정된다.
설정가능한 옵션은 아래의 4가지이다.

1. PASSWORD EXPIRE : 계정 생성과 동시에 비밀번호의 만료 처리
2. PASSWORD EXPIRE NEVER : 계정 비밀번호의 만료 기간 없음
3. PASSWORD EXPIRE DEFAULT : default_password_lifetime 시스템 변수에 저장된 기간으로 비밀번호의 유효 기간을 설정
4. PASSWORD EXPIRE INTERVAL n DAY : 비밀번호의 유효기간을 오늘부터 n일자로 설정

아래는 default_password_lifetime의 값을 확인하는 방법이다.

```bash
SHOW VARIABLES LIKE 'default_password_lifetime';
```

### PASSWORD HISTORY

이 옵션은 한 번 사용했던 비밀번호를 재사용하지 못하게 설정할 수 있다. 설정가능한 옵션은 다음과 같다.

1. PASSWORD HISTORY DEFAULT 

password_history 시스템 변수에 저장된 개수만큼 비밀번호의 이력을 저장하며, 저장된 이력에 남아있는 비밀번호는 재사용할 수 없다.

2.  PASSWORD HISTORY n

비밀번호의 이력을 최근 n개까지만 저장하며, 저장된 이력에 남아있는 비밀번호를 재사용할 수 없다.

이전에 사용한 비밀번호들을 저장하기 위해서 MySQL 서버는 password_history 테이블을 사용한다.
```bash
# mysql 스키마 사용
use mysql;

# password_history 테이블 조회
select * from password_history;
```

### PASSWORD REUSE INTERVAL

한 번 사용했던 비밀번호의 재사용 금지 기간을 설정하는 옵션으로 별도로 명시하지 않으면 password_reuse_interval 시스템 변수에 저장된 기간으로 설정된다.
사용 가능한 옵션은 아래와 같다.

1. PASSWORD REUSE INTERVAL DEFAULT : password_reuse_interval 변수에 저장된 기간으로 설정
2. PASSWORD REUSE INTERVAL n DAY : n 일 이후에 비밀번호를 재사용할 수 있도록 설정

- password_reuse_interval 변수 값 확인하기

```bash
SHOW VARIABLES LIKE 'password_reuse_interval';
```

### PASSWORD REQUIRE

이 옵션은 비밀번호가 만료되어 새로운 비밀번호로 변경해야 하는 경우에 현재 비밀번호(변경 전 만료된 비밀번호)를 필요로 할지 말지를 결정하는 옵션으로, 별도로 명시되어 있지 않으면
password_require_current 시스템 변수의 값으로 설정된다. 설정 가능한 옵션은 아래와 같다.

1. PASSWORD REQUIRE CURRENT : 비밀번호를 변경할 때 현재 비밀번호를 먼저 입력하도록 설정
2. PASSWORD REQUIRE OPTIONAL : 비밀번호를 변경할 때 현재 비밀번호를 입력하지 않아도 되도록 설정
3. PASSWORD REQUIRE DEFAULT : password_require_current 시스템 변수의 값으로 설정

- password_require_current 변수 값 확인하기

```bash
SHOW VARIABLES LIKE 'password_require_current';
```

### ACCOUNTS LOCK / UNLOCK

이 옵션은 누군가 ALTER USER 명령을 통해 계정 정보를 변경할 때 계정을 사용하지 못하게 잠글지 여부를 결정한다.
사용가능한 옵션은 다음과 같다.

1. ACCOUNT LOCK : 계정을 사용하지 못하게 잠금
2. ACCOUNT UNLOCK : 잠긴 계정을 다시 사용 가능 상태로 잠금 해제


## 비밀번호 관리

### 고수준 비밀번호

MySQL 서버는 쉽게 유추할 수 있는 비밀번호를 사용하지 못하도록 조합을 강제하거나 금칙어를 정하는 기능도 있다.
MySQL 서버에서 비밀번호의 유효성 체크 규칙을 적용하려면 validate_password 컴포넌트를 이용하면 되는데, 우선 다음과 같이 validate_password 컴포넌트를 설치해야 한다.
이 validate_password 컴포넌트는 MySQL 서버에 내장되어있기때문에 INSTALL COMPONENT 명령의 file:// 부분에 별도의 파일 경로를 지정하지 않아도 된다.

- validate_password 컴포넌트 설치하기

```bash
INSTALL COMPONENT 'file://component_validate_password';
```

validate_password를 위와 같이 설치했다면 mysql 스키마의 component 테이블에 validate_password가 추가된 것을 확인할 수 있을 것이다.

- validate_password 컴포넌트 확인하기

```bash
SELETE * FROM component;
```

- 결과
```bash
+--------------+--------------------+------------------------------------+
| component_id | component_group_id | component_urn                      |
+--------------+--------------------+------------------------------------+
|            1 |                  1 | file://component_validate_password |
+--------------+--------------------+------------------------------------+
```

또 validate_password 컴포넌트가 설치되면 다음과 같이 컴포넌트에서 제공하는 시스템 변수를 확인할 수 있다.

- validate_password 컴포넌트에서 제공하는 시스템 변수 확인하기
```bash
SHOW VARIABLES LIKE 'validate_password%';
```

- 결과
```bash
+--------------------------------------+--------+
| Variable_name                        | Value  |
+--------------------------------------+--------+
| validate_password.check_user_name    | ON     |
| validate_password.dictionary_file    |        |
| validate_password.length             | 8      |
| validate_password.mixed_case_count   | 1      |
| validate_password.number_count       | 1      |
| validate_password.policy             | MEDIUM |
| validate_password.special_char_count | 1      |
+--------------------------------------+--------+
```

validate_password.policy에서 MySQL 서버에 현재 적용되어 있는 비밀번호 정책을 확인할 수 있다.
비밀번호 정책은 LOW, MEDIUM, STRONG가 있으며 각 정책의 특징은 다음과 같다.

1. LOW

시스템 변수인 validate_password의 length 이상의 비밀번호인지 검증한다.

2. MEDIUM : 비밀번호의 길이를 검증하며, 숫자와 대소문자, 그리고 특수문자의 배합을 검증

MEDIUM은 시스템 변수인 validate_password의 length 이상의 비밀번호인지, mixed_case_count 이상의 비밀번호인지, number_count 이상의 비밀번호인지, special_char_count 이상의 비밀번호인지 확인한다.

3. STRONG

STRONG은 MEDIUM 레벨의 검증을 모두 수행하며, 금칙어가 포함됐는지 여부까지 검증한다.

MySQL 서버에서는 기본적으로 비밀번호에 'qwert'나 '1234'와 같이 연속된 단어를 사용해도 아무런 에러없이 설정된다.
하지만 높은 수준의 보안을 요구하는 서비스에서는 비밀번호를 사전에 명시되지 않은 단어들로 생성하도록 제어해야할 수 있는데 이 때 validate_password의 dictionary_file 시스템 변수를 통해 금칙어들이 저장된 파일을 등록하여 금칙어들이 비밀번호에 포함되지 않도록 할 수 있다.
금칙어 파일은 금칙어들을 한 줄에 하나씩 기록해 텍스트 파일로 작성하면 된다. 하나하나 금칙어들을 입력하는 것이 힘들다면 금칙어 파일을 다운받아 사용하도록 하자.

이렇게 validate_password 컴포넌트를 사용해서 비밀번호의 보안 수준을 높일 수 있다. 
MySQL 5.7 버전까지는 validate_password가 플러그인 형태로 제공됬지만 8.0버전부터 컴포넌트 형태로 제공되고 있다.

#### validate_password 플로그인과 컴포넌트 차이

- MySQL 5.7버전

```bash
SELECT * FROM mysql.plugin WHERE name='validate_password';
+-------------------+--------------------+
| component_id      | dl                 |
+-------------------+--------------------+
| validate_password |validate_password.so|
+-------------------+--------------------+

SELECT * FROM mysql.component;
Empty set (0.01 sec)

SHOW GLOBAL VARIABELS LIKE 'validate_password%';
+--------------------------------------+--------+
| Variable_name                        | Value  |
+--------------------------------------+--------+
| validate_password.check_user_name    | ON     |
| validate_password.dictionary_file    |        |
| validate_password.length             | 8      |
| validate_password.mixed_case_count   | 1      |
| validate_password.number_count       | 1      |
| validate_password.policy             | MEDIUM |
| validate_password.special_char_count | 1      |
+--------------------------------------+--------+
```



```bash
SELECT * FROM mysql.plugin WHERE name='validate_password';
Empty set (0.01 sec)

SELECT * FROM mysql.component;
+--------------+--------------------+------------------------------------+
| component_id | component_group_id | component_urn                      |
+--------------+--------------------+------------------------------------+
|            1 |                  1 | file://component_validate_password |
+--------------+--------------------+------------------------------------+

SHOW GLOBAL VARIABELS LIKE 'validate_password%';
+--------------------------------------+--------+
| Variable_name                        | Value  |
+--------------------------------------+--------+
| validate_password.check_user_name    | ON     |
| validate_password.dictionary_file    |        |
| validate_password.length             | 8      |
| validate_password.mixed_case_count   | 1      |
| validate_password.number_count       | 1      |
| validate_password.policy             | MEDIUM |
| validate_password.special_char_count | 1      |
+--------------------------------------+--------+
```

위처럼 validate_password가 플러그인과 컴포넌트일 때 차이가 존재하지만 모두 거의 동일한 기능을 제공한다.
하지만 플러그인의 단점을 보완하기 위해서 컴포넌트로 제공되고 있기 때문에 가능하다면 컴포넌트를 사용하는 편이 좋을 것 같다.


## 권한
> MYSQL PRIVILGES DOCUMENT URL : https://dev.mysql.com/doc/refman/8.0/en/privileges-provided.html

MySQL 5.7 버전에서는 글로벌(Global)권한과 객체 단위의 권한으로 구분되었는데 여기서 글로벌 권한이란 데이터베이스나 테이블 이외의 객체에 적용되는 권한을 말하고
 객체 권한이란 데이터베이스나 테이블을 제어하는데 필요한 권한을 말한다.
객체 권한은 GRANT 명령으로 권한을 부여할 때 반드시 특정 객체를 명시해야 하며, 글로벌 권한은 GRANT 명령에서 특정 객체를 명시하지 말아야한다.
예외적으로 ALL은 글로벌과 객체 권한 두 가지 용도로 사용될 수 있는데 글로벌에서의 ALL은 글로벌 수준에서 모든 것이 가능한 권한을 받는다. 
또 객체 권한으로 ALL 이 사용되면 특정 객체에 적용될 수 있는 모든 권한을 받는다.

MySQL 8.0 버전부터는 MySQL 5.7 버전의 권한에 동적 권한이라는 것이 추가되었다. 그리고 MySQL 5.7 버전에서 제공하던 권한을 정적 권한이라고 한다.
동적 권한은 MySQL 서버가 시작되면서 동적으로 생성하는 권한을 말하고 정적 권한은 MySQL 소스코드에 고정적으로 명시돼어 있는 권한을 의미한다.

> 동적 권한 관련 MySQL 도큐먼트 URI : https://dev.mysql.com/doc/mysql-security-excerpt/8.0/en/privileges-provided.html#privileges-provided-dynamic
> 정적 권한 관련 MySQL 도큐먼트 URI : https://dev.mysql.com/doc/mysql-security-excerpt/8.0/en/privileges-provided.html#privileges-provided-static 

MySQL 5.7 버전에서는 SUPER라는 권한이 데이터베이스 관리에 꼭 필요한 권한이었지만 8.0 버전부터는 SUPER 라는 권한이 잘게 쪼개져 동적 권한으로 분산되었다.
그래서 8.0 버전부터 계정에 꼭 필요한 권한만 부여할 수 있게 된 것이다.

### 사용자에게 권한 부여하기


#### 글로벌 권한 부여하기

```bash
# GRANT 사용 형식
GRANT SUPER ON *.* TO 'user'@'host';
```

글로벌 권한에는 객체 권한 부여할때와 같이 데이터베이스나 테이블을 부여할 수 없기때문에 GRANT 명령어의 ON 절에 항상 *.*를 추가한다. 
여기에서 *.*는 모든 데이터베이스의 오브젝트를 포함해 서버 전체를 의미한다.


#### 객체 권한 부여하기
사용자에게 권한을 부여할 때는 GRANT 명령을 사용한다. 이 GRANT 명령은 아래와 같이 사용할 수 있다.

- GRANT 사용 예

```bash
# GRANT 사용 형식
GRANT privilege_list ON db.table TO 'user'@'host';

# GRANT 실제 사용 예
GRANT DELETE ON db.user TO 'sim'@'localhost';
GRANT DELETE ON db.* TO 'sim'@'localhost';
GRANT SELECT, UPDATE ON db.* TO 'sim'@'localhost';
GRANT SELETE ON *.* TO 'sim'@'localhost';
```

MySQL 8.0 버전부터는 만약 존재하지 않는 사용자에게 GRANT 명령을 사용할 경우에 에러가 발생하므로 반드시 사용자가 생성되어있는지 확인하고, 사용자가 없다면 생성한 후 사용하도록 하자.
GRANT 사용 형식 예에서  privilege_list에는 구분자(,)를 사용해 권한 여러 개를 동시에 명시할 수 있다. 물론 한 개의 권한만을 명시할 수도 있다.

만약 특정 컬럼에 대해서 권한을 주고싶다면 위에서의 GRANT 명령과 조금 달라져야 한다.

- GRANT로 컬럼에 권한 부여하기

```bash
GRANT SELECT, UPDATE(dept_name) ON nunukang.* TO 'sim'@'localhost';
```

하지만 이렇게 테이블이나 컬럼 단위의 권한은 잘 사용하지 않는데 그 이유는 컬럼 단위의 권한이 하나라도 설정되면 나머지 모든 테이블의 모든 칼럼에 대해서도 권한 체크를 하기 때문에
칼럼 하나에 대해서만 권한을 설정하더라도 전체적인 성능에 영향을 미칠 수 있기 때문이다.

### 권한 확인하기

각 사용자의 권한을 확인하고 싶다면 SHOW GRANTS 명령어를 사용하거나 mysql의 DB관련 테이블을 확인하면 된다.

- 사용자의 권한 확인하기

```bash
# 1. SHOW GRANTS로 사용자의 권한 확인하기
SHOW GRANTS

# 2. mysql의 DB관련 테이블에서 사용자 권한 확인하기
mysql> select user, host, Select_priv, Insert_priv from user;;
+------------------+-----------+-------------+-------------+
| user             | host      | Select_priv | Insert_priv |
+------------------+-----------+-------------+-------------+
| mysql.infoschema | localhost | Y           | N           |
| mysql.session    | localhost | N           | N           |
| mysql.sys        | localhost | N           | N           |
| root             | localhost | Y           | Y           |
| sim              | localhost | N           | N           |
+------------------+-----------+-------------+-------------+
5 rows in set (0.00 sec)

mysql> select user, host, db, Select_priv from db;
+---------------+-----------+--------------------+-------------+
| user          | host      | db                 | Select_priv |
+---------------+-----------+--------------------+-------------+
| mysql.session | localhost | performance_schema | Y           |
| mysql.sys     | localhost | sys                | N           |
| sim           | localhost | nunukang           | Y           |
+---------------+-----------+--------------------+-------------+
3 rows in set (0.00 sec)
```

mysql의 권한 관련 테이블은 필요할 때 찾아서 확인하도록 하자.

## 역할

MySQL 8.0 버전부터 권한을 묶어서 역할(Role)을 사용할 수 있게 됐다.
역할은 계정과 같은 모습을 하고 있는데 역할과 관련된 예시를 보면서 이해해보자.

- 역할 생성 예

```bash
CREATE ROLE
    role_test_read,
    role_test_write;
```

역할을 생성하기 위해서 CREATE ROLE 명령어를 사용할 수 있다. 만약 여러 개의 역할을 생성하고 싶다면 구분자(,)를 사용하면된다.
위의 CREATE ROLE 명령을 통해 생성한 역할은 아직 아무런 권한도 없는 빈 껍데기의 역할이다.
이제 role_test_read 역할에는 모든 객체에 대한 읽기 권한을, role_test_write 역할에는 모든 객체에 대한 읽기, 쓰기, 변경 권한을 부여하도록 하자.

- 역할에 권한 부여하기
```bash
GRANT SELECT ON test.* TO role_test_read;
GRANT INSERT, UPDATE, DELETE ON test.* TO role_test_write;
```

역할은 기본적으로 계정에 부여되어 사용되어야 한다. 이제 계정에 역할을 부여해보자.

- 계정에 역할 부여하기

```bash
# 계정 생성하기
CREATE USER 'reader'@'localhost' IDENTIFIED BY 'Testtest1!';
CREATE USER 'writer'@'localhost' IDENTIFIED BY 'Testtest1!';

# 계정에 권한 부여하기
GRANT role_test_read TO 'reader'@'localhost';
GRANT role_test_read, role_test_write TO 'writer'@'localhost';
```

만약 다른 계정으로 로그인되어있다면 SHOW GRANTS 명령어를 입력했을 때 그 계정의 권한이 출력된다. 그렇기 때문에 권한을 확인하고 싶은 계정으로 다시 MySQL 서버에 접속하자.

### reader 계정 권한 확인하기

```bash
# 권한을 확인할 계정으로 재접속
mysql -u reader -p;
Enter password : Testtest1!

# 권한 확인하기
mysql> SHOW GRANTS;
+--------------------------------------------+
| Grants for reader@localhost                |
+--------------------------------------------+
| GRANT USAGE ON *.* TO `reader`@`localhost` |
+--------------------------------------------+
1 row in set (0.00 sec)
```

### writer 계정 권한 확인하기

```bash
# 권한을 확인할 계정으로 재접속
mysql -u writer -p;
Enter password : Testtest1!

# 권한 확인하기
mysql> SHOW GRANTS;
+--------------------------------------------------------------------------+
| Grants for writer@localhost                                              |
+--------------------------------------------------------------------------+
| GRANT USAGE ON *.* TO `writer`@`localhost`                               |
| GRANT `role_test_read`@`%`,`role_test_write`@`%` TO `writer`@`localhost` |
+--------------------------------------------------------------------------+
2 rows in set (0.00 sec)
```

하지만 이 상태에서 test 데이터베이스에 작업을 요청하면 권한이 없다는 에러를 확인할 수 있다.

- 권한 없음 예
```bash
mysql> use test;
ERROR 1044 (42000): Access denied for user 'writer'@'localhost' to database 'test'
```

역할을 부여했음에도 권한이 없다고 나오는 이유는 아직 계정에 역할이 진짜 없기 때문이다.

- 역할 확인

```bash
mysql> SELECT current_role();
+----------------+
| current_role() |
+----------------+
| NONE           |
+----------------+
1 row in set (0.00 sec)
```

계정이 부여받은 역할을 사용할 수 있도록 하려면 SET ROLE 명령어를 통해 역할을 활성화해줘야 한다.
역할을 활성화하면 그 역할이 가진 권한을 사용할 수 있게된다. 하지만 로그아웃을 한 이후에 접속한다면 계정에 역할을 다시 활성화해줘야한다.

- 역할 활성화 예
```bash
# 활성화
mysql> SET ROLE 'role_test_write';
Query OK, 0 rows affected (0.00 sec)

# 역활 확인하기
mysql> SELECT current_role();
+-----------------------+
| current_role()        |
+-----------------------+
| `role_test_write`@`%` |
+-----------------------+
1 row in set (0.00 sec)
```

이렇게 해줘야 하는 이유는 MySQL 서버가 실행될 때 자동으로 역할을 활성화하도록 설정해주지 않았기 때문이다.
MySQL 서버가 실행될 때 자동으로 역할을 활성화해주기 위해서는 activate_all_roles_on_login 시스템 변수의 값을 변경해주면 된다.

- activate_all_roles_on_login 값 확인하기

```bash
mysql> SHOW VARIABLES LIKE 'activate_all_roles_on_login';
+-----------------------------+-------+
| Variable_name               | Value |
+-----------------------------+-------+
| activate_all_roles_on_login | OFF   |
+-----------------------------+-------+
1 row in set (0.01 sec)

# writer 에게는 해당 시스템 변수의 값을 변경할 수 있는 권한이 없어
# root 계정으로 로그인 후 변수의 값을 변경해줘야 함
mysql> SET GLOBAL activate_all_roles_on_login=ON;
Query OK, 0 rows affected (0.00 sec)

mysql> SHOW VARIABLES LIKE 'activate_all_roles_on_login';
+-----------------------------+-------+
| Variable_name               | Value |
+-----------------------------+-------+
| activate_all_roles_on_login | ON    |
+-----------------------------+-------+
1 row in set (0.01 sec)
```

그럼 이제 간단하게 역할에 대해서 알아봤다. 역할에 대한 내용을 시작하면서 역할과 계정이 같은 모습을 하고 있다고 말했다.
**실제로 MySQL 서버에서 역할과 계정을 동일한 객체로 취급한다. 단지 하나의 사용자 계정에서 다른 사용자 계정이 가진 권한을 병합해서 권한제어가 가능해졌을 뿐이다.**
그렇다면 왜 CREATE USER와 CREATE ROLE 명령어로 나뉘어있는 것일까? 그 이유는 보안 강화하기 위함인데 CREATE ROLE로 생성한 역할은 accounts_locked 값이 Y로 설정되어 있어서 로그인 용도로 사용할 수 없게된다.

- 역할과 계정 확인하기 예

```bash
mysql> select user, host, account_locked from mysql.user;
+------------------+-----------+----------------+
| user             | host      | account_locked |
+------------------+-----------+----------------+
| role_test_read   | %         | Y              |
| role_test_write  | %         | Y              |
| mysql.infoschema | localhost | Y              |
| mysql.session    | localhost | Y              |
| mysql.sys        | localhost | Y              |
| reader           | localhost | N              |
| root             | localhost | N              |
| sim              | localhost | N              |
| writer           | localhost | N              |
+------------------+-----------+----------------+
9 rows in set (0.01 sec)
```

위의 예제를 보면 우리가 생성한 역할들이 user 테이블에 역할이라는 것을 표기한 컬럼도 없이 계정과 같이 정보가 들어가 있다.
사실 MySQL은 우리가 생성한 역할을 계정으로 저장해 계정들을 병합해 권한을 부여하므로 역할과 계정을 구분할 필요가 없다.
또 계정을 생성할때와 마찬가지로 역할에도 호스트 값을 설정해줄 수 있으며 설정하지 않았을 경우 모든 호스트(%)가 자동으로 추가된다.















