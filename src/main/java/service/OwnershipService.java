package service;

import constants.constants;
import model.*;

import java.util.*;

public class OwnershipService {

    /**
        Method assigns quasi-randomly the countries distribution for 2 players
        The stop condition: 1 / number of players (even distribution of countries)

        The method does a random search based on the initial copy of the board
        The found results are assigned to player 1 and the remainder to the other.

        @return Players with updated ownerships

        TODO -- Armies per country. Requires rule of the number of armies at the beginning (read risk rules).

     */
    public static void randomCountryAllocation(List<PlayerImpl> players, BoardImpl board) {
        int countriesSize = board.getCountries().size();
        List<Country> countriesCopy = new ArrayList<Country>(board.getCountries()); // Deep copy of the list:  https://stackoverflow.com/questions/10457087/how-to-copy-java-util-list-collection
        int evenDistribution = countriesSize / constants.PLAYERS;

        for (int i = 0; i < evenDistribution; i++) {
            //random search
            int randomSearchId = randomIntRange(0, countriesSize); //random id within the range (0, countriesSize) to get the respective country
            //identifies the country found from the random search id in the remainder list to player 1
            Country countryFound = countriesCopy.get(randomSearchId);
            try {
                players.getFirst().getOwnerships().add(countryFound); //add the random country found
            }
            catch(NullPointerException e) {
                players.getFirst().setOwnerships(Collections.singletonList(countryFound));
            }
            // remove from copy the result found
            countriesCopy.remove(countryFound);

            // updating the size of the list to remaining ones
            countriesSize = countriesCopy.size();
        }
        //assign remainder copy to the other player
        players.getLast().setOwnerships(countriesCopy); //countries copy will become the remainder countries after removal
    }

    public static int randomIntRange(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }

    /**
     * Which  continents does the player own knowing their country ownerships?
     *
     * @param board
     * @param player
     * @return  Lists the continentId which he owns based on their country ownership
     */
    public static List<Integer> continentOwnershipByPlayer(BoardImpl board, PlayerImpl player) {
        List <Continent> continentsBoard = new ArrayList<Continent>(board.getContinents());
        List<Country> countriesBoard = new ArrayList<Country>(board.getCountries()); // I can get the maps continent id , List<countries>
        List<Integer> continentsOwned = new ArrayList<Integer>();

        //checking for each continent, the players' countries' ownership
        for (int i = 0; i < continentsBoard.size(); i++) {
            int finalI = i;

            //NB : The game dynamic of ownership is capture in the player object.
            List<Country> playerContinentCountries = player.getOwnerships().stream().filter(country -> country.getContinentId() == (finalI +1)).toList();
            List<Country> totalCountriesInContinent = countriesBoard.stream().filter(country -> country.getContinentId() == (finalI +1)).toList();
            if (playerContinentCountries.containsAll(totalCountriesInContinent)) { //checks the subset of countries for the continent the player owns correspond to a whole continent
                continentsOwned.add(i+1); //if so, we add the continent Id
            }
        }
        return continentsOwned;
    }

}
