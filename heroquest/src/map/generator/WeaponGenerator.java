package map.generator;

import item.Collectible;
import item.equipment.weapon.Dagger;
import item.equipment.weapon.LongSword;
import item.equipment.weapon.ShortSword;
import item.equipment.weapon.WeaponType;

import java.util.ArrayList;
import java.util.Random;

public class WeaponGenerator {

    private Random randGenerator = new Random();

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

    public ArrayList<Collectible> generateMultipleRandomEntities(
            int maxNumber) {

        ArrayList<Collectible> items = new ArrayList<>(maxNumber);
        int remainingEntities= maxNumber;
        do {
            WeaponType type =
                    WeaponType.values()[randGenerator.nextInt(
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
