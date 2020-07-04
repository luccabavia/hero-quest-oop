package battle;

<<<<<<< HEAD
import entity.character.Character;
import dice.*;
import io.Keyboard;
=======
/**
 * Define combat interaction between two chacracter
 */
>>>>>>> 37f24fad485c5be04696c57cf938cff25b64e3d6

public class Battle {

    private static Battle instance;
    private Character charA;
    private Character charB;

    private Battle() {

    }

    public static Battle getInstance() {
        if (instance == null) {
            instance = new Battle();
        }
        return instance;
    }

    public void Combat(Character attacker, Character defender) {

        // decidir se vai atacar fisicamente ou spell

        //Keyboard.getInput();

    }

    private void PhysicalCombat(Character attacker, Character defender) {

//        int atk = Dice.rollCombatDice(
//                attacker.getAttackDice(),
//                DiceCombatType.SKULL
//        );
//        int def = Dice.rollCombatDice(
//                defender.getDefenseDice(),
//                defender.getDefDiceType()
//        );
//
//        defender.sufferEffect(Math.max(0, (atk - def)));

    }


}
