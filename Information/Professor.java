package Information;

public enum Professor {
    PAVEL_SHOSTAK("Pavel Shostak"),
    JULIO_KUPLINSKY("Julio Kuplinsky"),
    MUSAB_YASIN("Musab Yasin"),
    XIAOKE_SHEN("Xiaoke Shen"),
    BASAK_TAYLAN("Basak Taylan"),
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
    SIMON_AYZMAN("Simon Ayzman"),
    JONATHAN_GALSURKAR("Jonathan Galsurkar"),
    PARADORN_WONGCHANAPAI("Paradorn Wongchanapai"),
    HENRY_WONG("Henry Wong"),
    WENDELL_WILLIAMS("Wendell Williams"),
    KADRI_BROGI("Kadri Brogi"),
    AUBREY_ETWAROO("Aubrey Etwaroo"),
    IRIS_HERSHENSON("Iris Hershenson"),
    SYADU_OOSEPFRIS("Syadu Oosepfris"),
    ALI_ELSAYED("Ali Elsayed"),
    KONSTANTINOS_POULIASIS("Konstantinos Pouliasis"),
    PIOTR_KAPELA("Piotr Kapela"),
    ANOOP_AROOR("Anoop Aroor"),
    MICHAEL_ZAMANSKY("Michael Zamansky"),
    ADRIANA_WISE("Adriana Wise"),
    SYED_ALIAHMED("Syed Ali Ahmed"),
    JIA_XU("Jia Xu"),
    SAPTARSHI_DEBROY("Saptarshi Debroy"),
    GILBERT_PAJELA("Gilbert Pajela"),
    MARTIN_VASAS("Martin Vasas"),
    ZHAOHENG_NI("Zhaoheng Ni"),
    STEWART_WEISS("Stewart Weiss"),
    MINH_NGUYEN("Minh Nguyen"),
    SILVANO_BERNABEL("Silvano Bernabel"),
    PRIYANKA_SAMANTA("Priyanka Samanta"),
    MICHAEL_GAROD("Michael Garod"),
    AARSH_VORA("Aarsh Vora"),
    RAFFI_KHATCHADOURIAN("Raffi Khatchadourian"),
    STAFF("STAFF");

    private String name;

    Professor(String name) {
        this.name = name;
    }

    public static Professor getProfessor(String name) {
        for (Professor professor : Professor.values()) {
            if (name.equals(professor.name)) {
                return professor;
            }
        }

        throw new IllegalArgumentException(name + " is not a valid Professor's name");
    }

    @Override
    public String toString() {
        return name;
    }
}
