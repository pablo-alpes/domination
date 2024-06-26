import model.BoardImpl;
import service.MapFetchService;

import java.io.FileNotFoundException;

public class Domination {
    public static void main(String... args) throws FileNotFoundException {

        System.out.println("Domination game -- To conquer the world");

        //Map selection
        String filename = "spain/spain.map";
        BoardImpl board = new BoardImpl();
        board = MapFetchService.translatorToBoard(filename);
        System.out.println("Let's begin - Default map is <Spain>");

        //ACT
        /* Game sequence
        1. Choose the player name: PlayerService
        2. Assign randomly the countries: OwnershipService
        3. Show ownerships to each player: GameStatusService
        4. Assign turns until a player is "alive": TurnsService
        5. Ask for a move: MoveExecutionService
        6. Execute the move checking whether is valid: MoveCheckService
        7. Repeat the cycle until world is conquered by one player:
         */

    }
}
