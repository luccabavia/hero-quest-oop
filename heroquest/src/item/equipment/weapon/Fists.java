package item.equipment.weapon;

public class Fists extends Weapon {

    public Fists(){
        super("Fists", 1, 0, 1, 999);
        this.setDescription();
    }

    private void setDescription() {
        String description =
                "Hardened fists, these hands " +
                "have seen many battles.";
        this.setDescription(description);
    }
}
