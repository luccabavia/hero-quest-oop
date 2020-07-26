package item.equipment.weapon;

public class LongSword extends Weapon {

    public LongSword() {
        super("LongSword", 2, 3, 1, 100);
        this.setDescription("Long sword used by barbarians, heavy but " +
                "effective. Has to be used with both hands.");
    }
}
