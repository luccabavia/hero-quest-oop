package map.generator;

import item.Collectible;
import item.treasure.*;
import java.util.ArrayList;
import java.util.Random;

public class TreasureGenerator {

    private Random randGenerator = new Random();

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
                    TreasureType.values()[randGenerator.nextInt(
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
