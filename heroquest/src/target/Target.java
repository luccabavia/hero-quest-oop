package target;

/**
 * Container class of possibilities spells targets
 * Target can be a position, a Character or both
 */
import entity.character.Character;

import java.util.ArrayList;

public class Target {

   private ArrayList<Character> characters = new ArrayList<>();
   private int[] position;

   public void setPosition(int[] position) {
       this.position = position;
   }

    public int[] getPosition() {
        return this.position;
    }

    public void setCharacter(Character character) {
        this.characters.add(character);
    }

    public Character getCharacter() {
        return this.characters.remove(0);
    }

    public int getSize() {
       return this.characters.size();
    }
}
