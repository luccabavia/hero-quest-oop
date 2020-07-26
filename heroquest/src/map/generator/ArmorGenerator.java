package map.generator;

import item.Collectible;
import item.equipment.armor.*;

import java.util.ArrayList;
import java.util.Random;

public class ArmorGenerator {

    private Random randGenerator = new Random();

    public Collectible generateItem(ArmorTypes itemType) {

        switch (itemType) {
            case HEAVY_ARMOR:
                return new HeavyArmor();
            case LIGHT_ARMOR:
                return new LightArmor();
        }
        return null;
    }

    public ArrayList<Collectible> generateMultipleRandomEntities(
            int maxNumber) {

        ArrayList<Collectible> items = new ArrayList<>(maxNumber);
        int remainingEntities= maxNumber;
        do {
            ArmorTypes type =
                    ArmorTypes.values()[randGenerator.nextInt(
                                    ArmorTypes.values().length)
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
