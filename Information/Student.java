package Information;

import java.util.ArrayList;

public class Student {

    private ArrayList<CompSciClass> currentCourses;
    private ClassYear year;
    private String firstName;
    private String lastName;
    private int emplId;

    public Student(int emplId, String firstName, String lastName, ClassYear year) {
        this.emplId = emplId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
    }

    public ArrayList<CompSciClass> getCurrentClasses() {
        return currentCourses;
    }

    public void setCurrentClasses(ArrayList<CompSciClass> currentClasses) {
        this.currentCourses = currentClasses;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getEmplId() {
        return emplId;
    }

    public void setEmplId(int emplId) {
        this.emplId = emplId;
    }

    public ClassYear getYear() {
        return year;
    }
}
