package service;

import model.BoardImpl;
import model.Country;
import model.PlayerImpl;

import java.util.*;

public class GameRulesService {
    //dice rollup and its result
    public static void diceRollup(List<PlayerImpl> players, BoardImpl board) {
        //player objectives

        //player wants to attack from a country he owns (to check that) : from
        //String countryFrom = getCountryFrom();
        String countryFrom = players.getFirst().getOwnerships().getFirst().getCountryName(); //TODO -- for testing only
        int countryFromId = getCountryIndexOfFromCountryObject(getCountryByNameForPlayer(players.getFirst(), countryFrom), players.getFirst());

        String countryNameTarget;
        int countryTargetId;
        //checks if that country has the mininum of 2 armies to attack
        if (checksPlayerHasMinimumOf2Armies(players.getFirst(), board, countryFrom)) {
            List<Integer> bordersToAttack = new ArrayList<Integer>(getBorders(players.getFirst(), countryFrom));
            //target : player sees options to attack and picks one (options based on borders)
            //and excludes the ones he owns

            bordersToAttack.removeIf(countryId -> (getByCountryIdForPlayer(players.getLast(), countryId) == 0));

            System.out.println("The country you choose to attack from is:"+ countryFrom);
            System.out.println("Your choices to attack are:");
            System.out.println(bordersToAttack);

            countryTargetId = bordersToAttack.getLast(); // to find out where is storaged in the P2
            countryNameTarget = board.getCountries().get(countryTargetId).getCountryName();
            //TODO -- for testing purposes
            int countryTargetIndex = getCountryIndexOfFromCountryObject(getCountryByNameForPlayer(players.getLast(), countryNameTarget), players.getLast());

            System.out.println("You'll attack to:"+ countryNameTarget);
            //tactics of battle
            //attacker: player decide to put number to attack into the target territory : from 1 to 3
            System.out.println("How many armies to deploy to to attack (1 to 3):");
            int p1DiceNumber = 3;
            //defender: AI decides to put number to put to defend : 1 to 2 (from anywhere if the country has not? TBC)
            System.out.println("AI decides to defend with 2 armies");
            int p2DiceNumber = 2;
            //define the number of dices to launch for attacker based on the number of attacker armies (1 to 3 where 1 dice = 1 army)
            // always 2 for defender

            //dices roll up for each player and ordering them
            //https://howtodoinjava.com/java8/convert-intstream-collection-array/
            Random randInt = new Random();
            List<Integer> dicesP1 = randInt.ints(p1DiceNumber, 1, 6)
                    .boxed()
                    .sorted()
                    .toList();
            List<Integer> dicesP2 = randInt.ints(p2DiceNumber, 1, 6)
                    .boxed()
                    .sorted()
                    .toList();
            //organizing pars from max to min values
            dicesP1 = dicesP1.reversed();
            dicesP2 = dicesP2.reversed();
            //counter of end result after battle
            int armiesP1BattleResult = 0;
            int armiesP2BattleResult = 0;

            //result of each dice
            System.out.println("P1 dices: " + dicesP1);
            System.out.println("P2 dices: " + dicesP2);

            int armiesAttacking = p1DiceNumber;
            int armiesDefending = p2DiceNumber;

            //comparison of pairs winner: 3 scenarios according rules
            for (int i = 0; i < p2DiceNumber; i++) {
                if (armiesAttacking == 0) break; //Stops the battle if any of the player are left out of armies to defend or attack
                if (armiesDefending == 0) break;

                if (dicesP1.get(i) > dicesP2.get(i)) {
                    armiesP2BattleResult--;// p1 wins , p2 looses: -1 army
                    armiesDefending--;
                }
                if (Objects.equals(dicesP1.get(i), dicesP2.get(i))) {
                    armiesP1BattleResult--;// p1 looses : -1 army , p2 wins
                    armiesAttacking--;
                }
                if (dicesP1.get(i) < dicesP2.get(i)) {
                    armiesP1BattleResult--;// p1 looses : -1 army , p2 wins
                    armiesAttacking--;
                }
            }
            //end result of the battle in terms of armies
            //defining new armies for each case
            int p1ArmiesAfterBattle = players.getFirst().getOwnerships().get(countryFromId).getArmies() + armiesP1BattleResult;
            players.getFirst().getOwnerships().get(countryFromId).setArmies(p1ArmiesAfterBattle);

            ; //it's not the same index for this case
            int p2ArmiesAfterBattle = players.getLast().getOwnerships().get(countryTargetIndex).getArmies() + armiesP2BattleResult;
            players.getLast().getOwnerships().get(countryTargetIndex).setArmies(p2ArmiesAfterBattle);

            //battle result
            System.out.println("Battle for territory " + countryNameTarget + " ended");
            System.out.println("P1 =" + armiesP1BattleResult + " armies lost " + "- P2 = " + armiesP2BattleResult + " armies lost ");

            //if armies defending = 0 then it needs to change ownership between players
            if ((armiesDefending == 0) && (players.getLast().getOwnerships().get(countryTargetIndex).getArmies() == 0)) {
                players.getFirst().getOwnerships().add(players.getLast().getOwnerships().get(countryTargetIndex)); //adds to the new ownership
                players.getLast().getOwnerships().remove(players.getLast().getOwnerships().get(countryTargetIndex)); // removes from the old ownership
            }
            //viceversa, if AI attacked and won
            //if ((armiesAttacking == 0) && (players.getFirst().getOwnerships().get(countryTargetIdP2).getArmies() == 0)) {
            //    players.getFirst().getOwnerships().add(players.getLast().getOwnerships().get(countryTargetIdP2)); //adds to the new ownership
            //    players.getLast().getOwnerships().remove(board.getCountries().get(countryTargetIdP2)); // removes from the old ownership

        } else {
            System.out.println("Not enough armies to attack from " + countryFrom  + " (at least 2 armies are needed)");
        }

    }

