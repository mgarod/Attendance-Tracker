package Information;

public enum Professor {
    PAVEL_SHOSTAK("Pavel Shostak"),
    JULIO_KUPLINSKY("Julio Kuplinsky"),
    MUSAB_YASIN("Musab Yasin"),
    XIAOKE_SHEN("Xiaoke Shen"),
    BASAK_TAYLAN("Basak_Taylan"),
    MAHDI_MAKKI("Mahdi Makki"),
    BENJAMIN_GARRETT("Benjamin Garrett"),
    ANNA_WISNIEWSKA("Anna Wisniewska"),
    CULLEN_SCHAFFER("Cullen Schaffer"),
    GENNADIY_MARYASH("Gennadiy Maryash"),
    WILLIAM_SAKAS("William Sakas"),
    SUBASH_SHANKAR("Subash Shankar"),
    JUSTIN_TOJEIRA("Justin Tojeira"),
    ILYA_KORSUNSKIY("Ilya Korsunskiy"),
    ALEXEY_NIKOLAEV("Alexey Nikolaev"),
    ERIC_SCHWEITZER("Eric Schweitzer"),
    SAADEDDINE_MNEIMNEH("Saadeddine Mneimneh"),
    SEVERIN_NGOSSE("Severin Ngosse"),
    EDMOND_LLESHI("Edmond Lleshi"),
    CHRISTINA_ZAMFIRESCU("Christina Zamfirescu"),
    SIMON_AYZMAN("Simon Ayzman");

    private String name;

    Professor(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
