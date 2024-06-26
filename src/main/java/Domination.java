import constants.constants;
import model.Board;
import model.BoardImpl;
import model.Player;
import model.PlayerImpl;
import service.*;

import java.io.FileNotFoundException;
import java.util.List;

public class Domination {
    public static void main(String... args) throws FileNotFoundException {

        System.out.println("Domination game -- To conquer the world");

        //Map selection
        String filename = constants.MAP;
        BoardImpl board = new BoardImpl();
        board = MapFetchService.translatorToBoard(filename);
        System.out.println("Let's begin - Default map is <Spain>");

        //User registration and players creation
        String username = UserRegistrationService.getUserName();
        List<PlayerImpl> players = PlayerService.createPlayers(username);
        // Random allocation of countries ownership
        OwnershipService.randomCountryAllocation(players, board); //we send the initial board to do the random setting

        GameStatusService.ownershipStatus(username, players);
        //ACT
        /* Game sequence
        1. Choose the player name: PlayerService
        2. Assign randomly the countries: OwnershipService
        3. Define armies initial distribution and do random allocation as well (to simplify).
        3. Show ownerships to each player: GameStatusService
        4. Assign turns until a player is "alive": TurnsService
        4.5 Troops' reinforcement:
            - Define the rule of cards and deck, are they going to be implemented?
            - Once the capture happens, and number of attacks, how to reinforce troops per player.
        5. Ask for a move: MoveExecutionService
        6. Execute the move checking whether is valid: MoveCheckService
        7. Define game continuation rules : a Game check service to do so ?
        7. Repeat the cycle until world is conquered by one player:
         */

    }
}
