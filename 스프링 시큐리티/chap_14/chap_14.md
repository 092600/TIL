<br>

- 스프링 시큐리티 인 액션을 읽고 내용을 정리한 파일입니다.

<br>
<br>
<br>

# OAuth2 : 리소스 서버 구현하기

이번에는 스프링 시큐리티로 리소스 서버를 구현할 것이다. OAuth2의 관점으로 보면 전에 구현한 다른 앱들과 마찬가지로 리소스 서버도 보호해야 하는 백엔드다. 클라이언트는 권한 부여 서버에서 발급한 토큰을 Authorization 헤더에 담아 리소스 서버에게 전송하고 리소스 서버는 Authorization 헤더에 담긴 토큰 값을 검증한다.

여기까지가 우리가 이전까지 공부한 내용을 간단하게 정리한 것이다. 하지만 리소스 서버가 Authorization 헤더에 담긴 토큰 값을 어떻게 검증하는지에 대한 내용은 따로 생각해보지 않았다. 이번 챕터 14에서 생각해보도록 하자.

## 리소스 서버가 토큰을 검증하는 방법

### 권한 부여 서버에게 직접 토큰의 검증을 요청하는 방법 

<img src="./images/2.png">

간단하게 생각해보자. 권한 부여 서버가 만든 토큰을 어떻게하면 쉽게 검증할 수 있을까 ? 당연하게도 권한 부여 서버에게 토큰 검증을 맡기면 된다. 1번부터 4번, 6번까지는 이전의 이미지와 같다. 이번에 추가된 것은 5번으로 리소스 서버는 클라이언트가 보내온 토큰을 권한 부여 서버에게 직접 검증을 요청한다. 이렇게 함으로써 권한 부여 서버는 신뢰하지 못하는 리소스 서버에게 간단한 토큰 검증을 위한 API만 만들면 리소스 서버가 토큰 검증을 할 수 있다. 권한 부여 서버에게 직접 토큰 검증을 요청하는 방법말고도 다른 방법에 대해서 알아보자.

### 권한 부여 서버가 만든 토큰의 정보가 담긴 데이터베이스를 사용하는 방법

<img src="./images/3.png">

이전보다 조금 과정이 늘어났지만 전혀 복잡하지 않다. 권한 부여 서버는 토큰을 만들어 클라이언트에게 전달해주기 전에 DB에 저장하는 3번 과정을 거친다. 또 리소스 서버는 클라이언트로 부터 토큰을 받고 6번 과정, 권한 부여 서버가 생성한 토큰의 정보를 저장한 데이터베이스에서 받은 토큰을 검색하는 과정, 을 거치기만 할 뿐이다. 이 방법은 두 서버가 데이터베이스를 공유하기 때문에 두 서버 간에 신뢰가 있는 경우에만 사용할 수 있지 않을까 생각한다.


### 암호화 서명을 이용하는 방법

<img src="./images/1.png">

이 방법은 토큰의 검증하기 위해 권한 부여 서버를 이용하거나 데이터베이스를 이용하지 않고 토큰을 암호화하는 방식을 사용한다. 권한 부여 서버는 권한 부여 서버는 토큰을 발행할 때 토큰에 서명을 한 후 클라이언트에게 토큰을 전달한다. 이후에 클라이언트가 리소스 서버에 요청과 함께 토큰을 전달할 때 이 서명이 맞는지를 검증한다. 이 방식에는 일반적으로 JWT 토큰을 사용한다. 

지금까지 간단하게 리소스 서버가 토큰을 검증하는 방법에 대해서 어떤게 있는지 알아봤다. 그러면 이제 리소스 서버를 구현해보자.

## 리소스 서버 구현하기 ( 1 )

지금 리소스 서버를 구현할 것인데 이 리소스 서버가 토큰을 검증하는 방법은 이전에 처음에 소개했던 권한 부여 서버에게 직접 토큰 검증을 부탁하는 방법으로 구현해보도록 하자. 참고로 이 방법은 시스템의 토큰이 간단한 형식일 때 적합하다.

