package item.equipment.armor;

import item.Item;

/**
 * Class for armor that can be used by heroes at the game.
 */
public abstract class Armor implements Item {

    protected String name;
    private int defense;
    private int durability;

    /**
     * Constructor method for armor class.
     *
     * @param name String
     * @param defense int
     * @param durability int
     */
    public Armor(String name, int defense, int durability) {
        this.name = name;
        this.defense = defense;
        this.durability = durability;
    }

    public int getDefense() {
        return this.defense;
    }

    public Item collect() {
        return this;
    }

    public String getCharacteristics() {
        return String.format("%s -> Defense: %d",
                this.name, this.defense);
    }
}
