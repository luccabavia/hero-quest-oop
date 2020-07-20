package entity.character.hero;

import item.Item;
import map.Map;
import item.equipment.weapon.*;
import item.equipment.spell.*;

public class Wizard extends Hero {

    public Wizard(Map map, int x, int y, String name) {
        super(map, x, y, "Wz", 4,1, 2,
                2, 6, name);
        this.setStartingEquipment();

    }

    @Override
    protected void setStartingEquipment() {
        this.setRightHandWeapon(new Dagger());
        /* Arrumar para ser tres punhais que
           quando lança é perdido */
        this.setSpell(new MagicMissile());
        Item[] items = new Item[] {new Dagger(), new Dagger(),
                new MagicMissile(), new Fireball(), new Teleport()};
        for (Item i : items) {
            this.addItemToBag(i);
        }
    }
}
