package generator;

import entity.Entity;
import entity.character.monster.MonsterType;
import exceptions.InvalidGeneratorSeedException;
import map.Map;

import java.util.ArrayList;
import java.util.Random;

public class RandomMonsterGenerator extends MonsterGenerator {

    private MonsterType[] availableTypes;
    private Random randomNumberGenerator = new Random();

    public RandomMonsterGenerator(Map map, MonsterType[] availableTypes) {
        super(map);
        this.availableTypes = availableTypes;
    }

    public ArrayList<Entity> generateMultipleRandomEntities(
            int maxEntitiesNumber) {
        ArrayList<Entity> entities = new ArrayList<>();
        int remainingEntities= maxEntitiesNumber;
        do {
            MonsterType type =
                    availableTypes[
                            randomNumberGenerator.nextInt(
                                    availableTypes.length)
                            ];
            int[] position = new int[] {
                    randomNumberGenerator.nextInt(this.map.getMapSize()[0]),
                    randomNumberGenerator.nextInt(this.map.getMapSize()[1])
            };

            try {
                remainingEntities--;
                entities.add(this.generateNewEntity(type, position));
            } catch (InvalidGeneratorSeedException e) {
                remainingEntities++;
            }

        } while(remainingEntities > 0);
        return entities;
    }
}
