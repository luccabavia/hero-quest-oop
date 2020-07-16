package map;

import entity.Entity;
import entity.character.Character;
import entity.character.hero.Hero;
import entity.character.monster.Goblin;
import entity.character.monster.Monster;
import entity.character.monster.Skeleton;
import entity.chest.Chest;
import entity.scenery.*;
import exceptions.CannotWalkOverException;
import exceptions.NoChestFoundException;
import exceptions.PositionDoesNotExistException;
import io.Display;
import io.ImportFromFile;
import item.Item;
import item.equipment.weapon.LongSword;
import item.equipment.weapon.ShortSword;

import java.util.ArrayList;

public class Map {

    private static MapMode mapMode;
    private static Map instance;
    private Entity[][][] map;
    private Hero hero;
    private boolean visibility[][];

    private Map() {
        this.importMap();
//        this.setGameMode();
    }

    private void importMap() {

        String currentDir = System.getProperty("user.dir");
        String path = String.format("%s/%s",
                currentDir,
                "/config/map/default_map_hero_quest.txt"
        );
//        path = "/heroquest/config/map/test_map.txt";
        this.map = ImportFromFile.importMap(path);

        visibility = new boolean[map.length][map[0].length];
    }

    public int[] setGameMode(MapMode mapMode) {

        int[] startPosition = new int[] {0, 0};
        switch (mapMode) {
            case RANDOM:
                // mapa aleatorio
                this.createRandomMap();
                break;
            default:
                // mapa padrao
                this.createStandardMap();
                break;
        }
        return startPosition;
    }

    public static Map getInstance() {
        if (instance == null) {
            instance = new Map();
        }
        return instance;
    }

    public Chest hasChest(int x, int y) throws
            PositionDoesNotExistException {

            if (this.positionExists(x, y)) {
                try {
                    return (Chest) this.map[x][y][1];
                } catch (ClassCastException e) {
                    return null;
//                    throw new NoChestFoundException(
//                            String.format("No chest at (%d, %d)", x, y));
                }
            }
            return null;
    }

