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
     * @param nDices int
     * @param action enum entry
     * @return a number of action figures used for damage calculations
     */
    public static int rollCombatDice(int nDices,
                                                CombatDiceType action) {

        int skull = 0;
        int whiteShield = 0;
        int blackShield = 0;

        Random random = new Random();
        for (int i = 0; i < nDices; i++) {

            int number = random.nextInt(6) + 1;
            if (number == 1 || number == 3 || number == 5) skull++;
            else if (number == 2 || number == 4) whiteShield++;
            else blackShield++;
        }

        switch (action) {
            case BLACK_SHIELD:
                return blackShield;
            case WHITE_SHIELD:
                return whiteShield;
            default:
                return skull;
        }

    }
}