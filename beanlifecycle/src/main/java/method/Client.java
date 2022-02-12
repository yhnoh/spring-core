package method;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

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
