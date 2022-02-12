package anno;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
