package service;

import model.PlayerImpl;

public class GameRulesService {
    //dice rollup and its result
    public void diceRollup(PlayerImpl players) {
        //player objectives
            //player wants to attack from a country he owns (to check that) : from
                //checks if that country has the mininum of 2 armies to attack
            //player sees options to attack and picks one (options based on borders) : target

        //tactics of battle
            //attacker: player decide to put number to attack into the target territory : from 1 to 3
            //defender: AI decides to put number to put to defend : 1 to 2 (from anywhere if the country has not? TBC)

        //define the number of dices to launch for attacker based on the number of attacker armies (1 to 3 where 1 dice = 1 army)
        // always 2 for defender
            //dice roll up
        //comparison of pairs winner
        //result of each pair and end result the battle
    }

    //winner or looser and army count consequence
    //alive or dead players
    //borders check
    //validator of rules

    //card assignment if time
}
