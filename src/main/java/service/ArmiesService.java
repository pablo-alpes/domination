package service;


import model.BoardImpl;
import model.Country;
import model.PlayerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ArmiesService {
    /**
     * Armies by country ownership:
     * Rule says : nb countries / 3
     * or min of 3 for whatever if ownership less than 9
     */
    public static int armiesByCountryOwnership(PlayerImpl player) {
        int numberOfCountries = player.getOwnerships().size();

        if (numberOfCountries <= 9) {
            return 3;
        } else {
            return numberOfCountries / 3;
        }
    }

    /**
     * Returns number of armies based on the continent value if its owned
     *
     * @param board
     * @param player
     * @return total sum of armies for all the continents owned
     */
    public static int armiesByContinentOwnership(BoardImpl board, PlayerImpl player) {
        int totalArmies = 0;
        for (Integer continentId : OwnershipService.continentOwnershipByPlayer(board, player)) {
            totalArmies += board.getContinents().get(continentId).getContinentArmy();
        }
        return totalArmies;
    }

    /**
     * In manual is required 40 for 42 countries, but it may happen more countries can exist. To ensure coherence with the rule I proposeà 2x.
     *
     * @param player
     * @return armies = 2 * number of countries - 10% (to make some countries have less Strength)
     */
    public static int armiesByInitialGame(PlayerImpl player) {
        return (int) (player.getOwnerships().size() * 3 * (1 - 0.1)); //TODO -- 3 so to test better the possibilities within the scenario
    }

    public static int armiesProvider(BoardImpl board, PlayerImpl player, boolean firstPlay) {
        if (firstPlay) {
            return armiesByInitialGame(player); // We only assign the minimum nothing else for game setup
        } else {
            return armiesByContinentOwnership(board, player) + armiesByCountryOwnership(player);
        }
    }

    public static void armiesRandomAllocationPerPlayer(BoardImpl board, PlayerImpl player) {
        int totalArmies = armiesProvider(board, player, true); //assumes this is only for the first turn
        List<Country> countries = new ArrayList<Country>(player.getOwnerships());

        while (totalArmies > 0) {
            if (totalArmies == 0) {
                break;
            }
            for (Country country : countries) {
                country.setArmies(country.getArmies() + 1);
                totalArmies = totalArmies - 1;

                if ((totalArmies > 0) & (countries.getLast() == country)) { // still troops remaining and all countries transversed, we restart
                    countries = countries.reversed(); //we turn tables
                    country = countries.getFirst();
                }
                if (totalArmies == 0) {
                    break;
                }
            }
        }

    }

    /**
     * Returns for the country/city and player asked if whether it's owned by player
     * and the army count if the case
     * @param player
     * @param mapName (String)
     * @return int army count for the country (input in String)
     */
    public static int armiesForPlayerByCountryName(PlayerImpl player, String mapName) {
        int armiesForTheCountryPlayer;

        try {
            armiesForTheCountryPlayer = (player.getOwnerships()).stream()
                    .filter(country -> (country.getCountryName()).equals(mapName))
                    .toList()
                    .getFirst()
                    .getArmies();
        } catch (NoSuchElementException e) {
            armiesForTheCountryPlayer = -1;
        }
        return armiesForTheCountryPlayer;
    }

}



