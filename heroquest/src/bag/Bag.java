package bag;

import exceptions.UnknownItemException;
import item.Item;
import item.treasure.Treasure;
import item.equipment.armor.Armor;
import item.equipment.spell.Spell;
import item.equipment.weapon.Weapon;

import java.util.ArrayList;

public class Bag {
    
    /**
    * Class for objects which an hero can store. 
    */

    private ArrayList<Weapon> weapons;
    private ArrayList<Spell> spells;
    private ArrayList<Armor> armors;
    private ArrayList<Treasure> coins;

     /**
     * Constructor method for bag class.
     */
//     * @param numberOfWeapons
//     * @param numberOfSpells
//     * @param numberOfArmors
//     * @param numberOfTreasures
//     */
    public Bag() {
//            int numberOfWeapons, int numberOfSpells, int numberOfArmors,
//               int numberOfTreasures) {
        this.weapons = new ArrayList<>();
        this.spells = new ArrayList<>();
        this.armors = new ArrayList<>();
        this.coins = new ArrayList<>();
    }

    public void addItem(Item item) throws UnknownItemException {

        try {
            this.weapons.add((Weapon) item);
        } catch (ClassCastException e) {
            try {
                this.spells.add((Spell) item);
            } catch (ClassCastException e1) {
                try {
                    this.armors.add((Armor) item);
                } catch (ClassCastException e2) {
                    try {
                        this.coins.add((Treasure) item);
                    } catch (ClassCastException e3) {
                        throw new UnknownItemException(
                                String.format("Unknown item type: %s",
                                        item.getClass())
                        );
                    }
                }
            }
        }

    }

    public String getStatus() {
        StringBuilder s = new StringBuilder();
        s.append("Bag: ");
        if (this.weapons.size() > 0) {
            s.append("Weapon: ");
            for (Weapon i: weapons) {
                s.append(i.getName());
                s.append(", ");
            }
        }
        if (this.spells.size() > 0) {
            s.append("Spell: ");
            for (Spell i: spells) {
                s.append(i.getName());
                s.append(", ");
            }
        }
        if (this.armors.size() > 0) {
            s.append("Armor: ");
            for (Armor i: armors) {
                s.append(i.getName());
                s.append(", ");
            }
        }
        if (this.coins.size() > 0) {
            s.append("Armor: ");
            for (Treasure i: coins) {
                s.append(i.getName());
                s.append(", ");
            }
        }

        return s.toString();
    }
}
