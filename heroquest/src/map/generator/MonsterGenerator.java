package map.generator;

import entity.character.monster.*;
import io.Display;
import map.Map;
import exceptions.*;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class MonsterGenerator {

    private Map map;
    private Random randGenerator = new Random();

    public MonsterGenerator(Map map) {
        this.map = map;
    }

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

    public void generateMultipleRandomEntities(
            int maxEntitiesNumber) {
        int remainingEntities= maxEntitiesNumber;
        do {
            MonsterType type =
                    MonsterType.values()[
                            randGenerator.nextInt(
                                    MonsterType.values().length)
                            ];
            int[] position = new int[] {
                    randGenerator.nextInt(this.map.getMapSize()[0]),
                    randGenerator.nextInt(this.map.getMapSize()[1])
            };

            try {
                remainingEntities--;
                this.generateEntity(type, position);
            } catch (InvalidGeneratorSeedException e) {
                remainingEntities++;
            }

        } while(remainingEntities > 0);
    }

}
