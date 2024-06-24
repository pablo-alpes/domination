package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlayerImpl implements Player {
    String name;
    List<Country> ownerships;
    List<String> cards;
    int human;
    int alive;
}
