package Information;

import java.util.ArrayList;
import java.util.Arrays;

public enum Course {
    /*CSCI_12000("CSCI 120",
            new ArrayList<Professor>(Arrays.asList(
                    Professor.SYADU_OOSEPFRIS,
                    Professor.JONATHAN_GALSURKAR,
                    Professor.PARADORN_WONGCHANAPAI,
                    Professor.KONSTANTINOS_POULIASIS,
                    Professor.MUSAB_YASIN,
                    Professor.IRIS_HERSHENSON,
                    Professor.KADRI_BROGI,
                    Professor.PIOTR_KAPELA,
                    Professor.HENRY_WONG,
                    Professor.ANOOP_AROOR,
                    Professor.WENDELL_WILLIAMS,
                    Professor.STAFF))),
    */
    CSCI_12700("CSCI 127",
            new ArrayList<Professor>(Arrays.asList(
                    Professor.BENJAMIN_GARRETT,
                    Professor.MAHDI_MAKKI,
                    Professor.XIAOKE_SHEN,
                    Professor.PAVEL_SHOSTAK,
                    Professor.BASAK_TAYLAN,
                    Professor.ADRIANA_WISE,
                    Professor.MICHAEL_ZAMANSKY,
                    Professor.STAFF))),
    /*CSCI_13300("CSCI 133",
            new ArrayList<Professor>(Arrays.asList(
                    Professor.JONATHAN_GALSURKAR,
                    Professor.CULLEN_SCHAFFER,
                    Professor.SYED_ALIAHMED,
                    Professor.GENNADIY_MARYASH,
                    Professor.STAFF))),
    */
    CSCI_13500("CSCI 135",
            new ArrayList<Professor>(Arrays.asList(
                    Professor.SUBASH_SHANKAR,
                    Professor.JIA_XU,
                    Professor.STAFF))),
    CSCI_13600("CSCI 136",
            new ArrayList<Professor>(Arrays.asList(
                    Professor.SYED_ALIAHMED,
                    Professor.ALI_ELSAYED,
                    Professor.ALEXEY_NIKOLAEV,
                    Professor.GILBERT_PAJELA,
                    Professor.STAFF))),
    CSCI_15000("CSCI 150",
            new ArrayList<Professor>(Arrays.asList(
                    Professor.ZHAOHENG_NI,
                    Professor.CULLEN_SCHAFFER,
                    Professor.ERIC_SCHWEITZER,
                    Professor.MARTIN_VASAS,
                    Professor.STAFF))),
    CSCI_16000("CSCI 160",
            new ArrayList<Professor>(Arrays.asList(
                    Professor.EDMOND_LLESHI,
                    Professor.MUSAB_YASIN,
                    Professor.CHRISTINA_ZAMFIRESCU,
                    Professor.STAFF))),
    CSCI_23500("CSCI 235",
            new ArrayList<Professor>(Arrays.asList(
                    Professor.SIMON_AYZMAN,
                    Professor.STEWART_WEISS,
                    Professor.STAFF)));

    private String name;
    private ArrayList<Professor> professors;

    Course(String name, ArrayList<Professor> professors) {
        this.name = name;
        this.professors = professors;
    }

    @Override
    public String toString() {
        return name;
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
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
