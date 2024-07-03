package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public class BoardImpl implements Board {
    List<Continent> continents = new ArrayList<Continent>();
    List<Country> countries = new ArrayList<Country>();
    List<Player> players = new ArrayList<Player>();

    public void addCountry(Country country) {
        countries.add(country);
    }

    public void addContinent(Continent continent) {
        continents.add(continent);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public List<Continent> getContinents() {
        return continents;
    }

    public void setContinents(List<Continent> continents) {
        this.continents = continents;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
