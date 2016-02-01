package Information;

import java.util.ArrayList;
import java.util.Arrays;

public enum Course {
    CSCI_12700("CSCI 127",
            new ArrayList<Professor>(Arrays.asList(
                    Professor.PAVEL_SHOSTAK,
                    Professor.JULIO_KUPLINSKY,
                    Professor.MUSAB_YASIN,
                    Professor.XIAOKE_SHEN,
                    Professor.BASAK_TAYLAN,
                    Professor.MAHDI_MAKKI))),
    CSCI_13500("CSCI 135",
            new ArrayList<Professor>(Arrays.asList(
                    Professor.WILLIAM_SAKAS,
                    Professor.SUBASH_SHANKAR))),
    CSCI_13600("CSCI 136",
            new ArrayList<Professor>(Arrays.asList(
                    Professor.BENJAMIN_GARRETT,
                    Professor.JUSTIN_TOJEIRA,
                    Professor.ILYA_KORSUNSKIY,
                    Professor.ALEXEY_NIKOLAEV))),
    CSCI_15000("CSCI 150",
            new ArrayList<Professor>(Arrays.asList(
                    Professor.ERIC_SCHWEITZER,
                    Professor.SAADEDDINE_MNEIMNEH,
                    Professor.CULLEN_SCHAFFER,
                    Professor.SEVERIN_NGOSSE,
                    Professor.ALEXEY_NIKOLAEV))),
    CSCI_16000("CSCI 160",
            new ArrayList<Professor>(Arrays.asList(
                    Professor.JUSTIN_TOJEIRA,
                    Professor.MUSAB_YASIN,
                    Professor.EDMOND_LLESHI,
                    Professor.CHRISTINA_ZAMFIRESCU))),
    CSCI_23500("CSCI 235",
            new ArrayList<Professor>(Arrays.asList(
                    Professor.JULIO_KUPLINSKY,
                    Professor.SEVERIN_NGOSSE,
                    Professor.SIMON_AYZMAN)));

    private String name;
    private ArrayList<Professor> profesors;

    Course(String name, ArrayList<Professor> professors) {
        this.name = name;
        this.profesors = professors;
    }

    @Override
    public String toString() {
        return name;
    }

    public ArrayList<Professor> getProfesors() {
        return profesors;
    }

    public static Course getCourse(String courseName) {
        for (Course course : Course.values()) {
            if (courseName.equals(course.toString())) {
                return course;
            }
        }

        throw new IllegalArgumentException(courseName + " is not a valid course");
    }
}
