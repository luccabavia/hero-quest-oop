package entity.Item.equipment.armor;


/**
 * Class for armor that can be used by heroes at the game.
 */
public abstract class Armor {

    private String type;
    private int defense;
    private int durability;

    /**
     * Constructor method for armor class.
     *
     * @param tring type
     * @param int defense
     * @param int durability

     */
    
    public Armor(String type, int defense, int durability) {
        this.type = type;
        this.defense = defense;
        this.durability = durability;
    }

    public int getDefense() {
        return this.defense;
    }
}
