package service;


import model.Board;
import model.BoardImpl;
import model.PlayerImpl;

public class ArmiesService {
    /**
    Armies by country ownership:
     Rule says : nb countries / 3
     or min of 3 for whatever if ownership less than 9
     */
    public static int armiesByCountryOwnership(PlayerImpl player) {
        int numberOfCountries = player.getOwnerships().size();

        if (numberOfCountries <= 9) {
            return 3;
        }
        else {
            return numberOfCountries/3;
        }
    }

    /**
     * Returns number of armies based on the continent value if its owned
     * @param board
     * @param player
     * @return total sum of armies for all the continents owned
     */
    public static int armiesByContinentOwnership(BoardImpl board, PlayerImpl player) {
        int totalArmies = 0;
        for(Integer continentId : OwnershipService.continentOwnershipByPlayer(board, player)) {
            totalArmies += board.getContinents().get(continentId).getContinentArmy();
        }
        return totalArmies;
    }

    /**
     * In manual is required 40 for 42 countries, but it may happen more countries can exist. To ensure coherence with the rule I proposeà 2x.
     * @param player
     * @return armies = 2 * number of countries
     */
    public static int armiesByInitialGame(PlayerImpl player) {
        return player.getOwnerships().size()*2;
    }

    public static int armiesProvider(BoardImpl board, PlayerImpl player, boolean firstPlay) {
        if (firstPlay) {
            return armiesByInitialGame(player); // We only assign the minimum nothing else for game setup
        }
        else {
            return armiesByContinentOwnership(board, player) + armiesByCountryOwnership(player);
        }
    }

}
