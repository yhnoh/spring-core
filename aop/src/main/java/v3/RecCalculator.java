package v3;


/**
 * 들어온 수자만큼 곲한다.
 */
public class RecCalculator implements Calculator {

    @Override
    public long factorial(long num) {

        if (num == 0){
            num = 1;
        }else{
            num *= factorial(num - 1);
        }


        return num;
    }
}
