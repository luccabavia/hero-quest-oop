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

public class ChestGenerator {

    protected Map map;
    private Random randGenerator = new Random();

    public ChestGenerator(Map map) {
        this.map = map;
    }

    private void generateEntity(boolean isTrap, int[] position,
                                boolean hasSpellCaster)
            throws InvalidGeneratorSeedException {

        try {
            if (this.map.isAvailable(position[0], position[1])) {
                if (isTrap) {
                    this.map.setEntity(new TrapChest(position[0],
                            position[1], true));
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

    public void generateMultipleRandomEntities(int maxNumberOfEntities,
                                               boolean hasSpellCaster) {
        int remainingEntities= maxNumberOfEntities;
        do {

            int[] position = new int[] {
                    randGenerator.nextInt(this.map.getMapSize()[0]),
                    randGenerator.nextInt(this.map.getMapSize()[1])
            };
            boolean isTrap = randGenerator.nextBoolean();

            try {
                remainingEntities--;
                this.generateEntity(isTrap, position, hasSpellCaster);
            } catch (InvalidGeneratorSeedException e) {
                remainingEntities++;
            }

        } while(remainingEntities > 0);
    }

    private void createContents(NormalChest chest, boolean hasSpellCaster) {

        ArrayList<Collectible> items = new ArrayList<Collectible>();

        PotionGenerator potionGenerator = new PotionGenerator();
        items.addAll(potionGenerator.generateMultipleRandomEntities(1));

        WeaponGenerator weaponGenerator = new WeaponGenerator();
        items.addAll(weaponGenerator.generateMultipleRandomEntities(1));

        ArmorGenerator armorGenerator = new ArmorGenerator();
        items.addAll(armorGenerator.generateMultipleRandomEntities(1));

        TreasureGenerator treasureGenerator = new TreasureGenerator();
        items.addAll(treasureGenerator.generateMultipleRandomEntities(1));

        if (hasSpellCaster) {
            SpellGenerator spellGenerator = new SpellGenerator(this.map);
            items.addAll(spellGenerator.generateMultipleRandomEntities(1));
        }

        for (Collectible i : items) {
            chest.addItem(i);
        }

    }
}
