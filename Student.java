
/**
 * student 클래스의 설명을 작성하세요.
 *
 * @author (작성자 이름)
 * @version (버전 번호 또는 작성한 날짜)
 */
class Student {
    private String studentId;
    private String name;
    private int subjectCount; //과목수
    private String[] subjectNames; //과목이름
    private int[] scores; //점수
    private int[] credits; //과목별 학점
    private double[] points; //과목별 평점
    private String[] letterGrades; // 학점 배열
    
    private int totalCredit; //전체 이수 학점
    private double averagePoint; // 최종 평점평균
    public Student(String studentId, String name, int subjectCount, String[] subjectNames, int[] scores, int[] credits) {
        this.studentId = studentId;
        this.name = name;
        this.subjectCount = subjectCount;
        this.subjectNames = subjectNames;
        this.scores = scores;
        this.credits = credits;
        this.points = new double[subjectCount];
        this.letterGrades = new String [subjectCount];
        calculateGPA(); 
    }

    private double con(int s) {
        if (s >= 95) return 4.5;      // A+
        else if (s >= 90) return 4.0; // A
        else if (s >= 85) return 3.5; // B+
        else if (s >= 80) return 3.0; // B
        else if (s >= 75) return 2.5; // C+
        else if (s >= 70) return 2.0; // C
        else if (s >= 65) return 1.5; // D+
        else if (s >= 60) return 1.0; // D
        else return 0.0;              // F
    }

    private String determineLetterGrade(int score) {
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
    
    private void calculateGPA() {
        this.totalCredit = 0;
        double sumOfGradePoints = 0.0;

        for (int i = 0; i < this.subjectCount; i++) {
            this.points[i] = con(this.scores[i]);
            this.letterGrades[i] = determineLetterGrade(this.scores[i]); 
            
            this.totalCredit += this.credits[i];
            sumOfGradePoints += (this.points[i] * this.credits[i]);
        }

        if (this.totalCredit > 0) {
            this.averagePoint = sumOfGradePoints / this.totalCredit;
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
    public int getSubjectCount() { 
        return subjectCount; 
    }
    public double getAveragePoint() { 
        return averagePoint; 
    }
    public int getTotalCredit() { 
        return totalCredit; 
    }

    public void printStudentInfo() {
        System.out.println("--------------------------------------------------");
        System.out.printf("학번: %s\t이름: %s\n", this.studentId, this.name);
        
        System.out.println("\n[과목별 상세 성적]");
        for (int i = 0; i < this.subjectCount; i++) {
            System.out.printf(" - %s : %s\n", this.subjectNames[i], this.letterGrades[i]);
        }
        
        System.out.println("\n[전체 성적 처리 결과]");
        System.out.printf("수강과목수: %d개\t총 이수학점: %d학점\t최종 평점: %.2f\n",
                this.subjectCount, this.totalCredit, this.averagePoint);
        System.out.println("--------------------------------------------------");
    }
}