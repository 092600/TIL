# 1. JRE와 JDK

Java를 처음 접하면 JRD(Java Runtime Environment)와 JDK(Java Develpment Kit)가 무엇인지 어떤 차이를 가지고 있는지 전혀 감이 잡히지 않는 경우가 있습니다. 이번에는 JRE와 JDK가 무엇인지 알아보도록 하겠습니다.

---

<br>

## 1-1. JRE (Java Runtime Environment)

JRE란 Java Runtime Environment의 약자로 자바 실행 환경이라는 의미를 가지고 있습니다. 자바 프로그램을 실행하기 위해서는 JVM 뿐 아니라 여러가지 라이브러리가 필요한데 JVM 및 여러 라이브러리를 포함하며 자바 프로그램의 실행을 지원하는 역할을 합니다.

<br>

## 1-2. JDK (Java Develpment Kit)

JDK는 Java Develpment Kit의 약자로 자바 개발키트라는 의미를 가지고 있습니다. JDK는 JVM 뿐만 아니라 javac, javadoc 등의 개발에 유용한 도구들을 포함하고 있어 자바 프로그램을 실행 및 개발하는데 사용됩니다.

<br>

## 1-3. JRE와 JDK의 공통점 및 차이점

<table>
  <tr>
    <td></td>
    <td>JRE (Java Runtime Environment)</td>
    <td>JDK (Java Develpment Kit)</td>
  </tr>
  <tr>
    <td>공통점 </td>
    <td colspan="2">자바 프로그램을 실행할 수 있으며 JVM을 포함한다.</td>
  </tr>
    <tr>
    <td>차이점 </td>
    <td>javac, javadoc 등이 없다.</td>
    <td>javac, javadoc 등의 개발에 유용한 도구들을 포함하고 있어 자바 프로그램을 개발할 수 있다.</td>
  </tr>
</table>


위의 표를 보면 알 수 있듯이 JRE은 자바 프로그램을 실행할 수 있지만 개발할 수 없으며, JDK는 자바 프로그램 실행 및 개발까지 가능하기 때문에 JRE는 JDK에 포함된다 (JRE⊂JDK) 라고도 할 수 있다.