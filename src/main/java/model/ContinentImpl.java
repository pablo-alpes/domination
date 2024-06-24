package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ContinentImpl implements Continent {
    String continentName;
    int continentId;
    int continentArmy;
    String ownership;
    List<Country> countries;

    public ContinentImpl(String continentName, int continentId, int continentArmy, String ownership) {
        this.continentName = continentName;
        this.continentId = continentId;
        this.continentArmy = continentArmy;
        this.ownership = ownership;
    }
}

