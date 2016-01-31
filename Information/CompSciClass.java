package Information;

public class CompSciClass {

    private Courses course;
    private String professor;

    public CompSciClass(Courses course, String professor) {
        this.course = course;
        this.professor = professor;
    }

    public String getCourse() {
        return course.toString();
    }

    public String getProfessor() {
        return professor;
    }

    @Override
    public String toString() {
        return course.toString();
    }
}
