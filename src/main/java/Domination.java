import constants.constants;
import model.BoardImpl;
import model.PlayerImpl;
import service.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;

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

        // Random allocation of armies per ownership of each player
        ArmiesService.armiesRandomAllocationPerPlayer(board, players.getFirst());
        ArmiesService.armiesRandomAllocationPerPlayer(board, players.getLast());

        int turn = 1; //by default, we just start with player one

        PlayerImpl p1 = players.getFirst();
        PlayerImpl p2 = players.getLast();

        Random rand = new Random();

        while (PlayerService.playerAlive(p1) && PlayerService.playerAlive(p2)) {
            //reinforcement of troops -- done randomnly

            //checks if game is possible to continue or declares Draw
            if (PlayerService.declareDraw(players)) {
                System.out.println("Draw. Game is finished.");
                break;
            }

            //Game ownership status is launch
            GameStatusService.ownershipStatus(username, players);
            if (GameRulesService.diceRollup(players, board, turn) == -1) { //we relaunch the choice because the country didn't have borders to attack
                GameRulesService.diceRollup(players, board, turn);
            };
            //shifting turns
            switch (turn) {
                case 1:
                    System.out.println(">> Turn of " + p2.getName() + " to play");

                    int newTroopsP2 = ArmiesService.armiesProvider(board, p2, false);
                    int randomCountryP2 = rand.nextInt(0, p2.getOwnerships().size()-1);
                    newTroopsP2 += p2.getOwnerships().get(randomCountryP2).getArmies(); //to refine because it's overwriting
                    p2.getOwnerships().get(randomCountryP2).setArmies(newTroopsP2);
                    System.out.println(">> Troops reinforcement have arrived.");
                    System.out.println(">>" + newTroopsP2 + " Troops reinforcement have arrived. They were all sent to a random country.");

                    turn++;
                    break;

                case 2:
                    System.out.println(">> Turn of " + p1.getName() + " to play");

                    int newTroopsP1 = ArmiesService.armiesProvider(board, p1, false);
                    int randomCountryP1 = rand.nextInt(0, p1.getOwnerships().size()-1);
                    newTroopsP1 += p1.getOwnerships().get(randomCountryP1).getArmies();
                    p1.getOwnerships().get(randomCountryP1).setArmies(newTroopsP1);
                    System.out.println(">>" + newTroopsP1 + " Troops reinforcement have arrived. They were all sent to a random country.");

                    turn--;
                    break;
            }

            //ACT
        /* Game sequence
        1. Choose the player name: PlayerService OK
        2. Assign randomly the countries: OwnershipService OK
        3. Define armies initial distribution and do random allocation as well (to simplify). OK
        3. Show ownerships to each player: GameStatusService OK
        4. Assign turns until a player is "alive": TurnsService
        4.5 Troops' reinforcement:
            - Define the rule of cards and deck, are they going to be implemented?
            - Once the capture happens, and number of attacks, how to reinforce troops per player.
        5. Ask for a move: MoveExecutionService
        6. Execute the move checking whether is valid: ©
        7. Define game continuation rules : a Game check service to do so ?
        7. Repeat the cycle until world is conquered by one player:
         */


        }
        System.out.println("Game is finished.");

    }
}

