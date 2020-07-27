package map.generator;

import item.Collectible;
import item.equipment.armor.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class used to generate all Armor which will set inside chest
 */
public class ArmorGenerator {

    private static ArmorGenerator instance;
    private final Random RANDOM = new Random();

    
    
    public static ArmorGenerator getInstance() {
        if (instance == null) {
            instance = new ArmorGenerator();
        }
        return instance;
    }
    
    /**
     * Method use to generate an Armor
     * @param itemType
     * @return
     */
    public Collectible generateItem(ArmorTypes itemType) {

        switch (itemType) {
            case HEAVY_ARMOR:
                return new HeavyArmor();
            case LIGHT_ARMOR:
                return new LightArmor();
        }
        return null;
    }
    /**
     * Method use to set a maxNumber of random Armor types to set inside a Chest
     * @param maxNumber
     * @return
     */
    public ArrayList<Collectible> generateMultipleRandomEntities(
            int maxNumber) {

        ArrayList<Collectible> items = new ArrayList<>(maxNumber);
        int remainingEntities= maxNumber;
        do {
            ArmorTypes type =
                    ArmorTypes.values()[RANDOM.nextInt(
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
