package map.generator;

import item.Collectible;
import item.equipment.potion.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class used to generate all Potion which will set inside chest
 */
public class PotionGenerator {

    private static PotionGenerator instance;
    private final Random RANDOM = new Random();

    public static PotionGenerator getInstance() {
        if (instance == null) {
            instance = new PotionGenerator();
        }
        return instance;
    }

    /**
     * Method use to generate a Potion
     * @param itemType
     * @return
     */
    public Collectible generateItem(PotionType potionTypes) {

        switch (potionTypes) {
            case SMALL_HEALTH_ELIXIR:
                return new SmallHealthElixir();
            case LARGE_HEALTH_ELIXIR:
                return new LargeHealthElixir();
        }
        return null;
    }

    /**
     * Method use to set a maxNumber of random Potion types to set inside a Chest
     * @param maxNumber
     * @return
     */
    public ArrayList<Collectible> generateMultipleRandomEntities(
            int maxNumber) {

        ArrayList<Collectible> items = new ArrayList<>(maxNumber);
        int remainingEntities= maxNumber;
        do {
            PotionType type =
                    PotionType.values()[RANDOM.nextInt(
                    		PotionType.values().length)
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
