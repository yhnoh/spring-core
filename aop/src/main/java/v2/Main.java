package v2;

/**
 * 요구 조건
 * - 메소드 실행 시간을 출력하고자 한다.
 *
 * 방법
 * 메소드 로출하는 곳 밖에서 해결
 *
 * 문제
 * 1. 메소드를 하나 만들때 마다 실행시간을 출력하고자 하는 로직 생성 (중복코드 발생)
 * 2. 비지니스 로직에 집중을 못한다.
 * 3. 실행시간 출력이 아닌 다른 요구사항이 들어올 경우 실행시간 로직을 다 찾아서 변경
 *
 * 해결
 * 실행시간을 출력하고 자하는 대리자가 필요
 */
public class Main {

    public static void main(String[] args) {
        Calculator impeCalculator = new ImpeCalculator();
        long start1 = System.currentTimeMillis();
        long factorial1 = impeCalculator.factorial(20);
        long end1 = System.currentTimeMillis();
        System.out.printf("ImpeCalculator.factorial 결과값 = %d 실행시간 = %d\n", factorial1, (end1 - start1));

        Calculator recCalculator = new RecCalculator();
        long start2 = System.currentTimeMillis();
        long factorial2 = recCalculator.factorial(20);
        long end2 = System.currentTimeMillis();
        System.out.printf("RecCalculator.factorial 결과값 = %d 실행시간 = %d\n", factorial2, (end2 - start2));


    }
}
