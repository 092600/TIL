# 데이터 압축

디스크의 데이터 파일이 크면 클수록 쿼리를 처리하기 위해서 더 많은 데이터 페이지를 InnoDB 버퍼 풀로 읽어야 할 수 있고 새로운 페이지가 버퍼 풀로 적재되기
때문에 그만큼 더티 페이지가 더 자주 디스크로 기록되어야 한다. 또 데이터의 크기가 크면 백업 시간 및 복구하는데 걸리는 시간이 그만큼 많이 걸린다.

이렇듯 일반적으로 데이터의 크기는 읽기 성능과 백업 및 복구 시간등에 많은 영향을 미치는 요소이기때문에 많은 DBMS가 이러한 문제를 해결하기 위해서 
데이터를 압축하는 기능을 제공한다.

## 페이지 압축

페이지 압축이란 "Transparent Page Compression"이라고도 불린다. MySQL 서버가 디스크에 저장하는 시점에 데이터 페이지가 압축되어 저장되고,
반대로 MySQL 서버가 디스크에서 데이터 페이지를 읽어올 때 압축이 해제되기 때문이다. 그래서 버퍼 풀에서는 데이터 페이지가 한 번 적재되면 InnoDB 스토리지
엔진이 압축이 해제된 상태로만 데이터 페이지를 관리하고 그렇기 때문에 MySQL 서버의 내부 코드에서는 압축 여부와 상관없이 "투명(Transparent)"하게 작동한다.
하지만 하나의 테이블은 한 크기의 데이터 페이지로 통일되어야만 하는데 데이터 페이지를 압축한 용량이 얼마나 될지 예측이 불가능하다는 점때문에 데이터 페이지 압축방식에 문제가 발생한다.

그렇기 때문에 페이지 압축 기능은 운영체제별로 특정 버전의 파일 시스템에서만 지원되는 펀치 홀(Punch-hole)이라는 기능을 사용한다. 
하지만 이 펀치 홀기능은 운영체제뿐만 아니라 하드웨어까지 해당 기능을 지원해야 사용이 가능하고 데이터를 백업하고 복구하는 과정에서 발생하는 데이터 복사 과정 등 여러 과정에서
파일 시스템 관련 명령어(유틸리티)를 사용해야 하는데 아직 펀치 홀을 지원하는 파일 시스템 관련 명령어가 존재하지 않고 있다.
페이지 압축방식은 이러한 여러 문제들로 인해 많이 사용되지 않는 상태이다.

## 테이블 압축

테이블 압축은 페이지 압축과는 달리 운영체제나 하드웨어에 대한 제약없이 사용할 수 있기 때문에 좀 더 활용도가 높은 방식이다. 
하지만 테이블 압축 방식도 몇 가지 단점이 존재하고 테이블 압축 방식이 지닌 단점은 아래와 같다.

- 테이블 압축 방식의 단점
1. 버퍼 풀 공간 활용률이 낮음
2. 쿼리 처리 성능이 낮음
3. 빈번한 데이터 변경 시 압축률이 떨어짐

이러한 단점이 발생하는 이유를 이해하기 위해서 어떻게 압축이 실행되어 디스크에 저장되는지, 압축된 데이터 페이지들이 버퍼 풀에 어떻게
적재되어 사용되는지 이해해보자.

### 압축 테이블 생성

테이블을 압축하기 위해서는 우선 압축하려는 테이블이 별도의 테이블 스페이스를 사용해야 한다.
하지만 이를 위해서는 innodb_file_per_table 시스템 변수가 ON 으로 설정되어있는 상태에서 테이블이 생성되어야 하고 ROW_FORMAT=COMPRESSED 옵션을
명시해야 하며 KEY_BLOCK_SIZE 옵션을 이용해 압축된 페이지의 타깃크기를 명시해야 하는데 이 값은 2n(n>=2)로만 설정할 수 있다.

