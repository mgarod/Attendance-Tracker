package Information;

public enum ClassYear {
    FRESHMAN, SOPHOMORE, JUNIOR, SENIOR, POST_BACC, NON_DEGREE, OTHER;

    ClassYear() {}

    public static ClassYear getClassYear(String classYear) {
        for (ClassYear cy : ClassYear.values()) {
            if (cy.name().equals(classYear)) {
                return cy;
            }
        }
        throw new IllegalArgumentException(classYear + " is not a valid ClassYear");
    }
}
