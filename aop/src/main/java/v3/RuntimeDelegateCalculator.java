package v3;

public class RuntimeDelegateCalculator implements Calculator{

    private Calculator delegate;

    public RuntimeDelegateCalculator(Calculator delegate) {
        this.delegate = delegate;
    }

    @Override
    public long factorial(long num) {
        long start = System.currentTimeMillis();
        long factorial = delegate.factorial(num);
        long end = System.currentTimeMillis();

        System.out.printf("%s.factorial 결과값 = %d 실행시간 = %d\n", delegate.getClass().getSimpleName(), factorial, (end - start));
        return factorial;
    }
}
