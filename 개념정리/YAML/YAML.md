# YAML 

YAML이란 XML, JSON과 같이 데이터를 표현하는 양식의 한 종류이다.
데이터를 포맷에 맞게 사용하는 이유는 다른 시스템과 데이터를 주고 받을 때 생기는 문제를 미연에 방지하기 위해서 사용된다.

YAML은 데이터를 사람이 쉽게 알아보기 위해서 데이터 직렬화양식을 사용하여 가독성이 좋으며 설정파일로 많이 사용된다.

<br>

## YAML 속뜻 알아보기

지금은 YAML이 'Yaml Ain't Markup Langugae'라는 뜻으로 'YAML은 마크업 언어가 아니다'라는 재귀적인 말장난 같은 이름을 가지고 있지만 이전에는 'Yet Another Markup Language'라는 이름을 가지고 있었다.

<br>

## YAML에 대해서 알아보기

스프링 프로젝트를 가지고 개발을 하면서 application.properties 혹은 application.yml 파일을 다룬 경험이 있을 것이다.
두 파일 모두 스프링 프로젝트에서 여러가지 설정을 위해서 사용된다.

- properties 파일

```properties
example.jdbc.url=127.0.0.1
example.jdbc.port=3306
example.jdbc.user=user
example.jdbc.password=password
```


- yml 파일 (YAML)

```yml
example
    jdbc
        url: 127.0.0.1
        port: 3306
        user: user
        password: password
```

properties 파일의 각 라인은 단일 구성이다. 그래서 위의 예처럼 example.jdbc 까지 계층이 같음에도 불구하고 계속해서 작성해줘야한다는 단점이 있다. 하지만 yml 파일의 경우 들여쓰기(indent) 를 통해서 데이터는 key-value 형식으로 작성한다.

properties와 yml 모두 기본적으로 key-value 형태로 데이터를 정의하지만 properties는 .으로 yml은 들여쓰기로 계층 구조를 표현하고 yml은  key-value 형태로 반드시 사이에 띄어쓰기가 들어가야한다는 특징이 있다.

properties 대신 yml을 사용하는 이유는 인간이 보기에 좀 더 계층 구조를 한눈에 파악하기 쉬운게 yml 형식이기 때문이다.


YAML 에 대해서 자세히 알고싶다면 YAML의 공식 홈페이지인 https://yaml.org/ 로 가보자.


