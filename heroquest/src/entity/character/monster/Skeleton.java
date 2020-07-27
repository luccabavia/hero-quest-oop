package entity.character.monster;

import item.equipment.weapon.*;
import map.Map;

/**
 * Define an Skeleton can be set on Map
 *
 */
public class Skeleton extends Monster {

    public Skeleton(Map map, int x, int y) {
        super(map, x, y, "Sk", 4, 2,2,
                1,2);
        this.setStartingEquipment();
    }

    @Override
    protected void setStartingEquipment() {
        this.weapon = new ShortSword();
    }

}
