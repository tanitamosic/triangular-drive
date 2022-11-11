package com.NWT_KTS_project.model.enums;

public enum City {
    BG("Beograd"),
    JA("Jagodina"),
    KG("Kragujevac"),
    NI("Niš"),
    NS("Novi Sad"),
    SU("Subotica");

    public final String name;

    private City(String name){
        this.name = name;
    }
}
