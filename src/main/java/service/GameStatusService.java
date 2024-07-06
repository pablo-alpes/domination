package service;

import model.Country;
import model.Player;
import model.PlayerImpl;

import java.util.List;

public class GameStatusService {
    public static void ownershipStatus(String username, List<PlayerImpl> players) {
        System.out.println("--------------------------------");
        System.out.println("Ownership status");
        System.out.println(username + " v/s Boring machine");
        System.out.println("--------------------------------");

        List<Country> p1Countries = players.getFirst().getOwnerships();
        List<Country> p2Countries = players.getLast().getOwnerships();

        int maxLines = Math.max(p2Countries.size(), p1Countries.size());

        for (int i = 0; i < maxLines; i++) { //assuming both players have same number of countries
            //TODO - Bug found when the count surpassed the longest word if is superior to 20
            String spaces = " ".repeat(15);
            //String spaces = " ".repeat(25 - players.getFirst().getOwnerships().get(i).getCountryName().length()); //for spacing alignment (10 is the longest word)
            String p1Display = "";
            String p2Display = "";

            if (p1Countries.size() == p2Countries.size()) {
                p1Display = p1Countries.get(i).getCountryName() + "(" + p1Countries.get(i).getArmies() + ")";
                p2Display = p2Countries.get(i).getCountryName() + "(" + p2Countries.get(i).getArmies() + ")";
            } else {
                try {
                    p1Display = p1Countries.get(i).getCountryName() + "(" + p1Countries.get(i).getArmies() + ")";
                    p2Display = p2Countries.get(i).getCountryName() + "(" + p2Countries.get(i).getArmies() + ")";
                } catch (Exception e) {
                    //FIX -- To show countries where the lists are imbalanced (one player has more than the other)
                    if (i > p1Countries.size()) { //p1 out of elements
                        p1Display = " - ";
                        p2Display = p2Countries.get(i).getCountryName() + "(" + p2Countries.get(i).getArmies() + ")";
                    }
                    if (i > p2Countries.size()) { // p2 out of elements
                        p1Display = p1Countries.get(i).getCountryName() + "(" + p1Countries.get(i).getArmies() + ")";
                        p2Display = " - ";
                    }
                }
            }
            System.out.println("| " + p1Display + spaces + "| " + p2Display);
        }
    }
}
