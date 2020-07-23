package battle;

import dice.CombatDiceType;
import entity.character.Character;
import dice.Dice;
import io.Display;
import io.Keyboard;
import map.Map;

/**
 * Define combat interaction between two characters
 */
public class Battle {

    private static Battle instance;
    private Character charA;
    private Character charB;
    //private Map map;

    public Battle(){}//Map map) {
       //this.map = map;
    //}

//    public static Battle getInstance() {
//        if (instance == null) {
//            instance = new Battle();
//        }
//        return instance;
//    }

    public void physicalCombat(Character attacker, Character defender) {

        int atk = Dice.rollCombatDice(
                attacker.getAttack(),
                CombatDiceType.SKULL
        );

        int def = Dice.rollCombatDice(
                defender.getDefense(),
                defender.getDefenseType()
        );

        defender.sufferEffect(Math.min(0, -(atk - def)));
        this.afterActionReport(attacker, defender);

    }

    private void afterActionReport(Character attacker, Character defender) {
        String report = String.format(
                "\nAttacker: {%s}\n" +
                        "Defender: {%s}",
                attacker.getStatus(true),
                defender.getStatus(true)
        );
        Display.print(report);
    }


}
