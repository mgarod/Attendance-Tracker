package Information;

public enum Tutor {
    KARAN_BOODWA("Karan Boodwa"),
    GIL_DEKEL("Gil Dekel"),
    MICHAEL_GAROD("Michael Garod"),
    MEREDITH_LANCASTER("Meredith Lancaster"),
    WEI_SHI("Wei Shi");

    private String name;

    Tutor(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