- 리소스 서버 구현하기 전 다시보기

<img src="./images/2.png">

1. 사용자가 클라이언트에게 자격 증명 정보를 전달한다.
2. 클라이언트가 사용자의 자격 증명 정보를 권한 부여 서버에게 전달한다.
3. 권한 부여 서버가 자격 증명 정보를 검증하고 JWT 토큰을 만들어 클라이언트에게 전달한다.
4. 권한 부여 서버에게 받은 리소스 서버에게 JWT 토큰과 함께 요청을 보낸다.
5. 리소스 서버가 클라이언트에게서 받은 토큰을 검증하기 위해 권한 부여 서버의 토큰 검증 엔드포인트에 토큰 검증을 해달라는 요청을 보낸다.
6. 토큰이 검증되면 클라이언트에게 알맞은 응답을 해준다.

### 리소스 서버 종속성 추가하기
```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-oauth2-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Hoxton.SR1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

위의 종속성을 추가했다면 테스트용 엔드포인트를 생성하자.

- 리소스 서버 테스트용 엔드포인트
```java
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }
}
```

여기까지 했다면 잠시 생각해보자. 이 방식은 리스소 서버가 권한 부여 서버에게 토큰 검증을 매번 요청해야 한다는 점에서 잦은 호출 시, 권한 부여 서버에 불필요한 부하를 줄 수 있다는 단점이 있다. 또 네트워크가 불안할 때 토큰 검증이 제대로 되지 않을 수 있다는 사실 또한 서버 구현시에 참고해야한다. 또 구현하는 방식에 맞게 권한 부여 서버 또한 잘 구현되어있어야한다. 이번에는 chap_13을 사용하지만 이번 구현에 맞게 따로 수정을 해줘야 한다.

그러면 이제 리소스 서버를 구현하기에 앞서 권한 부여 서버부터 구현해보도록 하자.

### 권한 부여 서버 구현하기

이번 권한 부여 서버의 설정은 chap_13 프로젝트의 설정을 그대로 가져온 상태에서 상황에 맞게 약간의 코드만 수정할 것이다.
권한 부여 서버는 토큰 검증을 위한 /oauth/check_token 엔드포인트를 구현한다. 하지만 권한 부여 서버는 엔드포인트에 대한 모든 요청을 암시적으로 거부하기때문에 /oauth/check_token 엔드포인트를 호출하는 인증된 요청을 허용하기 위해서 권한 부여 서버의 AuthServerConfig 클래스에서 configure(AuthorizationServerSecurityConfigurer c) 메서드를 재정의해야한다. 이 메서드를 재정의하면 /oauth/check_token 엔드포인트를 호출하기 위한 조건을 설정할 수 있다.

- configure(AuthorizationServerSecurityConfigurer c) 메서드 재정의하기
```java
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        var service = new InMemoryClientDetailsService();

        clients.inMemory()
                .withClient("client") //클라이언트 아이디
                .secret("secret")  // 클라이언트 시크릿
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read") // 클라이언트의 스코프
                .and()
                .withClient("resourceserver") // 리소스 서버의 클라이언트 아이디
                .secret("resourceserversecret"); // 리소스 서버의 클라이언트 시크릿
    }
    
    // 재정의한 부분
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("isAuthenticated()");
    }
}
```

위의 재정의한 부분에서 security.checkTokenAccess("permitAll()");로 작성한다면 모든 요청이 이 엔드포인트에 접근하도록 할 수 있다. 이렇게 코드를 작성한 후에 권한 부여 서버를 실행하고 아래와 같이 권한 부여 서버에 요청을 보내보자.

- 권한 부여 서버로 요청
```bash
curl -v -X POST -u client:secret "http://localhost:8080/oauth/token?grant_type=password&username=sim&password=1234&scope=read"
```

- 권한 부여 서버로의 응답
```bash
{"access_token":"4fe1c1ee-d021-4c75-a2bd-f74364fd3f01","token_type":"bearer","refresh_token":"8b099303-01d1-4f9e-8f25-069af352d5c0","expires_in":43199,"scope":"read"}%
```
권한 부여 서버가 정상적으로 액세스 토큰을 반환해주는 것을 확인할 수 있다. 그러면 이제 이 액세스 토큰이 유효한 토큰인지 확인해보자.

- 리소스 서버에 요청
```bash
curl -X POST -u resourceserver:resourceserversecret "http://localhost:8080/oauth/check_token?token=4fe1c1ee-d021-4c75-a2bd-f74364fd3f01"
```

- 리소스 서버로의 응답
```bash
{"exp":1666827320,"user_name":"sim","authorities":["read"],"client_id":"client","scope":["read"]}%
```

유저 정보를 리턴해주는 것으로 보아 요청과 함께 보낸 액세스 토큰이 유효한 토큰이라는 것을 확인할 수 있다.

마지막으로 리소스 서버를 실행하기 전에 아래의 내용을 application.properties에 추가한 후 토큰을 Authorization 헤더에 담아 리소스 서버에 요청을 보내보자. 

- application.properties에 내용 추가하기
```properties
server.port=9090 // 리소스 서버 실행 포트를 9090포트로 변경

