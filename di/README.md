# DI

Maven Repository

[https://mvnrepository.com/artifact/org.springframework/spring-context](https://mvnrepository.com/artifact/org.springframework/spring-context)

스프링 컨테이너에서 객체를 관리하기 위한 메아븐 레파지토리

@Bean 

- 스프링 컨테이너에서 어떤 객체를 관리해야하는지 명시하는 어노테이션
- AnnotationConfigApplicationContext.claas 에서 빈 객체 관리
    - ApplicationContext.class의 구현체
    - 구현체의 이름에서 보이듯이 어노테이션을 통해서 관리, 즉 다른 구현체들도 존재
- 어플리케이션 실행시 한번만 빈 객체는 한번만 생성 (싱글톤 객체)
- 필드에 값을 넣는 순간 멀티스레드 환경에서 동시성 이슈 발생 가능성 높음
- 이후 설명 : DI(Dependency Injection: 의존관계 주입)

```java
@Getter
@Setter
public class Hello {
    private String hello = "%s 안녕하세요";

    public String helloFormat(String name){
        return String.format(hello, name);
    }
}

public class Config {
		//메인에서 직접 생성하지 않음, 즉 스프링 컨테이너가 관리
    @Bean
    public Hello hello(){
        Hello hello = new Hello();
        //어플리케이션 실행시점에 실제로 빈이 등록 되는지 확인
        String format = hello.helloFormat("스프링1");
        System.out.println(format);
        return hello;
    }
}

public class Main {

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        //빈 객체 관리 확인
        Hello hello1 = ac.getBean("hello", Hello.class);
        String format1 = hello1.helloFormat("스프링2");
        System.out.println(format1);

        //객체를 한번만 생성하는지 확인
        Hello hello2 = ac.getBean("hello", Hello.class);
        if(hello1 == hello2){
            System.out.println("hello1 과 hello2는 같다");
        }

				//싱글톤이기 대문에 동시성 이슈 발생
        hello1.setHello("hello1");
        hello2.setHello("hello2");
        
        System.out.println("hello1 = " + hello1.getHello());
        System.out.println("hello2 = " + hello2.getHello());
    }
}
```

 DI(Dependency Injection: 의존관계 주입)

- 하나의 클래스가 다른 클래스를 필요로 할 때  이를 의**존**이라고 한다.
- 스프링 컨테이너에서 생성하여여 객체를 주입시(DI)켜 준다.
- DI의 가장 큰 장점은 객체의 상태 변경이 가능하다.
- 다형성을 활용하면 객체의 상태를 변경해도 사용자의 코드를 변경할 필요가 없다.
- DI에는 필드 주입, 메소드 주입, 생성자 주입이 있다.
- 메소드 주입 및 생성자 주입은 객체의 상태 변경이 가능하다.
- 메소드 주입은 객체 조립방식을 사용자가 알아야하기 때문에 생성자 주입 방식을 선호한다.
- 이후 설명 :  Ioc(Inversion of Control : 제어의 역전) , @Autowired(의존 자동 주입) 이를 이용하여 메소드

DI와 필요성을 위한 시나리오

1. 회원가입 로직이 필요하다는 요청
2. 회원가입 로직에 중복체크가 필요
3. 회원 중복체크를 이메일로 할지 사용자 이름을 할지 고민
4. 아직 결정은 안됬지만 일단 만들어 달라고한다.

공통

```java
@Getter
public class Member {
    private String username;
    private String email;

    public Member(String username, String email) {
        this.username = username;
        this.email = email;
    }
}

/**
 * 중복 체크 인터페이스
 * 객체의 상태가 변경해도 사용자의 코드를 변경하지 않기 위해서 다형성이 꼭 필요
 */
public interface MemberDuplication {
    Member duplicate(Member member);
}

//Email 중복체크
public class MemberEmailDuplication implements MemberDuplication{
    public static Map<String, Member> members = new HashMap<>();

    @Override
    public Member duplicate(Member member) {
        if(members.containsKey(member.getUsername())){
            throw new IllegalArgumentException("사용자 이메일은 이미 존재합니다.");
        }
        members.put(member.getEmail(), member);
        return member;

    }
}

//Username 중복 체크
public class MemberUsernameDuplication implements MemberDuplication{
    private static Map<String, Member> members = new HashMap<>();
    @Override
    public Member duplicate(Member member) {
        if(members.containsKey(member.getUsername())){
            throw new IllegalArgumentException("사용자 이름은 이미 존재합니다.");
        }
        members.put(member.getUsername(), member);
        return member;
    }
}
```

1. 필드 주입

```java
public class MemberJoin {

    //필드 주입
    MemberDuplication memberDuplication = new MemberUsernameDuplication();

    //회원 가입
    public Member join(String username, String email){
        Member member = new Member(username, email);
        Member duplicate = memberDuplication.duplicate(member);
        return member;
    }
}

/**
 * 필드 주입 방식
 * 1.객체 조립이 불가능 하다.
 * 2.의존성 주입을 받는 객체의 상태 변경이 한번에 불가능 하다. (사용자 중복 -> 이메일 중복)
 * 3.수백개의 클래스가 의존성을 주입받는 경우 실수가 분명히 일어난다.
 * 4.이 실수는 런타임 에러 시점에 확인 가능하다. (치명적)
 */
public class Config {
    @Bean
    public MemberJoin memberJoin(){
        return new MemberJoin();
    }
}

public class Main {

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        MemberJoin memberJoin = ac.getBean("memberJoin", MemberJoin.class);
        memberJoin.join("username", "email");
        memberJoin.join("username", "email");
    }
}
```

1. 메소드 주입

```java
public class MemberJoin {

    MemberDuplication memberDuplication;
    //메소드 주입
    public void setMemberDuplication(MemberDuplication memberDuplication) {
        this.memberDuplication = memberDuplication;
    }

    //회원 가입
    public Member join(String username, String email){
        Member member = new Member(username, email);
        Member duplicate = memberDuplication.duplicate(member);
        return member;
    }
}

/**
 * 메소드 주입 방식
 * 1.객체의 조립이 가능하다.
 * 2.인스턴스를 생성하고 난 뒤에 객체의 사용자는 다시 setter메소드를 이용해서 의존성 주입을 해줘야 한다.
 * 3.런타임 에러가 발생할 가능성이 있다.
 */
public class Config {
    //사용자 이름 중복 체크
    @Bean
    public MemberJoin memberJoin(){
        MemberJoin memberJoin = new MemberJoin();
        memberJoin.setMemberDuplication(new MemberUsernameDuplication());
        return memberJoin;
    }

    //이메일 중복 체크
/*
    @Bean
    public MemberJoin memberJoin(){
        MemberJoin memberJoin = new MemberJoin();
        memberJoin.setMemberDuplication(new MemberEmailDuplication());
        return memberJoin;
    }
*/

}

public class Main {

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        MemberJoin memberJoin = ac.getBean("memberJoin", MemberJoin.class);
        memberJoin.join("username", "email");
        memberJoin.join("username", "email");
    }
}
```

1. 생성자 주입

```java
public class MemberJoin {

    MemberDuplication memberDuplication;
    //생성자 주입
    public MemberJoin(MemberDuplication memberDuplication) {
        this.memberDuplication = memberDuplication;
    }

    //회원 가입
    public Member join(String username, String email){
        Member member = new Member(username, email);
        Member duplicate = memberDuplication.duplicate(member);
        return member;
    }
}

/**
 * 생성자 주입 방식
 * 1.객체 조립을 객체를 생성할때 어떤것을 주입해할지 명확하다.
 * 2.가장 좋은 방식
 */
public class Config {

    //사용자 이름 중복 체크
    @Bean
    public MemberJoin memberJoin(){
        MemberJoin memberJoin = new MemberJoin(new MemberUsernameDuplication());
        return memberJoin;
    }

    //이메일 중복 체크
/*
    @Bean
    public MemberJoin memberJoin(){
        MemberJoin memberJoin = new MemberJoin(new MemberEmailDuplication());
        return memberJoin;
    }
*/

}

public class Main {

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        MemberJoin memberJoin = ac.getBean("memberJoin", MemberJoin.class);
        memberJoin.join("username", "email");
        memberJoin.join("username", "email");
    }
}
```

Ioc(Inversion of Control : 제어의 역전) , @Autowired(의존 자동 주입)

- @Autowired(의존 자동 주입)란 직접 객체를 생성하는게 아닌 스프링에서 자동으로 의존하는 빈 객체를 주입해주는 기능
- 이는 곧 개발자가 의존성을 주입시켜주는게 아닌 외부(스프링 컨테이너)에서 의존성을 자동으로 주입(객체 생성)시켜 주는 것을 의미, 이를 Ioc(Inversion of Control : 제어의 역전)이라고 한다.
- 객체의 상태가 변경되었을 때 필드 주입 및 메소드 주입은 개발자의 실수가 일어날 수 있는 가능성이 있지만 자동 주입 기능을 사용하면 이를 방지할 수 있고 한번에 수정이 가능하다.
- 필드주입, 메소드주입, 생성자 주입 모두 가능하다.

공통 코드

```java
//설정 클래스
public class Config {

//    @Bean
//    public MemberDuplication memberDuplication(){
//        return new MemberUsernameDuplication();
//    }

    @Bean
    public MemberDuplication memberDuplication(){
        return new MemberEmailDuplication();
    }

    @Bean
    public MemberJoin memberJoin(){
        return new MemberJoin();
    }
}

//실행 클래스
public class Main {

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        MemberJoin memberJoin = ac.getBean("memberJoin", MemberJoin.class);
        memberJoin.join("username", "email");
        memberJoin.join("username", "email");
    }
}
```

필드 자동 주입

```java
public class MemberJoin {

    /**
     * 필드 자동 주입
     * 개발자가 new 키워드를 사용하여 인스턴스를 생성한 적 없음
     * 즉, 스프링에게 의존 주입을 위임한 경우
     * 다형성을 활용했기 때문에 Config 클래스에서 다른 기능으로 변경이 가능
     */
    @Autowired
    MemberDuplication memberDuplication;

    //회원 가입
    public Member join(String username, String email){
        Member member = new Member(username, email);
        Member duplicate = memberDuplication.duplicate(member);
        return member;
    }
}
```

메소드 자동 주입

```java
public class MemberJoin {

    MemberDuplication memberDuplication;

    /**
     * 메소드 주입
     * 개발자가 set메소드를 이용하여 인스턴스를 생성한 적 없음
     * 즉, 스프링에게 의존 주입을 위임한 경우
     * 다형성을 활용했기 때문에 Config.java에서 다른 기능으로 변경이 가능
     */
    @Autowired
    public void setMemberDuplication(MemberDuplication memberDuplication) {
        this.memberDuplication = memberDuplication;
    }

    //회원 가입
    public Member join(String username, String email){
        Member member = new Member(username, email);
        Member duplicate = memberDuplication.duplicate(member);
        return member;
    }
}
```

> 출처 : 최범균, 초보 웹 개발자를 위한 스프링 5 프로그래밍 입문
>