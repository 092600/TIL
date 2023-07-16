
# 몽고디비(MongoDB)란 ?

몽고디비란 NoSQL 데이터베이스로 성능과 확장성이 SQL에 비해 좋으며 유연하고 복잡성이 낮은 것이 특징입니다.

몽고디비는 고정적인 스키마를 가지는 SQL과는 다르게 고정적인 스키마를 가지지 않기 때문에 다양한 형태의 데이터를 가질 수 있으며, 스키마의 구조가 바뀌어도 데이터를 저장하는데 큰 문제가 발생하지 않으며 Read/Write 성능이 좋아 많은 양의 데이터를 저장하고 처리하는데 용이합니다. 하지만 많은 인덱스를 사용 시, 많은 메모리가 사용되며 데이터를 저장하는데 많은 자원이 소모된다는 것과 데이터 손실 가능성이 크다는 단점이 있습니다.

# 몽고디비 설치하기

우분투에서 몽고디비를 설치하는 방법은 몽고디비 도큐먼트인 [Install MongoDB Community Edition on Ubuntu](https://www.mongodb.com/docs/manual/tutorial/install-mongodb-on-ubuntu/)에서 확인할 수 있으며, 공식 도큐먼트에 적혀있는 몽고디비 설치과정을 하나씩 따라해보도록 하겠습니다.

MongoDB는 64-bit 만을 지원하며 어떤 버전이 자신의 플랫폼에 맞는지 몽고디비 도큐먼트에서 [Production Notes](https://www.mongodb.com/docs/manual/administration/production-notes/#std-label-prod-notes-supported-platforms)에서 확인해볼 수 있습니다.


- MongoDB Platform Support Matrix
	![[Obsidians_Multi_Uses/프로그램_사용_및_설치_방법/몽고디비/이미지/몽고디비(MongoDB)_설치하기_1.png]]


우분투 20.04 LTS 버전, ARM64 아키텍처에 몽고디비를 설치할 것이기 때문에 우분투 20.04 LTS 버전을 지원하는 MongoDB 6.0 Community Edition을 설치해보도록 하겠습니다. 참고로 몽고디비는 이와같은 플랫폼에서 몽고디비 6.0버전, 5.0버전과 4.4버전을 지원한다고 합니다.

그렇다면 이제 본격적으로 몽고디비를 설치해보도록 하겠습니다.

## 1. package management system 을 위한 public key 를 import하기

https://www.mongodb.org/static/pgp/server-6.0.asc에 있는 MongoDB public GPG Key를 import해주기 위해서 아래의 명령어를 터미널에 입력해줘야합니다.

- MongoDB public GPG Key Import 명령어
```
wget -qO - https://www.mongodb.org/static/pgp/server-6.0.asc | sudo apt-key add -
```

- 명령어 실행화면
	![[Obsidians_Multi_Uses/프로그램_사용_및_설치_방법/몽고디비/이미지/몽고디비(MongoDB)_설치하기_2.gif]]

위의 화면처럼 명령어 입력 후 터미널에 OK가 출력되어야합니다.

만약 gnupg is not installed라는 에러가 발생했다면 아래의 명령어를 입력해주도록 합시다.

- gnupg is not installed 에러 발생시 명령어
```
sudo apt-get install gnupg
```

위의 명령어 입력 후 gnupg가 설치되었다면 다시 한번 아래의 명령어를 터미널에 입력해줍시다.

```
wget -qO - https://www.mongodb.org/static/pgp/server-6.0.asc | sudo apt-key add -
```

## 2. 몽고디비를 위해 리스트 생성하기

우분투 버전에 맞는 리스트 파일을 만들어 줍시다. 20.04 LTS 버전의 경우 /etc/apt/sources.list.d/mongodb-org-6.0.list 파일을 생성해줘야합니다.

- 리스트 파일 생성하기 명령어
```
echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/6.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-6.0.list
```

- 명령어 실행화면
	![[Obsidians_Multi_Uses/프로그램_사용_및_설치_방법/몽고디비/이미지/몽고디비(MongoDB)_설치하기_3.gif]]


위와같이 리스트 파일을 생성해주었다면 다음 단계로 넘어가도록 합시다.

## 3. 로컬 패키지 데이터베이스 재배치

- local package database를 재배치하기위한 명령어
```
sudo apt-get update
```

- 명령어 실행화면
	![[Obsidians_Multi_Uses/프로그램_사용_및_설치_방법/몽고디비/이미지/몽고디비(MongoDB)_설치하기_4.gif]]




## 4. 몽고디비 패키지 설치하기

몽고디비의 가장 최신버전을 다운받기위해서는 아래와 같은 명령어를 터미널에 입력해주먄 됩니다.

- 최신버전의 몽고디비 설치하기
```
sudo apt-get install -y mongodb-org
```

- 특정버전(6.0.3)의 몽고디비 설치하기
```
sudo apt-get install -y mongodb-org=6.0.3 mongodb-org-database=6.0.3 mongodb-org-server=6.0.3 mongodb-org-mongos=6.0.3 mongodb-org-tools=6.0.3
```

- 명령어 실행화면
	![[Obsidians_Multi_Uses/프로그램_사용_및_설치_방법/몽고디비/이미지/몽고디비(MongoDB)_설치하기_5.gif]]

이렇게 몽고디비 설치를 마쳤다면 마지막으로 몽고디비를 실행해봅시다.


# 몽고디비 사용하기
## 실행하기

몽고디비는 터미널에 아래의 명령어를 입력하여 실행할 수 있습니다.

- 몽고디비 실행 명령어
```
sudo systemctl start mongod
```

위의 명령어로 잘 실행되었는지 확인하기 위해서 터미널에 아래의 명령어를 입력해봅시다.

- 몽고디비 상태확인 명령어
```
sudo systemctl status mongod
```

- 명령어 실행화면
	![[Obsidians_Multi_Uses/프로그램_사용_및_설치_방법/몽고디비/이미지/몽고디비(MongoDB)_설치하기_6.gif]]

위의 화면처럼 몽고디비가 Activate되었다면 성공입니다.

## 접속하기

몽고디비가 Activate 상태로 정상적으로 실행되었다면 터미널에 아래의 명령어를 입력하여 몽고디비 서버에 접속해봅시다.

- 몽고디비 서버 접속하기 명령어
```
mongosh
```

- 명령어 실행화면
	![[Obsidians_Multi_Uses/프로그램_사용_및_설치_방법/몽고디비/이미지/몽고디비(MongoDB)_설치하기_7.gif]]

접속한 몽고디비 서버에서 나오기위해서는 exit를 입력해줍시다.

## 종료하기

Activate 상태인 몽고디비 서버를 종료하기 위해서 터미널에 아래의 입력해줍시다.

- 몽고디비 서버 종료 명령어
```
sudo systemctl stop mongod
```

- 명령어 실행화면
	![[Obsidians_Multi_Uses/프로그램_사용_및_설치_방법/몽고디비/이미지/몽고디비(MongoDB)_설치하기_8.gif]]

위의 화면을 보면 명령어 입력 후 몽고디비 서버가 inactivate 상태가 된 것을 확인할 수 있습니다.
