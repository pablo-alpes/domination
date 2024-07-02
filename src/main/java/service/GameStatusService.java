package service;

import model.Player;
import model.PlayerImpl;

import java.util.List;

public class GameStatusService {
    public static void ownershipStatus(String username, List<PlayerImpl> players) {
        System.out.println("--------------------------------");
        System.out.println("Ownership status");
        System.out.println(username + " v/s Boring machine");
        System.out.println("--------------------------------");

        for (Player player : players) {
            for (int i = 0; i < players.getFirst().getOwnerships().size(); i++) { //assuming both players have same number of countries
                //TODO - Bug found when the count surpassed the longest word if is superior to 20
                String spaces = " ".repeat(20 - players.getFirst().getOwnerships().get(i).getCountryName().length()); //for spacing alignment (10 is the longest word)
                System.out.println("| " + players.getFirst().getOwnerships().get(i).getCountryName() + "(" + players.getFirst().getOwnerships().get(i).getArmies() + ")" + spaces + "| " + players.getLast().getOwnerships().get(i).getCountryName() + "(" + + players.getLast().getOwnerships().get(i).getArmies() + ")" + " ");
            }
        }
    }
}
