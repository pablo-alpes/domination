package model;

import java.util.List;

public interface Country {
    String countryName = null;
    int countryId = 0;
    int continentId = 0;
    List<Integer> borders = null;
    String ownership = "";
    int armyPayOff = 0;

    void setBorders(List<Integer> borders);
    List<Integer> getBorders();

    int getCountryId();

    String getCountryName();
}
