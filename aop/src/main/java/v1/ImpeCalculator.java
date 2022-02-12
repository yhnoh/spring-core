package v1;

/**
 * 들어온 숫자 만큼 더한다.
 */
public class ImpeCalculator implements Calculator {

    @Override
    public long factorial(long num) {
        long start = System.currentTimeMillis();
        long result = 1;
        for (int i = 0; i < num; i++) {
            result += i;
        }
        long end = System.currentTimeMillis();
        System.out.printf("ImpeCalculator.factorial 결과값 = %d 실행시간 = %d\n", num, (end - start));
        return result;
    }
}
