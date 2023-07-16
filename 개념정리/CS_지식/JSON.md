참고 
1. https://velog.io/@cil05265/XML%EA%B3%BC-JSON%EC%9D%98-%ED%8A%B9%EC%A7%95-%EA%B3%B5%ED%86%B5%EC%A0%90-%EC%B0%A8%EC%9D%B4%EC%A0%90
2. https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/JSON/parse

# JSON ( JavaScript Object Notation)

JSON은 데이터를 저장하거나 전송할 때 많이 사용되는 텍스트 기반의 교환 표준이다. JSON은 텍스트 기반으로 데이터를 저장하거나 전송하기 때문에 많은 프로그래밍 언어에서 데이터를 읽고 저장할 수 있다. 이제 JSON의 구조에 대해서 알아보자.

## JSON 구조

JSON은 Javascript 객체 리터럴 문법을 따르는 문자열이기 때문에 JSON 안에 Javascript의 기본 데이터 타입인 문자열, 숫자, 배열, 불리언 그리고 다른 객체를 포함할 수 있다. 또 JSON의 형태는 키(Key)와 값(Value)의 쌍으로 이루어져 있으며 키 : 값의 형태로 적을 수 있다. 

- JSON 기본 구조

```JSON
var ex1 = {
    "key1" : "value" 
}
```

JSON은 위와 같이 중괄호 안에 키와 밸류값을 콜론으로 연결시켜주면 된다. 자바스크립트의 데이터 기본형식인 문자열, 숫자, 불리언을 넣을 때는 위의 형식을 그대로 따라주면 된다.
또 여러 데이터를 나열할 경우에는 쉼표를 붙인 후에 나열해주면 된다.

- 여러 데이터 나열하기

```JSON
var ex2 = {
    "key1" : "value1",
    "key2" : 3,
    "key3" : true
}
```

지금까지는 키 값과 밸류값이 1대1로 대응할 때 JSON을 작성하는 방식이였다.
그렇다면 키값과 밸류값이 1대1로 대응하는 것이 아닌 한 개의 키값에 여러 개의 밸류 값이 대응되야 하는 경우에는 어떻게 작성해야할까 ?
바로 배열을 사용하면 된다.

- 배열 사용하기

```JSON
var ex3 = { 
    "key1" : "1", 
    "key2" : 2,
    "key3" : ["k1", "k2", "k3"],
}
```

위와 같이 배열을 사용한 경우에는 key값에 여러 개의 밸류 값이 있어 인덱스 방식으로 데이터를 가져올 수 있다.

- ex3에서 k2 가져오기

```JavaScript
console.log(ex3["key3"][1])
```

- 결과

```
k2
```

그렇다면 배열 안에 다시 키와 밸류 형태의 데이터를 넣으려면 어떻게 해야할까 ?

```JSON
var ex4 = { 
    "key1" : "1", 
    "key2" : 2,
    "key3" : ["k1", "k2", "k3"],
    "key4" : [
        {
            "k1" : "v1",
            "k2" : "v2",
        },
        "k3", "k4"
    ],
}
```

위처럼 다시 중괄호로 키와 밸류값을 묶어주면된다. 위의 ex4에서 key4의 k2의 밸류 값을 가져와보자.

- ex4에서 key4의 k2 값 가져오기

```JavaScript
ex4["key4"][0]["k2"];
```

- 결과
```
'v2'
```

여기까지 했다면 간단하게 JSON 객체를 만드는 방법에 대해서 알아봤다.
이제 JSON.parse() 메서드와 JSON.stringify() 메서드에 대해서 알아보자.

## JSON.parse() 와 JSON.stringify()

### JSON.parse()

JSON.parse()는 JSON 문자열을 분석해 JavaScript 값이나 객체를 생성한다. 
JSON.parse()를 어떻게 사용하는지 예제를 통해 이해해보자.

- JSON.parse() 예제

```JavaScript
var jsonStr = '{"key1":"value1", "key2":"value2"}'
var jsonObj = JSON.parse(jsonStr);

console.log(jsonObj);
```

- 결과 

```
{"key1":"value1", "key2":"value2"}
```

예제에서 보이는 것처럼 JSON.parse()메서드의 인자로 '{"key1":"value1", "key2":"value2"}'의 문자열이 들어갔는데 JSON.parse()가 해당 문자열을 JSON 객체로 변환한 후에 jsonObj 변수에 저장해준 것을 볼 수 있다. 
이렇게 JSON.parse()는 JSON 형태의 문자열을 JSON 객체로 변환해주는 역할을 한다.
만약 JSON 형태의 문자열이 아닌 값이 JSON.parse()의 인자로 들어간다면 에러가 발생할 것이다. 이점에 주의하자.

### JSON.stringify()

JSON.stringify()는 JSON 객체를 문자열로 바꿔주는 역할을 한다. 이번에도 예를보면서 이해해보자.

- JSON.stringify() 예제

```JavaScript
var jsonStr = '{"key1":"value1", "key2":"value2"}'
var jsonObj = JSON.parse(jsonStr);
var jsonStr2 = JSON.stringify(jsonObj);

console.log(jsonStr2);
```

- 결과
```
'{"key1":"value1","key2":"value2"}'
```

지금까지 간단하게 JSON의 구조와 JSON.parse(), JSON.stringify() 메서드 2개를 공부해봤다. 

마지막으로 JSON이 데이터의 저장 및 전송에 사용된다면 데이터를 저장하고 전송할 때 사용하는 XML과는 어떤 차이가 있는지 알아보고 마무리하자.

## JSON과 XML의 차이

1. XML은 마크업 언어로 만들어졌고 JSON은 자바스크립트 기반으로 만들어짐
2. XML은 부가정보가 많고 JSON은 부가 정보가 많지 않다.
3. XML은 SGML과 호환되어야 하지만 JSON은 텍스트기반이기 떄문에 운영체제와 언어에 독립적이다.
4. XML에 비해 JSON은 좀 더 쉽게 데이터를 저장하고 교환할 수 있다.
5. XML은 시작과 종료 태그를 사용하지만 JSON은 사용하지 않는다.
6. XML에 비해 JSON의 구문이 더 짧다.
7. XML은 배열을 사용할 수 없지만 JSON은 배열을 사용할 수 있다.
8. XML은 XML 파서로 파싱되지만 JSON은 자바스크립트 표준 함수인 eval() 함수로 파싱된다.
