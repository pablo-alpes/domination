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
        List<Country> countriesCopy = board.getCountries(); // TODO-- deep copy needed, to check how to do so
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

}
