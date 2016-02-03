package Information;

public enum Tutor {
    KARAN_BOODWA("Karan Boodwa"),
    GIL_DEKEL("Gil Dekel"),
    JOHNNY_GALSURKAR("Johnny Galsurkar"),
    MICHAEL_GAROD("Michael Garod"),
    AARSH_VORA("Aarsh Vora");

    private String name;

    Tutor(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
