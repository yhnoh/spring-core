package inter;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

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