    //winner or looser and army count consequence
    //alive or dead players
    //borders check
    //validator of rules

    //card assignment if time

    private static boolean checksPlayerHasMinimumOf2Armies(PlayerImpl player, BoardImpl board, String countryAttackFrom) {
        int armiesCheck = ArmiesService.armiesForPlayerByCountryName(player, countryAttackFrom);
        return armiesCheck >= 2;
    }

    private static String getCountryFrom() {
        Scanner scan = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter country from where you want to attack:");

        String countryFromAttack = scan.nextLine();  // Read user input
        System.out.println("You have chosen: " + countryFromAttack);  // Output user input
        return countryFromAttack;
    }

    private static String getCountryToAttack() {
        Scanner scan = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter country target you want to attack:");

        String countryAttack = scan.nextLine();  // Read user input
        System.out.println("You have chosen: " + countryAttack);  // Output user input
        return countryAttack;
    }

    private static List<Integer> getBorders(PlayerImpl player, String countryOrigin) {
        List<Integer> borders = getCountryByNameForPlayer(player, countryOrigin).getBorders();
        return borders.subList(1, borders.size()); //TODO ensure removal of first element is done rather
        //removes first value because is code of country origin
    }

    public static Country getCountryByNameForPlayer(PlayerImpl player, String mapName) {
        return player.getOwnerships().stream()
                .filter(country -> (country.getCountryName()).equals(mapName))
                .toList()
                .getFirst();
    }

    static int getCountryIndexOfFromCountryObject(Country country, PlayerImpl player) {
        return player.getOwnerships().indexOf(country);
    }

    public static int getByCountryIdForPlayer(PlayerImpl player, int countryId) {
        try {
            return player.getOwnerships().stream()
                    .filter(country -> (country.getCountryId() == countryId))
                    .toList().getFirst().getCountryId();
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

}
