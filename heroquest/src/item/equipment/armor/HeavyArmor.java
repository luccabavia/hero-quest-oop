package item.equipment.armor;

public class HeavyArmor extends Armor {

    public HeavyArmor() {
        super("Heavy", 3, 10);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
