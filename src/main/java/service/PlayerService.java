package service;

import model.Country;
import model.Player;
import model.PlayerImpl;

import java.util.ArrayList;
import java.util.List;

public class PlayerService {

    public static List<PlayerImpl> createPlayers(String userName) {
        final PlayerImpl human = new PlayerImpl();
        final PlayerImpl machine = new PlayerImpl();

        List<PlayerImpl> players = new ArrayList<>();

        human.setName(userName);
        machine.setName("AI God");

        players.add(human);
        players.add(machine);

        return players;
    }

    public static boolean playerAlive(PlayerImpl player) {
        //if they still have territories, they are alive
        return (!player.getOwnerships().isEmpty());
    }

    public static boolean declareDraw(List<PlayerImpl> players) {
        //if the scenario leads to a situation where no attack is possible after reinforcements since each side has 0 or 1 people in their turn
        PlayerImpl p1 = players.getFirst();
        PlayerImpl p2 = players.getLast();

        int p1CountriesLessThan2Armies = (int) p1.getOwnerships().stream()
                .filter(country -> country.getArmies() > 1)
                .count();

        int p2CountriesLessThan2Armies = (int) p2.getOwnerships().stream()
                .filter(country -> country.getArmies() > 1)
                .count();


        //Exclude the possibility at least one country has more than 1 army, otherwise attack can continue
        if (p1CountriesLessThan2Armies > 0 || p2CountriesLessThan2Armies > 0) {
            return false;
        } else {
            int p1TotalArmies = p1.getOwnerships().stream()
                    .mapToInt(Country::getArmies)
                    .sum();
            int p2TotalArmies = p2.getOwnerships().stream()
                    .mapToInt(Country::getArmies)
                    .sum();

            return ((p1.getOwnerships().size() < p1TotalArmies) && (p2.getOwnerships().size() < p2TotalArmies));
        }
    }
}
