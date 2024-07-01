package model;

import java.util.List;

public interface Continent {
    String continentName = null;
    int continentId = 0;
    int continentArmy = 0;
    String ownership = null;
    List<Country> countries = null;

    public int getContinentArmy();

}
