참고한 글들
> 1. https://ko.wikipedia.org/wiki/%EB%A7%88%ED%81%AC%EC%97%85_%EC%96%B8%EC%96%B4
> 2. https://monsterspost.com/what-is-markup/
> 3. https://developer.mozilla.org/ko/docs/Web/HTML/Element/Heading_Elements#%EC%8B%9C%EB%8F%84%ED%95%B4%EB%B3%B4%EA%B8%B0

# 마크업 언어

마크업 언어에 대해서 공부해보자.


<br>
<br>


## 마크업 언어란 ?

<br>


마크업 언어는 태그 등을 이용해 문서나 데이터의 구조를 명기하는 언어의 한가지이다. 원래 마크업 언어에서 사용되는 태그는 원고의 교정부호와 주석을 표한하기 위해 사용했으나 점차 문서의 구조를 표현하는 역할을 하게되었다. 간단하게 말해서 document 안에 마크업 언어로 코드를 작성하면 이 document를 보는 장치(컴퓨터, 핸드폰)에 마크업 언어로 작성한 형식에 맞는 모습을 볼 수 있다.

<br>

마크업 언어는 일반적으로 데이터를 기술하는 정도로 사용되기때문에 프로그래밍 언어와는 구별된다는 특징을 가지고 있다. 하지만 모든 마크업 언어가 데이터를 기술하는데 사용되지 않는데 예를들어 XML(Extensible Markup Language)과 같은 경우 인간과 응용프로그램 혹은 응용프로그램 간에 데이터를 주고 받는데 사용된다.

<br>
<br>


## HTML 이란 ?

매우 많은 마크업 언어 중에서 우리에게 가장 익숙하고 쉬운 HTML(HyperText Markup Language)에 대해서 알아보자. HTML은 1990년대 초에 만들어져 인터넷의 첫번째 마크업 언어가 되었으며 대표적인 Descriptive Markup Language로 semantic markup으로도 알려져 있다.


<br>
<br>



## HTML 태그

<br>

HTML의 태그 이름은 꺾쇠 괄호(angle brackets)쌍 안에 있고 이 태그 이름은 다른 텍스트들과 구분된다는 특징을 가지고 있다. 또 시작 태그(start tag)와 끝 태그(end tag)라는 것이 존재한다. 끝태그는 시작태그와 달리 앞 꺾쇠 괄호 뒤에 /가 존재한다.  시작태그와 끝태그의 예를 한번 보고 가도록 하자.

<br>

- 시작태그와 끝태그

> 시작태그 : \<tagname\> \<br\> <br>
> 끝태그 : \</tagname\> \<br\>

<br>

HTML은 많은 태그를 가지고 있는데 각각의 태그들마다 다른 기능을 가지고 있다. 마지막으로 간단하게 HTML에서 사용되는 몇 개의 태그들이 어떤 기능을 가지는지 알아보.


### h1태그

<br>

```html
<h1>h1태그</h1>
```

<br>


h1태그는 구획 제목을 나타내는 태그 중 가장 높은 단계의 구획 제목을 나타낸다. 
구획 제목을 나타내는 태그는 h1부터 h6까지있고 h 뒤의 숫자가 커질수록 낮은 단계의 구획제목을 나타낸다.

<br>

\<h1\>태그에 대해서 더 공부하고 싶다면 https://developer.mozilla.org/ko/docs/Web/HTML/Element/Heading_Elements 로 가서 공부하길 바란다.

<br>

### a 태그

<br>

```html
<a href="https://www.naver.com">네이버 URL로 가기</a>
```

<br>

a태그는 a태그의 고유한 href 특성을 통해 다른 페이지나 같은 페이지의 어느 위치, 파일, 이메일 주소, 그 외 다른 URL로 연결할 수 있는 하이퍼링크를 만드는 태그이다. 

<br>

\<a\>태그에 대해서 더 공부하고 싶다면 https://developer.mozilla.org/ko/docs/Web/HTML/Element/a 로 가서 공부하길 바란다.

<br>

### br태그

<br>

```html
<p> O’er all the hilltops<br>
    Is quiet now,<br>
    In all the treetops<br>
    Hearest thou<br>
    Hardly a breath;<br>
    The birds are asleep in the trees:<br>
    Wait, soon like these<br>
    Thou too shalt rest.
</p>
```

<br>

br태그는  텍스트 안에 줄바꿈(캐리지 리턴)을 생성한다. 이 태그는 \<br\> 혹은 \<br/\> 로 사용할 수 있다는 특징을 가진다.


\<br\>태그에 대해서 더 공부하고 싶다면 https://developer.mozilla.org/ko/docs/Web/HTML/Element/br 로 가서 공부하길 바란다.


## HTML의 한계

HTML은 확장용이성, 문서확인용이성 등에서 한계가 존재한다. HTML이 가진 한계점에 대해서 알아보자.


<br>

### 확장용이성

같은 웹사이트에 경우 웹페이지들을 이루는 스타일은 같게하고 안의 내용만 다르게 하는 경우가 많다. 하지만 웹 사이트에 접속할 경우 달라지는 웹페이지 내의 내용과 웹피이지들간에  같기때문에 다시 불러오지 않아도 괜찮은 스타일 정보까지 불러와야한다. 또 A 사이트와 B 사이트 간에 HTML로 된 문서를 주고받을때, 서로 다른 스타일때문에 자신의 스타일에 맞도록 고친 후에야 주고받은 HTML을 사용할 수 있다는 점에서 HTML은 확장에 용이하지 않다는 한계가 존재한다.

<br>
<br>

### 문서확인용이성

HTML은 태그와 여러가지 속성들을 사용해서 웹페이지가 어떻게 어떻게 보일 것인가에 대한 스타일 정보를 주로 담고 있다. 그러다보니 웹 페이지들이 서로 다른 모양을 가지게 되었고 그 속에서 핵심 내용을 찾아내기 어려워져 원하는 웹문서를 찾기가 어려워졌다라는 한계점이 있다.