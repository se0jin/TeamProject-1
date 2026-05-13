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
    private static Subject[] globalSubjects;

    public static void main(String[] args) {
        setupGlobalSubjects();

        int n = inputStudentCount();
        Student[] students = new Student[n];

        inputStudentData(students);
        boolean isRunning = true;
        while (isRunning) {
            int menuOption = displayMenuAndSelect();

            if (menuOption == 1) {
                printFullReport(students);
            } else if (menuOption == 2) {
                searchSubjectScore(students);
            } else if (menuOption == 3) {
                System.out.println("프로그램을 종료합니다.");
                isRunning = false;
            } else {
                System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        }
        scan.close();
    }

    /**
     * 메소드 예제 - 사용자에 맞게 주석을 바꾸십시오.
     *
     * @param  y  메소드의 샘플 파라미터
     * @return    x 와 y의 합
     */
    private static void setupGlobalSubjects()   
    {
        System.out.println("=== [과목 생성 메뉴] ===");
        System.out.print("이번 학기에 등록할 총 과목 수를 입력하세요: ");
        int count = scan.nextInt();
        scan.nextLine();
        globalSubjects = new Subject[count];

        for (int i = 0; i < count; i++) {
            System.out.print((i + 1) + "번째 과목명: ");
            String subName = scan.next();
            globalSubjects[i] = new Subject(i + 1, subName);
        }
        System.out.println(">> 과목 등록이 완료되었습니다.\n");
    }

    /**
     * 사용자로부터 처리할 학생 수를 입력받습니다. 
     * 숫자가 아니거나 0 이하인 경우 예외 처리를 통해 다시 입력받습니다.
     * @return 유효한 학생 수(1 이상)
     */
    private static int inputStudentCount()

    {
        int n = 0;
        while (true) {
            try {
                System.out.print("처리할 학생 수를 입력하세요: ");
                n = scan.nextInt();
                if (n > 0) break;
                System.out.println("1 이상의 정수를 입력하세요.");
            } catch (InputMismatchException e) {
                System.out.println("정수가 아닙니다. 다시 입력하세요.");
                scan.next();
            }
        }
        return n;
    }

    /**

     * 지정된 학생 수만큼 반복하여 학생들의 기본 정보를 입력받고 배열에 저장합니다.
     * @param students 학생 객체를 저장할 배열
     */

    private static void inputStudentData(Student[] students)

    {
        for (int i = 0; i < students.length; i++) {
            try {
                System.out.println("\n--- " + (i + 1) + "번째 학생 정보 입력 ---");
                System.out.print("학번: "); String id = scan.next();
                System.out.print("이름: "); String name = scan.next();
                System.out.print("학년(1~4): "); int year = scan.nextInt();

                if (year < 1 || year > 4) {
                    System.out.println("학년 오류! 다시 입력하세요.");
                    i--; continue;
                }

                System.out.print("수강한 과목 수를 입력하세요: ");
                int subCount = scan.nextInt();

                if (!processSubjectInput(students, i, id, name, year, subCount)) {
                    i--; 
                }
            } catch (InputMismatchException e) {
                System.out.println("입력 오류! 처음부터 다시 입력하세요.");
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

    private static boolean processSubjectInput(Student[] students, int i, String id, String name, int year, int subCount)
    {
        System.out.println("[수강 과목 리스트]");
        for (int k = 0; k < globalSubjects.length; k++) {
            System.out.print(globalSubjects[k].id + "." + globalSubjects[k].name + " ");
        }

        int[] selectedIds = new int[subCount];
        System.out.print("\n수강한 과목 번호 " + subCount + "개를 띄어쓰기로 입력하세요: ");
        for (int j = 0; j < subCount; j++) {
            selectedIds[j] = scan.nextInt(); 
        }

        String[] subNames = new String[subCount];
        int[] scores = new int[subCount];
        int[] credits = new int[subCount];

        for (int j = 0; j < subCount; j++) {
            int subId = selectedIds[j];
            if (subId < 1 || subId > globalSubjects.length) {
                System.out.println("\n오류: " + subId + "번 과목은 존재하지 않습니다. 다시 하세요.");
                return false;
            }
            
            subNames[j] = globalSubjects[subId - 1].name;

            System.out.print("\n[" + subNames[j] + "]의 점수와 학점 입력(예: 95 3): ");
            scores[j] = scan.nextInt();
            credits[j] = scan.nextInt();

            if (scores[j] < 0 || scores[j] > 100) return false;
        }

        students[i] = new Student(id, name, year, subCount, subNames, scores, credits);
        return true;
    }

    /**
     * 프로그램의 메인 메뉴를 출력하고 사용자의 선택에 따라 성적표 출력 또는 검색 기능을 실행합니다.
     * @param students 데이터가 저장된 학생 배열
     */
    private static int displayMenuAndSelect()

    {
        System.out.println("\n1. 전체 성적표 | 2. 과목 조회 | 3. 종료");
        System.out.print(">> 선택: ");
        return scan.nextInt();
    }

    /**
     * 메소드 예제 - 사용자에 맞게 주석을 바꾸십시오.
     *
     * @param  y  메소드의 샘플 파라미터
     * @return    x 와 y의 합
     */
    private static void printFullReport(Student[] students)
    {
        System.out.print("정렬 기준(1.이름 2.성적 3.학번): ");
        int opt = scan.nextInt();

        for (int i = 0; i < students.length - 1; i++) {
            for (int j = 0; j < students.length - 1 - i; j++) {
                boolean swap = false;
                if (opt == 1) {
                    if (students[j].getName().compareTo(students[j+1].getName()) > 0) swap = true;
                } else if (opt == 2) {
                    if (students[j].getAveragePoint() < students[j+1].getAveragePoint()) swap = true;
                } else if (opt == 3) {
                    if (students[j].getStudentId().compareTo(students[j+1].getStudentId()) > 0) swap = true;
                }
                if (swap) {
                    Student temp = students[j];
                    students[j] = students[j+1];
                    students[j+1] = temp;
                }
            }
        }

        System.out.println("\n=== 전체 학생 성적표 ===");
        for (int i = 0; i < students.length; i++) {
            int rank = 1;
            for (int j = 0; j < students.length; j++) {
                if (students[j].getAveragePoint() > students[i].getAveragePoint()) rank++;
            }
            System.out.print("[석차: " + rank + "/" + students.length + "] ");
            students[i].printStudentInfo();
        }
    }

    /**
     * 메소드 예제 - 사용자에 맞게 주석을 바꾸십시오.
     *
     * @param  y  메소드의 샘플 파라미터
     * @return    x 와 y의 합
     */
    private static void searchSubjectScore(Student[] students) {
        System.out.println("--- 과목별 성적 조회 ---");
        for (int k = 0; k < globalSubjects.length; k++) {
            System.out.print(globalSubjects[k].id + "." + globalSubjects[k].name + " ");
        }
        System.out.print("조회할 과목의 번호를 선택하세요: ");
        int choice = scan.nextInt();
        if (choice < 1 || choice > globalSubjects.length) {
            System.out.println("해당 번호의 과목이 없습니다.");
            return;
        }
        String target = globalSubjects[choice - 1].name;
    
        System.out.println("=== [" + target + "] 수강생 성적 목록 ===");
        boolean isFound = false;
        for (int i = 0; i < students.length; i++) {

            if (students[i].printSingleSubjectInfo(target)) {
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("해당 과목을 수강한 학생이 없습니다.");
        }
    }
}