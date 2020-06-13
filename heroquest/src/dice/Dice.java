package dice;

import java.util.Random;

/**
 * Simulates the behaviour of dice, generating randomized outputs for
 * numbered dice and action dice.
 */
public class Dice {

    /**
     * Generate random numbers from 1 through 6, simulation n number of dices.
     * @param numberOfDices int
     * @return the sum of random values generated
     */
    public static int rollRedDice(int numberOfDices) {

        Random random;
        int value = 0;

        for (int i = 0; i < numberOfDices; i++) {
            random = new Random();
            value += random.nextInt(6) + 1;
        }

        return value;
    }

    /**
     * Generate a random output from action dice enumeration
     * (@link dice.DiceCombatType).
     * @return  a random entry from combat dice enumeration
     */
    public static DiceCombatType rollCombatDice() {
        Random random = new Random();
        int number = random.nextInt(6) + 1;

        if (number == 1 || number == 3 || number == 5) return DiceCombatType.SKULL;
        else if (number == 2 || number == 4) return DiceCombatType.WHITE_SHIELD;
        else return DiceCombatType.BLACK_SHIELD;
    }
}