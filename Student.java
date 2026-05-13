/**
 * student 학생 1명의 데이터와 계산하는 클래스.
 *
 * @author (장서윤, 유서진, 이지헌)
 * @version (2026.05.07)
 */
class Student {
    private String studentId; //학번
    private String name; //이름
    private int year; //학년
    private int subjectCount; //과목수
    private String[] subjectName; //과목이름
    private int[] score; //점수
    private int[] credit; //과목별 학점
    private double[] points; //과목별 평점
    private String[] Grades; // 등급 배열

    private int totalCredit; //전체 이수 학점
    private double averagePoint; // 최종 평점평균

    /**
     * Student 생성자: 학생의 기본 정보와 성적 데이터를 받아 객체를 초기화합니다.
     * 
     * @param studentId 학번
     * @param name 이름
     * @param year 학년
     * @param subjectCount 수강 과목 수
     * @param subjectName 과목 이름 배열
     * @param score 점수 배열 (0~100)
     * @param credit 이수 학점 배열
     */
    public Student(String studentId, String name, int year, int subjectCount, String[] subjectName, int[] score, int[] credit)
    {
        this.studentId = studentId;
        this.name = name;
        this.year = year;
        this.subjectCount = subjectCount;
        this.subjectName = subjectName;
        this.score = score;
        this.credit = credit;
        this.points = new double[subjectCount];
        this.Grades = new String [subjectCount];
        calcAvgPoint(); //평점.등급 계산
    }

    /**
     * 점수를 4.5 만점 기준의 평점으로 변환합니다.
     * @param score 점수 (0~100)
     * @return 4.5 만점 환산 평점
     */
    private double scoreToPoint(int score)
    {
        if (score >= 95) return 4.5;      // A+
        else if (score >= 90) return 4.0; // A
        else if (score >= 85) return 3.5; // B+
        else if (score >= 80) return 3.0; // B
        else if (score >= 75) return 2.5; // C+
        else if (score >= 70) return 2.0; // C
        else if (score >= 65) return 1.5; // D+
        else if (score >= 60) return 1.0; // D
        else return 0.0;  
    }

    /**
     * 점수를 문자 등급(A+ ~ F)으로 변환합니다.
     * @param score 점수 (0~100)
     * @return 문자 등급
     */
    private String scoreToGrade(int score)
    {
        if (score >= 95) return "A+";      
        else if (score >= 90) return "A"; 
        else if (score >= 85) return "B+"; 
        else if (score >= 80) return "B"; 
        else if (score >= 75) return "C+"; 
        else if (score >= 70) return "C"; 
        else if (score >= 65) return "D+"; 
        else if (score >= 60) return "D"; 
        else return "F";
    }

    /**
     * 총 이수학점을 산출하고, 과목별 평점을 이용해 최종 평균 평점을 계산합니다.
     */
    private void calcAvgPoint()
    {
        this.totalCredit = 0;
        double sumOfGradePoint = 0.0;

        for (int i = 0; i < this.subjectCount; i++) {
            this.points[i] = scoreToPoint(this.score[i]);
            this.Grades[i] = scoreToGrade(this.score[i]); 

            this.totalCredit += this.credit[i];
            sumOfGradePoint += (this.points[i] * this.credit[i]);
        }

        if (this.totalCredit > 0) {
            this.averagePoint = sumOfGradePoint / this.totalCredit; 
        } else {
            this.averagePoint = 0.0;
        }
    }

    public String getStudentId() { 
        return studentId;
    }

    public String getName() { 
        return name; 
    }

    public double getAveragePoint() { 
        return averagePoint; 
    }

    /**
     * 학생의 기본 정보와 과목별 등급, 최종 성적 처리 결과를 화면에 출력합니다.
     */
    public void printStudentInfo()
    {
        System.out.println("--------------------------------------------------");
        System.out.println("학번: " + this.studentId + "\t이름: " + this.name + "\t학년: " + this.year + "학년");

        System.out.println("\n[과목별 성적]");
        for (int i = 0; i < this.subjectCount; i++) {
            System.out.println(" - " + this.subjectName[i] + " : " + this.Grades[i]);
        }

        System.out.println("\n[전체 성적 처리 결과]");
        System.out.print("수강과목수: " + this.subjectCount + "개\t");
        System.out.print("총 이수학점: " + this.totalCredit + "학점\t");

        System.out.println("최종 평점: " + this.averagePoint);
        System.out.println("--------------------------------------------------");
    }

    /**
     * 특정 과목명을 검색하여 수강 여부를 확인하고, 수강한 경우 성적 정보를 출력합니다.
     * @param searchSubject 조회할 과목명
     * @return 과목 수강 여부 (수강 시 true)
     */
    public boolean printSingleSubjectInfo(String searchSubject)
    {
        for (int i = 0; i < this.subjectCount; i++) {
            if (this.subjectName[i].equals(searchSubject)) { 
                System.out.print(" - 학번: " + this.studentId);
                System.out.print("\t이름: " + this.name);
                System.out.print("\t학년: " + this.year + "학년");
                System.out.println("\t[" + this.subjectName[i] + "] 학점: " + this.Grades[i]);
                return true; 
            }
        }
        return false; 
    }
}