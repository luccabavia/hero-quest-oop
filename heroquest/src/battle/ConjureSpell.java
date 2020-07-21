package battle;

import dice.CombatDiceType;
import entity.character.Character;
import dice.Dice;
import entity.character.SpellCaster;
import io.Keyboard;
import item.equipment.spell.Spell;
import map.Map;

public class ConjureSpell {

    private SpellCaster caster;
    private Map map;

    private ConjureSpell(Map map, SpellCaster caster) {
        this.map = map;
        this.caster = caster;
    }

    public void castSpell() {

        //
        int range = this.caster.getEquippedSpellRange();

    }


}
