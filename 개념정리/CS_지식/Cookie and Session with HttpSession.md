# HttpSession에 대한 궁금증

## 작성배경
개발바닥이라는 유튜브 채널의 신입 면접 영상?을 보던 중에 로그인 과정 중에서 세션을 사용할 때 어떻게 user라는 이름의 세션이 모든 유저들의 정보를 다르게 저장하고 사용하는가? 에 대한 질문을 듣고 갑자기 궁금해져서 쓰게되었다.


```java
httpSession.setAttribute("user", new SessionUser(user));
```
```java
public class SecurityConfig extends     WebSecurityConfigurerAdapter {
  @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 생략
        .and()
            .formLogin()
            .loginPage("/accounts/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .loginProcessingUrl("/api/v4/accounts/login")
            .defaultSuccessUrl("/")
        // 생략
}
```

<br>


```java
@Service
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {
    private final UserService userService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final HttpSession httpSession;

    // 어디로 리턴되는가 .. > 시큐리티 session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByUserEmail(email);
        if (user != null){
            httpSession.setAttribute("user", new SessionUser(user));
            return new SecurityUser(user);
        }
        return null;
    }
}
```
위의 작성 코드는 내 개인 프로젝트에서 스프링시 큐리티를 사용해 로그인을 구현할 당시 작성한 코드다. 위의 코드는 받은 이메일이 존재하고 해당 이메일의 패스퉈드가 DB의 패스워드와 일치한다면 해당 유저를 세션 유저라는 세션용 유저 클래스로 만들어서 user라는 이름의 세션에 저장하도록 코드가 작성되어있다.

그렇다면 여기서 어떻게 "user"라는 이름의 세션에 각각 다른 유저 정보들이 저장되서 사용될텐데 유저가 사용하는 브라우저에서 정보를 사용하기 위해 세션에 접근할 때 그 유저에 맞는 세션을 사용할 수 있는 것일까 ?

이를 알기위해 HTTP의 특징과 쿠키, 세션에 대해서 자세하기 알아보도록 하자.

<br>
<br>
<br>

# HTTP의 특징
HTTP는 비연결성(Connectionless)과 무상태(Stateless)의 특징을 가진다.

## 비연결성이란 ?
클라이언트는 서버에 요청을 보내고 응답을 받는다. 이렇게 요청을 보내고 응답을 받는 과정에서 서버와 클라이언트는 연결되어있는데 TCP/IP에서는 서버가 클라이언트에게 요청을 받아 해당 요청에 맞는 응답을 주고 난 후에도 연결을 유지하는데 이렇게 지속적이게 연결을 유지하는 과정에서 불필요한 리소스가 낭비되는 경우가 있다.

TCP/IP와는 다르게 HTTP는 기본적으로 연결을 유지하지 않는 비연결성이라는 특징을 가지고 있는데 비연결성이란 클라이언트가 서버에 요청을 보내고 서버가 클라이언트에게 응답을 보내면 연결을 끊어버리는 것을 말한다. 이렇게 요청과 응답을 보내고 바로 연결을 끊는 HTTP의 특징은 속도가 매우 빠르며 불필요하게 연결을 유지하여 리소스가 낭비되는 것을 방지하는 효과를 가진다.

<br>
<br>

## 무상태 프로토콜(Stateless)이란 ?
무상태 프로토콜이란 서버가 클라이언트의 정보를 보존하지 않는다는 것이다.
예를들어 내가 어제 서버에게 요청을 보내고 응답을 받았어도 서버는 내가 어제보낸 요청을 기억하지 못한다. 어제와 오늘이 아니라 단 몇분 전이라고 해도 말이다.

하지만 이렇게 모든 것을 무상태로 설계했을때 발생하는 문제도 있다. 바로 로그인과 같이 계속 유지되어야 하는 정보가 있는 경우에는 Stateful하게 설계해야한다. 여기서 Stateful하게 설계되어야 한다는 것은 로그인을 하고난 후에 게속해서 서버가 내가 로그인했다는 것을 알고 있어야 한다는 것이다.

