package Information;

public enum Tutor {
    GWENAEL_GATTO("Gwenael Gatto"),
    GIL_DEKEL("Gil Dekel"),
    ALLAN_SPEKTOR("Allan Spektor"),
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
