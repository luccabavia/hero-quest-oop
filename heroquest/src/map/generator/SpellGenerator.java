package map.generator;

import item.Collectible;
import item.equipment.spell.*;
import map.Map;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class used to generate all Spell which will set inside chest
 */
public class SpellGenerator {

    private Map map;
    private static SpellGenerator instance;
    private final Random RANDOM = new Random();

    public static SpellGenerator getInstance() {
        if (instance == null) {
            instance = new SpellGenerator();
        }
        return instance;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Method use to generate a Spell
     * @param itemType
     * @return
     */
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
    /**
     * Method use to set a maxNumber of random Spells types to set inside a Chest
     * @param maxNumber
     * @return
     */
    public ArrayList<Collectible> generateMultipleRandomEntities(
            int maxNumber) {

        ArrayList<Collectible> items = new ArrayList<>(maxNumber);
        int remainingEntities= maxNumber;
        do {
            SpellType type =
                    SpellType.values()[RANDOM.nextInt(
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
