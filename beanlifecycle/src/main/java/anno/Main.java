package anno;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
