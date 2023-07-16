# XML ( Extensible Markup Language )

XML에 대해서 알아보기 전 마크업 언어가 무엇인지 궁금하다면 <a>마크업 언어 알아보기</a>로 가서 마크업 언어에 대해서 공부해보자.

## XML 속뜻 알아보기

- Extensible : 확장성이 있음 <br>
- Markup Language : 태그 등을 이용하여 문서나 데이터의 구조를 명기하는 언어의 한 가지

## XML이란 ?

XML(eXtensible Markup Language)이란 W3C에서 개발된 다목적 마크업 언어이다.
XML은 인간과 응용프로그램 혹은 응용프로그램 간 데이터를 쉽게 교환할 수 있도록 해 HTML이 가진 한계와 SGML이 가진 복잡함을  해결하기 위한 목적으로 만들어졌으며 HTML처럼 데이터를 보여주는 목적이 아닌 데이터를 저장하고 전달할 목적으로만 만들어졌다.

XML은 HTML과 달리 문서의 내용에 관련된 태그를 사용자가 직접 정의할 수 있으며 직접 정의한 태그를 다른 사람들이 사용할 수 있다는 특징을 가진다. 

XML 태그는 다른 마크업 언어들과 같이 시작태그와 끝태그를 가진다. 또 태그의 이름은 대소문자를 구별하며 태그를 열었다면 반드시 닫아야한다. 또 태그는 오버랩될 수 없으며 Root 태그는 하나만 존재해야 한다는 특징을 가진다.

## XML 파일 작성하며 알아보기

지금까지 간단하게 XML이 가진 특징에 대해서 알아봤으니까 XML 파일 작성하고 웹브라우저로 XML파일을 보고

### XML 파일 작성하기

확장자명을 xml으로 하는 XML 문서를 만든 후에 XML에 여러 태그를 작성해보며 공부해보자.

```xml
<?xml version="1.0" encoding="euc-kr"?>
<students>
    <student>
        <number>1</number>
        <name age="23">sim</name>
        <mathScore>100</mathScore>
        <korScore>95</korScore>
    </student>
    <student>
        <number>2</number>
        <name age="21">kim</name>
        <mathScore>90</mathScore>
        <korScore>90</korScore>
    </student>
    <student>
        <number>3</number>
        <name age="25">park</name>
        <mathScore>95</mathScore>
        <korScore>100</korScore>
    </student>
</students>
```

위와 같이 xml 문서을 만들었는데 이 xml 파일은 XML 디렉토리 안의 studentsXML.xml 이다.

#### XML 문서의 선언

xml 문서의 맨 처음에는 반드시 xml 의 버전, 인코딩 방식 등을 선언해줘야 한다. 위 xml 문서에는 <?xml version="1.0" encoding="euc-kr"?>에 해당하는데 xml 1.0 버전을 사용하며 euc-kr 로 인코딩하는 xml 문서라는 것을 의미한다. 

xml 문서의 선언을 할 때는 version, encoding 순으로 적어야 하며 인코딩 방식은 지정하지 않으면 기본값으로 UTF-16 혹은 UTF-8로 지정된다.

-  XML 문서 선언시 규칙
1. XML 파일의 맨 처음에 위치해야 한다.
2. version, encoding, standalone 순으로 작성해야 한다.
3. encoding 방식을 지정하지 않으면 UTF-16 혹은 UTF-8로 지정된다.


#### XML 요소 ( Elements ) 알아보기

students 태그를 보자. students 태그와 같이 요소들의 최상위 요소를 root elements 라고 부르는데 xml은 반드시 하나의 root elements는 가져야한다.

그러면 이제 elements(요소)의 이름을 작성할 때 지켜야할 규칙에 대해서 알아보자.
요소의 이름은 문자, 밑줄이 가능하고 이름의 중간에는 문자, 밑줄 외에 숫자, 하이픈, 마침표가 가능하다. 또 이름의 중간에 공백이 없어야하며 대소문자를 구분해야한다.

#### XML 속성 ( Attribute )

마지막으로 xml에서 요소의 속성(Attribute)를 설정해 줄 수 있다. 속성은 요소와 결합된 이름="값" 형태의 쌍을 의미하며 속성을 통해 요소의 텍스트가 아닌 방식으로 요소를 표현할 수 있어 유용하다. 
요소의 속성을 설정해 줄 때도 지켜야할 규칙이 존재하는데 이 규칙들에 대해서 알아보자.

- 요소의 속성 설정 규칙
1. 시작 태그에만 지정해야한다.
2. 속성이름과 속성 값이 하나의 쌍을 이뤄야한다.
3. 속성이 여러 개 일 때 공백으로 분리하며 순서는 무의미하다.
4. 속성이름을 설정할 때는 요소의 이름을 설정할 때와 동일한 규칙을 따른다.

xml 문서를 웹브라우저를 통해 읽어보는 것으로 마무리하자.

## 웹브라우저로 XML 문서 열기

지금부터 웹브라우저로 XML 문서를 열어볼 것인데 이 때 사용할 XML 문서는 위에서 작성한 studentsXML 문서를 사용할 것이다.

![[Obsidians_Multi_Uses/개념정리/CS_지식/이미지/XML_1.png]]

위와같이 xml 문서을 오른쪽 마우스로 누른 후 웹브라우저로 열어보자.
웹브라우저로 아래의 이미지와 같이 나왔다면 성공이다.

![[Obsidians_Multi_Uses/개념정리/CS_지식/이미지/XML_2.png]]

지금까지 XML에 기본적인 개념에 대해서 공부해봤고 xml 문서를 작성하고 웹브라우저로 열어보았다.
아직 XML에 대해 자세하게 아는 것은 아니기 때문에 새롭게 공부하는 내용이 생길때마다 추가하도록 하겠다.

