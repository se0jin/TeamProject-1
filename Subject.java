import java.util.Scanner;
import java.util.InputMismatchException;
/**
 * Subject 시스템에 등록된 과목 정보(번호, 이름)를 관리하는 클래스
 * 
 * @author (유서진, 이지헌, 장서윤)
 * @version (2026.05.13)
 */
class Subject {
    int id; //과목 번호
    String name; //과목 이름

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }
}