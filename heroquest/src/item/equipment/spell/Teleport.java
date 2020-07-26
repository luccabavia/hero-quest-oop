package item.equipment.spell;

import entity.character.Character;
import exceptions.CannotWalkOverException;
import exceptions.IsTrapException;
import exceptions.PositionDoesNotExistException;
import io.Display;
import map.Map;
import target.Target;

public class Teleport extends Spell {

    private Map map;

    public Teleport(Map map) {
        super(1, 0, "Teleport", 6,
                SpellEffectType.TRANSPORT, 1);
        this.map = map;
    }

    /**
     * Method to cast teleport spell which is featured by go from actual position to a new selected
     * position by user when this position is visible and available
     */
    @Override
    public void castSpell(Target target) {

        Character character = target.getCharacter();
        int[] position = target.getPosition();

        try {
            if (this.map.isVisible(position[0], position[1])
                    && this.map.isAvailable(position[0], position[1])) {
                int[] oldPosition = character.getPosition();
                character.setPosition(position[0], position[1]);
                this.map.updateMap(oldPosition[0], oldPosition[1]);
                this.casts--;
            } else {
                Display.printWarning("Cannot teleport to this position: " +
                        "it is not visible");
            }
        } catch (PositionDoesNotExistException | CannotWalkOverException e) {
            Display.printWarning(e.getMessage());
        } catch (IsTrapException e) {
            Display.printWarning(e.getMessage());
            int damage = e.getDamage();
            int[] newPosition = e.getTrapPosition();
            character.sufferEffect(-damage);
            this.map.disarmTrap(newPosition[0], newPosition[1]);
            Display.print(character.getStatus());
            this.map.updateVisibility();
        }

    }
}
