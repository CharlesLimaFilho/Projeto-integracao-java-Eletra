package Enums;

public enum Models {
    CRONOS_6001_A("Cronos6001_A"),
    CRONOS_6003("Cronos_6003"),
    CRONOS_7023("Cronos_7023"),
    CRONOS_6021L("Cronos_6021L"),
    CRONOS_7023L("Cronos_7023L"),
    CRONOS_6001_NG("Cronos6001_NG"),
    CRONOS_6003_NG("Cronos_6003_NG"),
    CRONOS_6021_NG("Cronos_6021_NG"),
    CRONOS_6031_NG("Cronos_6031_NG"),
    CRONOS_7021_NG("Cronos_7021_NG"),
    CRONOS_7023_NG("Cronos_7023_NG"),
    ARES_7021("Ares_7021"),
    ARES_7031("Ares_7031"),
    ARES_7023("Ares_7023"),
    ARES_8023_15("Ares_8023_15"),
    ARES_8023_200("Ares_8023_200"),
    ARES_8023_25("Ares_8023_2,5");

    public String name;

    Models(String name) {
        this.name = name;
    }
}