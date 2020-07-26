package item.equipment.armor;

public class HeavyArmor extends Armor {

    public HeavyArmor() {
        super("HeavyArmor", 3);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
