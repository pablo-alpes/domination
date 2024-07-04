import constants.constants;
import model.BoardImpl;
import model.Country;
import model.PlayerImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.SpringBootConfiguration;
import service.*;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertTrue;;

@SpringBootConfiguration
public class AttackMoveTest {
    //TODO-- Dependency Injection to Implement
    // @Autowired
    //public MapFetchService mapFetchService;

    @Test
    @DisplayName("Player Sets Attack Objectives")
    public void GivenACountryOriginWhenPlayerChoosesItThenDetermineIfOwnershipAndArmiesAreAtLeast2() throws FileNotFoundException {
        //ARRANGE -- Setting up the game board and players
        BoardImpl board;
        String filename = constants.MAP;
        board = MapFetchService.translatorToBoard(filename);
        //User registration and players creation
        String username = "Chilean God";
        List<PlayerImpl> players = PlayerService.createPlayers(username);
        // Random allocation of countries ownership and armies
        OwnershipService.randomCountryAllocation(players, board); //we send the initial board to do the random setting
        ArmiesService.armiesRandomAllocationPerPlayer(board, players.getFirst());
        ArmiesService.armiesRandomAllocationPerPlayer(board, players.getLast());

        //ACT
        String mapName = "Madrid";
        int armiesForTheCountryP1 = ArmiesService.armiesForPlayerByCountryName(players.getFirst(), mapName);
        int armiesForTheCountryP2 = ArmiesService.armiesForPlayerByCountryName(players.getLast(), mapName);

        System.out.println(armiesForTheCountryP1);
        System.out.println(armiesForTheCountryP2);
        //ASSERT
        assertTrue((armiesForTheCountryP1 > 0 || armiesForTheCountryP2 > 0));
    }

    @Test
    @DisplayName("Player Attacks")
    public void player1Attacks() throws FileNotFoundException {
        //ARRANGE -- Setting up the game board and players
        BoardImpl board;
        String filename = constants.MAP;
        board = MapFetchService.translatorToBoard(filename);
        //User registration and players creation
        String username = "Chilean God";
        List<PlayerImpl> players = PlayerService.createPlayers(username);
        // Random allocation of countries ownership and armies
        OwnershipService.randomCountryAllocation(players, board); //we send the initial board to do the random setting
        ArmiesService.armiesRandomAllocationPerPlayer(board, players.getFirst());
        ArmiesService.armiesRandomAllocationPerPlayer(board, players.getLast());

        //act
        //String countryFrom = players.getFirst().getOwnerships().getFirst().getCountryName();
        GameStatusService.ownershipStatus(username, players);
        GameRulesService.diceRollup(players, board, 2);
        GameStatusService.ownershipStatus(username, players);
    }
}


