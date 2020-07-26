package item.equipment.armor;

import item.Collectible;

/**
 * Class for armor that can be used by heroes at the game.
 */
public abstract class Armor implements Collectible {

    protected String name;
    private int defense;

    /**
     * Constructor method for armor class.
     *
     * @param name String
     * @param defense int
     */
    public Armor(String name, int defense) {
        this.name = name;
        this.defense = defense;
    }

    public int getDefense() {
        return this.defense;
    }


    public String getCharacteristics() {
        return String.format("%s -> Defense: %d",
                this.name, this.defense);
    }
}
