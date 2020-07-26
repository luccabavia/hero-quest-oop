package map.generator;

import item.Collectible;
import item.equipment.spell.*;
import map.Map;

import java.util.ArrayList;
import java.util.Random;

public class SpellGenerator {

    private Map map;
    private Random randGenerator = new Random();

    public SpellGenerator(Map map) {
        this.map = map;
    }

    public Collectible generateItem(SpellType itemType) {

        switch (itemType) {
            case FIREBALL:
                return new Fireball(this.map);
            case MAGIC_MISSILE:
                return new MagicMissile();
            case TELEPORT:
                return new Teleport(this.map);
            case SIMPLE_HEAL:
                return new SimpleHeal();
        }
        return null;
    }

    public ArrayList<Collectible> generateMultipleRandomEntities(
            int maxNumber) {

        ArrayList<Collectible> items = new ArrayList<>(maxNumber);
        int remainingEntities= maxNumber;
        do {
            SpellType type =
                    SpellType.values()[randGenerator.nextInt(
                            SpellType.values().length)
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