<br>

> HTTP의 Stateless와 Stateful에 대해서 자세히 알고싶다면 https://ojt90902.tistory.com/642 링크를 참조하자.

<br>
<br>
<br>

# 쿠키와 세션을 사용하는 이유
내가 로그인을 했음에도 불구하고 서버가 내가 로그인했다는 것을 기억하지 못한다면 사용자는 유저정보가 필요한 로직을 사용할 때마다 아이디와 비밀번호를 입력해야 할 것이다. 하지만 이러한 HTTP의 비연결성과 무상태의 한계를 극복하기 위해서 쿠키와 세션을 사용하는데 쿠키가 무엇인지부터 알아보도록 하자.

<br>
<br>
<br>

# 쿠키란 ?
쿠키란 클라이언트 로컬에 키와 값의 형태로 저장되는 데이터 파일로 사용자 인증이 유효한 시간을 설정할 수 있고 해당 시간이 안에는 브라우저가 연결을 종료하더라도 연결이 유지된다는 특징이 있고 클라이언트에 300개까지 쿠키저장 가능하고 하나의 도메인당 20개의 값만 가질 수 있으며 하나의 쿠키값은 4KB까지 저장가능하다.

쿠키를 사용함으로써 얻을 수 있는 장점으로는 쿠키는 클라이언트의 로컬에 저장되기 때문에 서버의 리소스에 부담을 주지않는다는 장점이 있다. 하지만 하나의 도메인당 20개의 값만 가질 수 있고 크기제한이 있는 만큼 세션과 쿠키를 적절하게 사용하는 것이 좋지 않을까하는 생각이 든다.

<br>
<br>


# 세션이란 ?
이번 글의 메인인 세션이다. 세션이란 쿠기를 기반으로 하지만 사용자 정보 파일을 브라우저에 저장하는 쿠키와 달리 서버에서 관리한다. 하지만 **서버에서는 클라이언트를 구분하기 위해 세션ID를 부여하여 웹 브라우저가 서버에 접속해서 브라우저를 종료할 때까지 인증상태를 유지한다.**

자, 이제 어떻게 서버가 "user"라는 사용자마다 같은 이름의 세션을 사용하면서도 클라이언트 별로 다른 정보를 주는 것이 가능한가에 대해서 알게되었다. 세션은 쿠키와 다르게 서버에서 관리하지만 세션별로 세션ID를 부여해 클라이언트를 구분하고 있었다.

## 세션의 동작 방식

1. 클라이언트가 서버에 접속 시 세션 ID를 발급 받음
2. 클라이언트는 세션 ID에 대해 쿠키를 사용해서 저장하고 가지고 있음
3. 클라리언트는 서버에 요청할 때, 이 쿠키의 세션 ID를 같이 서버에 전달해서 요청
4. 서버는 세션 ID를 전달 받아서 별다른 작업없이 세션 ID로 세션에 있는 클라언트 정보를 가져와서 사용
5. 클라이언트 정보를 가지고 서버 요청을 처리하여 클라이언트에게 응답

위의 세션의 동작방식을 보면 서버에서 세션을 ID를 발급받아 해당 세션ID의 값을 쿠키로 저장하고 요청을 보낼 때 세션 ID를 같이 서버에 전달해 요청하고 서버는 해당 세션 ID를 가지는 세션의 클라이언트 정보를 사용한다.

![[Obsidians_Multi_Uses/개념정리/CS_지식/이미지/세션아이디_쿠키.png]]

사실 JESSIONID라는 쿠키가 있는 것을 개발을 하면서 아래와 같이 쿠키가 있는 것을 본적이 있었는데 이렇게 몰래 내 개발을 도와주다니 뭔가 감동이다. 뭔가 소름돋는 느낌.. 이렇게 어떻게 같은 key값을 가지는 세션의 정보를 사용자에게 알맞게 사용할 수 있었는지 알수 있었다

<br>
<br>
<br>

## 참고 URL

> 1. https://interconnection.tistory.com/74, 
> 2. https://ojt90902.tistory.com/642 
