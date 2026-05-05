
/**
 * student 클래스의 설명을 작성하세요.
 *
 * @author (작성자 이름)
 * @version (버전 번호 또는 작성한 날짜)
 */
class Student {
    String name;
    int score; 
    int credit; 
    double point;  

    public Student(String n, int s, int c) {
        this.name = n;
        this.score = s;
        this.credit = c;
        this.point = con(s); 
    }

    // 점수를 평점으로 변환하는 로직 
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

    public int getCredit() {
        return credit;
    }
    public double getPoint() {
    return point; 
    }
}