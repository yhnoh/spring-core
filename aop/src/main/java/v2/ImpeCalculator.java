package v2;

/**
 * 들어온 숫자 만큼 더한다.
 */
public class ImpeCalculator implements Calculator {

    @Override
    public long factorial(long num) {
        long result = 1;
        for (int i = 0; i < num; i++) {
            result += i;
        }
        return result;
    }
}
