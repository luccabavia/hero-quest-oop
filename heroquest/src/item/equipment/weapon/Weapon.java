package item.equipment.weapon;

import item.Item;

public abstract class Weapon implements Item {

    private String name;
    private int hands;
    private String description;
    private int attack;
    private int range;
    private int durability;

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
    public Item collect() {
        return this;
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

    public int getAttack() {
        return this.attack;
    }

    public int getRange() { return this.range; }
}