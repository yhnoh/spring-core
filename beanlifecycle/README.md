# 빈 라이프 사이클

Maven Repository

[https://mvnrepository.com/artifact/org.springframework/spring-context](https://mvnrepository.com/artifact/org.springframework/spring-context)

- 스프링 컨테이너가 초기화 및 종료할 때 빈 객체도 초기화 및 종료를 한다.
- 컨테이너 초기화 → 빈 객체의 생성, 의존 주입, 초기화
- 컨테이너 종료 → 빈 객체의 소멸
- 빈 라이프 사이클 :  객체 생성 → 의존 주입 → 초기화 → 소멸
- 스프링은 빈 라이프 사이클을 개발자가 제어할 수 있도록 여러가지 인터페이스 제공 (인터페이스, 메소드, 어노테이션)
- 빈 기본 전략은 싱글톤
- 빈 기본 전략을 프로토 타입으로 변경시 빈 호출시마다 객체 생성한다. 하지만 개발자가 직접 소멸 메소드를 호출해야한다.

시나리오 

1. 클라이언트가 서버에게 메시지를 전송하는 프로그램을 만들어 달라는 요청
2. 메시지를 전송할때 마다 클라이언트는 서버와 통신연결을 해야하고, 메시지 전송 이후 연결을 종료시켜 주어야 한다.

공통

```java
public class Main {
    public static void main(String[] args) {
        //1. 컨테이너 초기화 -> 빈 객체의 생성, 의존 주입, 초기화
        AnnotationConfigApplicationContext ctx   = new AnnotationConfigApplicationContext(AppConfig.class);

        //2. 빈 객체를 구해서 사용
        Client client = ctx.getBean("client", Client.class);
        client.send("안녕하세요.");

        //컨테이너 종료 -> 빈 객체 소멸
        ctx.close();
    }
}
```

인터페이스

```java
public class AppConfig {

    @Bean
    public Client client(){
        Client client = new Client();
        client.setHost("111.111.111.111");
        return client;
    }
}

/**
 * Client 클래스
 * 초기화 일때 서버에게 연결을 하고
 * 소멸할때 서버와의 연결을 끝낸다.
 *
 * 인터페이스를 활용한 빈 초기화,소멸
 * 초기화 : InitializingBean
 * 소멸 : DisposableBean
 *
 * 불편한점 : 계속해서 인터페이스를 상속 받아야한다.
 */
public class Client implements InitializingBean, DisposableBean {

    private String host;

public Client(){
        System.out.println("Client 생성자 호출");
    }
    public void setHost(String host){
        this.host = host;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("connect");
    }

    public void send(String msg){
        System.out.println("host : " + host + " msg : " + msg);
    }
    @Override
    public void destroy() throws Exception {
        System.out.println("close");
    }

}
```

메소드

```java
public class AppConfig {

    @Bean(initMethod = "connect", destroyMethod = "close")
    public Client client(){
        Client client = new Client();
        client.setHost("111.111.111.111");
        return client;
    }
}

/**
 * Client 클래스
 * 초기화 일때 서버에게 연결을 하고
 * 소멸할때 서버와의 연결을 끝낸다.
 *
 * 메소드를 활용한 빈 초기화,소멸
 * @Bean(initMethod = "connect", destroyMethod = "close")
 * 초기화 : initMethod = "connect"
 * 소멸 : destroyMethod = "close"
 *
 * 직접 구현이 아닌, 외부에서 제공 받을 때 사용
 *
 * 불편하점 : Client 클래스 내에서 어떤게 초기화 메소드이고 소멸 메소드인지 알수 없다.
 */
public class Client {

    private String host;
		public Client(){
        System.out.println("Client 생성자 호출");
    }
    public void setHost(String host){
        this.host = host;
    }
    public void connect() throws Exception {
        System.out.println("connect");
    }

    public void send(String msg){
        System.out.println("host : " + host + " msg : " + msg);
    }
    public void close() throws Exception {
        System.out.println("close");
    }

}
```

어노테이션

```java
public class AppConfig {

    @Bean
    public Client client(){
        Client client = new Client();
        client.setHost("111.111.111.111");
        return client;
    }
}

/**
 * Client 클래스
 * 초기화 일때 서버에게 연결을 하고
 * 소멸할때 서버와의 연결을 끝낸다.
 *
 * 어노테이션을 활용한 빈 초기화,소멸
 * 초기화 : @PostConstruct
 * 소멸 : @PreDestroy
 *
 * 가장 명확하면서 실무에서 사용가능성이 높다.
 */
public class Client {

    private String host;
		public Client(){
        System.out.println("Client 생성자 호출");
    }

    public void setHost(String host){
        this.host = host;
    }
    @PostConstruct
    public void connect() throws Exception {
        System.out.println("connect");
    }

    public void send(String msg){
        System.out.println("host : " + host + " msg : " + msg);
    }
    @PreDestroy
    public void close() throws Exception {
        System.out.println("close");
    }

}
```

결과

```
Client 생성자 호출
connect
host : 111.111.111.111 msg : 안녕하세요.
close
```

프로토 타입

```java
public class AppConfig {

    @Bean
    @Scope("prototype")
    public Client client(){
        Client client = new Client();
        client.setHost("111.111.111.111");
        return client;
    }
}

/**
 * Client 클래스
 * 초기화 일때 서버에게 연결을 하고
 * 소멸할때 서버와의 연결을 끝낸다.
 *
 * 어노테이션을 활용한 빈 초기화,소멸
 * @PreDestroy 작동 안함
 * 스프링 컨테이너 소멸 시점에 호출 안됨
 * 직정 소멸 메소드를 호출
 */
public class Client {

    private String host;

    public Client(){
        System.out.println("Client 생성자 호출");
    }
    public void setHost(String host){
        this.host = host;
    }
    @PostConstruct
    public void connect() {
        System.out.println("connect");
    }

    public void send(String msg){
        System.out.println("host : " + host + " msg : " + msg);
    }
    //직정 호출하지 않으면 스프링 컨테이너 소멸 시점에 호출 안됨
    @PreDestroy
    public void close() {
        System.out.println("close");
    }

}

public class Main {
    public static void main(String[] args) {
        //1. 컨테이너 초기화
        AnnotationConfigApplicationContext ctx   = new AnnotationConfigApplicationContext(AppConfig.class);
        
        //2. 빈 객체를 구해서 사용
        //프로토 타입의 경우 빈을 호출하때마다 빈 객체의 생성,의존 주입, 초기화
        Client client1 = ctx.getBean("client", Client.class);
        client1.send("client1 : 안녕하세요.");

        Client client2 = ctx.getBean("client", Client.class);
        client1.send("client2 : 안녕하세요.");

        if(client1 == client2){
            System.out.println("client1 == client2");
        }else {
            System.out.println("client1 != client2");
        }

        //프로토 타입의 경우 개발자가 close를 직접 해준다.
//        client1.close();
//        client2.close();

        // 컨테이너 종료
        // 컨테이너가 종료 되어도 소멸 메소드는 작동하지 않는다.
        // 왜냐하면 빈이 호출될때 마다 계속해서 빈을 생성하기 대문에
        // 사용하는 순간 스프링 컨테이너에서 더이상 관리를 하지 않는다.
        // 그러므로 소멸처리를 개발자가 직접 명시해주어야한다.
        ctx.close();

    }
}
```

결과

```
Client 생성자 호출
connect
host : 111.111.111.111 msg : client1 : 안녕하세요.
Client 생성자 호출
connect
host : 111.111.111.111 msg : client2 : 안녕하세요.
client1 != client2
```

> 출처 : 최범균, 초보 웹 개발자를 위한 스프링 5 프로그래밍 입문
>