만약 InnoDB 스토리지 엔진의 페이지 크기(innodb_page_size)가 16KB라면 KEY_BLOCK_SIZE는 4KB 또는 8KB만 설정할 수 있다. 그리고 페이지 크기(innodb_page_size)가 32KB 또는 64KB
인 경우에는 테이블 압축을 적용할 수 없다.

- 압축 테이블 생성하기 예
```mysql
mysql> SET GLOBAL innodb_file_per_table=ON;

## 1. ROW_FORMAT, KEY_BLOCK_SIZE 명시
mysql> CREATE TABLE compressed_table (
    c1 INT PRIMARY KEY 
   )
   ROW_FORMAT=COMPRESSED 
    KEY_BLOCK_SIZE=8;
## 2. KEY_BLOCK_SIZE 명시
mysql> CREATE TABLE compressed_table (
        c1 INT PRIMARY KEY
   )
   KEY_BLOCK_SIZE=8;
```

만약 2번과 같이 ROW_FORMAT가 생략되었다면 ROW_FORMAT=COMPRESSED 옵션이 추가되어 생성된다.
그리고 KEY_BLOCK_SIZE의 값은 압축된 페이지가 저장될 페이지의 크기를 지정하는데 KB 단위로 설정한다.

그렇다면 지금까지의 설정으로 데이터 페이지 크기가 16KB, KEY_BLOCK_SIZE가 8로 설정하였는데
아직 데이터 페이지를 압축하지 않았음에도 불구하고 KEY_BLOCK_SIZE를 테이블이 생성되기 전에 설정하는 이유가 무엇일까 ?
그 이유를 알기 위해서는 InnoDB 스토리지 엔진이 압축을 적용하는 방법에 대해서 알아보자.

- InnoDB 스토리지 엔진이 압축을 적용하는 방법
1. 16KB의 데이터 페이지를 압축
   1. 압축된 결과가 8KB 이하이면 그대로 디스크에 저장(압축 완료)
   2. 압축된 결과가 8KB를 초과하면 원본 페이지를 스플릿해서 2개의 페이지에 8KB씩 저장
2. 나뉜 페이지 각각에 대해 "1"번 단계를 반복 실행

위의 단계를 읽어본 후 아래의 이미지를 보도록 하자.
위의 이미지에서 KEY_BLOCK_SIZE 값은 8이며 테이블 압축에 InnoDB I/O 레이어는 아무런 역할도 하지 않았다.

 ![[Obsidians_Multi_Uses/책_및_강의_정리/RealMySQL/이미지/Chap6. 2.png]]

### KEY_BLOCK_SIZE 결정

테이블 압축에서 가장 중요한 것은 압축된 결과가 어느 정도가 될지 예측해 KEY_BLOCK_SIZE 값을 결정하는 것이다.
하지만 알맞은 KEY_BLOCK_SIZE 값은 간단하게 예측하는 할 수는 없기 때문에 샘플 데이터를 통해 테스트해보는 것이 좋다.
샘플 데이터의 양은 많을 수록 정확한 테스트가 가능하며 최소한 데이터 페이지가 10개 정도는 생성될 정도의 데이터 양을 테스트
테이블에 INSERT 하는 것이 좋다.

- 적절한 KEY_BLOCK_SIZE 값을 위한 테스트 1 (KEY_BLOCK_SIZE : 4KB)
```mysql
mysql> USE employees;
mysql> CREATE TABLE employees_comp4k (
    emp_no int NOT NULL,
    birth_date date NOT NULL,
    first_name varchar(14) NOT NULL,
    last_name varchar(16) NOT NULL,
    gender enum('M', 'F') NOT NULL,
    hire_date date NOT NULL,
    PRIMARY KEY (emp_no),
    KEY ix_firstname (first_name),
    KEY ix_hiredate (hire_date)
) ROW_FORMAT COMPRESSED KEY_BLOCK_SIZE=4;

mysql> SET GLOBAL innodb_cmp_per_index_enabled=ON;
mysql> INSERT INTO employees_comp4k SELECT * FROM employees;

mysql> SELECT
    table_name, index_name, compress_ops, compress_ops_ok,
    (compress_ops-compress_ops_ok)/compress_ops * 100 as compression_failure_pct
    FROM information_schema.INNODB_CMP_PER_INDEX;

+------------------+--------------+--------------+-----------------+-------------------------+
| table_name       | index_name   | compress_ops | compress_ops_ok | compression_failure_pct |
+------------------+--------------+--------------+-----------------+-------------------------+
| employees_comp4k | PRIMARY      |        18636 |           13479 |                 27.6722 |
| employees_comp4k | ix_firstname |         8321 |            7654 |                  8.0159 |
| employees_comp4k | ix_hiredate  |         7767 |            6722 |                 13.4544 |
+------------------+--------------+--------------+-----------------+-------------------------+
3 rows in set (0.00 sec)
```

