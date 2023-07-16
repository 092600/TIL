# 1. SQL 문법

## 1-1. 사전 작업

SQL 문법에 대해 공부하기에 앞서 사용할 데이터를 [Downloda MySQL Sample Database](https://www.mysqltutorial.org/mysql-sample-database.aspx)에서 다운로드 받자. 해당 사이트에서 아래의mysqlsampledatabase.sql 파일을 다운 받을 수 있고 Dbeaver나 MySQLWorkbench 혹은 터미널에서 해당 파일의 SQL문을 실행하여 'classicmodels' 스키마(Schema)를 생성하고 해당 스키마에 테이블 및 데이터를 생성할 수 있다.

- 샘플데이터 설치
	![[Obsidians_Multi_Uses/책_및_강의_정리/SQL로_맛보는_데이터_전처리_분석/이미지/Chap2_샘플데이터_설치.png]]

- DataBase 생성
	```SHELL
	mysql> create database classicmodels
	```

- .sql 파일 import 하기
	```SHELL
	mysql -u [계정] -p classicmodels < mysqlsampledatabase.sql
	
	// 입력 후 패스워드 입력
	Enter password:
	```

위와 같이 코드를 입력해 다운받은 mysqlsampledatabase.sql 파일로 classicmodels 스키마를 만들고 해당 스키마 안에 데이터를 넣었다면 SQL 문법을 공부해보기 전 준비는 마친 것이다.

## 1-2. SQL 문법

### 1-2-1. SELECT

SELECT는 데이터를 조회할 때 사용하는 구문으로 대부분의 경우 아래와 같이 사용한다.

- SELECT 사용 예시
	```mysql
	SELECT 조회 칼럼 FROM DB.테이블; 
	SELECT 조회 칼럼 FROM 테이블;
	SELECT * (모든 칼럼) FROM 테이블;
	```

그러면 이제 실제로 SELECT 문을 사용하여 데이터베이스에서 데이터를 조회해보자.

#### 1-2-1-1. SELECT 사용 예시

| 사용 SQL문                                          | SQL문 실행 화면  |
| --------------------------------------------------- | ---------------- |
| SELECT CUSTOMERNUMBER FROM CLASSICMODELS.CUSTOMERS; | ![[Obsidians_Multi_Uses/책_및_강의_정리/SQL로_맛보는_데이터_전처리_분석/이미지/Chap2_SELECT1.gif]] |
| SELECT * FROM CLASSICMODELS.PRODUCT; | ![[Obsidians_Multi_Uses/책_및_강의_정리/SQL로_맛보는_데이터_전처리_분석/이미지/Chap2_SELECT2.gif]]|

두 번째 예시에서와 같이 * 를 사용하여 테이블의 모든 칼럼을 조회할 수 있다.

#### 1-2-1-2. SELECT 문과 함께 함수 사용하기 "COUNT, SUM, AVG"

지금 사용하는 대표적인 RDM MySQL에서는 기본적으로 제공하는 여러 함수들이 존재한다. 이 중에서 개수를 세주는 COUNT, 합을 계산해주는 SUM 함수, 평균을 계산해주는 AVG 함수 등이 존재한다.

- SELECT 문 함수 사용 예시

| 사용 SQL문                                          | SQL문 실행 화면  |
| --------------------------------------------------- | ---------------- |
| SELECT SUM(AMOUNT), COUNT(CHECKNUMBER) FROM CLASSICMODELS.PAYMENTS; | ![[Obsidians_Multi_Uses/책_및_강의_정리/SQL로_맛보는_데이터_전처리_분석/이미지/Chap2_SELECT3.gif]] |
|

#### 1-2-1-3. 칼럼 명 변경하기 "AS"

위에서 "SELECT SUM(AMOUNT), COUNT(CHECKNUMBER) FROM CLASSICMODELS.PAYMENTS;" 예시에서 조회 결과를 확인해보면 SUM(AMOUNT)와 같이 조회하면 SQL 문의 실행 결과를 한눈에 파악하기 어려울 수 있다. 이를 위해 MySQL에서는 "AS" 를 사용하여 칼럼 명을 변경하여 데이터를 조회하는 것이 가능하다.

| 사용 SQL문                                                                                                | SQL문 실행 화면  |
| --------------------------------------------------------------------------------------------------------- | ---------------- |
| SELECT SUM(AMOUNT) AS "AMOUNT 합", COUNT(CHECKNUMBER) AS "CHECKNUMBER 개수" FROM CLASSICMODELS.PAYMENTS; | ![[Obsidians_Multi_Uses/책_및_강의_정리/SQL로_맛보는_데이터_전처리_분석/이미지/Chap2_SELECT4.gif]] |


#### 1-2-1-4. 중복 값 제거 "DISTINCT"

orderdetails의 ordernumber 값을 중복 없이 보고 싶다면 아래와 같이 DISTINCT 를 사용하면 된다.

| 사용 SQL문                                                                                                | SQL문 실행 화면  |
| --------------------------------------------------------------------------------------------------------- | ---------------- |
| SELECT DISTINCT ordernumber FROM orderdetails; | ![[Obsidians_Multi_Uses/책_및_강의_정리/SQL로_맛보는_데이터_전처리_분석/이미지/Chap2_DISTINCT.gif]] |


### 1-2-2. FROM

- 예시
	```mysql
	SELECT 조회 컬럼
	FROM 테이블;
	```

FROM은 SELECT와 함께 사용해야하며 FROM 뒤에는 조회하고 특정 테이블의 이름을 입력해주면 된다.

### 1-2-3. WHERE

- 예시
	```mysql
	SELECT 조회 컬럼
	FROM 테이블
	WHERE 조건;
	```

WHERE은 데이터를 조회할 때, 특정 조건을 만족하는 데이터만을 조회할 수 있도록 조건을 만들어주는 역할을 하며 FROM 뒤에 작성한다. 아래의 예시를 보며 좀 더 이해해보자.

| 사용 SQL문                                                                                                | SQL문 실행 화면  |
| --------------------------------------------------------------------------------------------------------- | ---------------- |
| SELECT * FROM orders WHERE status = "Shipped"; | ![[Obsidians_Multi_Uses/책_및_강의_정리/SQL로_맛보는_데이터_전처리_분석/이미지/Chap2_WHERE1.gif]] |


#### 1-2-3-1. BETWEEN

- 예시
	```mysql
	SELECT 조회 컬럼
	FROM 테이블
	WHERE 컬럼 BETWEEN 시작 AND 끝;
	```

BETWEEN 은 WHERE 을 사용할 때 ~와 ~ 사이의 조건을 만들고 싶을 때 사용할 수 있으며 아래와 같이 사용할 수 있다.

- classicmodels.orderdetails의 priceeach가 30 에서 50 사이인 데이터를 조회하라.
	```mysql
	SELECT *
	FROM classicmodels.orderdetails
	WHERE priceeach BETWEEN 30 AND 50;
	```


#### 1-2-3-2. 대소관계 표현하기

- classicmodels.orderdetails의 priceeach가 40보다 작은 데이터를 조회하라.
	```mysql
	SELECT *
	FROM classicmodels.orderdetails
	WHERE priceeach < 40;
	```


#### 1-2-3-3. ~이거나 ~인 데이터 조회하기 "IN"

- classicmodels.orders의 status가 "Shipped" 또는 "In Process"인  데이터를 조회하라.
	```mysql
	SELECT *
	FROM classicmodels.orders
	WHERE status IN ("Shipped", "In Process");
	```

- classicmodels.customers의 city가 "Arhus" 또는 "Madrid" , "Torino"인  데이터를 조회하라.
	```mysql
	SELECT *
	FROM classicmodels.customers
	WHERE city IN ("Arhus", "Madrid", "Torino");
	```


#### 1-2-3-4. ~가 아닌 데이터 조회하기 "NOT"

"NOT" 은 조건을 부정하는 조건을 만들어준다. IN 또는 BETWEEN 등 여러 조건 구문의 앞에 사용할 수 있다.  

- classicmodels.orders의 status가 "Shipped" 또는 "In Process"가 아닌  데이터를 조회하라.
	```mysql
	SELECT *
	FROM classicmodels.orders
	WHERE status NOT IN ("Shipped", "In Process");
	```

- classicmodels.customers의 city가 "Arhus" 또는 "Madrid" , "Torino"인  데이터를 조회하라.
	```mysql
	SELECT *
	FROM classicmodels.orderdetails
	WHERE priceeach NOT BETWEEN 30 AND 50;
	```


#### 1-2-3-5. NULL 여부 검사하기 "IS NULL"

테이블의 컬럼에 데이터가 무조건 있어야 할 때 컬럼에 NOT NULL 속성을 추가하지만 NOT NULL 속성을 추가하지 않는다면 기본적으로 Nullable 하게 컬럼이 설정된다. 이렇게 컬럼이 Nullable 할 때, 컬럼의 데이터가 NULL인지 확인하고 싶을 때는 "IS NULL"을 사용하면 된다.

- classicmodels.orders의 comments가 NULL인 데이터 조회하기
	```mysql
	SELECT orderNumber, comments
	FROM orders
	WHERE comments IS NULL;
	```

- classicmodels.orders의 comments가 NULL이 아닌 데이터 조회하기
	```mysql
	SELECT orderNumber, comments
	FROM orders
	WHERE comments IS NOT NULL;
	```


#### 1-2-3-6. 특정 문자열을 포함하는가 ? 'LIKE "%Text%"'

컬럼의 데이터 중특정 문자열로 시작하거나 끝나는 데이터를 조회하거나, 중간에 특정 문자열을 포함하는 데이터를 조회하고 싶을 때 LIKE를 사용한다. LIKE "문자열"과 같은 형태로 조건을 지정하며 "%" 을 사용하여 문자열의 앞 뒤에 어떤 문자열이 들어가도 되는지 설정할 수 있다.


- classicmodels.orders의 comments 중 is로 끝나는 데이터 조회하기
	```mysql
	SELECT orderNumber, comments
	FROM orders
	WHERE comments LIKE "%is";
	```


- classicmodels.orders의 comments 중 is로 시작하는 데이터 조회하기
	```mysql
	SELECT orderNumber, comments
	FROM orders
	WHERE comments LIKE "is%";
	```


- classicmodels.orders의 comments 중 is를 포함하는 데이터 조회하기
	```mysql
	SELECT orderNumber, comments
	FROM orders
	WHERE comments LIKE "%is%";
	```



### 1-2-4. GROUP BY

GROUP BY는 칼럼의 값들을 그룹화해 각 값들의 평균 값, 개수 등을 구할 때 사용한다. 그렇기 때문에 GROUP BY는 보통 집계 함수(SUM, AVG, COUNT)등과 함께 사용된다는 특징이 있다.

- classicmodels.customers 테이블의 데이터를 이용해 국가, 도시별 고객 수를 구하라.
	```mysql
	SELECT COUNTRY, CITY, COUNT(CUSTOMERNUMBER) as N_CUSTOMERS
	FROM classicmodels.customers
	GROUP BY COUNTRY, CITY;
	```

SELECT SUM(CASE WHEN country = 'USA' THEN 1 ELSE 0 END) N_USA, SUM(CASE WHEN country = 'USA' THEN 1 ELSE 0 END)/COUNT(*) USA_PORTION FROM customers where country = "USA";


## 1-2-5. JOIN

RDB에서는 많은 데이터들이 여러 개의 테이블에 나뉘어 저장되기 때문에 이를 적절하게 활용하기 위해서는 JOIN을 잘 사용해야 한다. JOIN은 크게 OUTER JOIN과 INNER JOIN으로 나뉘며 이 중에서 다시 OUTER JOIN과 INNER JOIN으로 나뉜다.

- JOIN 종류
	![[Obsidians_Multi_Uses/책_및_강의_정리/SQL로_맛보는_데이터_전처리_분석/이미지/Chap2_JOIN.png]]


INNER JOIN과 OUTER JOIN에 대해서 간단하게 말하자면 JOIN 하는 두 테이블 간 교집합 부분의 데이터를 조회하는 것이 INNER JOIN, 합집합 부분의 데이터를 조회하는 것이 OUTER JOIN이다.

#### 1-2-5-1. INNER JOIN

INNER JOIN 은 LEFT INNER JOIN과 RIGHT INNER JOIN이 있으며 LEFT와 RIGHT는 두 테이블의 교집합 및 어떤 테이블의 데이터를 모두 가져올 것인지 위치를 기준으로 정하는 것이다.

- INNER JOIN
	 ![[Obsidians_Multi_Uses/책_및_강의_정리/SQL로_맛보는_데이터_전처리_분석/이미지/Chap2_INNER_JOIN.png]]

INNER JOIN은 위의 이미지와 같이 두 테이블의 교집합을 의미하며 아래와 같이 SQL문을 작성하여 조회할 수 있다.

- INNER JOIN을 구현하는 SQL 예시
	```mysql
	SELECT 조회 컬럼
	FROM TableA A
	INNER JOIN TableB B
	ON A.Column = B.Column;
	```

#### 1-2-5-2. OUTER JOIN

OUTER JOIN은 LERT OUTER JOIN과 RIGHT OUTER JOIN으로 나뉘며 왼쪽과 오른쪽 중 특정 테이블 정보를 기준으로 타 테이블을 결합한다.

| LEFT (OUTER) JOIN | RIGHT (OUTER) JOIN |
| ----------------- | ------------------ |
|![[Obsidians_Multi_Uses/책_및_강의_정리/SQL로_맛보는_데이터_전처리_분석/이미지/Chap2_LEFT_JOIN.png]]| ![[Obsidians_Multi_Uses/책_및_강의_정리/SQL로_맛보는_데이터_전처리_분석/이미지/Chap2_RIGHT_JOIN.png]]|

- OUTER JOIN을 구현하는 SQL 예시
	```mysql
	SELECT 조회 컬럼
	FROM TableA A
	LEFT JOIN TableB B
	ON A.Column = B.Column;
	```

ON 뒤에는 두 테이블에서 어떤 컬럼을 기준으로 데이터를 결합할 지 적으면 된다.


#### 1-2-5-3. FULL JOIN

- FULL JOIN
	![[Obsidians_Multi_Uses/책_및_강의_정리/SQL로_맛보는_데이터_전처리_분석/이미지/Chap2_FULL_JOIN.png]]

FULL JOIN은 TableA, TableB와 매칭되는 모든 데이터를 출력하는 JOIN이며 위의 이미지와 같다고 생각하면 된다. MySQL에서는 FULL JOIN을 지원하지 않기 때문에 LEFT JOIN과 RIGHT JOIN을 UNION하여 FULL OUTER JOIN을 사용할 수 있다.

- FULL JOIN을 구현하는 SQL 예시
	```mysql
	SELECT *
	FROM TableA A LEFT JOIN B
	UNION 
	SELECT *
	FROM TableB B LEFT JOIN A;
	```

### 1-2-6. CASE WHEN

CASE WHEN은 조건에 따라 값을 다르게 출력하고 싶은 경우에 사용한다. CASE WHEN의 이해를 돕기위해 아래의 CASE WHEN 사용 예시를 확인해보자.

- classicmodels.customers 에서 country 가 'USA'이거나 'Canada'인 고객들은 North America라고 출력하고 나머지 country 값을 가지는 고객들은 OTHERS라고 출력하는 예시
	```mysql
	SELECT country, CASE WHEN country IN ('USA', "Canada") THEN 'North America' ELSE 'OTHERS' END AS region
	FROM classicmodels.customers
	```

- 북미(USA, Canada)와 비북미를 출력하는 칼럼과 북미, 비북미 거주 고객의 수를 계산하는 예시
	```mysql
	SELECT 
		COUNT(CUSTOMERNUMBER) N_CUSTOMERS,
		CASE WHEN country IN ("USA", "Canada") THEN "North America" ELSE "OTHER" END AS Region
	FROM classicmodels.customers
	GROUP 
	BY CASE WHEN country IN ("USA", "Canada") THEN "North America" ELSE "OTHER" END;
	```




### 1-2-7. SubQuery

SubQuery는 하나의 SQL 문 안에 포함되어 있는 또 다른 SQL문을 말하며, 메인 쿼리가 서브 쿼리를 포함하는 종속적인 관계를 말한다. **Subquery는 SELECT, FROM, WHERE, HAVING, ORDER BY, INSERT 문의 VALUES, UPDATE문의 SET에서 사용 가능하다.**

- NYC에 거주하는 고객들의 주문 번호 조회하기
	```mysql
	SELECT orderNumber 
	FROM orders
	WHERE 
		customerNumber IN (SELECT customerNumber
							 FROM customers
							 WHERE city = 'NYC');
	```

- FROM 또는 JOIN에 SubQuery를 사용하는 예시
	```mysql
	SELECT customerNumber
		FROM (SELECT customerNumber
				 FROM classic.customers
				 WHERE city = "NYC") A
	```

- classicmodels.customers와 classicmodels.order를 이용해 USA 거주자의 주문 번호를 출력하라.
	```mysql
	SELECT orderNumber 
	FROM orders o 
	WHERE o.customerNumber IN (SELECT customerNumber
								FROM customers c 
								WHERE c.country = "USA");
	```


























