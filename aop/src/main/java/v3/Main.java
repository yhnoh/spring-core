package v3;

/**
 * 요구 조건
 * - 메소드 실행 시간을 출력하고자 한다.
 *
 * 방법
 * 대리자 인터페이스로 해결
 *
 * 해결
 * 비지니스로직과 실행시간 로직을 분리 했다.
 *
 * 고려
 * 우리는 스프링을 사용하고 있다.
 * 스프링은 어떤방식으로 비지니스 로직과 공통로직을 분리할까?
 * */
public class Main {

    public static void main(String[] args) {
        ImpeCalculator impeCalculator = new ImpeCalculator();
        RuntimeDelegateCalculator impeDelegateCalculator = new RuntimeDelegateCalculator(impeCalculator);
        impeDelegateCalculator.factorial(20);

        RecCalculator recCalculator = new RecCalculator();
        RuntimeDelegateCalculator recDelegateCalculator = new RuntimeDelegateCalculator(recCalculator);
        recDelegateCalculator.factorial(20);

    }
}
