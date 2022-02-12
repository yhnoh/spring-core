package v1;


/**
 * 들어온 수자만큼 곲한다.
 */
public class RecCalculator implements Calculator{

    @Override
    public long factorial(long num) {
        long start = System.currentTimeMillis();

        if (num == 0){
            num = 1;
        }else{
             num *= factorial(num - 1);
        }

        long end = System.currentTimeMillis();

        System.out.printf("RecCalculator.factorial 결과값 = %d 실행시간 = %d\n", num, (end - start));
        return num;
    }
}
