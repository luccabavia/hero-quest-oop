package generator;

import entity.Entity;
import entity.character.monster.*;
import io.Display;
import map.Map;
import exceptions.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Set;

public class MonsterGenerator {

    protected Map map;

    public MonsterGenerator(Map map) {
        this.map = map;
    }

    public Entity generateNewEntity(MonsterType monsterType, int[] position)
            throws InvalidGeneratorSeedException {

        try {
            if (this.map.isAvailable(position[0], position[1])) {
                switch (monsterType) {
                    case SKELETON:
                        return new Skeleton(map, position[0], position[1]);
                    case SKELETON_MAGE:
                        return new SkeletonMage(map, position[0], position[1]);
                    case GOBLIN:
                        return new Goblin(map, position[0], position[1]);
                }
            }
        } catch (PositionDoesNotExistException
                | IsTrapException
                | CannotWalkOverException e) {
            throw new InvalidGeneratorSeedException(
                    String.format("Cannot place entity at " +
                    "position: %s", e.getMessage())
            );
        }

        return null;

    }

    public ArrayList<Entity> generateMultipleEntities(
            HashMap<MonsterType, int[][]> hashMap) throws
            InvalidGeneratorSeedException {

        Set<MonsterType> keys = hashMap.keySet();
        MonsterType[] monsterTypes = new MonsterType[keys.size()];
        monsterTypes = keys.toArray(monsterTypes);

        ArrayList<Entity> entities = new ArrayList<>();
        for (MonsterType type : monsterTypes) {
            int[][] positions = hashMap.get(type);
            for (int[] position : positions) {
                entities.add(this.generateNewEntity(type,
                        position));
            }
        }
        return entities;

    }

}
