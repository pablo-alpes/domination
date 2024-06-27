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
            for (int i = 0; i < players.getFirst().getOwnerships().size(); i++) {
                String spaces = " ".repeat(10 - players.getFirst().getOwnerships().get(i).getCountryName().length()); //for spacing alignment (10 is the longest word)
                System.out.println("| " + players.getFirst().getOwnerships().get(i).getCountryName() + spaces + "| " + players.getLast().getOwnerships().get(i).getCountryName() + " ");
            }
        }
    }

}
