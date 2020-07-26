package map.generator;

import item.Collectible;
import item.equipment.potion.*;

import java.util.ArrayList;
import java.util.Random;

public class PotionGenerator {

    private Random randGenerator = new Random();

    public Collectible generateItem(PotionType potionTypes) {

        switch (potionTypes) {
            case SMALL_HEALTH_ELIXIR:
                return new SmallHealthElixir();
            case LARGE_HEALTH_ELIXIR:
                return new LargeHealthElixir();
        }
        return null;
    }

    public ArrayList<Collectible> generateMultipleRandomEntities(
            int maxNumber) {

        ArrayList<Collectible> items = new ArrayList<>(maxNumber);
        int remainingEntities= maxNumber;
        do {
            PotionType type =
                    PotionType.values()[randGenerator.nextInt(
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
