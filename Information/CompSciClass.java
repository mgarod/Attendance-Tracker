package Information;

public class CompSciClass {

    private Course course;
    private Professor professor;

    public CompSciClass(Course course, Professor professor) {
        this.course = course;
        this.professor = professor;
    }

    public Course getCourse() {
        return course;
    }

    public Professor getProfessor() {
        return professor;
    }

    @Override
    public String toString() {
        return course.toString();
    }
}
