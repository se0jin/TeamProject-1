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
                        break; 
                    } catch (InputMismatchException e) {
                        System.out.println("문자가 입력되었습니다. 숫자만 다시 입력하세요.");
                        scanner.next(); 
                    }
                }

                String[] subjectName = new String[subjectCount];
                int[] score = new int[subjectCount];
                int[] credit = new int[subjectCount];
                boolean isInputValid = true;

                for (int j = 0; j < subjectCount; j++) {
                    System.out.print((j + 1) + "번째 과목의 [과목명] [점수] [이수학점]을 띄어쓰기로 구분하여 입력하세요 (예: 자바프로그래밍 95 3): ");
                    subjectName[j] = scanner.next(); 
                    score[j] = scanner.nextInt();    
                    credit[j] = scanner.nextInt();   

                    if (score[j] < 0 || score[j] > 100 || credit[j] <= 0) {
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
                                            subjectName, score, credit);

            } catch (InputMismatchException e) {
                System.out.println("숫자만 입력하세요.");
                scanner.next(); 
                i--; 
                continue;
            }
        }

        while (true) {
            System.out.println("\n=================================");
            System.out.println("1. 전체 성적표 출력");
            System.out.println("2. 과목별 학생 성적 조회");
            System.out.println("3. 프로그램 종료");
            System.out.print(">> 선택: ");

            int menuOption = scanner.nextInt();

            if (menuOption == 1) {
                System.out.println("\n정렬 방식을 선택하세요.");
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
                int[] rank = new int[students.length];

                for (int i = 0; i < students.length; i++) {
                    if (i == 0) {
                        rank[i] = 1; // 첫번째는 무조건 1등
                    } else {
                        if (students[i].getAveragePoint() == students[i-1].getAveragePoint()) {
                            rank[i] = rank[i-1]; // 평점 같으면 같은 순위
                        } else {
                            rank[i] = i + 1; // 다르면 현재 인덱스+1 이 순위
                        }
                    }
                }

                System.out.println("\n=== 전체 학생 성적표 ===");
                for (int i = 0; i < students.length; i++) {
                    System.out.println("석차: " + rank[i] + "/" + students.length);
                    students[i].printStudentInfo();
                }

            } else if (menuOption == 2) {
                System.out.print("조회할 과목명을 입력하세요: ");
                String searchSubject = scanner.next();

                System.out.println("\n=== [" + searchSubject + "] 과목 수강 학생 성적 ===");
                boolean isFound = false;

                for (Student s : students) {
                    if (s.printSingleSubjectInfo(searchSubject)) {
                        isFound = true;
                    }
                }

                if (!isFound) {
                    System.out.println("해당 과목을 수강한 학생이 없습니다.");
                }

            } else if (menuOption == 3) {
                System.out.println("프로그램을 종료합니다.");
                scanner.close();
                break;
            }
        }
    }
}