package prototype;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
