package Information;

public class CompSciClass {

    private Course course;
    private String professor;

    public CompSciClass(Course course, String professor) {
        this.course = course;
        this.professor = professor;
    }

    public Course getCourse() {
        return course;
    }

    public String getProfessor() {
        return professor;
    }

    @Override
    public String toString() {
        return course.toString();
    }
}
