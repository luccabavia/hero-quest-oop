package map.generator;

import item.Collectible;
import item.equipment.weapon.Dagger;
import item.equipment.weapon.LongSword;
import item.equipment.weapon.ShortSword;
import item.equipment.weapon.WeaponType;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class used to generate all Weapon which will set inside chest
 */
public class WeaponGenerator {

    private static WeaponGenerator instance;
    private final Random RANDOM = new Random();

    public static WeaponGenerator getInstance() {
        if (instance == null) {
            instance = new WeaponGenerator();
        }
        return instance;
    }

    /**
     * Method use to generate a Weapon
     * @param itemType
     * @return
     */
    public Collectible generateItem(WeaponType itemType) {

        switch (itemType) {
            case DAGGER:
                return new Dagger();
            case LONG_SWORD:
                return new LongSword();
            case SHORT_SWORD:
                return new ShortSword();
            default:
            	return null;
        }
    }

    /**
     * Method use to set a maxNumber of random Weapon types to set inside a Chest
     * @param maxNumber
     * @return
     */
    public ArrayList<Collectible> generateMultipleRandomEntities(
            int maxNumber) {

        ArrayList<Collectible> items = new ArrayList<>(maxNumber);
        int remainingEntities= maxNumber;
        do {
            WeaponType type =
                    WeaponType.values()[RANDOM.nextInt(
                            WeaponType.values().length)
                            ];
            remainingEntities--;
            Collectible item = this.generateItem(type);
            if (item != null) {
                items.add(item);
            }

        } while(remainingEntities > 0);

        return items;
    }

}