security.oauth2.resource.token-info-uri=http://localhost:8080/oauth/check_token
security.oauth2.client.client-id=resourceserver // 리소스 서버의 클라이언트 아이디 설정
security.oauth2.client.client-secret=resourceserversecret  // 리소스 서버의 클라이언트 시크릿 설정
```

요청을 보내기 전에 권한 부여 서버와 리소스 서버 모두 실행되어 있어야하며 두 서버가 실행할 때 충돌을 막기위해 리소스 서버의 실행 포트를 스프링부트 프로젝트의 기본 실행 포트인 8080포트에서 9090포트로 변경해줘야한다.

- 리소스 서버로 요청 보내기
```bash
curl -X GET -H "Authorization: bearer 4fe1c1ee-d021-4c75-a2bd-f74364fd3f01" "http://localhost:9090/hello"
```

- 응답
```bash
Hello!
```

클라이언트가 권한 부여 서버에게서 받은 토큰을 리소스 서버에게 Authorization 헤더에 담아 요청을 보내면 리소스 서버는 해당 토큰의 검증을 위해 권한 부여 서버에게 해당 토큰 값을 보내 검증을 요청한다. 검증이 완료되면 요청에 맞는 응답을 클라이언트에게 보내주도록 코드를 작성했다.

## 리소스 서버 구현하기 ( 2 )

이번에는 리소스 서버가 클라이언트에게서 받은 액세스 토큰을 검증할 때 권한 부여 서버와 리소스 서버가 공유하는 데이터베이스를 이용해보자.
이렇게 권한 부여 서버와 리소스 서버가 공유 데이터베이스를 이용하도록 애플리케이션을 구현하는 방식을 데이터 베이스 참조 아키텍쳐 스타일이라고 한다.
이 방식은 권한 부여 서버에게 리소스 서버가 직접 요청을 보내지 않기 때문에 네트워크 문제나 권한 부여 서버의 과부하 문제가 발생하지 않지만 데이터베이스에 많은 데이터 요청과 접근으로 인한 병목 현상이 생길 수 있다. 병목 현상과 관련해서는 나중에 따로 정리해보자.

또 리소스 서버를 구현하기에 앞서서 TokenStore에 대해서 알아보자. TokenStore는 권한 부여 서버와 리소스 서버 양쪽에 스프링 시큐리티로 토큰을 관리하는 객체를 나타내는 계약이다. 이전에 공부했던 SecurityContext와 유사한 개념이다. 이전에 인증 후 Authentication 객체를 SecurityContext에 저장했듯이 이번에는 인증 후에 Token을 TokenStore에 저장한다.

<img src="./images/4.png">

위의 이미지는 권한 부여 서버에서 유저를 인증하고 토큰을 TokenStore에 저장하는 과정을 나타낸 이미지이다. 이 이미지를 보니 이전에 구현했던 것들과의 차이점이 한 눈에 들어오지 않는가 ? 새로운 개념이 나왔지만 전혀 새로울 것이 없는 개념이다. 그렇다면 리소스 서버에서 TokenStore를 어떻게 사용하는지 이미지를 보고 이해해보자.

<img src="./images/5.png">

인증 필터는 이전과 마찬가지로 HTTP 요청을 가로챈다. 이후에 TokenStore가 요청과 함께 전달받은 토큰을 검증하고 사용자의 세부 정보를 검색한 후 사용자의 세부 정보는 나중에 권한 부여에 이용할 수 있도록 SecurityContext에 저장한다.

TokenStore는 이전의 InMemoryUserDetailsService와 비슷한 InMemoryTokenStore 객체도 존재하고 JdbcUserDetailsService와 비슷한 JdbcTokenStore 객체도 존재한다. 

우리는 권한 부여 서버와 리소스 서버가 공유하면서 사용할 데이터베이스에 토큰의 정보를 저장해야하기 때문에 JdbcTokenStore 객체를 사용할 것이다. JdbcTokenStore를 사용함에 있어 주의해야할 점은 액세스 토큰을 저장할 oauth_access_token 이름의 테이블과 갱신 토큰을 저장할 oauth_refresh_token 테이블이 있어야한다는 것이다. 이정도의 개념만 우선 정리하고 권한 부여 서버를 구현해보자.


### 권한 부여 서버 구현하기 ( 2 )

우리는 권한 부여 서버와 리소스 서버가 데이터베이스를 공유해 토큰을 검증하는 방식을 사용하도록 프로젝트를 만들 것이다. 권한 부여 서버는 이전 과정에서 사용했던 chap_14_auth를 그대로 사용하지만 이번 구현 목적에 맞게 설정을 조금 변경해줘야한다.

#### 권한 부여 서버 데이터베이스 세팅하기

```bash
CREATE TABLE IF NOT EXISTS `oauth_access_token` (
	`token_id` varchar(255) NOT NULL,
	`token` blob,
	`authentication_id` varchar(255) DEFAULT NULL,
	`user_name` varchar(255) DEFAULT NULL,
	`client_id` varchar(255) DEFAULT NULL,
	`authentication` blob,
	`refresh_token` varchar(255) DEFAULT NULL,
	PRIMARY KEY(`token_id`)
);


