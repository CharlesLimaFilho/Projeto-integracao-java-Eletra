package Enums;

import java.util.Arrays;
import java.util.List;

public enum Categories {
    CRONOS_OLD("Cronos_Old", Arrays.asList(Models.CRONOS_6001_A, Models.CRONOS_6003, Models.CRONOS_7023)),
    CRONOS_L("Cronos_L", Arrays.asList(Models.CRONOS_6021L, Models.CRONOS_7023L)),
    CRONOS_NG("Cronos_NG", Arrays.asList(Models.CRONOS_6001_NG, Models.CRONOS_6003_NG, Models.CRONOS_6021_NG,
            Models.CRONOS_6031_NG, Models.CRONOS_7021_NG, Models.CRONOS_7023_NG)),
    ARES_TB("Ares_TB", Arrays.asList(Models.ARES_7021, Models.ARES_7031, Models.ARES_7023)),
    ARES_THS("Ares_THS", Arrays.asList(Models.ARES_8023_15, Models.ARES_8023_200, Models.ARES_8023_25));

    public final String name;
    public List<Models> models;

    Categories(String name, List<Models> models) {
        this.name = name;
        this.models = models;
    }
}