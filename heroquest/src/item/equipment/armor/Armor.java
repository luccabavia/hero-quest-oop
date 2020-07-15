package entity.item.equipment.armor;

import entity.item.Item;

/**
 * Class for armor that can be used by heroes at the game.
 */
public abstract class Armor implements Item {

    private String type;
    private int defense;
    private int durability;

    /**
     * Constructor method for armor class.
     *
     * @param type String
     * @param defense int
     * @param durability int

     */
    public Armor(String type, int defense, int durability) {
        this.type = type;
        this.defense = defense;
        this.durability = durability;
    }

    public int getDefense() {
        return this.defense;
    }

    public Item collect() {
        return this;
    }
}
