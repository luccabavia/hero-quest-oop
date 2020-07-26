package entity.chest;

import exceptions.MonsterHiddenInChestException;
import item.*;

import java.util.ArrayList;

/**
 * Define collectible items chest than can be created in the game.
 * 
 */
public class NormalChest extends Chest {

	protected ArrayList<Collectible> items = new ArrayList<>();

    public NormalChest(int x, int y) {
        super(x, y);
    }

    public void addItem(Collectible Collectible) {
        items.add(Collectible);
    }

    /**
     * Method to return all contents inside the Chest
     * @return
     */
    public String[] displayContents() {
        String[] contents = new String[this.items.size()];
        for (int i = 0; i < this.items.size(); i++) {
            contents[i] = this.items.get(i).getName();
        }
        return contents;
    }

    public int getSize() {
        return items.size();
    }

    @Override
    public boolean hasContents() throws MonsterHiddenInChestException {
        return this.getSize() > 0;
    }

    /**
     * Method to collect an Item selected by Chest Index
     * @param itemIndex
     * @return
     */
    public Collectible collectItems(int itemIndex) {
        Collectible Collectible = items.get(itemIndex);
        items.remove(Collectible);
        return Collectible;
    }

}
