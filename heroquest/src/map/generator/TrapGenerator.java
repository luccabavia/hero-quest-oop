package map.generator;

import entity.trap.Trap;
import exceptions.*;
import io.Display;
import map.Map;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class TrapGenerator {

    private Map map;
    private Random randomNumberGenerator = new Random();

    public TrapGenerator(Map map) {
        this.map = map;
    }

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

    public void generateMultipleRandomEntities(int maxNumberOfEntities) {
        int remainingEntities= maxNumberOfEntities;
        do {

            int damage = randomNumberGenerator.nextInt(3) + 1;
            int[] position = new int[] {
                    randomNumberGenerator.nextInt(this.map.getMapSize()[0]),
                    randomNumberGenerator.nextInt(this.map.getMapSize()[1])
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