CREATE TABLE IF NOT EXISTS `oauth_refresh_token` (
	`token_id` varchar(255) NOT NULL,
	`token` blob,
	`authentication` blob,
	PRIMARY KEY(`token_id`)
);
```

토큰을 저장할 테이블은 스프링 시큐리티가 제공하는 기본 설정 테이블을 사용하자. oauth_access_token과 oauth_refresh_token 테이블을 위의 코드를 통해 생성하면 된다.

또 JdbcTokenStore 객체를 생성하기 위해서 DataSource 객체를 사용해야하는데 그러기 위해서 우리는 application.yml파일을 통해 DataSource 객체를 빈으로 등록해서 사용하도록 하자.

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/chap14 // chap14 데이터베이스를 사용함
    username: root // mysql 아이디
    password: 1234 // mysql 비밀번호

```

여기까지 잘 작성했다면 권한 부여 서버에서 데이터베이스 연결을 위한 설정은 끝이다. 이제 권한 부여 서버의 설정을 변경해보자.

#### 권한 부여 서버 세팅하기

권한 부여 서버는 chap_14_auth 프로젝트의 세팅을 사용하지만 권한 부여 서버가 토큰을 생성한 후 데이터베이스에 저장할 수 있도록 조금의 코드만 변경하도록 하자.

