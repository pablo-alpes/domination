package service;

import model.BoardImpl;
import model.Country;
import model.PlayerImpl;

import java.util.*;

public class GameRulesService {
    //dice rollup and its result
    public static int diceRollup(List<PlayerImpl> players, BoardImpl board, int playerTurn) {
        //player definition : for readability
        PlayerImpl p1 = players.getFirst();
        PlayerImpl p2 = players.getLast();
        PlayerImpl attacker = new PlayerImpl();
        PlayerImpl defender = new PlayerImpl();

        //player objectives
        String countryFrom = "";
        int countryFromId = -1;
        //player wants to attack from a country he owns (to check that) : from
        switch(playerTurn) {
            case 1:
                //human
                attacker = p1;
                defender = p2;
                countryFrom = getCountryFrom();
                countryFromId = getCountryIndexOfFromCountryObject(getCountryByNameForPlayer(p1, countryFrom), p1);
                break;
            case 2: //machine
                //subset of countries can attack (ie at least 2 armies)
                attacker = p2;
                defender = p1;
                List<Country> possibleCountries = p2.getOwnerships().stream()
                        .filter(country -> country.getArmies()>1)
                        .toList();
                Random randCountry = new Random();
                int countryRand = 0;
                if (!possibleCountries.isEmpty()) {
                    countryRand = randCountry.nextInt(0, possibleCountries.size());
                }
                else {
                    System.out.println("There's no possible countries from where to attack in your board.");
                    System.out.println("Passing turn to next player."); //TODO ??
                    return -1;
                }
                countryFrom = possibleCountries.get(countryRand).getCountryName(); //simply strategy of getting random country each time out of the ones can attack (ie at least 2 troops)
                countryFromId = getCountryIndexOfFromCountryObject(getCountryByNameForPlayer(p2, countryFrom), p2);
                break;
        }

        String countryNameTarget = "";
        int countryTargetId = -1;
        //checks if that country has the mininum of 2 armies to attack
        if (checksPlayerHasMinimumOf2Armies(attacker, board, countryFrom)) {
            List<Integer> bordersToAttack = new ArrayList<Integer>(getBorders(attacker, countryFrom));
            //target : player sees options to attack and picks one (options based on borders)
            //and excludes the ones he owns

            PlayerImpl finalDefender = defender;
            bordersToAttack.removeIf(countryId -> (getByCountryIdForPlayer(finalDefender, countryId) == 0));

            if (bordersToAttack.isEmpty()) {
                System.out.println("The country chosen does not have any borders to attack or all yours");
                System.out.println("Please select other country.");
                return -1;
            }

            System.out.println("The country you choose to attack from is:"+ countryFrom);
            System.out.println("Your choices to attack are:");
            System.out.println(bordersToAttack);

            for (int i = 0; i < bordersToAttack.size(); i++) {
                System.out.println(board.getCountries().get(bordersToAttack.get(i)-1).getCountryName());
            }

            switch(playerTurn) {
                case 1: //human
                    countryNameTarget = getCountryFrom();
                    //countryTargetId = getCountryIndexOfFromCountryObject(getCountryByNameForPlayer(attacker, countryTargetAttacker), attacker);
                    //countryTargetId = bordersToAttack.getLast(); // to find out where is storaged in the P2 -> Done in countryIndex
                    break;
                case 2:  //machine - random selection
                    Random randBorder = new Random();
                    int borderRand = 0;
                    if (bordersToAttack.size() > 1) { //if more than 1 element
                        borderRand = randBorder.nextInt(0, bordersToAttack.size()-1);
                    }
                    countryTargetId = bordersToAttack.get(borderRand) - 1; //changing index, since its starts in 0 (and not in 0)
                    countryNameTarget = board.getCountries().get(countryTargetId).getCountryName();
                    break;
            }
            Random randInt = new Random();
            int countryTargetIndex = getCountryIndexOfFromCountryObject(getCountryByNameForPlayer(defender, countryNameTarget), defender);

            System.out.println("You'll attack to:"+ countryNameTarget);
            //tactics of battle
            //define the number of dices to launch for attacker based on the number of attacker armies (1 to 3 where 1 dice = 1 army)

            //attacker: player decide to put number to attack into the target territory : from 1 to 3
            /**TODO -- TO FIX BASED ON THE NUMBER MAX OF TROOPS THAT THE COUNTRY HAS **/
            System.out.println("How many armies to deploy to to attack (1 to 3):");

            int attackerDiceNumber = 0;
            if (attacker.getOwnerships().get(countryFromId).getArmies() == 2) {
                attackerDiceNumber = 1; //required by the game to leave 1 behind the territory of origin
            }
            else { //more than 2 armies
                attackerDiceNumber = randInt.nextInt(2, 3); // Random between 2 and 3 dices;
            }

            System.out.println("Selected " + attackerDiceNumber + " armies to deploy.");
            //defender: AI or Player decides to put number to put to defend : 1 to 2 depending number of troops in the territory and within the whole map
            int defenderDiceNumber = 0;
            int totalArmiesDefender = defender.getOwnerships()
                    .stream()
                    .mapToInt(Country::getArmies)
                    .sum();
            if (defender.getOwnerships().get(countryTargetIndex).getArmies() == 0 || defender.getOwnerships().get(countryTargetIndex).getArmies() == 1) {
                defenderDiceNumber = 1;
            }
            else if (totalArmiesDefender == 1) {
                defenderDiceNumber = 1;
            } else if (totalArmiesDefender > 1 || defender.getOwnerships().get(countryTargetIndex).getArmies() > 1)  {
                defenderDiceNumber = randInt.nextInt(1, 2);
            }
            System.out.println("Defender decides to deploy " + defenderDiceNumber + " armies");

            //dices roll up for each player and ordering them
            //https://howtodoinjava.com/java8/convert-intstream-collection-array/

            List<Integer> dicesAttacker = randInt.ints(attackerDiceNumber, 1, 6)
                    .boxed()
                    .sorted()
                    .toList();
            List<Integer> dicesDefender = randInt.ints(defenderDiceNumber, 1, 6)
                    .boxed()
                    .sorted()
                    .toList();

            //organizing pairs from max to min values
            dicesAttacker = dicesAttacker.reversed();
            dicesDefender = dicesDefender.reversed();
            //counter of end result after battle
            int armiesAttackerBattleResult = 0;
            int armiesDefenderBattleResult = 0;

            //result of each dice
            System.out.println("Attacker dices: " + dicesAttacker);
            System.out.println("Defender dices: " + dicesDefender);

            int armiesAttacking = attackerDiceNumber;
            int armiesDefending = defenderDiceNumber;

            //comparison of winner pairs: 3 scenarios according rules
            for (int i = 0; i < defenderDiceNumber; i++) {
                if (armiesAttacking == 0) break; //Stops the battle if any of the player are left out of armies to defend or attack
                if (armiesDefending == 0) break;

                if (dicesAttacker.get(i) > dicesDefender.get(i)) {
                    armiesDefenderBattleResult--;// p-Attacker wins , p-defender looses: -1 army
                    armiesDefending--;
                }
                if (Objects.equals(dicesAttacker.get(i), dicesDefender.get(i))) {
                    armiesAttackerBattleResult--;// p-attacker looses : -1 army , p-defender wins
                    armiesAttacking--;
                }
                if (dicesAttacker.get(i) < dicesDefender.get(i)) {
                    armiesAttackerBattleResult--;// p-attacker looses : -1 army , p-defender wins
                    armiesAttacking--;
                }
            }
            //end result of the battle in terms of armies
            //defining new armies for each case
            int attackerArmiesAfterBattle = attacker.getOwnerships().get(countryFromId).getArmies() + armiesAttackerBattleResult;
            attacker.getOwnerships().get(countryFromId).setArmies(attackerArmiesAfterBattle);

            ; //it's not the same index for this case
            int defenderArmiesAfterBattle = defender.getOwnerships().get(countryTargetIndex).getArmies() + armiesDefenderBattleResult;
            defender.getOwnerships().get(countryTargetIndex).setArmies(defenderArmiesAfterBattle);

            //battle result
            System.out.println("Battle for territory " + countryNameTarget + " ended");
            System.out.println("Attacker =" + armiesAttackerBattleResult + " armies lost " + "- Defender = " + armiesDefenderBattleResult + " armies lost ");

            //Ownership changes if winner destroys all the armies of the opponent
            if (defender.getOwnerships().get(countryTargetIndex).getArmies() < 1) {
                attacker.getOwnerships().add(defender.getOwnerships().get(countryTargetIndex)); //adds to the new ownership
                attacker.getOwnerships().getLast().setArmies(armiesAttacking - armiesAttackerBattleResult); //Leaves the armies that survive in the new territory
                int armiesLeftInCountryFrom = (defender.getOwnerships().get(countryFromId).getArmies() - (armiesAttacking - armiesAttackerBattleResult));

                defender.getOwnerships().remove(countryTargetIndex); // removes from the old ownership the corresponding Id

                //leaves the territory of origin with the right number of armies after leaving the ones survived in battl
                attacker.getOwnerships().get(countryFromId).setArmies(armiesLeftInCountryFrom);

            }

        } else {
            System.out.println("Not enough armies to attack from " + countryFrom  + " (at least 2 armies are needed)");
        }

        return 1;
    }

    //winner or looser and army count consequence
    //alive or dead players
    //borders check
    //validator of rules

    //card assignment if time

    private static boolean checksPlayerHasMinimumOf2Armies(PlayerImpl player, BoardImpl board, String countryAttackFrom) {
        int armiesCheck = ArmiesService.armiesForPlayerByCountryName(player, countryAttackFrom);
        return armiesCheck > 1;
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
