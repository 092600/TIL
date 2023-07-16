# 1. 상품 리뷰 데이터를 이용한 리포트 작성

이번에는 [# Women's E-Commerce Clothing Reviews](https://www.kaggle.com/datasets/nicapotato/womens-ecommerce-clothing-reviews)의 데이터를 사용해보자. 해당 페이지에서 .csv 파일을 다운받고 데이터베이스에 데이터를 INSERT하자.

## 1-1. Division별 평점 분포 계산

- DIVISION NAME 별 평균 별점
	```mysql
	mysql> SELECT DivisionName, AVG(Rating) AVG_RATE
		-> FROM division
		-> GROUP BY 1
		-> ORDER BY 2 DESC;
		
	+----------------+-------------+
	| Division_Name  | AVG(Rating) |
	+----------------+-------------+
	| Initmates      |      4.2863 |
	| General        |      4.1766 |
	| General Petite |      4.2111 |
	|                |      5.0000 |
	+----------------+-------------+
	4 rows in set (0.04 sec)
	```


- 부서 별 평균 펼점
	```mysql
	mysql> SELECT department_name AS 'DEPARTMENT NAME', AVG(rating) AVG_RATE
    -> FROM division
    -> GROUP BY 1
    -> ORDER BY 2 DESC;
	    
	+-----------------+----------+
	| DEPARTMENT NAME | AVG_RATE |
	+-----------------+----------+
	|                 |   5.0000 |
	| Bottoms         |   4.2888 |
	| Intimate        |   4.2801 |
	| Jackets         |   4.2645 |
	| Tops            |   4.1722 |
	| Dresses         |   4.1508 |
	| Trend           |   3.8151 |
	+-----------------+----------+
	7 rows in set (0.03 sec)
	```

- Trend 의 평점 3점 이하 리뷰
	```mysql
	mysql> SELECT
		->     rating,
		->     positive_feedback_count AS 'Positive Feedback Count',
		->     division_name AS 'Division Name',
		->     department_name AS 'Department name'
		-> FROM division
		-> WHERE department_name = "Trend" AND rating <= 3
		-> LIMIT 10;
	
	mysql> SELECT  rating,   positive_feedback_count AS 'Positive Feedback Count', division_name AS 'Division Name', department_name AS 'Department name' FROM division WHERE department_name = "Trend" AND rating <= 3 LIMIT 10;
	+--------+-------------------------+----------------+-----------------+
	| rating | Positive Feedback Count | Division Name  | Department name |
	+--------+-------------------------+----------------+-----------------+
	|      3 |                       0 | General        | Trend           |
	|      3 |                       3 | General Petite | Trend           |
	|      3 |                      10 | General        | Trend           |
	|      3 |                      17 | General Petite | Trend           |
	|      3 |                       4 | General Petite | Trend           |
	|      3 |                       2 | General Petite | Trend           |
	|      2 |                       6 | General        | Trend           |
	|      1 |                       0 | General        | Trend           |
	|      2 |                       2 | General        | Trend           |
	|      3 |                       0 | General        | Trend           |
	+--------+-------------------------+----------------+-----------------+
	10 rows in set (0.01 sec)
	```

- department_name이 "Trend"이고 rating이 3이하인 사람들의 연령대와 연령 조회하기
	```mysql
	mysql> SELECT
	->     FLOOR(age/10) * 10 AS AGEBAND,
	->     age AS AGE
	-> FROM division
	-> WHERE department_name = "Trend" AND rating <= 3;
	
	+---------+------+
	| AGEBAND | AGE  |
	+---------+------+
	|      60 |   66 |
	|      30 |   36 |
	|      60 |   66 |
	|      40 |   43 |
	|      30 |   34 |
	|      40 |   41 |
			...
	|      30 |   34 |
	|      50 |   57 |
	|      40 |   46 |
	+---------+------+
	41 rows in set (0.02 sec)
	```


- department_name이 "Trend"이고 rating이 3이하인 사람들의 연령대로 묶은 후, 연령대와 연령 조회하기
	```mysql
	mysql> SELECT
	    ->     FLOOR(age/10) * 10 AS AGEBAND,
	    ->     COUNT(*)
	    -> FROM division
	    -> WHERE department_name = "Trend" AND rating <= 3
	    -> GROUP BY 1
	    -> ORDER BY 2 DESC;
	+---------+----------+
	| AGEBAND | COUNT(*) |
	+---------+----------+
	|      40 |       13 |
	|      50 |       10 |
	|      30 |        8 |
	|      20 |        5 |
	|      60 |        4 |
	|      70 |        1 |
	+---------+----------+
	6 rows in set (0.03 sec)
	```

- Department 가 "Trend"인 사람들의 연령별 리뷰 수
	```mysql
	mysql> SELECT
    ->     FLOOR(age/10)*10 AS AGEBAND,
    ->     COUNT(*)
    -> FROM division
    -> WHERE department_name = "Trend"
    -> GROUP BY 1;
    
	+---------+----------+
	| AGEBAND | count(*) |
	+---------+----------+
	|      40 |       31 |
	|      30 |       36 |
	|      60 |       11 |
	|      50 |       25 |
	|      20 |       14 |
	|      80 |        1 |
	|      70 |        1 |
	+---------+----------+
	7 rows in set (0.03 sec)
	```



- 50대 3점 이하 Trend 리뷰
	```mysql
	mysql> SELECT
	    ->     *
	    -> FROM division
	    -> WHERE department_name = "Trend"
	    ->     AND rating <= 3
	    ->     AND AGE BETWEEN 50 AND 59
	    -> LIMIT 10;
	```

 
## 1-2. 평점이 낮은 상품의 주요 Complain

Department 별 평점이 낮은 주요 10개의 상품을 조회한 후, 해당 상품들의 리뷰를 살펴보자. 우선 Department 별 평점이 낮은 10 개 상품을 임시 테이블로 생성한다.
- Department Name, Clothing Name 별 평균 평점 게산
	```mysql
	mysql> SELECT
	    ->     department_name,
	    ->     clothing_id,
	    -> AVG(rating)
	    -> FROM division
	    -> GROUP BY 1, 2
	    -> LIMIT 10;
	    
	+-----------------+-------------+-------------+
	| department_name | clothing_id | AVG(rating) |
	+-----------------+-------------+-------------+
	| Intimate        |         767 |      4.5000 |
	| Dresses         |        1080 |      4.2941 |
	| Dresses         |        1077 |      4.0842 |
	| Bottoms         |        1049 |      4.3125 |
	| Tops            |         847 |      4.0000 |
	| Tops            |         858 |      3.9048 |
	| Dresses         |        1095 |      4.0520 |
	| Bottoms         |        1065 |      3.8750 |
	| Tops            |         853 |      3.6667 |
	| Jackets         |        1120 |      4.5000 |
	+-----------------+-------------+-------------+
	10 rows in set (0.05 sec)
	```

- Department 별 순위 생성
	```mysql
	mysql> SELECT
	    ->     *,
	    ->     DENSE_RANK() OVER(ORDER BY AVG_RATE DESC) RNK
	    -> FROM (
		    -> SELECT
		    ->     department_name,
		    ->     clothing_id,
		    ->     AVG(clothing_id) AVG_RATE
		    -> FROM division
		    -> GROUP BY 1, 2
	    -> ) A;
	
	+-----------------+-------------+-----------+-----+
	| department_name | clothing_id | AVG_RATE  | RNK |
	+-----------------+-------------+-----------+-----+
	| Jackets         |        1120 | 1120.0000 |   1 |
	| Dresses         |        1095 | 1095.0000 |   2 |
	| Dresses         |        1080 | 1080.0000 |   3 |
	| Dresses         |        1077 | 1077.0000 |   4 |
	| Bottoms         |        1065 | 1065.0000 |   5 |
							....
	| Tops            |           2 |    2.0000 | 1204 |
	| Intimate        |           1 |    1.0000 | 1205 |
	| Jackets         |           0 |    0.0000 | 1206 |
	+-----------------+-------------+-----------+------+
	1206 rows in set (0.05 sec)
	```

- 순위 1 ~ 10위인 데이터 조회
	```mysql
	mysql> SELECT
	    ->     *,
	    ->     DENSE_RANK() OVER(ORDER BY AVG_RATE DESC) RNK
	    -> FROM (
		->     SELECT
		->         department_name,
		->         clothing_id,
		->         AVG(clothing_id) AVG_RATE
		->     FROM division
		->     GROUP BY 1, 2
	    -> ) A;
	
	+-----------------+-------------+-----------+-----+
	| department_name | clothing_id | AVG_RATE  | RNK |
	+-----------------+-------------+-----------+-----+
	| Jackets         |        1120 | 1120.0000 |   1 |
	| Dresses         |        1095 | 1095.0000 |   2 |
	| Dresses         |        1080 | 1080.0000 |   3 |
	| Dresses         |        1077 | 1077.0000 |   4 |
	| Bottoms         |        1065 | 1065.0000 |   5 |
							....
	| Tops            |           2 |    2.0000 | 1204 |
	| Intimate        |           1 |    1.0000 | 1205 |
	| Jackets         |           0 |    0.0000 | 1206 |
	+-----------------+-------------+-----------+------+
	1206 rows in set (0.05 sec)
	```

- Department별 평균 평점이 낮은 10개의 상품
```mysql
mysql> CREATE TABLE stat AS
	-> SELECT * 
	-> FROM (
	->     SELECT
	->         *,
	->         DENSE_RANK() OVER(ORDER BY AVG_RATE DESC) RNK
	->     FROM (
	->         SELECT
	->             department_name,
	->             clothing_id,
	->             AVG(clothing_id) AVG_RATE
	->         FROM division
	->         GROUP BY 1, 2
	->     ) A
	-> ) A
	-> WHERE RNK <= 10;

mysql> SELECT clothing_id
	-> FROM stat
	-> WHERE department_name = 'Bottoms';
```


## 1-3. 연령별 Worst Department

리뷰 데이터를 기반으로 프로모션을 진행한다고 가정했을 때, 먼저 연령대별로 가장 낮은 점수를 준 Department를 구하고, 해당 Department의 할인 쿠폰을 발송하기호 한다. 연령별로 가장 낮은 점수를 준 Department가 구해지면, 연령별로 가장 낮은 점수를 준 Department에 혜택을 준다.

이를 구하기 위해서는 아래의 과정을 따라야한다.
1. 연령, Department별 가장 낮은 점수 계산
2. 생성한 점수를 기반으로 Rank 생성
3. Rank 값이 1인 데이터를 조회

우선 연령, Department별 가장 낮은 점수를 계산한다.

- 연령, Department별 가장 낮은 점수를 계산
	```mysql
	mysql> SELECT
	    -> department_name AS "DEPARTMENT NAME",
	    -> FLOOR(AGE/10) * 10 AS "AGEBAND",
	    -> AVG(rating) AS "AVG_RATING"
	    -> FROM division
	    -> GROUP BY 1, 2;
	+-----------------+---------+------------+
	| DEPARTMENT NAME | AGEBAND | AVG_RATING |
	+-----------------+---------+------------+
	| Intimate        |      30 |     4.2527 |
	| Dresses         |      30 |     4.1231 |
	| Dresses         |      60 |     4.2549 |
	| Bottoms         |      50 |     4.3581 |
	| Tops            |      40 |     4.1055 |
						....
	|                 |      30 |     5.0000 |
	|                 |      50 |     5.0000 |
	| Trend           |      70 |     2.0000 |
	+-----------------+---------+------------+
	55 rows in set (0.04 sec)
	```

- 생성한 점수를 기반으로 Rank 생성
	```mysql
	mysql> SELECT
	    -> *,
	    -> DENSE_RANK() OVER (ORDER BY AVG_RATING) AS RNK
	    -> FROM (
	    -> SELECT
	    -> department_name AS "DEPARTMENT NAME",
	    -> FLOOR(AGE/10) * 10 AS "AGEBAND",
	    -> AVG(rating) AS "AVG_RATING"
	    -> FROM division
	    -> GROUP BY 1, 2
	    -> ) A;
	    
	+-----------------+---------+------------+-----+
	| DEPARTMENT NAME | AGEBAND | AVG_RATING | RNK |
	+-----------------+---------+------------+-----+
	| Trend           |      70 |     2.0000 |   1 |
	| Dresses         |      90 |     3.2857 |   2 |
	| Trend           |      50 |     3.4000 |   3 |
	| Intimate        |      10 |     3.5714 |   4 |
						   ....
	| Intimate        |      80 |     5.0000 |  48 |
	| Trend           |      80 |     5.0000 |  48 |
	|                 |      20 |     5.0000 |  48 |
	| Jackets         |      10 |     5.0000 |  48 |
	|                 |      40 |     5.0000 |  48 |
	|                 |      30 |     5.0000 |  48 |
	|                 |      50 |     5.0000 |  48 |
	+-----------------+---------+------------+-----+
	55 rows in set (0.04 sec)
	```


- Rank 값이 1인 데이터를 조회
```mysql
SELECT * 
FROM (
	SELECT 
		*,
		ROW_NUMBER() OVER (PARTITION BY AGEBAND ORDER BY AVG_RATING) AS RNK 
	FROM ( 
		SELECT  
			department_name AS "DEPARTMENT NAME",
			FLOOR(AGE/10) * 10 AS "AGEBAND",
			AVG(rating) AS "AVG_RATING"
		FROM division 
		GROUP BY 1, 2) A
) A 
WHERE RNK = 1;

+-----------------+---------+------------+-----+
| DEPARTMENT NAME | AGEBAND | AVG_RATING | RNK |
+-----------------+---------+------------+-----+
| Intimate        |      10 |     3.5714 |   1 |
| Trend           |      20 |     3.7143 |   1 |
| Dresses         |      30 |     4.1231 |   1 |
| Trend           |      40 |     3.7097 |   1 |
| Trend           |      50 |     3.4000 |   1 |
| Trend           |      60 |     3.9091 |   1 |
| Trend           |      70 |     2.0000 |   1 |
| Tops            |      80 |     4.4400 |   1 |
| Dresses         |      90 |     3.2857 |   1 |
+-----------------+---------+------------+-----+
9 rows in set (0.05 sec)
```


## 1-4. Size Complain

컴플레인 중 Complain 내용의 다수가 Size와 관련된 문제이다. 먼저 전체 리뷰 내용 중 Size와 관련된 리뷰가 얼마나 되는지 확인해보자.

- Size와 관련된 리뷰 개수 확인하기
	```mysql
	mysql> SELECT
		->     SUM(CASE WHEN review_text LIKE "%SIZE%" THEN 1 ELSE 0 END) N_SIZE,
		->     COUNT(*) N_TOTAL
		-> FROM division;
	
	+--------+---------+
	| N_SIZE | N_TOTAL |
	+--------+---------+
	|   7388 |   23486 |
	+--------+---------+
	1 row in set (0.08 sec)
	```
