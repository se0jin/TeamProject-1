import java.util.Scanner;
import java.util.InputMismatchException;
/**
 * MyApp 클래스의 설명을 작성하세요.
 *
 * @author (작성자 이름)
 * @version (버전 번호 또는 작성한 날짜)
 */
public class MyApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = 0;

        System.out.print("처리할 학생 수를 입력하세요: ");
        while (true) {
            try {
                n = scanner.nextInt();
                if (n <= 0) {
                    System.out.println("1 이상의 정수를 입력하세요.");
                    continue;
                }
                break; 
            } catch (InputMismatchException e) {
                System.out.println("정수가 아닙니다. 숫자를 다시 입력하세요!");
                scanner.next();
            }
        }

        Student[] students = new Student[n];
        
        for (int i = 0; i < n; i++) {
            try {
                System.out.println("\n--- " + (i + 1) + "번째 학생 정보 입력 ---");
                
                System.out.print("학번: ");
                String studentId = scanner.next();

                System.out.print("이름: ");
                String name = scanner.next();

                System.out.print("수강한 과목 수를 입력하세요: ");
                int subjectCount = scanner.nextInt();
                if (subjectCount <= 0) {
                    System.out.println("과목 수는 1 이상이어야 합니다. 현재 학생을 다시 입력하세요.");
                    i--; 
                    continue;
                }

                String[] subjectNames = new String[subjectCount];
                int[] scores = new int[subjectCount];
                int[] credits = new int[subjectCount];
                
                boolean isInputValid = true;

                for (int j = 0; j < subjectCount; j++) {
                    System.out.print((j + 1) + "번째 과목의 [과목명] [점수] [이수학점]을 띄어쓰기로 구분하여 입력하세요 (예: 자바프로그래밍 95 3): ");
                    
                    subjectNames[j] = scanner.next();
                    scores[j] = scanner.nextInt();
                    credits[j] = scanner.nextInt();
                    
                    if (scores[j] < 0 || scores[j] > 100 || credits[j] <= 0) {
                        System.out.println("점수는 0~100 사이, 이수학점은 1 이상이어야 합니다. 현재 학생을 처음부터 다시 입력하세요.");
                        isInputValid = false;
                        break;
                    }
                }
                
                if (!isInputValid) {
                    i--;
                    continue; 
                }

                students[i] = new Student(studentId, name, subjectCount, subjectNames, scores, credits);

            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 문자가 입력되었습니다. 다시 입력하세요!");
                scanner.next(); // 버퍼 비우기
                i--; 
                continue;
            }
        }
        
        System.out.println("\n=== 전체 학생 성적표 ===");
        for (Student s : students) {
            s.printStudentInfo();
        }
        
        System.out.println("프로그램을 종료합니다.");
        scanner.close();
    }
}