package v1;

/**
 * 요구 조건
 * - 메소드 실행 시간을 출력하고자 한다.
 *
 * 방법
 * 구현체 메소드 내에서 구현
 *
 * 문제
 * 1. 메소드를 하나 만들때 마다 실행시간을 출력하고자 하는 로직 생성 (중복코드 발생)
 * 2. 비지니스 로직에 집중을 못한다.
 * 3. 실행시간 출력이 아닌 다른 요구사항이 들어올 경우 실행시간 로직을 다 찾아서 변경
 * 4. 재귀함수 같은 경우 이를 만족하지 못한다.
 *
 * 해결
 * 메소드 밖에서 해결
 */
public class Main {

    public static void main(String[] args) {
        Calculator impeCalculator = new ImpeCalculator();
        impeCalculator.factorial(20);

        Calculator recCalculator = new RecCalculator();
        recCalculator.factorial(20);


    }
}
