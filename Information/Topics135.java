package Information;

public enum Topics135 {
    CONTROL_FLOW_AND_LOOPS("Flow of Control / Loops"),
    FUNCTIONS("Functions"),
    ARRAYS("Arrays"),
    RECURSION("Recursion"),
    STRINGS("Strings"),
    STREAMS_AND_FILE_IO("Streams / File IO"),
    CHARACTERS_AND_ASCII_CODE("Characters / ASCII CODE"),
    CLASSES_STRUCTS_OOP("Classes / Structs / OOP"),
    PARAMETERS_REFERENCE_AND_VALUE("Call by Reference & Value"),
    OPERATOR_OVERLOADING("Operator Overloading"),
    POINTERS_AND_DYNAMIC_ARRAYS("Pointers / Dynamic Arrays"),
    SEPERATE_COMPILATION_LINUX("Seperate Compilation / Linux"),
    LINKED_DATA_STRUCTURES("Linked Data Structures"),
    OTHER("Other");

    private String name;

    Topics135(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
