package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContinentImpl implements Continent {
    String continentName;
    int continentId;
    int continentArmy;
    String ownership;

    public ContinentImpl(String continentName, int continentId, int continentArmy, String ownership) {
        this.continentName = continentName;
        this.continentId = continentId;
        this.continentArmy = continentArmy;
        this.ownership = ownership;
    }
}

