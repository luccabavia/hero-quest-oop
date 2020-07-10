package bag;

import entity.item.treasure.Treasure;
import entity.item.equipment.armor.Armor;
import entity.item.equipment.spell.Spell;
import entity.item.equipment.weapon.Weapon;

public class Bag {
    
    /**
    * Class for objects which an hero can store. 
    */

    private Weapon[] weapons;
    private Spell[] spells;
    private Armor[] armors;
    private Treasure[] coins;

     /**
     * Constructor method for bag class.
     *
     * @param int numberOfWeapon
     * @param int numberOfSpells
     * @param int numberOfArmors
     * @param int numberOfTreasure
     */
    
    public Bag(int numberOfWeapons, int numberOfSpells, int numberOfArmors,
               int numberOfTreasures) {
        this.weapons = new Weapon[numberOfWeapons];
        this.spells = new Spell[numberOfSpells];
        this.armors = new Armor[numberOfArmors];
        this.coins = new Treasure[numberOfTreasures];
    }

}
