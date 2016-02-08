package Information;

public enum Topics135 {
    CONTROL_FLOW_AND_LOOPS("Control Flow / Loops"),
    FUNCTIONS("Functions"),
    ARRAYS("Arrays"),
    RECURSION("Recursion"),
    CHARACTERS_AND_ASCII_CODE("Characters / ASCII Code"),
    STRINGS("Strings"),
    STREAMS_AND_FILE_IO("Streams / File IO"),
    PARAMETERS_REFERENCE_AND_VALUE("Call by Reference & Value"),
    OPERATOR_OVERLOADING("Operator Overloading"),
    CLASSES_STRUCTS_OOP("Classes / Structs / OOP"),
    SEPARATE_COMPILATION_LINUX("Separate Compilation / Linux"),
    POINTERS_AND_DYNAMIC_ARRAYS("Pointers / Dynamic Arrays"),
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
