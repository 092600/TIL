
# 1. 식품 배송 데이터 분석

이번에는 Instacart라는 e-commerce 회사의 데이터를 분석해보자. 먼저 사용할 데이터 세트는 kaggle(Machine Learning Competition)에 존재하는 데이터 세트로 해당 데이터의 크기가 크기 때문에 샘플링한 데이터를 [데이터 다운로드 링크](https://www.github.com/billyrohh/instacart_dataset에서 다운로드해 사용하자.

## 1-1. 지표 추출

다운받은 데이터 셋을 활용하여 해당 Bussiness의 전반적인 현황을 파악해보자.

### 1-1-1. 전체 주문 건수 중복 데이터 확인

- 전체 주문 건수 중 중복 데이터 확인하기
	```mysql
	mysql> SELECT COUNT(*), COUNT(DISTINCT order_id) FROM orders;
	+----------+--------------------------+
	| COUNT(*) | COUNT(DISTINCT order_id) |
	+----------+--------------------------+
	|     3220 |                     3220 |
	+----------+--------------------------+
	1 row in set (0.00 sec)
	```


### 1-1-2. 구매자 수 

- 전체 구매자 수 확인하기
	```mysql
	mysql> SELECT COUNT(DISTINCT user_id) FROM orders;
	+-------------------------+
	| COUNT(DISTINCT user_id) |
	+-------------------------+
	|                    3159 |
	+-------------------------+
	1 row in set (0.00 sec)
	```

### 1-1-3. 상품별 주문 건수

- 상품별 주문 건수
	```mysql
	mysql> SELECT 
		->     p.product_name,
		->     COUNT(DISTINCT op.order_id) F
		-> FROM order_products op
		-> LEFT JOIN products p
		-> ON op.product_id = p.product_id
		-> GROUP BY 1;
	```

### 1-1-3. 장바구니에 가장 먼저 넣는 상품 10개

장바구니에 상품이 담기는 순서 정보는  order_products 테이블의  ADD_TO_CART_ORDER 컬럼에 저장된다. 해당 데이터를 활용해 어떤 상품이 장바구니에 가장 먼저 들어가는 상품 상위 10개를 조회해보자.

- 장바구니에 가장 먼저 넣는 상품 10개
	```mysql
	SELECT 
		*,
		ROW_NUMBER() OVER(ORDER BY F_1ST DESC) RNK
	FROM 
		(SELECT 
			product_id,
			SUM(CASE WHEN ADD_TO_CART_ORDER = 1 THEN 1 ELSE 0 END) F_1ST
		FROM order_products
		GROUP BY 1) A
	LIMIT 10;
	
	+------------+-------+-----+
	| product_id | F_1ST | RNK |
	+------------+-------+-----+
	|      24852 |   117 |   1 |
	|      13176 |    62 |   2 |
	|      27845 |    37 |   3 |
	|      21137 |    31 |   4 |
	|      21903 |    27 |   5 |
	|      47209 |    24 |   6 |
	|      19660 |    18 |   7 |
	|      16797 |    17 |   8 |
	|       5785 |    16 |   9 |
	|      12341 |    16 |  10 |
	+------------+-------+-----+
	10 rows in set (0.04 sec)
	```



### 1-1-4. 시간별 주문 건수

시간별 주문 건수는 orders의 ORDER_HOUR_OF_DAY 으로 그룹핑 한 후, order_id를 카운트하여 조회할 수 있다. 이 때 order_id가 중복될 수 있어 DISTINCT를 추가해 중복을 제거하자.

- 시간별 주문 건수
	```mysql
	SELECT 
		ORDER_HOUR_OF_DAY,
		COUNT(DISTINCT order_id) f
	FROM orders
	GROUP BY 1
	ORDER BY 1;
	```

### 1-1-5. 첫 구매 후 다음 구매까지 걸린 평균 일수

orders 테이블에서 DAYS_SINCE_PRIOR_ORDER는 이전 주문이 이루어진 후, 며칠 뒤에 구매가 이루어졌는지를 나타내는 값이다.\ 이 값들의 평균을 구해 첫 구매 후 다음 구매까지 얼마나 걸리는 지를 구해보자.

- 첫 구매 후 다음 구매까지 걸린 평균 일수
	```mysql
	SELECT 
		SUM(DAYS_SINCE_PRIOR_ORDER)/COUNT(*) AS AVG_REORDER_DAY
	FROM orders;
	```


### 1-1-6. 주문 건당 평균 구매 상품 수(UPT, Unit Per Transaction)


- 주문 건당 평균 구매 상품 수(UPT, Unit Per Transaction)
	```mysql
	SELECT
		COUNT(product_id)/COUNT(DISTINCT order_id) AS UPT
	FROM order_products;
	```

### 1-1-7. 인당 평균 주문 건수

- 인당 평균 주문 건수
	```mysql
	SELECT
		COUNT(DISTINCT order_id)/COUNT(DISTINCT user_id) AS AVG_F
	FROM orders
	```


## 1-2. 구매자 분석

### 1-2-1. 10분위 분석

10분위 분석이란 전체를 10분위로 나누어 각 분위 수에 해당하는 집단의 성질을 나타내는 방법으로 10분위 분석을 진행하려면 먼저 각 구매자의 분위 수를 구해야 한다. 

#### 1-2-1-1. 각 구매자의 분위수 구하기

- 각 구매자의 분위수 구하기
	```mysql
	SELECT 
		*,
		ROW_NUMBER() OVER(ORDER BY F DESC) RNK
	FROM (
		SELECT 
			USER_ID,
			COUNT(DISTINCT ORDER_ID) F 
		FROM orders 
		GROUP BY 1) A


	+---------+---+-----+
	| USER_ID | F | RNK |
	+---------+---+-----+
	|   32099 | 3 |   1 |
	|    2610 | 2 |   2 |
	|   10132 | 2 |   3 |
			 ....
	|  206124 | 1 | 3157 |
    |  206155 | 1 | 3158 |
    |  206201 | 1 | 3159 |
    +---------+---+------+
	3159 rows in set (0.01 sec)
	```

위와 같이 쿼리를 작성하여 현재 유저가 3159 명이라는 것을 알았다. 고객을 10분위로 나눈 데이터로 고객 10분위 테이블을 만들어보자.

- 고객 10분위 테이블 만들기
	```mysql
	CREATE TEMPORARY TABLE USER_QUANTILE AS 
	SELECT 
		*,
		CASE WHEN RNK <= 316 THEN "Quantile_1"
		WHEN RNK <= 632 THEN "Quantile_2"
		WHEN RNK <= 948 THEN "Quantile_3"
		WHEN RNK <= 1264 THEN "Quantile_4"
		WHEN RNK <= 1580 THEN "Quantile_5"
		WHEN RNK <= 1895 THEN "Quantile_6"
		WHEN RNK <= 2211 THEN "Quantile_7"
		WHEN RNK <= 2527 THEN "Quantile_8"
		WHEN RNK <= 2843 THEN "Quantile_9"
		WHEN RNK <= 3159 THEN "Quantile_10" END quantile
	FROM
		(SELECT 
			*,
			ROW_NUMBER() OVER(ORDER BY F DESC) RNK
		FROM (
			SELECT 
				user_id,
			    COUNT(DISTINCT order_id) F
			FROM orders
			GROUP BY 1) A
			) A;
	```

- 고객 10분위를 활용한 데이터 분석
	```mysql
	mysql> SELECT
	    -> quantile,
	    -> SUM(f)/3220 f
	    -> FROM user_quantile
	    -> GROUP BY 1;
	    
	+-------------+--------+
	| quantile    | f      |
	+-------------+--------+
	| Quantile_1  | 0.1171 |
	| Quantile_2  | 0.0981 |
	| Quantile_3  | 0.0981 |
	| Quantile_4  | 0.0981 |
	| Quantile_5  | 0.0981 |
	| Quantile_6  | 0.0978 |
	| Quantile_7  | 0.0981 |
	| Quantile_8  | 0.0981 |
	| Quantile_9  | 0.0981 |
	| Quantile_10 | 0.0981 |
	+-------------+--------+
	10 rows in set (0.01 sec)
	```

이렇게 각 분위 수의 주문 건수/전체 주문건수를 하여 각 분위 수별로 주문 건수가 균등하게 분포되어있다는 것을 확인할 수 있다. 



### 1-2-2. 상품 분석

이번에는 재구매를 많이 하는 상품을 알아보고, 각 상품의 판매 특성에 대해 살펴보자.

- 재구매 비중이 높은 상품 조회하기
	```mysql
	SELECT 
		product_id,
		SUM(reordered)/SUM(1) reorder_rate,
		COUNT(DISTINCT order_id) f
	FROM order_products op
	GROUP BY product_id
	ORDER BY reorder_rate DESC;

	+------------+--------------+---+
	| product_id | reorder_rate | f |
	+------------+--------------+---+
	|        101 |       1.0000 | 1 |
	|        106 |       1.0000 | 1 |
	|         47 |       1.0000 | 1 |
					...
	|      49644 |       0.0000 |   1 |
	|      49652 |       0.0000 |   1 |
	|      49668 |       0.0000 |   1 |
	+------------+--------------+-----+
	9288 rows in set (0.06 sec)
	```

- 주문이 10회 이하인 상품 제외하기
	```mysql
	SELECT 
		op.product_id,
		SUM(reordered)/SUM(1) reorder_rate,
		COUNT(DISTINCT order_id) f
	FROM order_products op
	LEFT JOIN products p
	ON op.product_id = p.product_id
	GROUP BY product_id
	HAVING COUNT(DISTINCT ORDER_ID) > 10;
	
	+------------+--------------+----+
	| product_id | reorder_rate | f  |
	+------------+--------------+----+
	|         45 |       0.7778 | 18 |
	|        196 |       0.6857 | 35 |
	|        260 |       0.7241 | 29 |
	|        432 |       0.8108 | 37 |
					...
	|      49235 |       0.8254 |  63 |
	|      49383 |       0.4737 |  19 |
	|      49520 |       0.5294 |  17 |
	|      49683 |       0.7033 |  91 |
	+------------+--------------+-----+
	486 rows in set (0.07 sec)
	```


- 상품 이름까지 출력하기
	```mysql
		SELECT 
			op.product_id,
			p.product_name,
			SUM(reordered)/SUM(1) reorder_rate,
			COUNT(DISTINCT order_id) f
		FROM order_products op
		LEFT JOIN products p
		ON op.product_id = p.product_id
		GROUP BY product_id, p.product_name
		HAVING COUNT(DISTINCT ORDER_ID) > 10;
	
	+------------+----------------------------------------+--------------+----+
	| product_id | product_name                           | reorder_rate | f  |
	+------------+----------------------------------------+--------------+----+
	|         45 | European Cucumber                      |       0.7778 | 18 |
	|        196 | Soda                                   |       0.6857 | 35 |
									...
	|      49235 | Organic Half & Half                    |       0.8254 | 63 |
	|      49383 | Organic Bunch Beets                    |       0.4737 | 19 |
	|      49520 | Orange Sparkling Water                 |       0.5294 | 17 |
	|      49683 | Cucumber Kirby                         |       0.7033 | 91 |
	+------------+----------------------------------------+--------------+----+
	486 rows in set (0.10 sec)
	```

> **HAVING vs WHERE**
> 
> HAVING과 WHERE 모두 조건을 생성할 수 있지만 WHERE은 FROM에 위치한 테이블에만 조건을 걸 수 있고 HAVING은 그룹핑한 데이터에 조건을 생성하고 싶을 때 사용한다는 차이가 있다


