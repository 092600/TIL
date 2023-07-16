
# 1. 자동차 매출 데이터를 이용한 리포트 작성

이번에도 이전과 마찬가지로 classicmodels 데이터베이스 안에 있는 데이터를 사용할 것이다. 하지만 이번에는 좀 더 리포트에 사용할 법한 데이터를 조회해보는 쿼리를 작성해보는 시간을 가져보자.

## 1-1. 구매 지표 추출

구매 지표 추출에는 아래와 같은 데이터들을 추출해볼 수 있는 쿼리를 작성하는 것을 목표로 한다.

1. 매출액(일자별, 월별, 연도별)
2. 구매자 수, 구매 건수(일자별, 월별, 연도별)
3. 인당 매출액(AMV, Average Member Value) (연도별)
4. 건당 구매 금액(ATV, Average Transaction Value) (연도별)

우선 쿼리 작성 전에 데이터베이스의 ERD를 확인해보자.

- DB ERD
	![[Obsidians_Multi_Uses/책_및_강의_정리/SQL로_맛보는_데이터_전처리_분석/이미지/Chap4_classicmodels_DB_ERD.png]]

이제 본격적으로 여러가지 쿼리를 작성하는 방법에 대해 알아보자.

### 1-1-1. 매출액 조회

데이터베이스에 데이터를 활용해 고객의 매출액 정보를 확인해보기 위해서 orders 테이블의 데이터를 활용해보자.

#### 1-1-1-1. 일별 매출액 조회하기

일별 매출액 조회하기 위해서는 orders 테이블의 주문 날짜(orderDate) 기준으로 데이터를 그룹핑해야하며, 매출액 정보를 계산하기 위해서 orderdetails 테이블와 order 테이블을 orderNumber를 기준으로 조인해주어야 한다. 두 테이블을 조인한 후에는 priceEach 컬럼과 quantityOrdered 컬럼의 데이터를 곱한 값을 조회하도록 해 일별 매출액 정보를 조회할 수 있다.

- 일별 매출액 조회 시 해주어야할 작업
	1. orders 테이블의 주문 날짜(orderDate) 기준으로 데이터를 그룹핑한다.
	2. 매출액 정보를 조회하기 위해 orders 테이블과 orderdetails 테이블을 조인한다.
	3. orderdetails 테이블의 priceEach 컬럼과 quantityOrdered 컬럼의 값을 곱하여 데이터를 조회한다.

근데 이렇게 코드를 작성하면 order 테이블에는 같은 날짜에 여러 개의 orderNumber Row가 여러 개 존재하기 때문에 아래와 같이 날짜별 매출액 합이 나오지 않는다. 한 번 확인해보자.

- 잘못된 쿼리 작성 예시
	```mysql
	mysql> SELECT o.orderDate, o.orderNumber d.priceeach * d.quantityordered 
		   FROM orders o 
		   LEFT JOIN orderdetails d 
		   ON o.orderNumber = d.orderNumber 
		   LIMIT 10;
		   
	+------------+-------------+---------------------------------+
	| orderDate  | orderNumber | d.priceeach * d.quantityordered |
	+------------+-------------+---------------------------------+
	| 2003-01-06 |       10100 |                         4080.00 |
	| 2003-01-06 |       10100 |                         2754.50 |
	| 2003-01-06 |       10100 |                         1660.12 |
	| 2003-01-06 |       10100 |                         1729.21 |
	| 2003-01-09 |       10101 |                         2701.50 |
	| 2003-01-09 |       10101 |                         4343.56 |
	| 2003-01-09 |       10101 |                         1463.85 |
	| 2003-01-09 |       10101 |                         2040.10 |
	| 2003-01-10 |       10102 |                         3726.45 |
	| 2003-01-10 |       10102 |                         1768.33 |
	+------------+-------------+---------------------------------+
	10 rows in set (0.00 sec)
	```

그러므로 이제 같은 orderDate 값을 가지는 Row들의 priceeach * quantityorderd 의 값의 총 합을 구할 수 있도록 SUM 함수를 사용해 쿼리를 작성해보자.



