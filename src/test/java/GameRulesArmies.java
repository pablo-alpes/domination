import constants.constants;
import model.Board;
import model.BoardImpl;
import model.Continent;
import model.PlayerImpl;
import service.MapFetchService;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.boot.SpringBootConfiguration;
import service.OwnershipService;
import service.PlayerService;
import service.UserRegistrationService;

import static org.junit.Assert.*;

@SpringBootConfiguration
public class GameRulesArmies {
    //TODO-- Dependency Injection to Implement
    // @Autowired
    //public MapFetchService mapFetchService;

    @Test
    @DisplayName("Checks a player owns a continent")
    public void GivenAMapWhenPlayerOwnsCountriesSetThenDetermineIfContinentOwnership() throws FileNotFoundException {
        //Since the random allocation, the test is done with spain which has more chances to assign a continent and be true (one city = one continent);
        //For a small number of continents, this test will likely fail despite it works properly (several cities = one continent).

        //ARRANGE -- Setting up the game board and players
        String filename = constants.MAP;

        BoardImpl board = new BoardImpl();
        board = MapFetchService.translatorToBoard(filename);
        System.out.println("Let's begin - Default map is <Spain>");

        //User registration and players creation
        String username = "Chilean God";
        List<PlayerImpl> players = PlayerService.createPlayers(username);
        // Random allocation of countries ownership
        OwnershipService.randomCountryAllocation(players, board); //we send the initial board to do the random setting

        //ACT
        List<Integer> continents = OwnershipService.continentOwnershipByPlayer(board, players.getFirst());
        System.out.println(continents);
        System.out.println(continents.size());

        //ASSERT
        assertNotEquals(0, continents.size());
    }
}
