package v4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @EnableAspectJAutoProxy
 * AOP 생성과 관련된 AnnotationAwareAspectJAutoProxyCreator를 빈으로 등록
 */
@EnableAspectJAutoProxy
public class Config {
    @Bean
    public RuntimeAspect runtimeAspect(){
        return new RuntimeAspect();
    }
    @Bean
    public Calculator calculator(){
        return new RecCalculator();
    }
}
