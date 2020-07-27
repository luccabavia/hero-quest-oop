package item.equipment.weapon;

import item.Collectible;
/**
 * Parent class of all kind of Weapons can be created in the Game
 */
public abstract class Weapon implements Collectible {

    private String name;
    /**
     * Value of how many hands is necessary to hold this Weapon
     */
    private int hands;
    private String description;
    private int attack;
    private int range;
    private int durability;

    /**
     * Constructor method of Weapon
     * @param name
     * @param hands
     * @param attack
     * @param range
     * @param durability
     */
    public Weapon(String name, int hands, int attack, int range,
                  int durability) {
        this.name = name;
        this.hands = hands;
        this.attack = attack;
        this.range = range;
        this.durability = durability;
    }

    protected void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String getCharacteristics() {
       return String.format("%s -> Hands: %d, Attack: %d, Range: %d",
               this.name, this.hands, this.attack, this.range);
    }

    @Override
    public String getName() {
        return name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getHands() {
        return this.hands;
    }
    
    /**
     * Method to get attack point and refresh Weapon durability
     * @return
     */
    public int getAttack() {
        this.durability--;
        return this.attack;
    }

    public int getDurability() {
        return this.durability;
    }


    public int getRange() { return this.range; }
}
