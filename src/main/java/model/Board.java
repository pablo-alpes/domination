package model;

import java.util.List;

public interface Board {

    void addCountry(Country country);

    void addContinent(Continent continent);
    void addPlayer(Player player);
    List<Continent> getContinents() ;

    void setContinents(List<Continent> continents);

    List<Country> getCountries();

    void setCountries(List<Country> countries);

    List<Player> getPlayers();

    void setPlayers(List<Player> players);
}
