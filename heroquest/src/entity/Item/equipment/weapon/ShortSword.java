package entity.Item.equipment.weapon;

public class ShortSword extends Weapon {

    public ShortSword() {
        super("ShortSword", 1, 2, 1, 999);
        this.setDescription("Sword used by dwarves. May seem not more than a" +
                " long knife for most men, but is effective nonetheless.");
    }
}
