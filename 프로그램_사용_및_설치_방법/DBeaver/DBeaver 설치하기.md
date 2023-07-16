
# 1. DBeaver 설치하기

이번에는 M1 Mac에서 DBeaver를 설치해보도록 하겠습니다.

DBeaver는 MySQL Workbench와 같은 sqldeveloper로 MySQL만 사용가능한 MySQL Workbench와는 달리 여러 데이터베이스와 연결하여 사용할 수 있습니다. 

DBeaver는 [DBeaver 공식 설치 페이지](https://dbeaver.io/download/)인 https://dbeaver.io/download/ 에서 설치하거나 brew를 통해 간단하게 설치할 수 있습니다. 저는 brew를 사용하여 설치해보도록 하겠습니다.

---

<br>
<br>


## 1-1. brew 로 DBeaver 설치하기

<br>

- DBeaver 설치 명령어
  ```terminal
  brew install --cask dbeaver-community
  ```
  
  위의 명령어를 터미널에 입력하여 DBeaver의 설치가 완료가 되면 아래의 앱을 눌러 DBeaver를 실행할 수 있습니다.

<br>

- DBeaver 실행 앱
	![[Obsidians_Multi_Uses/프로그램_사용_및_설치_방법/DBeaver/이미지/DBeaver_설치하기_1.png]]
<br>
<br>

---

# 2. DBeaver 실행하기

<br>

- DBeaver 실행 시 알림
	![[Obsidians_Multi_Uses/프로그램_사용_및_설치_방법/DBeaver/이미지/DBeaver_설치하기_2.png]]
<br>
앱을 눌러 실행한 후 위와 같은 알림이 뜬다면 열기를 눌러줍시다.

---

<br>
<br>
<br>

## 3. DBeaver와 데이터베이스 연결하기

### 3-1. 코드? 버튼 클릭하기

![[Obsidians_Multi_Uses/프로그램_사용_및_설치_방법/DBeaver/이미지/DBeaver_설치하기_3.png]]


위 이미지의 빨간색 동그라미 안에 있는 코드? 버튼을 눌러줍시다.

<br>


### 3-2. 연결할 데이터베이스 선택하기

![[Obsidians_Multi_Uses/프로그램_사용_및_설치_방법/DBeaver/이미지/DBeaver_설치하기_4.png]]

MySQL 데이터베이스와 DBeaver를 연결해줄 것이기 때문에 MySQL 데이터베이스를 눌러주겠습니다.

<br>

### 3-3. 연결할 데이터베이스 정보 입력


![[Obsidians_Multi_Uses/프로그램_사용_및_설치_방법/DBeaver/이미지/DBeaver_설치하기_5.png]]

<br>

1번 박스에는 MySQL서버의 정보와 연결할 데이터베이스를 적어주고 2번 박스에는 MySQL 서버 연결을 위한 Username과 Password를 입력해줍시다. 입력을 마쳤다면 완료 버튼을 눌러주면됩니다.

<br>

### 3-4. 데이터베이스 연결 확인하기

- 연결 확인하기
	![[Obsidians_Multi_Uses/프로그램_사용_및_설치_방법/DBeaver/이미지/DBeaver_설치하기_6.gif]]



<br>

연결할 데이터베이스 정보를 입력한 후 완료 버튼을 누르면 필요한 경우 드라이버 설치 화면이 나올 수 있습니다. 드라이버까지 설치까지 완료되면 위와 같이 로컬호스트 3306 포트에서 실행되고 있는 MySQL 서버와 연결이 잘된것을 확인할 수 있습니다.


