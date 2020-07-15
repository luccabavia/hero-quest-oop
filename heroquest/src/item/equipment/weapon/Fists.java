package item.equipment.weapon;

public class Fists extends Weapon {

    public Fists(){
        super("Fists", 2, 2, 1, 999);
        this.setDescription();
    }

    private void setDescription() {
        String description =
                "Hardened fists of an adventurer, these hands " +
                "have seen many battles.";
        this.setDescription(description);
    }
}
