package entity.item.equipment.armor;

public abstract class Armor {

    private String type;
    private int defense;
    private int durability;

    public Armor(String type, int defense, int durability) {
        this.type = type;
        this.defense = defense;
        this.durability = durability;
    }

    public int getDefense() {
        return this.defense;
    }
}
