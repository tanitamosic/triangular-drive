package com.NWT_KTS_project.model.enums;

public enum CarType {
    LIMOUSINE("Luksuzno vozilo"), // lux
    VAN("Prevoz većeg broja putnika ili velikih tereta"), // prevoz tereta
    STANDARD("Standardno putničko vozilo"),
    MINIVAN("Prevoz većeg broja putnika"), // vise ljudi i ili prevoz tereta
    CARAVAN("Veći prostor za prtljag"), // vise prostora u gepeku
    SUV("Prostrano terensko vozilo");

    public final String desctiption;

    private CarType(String desctiption){
        this.desctiption = desctiption;
    }
}
