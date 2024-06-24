package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CountryImpl implements Country {
    String countryName;
    int countryId;
    int continentId;
    List<Integer> borders;
    String ownership;
    int armies;
}
