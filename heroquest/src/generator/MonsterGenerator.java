package generator;

import entity.Entity;
import entity.character.monster.*;
import map.Map;
import exceptions.*;

import java.util.ArrayList;

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
            MonsterType[] monsterTypes, int[][] positions) throws
            InvalidGeneratorSeedException {

        if (monsterTypes.length == positions.length) {
            ArrayList<Entity> entities = new ArrayList<>(monsterTypes.length);
            for (int i = 0; i < monsterTypes.length; i++) {
                entities.add(this.generateNewEntity(monsterTypes[i],
                        positions[i]));
            }
            return entities;
        } else {
            throw new InvalidGeneratorSeedException(
                    String.format("Source type array and position array " +
                            "are of different sizes: types=%d, positions=%d",
                            monsterTypes.length,
                            positions.length
                    )
            );
        }
    }

}
