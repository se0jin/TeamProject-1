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
    // [공통 데이터 변수]
    private static Scanner scan = new Scanner(System.in);
    private static Subject[] subject;
    private static Student[] students;
    private static String id, name;
    private static int year, subCount;
    private static int subId;

    public static void main(String[] args) {
        SubjectList();

        int n = StudentCount();
        students = new Student[n];

        InputStudent(students);
        while (true) {
            int menuOption = Menu();

            if (menuOption == 1) {
                SortMenu(students);
            } else if (menuOption == 2) {
                SearchSubject(students);
            } else if (menuOption == 3) {
                students = addStudent(students);
            } else if (menuOption == 4) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else {
                System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        }
        scan.close();
    }

    /**
     * 과목수 입력받아 배열 생성 후 과목 생성.
     *
     */
    private static void SubjectList() {
        System.out.println("=== [과목 생성 메뉴] ===");
        System.out.print("이번 학기에 등록할 총 과목 수를 입력하세요: ");
        int count = scan.nextInt();
        scan.nextLine();
        subject = new Subject[count];

        for (int i = 0; i < count; i++) {
            System.out.print((i + 1) + "번째 과목명: ");
            String subName = scan.next();
            subject[i] = new Subject(i + 1, subName);
        }
        System.out.println(">> 과목 등록이 완료되었습니다.\n");
    }

    /**
     * 사용자로부터 처리할 학생 수를 입력받습니다. 
     * 숫자가 아니거나 0 이하인 경우 예외 처리를 통해 다시 입력받습니다.
     * @return 학생 수(1 이상)
     */
    private static int StudentCount() {
        int n = 0;
        while (true) {
            try {
                System.out.print("처리할 학생 수를 입력하세요: ");
                n = scan.nextInt();
                if (n > 0) {
                    break;
                }
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

    private static void InputStudent(Student[] students) {
        for (int i = 0; i < students.length; i++) {
            try {
                System.out.println("\n--- " + (i + 1) + "번째 학생 정보 입력 ---");
                System.out.print("학번: "); id = scan.next();
                System.out.print("이름: "); name = scan.next();
                System.out.print("학년(1~4): "); year = scan.nextInt();

                if (year < 1 || year > 4) {
                    System.out.println("학년 오류! 다시 입력하세요.");
                    i--; 
                    continue;
                }

                System.out.print("수강한 과목 수를 입력하세요: ");
                int subCount = scan.nextInt();

                if (!InputSubject(students, i, id, name, year, subCount)) {
                    i--;  //중간에 몇개를 오타내었을 경우
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
     * @param i 학생들 순서
     * @param studentid 학번
     * @param name 이름
     * @param year 학년
     * @param subCount 수강 과목 수
     * @return 데이터 유효성 검사 결과 (유효 시 true)
     */

    private static boolean InputSubject(Student[] students, int i, String id, String name, int year, int subCount) {
        System.out.println("[수강 과목 리스트]");

        for (int k = 0; k < subject.length; k++) {
            System.out.print(subject[k].id + "." + subject[k].name + " ");
        }

        int[] subjectId = new int[subCount];

        System.out.print("\n수강한 과목 번호 " + subCount + "개를 띄어쓰기로 입력하세요: ");
        for (int j = 0; j < subCount; j++) {
            subjectId[j] = scan.nextInt(); 
        }

        String[] subNames = new String[subCount];
        int[] scores = new int[subCount];
        int[] credits = new int[subCount];

        for (int m = 0; m < subCount; m++) {
            if (subjectId[m] < 1 || subjectId[m] > subject.length) {
                System.out.println("\n오류: " + subjectId[m] + "번 과목은 존재하지 않습니다. 다시 하세요.");
                return false;
            }

            subNames[m] = subject[subjectId[m] - 1].name; //인덱스라서 -1을 함

            System.out.print("\n[" + subNames[m] + "]의 점수와 학점 입력(예: 95 3): ");
            scores[m] = scan.nextInt();
            credits[m] = scan.nextInt();

            if (scores[m] < 0 || scores[m] > 100) {
                System.out.println("점수는 0~100 사이여야 합니다.");
                return false;
            }
        }

        students[i] = new Student(id, name, year, subCount, subNames, scores, credits);
        return true;
    }

    /**
     * 프로그램의 메인 메뉴를 출력하고 사용자의 선택에 따라 성적표 출력 또는 검색 기능을 실행합니다.
     * @param students 데이터가 저장된 학생 배열
     */
    private static int Menu() {
        System.out.println("\n1. 전체 성적표 | 2. 과목 조회 | 3. 학생 추가 | 4. 종료");
        System.out.print(">> 선택: ");
        return scan.nextInt();
    }

    /**
     * 학생 성적을 정렬하여 출력하는 메소드.
     * 이름,성적,학번 중 선택해 정렬
     *
     * @param students 학생 배열
     */
    private static void SortMenu(Student[] students) {
        System.out.print("정렬 기준(1.이름 2.성적 3.학번): ");
        int option = scan.nextInt();

        for (int i = 0; i < students.length - 1; i++) {
            for (int j = 0; j < students.length - 1 - i; j++) { //하나씩 비교해 맨뒤까지 이동
                boolean turn = false;  //비교해서 자리를 바꿀차례인지 보는 코드
                if (option  == 1) {
                    if (students[j].getName().compareTo(students[j+1].getName()) > 0) {
                        turn = true;
                    }
                } else if (option  == 2) {
                    if (students[j].getAveragePoint() < students[j+1].getAveragePoint()) {
                        turn = true;
                    }
                } else if (option  == 3) {
                    if (students[j].getStudentId().compareTo(students[j+1].getStudentId()) > 0) {
                        turn = true;
                    }
                }
                if (turn) {
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
            students[i].printStudent();
        }
    }

    /**
     * 특정 과목을 수강하는 학생들의 성적을 조회하는 메소드.
     *
     * @param students 학생 배열
     */
    private static void SearchSubject(Student[] students) {
        System.out.println("--- 과목별 성적 조회 ---");
        for (int k = 0; k < subject.length; k++) {
            System.out.print(subject[k].id + "." + subject[k].name + " ");
        }
        System.out.print("조회할 과목의 번호를 선택하세요: ");
        int choice = scan.nextInt();
        if (choice < 1 || choice > subject.length) {
            System.out.println("해당 번호의 과목이 없습니다.");
            return;
        }
        String target = subject[choice - 1].name;

        System.out.println("=== [" + target + "] 수강생 성적 목록 ===");
        boolean isFound = false;
        for (int i = 0; i < students.length; i++) {

            if (students[i].printSubject(target)) {
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("해당 과목을 수강한 학생이 없습니다.");
        }
    }

    /**
     * 기존 학생 배열의 크기를 늘리고 새 학생을 추가하는 메소드.
     *
     * @param oldStudents 현재 저장된 학생 배열
     * @return 학생이 한 명 추가된 새로운 학생 배열
     */
    private static Student[] addStudent(Student[] oldStudents) {
        Student[] newStudents = new Student[oldStudents.length + 1];

        for (int i = 0; i < oldStudents.length; i++) {
            newStudents[i] = oldStudents[i];
        }

        int newIndex = oldStudents.length;
        try {
            System.out.println("\n--- 추가 학생 정보 입력 ---");
            System.out.print("학번: "); String id = scan.next();
            System.out.print("이름: "); String name = scan.next();
            System.out.print("학년(1~4): "); int year = scan.nextInt();

            System.out.print("수강한 과목 수를 입력하세요: ");
            int subCount = scan.nextInt();

            if (InputSubject(newStudents, newIndex, id, name, year, subCount)) {
                System.out.println(">> 학생이 추가되었습니다.");
                return newStudents;
            } else {
                System.out.println(">> 추가 되지않았습니다.");
            }
        } catch (InputMismatchException e) {
            System.out.println(">> 형식이 잘못되었습니다.");
            scan.next(); 
        }

        return oldStudents; 
    }
}