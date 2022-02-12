package prototype;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
