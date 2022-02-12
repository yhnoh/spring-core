package v4;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class RuntimeAspect {

    @Pointcut("execution(* v4..*Calculator.*(..))")
    private void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        Object proceed = null;
        try{
            proceed = joinPoint.proceed();
            return proceed;
        }finally {
            long end = System.currentTimeMillis();
            System.out.printf("%s.%s 결과값 = %d 실행시간 = %d\n", joinPoint.getTarget().getClass().getSimpleName(),
                    joinPoint.getSignature().getName(), proceed, (end - start));
        }
    }
}
