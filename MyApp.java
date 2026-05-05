import java.util.Scanner;
/**
 * MyApp 클래스의 설명을 작성하세요.
 *
 * @author (작성자 이름)
 * @version (버전 번호 또는 작성한 날짜)
 */
public class MyApp
{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String Student[] list = new Student[2];    // 객체 배열 생성
        
        // 1. 데이터 입력
        for (int i = 0; i < list.length; i++) {
            System.out.print("Name: ");
            String name = sc.next();
            System.out.print("Score: ");
            int score = sc.nextInt();
            list[i] = new Student(name, score); // 객체 생성 및 배열 저장
        }

        // 2. 평점평균 계산 
        double totalPoint = 0;
        for (Student s : list) {
            totalPoint += s.point;
        }
        double avgGpa = totalPoint / list.length;

        // 3. 결과 출력
        System.out.println("Average GPA: " + avgGpa);
        sc.close();
    }
}