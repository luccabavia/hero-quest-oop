package dice;

import java.util.Random;

public class Dice {

    // Returns the sum of the values of the number of dices rolled.
    public static int rollRedDice(int nDices) {

        Random random;
        int value = 0;

        for (int i = 0; i < nDices; i++) {
            random = new Random();
            value += random.nextInt(6) + 1;
        }

        return value;
    }

    // Returns a combat mark among skull, white shield and black shield.
    public DiceCombatType rollCombatDice() {

        Random random = new Random();
        int number = random.nextInt(6) + 1;

        if (number == 1 || number == 3 || number == 5) return DiceCombatType.SKULL;
        else if (number == 2 || number == 4) return DiceCombatType.WHITE_SHIELD;
        else return DiceCombatType.BLACK_SHIELD;
    }
}