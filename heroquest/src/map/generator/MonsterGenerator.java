package map.generator;

import entity.character.monster.*;
import io.Display;
import map.Map;
import exceptions.*;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * Class used to generate all Monster which will set on Map
 */
public class MonsterGenerator {

    private Map map;
    private static MonsterGenerator instance;
    private final Random RANDOM = new Random();

    public static MonsterGenerator getInstance() {
        if (instance == null) {
            instance = new MonsterGenerator();
        }
        return instance;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Method use to generate a Monster
     * @param monsterType
     * @param position
     * @throws InvalidGeneratorSeedException
     */
    private void generateEntity(MonsterType monsterType, int[] position)
            throws InvalidGeneratorSeedException {

        try {
            if (this.map.isAvailable(position[0], position[1])) {
                switch (monsterType) {
                    case SKELETON:
                        this.map.setEntity(new Skeleton(map, position[0],
                                position[1]));
                        break;
                    case SKELETON_MAGE:
                        this.map.setEntity(new SkeletonMage(map, position[0]
                                , position[1]));
                        break;
                    case GOBLIN:
                        this.map.setEntity(new Goblin(map, position[0],
                                position[1]));
                        break;
                }
            }
        } catch (PositionDoesNotExistException
                | IsTrapException
                | CannotWalkOverException e) {
            throw new InvalidGeneratorSeedException(
                    String.format("Cannot place entity at " + 
                    "position: %d %d", position[0], position[1])
            );
        }
    }

    /**
     * Method use to set multiples Monsters can be set on map
     * @param hashMap
     */
    public void generateMultipleEntities(
            HashMap<MonsterType, int[][]> hashMap) {

        Set<MonsterType> keys = hashMap.keySet();
        MonsterType[] monsterTypes = new MonsterType[keys.size()];
        monsterTypes = keys.toArray(monsterTypes);

        for (MonsterType type : monsterTypes) {
            int[][] positions = hashMap.get(type);
            for (int[] position : positions) {
                try {
                    this.generateEntity(type, position);
                } catch (InvalidGeneratorSeedException e) {
                    Display.printWarning(e.getMessage());
               }
            }
        }
    }
    /**
     * Method use to set random positions and damage Traps can be set on map
     * @param maxEntitiesNumber
     */
    public void generateMultipleRandomEntities(
            int maxEntitiesNumber) {
        int remainingEntities= maxEntitiesNumber;
        do {
            MonsterType type =
                    MonsterType.values()[
                            RANDOM.nextInt(
                                    MonsterType.values().length)
                            ];
            int[] position = new int[] {
                    RANDOM.nextInt(this.map.getMapSize()[0]),
                    RANDOM.nextInt(this.map.getMapSize()[1])
            };

            try {
                remainingEntities--;
                this.generateEntity(type, position);
            } catch (InvalidGeneratorSeedException e) {
                remainingEntities++;
            }

        } while(remainingEntities > 0);
    }

    public void generateRandomEntityAtPosition(int[] position)
            throws InvalidGeneratorSeedException {
        MonsterType type =
                MonsterType.values()[
                        RANDOM.nextInt(
                                MonsterType.values().length)
                        ];
        this.generateEntity(type, position);
    }

}
