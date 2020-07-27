package map.generator;

import entity.chest.*;
import exceptions.*;
import io.Display;
import map.Map;
import item.Collectible;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * Class used to generate all Chest which will set on Map
 */
public class ChestGenerator {

    protected Map map;
    private static ChestGenerator instance;
    private final Random RANDOM = new Random();
    private final PotionGenerator POTION_GENERATOR;
    private final WeaponGenerator WEAPON_GENERATOR;
    private final ArmorGenerator ARMOR_GENERATOR;
    private final TreasureGenerator TREASURE_GENERATOR;
    private final SpellGenerator SPELL_GENERATOR;

    /**
     *  Constructor method to instance all kinds of Collectible objects generators can be set inside a chest
     */
    private ChestGenerator() {

        this.POTION_GENERATOR = PotionGenerator.getInstance();
        this.WEAPON_GENERATOR = WeaponGenerator.getInstance();
        this.ARMOR_GENERATOR = ArmorGenerator.getInstance();
        this.TREASURE_GENERATOR = TreasureGenerator.getInstance();
        this.SPELL_GENERATOR = SpellGenerator.getInstance();

    }

    public static ChestGenerator getInstance() {
        if (instance == null) {
            instance = new ChestGenerator();
        }
        return instance;
    }

    public void setMap(Map map) {
        this.map = map;
        this.SPELL_GENERATOR.setMap(this.map);
    }

    /**
     * Method use to generate a Chest
     * @param isTrap
     * @param position
     * @param hasSpellCaster
     * @throws InvalidGeneratorSeedException
     */
    private void generateEntity(boolean isTrap, int[] position,
                                boolean hasSpellCaster)
            throws InvalidGeneratorSeedException {

        try {
            if (this.map.isAvailable(position[0], position[1])) {
                if (isTrap) {
                    this.map.setEntity(new TrapChest(position[0],
                            position[1]));
                } else {
                    NormalChest chest = new NormalChest(position[0],
                            position[1]);
                    this.createContents(chest, hasSpellCaster);
                    this.map.setEntity(chest);
                }
            }
        } catch (PositionDoesNotExistException
                | IsTrapException
                | CannotWalkOverException e) {
        	throw new InvalidGeneratorSeedException(
                    String.format("Cannot place entity at " + 
                    "position: %d %d", position[0], position[1], e.getMessage())
            );
        }
    }

    /**
     * Method use to set multiples Chest can be set on map
     * @param hashMap
     * @param hasSpellCaster
     */
    public void generateMultipleEntities(
            HashMap<Boolean, int[][]> hashMap, boolean hasSpellCaster) {

        Set<Boolean> keys = hashMap.keySet();
        Boolean[] isTrap = new Boolean[keys.size()];
        isTrap = keys.toArray(isTrap);

        for (Boolean bool : isTrap) {
            int[][] positions = hashMap.get(bool);
            for (int[] position : positions) {
                try {
                    this.generateEntity(bool, position, hasSpellCaster);
                } catch (InvalidGeneratorSeedException e) {
                    Display.printWarning(e.getMessage());
                }
            }
        }
    }
    /**
     * Method use to set random positions and damage Chest can be set on map
     * @param maxNumberOfEntities
     * @param hasSpellCaster
     */
    public void generateMultipleRandomEntities(int maxNumberOfEntities,
                                               boolean hasSpellCaster) {
        int remainingEntities= maxNumberOfEntities;
        do {

            int[] position = new int[] {
                    RANDOM.nextInt(this.map.getMapSize()[0]),
                    RANDOM.nextInt(this.map.getMapSize()[1])
            };
            boolean isTrap = RANDOM.nextBoolean();

            try {
                remainingEntities--;
                this.generateEntity(isTrap, position, hasSpellCaster);
            } catch (InvalidGeneratorSeedException e) {
                remainingEntities++;
            }

        } while(remainingEntities > 0);
    }

    /**
     * Method to insert all Collectible items inside Chest
     * @param chest
     * @param hasSpellCaster
     */
    private void createContents(NormalChest chest, boolean hasSpellCaster) {

        ArrayList<Collectible> items = new ArrayList<Collectible>();

        items.addAll(this.POTION_GENERATOR.generateMultipleRandomEntities(1));
        items.addAll(this.WEAPON_GENERATOR.generateMultipleRandomEntities(1));
        items.addAll(this.ARMOR_GENERATOR.generateMultipleRandomEntities(1));
        items.addAll(this.TREASURE_GENERATOR.generateMultipleRandomEntities(1));
        if (hasSpellCaster) {
            items.addAll(this.SPELL_GENERATOR.generateMultipleRandomEntities(1));
        }

        for (Collectible i : items) {
            chest.addItem(i);
        }

    }
}
