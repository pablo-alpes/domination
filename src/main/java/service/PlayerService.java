package service;

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
}