위의 테스트 과정에서 information_schema.INNODB_CMP_PER_INDEX의 compress_ops, compress_ops_ok, compression_failure_pct 컬럼 값을 가져왔는데
각각의 의미는 압축 횟수, 압축 성공 횟수, 업축 실패 확률이다. PRIMARY KEY가 아닌 나머지 인덱스 2개도 압축 실패율이 각각 8 퍼센트, 13퍼센트 정도로 높게 나온 것을 확인할
수 있는데 일반적으로 압축 실패율은 3~5%미만으로 유지할 수 있게 KEY_BLOCK_SIZE를 선택하는 것이 좋다.


- 적절한 KEY_BLOCK_SIZE 값을 위한 테스트 2 (KEY_BLOCK_SIZE : 8KB)
```mysql
mysql> USE employees;
mysql> CREATE TABLE employees_comp8k (
    emp_no int NOT NULL,
    birth_date date NOT NULL,
    first_name varchar(14) NOT NULL,
    last_name varchar(16) NOT NULL,
    gender enum('M', 'F') NOT NULL,
    hire_date date NOT NULL,
    PRIMARY KEY (emp_no),
    KEY ix_firstname (first_name),
    KEY ix_hiredate (hire_date)
) ROW_FORMAT COMPRESSED KEY_BLOCK_SIZE=8;

mysql> SET GLOBAL innodb_cmp_per_index_enabled=ON;
mysql> INSERT INTO employees_comp8k SELECT * FROM employees;

mysql> DELETE FROM information_schema.INNODB_CMP_PER_INDEX; 
mysql> SELECT
    table_name, index_name, compress_ops, compress_ops_ok,
    (compress_ops-compress_ops_ok)/compress_ops * 100 as compression_failure_pct
    FROM information_schema.INNODB_CMP_PER_INDEX;

+------------------+--------------+--------------+-----------------+-------------------------+
| table_name       | index_name   | compress_ops | compress_ops_ok | compression_failure_pct |
+------------------+--------------+--------------+-----------------+-------------------------+
| employees_comp8k | PRIMARY      |         8092 |            6593 |                 18.5245 |
| employees_comp8k | ix_firstname |         1996 |            1996 |                  0.0000 |
| employees_comp8k | ix_hiredate  |         1391 |            1381 |                  0.7189 |
+------------------+--------------+--------------+-----------------+-------------------------+
3 rows in set (0.00 sec)
```

KEY_BLOCK_SIZE 값을 8KB로 설정했음에도 불구하고 PRIMARY 키의 압축 실패율이 18 퍼센트 정도로 꽤 높게 나타난 것을 확인할 수 있다.
위에서의 2번의 테스트 결과로 판단하자면 압축을 했을때 압축 실패율이 꽤 높아서 InnoDB 버퍼 풀에서 디스크로 기록되기 전에 압축하는 과정에 꽤 오랜
시간이 걸릴 것이라고 예측할 수 있다. 그렇기 때문에 성능에 민감한 서비스라면 employees 테이블은 압축을 적용하지 않는 것이 좋다고 판단할 수 있다.

