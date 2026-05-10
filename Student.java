/**
 * student 학생 1명의 데이터와 계산하는 클래스.
 *
 * @author (장서윤, 유서진, 이지헌)
 * @version (2026.05.07)
 */
class Student {
    private String studentId;
    private String name;
    private int year;
    private int subjectCount; //과목수
    private String[] subjectNames; //과목이름
    private int[] scores; //점수
    private int[] credits; //과목별 학점
    private double[] points; //과목별 평점
    private String[] letterGrades; // 학점 배열
    
    private int totalCredit; //전체 이수 학점
    private double averagePoint; // 최종 평점평균
    public Student(String studentId, String name, int year, int subjectCount, String[] subjectNames, int[] scores, int[] credits) {
        this.studentId = studentId;
        this.name = name;
        this.year = year;
        this.subjectCount = subjectCount;
        this.subjectNames = subjectNames;
        this.scores = scores;
        this.credits = credits;
        this.points = new double[subjectCount];
        this.letterGrades = new String [subjectCount];
        calculateGPA(); 
    }

    private double con(int scores) {
        if (scores >= 95) return 4.5;      // A+
        else if (scores >= 90) return 4.0; // A
        else if (scores >= 85) return 3.5; // B+
        else if (scores >= 80) return 3.0; // B
        else if (scores >= 75) return 2.5; // C+
        else if (scores >= 70) return 2.0; // C
        else if (scores >= 65) return 1.5; // D+
        else if (scores >= 60) return 1.0; // D
        else return 0.0;              // F
    }

    private String determineLetterGrade(int scores) {
        if (scores >= 95) return "A+";      
        else if (scores >= 90) return "A"; 
        else if (scores >= 85) return "B+"; 
        else if (scores >= 80) return "B"; 
        else if (scores >= 75) return "C+"; 
        else if (scores >= 70) return "C"; 
        else if (scores >= 65) return "D+"; 
        else if (scores >= 60) return "D"; 
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
    
    public int getYear() { 
        return year; 
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
        System.out.printf("학번: %s\t이름: %s\t학년: %d학년\n", this.studentId, this.name, this.year);
        
        System.out.print("\n[과목별 성적]\n");
        for (int i = 0; i < this.subjectCount; i++) {
            System.out.printf(" - %s : %s\n", this.subjectNames[i], this.letterGrades[i]);
        }
        
        System.out.print("\n[전체 성적 처리 결과]\n");
        System.out.printf("수강과목수: %d개\t총 이수학점: %d학점\t최종 평점: %.2f\n",
                this.subjectCount, this.totalCredit, this.averagePoint);
        System.out.println("--------------------------------------------------");
    }

    public boolean printSingleSubjectInfo(String searchSubject) {
        for (int i = 0; i < this.subjectCount; i++) {
            if (this.subjectNames[i].equals(searchSubject)) {
                System.out.printf(" - 학번: %s\t이름: %s\t학년: %d학년\t[%s] 학점: %s\n", 
                        this.studentId, this.name, this.year, this.subjectNames[i], this.letterGrades[i]);
                return true; 
            }
        }
        return false; 
    }
}