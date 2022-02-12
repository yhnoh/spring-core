package v4;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

        Calculator calculator = ctx.getBean("calculator", Calculator.class);
        long factorial = calculator.factorial(5);
        ctx.close();
    }
}
