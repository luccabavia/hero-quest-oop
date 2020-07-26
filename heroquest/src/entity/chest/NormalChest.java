package entity.chest;

import exceptions.MonsterHiddenInChestException;
import io.Display;
import item.*;

import java.util.ArrayList;

public class NormalChest extends Chest {

	protected ArrayList<Collectible> items = new ArrayList<>();

    public NormalChest(int x, int y) {
        super(x, y);
    }

    public void addItem(Collectible Collectible) {
        items.add(Collectible);
    }

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

    public Collectible collectItems(int itemIndex) {
        Collectible Collectible = items.get(itemIndex);
        items.remove(Collectible);
        return Collectible;
    }

}
