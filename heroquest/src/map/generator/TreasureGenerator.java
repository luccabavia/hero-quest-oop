package map.generator;

import item.Collectible;
import item.treasure.*;
import java.util.ArrayList;
import java.util.Random;

public class TreasureGenerator {

    private static TreasureGenerator instance;
    private final Random RANDOM = new Random();

    public static TreasureGenerator getInstance() {
        if (instance == null) {
            instance = new TreasureGenerator();
        }
        return instance;
    }

    public Collectible generateItem(TreasureType itemType) {

        switch (itemType) {
            case DIAMOND:
                return new Diamond();
            case GOLD_BAR:
                return new GoldBar();
            case GOLD_COIN:
                return new GoldCoin();
        }
        return null;
    }

    public ArrayList<Collectible> generateMultipleRandomEntities(
            int maxNumber) {

        ArrayList<Collectible> items = new ArrayList<>(maxNumber);
        int remainingEntities= maxNumber;
        do {
            TreasureType type =
                    TreasureType.values()[RANDOM.nextInt(
                            TreasureType.values().length)
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
