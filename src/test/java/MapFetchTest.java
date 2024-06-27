import model.*;
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
public class MapFetchTest {
    //TODO-- Dependency Injection to Implement
    // @Autowired
    //public MapFetchService mapFetchService;

    @Test
    @DisplayName("Checks file is a valid map")
    public void GivenMapWhenReadThenChecksFileIntegrity() throws FileNotFoundException {
        //ARRANGE
        //String mapFile = constants.PATH + "brazil/brasil.map";
        String filename = "spain/spain.map";
        int checks = 0;

        //ACT
        checks = MapFetchService.integrityCheck(filename);
        //ASSERT
        assertEquals(checks,3);

    }
    @Test
    @DisplayName("Map correctly 2 continents from  2 line parsed")
    public void GivenMapWhenContinentFoundThenMapContinentObjectList() {
        //Example1 "Galicia 2 verde"
        //Example2 "South 2 yellow"
        //ARRANGE
        String continentLine = "Galicia 2 verde";
        //ACT
        ContinentImpl continent = MapFetchService.populateContinents(continentLine);
        //ASSERT
        assertEquals(continent.getContinentName(), "Galicia");
        assertEquals(continent.getContinentArmy(), 2);

        continentLine = "South 3 yellow";

        //ACT
        continent = MapFetchService.populateContinents(continentLine);
        //ASSERT
        assertEquals(continent.getContinentName(), "South");
        assertEquals(continent.getContinentArmy(), 3);
    }

    @Test
    @DisplayName("Map correctly 2 countries from 2 lines passed")
    public void GivenMapWhenCountryFoundThenMapContinentObjectList() {
        //1 La-Coruna 1 59 39
        //2 Lugo 1 98 42
        //ARRANGE
        String countryLine = "1 La-Coruna 1 59 39";
        //ACT
        CountryImpl country = MapFetchService.populateCountries(countryLine);
        //ASSERT
        assertEquals(country.getCountryId(), 1);
        assertEquals(country.getCountryName(), "La-Coruna");
        assertEquals(country.getContinentId(), 1);

        countryLine = "2 Lugo 1 98 42";

        //ACT
        country = MapFetchService.populateCountries(countryLine);
        //ASSERT
        assertEquals(country.getCountryId(), 2);
        assertEquals(country.getCountryName(), "Lugo");
        assertEquals(country.getContinentId(), 1);
    }

    @Test
    @DisplayName("Map correctly 2 borders from 2 lines passed")
    public void GivenMapWhenBordersFoundThenMapBordersObjectList() {
        //Assumption: Borders will be an attribute of the corresponding country
        //[borders]
        // 1= 2 3
        // 2= 1 3 4 5 18
        // 3= 1 2 4
        //1st parameter is the countryId, rest are the country bordersId
        //ARRANGE
        String borderLine = "1 2 3";
        //ACT
        CountryImpl country = new CountryImpl();
        country = MapFetchService.populateBorders(borderLine);

        //ASSERT
        assertTrue(country.getBorders().contains(2));
        assertTrue(country.getBorders().contains(3));
        assertFalse(country.getBorders().contains(4));

    }


    //@Test
    //@DisplayName("Map correctly a continent from the map file")

    @Test
    @DisplayName("Fetches map from file map and does the mapping towards the object Continent and Country model")
    public void GivenMapWhenReadThenFetchMapToObjectModel() throws FileNotFoundException {
        //ARRANGE
        String filename = "spain/spain.map";
        BoardImpl board = new BoardImpl();
        //ACT
        board = MapFetchService.translatorToBoard(filename);
        //ASSERT
        System.out.println(board.getCountries().get(51).getBorders());
        assertEquals(19, board.getContinents().size());
        assertEquals(52, board.getCountries().size());
        assertEquals(3, board.getCountries().get(51).getBorders().size()); //spain test: tests the latest case of the borders list has been correctly parsed.
    }

    @Test
    @DisplayName("Random and even distribution of countries between 2 players")
    public void GivenPlayersAndBoardThenDistributeRandomlyCountriesThenReturnPlayersOwnership() throws FileNotFoundException {
        //ARRANGE
        String filename = "spain/spain.map";
        BoardImpl board = new BoardImpl();
        board = MapFetchService.translatorToBoard(filename);

        //User registration and players creation
        String username = "Chilean God";
        List<PlayerImpl> players = PlayerService.createPlayers(username);
        //ACT
        // Random allocation of countries ownership
        OwnershipService.randomCountryAllocation(players, board);
        //ASSERT
        assertEquals(players.getFirst().getOwnerships().size(),52/2);
        assertEquals(players.getLast().getOwnerships().size(),52/2);

        System.out.println("Ownership status");
        System.out.println(username + " v/s Boring machine");
        System.out.println("--------------------------------");
        for (Player player : players) {
            for (int i = 0; i < players.getFirst().getOwnerships().size(); i++) {
                System.out.println(players.getFirst().getOwnerships().get(i).getCountryName() + "       *         " + players.getLast().getOwnerships().get(i).getCountryName());
            }
        }
    }

    @Test
    @DisplayName("Ownership status per player")
    public void OwnershipCountriesPerPlayer() throws FileNotFoundException {
        GivenPlayersAndBoardThenDistributeRandomlyCountriesThenReturnPlayersOwnership();

    }


}

