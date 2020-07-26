package item.equipment.spell;

import entity.character.Character;
import entity.character.monster.Monster;
import map.Map;
import target.Target;

import java.util.ArrayList;

public class Fireball extends AttackSpell {

    private final int AREA_OF_EFFECT_DAMAGE = 3;
    private Map map;

    public Fireball(Map map) {
        super(1, 6, "Fireball", 6,
                SpellEffectType.ATTACK, 1);
        this.map = map;
    }

    @Override
    public void castSpell(Target target) {
        Character targetChar = target.getCharacter();
        this.damageTarget(targetChar, lifeEffect);

        int[] targetsPosition = targetChar.getPosition();
        this.aoeDamage(targetsPosition[0], targetsPosition[1]);

        this.casts--;
    }

    private void aoeDamage(int x, int y) {
        ArrayList<Monster> monster =
                this.map.getCharactersAround(x, y);
        for (Monster m: monster) {
            this.damageTarget(m, AREA_OF_EFFECT_DAMAGE);
        }
    }
}
