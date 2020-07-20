package item.equipment.spell;

import entity.character.Character;

public interface AttackSpell {

    void castSpell(Character target);
        //target.sufferEffect(this.lifeEffect);

    void castSpell(Character[] targets);

}