- 제대로 쿼리 작성하기
	```mysql
	mysql> SELECT o.orderDate, SUM(d.priceeach * d.quantityordered) AS SALES
		   FROM orders o 
		   LEFT JOIN orderdetails d 
		   ON o.orderNumber = d.orderNumber 
		   GROUP BY 1
		   ORDER BY 1;
		   
	+------------+-----------+
	| orderDate  | SALES     |
	+------------+-----------+
	| 2003-01-06 |  10223.83 |
	| 2003-01-09 |  10549.01 |
	| 2003-01-10 |   5494.78 |
	| 2003-01-29 |  50218.95 |
	| 2003-01-31 |  40206.20 |
	| 2003-02-11 |  53959.21 |
	|     ...    |    ...    |
	| 2005-05-29 |  49890.61 |
	| 2005-05-30 |  14447.17 |
	| 2005-05-31 |  70933.74 |
	+------------+-----------+
	265 rows in set (0.10 sec)
	```

위의 쿼리에서 GROUP BY 1과 ORDER BY 1은 첫 번째 열을 기준으로 그룹화한다는 것을 의미하며 [# [What does SQL clause "GROUP BY 1" mean?](https://stackoverflow.com/questions/7392730/what-does-sql-clause-group-by-1-mean)](https://stackoverflow.com/questions/7392730/what-does-sql-clause-group-by-1-mean)에서 자세한 내용을 확인할 수 있다.

#### 1-1-1-2. 월별 매출액 조회하기

 월별 매출액은 일별 매출액 조회할 때와 나머지는 전부 같지만 orderDate 값을 월 별로 그룹화할 수 있도록 쿼리를 작성해주어야 한다. 이때 orderDate 컬럼의 값을 SUBSTR() 함수를 사용하여 orderDate 값을 월별로 그룹화할 수 있다.

- 쿼리 작성 예시
	```mysql
	mysql> SELECT SUBSTR(orderDate, 1, 7) AS MONTH, SUM(d.priceeach * d.quantityordered) AS SALES
	    -> FROM orders o
	    -> LEFT JOIN orderdetails d
	    -> ON o.orderNumber = d.orderNumber
	    -> GROUP BY 1
	    -> ORDER BY 1
	    -> LIMIT 10;
	    
	+---------+-----------+
	| MONTH   | SALES     |
	+---------+-----------+
	| 2003-01 | 116692.77 |
	| 2003-02 | 128403.64 |
	| 2003-03 | 160517.14 |
	| 2003-04 | 185848.59 |
	| 2003-05 | 179435.55 |
	| 2003-06 | 150470.77 |
	| 2003-07 | 201940.36 |
	| 2003-08 | 178257.11 |
	| 2003-09 | 236697.85 |
	| 2003-10 | 514336.21 |
	+---------+-----------+
	10 rows in set (0.01 sec)
	```

일별 매출액을 조회하는 방법만 잘 알고있다면 월별 매출액을 조회하거나 연도별 매출액을 조회하는 것은 매우 쉽다. 월별 매출액을 조회할 때와 동일하지만 이번에는 연도별 매출액을 조회해보자.

#### 1-1-1-3. 연도별 매출액 조회하기

연도별 매출액을 조회하기 위해서 이번에도 orderDate에 SUBSTR() 함수를 사용해보자.

- 연도별 매출액 조회 쿼리
	```mysql
	mysql> SELECT SUBSTR(orderDate, 1, 4) AS MONTH, SUM(d.priceeach * d.quantityordered) AS SALES
	    -> FROM orders o
	    -> LEFT JOIN orderdetails d
	    -> ON o.orderNumber = d.orderNumber
	    -> GROUP BY 1
	    -> ORDER BY 1
	    -> LIMIT 10;
	
	+-------+------------+
	| MONTH | SALES      |
	+-------+------------+
	| 2003  | 3317348.39 |
	| 2004  | 4515905.51 |
	| 2005  | 1770936.71 |
	+-------+------------+
	3 rows in set (0.01 sec)
	```

이제 구매자수와 구매 건수 데이터를 조회하는 쿼리를 작성해보자.

### 1-1-2. 구매자, 구매건수 데이터 조회

#### 1-1-2-1. 일자별 구매자 수 

일자별 구매자 수는  orders 테이블의 데이터를 판매일(orderDate) 값을 기준으로 그룹핑하고 고객 번호를 COUNT 해주면 된다. 이는 

- 쿼리 작성하기
	```mysql
	mysql> SELECT o.orderDate, c.customerNumber
	    -> FROM orders o
	    -> LEFT JOIN customers c
	    -> ON o.customerNumber = c.customerNumber
	    -> LIMIT 10;
	
	+------------+----------------+
	| orderDate  | customerNumber |
	+------------+----------------+
	| 2003-01-06 |            363 |
	| 2003-01-09 |            128 |
	| 2003-01-10 |            181 |
	| 2003-01-29 |            121 |
	| 2003-01-31 |            141 |
	| 2003-02-11 |            145 |
	| 2003-02-17 |            278 |
	| 2003-02-24 |            131 |
	| 2003-03-03 |            385 |
	| 2003-03-10 |            486 |
	+------------+----------------+
	10 rows in set (0.00 sec)
	```

#### 1-1-2-2. 인당 매출액(연도별)

고객의 인당 매출액은 고객의 로얄티(Loyalty)를 측정하는 요인으로 사용될 수 있으며 기간별로 평균 인당 고객 매출액을 비교하면, 고객 1명이 우리의 서비스에 얼마를 지불하는지 그 변화를 파악할 수 있다.

- 인당 매출액(연도별) 쿼리
	```mysql
	mysql> SELECT 
		-> SUBSTR(o.orderDate, 1, 4) AS YY,
		-> COUNT(DISTINCT o.customerNumber) AS N_PURCHASER,
		-> SUM(d.priceEach * d.quantityOrdered) AS SALES
		-> FROM orders o
		-> LEFT JOIN orderDetails d
		-> ON o.orderNumber = d.orderNumber
		-> GROUP BY 1
		-> ORDER BY 1;

	+------+-------------+------------+
	| YY   | N_PURCHASER | SALES      |
    +------+-------------+------------+
    | 2003 |          74 | 3317348.39 |
    | 2004 |          89 | 4515905.51 |
    | 2005 |          44 | 1770936.71 |
    +------+-------------+------------+
    3 rows in set (0.00 sec)
	```


#### 1-1-2-3. 건당 구매 금액(ATV, Average Transaction Value) (연도별)

건당 구매 금액은 1건의 거래 당 평균적으로 얼마의 매출을 발생시키는가를 알수 있는 정보로 총 가격을 구매 건수로 나누어 계산하면 된다.

- 건당 구매 금액 쿼리
	```mysql
	mysql> SELECT
	    -> SUBSTR(o.orderDate, 1, 4) AS YY,
	    -> COUNT(DISTINCT o.customerNumber) AS N_PURCHASER,
	    -> SUM(d.priceEach * d.quantityOrdered) AS SALES,
	    -> SUM(d.priceEach * d.quantityOrdered)/COUNT(DISTINCT o.orderNumber) AS ATV
	    -> FROM orders o
	    -> LEFT JOIN orderdetails d
	    -> ON o.orderNumber = d.orderNumber
	    -> GROUP BY 1
	    -> ORDER BY 1;
	    
	+------+-------------+------------+--------------+
	| YY   | N_PURCHASER | SALES      | ATV          |
	+------+-------------+------------+--------------+
	| 2003 |          74 | 3317348.39 | 29886.021532 |
	| 2004 |          89 | 4515905.51 | 29906.659007 |
	| 2005 |          44 | 1770936.71 | 27670.886094 |
	+------+-------------+------------+--------------+
	3 rows in set (0.00 sec)
	```

## 1-2. 그룹별 구매 지표 구하기

이번에도 데이터베이스에서 아래와 같은 데이터들을 추출해보도록 하자.

1. 국가별, 도시별 매출액
2. 북미 vs 비북미 매출액 비교
3. 매출 Top 5 국가 및 매출 및 순위

### 1-2-1. 국가별, 도시별 매출액

국가별, 도시별 매출액 데이터를 확인해보기기 위해서 우리는 데이터베이스의 orders, ordersdetails, customers 테이블을 사용해야 한다. 해당 테이블들의 ERD는 아래애서 확인할 수 있다.

- DB ERD
	![[Obsidians_Multi_Uses/책_및_강의_정리/SQL로_맛보는_데이터_전처리_분석/이미지/Chap4_classicmodels_DB_ERD.png]]

#### 1-2-1-1. 국가별, 국가별 매출액 구하기

국가별, 도시별 매출액을 구하기 위해서 아까도 말했듯 orders, orderdetails, customers 테이블을 조인해주어야 한다. 아래와 같이 3개의 테이블을 조인할 수 있다.

- 테이블 3 개 조인하기
	```mysql
	mysql> SELECT o.orderNumber, d.priceEach, c.country
	    -> FROM orders o
	    -> LEFT JOIN orderdetails d
	    -> ON o.orderNumber = d.orderNumber
	    -> LEFT JOIN customers c
	    -> ON o.customerNumber = c.customerNumber
	    -> LIMIT 5;
	    
	+-------------+-----------+---------+
    | orderNumber | priceEach | country |
    +-------------+-----------+---------+
    |       10123 |    120.71 | France  |
    |       10123 |    114.84 | France  |
    |       10123 |    117.26 | France  |
    |       10123 |     43.27 | France  |
    |       10298 |    105.86 | France  |
    +-------------+-----------+---------+
	5 rows in set (0.00 sec)
	```

테이블 3 개를 조인하는 것까지 마쳤으니 이제 국가별로  그룹핑하고 여러 국가, 도시별 매출액을 데이터를 조회해보자.  

- 국가 매출액 조회하기
	```mysql
	mysql> SELECT c.country AS COUNTRY, c.city AS CITY, SUM(d.priceEach * d.quantityOrdered) AS SALES
	    -> FROM orders o
	    -> LEFT JOIN orderdetails d
	    -> ON o.orderNumber = d.orderNumber
	    -> LEFT JOIN customers c
	    -> ON o.customerNumber = c.customerNumber
	    -> GROUP BY 1, 2
	    -> ORDER BY 1, 2
	    -> LIMIT 5;
	    
		+-----------+----------------+-----------+
		| COUNTRY   | CITY           | SALES     |
		+-----------+----------------+-----------+
		| Australia | Chatswood      | 133907.12 |
		| Australia | Glen Waverly   |  55866.02 |
		| Australia | Melbourne      | 180585.07 |
		| Australia | North Sydney   | 137034.22 |
		| Australia | South Brisbane |  55190.16 |
		+-----------+----------------+-----------+
		5 rows in set (0.01 sec)
	```


### 1-2-2. 북미 vs 비북미 매출액 비교

이전에 북미와 비북미를 구분하기 위해서 CASE WHEN을 사용했듯이 이번에도 3개의 테이블을 JOIN하고 여러 국가들 중 북미 국가인지 비북미 국가인지를 CASE WHEN을 사용하여 구분하면 된다.  이제 쿼리를 작성해보자.


#### 1-2-2-1. 북미 vs 비북미 매출액 비교

- 북미 vs 비북미 매출액 비교 
	```mysql
	mysql> SELECT 
		->     CASE WHEN c.country IN ("USA", "Canada")
		->     THEN 'North America'
		->     ELSE 'OTHERS'
		->     END AS COUNTRY_GRP,
		->     SUM(d.priceEach * d.quantityOrdered) AS SALES
		-> FROM orders o
		-> LEFT JOIN orderdetails d
		-> ON o.orderNumber = d.orderNumber
		-> LEFT JOIN customers c
		-> ON o.customerNumber = c.customerNumber
		-> GROUP BY 1
		-> ORDER BY 2 DESC;
	
		+---------------+------------+
		| COUNTRY_GRP   | SALES      |
		+---------------+------------+
		| OTHERS        | 6124998.70 |
		| North America | 3479191.91 |
		+---------------+------------+
		2 rows in set (0.01 sec)
	```

### 1-2-3. 매출 Top 5 국가 및 매출 및 순위

데이터의 값에 따라 등수를 매기는 함수로 row_number(), rank(), dense_rank() 함수가 있다. 각각은 등수를 만들어준다는 점에서는 비슷하지만 조금씩 랭크를 만드는 방법이 다르기 때문에 정리해보고 국가별 매출 랭크를 매겨보자.

- 랭크에 사용되는 함수
	1. RANK : 
	> 	동률을 모두 같은 등수로 처리한 후, 그 다음 등수를 동률의 수만큼 제외하고 매긴다.

	2. DENSE_RANK

	> 	RANK와 동일하게 동률을 같은 등수로 처리하지만, 그 다음 등수를 동률의 수만큼 제외하지 않고 바로 다음 등수로 매긴다.

	3. ROW_NUMBER

	> 	ROW_ NUMBER는 동률을 반영하지 않는다. 즉 동일한 등수는 존재하지 않고 모든 행은 다른 등수를 가지게 된다.
	

#### 1-2-3-1. 매출 Top 5 국가 및 매출 및 순위 구하기

매출 TOP 5 국가 및 매출을 구할 때 우선적으로 각 국가 별 매출(SALES) 값을 구해고 이후 등수를 매긴 후 등수를 바탕으로 매출이 높은 상위 5개의 국가를 조회하도록 쿼리를 작성해야 한다.

각 3 단계 중 2개의 단계를 진행할 때 각 단계마다 테이블로 만들어서 사용해도 되지만 여러 종류의 데이터를 조회할 때 매번 테이블을 만든다면 테이블의 수가 매우 많아지므로 데이터 관리에 어려움이 생긴다. 그러므로 이번에는 테이블 생성하지 않고 서브쿼리만을 사용해서 매출 TOP 5 국가 및 매출 데이터를 조회해보자.

우선 각 국가 별 매출 데이터를 조회해보자.

- 국가 별 매출 데이터를 조회
	```mysql
	SELECT c.country, SUM(d.priceEach * d.quantityOrdered) AS SALES
	FROM orders o
	LEFT JOIN orderdetails d
	ON o.orderNumber = d.orderNumber
	LEFT JOIN customers c
	ON o.customerNumber = c.customerNumber
	GROUP BY 1;
	
	mysql> SELECT c.country, SUM(d.priceEach * d.quantityOrdered) AS SALES
	    -> FROM orders o
	    -> LEFT JOIN orderdetails d
	    -> ON o.orderNumber = d.orderNumber
	    -> LEFT JOIN customers c
	    -> ON o.customerNumber = c.customerNumber
	    -> GROUP BY 1;
	+-------------+------------+
	| country     | SALES      |
	+-------------+------------+
	| France      | 1007374.02 |
	| USA         | 3273280.05 |
	| Australia   |  562582.59 |
	| Norway      |  270846.30 |
	| Germany     |  196470.99 |
	| Spain       | 1099389.09 |
	| Sweden      |  187638.35 |
	| Denmark     |  218994.92 |
	| Singapore   |  263997.78 |
	| Japan       |  167909.95 |
	| Finland     |  295149.35 |
	| UK          |  436947.44 |
	| Ireland     |   49898.27 |
	| Canada      |  205911.86 |
	| Hong Kong   |   45480.79 |
	| Italy       |  360616.81 |
	| Switzerland |  108777.92 |
	| Belgium     |  100068.76 |
	| New Zealand |  476847.01 |
	| Austria     |  188540.06 |
	| Philippines |   87468.30 |
	+-------------+------------+
	21 rows in set (0.01 sec)
	```

위처럼 국가 별 매출 데이터를 조회하는 쿼리를 작성한 후 서비 쿼리로 해당 쿼리를 사용해서 국가의 매출 별로 등수를 매겨보자.

```mysql
SELECT 
	COUNTRY,
	SALES,
	DENSE_RANK() OVER(ORDER BY SALES DESC) RNK
FROM (SELECT c.country, SUM(d.priceEach * d.quantityOrdered) AS SALES
	FROM orders o
	LEFT JOIN orderdetails d
	ON o.orderNumber = d.orderNumber
	LEFT JOIN customers c
	ON o.customerNumber = c.customerNumber
	GROUP BY 1 ) SALES;
	
mysql> SELECT
    ->     COUNTRY,
    ->     SALES,
    ->     DENSE_RANK() OVER(ORDER BY SALES DESC) RNK
    -> FROM 
	    (SELECT 
		    c.country,
		    SUM(d.priceEach * d.quantityOrdered) AS SALES
    ->   FROM orders o
    ->   LEFT JOIN orderdetails d
    ->   ON o.orderNumber = d.orderNumber
    ->   LEFT JOIN customers c
    ->   ON o.customerNumber = c.customerNumber
    ->   GROUP BY 1 ) SALES;
+-------------+------------+-----+
| COUNTRY     | SALES      | RNK |
+-------------+------------+-----+
| USA         | 3273280.05 |   1 |
| Spain       | 1099389.09 |   2 |
| France      | 1007374.02 |   3 |
| Australia   |  562582.59 |   4 |
| New Zealand |  476847.01 |   5 |
| UK          |  436947.44 |   6 |
| Italy       |  360616.81 |   7 |
| Finland     |  295149.35 |   8 |
| Norway      |  270846.30 |   9 |
| Singapore   |  263997.78 |  10 |
| Denmark     |  218994.92 |  11 |
| Canada      |  205911.86 |  12 |
| Germany     |  196470.99 |  13 |
| Austria     |  188540.06 |  14 |
| Sweden      |  187638.35 |  15 |
| Japan       |  167909.95 |  16 |
| Switzerland |  108777.92 |  17 |
| Belgium     |  100068.76 |  18 |
| Philippines |   87468.30 |  19 |
| Ireland     |   49898.27 |  20 |
| Hong Kong   |   45480.79 |  21 |
+-------------+------------+-----+
21 rows in set (0.01 sec)
```

이번에는 국가 별 매출액을 기준으로 순위를 매겼다. 이제 해당 순위가 1 ~ 5까지의 국가들만을 조회하도록 쿼리를 작성해보자.

- 매출액 상위 5개 국가 조회
	```mysql
	mysql> SELECT *
	    -> FROM (
	    -> SELECT
	    -> COUNTRY,
	    -> SALES,
	    -> DENSE_RANK() OVER(ORDER BY SALES DESC) RNK
	    -> FROM (SELECT c.country, SUM(d.priceEach * d.quantityOrdered) AS SALES
	    -> FROM orders o
	    -> LEFT JOIN orderdetails d
	    -> ON o.orderNumber = d.orderNumber
	    -> LEFT JOIN customers c
	    -> ON o.customerNumber = c.customerNumber
	    -> GROUP BY 1 ) SALES ) RNK
	    -> WHERE RNK BETWEEN 1 AND 5;
	    
	+-------------+------------+-----+
	| COUNTRY     | SALES      | RNK |
	+-------------+------------+-----+
	| USA         | 3273280.05 |   1 |
	| Spain       | 1099389.09 |   2 |
	| France      | 1007374.02 |   3 |
	| Australia   |  562582.59 |   4 |
	| New Zealand |  476847.01 |   5 |
	+-------------+------------+-----+
	5 rows in set (0.01 sec)) RNK
	```

**서브쿼리를 FROM이나 JOIN에 사용하는 경우에는 쿼리의 마지막에 문자를 입력해야 한다. 입력하지 않는 경우 에러가 발생하는 경우가 많으니 주의하자.**  또한 랭크가 1 ~ 5인 국가를 구하기 위해 다시 한 번 서브 쿼리를 사용한 이유는 WHERE 절은 FROM에 위치한 테이블에서만 조건을 걸 수 있기 때문이다. RNK는 SELECT에서 생성한 칼럼이기 때문에 조건절(WHERE)에서 사용할 수 없다는 점에 주의하자.

## 1-3. 재구매율 구하기

연도별 재구매율이란 특정 기간 구매자 중 특정 기간에 연달아 구매한 구매자의 비중을 말한다. 예를들어 특정기간 A(연도)와 A(연도) 이후의 특정 기간 B(연도) 사이에 상품을 구매한 고객의 비율을 구하려면 아래와 같이 쿼리를 작성할 수 있다.

- 연도별 재구매율 쿼리
	```mysql
	SELECT 
		A.customerNumber,
		A.orderDate, 
		B.customerNumber, 
		B.orderDate
	FROM orders A
	LEFT JOIN orders B
	ON 
		A.customerNumber = B.customerNumber AND
		SUBSTR(A.orderDate,1,4) = SUBSTR(B.orderDate,1,4)-1;

	+----------------+------------+----------------+------------+
	| customerNumber | orderDate  | customerNumber | orderDate  |
	+----------------+------------+----------------+------------+
	|            334 | 2004-05-05 |            334 | 2005-01-06 |
	|            131 | 2004-05-07 |           NULL | NULL       |
	|            173 | 2004-05-08 |           NULL | NULL       |
	|            450 | 2004-05-11 |            450 | 2005-04-01 |
	|            450 | 2004-05-11 |            450 | 2005-04-22 |
	+----------------+------------+----------------+------------+
	556 rows in set (0.01 sec)

	```


쿼리를 실행하면 위와같은 테이블이 생성되고 두 값을 나누이 연도별 재구매율, 즉 Retiontion Rate(%)를 구할 수 있다.


#### 1-3-1. 국가별 재구매율 구하기

- 국가별 재구매율 구하기
	```mysql
	mysql> SELECT
	    -> C.country,
	    -> SUBSTR(A.orderDate,1,4) AS YY,
	    -> COUNT(DISTINCT A.customerNumber) BU_1,
	    -> COUNT(DISTINCT B.customerNumber) BU_2,
	    -> COUNT(DISTINCT B.customerNumber)/COUNT(DISTINCT A.customerNumber) AS RETENTION_RATE
	    -> FROM orders A
	    -> LEFT JOIN orders B
	    -> ON
	    -> A.customerNumber = B.customerNumber AND
	    -> SUBSTR(A.orderDate,1,4) = SUBSTR(B.orderDate,1,4)-1
	    -> LEFT JOIN customers C
	    -> ON A.customerNumber = C.customerNumber
	    -> GROUP BY 1, 2;
	    
	+-------------+------+------+------+----------------+
	| country     | YY   | BU_1 | BU_2 | RETENTION_RATE |
	+-------------+------+------+------+----------------+
	| Australia   | 2003 |    5 |    3 |         0.6000 |
	| Australia   | 2004 |    3 |    2 |         0.6667 |
	| Australia   | 2005 |    4 |    0 |         0.0000 |
	| Austria     | 2003 |    2 |    1 |         0.5000 |
							...
	| Austria     | 2004 |    1 |    1 |         1.0000 |
	| USA         | 2003 |   26 |   22 |         0.8462 |
	| USA         | 2004 |   31 |    9 |         0.2903 |
	| USA         | 2005 |   13 |    0 |         0.0000 |
	+-------------+------+------+------+----------------+
	53 rows in set (0.00 sec)

	```


## 1-4. 베스트셀러 구하기

미국에서 어떤 상품이 가장 많이 팔리는지, 베스트셀러를 구하는 쿼리를 작성해보자.
- 미국에서 베스트셀러 상품 상위 5개 확인하기
```mysql
CREATE TABLE classicmodels.product_sales AS 
SELECT d.productName,
SUM(quantityOrdered * priceEach) SALES
FROM orders A
LEFT JOIN customers B
ON A.customerNumber = B.customerNumber
LEFT JOIN orderdetails C
ON A.orderNumber = C.orderNumber
LEFT JOIN products D
ON C.productCode = D.productCode
WHERE B.country = 'USA'
GROUP BY 1;

SELECT * 
FROM (
	SELECT *, 
  DENSE_RANK() OVER(ORDER BY SALES DESC) RNK
  FROM product_sales )A
WHERE RNK <= 5
ORDER BY RNK;

+--------------------------------------+----------+-----+
| productName                          | SALES    | RNK |
+--------------------------------------+----------+-----+
| 1952 Alpine Renault 1300             | 78860.11 |   1 |
| 1992 Ferrari 360 Spider red          | 76011.71 |   2 |
| 2003 Harley-Davidson Eagle Drag Bike | 74524.35 |   3 |
| 1998 Chrysler Plymouth Prowler       | 66599.93 |   4 |
| 2002 Suzuki XREO                     | 66150.36 |   5 |
+--------------------------------------+----------+-----+
5 rows in set (0.00 sec)
```


## 1-5. Churn Rate(%)

Churn Rate란 활동 곡객 중 얼마나 많은 고객이 비활동 고객으로 전환되었는지를 의미하는 지표로, 고객 1명을 획득하는 비용을 Acquisition Cost라고 한다. 기업들은 광고, 프로모션 등을 통해 신규 고객을 유치하는 데 생각보다 큰 비용을 사용한다. 그렇기 때문에, 한 번 획득한 고객을 Active로 유지하는 것은 굉장히 중요한 일이다.

Churn Rate를 구하기 전에 Churn에 대한 정의가 필요한데, 일반적으로 Churn은 다음과 같이 정의한다.

> Churn : max(구매일, 접속일) 이후 일정 기간(ex. 3개월) 구매, 접속하지 않은 상태

즉 마지막 구매, 접속일이 현재 시점을 기준으로 3개월 이상 지난 고객을 의미하며, 전체 고객 중에 Churn 에 해당하는 고객의 비중을 Churn Rate라고 한다.

### 1-5-1. Churn Rate(%) 구하기

데이터베이스 내에 고객이 주문한 마지막 날짜를 구해보고, 주문들이 주문 마지막 날짜와 며칠이 차이나는지를 조회할 수 있는 쿼리를 작성해보자.

- 주문과 마지막 날짜 간 차이 계산하기
	```mysql
	// 마지막 날짜 구하기
	mysql> SELECT MAX(orderDate) AS MX_ORDER
	    -> FROM orders;
	+------------+
	| MX_ORDER   |
	+------------+
	| 2005-05-31 |
	+------------+
	1 row in set (0.00 sec)
	
	mysql> SELECT
		-> customerNumber,
		-> MX_ORDER,
		-> '2005-06-01',
		-> DATEDIFF('2005-06-01',MX_ORDER) DIFF
		-> FROM (SELECT
		-> customerNumber,
		-> MAX(orderDate) AS MX_ORDER
		-> FROM orders
		-> GROUP BY 1) BASE;
	
	+----------------+------------+------------+------+
	| customerNumber | MX_ORDER   | 2005-06-01 | DIFF |
	+----------------+------------+------------+------+
	|            103 | 2004-11-25 | 2005-06-01 |  188 |
	|            112 | 2004-11-29 | 2005-06-01 |  184 |
	|            114 | 2004-11-29 | 2005-06-01 |  184 |
							...
	|            489 | 2004-01-22 | 2005-06-01 |  496 |
	|            495 | 2004-04-26 | 2005-06-01 |  401 |
	|            496 | 2005-04-01 | 2005-06-01 |   61 |
	+----------------+------------+------------+------+
	98 rows in set (0.00 sec)
	```


- DIFF에 따라 다른 문자열 출력하기
	```mysql
	SELECT
		CASE WHEN DIFF >= 90 THEN 'CHURN' ELSE 'NON-CHURN' END CHURN_TYPE, 
		COUNT(DISTINCT customerNumber) N_CUS
	FROM (
		SELECT
			customerNumber,
			MX_ORDER,
			'2005-06-01',
			DATEDIFF('2005-06-01',MX_ORDER) DIFF
		FROM (
			SELECT
				customerNumber,
				MAX(orderDate) AS MX_ORDER
			FROM orders
			GROUP BY 1) BASE
		) BASE
	GROUP BY 1;
	
	+------------+-------+
	| CHURN_TYPE | N_CUS |
	+------------+-------+
	| CHURN      |    69 |
	| NON-CHURN  |    29 |
	+------------+-------+
	2 rows in set (0.01 sec)
	```