    public void drawMap() {
        StringBuilder s = new StringBuilder("");
        String fog = "??";
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++) {
                /* Entidade atualmente é uma CLASSE ABSTRATA, podemos trocar
                * para ITERFACE, usando como exemplo o trabalho feito no
                * LAB 13
                */
                if(visibility[i][j]) {
                    if (map[i][j][1] != null) {
                        s.append(map[i][j][1].getSprite());
                    } else {
                        s.append(map[i][j][0].getSprite());
                    }
                }else {
                    s.append(fog);
                }

                if (j != map[0].length - 1) {
                    s.append(" ");
                }

            }
            s.append("\n");
        }
        System.out.println(s.toString());
    }

    private boolean positionExists(int x, int y) throws
            PositionDoesNotExistException {
        if (x >= 0 && y >= 0 &&
                x < this.map.length && y < this.map[0].length) {
            return true;
        } else {
            throw new PositionDoesNotExistException(
                    String.format("Position is out of bounds")
            );
        }

    }

    private boolean positionIsEmpty(int x, int y) throws
            CannotWalkOverException {
//        return this.map[x][y].equals(floor);
        try {
            if (!((Scenery) this.map[x][y][0]).canWalkOver()) {
                throw new CannotWalkOverException(
                        String.format("Cannot walk over wall.")
                );
            }
            if (this.map[x][y][1] != null) {
                throw new CannotWalkOverException(
                        String.format("Cannot walk over occupied position")
                );
            }
            return true;
        } catch (ClassCastException e) {
            throw new CannotWalkOverException(
                    String.format("Cannot walk to position (%d, %d)", x, y)
            );
        }

    }

    public boolean isAvailable(int x, int y) throws
            PositionDoesNotExistException, CannotWalkOverException {
        boolean isEmpty = false;
        boolean exists = false;
        try {
            exists = this.positionExists(x, y);
            isEmpty = this.positionIsEmpty(x, y);
        } catch (PositionDoesNotExistException e){
            exists = false;
            throw e;
        } catch (CannotWalkOverException e) {
            isEmpty = false;
            throw e;
        }
        return isEmpty && exists;
    }

    public void placeHero(Hero hero) throws
            CannotWalkOverException, PositionDoesNotExistException {
        this.hero = hero;
        this.setEntity((Entity) hero);
    }

    public int[] getHeroPosition() {
        return this.hero.getPosition();
    }

    //This function return all Visible Monster in a square of range which is defined by Hero´s range
    public ArrayList<Monster> getMonsterToAttack(int range) {
    	
    	ArrayList<Monster> monsters = new ArrayList<>();
    	int[] heroStart = this.hero.getPosition();
        
    	heroStart[0] -= range;
    	heroStart[1] -= range;
    	
    	
    	if(heroStart[0] < 0)
    		heroStart[0] = 0;
    		
    	if(heroStart[1] < 0)
    		heroStart[1] = 0;
    	
    	for (int i = heroStart[0]; i < this.map.length && i <= heroStart[0] + range; i++) {
            for (int j = heroStart[1]; j < this.map[0].length && i <= heroStart[1] + range; j++) {
                if (this.map[i][j][1] != null) {
                    try {
                        Character Monster = (Monster) this.map[i][j][1];
                        if(this.visibility[i][j])
                        	monsters.add((Monster) this.map[i][j][1]);
                    } catch (ClassCastException e) {
                    }

                }
            }
        }
    	
        return monsters;
    }
    
    private void createStandardMap() {

        int[][] skeletonPositions = new int[][] {{12, 18}, {1, 1}, {3, 3},
                {2, 4}};
        int[][] goblinPositions = new int[][] {{15, 16}};

        for (int[] pos: skeletonPositions) {
            this.map[pos[0]][pos[1]][1] = new Skeleton(
                    this,
                    pos[0],
                    pos[1]);
        }

        for (int[] pos: goblinPositions) {
            this.map[pos[0]][pos[1]][1] = new Goblin(
                    this,
                    pos[0],
                    pos[1]);
        }

        int[][] chestPosition = new int[][] {
                {0, 5},
                {0, 8}
        };

        Item longSword = new LongSword();
        Item shortSword = new ShortSword();

        for (int[] pos: chestPosition) {
            Chest chest = new Chest(pos[0], pos[1]);
            chest.addItem(longSword);
            chest.addItem(shortSword);
            this.map[pos[0]][pos[1]][1] = chest;
        }

    }

    private void createRandomMap() {

    }

    public ArrayList<Monster> getMonster() {

        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[0].length; j++) {
                if (this.map[i][j][1] != null) {
                    try {
                        Character Monster = (Monster) this.map[i][j][1];
                        monsters.add((Monster) this.map[i][j][1]);
                    } catch (ClassCastException e) {
//                        Display.print(String.format("No character found in " +
//                                        "position (%d, %d).\n", i, j));
//                        System.out.println(e);
                    }

                }
            }
        }

        return monsters;
    }

    public void setEntity(Entity ent) throws
            PositionDoesNotExistException, CannotWalkOverException {
        int pos[] = ent.getPosition();
        try {
            if(isAvailable(pos[0], pos[1]))
                this.map[pos[0]][pos[1]][1]= ent;

        } catch (PositionDoesNotExistException e) {
            throw e;
        } catch (CannotWalkOverException e) {
            throw e;
        }
    }

    public boolean hasMonsters() {
        ArrayList<Monster> monsters = getMonster();
        return monsters.size() > 0;
    }

    public void updateMap(int oldX, int oldY) {

        Entity ent = this.map[oldX][oldY][1];
        int[] pos = ent.getPosition();

        this.map[pos[0]][pos[1]][1] = ent;
        this.map[oldX][oldY][1] = null;
    }

    public void viewAllMap() {
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++) {
                visibility[i][j] = true;
            }
        }
    }

    public void updateVisibility() {

        int[] heroPos = this.getHeroPosition();
        this.visibility[heroPos[0]][heroPos[1]] = true;

        boolean stop = true;
        for (int i = heroPos[1] + 1; i < this.map[0].length && stop; i++) {
            try{
                if((!this.map[heroPos[0]][i][0].getSprite().contains("==") &&
                        !this.map[heroPos[0]][i][0].getSprite().contains("++"))
                        || !positionIsEmpty(heroPos[0], i))
                    stop = false;
            } catch (CannotWalkOverException e) {
                stop = false;
            }
            visibility[heroPos[0]][i] = true;
        }

        stop = true;
        for (int i = heroPos[1] - 1; i > 0 && stop; i--) {
            try{
                if((!this.map[heroPos[0]][i][0].getSprite().contains("==")
                        && !this.map[heroPos[0]][i][0].getSprite().contains("++"))
                        || !positionIsEmpty(heroPos[0], i))
                    stop = false;
            } catch (CannotWalkOverException e) {
                stop = false;
            }
            visibility[heroPos[0]][i] = true;
        }

        stop = true;
        for (int i = heroPos[0] + 1; i < this.map.length && stop; i++) {
            try{
                if((!this.map[i][heroPos[1]][0].getSprite().contains("==")
                        && !this.map[i][heroPos[1]][0].getSprite().contains("++"))
                        ||  !positionIsEmpty(i, heroPos[1]))
                    stop = false;
            } catch (CannotWalkOverException e) {
                stop = false;
            }
            visibility[i][heroPos[1]] = true;
        }

        stop = true;
        for (int i = heroPos[0] - 1; i > 0 && stop; i--) {
            try{
                if((!this.map[i][heroPos[1]][0].getSprite().contains("==")
                        && !this.map[i][heroPos[1]][0].getSprite().contains("++"))
                        ||  !positionIsEmpty(i, heroPos[1]))
                    stop = false;
            } catch (CannotWalkOverException e) {
                stop = false;
            }
            visibility[i][heroPos[1]] = true;
        }
    }
}