```java
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    // chap14_auth_2 프로젝트에서는 yml파일로 DataSource 객체를 빈으로 등록해서 사용한다.
    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read");
    }


    // auth 서버에서 우리가 빈으로 등록한 JdbcTokenStore 객체를 사용하도록 설정
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore());
    }

    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("isAuthenticated()");
    }

    // JdbcTokenStore 객체 빈으로 등록하기
    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource);
    }
}
```

여기까지 코드를 변경했다면 권한 부여 서버에 관한 설정은 마친 것이다. 액세스 토큰을 잘 반환하는지 테스트해보자.

- 권한 부여 서버에게 액세스 토큰 요청하기
```bash
curl -v -X POST -u client:secret "http://localhost:8080/oauth/token?grant_type=password&username=sim&password=1234&scope=read"
```

- 응답
```bash
{"access_token":"f4bc1389-e118-46a5-97f0-c7ee87cbaad1","token_type":"bearer","refresh_token":"a66ab484-1457-4c98-b643-d11c5a7b826a","expires_in":5843,"scope":"read"}%
```

이제 리소스 서버를 구현한 후 권한 부여 서버에서 반환한 액세스 토큰을 사용해보자.


### 리소스 서버 구현하기

이번 리소스 서버도 마찬가지로 chap_14를 그대로 사용할 것이지만 권한 부여 서버와 데이터베이스를 공유하도록 코드를 작성해줘야한다. 데이터베이스 설정부터 시작하도록 하자.

#### 리소스 서버 데이터베이스 세팅하기

당연하게도 데이터베이스를 공유해야하기때문에 위에서 작성한 데이터베이스 설정을 그대로 사용하면 된다. 

```properties
server.port=9090 // 권한 부여 서버와 리소스 서버가 실행되는 포트가 겹치지 않도록 리소스 서버의 실행 포트를 9090번 포트로 설정

spring.datasource.driver-class-name: com.mysql.cj.jdbc.Driver
spring.datasource.url: jdbc:mysql://localhost:3306/chap14
spring.datasource.username: root
spring.datasource.password: 1234
```

위의 설정으로 DataSource 객체를 빈으로 등록하는 것까지는 마쳤지만 위의 DataSource 객체를 사용해 JdbcTokenStore를 사용할 수 있도록 리소스 서버의 코드도 변경해줘야한다. 그리고 권한 부여 서버와 리소스 서버의 충돌을 막기 위해서 리소스 서버의 실행될 포트를 9090번 포트로 변경해줬다.

```java
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    // yml 파일로 생성한 DataSource 객체 의존성 주입
    @Autowired
    private DataSource dataSource;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 리소스 서버에 tokenStore 생성
        resources.tokenStore(tokenStore());
    }

    // DataSource 객체를 사용해 JdbcTokenStore 빈으로 등록하기
    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource);
    }
}
```

여기까지 코드 작성이 끝났다면 테스트를 위한 준비는 마친 것이다. 이제 리소스 서버와 권한 부여 서버를 실행한 후 권한 부여 서버에서 반환한 액세스 토큰을 사용해 /hello 엔드포인트를 호출해보자

-요청

```bash
curl -v -H "Authorization:Bearer f4bc1389-e118-46a5-97f0-c7ee87cbaad1" "http://localhost:9090/hello"
```

- 응답

```bash
Hello
```

정상적으로 /hello 엔드포인트에 대한 응답을 받아온 것을 확인할 수 있다. 이렇게 권한 부여 서버가 만든 액세스 토큰을 리소스 서버가 검증하고 사용하는 방법은 여러가지가 될 수 있다. 어떤 방법이 프로젝트의 성격에 맞는 방법인지 잘 생각해보고 좀 더 좋은 방법을 사용해보자.

