package com.NWT_KTS_project.model.enums;

public enum Color {
    BLUE("Blue"),
    RED("Red"),
    BLACK("Black"),
    WHITE("White"),
    GREY("Grey");

    public final String name;

    private Color (String name){
        this.name = name;
    }
}
