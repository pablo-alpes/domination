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
    String ownership; //TODO -- Extract this value from this class since it belongs to user finally; and can be calculated instead

    public ContinentImpl(String continentName, int continentId, int continentArmy, String ownership) {
        this.continentName = continentName;
        this.continentId = continentId;
        this.continentArmy = continentArmy;
        this.ownership = ownership;
    }
}

