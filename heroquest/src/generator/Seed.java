package generator;

import entity.character.monster.MonsterType;

public class Seed {

    private MonsterType[] monsterTypes;
    private int[] typeRanges;
    private int[][] positions;

    public Seed(MonsterType[] monsterTypes, int[] typeRanges,
                int[][] positions) {
        if (monsterTypes.length == typeRanges.length) {
            int sum = 0;
            for (int i:typeRanges) {
                sum = sum + i;
            }
            if (sum == positions.length) {

            }
        }

    }

}
