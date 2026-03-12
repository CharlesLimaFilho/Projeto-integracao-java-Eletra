package Enums;

import java.util.Arrays;
import java.util.List;

public enum Lines {
    CRONOS("Cronos", Arrays.asList(Categories.CRONOS_L, Categories.CRONOS_NG,
            Categories.CRONOS_OLD)),
    ARES("Ares", Arrays.asList(Categories.ARES_TB, Categories.ARES_THS));

    public String name;
    public List<Categories> categories;

    Lines(String name,
          List<Categories> categories) {
        this.name = name;
        this.categories = categories;
    }
}