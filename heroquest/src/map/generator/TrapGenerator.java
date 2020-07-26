package map.generator;

import entity.trap.Trap;
import exceptions.*;
import io.Display;
import map.Map;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * Class used to generate all Trap which will set on Map
 */
public class TrapGenerator {

    private Map map;
    private static TrapGenerator instance;
    private final Random RANDOM = new Random();

    public static TrapGenerator getInstance() {
        if (instance == null) {
            instance = new TrapGenerator();
        }
        return instance;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Method use to generate a Trap
     * @param damage
     * @param position
     * @throws InvalidGeneratorSeedException
     */
    private void generateEntity(int damage, int[] position)
            throws InvalidGeneratorSeedException {

        try {
            if (this.map.isAvailable(position[0], position[1])) {
               this.map.setEntity(new Trap(position[0], position[1], damage));
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
     * Method use to set multiples Traps can be set on map
     * @param hashMap
     */
    public void generateMultipleEntities(
            HashMap<Integer, int[][]> hashMap)  {

        Set<Integer> keys = hashMap.keySet();
        Integer[] damage = new Integer[keys.size()];
        damage = keys.toArray(damage);

        for (Integer d : damage) {
            int[][] positions = hashMap.get(d);
            for (int[] position : positions) {
                try {
                    this.generateEntity(d, position);
                } catch (InvalidGeneratorSeedException e) {
                    Display.printWarning(e.getMessage());
                }
            }
        }
    }
    /**
     * Method use to set random positions and damage Traps can be set on map
     * @param maxNumberOfEntities
     */
    public void generateMultipleRandomEntities(int maxNumberOfEntities) {
        int remainingEntities= maxNumberOfEntities;
        do {

            int damage = RANDOM.nextInt(3) + 1;
            int[] position = new int[] {
                    RANDOM.nextInt(this.map.getMapSize()[0]),
                    RANDOM.nextInt(this.map.getMapSize()[1])
            };

            try {
                remainingEntities--;
                this.generateEntity(damage, position);
            } catch (InvalidGeneratorSeedException e) {
                remainingEntities++;
            }

        } while(remainingEntities > 0);
    }
}
