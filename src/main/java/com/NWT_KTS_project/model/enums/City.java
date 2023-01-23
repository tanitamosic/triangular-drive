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
    public String getName() {return this.name;}

    public static City fromString(String text) {
        for (City c : City.values()) {
            if (c.name.equalsIgnoreCase(text)) {
                return c;
            }
        }
        return null;
    }
}