```bash
cd /opt/homebrew/var/mysql/employees
ls -alh employees*.ibd
-rw-r-----  1 sim  admin    22M 11 17 23:44 employees.ibd
-rw-r-----  1 sim  admin    20M 11 18 00:01 employees_comp4k.ibd
-rw-r-----  1 sim  admin    21M 11 18 00:07 employees_comp8k.ibd
```
위는 실제 데이터 디렉터리에 생성된 각 테이블의 크기다. 여기서 압축되지 않는 employees 테이블의 크기는 22MB이지만 4KB 압축을 적용한 
데이터 파일의 용량은 20MB, 8KB로 압축을 적용한 데이터 파일의 용량은 21MB로 별 차이가 나지 않는데 아마도 나도 모르는 사이에 employees 테이블이 압축되었을 수 있겠다라는 생각이 들었다.
RealMySQL 책에서는 압축을 진행하지 않은 employees.ibd의 용량은 30MB로 꽤 차이를 보이는데 다음에 해당 내용을 확인 후 내용을 추가하도록 하겠다.
어찌되었든 테스트 후 생각해보자면 4KB와 8KB와 압축 후 크기가 많이 차이나지 않기때문에 압축 효율이 더 높은 8KB를 사용하는 것이 더 좋아보인다.

위의 테스트 후 착각할 수 있는 내용이 있는데 압축 실패율이 높을 때는 압축을 사용하지 않아야 하는 것이 아니다.
만약 그 데이터를 자주 조회하지 않는다면 압축하는 것이 손해는 아닐 것이다. 또 데이터의 조회와 변경이 매우 빈번하다면 압축은 고려하지 않는 것이 좋다.
테이블 압축은 zlib를 이용해 압축을 실행하는데, 예상외로 압축 알고리즘은 많은 CPU 자원을 소모한다는 것을 기억하자.

### 압축된 페이지의 버퍼 풀 적재 및 사용

InnoDB 스토리지 엔진은 압축된 테이블의 데이터 페이지를 버퍼 풀에 적재하면 압축된 상태와 압축이 해제된 상태 2개 버전을 관리한다.
그래서 디스크를 읽은 상태 그대로의 데이터 페이지 목록을 관리하는 LRU 리스트와 압축된 페이지들의 압축 해제 버전인 Unzip_LRU 리스트를 별도로 관리한다.
결국은 같은 데이터 페이지의 압축된 버전, 압축 해제된 버전을 LRU 리스트로 가지게되어 버퍼 풀의 공간을 이중으로 사용함으로써
메모리를 낭비하는 효과를 가진다. 또 압축된 페이지에서 데이터를 읽거나 변경하기 위해서 압축을 해제해야하는데 이 과정에서 CPU를 상대적으로 많이 소모하게 된다는
문제도 가진다.

이 문제를 보완하기 위해서 아래와 같은 처리 작업을 수행한다.
1. InnoDB 버퍼 풀의 공간이 필요한 경우에는 LRU 리스트에서 원본 데이터 페이지(압축된 형태)는 유지하고, Unzip_LRU 리스트에서
압축 해제된 버전은 제거해서 버퍼 풀의 공간을 확보한다.
2. 압축된 데이터 페이지가 자주 사용되는 경우에는 Unzip_LRU 리스트에 압축 해제된 페이지를 계속 유지하면서 압축 및 압축 해제 작업을 최소화한다.
3. 압축된 데이터 페이지가 사용되지 않아서 LRU 리스트에서 제거되는 경우에는 Unzip_LRU 리스트에서도 함께 제거된다.

InnoDB 스토리지 엔진은 버퍼 풀에서 압축 해제된 버전의 데이터 페이지를 적적한 수준으로 유지하기 위해 다음과 같은 어댑티브 알고리즘을 사용한다.
- 어댑티브 알고리즘
> CPU 사용량이 높은 서버에서는 가능하면 압축과 압축 해제를 피하기 위해 Unzip_LRU 비율을 높여서 유지하고
> Disk IO 사용량이 높은 서버에서는 가능하면 Unzip_LRU 리스트 비율을 낮춰서 InnoDB 버퍼 풀의 공간을 더 확보하도록 작동한다.







