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
        <name>sim</name>
        <mathScore>100</mathScore>
        <korScore>95</korScore>
    </student>
    <student>
        <number>2</number>
        <name>kim</name>
        <mathScore>90</mathScore>
        <korScore>90</korScore>
    </student>
    <student>
        <number>3</number>
        <name>park</name>
        <mathScore>95</mathScore>
        <korScore>100</korScore>
    </student>
</students>
```
위와 같이 xml 문서을 만들었다. xml 문서의 맨 처음에는 반드시 xml 의 버전, 인코딩 방식 등을 선언해줘야 한다. 위 xml 문서에는 <?xml version="1.0" encoding="euc-kr"?>에 해당하는데 xml 1.0 버전을 사용하며 euc-kr 로 인코딩하는 xml 문서라는 것을 의미한다. 

xml 문서의 선언을 할 때는 version, encoding 순으로 적어야 하며 인코딩 방식은 지정하지 않으면 기본값으로 UTF-16 혹은 UTF-8로 지정된다.

이제 students 태그를 보자. students 태그와 같이 요소들의 최상위 요소를 root elements 라고 부른다. xml은 반드시 하나의 root elements는 가져야한다는 특징을 가진다. 

그러면 이제 elements의 이름을 작성할 때 지켜야할 규칙에 대해서 알아보자.
elements의 이름은 문자, 밑줄이 가능하고 이름의 중간에는 문자, 밑줄 외에 숫자, 하이픈, 마침표가 가능하다. 또 이름의 중간에 공백이 없어야하며 대소문자를 구분해야한다.



 








