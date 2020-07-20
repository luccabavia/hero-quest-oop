package item.equipment.armor;

public class LightArmor extends Armor {

    public LightArmor() {
        super("LightArmor", 1, 5);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
