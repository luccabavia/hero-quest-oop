package entity.character.hero;

import map.Map;

public class Dummy extends Hero {

    public Dummy(Map map, int X, int Y, int movementDice) {

        super(map, X, Y, "Dy", 2, 2,
                2, 2, movementDice, "Dummy");
    }

    @Override
    public void move(int steps) {

    }
}
