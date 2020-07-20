package entity.character.hero;

import map.Map;
import item.equipment.weapon.*;

public class Barbarian extends Hero {

    public Barbarian(Map map, int x, int y, String name) {
        super(map, x, y, "Bb", 8,3, 2,
                2, 2, name);
        this.setStartingEquipment();
    }

    @Override
    protected void setStartingEquipment() {
        this.setDualWieldingWeapon(new LongSword());
    }
}
