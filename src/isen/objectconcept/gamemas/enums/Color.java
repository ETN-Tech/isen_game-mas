package isen.objectconcept.gamemas.enums;

public enum Color {
    RED("\u001B[31m "),
    YELLOW("\u001B[33m "),
    BLUE("\u001B[34m "),
    PURPLE("\u001B[35m "),
    GREEN("\u001B[32m "),
    DEFAULT_COLOR("\u001B[0m ")
    ;

    String code;
    Color(String color_code) {
        code = color_code;
    }

    @Override
    public String toString() {
        return code;
    }
}
