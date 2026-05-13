import java.util.Scanner;
import java.util.InputMismatchException;
/**
 * MyApp 성적 처리 프로그램.
 * 1.학생 수 입력받기
 * 2.입력 받은 학생 정보 반복 입력
 * 3. 메뉴 선택
 *    3-1. 전체 성적표 출력 (정렬 선택 + 동점자 공동순위)
 *    3-2. 과목별 학생 성적 조회
 *    3-3. 종료 성적표 출력
 *
 * @author (장서윤, 유서진, 이지헌)
 * @version (2026.05.07)
 */
public class MyApp {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        // 학생 수 입력받기
        int n = inputTotalStudents();
        Student[] students = new Student[n];

        // 학생들의 정보 입력받기
        inputAllStudentData(students);

        // 메인 메뉴 루프
        runMainMenu(students);

        scan.close();
    }

    /**
     * 사용자로부터 처리할 학생 수를 입력받습니다. 
     * 숫자가 아니거나 0 이하인 경우 예외 처리를 통해 다시 입력받습니다.
     * @return 유효한 학생 수(1 이상)
     */
    private static int inputTotalStudents()
    {
        int n = 0;
        while (true) {
            try {
                System.out.print("처리할 학생 수를 입력하세요: ");
                n = scan.nextInt();
                if (n > 0) return n;
                System.out.println("1 이상의 정수를 입력하세요.");
            } catch (InputMismatchException e) {
                System.out.println("정수가 아닙니다. 다시 입력하세요.");
                scan.next();
            }
        }
    }

    /**
     * 지정된 학생 수만큼 반복하여 학생들의 기본 정보를 입력받고 배열에 저장합니다.
     * @param students 학생 객체를 저장할 배열
     */
    private static void inputAllStudentData(Student[] students)
    {
        for (int i = 0; i < students.length; i++) {
            try {
                System.out.println("\n--- " + (i + 1) + "번째 학생 정보 입력 ---");
                System.out.print("학번: "); String studentId = scan.next();
                System.out.print("이름: "); String name = scan.next();
                System.out.print("학년(1~4): "); int year = scan.nextInt();

                if (year < 1 || year > 4) {
                    System.out.println("학년 오류! 다시 입력하세요.");
                    i--; continue;
                }

                if (!inputSubjectDetails(students, i, studentId, name, year)) {
                    i--; // 과목 점수 오류 시 해당 학생 재입력
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 처음부터 다시 입력하세요.");
                scan.next(); i--;
            }
        }
    }

    /**
     * 학생의 수강 과목 수와 각 과목의 상세 정보(과목명, 점수, 학점)를 입력받습니다.
     * 입력된 데이터가 유효하면 Student 객체를 생성하여 배열에 할당합니다.
     * @param students 학생 배열
     * @param index 현재 입력 중인 학생의 인덱스
     * @param studentId 학번
     * @param name 이름
     * @param year 학년
     * @return 데이터 유효성 검사 결과 (유효 시 true)
     */
    private static boolean inputSubjectDetails(Student[] students, int index, String studentId, String name, int year)
    {
        System.out.print("수강한 과목 수를 입력하세요: ");
        int subjectCount = scan.nextInt();

        String[] subjectName = new String[subjectCount];
        int[] score = new int[subjectCount];
        int[] credit = new int[subjectCount];
        boolean isInputValid = true;

        for (int j = 0; j < subjectCount; j++) {
            System.out.print((j + 1) + "번째 과목명 점수 학점: ");
            subjectName[j] = scan.next();
            score[j] = scan.nextInt();
            credit[j] = scan.nextInt();

            if (score[j] < 0 || score[j] > 100) {
                isInputValid = false;
                break;
            }
        }

        if (isInputValid) {
            students[index] = new Student(studentId, name, year, subjectCount, subjectName, score, credit);
            return true;
        } else {
            System.out.println("점수 범위 오류(0~100)! 이 학생을 재입력합니다.");
            return false;
        }
    }

    /**
     * 프로그램의 메인 메뉴를 출력하고 사용자의 선택에 따라 성적표 출력 또는 검색 기능을 실행합니다.
     * @param students 데이터가 저장된 학생 배열
     */
    private static void runMainMenu(Student[] students)
    {
        while (true) {
            System.out.println("\n1. 전체 성적표 | 2. 과목별 조회 | 3. 종료");
            System.out.print(">> 선택: ");
            int menuOption = scan.nextInt();

            if (menuOption == 1) {
                processSortAndPrint(students);
            } else if (menuOption == 2) {
                processSearch(students);
            } else if (menuOption == 3) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }
        }
    }

    /**
     * 학생들을 평균 평점 기준 내림차순(성적순)으로 정렬(버블 정렬)하여 전체 성적표를 출력합니다.
     * @param students 정렬 및 출력할 학생 배열
     */
    private static void processSortAndPrint(Student[] students)
    {
        for (int i = 0; i < students.length - 1; i++) {
            for (int j = 0; j < students.length - 1 - i; j++) {
                if (students[j].getAveragePoint() < students[j+1].getAveragePoint()) {
                    Student temp = students[j];
                    students[j] = students[j+1];
                    students[j+1] = temp;
                }
            }
        }
        System.out.println("\n=== 전체 성적표 (성적순) ===");
        for (Student s : students) s.printStudentInfo();
    }

    /**
     * 사용자로부터 과목명을 입력받아 해당 과목을 수강한 학생들의 성적 정보를 검색하여 출력합니다.
     * @param students 검색 대상 학생 배열
     */
    private static void processSearch(Student[] students)
    {
        System.out.print("조회할 과목명: ");
        String target = scan.next();
        boolean isFound = false;
        for (Student s : students) {
            if (s.printSingleSubjectInfo(target)) isFound = true;
        }
        if (!isFound) System.out.println("해당 과목 수강생이 없습니다.");
    }
}