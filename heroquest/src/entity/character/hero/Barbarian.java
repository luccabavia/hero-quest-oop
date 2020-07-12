package entity.character.hero;

import map.Map;
import entity.item.equipment.weapon.*;

public class Barbarian extends Hero {

    public Barbarian(Map map, int x, int y, String name) {
        super(map, x, y, "Bb", 8,3, 2,
                2, 2, name);
        this.weapon.add(new LongSword());
    }

    @Override
    public void move(int steps) {

    }
}