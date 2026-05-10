import java.util.Scanner;
import java.util.InputMismatchException;
/**
 * MyApp 성적 처리 프로그램.
 * 1.학생 수 입력받기
 * 2.입력 받은 학생 정보 반복 입력
 * 3.정렬 방식 선택
 * 4.전체 성적표 출력
 *
 * @author (장서윤, 유서진, 이지헌)
 * @version (2026.05.07)
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
                System.out.println("정수가 아닙니다. 다시 입력하세요.");
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

                System.out.print("학년(1~4): ");
                int year = scanner.nextInt();
                if (year < 1 || year > 4) {
                    System.out.println("학년은 1~4 사이여야 합니다. 다시 입력하세요.");
                    i--; 
                    continue;
                }

                int subjectCount = 0;
                while (true) {
                    try {
                        System.out.print("수강한 과목 수를 입력하세요: ");
                        subjectCount = scanner.nextInt();
                        if (subjectCount <= 0) {
                            System.out.println("과목 수는 1 이상이어야 합니다. 다시 입력하세요.");
                            continue;
                        }
                        break; //while 탈출
                    } catch (InputMismatchException e) {
                        // 예외가 발생해도 바깥으로 튕기지 않고, 여기서 버퍼를 비운 뒤 과목 수만 다시 묻습니다.
                        System.out.println("문자가 입력되었습니다. 숫자만 다시 입력하세요.");
                        scanner.next(); 
                    }
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
                        System.out.println("점수는 0~100 사이, 이수학점은 1 이상이어야 합니다. 처음부터 다시 입력하세요.");
                        isInputValid = false;
                        break;
                    }
                }

                if (!isInputValid) {
                    i--;
                    continue; 
                }

                students[i] = new Student(studentId, name, year, subjectCount, 
                                            subjectNames, scores, credits);

            } catch (InputMismatchException e) {
                System.out.println("숫자만 입력하세요.");
                scanner.next(); 
                i--; 
                continue;
            }
        }

        System.out.println("\n=================================");
        System.out.println("완료 되었습니다.");
        System.out.println("정렬 방식을 선택하세요.");
        System.out.println("1. 이름순 (가나다순)");
        System.out.println("2. 성적순 (평점 높은 순)");
        System.out.println("3. 학번순 (오름차순)");
        System.out.print(">> 선택: ");

        int sortOption = 1;
        try {
            sortOption = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("잘못된 입력으로 기본값인 '이름순'으로 정렬합니다.");
            scanner.next();
        }

        for (int i = 0; i < students.length - 1; i++) {
            for (int j = 0; j < students.length - 1 - i; j++) {
                boolean shouldSwap = false;

                switch(sortOption) {
                    case 1: 
                        if (students[j].getName().compareTo(students[j+1].getName()) > 0) shouldSwap = true;
                        break;
                    case 2: 
                        if (students[j].getAveragePoint() < students[j+1].getAveragePoint()) shouldSwap = true;
                        break;
                    case 3: 
                        if (students[j].getStudentId().compareTo(students[j+1].getStudentId()) > 0) shouldSwap = true;
                        break;
                    default:
                        if (students[j].getName().compareTo(students[j+1].getName()) > 0) shouldSwap = true;
                }

                if (shouldSwap) {
                    Student temp = students[j];
                    students[j] = students[j+1];
                    students[j+1] = temp;
                }
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