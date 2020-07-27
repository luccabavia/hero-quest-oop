package map;

import entity.*;
import entity.character.*;
import entity.character.hero.*;
import entity.character.monster.*;
import entity.chest.Chest;
import entity.scenery.*;
import entity.trap.*;
import exceptions.*;
import map.generator.*;
import io.ImportFromFile;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Define Map Class where Game takes place
 *
 */
public class Map {

    private static Map instance;
    private Entity[][][] map;
    private Hero hero;
    private boolean visibility[][];
    private boolean heroCanCastSpell;
    private final MonsterGenerator MONSTER_GENERATOR;
    private final ChestGenerator CHEST_GENERATOR;
    private final TrapGenerator TRAP_GENERATOR;

    /**
     *  Constructor method to instance all kinds of interactive objects generators can be set inside a map
     */
    private Map() {
        this.importMap();

        this.MONSTER_GENERATOR = MonsterGenerator.getInstance();
        this.CHEST_GENERATOR = ChestGenerator.getInstance();
        this.TRAP_GENERATOR = TrapGenerator.getInstance();

        this.MONSTER_GENERATOR.setMap(this);
        this.CHEST_GENERATOR.setMap(this);
        this.TRAP_GENERATOR.setMap(this);

    }

    public static Map getInstance() {
        if (instance == null) {
            instance = new Map();
        }
        return instance;
    }

    /**
     * Method to import root map from a TXT file
     */
    private void importMap() {

        String currentDir = System.getProperty("user.dir");
        String path = String.format("%s/%s",
                currentDir,
                "/heroquest/config/map/default_map_hero_quest.txt"
        );
        this.map = ImportFromFile.importMap(path);

        visibility = new boolean[map.length][map[0].length];
    }

    public int[] getMapSize() {
        return new int[] {
                this.map.length,
                this.map[0].length
        };
    }

    /**
     * Method to set kind of game mode an user wants
     * @param mapMode
     * @param hero
     * @throws CannotWalkOverException
     * @throws PositionDoesNotExistException
     * @throws IsTrapException
     */
    public void setGameMode(MapMode mapMode, Hero hero) throws
            CannotWalkOverException, PositionDoesNotExistException,
            IsTrapException {

        try {
            SpellCaster spellCaster = (SpellCaster) hero;
            this.heroCanCastSpell = true;
        } catch (ClassCastException e) {
            this.heroCanCastSpell = false;
        }
        this.placeHero(hero);

        switch (mapMode) {
            case RANDOM:
                this.createRandomMap();
                break;
            default:
                this.createStandardMap();
                break;
        }

    }

    /**
     * Method to generate all kinds of interactive objects at fixed positions can be set inside a map
     */
    private void createStandardMap() {

        HashMap<Boolean, int[][]> chestHashMap = new HashMap<>();
        int[][] trapChestPositions = new int[][] {
                {0, 0},
                {32, 36},
                {4, 31},
                {29, 6},
                {29, 13},
                {22, 33}
        };
        chestHashMap.put(
                true,
                trapChestPositions
        );

        int[][] normalChestPositions = new int[][] {
                {0, 36},
                {3, 11},
                {3, 21},
                {6, 26},
                {7, 11},
                {8, 33},
                {9, 4},
                {10, 25},
                {22, 11},
                {22, 28},
                {24, 3},
                {27, 33},
                {32, 0}
        };
        chestHashMap.put(
                false,
                normalChestPositions
        );

        this.CHEST_GENERATOR.generateMultipleEntities(chestHashMap,
                heroCanCastSpell);

        HashMap<MonsterType, int[][]> monsterHashMap = new HashMap<>();
        int[][] goblinPositions = new int[][] {
                {5, 3},
                {16, 31},
                {26, 8}
        };
        monsterHashMap.put(
                MonsterType.GOBLIN,
                goblinPositions
        );

        int[][] skeletonPositions = new int[][] {
        	{8, 28},
        	{15, 7},
            {16, 27},
            {23, 9}
        };
        monsterHashMap.put(
        	   MonsterType.SKELETON,
               skeletonPositions
        );

        int[][] skeletonMagePositions = new int[][] {
        	{4, 15},
        	{17, 5},
            {26, 23}
        };
        
        monsterHashMap.put(
                MonsterType.SKELETON_MAGE,
                skeletonMagePositions
        );
        
        this.MONSTER_GENERATOR.generateMultipleEntities(monsterHashMap);

        HashMap<Integer, int[][]> trapHashMap = new HashMap<>();
        int[][] trapPositionsDamage2 = new int[][] {
            {4, 10},
            {15, 27}
        };
        trapHashMap.put(
	            2,
	            trapPositionsDamage2
	    );
        
        
        int[][] trapPositionsDamage1 = new int[][] {
            {5, 13},
            {17, 9},
            {27, 22},
            {28, 27}
        };
    	trapHashMap.put(
	            1,
	            trapPositionsDamage1
	    );
        
        this.TRAP_GENERATOR.generateMultipleEntities(trapHashMap);

    }

    /**
     * Method to generate randomly all kinds of interactive objects at randomly positions can be set inside a map
     * at a maximum value chosen by developer
     */
    private void createRandomMap() {

        this.CHEST_GENERATOR.generateMultipleRandomEntities(15,
                heroCanCastSpell);

        this.MONSTER_GENERATOR.generateMultipleRandomEntities(10);

        this.TRAP_GENERATOR.generateMultipleRandomEntities(7);

    }

    /**
     * Method to check if have any chest at specific position
     * @param x
     * @param y
     * @return
     * @throws PositionDoesNotExistException
     */
    public Chest hasChest(int x, int y) throws
    PositionDoesNotExistException {
    	try {
    		this.positionExists(x, y);
    		return (Chest) this.map[x][y][1];
    	} catch (ClassCastException e) {
    		return null;
    	}
    }

    /**
     * Method to return Map visualization 
     * @return an Sprite string array
     */
    public String[][] drawMap() {
    	int[] mapSize = this.getMapSize();
    	String[][] mapView = new String[mapSize[0]][mapSize[1]];
        String fog = "??";
        
        for (int i = 0; i < mapSize[0]; i++){
            for (int j = 0; j < mapSize[1]; j++) {
                if(visibility[i][j]) {
                    if (map[i][j][1] != null) {
                        if (!map[i][j][1].isHidden()) {
                        	mapView[i][j] = map[i][j][1].getSprite();
                        } else {
                        	mapView[i][j] = map[i][j][0].getSprite();
                        }
                    } else {
                    	mapView[i][j] = map[i][j][0].getSprite();
                    }
                } else {
                	mapView[i][j] = fog;
                }

            }
        }
        return mapView;
    }

    /**
     * Method to check if an specific position exists
     * @param x
     * @param y
     * @throws PositionDoesNotExistException when is out of map Bounds
     */
    private void positionExists(int x, int y) throws
            PositionDoesNotExistException {
        if (x < 0 && y < 0 && x > this.map.length && y > this.map[0].length) {
            throw new PositionDoesNotExistException(
                    String.format("Position is out of bounds"));
        }

    }

    /**
     * Method to check if an specific position is empty
     * @param x
     * @param y
     * @throws CannotWalkOverException when had already an object in position or is a Wall
     * @throws IsTrapException when had an trap at position
     */
    private void positionIsEmpty(int x, int y) throws
            CannotWalkOverException, IsTrapException {
        try {
            if (!((Scenery) this.map[x][y][0]).canWalkOver()) {
                throw new CannotWalkOverException(
                        String.format("Cannot walk over wall.")
                );
            }
            if (this.map[x][y][1] != null) {
                if(this.map[x][y][1].isHidden()) {
                    try {
                        int damage = ((Trap) this.map[x][y][1]).getDamage();
                        throw new IsTrapException(
                                String.format("GOTCHA!! You suffered %d " +
                                        "damage from a trap!\n", damage),
                                x, y, damage
                        );
                    } catch (ClassCastException e) {
                        throw e;
                    }
                } else {
                    throw new CannotWalkOverException(
                        String.format("Cannot walk over occupied position because of %s", this.map[x][y][1].getSprite())
                    );
                }
            }
        } catch (ClassCastException e) {
            throw new CannotWalkOverException(
                    String.format("Cannot walk to position (%d, %d)", x, y)
            );
        }

    }

    /**
     * Method to check if an specific position is empty and exist
     * @param x
     * @param y
     * @throws PositionDoesNotExistException
     * @throws CannotWalkOverException
     * @throws IsTrapException
     */
    public void isAvailable(int x, int y) throws
            PositionDoesNotExistException, CannotWalkOverException,
            IsTrapException {
        try {
            this.positionExists(x, y);
            this.positionIsEmpty(x, y);
        } catch (PositionDoesNotExistException e){
            throw e;
        } catch (CannotWalkOverException | IsTrapException e) {
            throw e;
        }
    }

    /**
     * Method to set hero in a specific position of Map
     * @param hero
     * @throws CannotWalkOverException
     * @throws PositionDoesNotExistException
     * @throws IsTrapException
     */
    private void placeHero(Hero hero) throws
            CannotWalkOverException, PositionDoesNotExistException,
            IsTrapException {
        this.hero = hero;
        this.setEntity(hero);
    }

    /**
     * Method to get hero position
     * @return
     */
    public int[] getHeroPosition() {
        return this.hero.getPosition();
    }

    /**
     *  Method to get monster was set at Map
     * @return
     */
    public ArrayList<Monster> getMonster() {

        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[0].length; j++) {
                if (this.map[i][j][1] != null) {
                    try {
                        Monster monster = (Monster) this.map[i][j][1];
                        monsters.add(monster);
                    } catch (ClassCastException e) {

                    }

                }
            }
        }

        return monsters;
    }

    /**
     * Method to get a list of monster inside a Hero's range
     * @param range
     * @return
     */
    public ArrayList<Monster> getMonstersToAttack(int range) {
    	ArrayList<Monster> monsters = new ArrayList<>();
    	
    	int[] heroPos = this.hero.getPosition();
    	
    	ArrayList<Entity> entities = getEntityInRange(range, heroPos[0], heroPos[1]);
    	
    	for(Entity ent: entities) {
    		try {
    			Monster m = (Monster) ent;
    			monsters.add(m);
    		} catch (ClassCastException e) {
    			
    		}
    	}
    	return monsters;
    }
    
    /**
     * Method to get the first not see trough entity from each direction (NORTH, SOUTH, WEST, EAST)
     * @param range
     * @param x
     * @param y
     * @return
     */
    private ArrayList<Entity> getEntityInRange(int range, int x, int y) {

    	ArrayList<Entity> entities = new ArrayList<>();
        
        boolean stop = true;
        for (int i = y + 1; i < this.map[0].length && i <= y + range && stop; i++) {
        	if(!this.map[x][i][0].isSeeThrough())
        		stop = false;
        	if (this.map[x][i][1] != null) {
        			if(this.visibility[x][i])
        				entities.add(this.map[x][i][1]);
        			stop = false;
        	}
        }

        stop = true;
        for (int i = y - 1; i >= 0 && i >= y - range && stop; i--) {
        	if(!this.map[x][i][0].isSeeThrough())
        		stop = false;
        	if (this.map[x][i][1] != null) {
        			if(this.visibility[x][i])
        				entities.add(this.map[x][i][1]);
        			stop = false;
        	}
        }

        stop = true;
        for (int i = x + 1; i < this.map.length && i <= x + range && stop; i++) {
        	if(!this.map[i][y][0].isSeeThrough())
        		stop = false;
        	if (this.map[i][y][1] != null) {
        		if(this.visibility[i][y])
        			entities.add(this.map[i][y][1]);
        		stop = false;
        	}
        }

        stop = true;
        for (int i = x - 1; i >= 0 && i >= x - range && stop; i--) {
        	if(!this.map[i][y][0].isSeeThrough())
        		stop = false;
        	if (this.map[i][y][1] != null) {
        		if(this.visibility[i][y])
        			entities.add(this.map[i][y][1]);
        		stop = false;
        	}	
        }
        return entities;
    }

    /**
     * Return a List of all Monsters which have Hero inside your
     * weapon Range
     * @return
     */
    public ArrayList<Monster> getAttackersMonsters() {
        int range = 0;
        int[] monsterPos;
        ArrayList<Monster> allMonsters = this.getMonster();
        ArrayList<Monster> monsters = new ArrayList<>();

        for(Monster m: allMonsters) {
        	monsterPos = m.getPosition();
        	try {
        		SpellCaster spellCaster = (SpellCaster) m;
        		spellCaster.hasSpells();
        		range = spellCaster.getEquippedSpellRange();
        	} catch (ClassCastException | NoSpellLeftException e) {
        		range = m.getEquippedWeaponRange();
        	}

        	ArrayList<Entity> entities = getEntityInRange(range, monsterPos[0], monsterPos[1]);
        	for(Entity ent: entities) {
        		try {
        			Hero h = (Hero) ent;
        			monsters.add(m);
        		} catch (ClassCastException e) {
        		}
        	}

        }
        
        return monsters;
    }

    /**
     * Method to take the list of characters around a specific position
     * @param x
     * @param y
     * @return
     */
    public ArrayList<Monster> getCharactersAround(int x, int y) {
        ArrayList<Monster> monsters = new ArrayList<>();

        int[][] adjacentPositions = new int[][] {
                {x, y + 1},       // East
                {x - 1, y + 1},   // Northeast
                {x - 1, y},       // North
                {x - 1, y - 1},   // Northwest
                {x, y - 1},       // West
                {x + 1, y - 1},   // Southwest
                {x + 1, y},       // South
                {x + 1, y + 1}    // Southeast
        };

        for (int[] position:adjacentPositions) {
            try {
                if (this.map[position[0]][position[1]][1] != null) {
                    monsters.add(
                            (Monster) this.map[position[0]][position[1]][1]
                    );
                }
            } catch (ClassCastException e) {
                continue;
            }
        }

        return monsters;
    }

    /**
     * Method to set an entity in the Map
     * @param ent
     * @throws PositionDoesNotExistException
     * @throws CannotWalkOverException
     * @throws IsTrapException
     */
    public void setEntity(Entity ent) throws
            PositionDoesNotExistException, CannotWalkOverException,
            IsTrapException {
        int[] pos = ent.getPosition();
        try {
            isAvailable(pos[0], pos[1]);
            this.map[pos[0]][pos[1]][1]= ent;
        } catch (PositionDoesNotExistException | CannotWalkOverException | IsTrapException e) {
            throw e;
        }
    }

    /**
     * Method to remove an entity in the Map
     * @param entity
     */
    public void removeEntity(Entity entity) {
        int[] pos = entity.getPosition();
        this.map[pos[0]][pos[1]][1] = null;
    }

    /**
     * Method to check if have any monster inside Map
     * @return
     */
    public boolean hasMonsters() {
        ArrayList<Monster> monsters = getMonster();
        return monsters.size() > 0;
    }

    /**
     * Method to disarm a Trap chest and generate a monster at same position
     * @param position
     */
    public void disarmTrapChest(int[] position) {

        this.removeEntity((Chest) this.map[position[0]][position[1]][1]);
        try {
            this.MONSTER_GENERATOR.generateRandomEntityAtPosition(position);
        } catch (InvalidGeneratorSeedException e) {
        }
    }

    /**
     * Method removing the disarmed trap and putting Hero at same position 
     * @param x
     * @param y
     */
    public void disarmTrap(int x, int y) {

        Trap trap = (Trap) this.map[x][y][1];
        this.removeEntity(trap);
        int[] oldPosition = this.hero.getPosition();
        this.hero.setPosition(x, y);
        this.updateMap(oldPosition[0], oldPosition[1]);

    }

    /**
     * Method to update Entity position inside the map
     * @param oldX
     * @param oldY
     */
    public void updateMap(int oldX, int oldY) {

        Entity entity = this.map[oldX][oldY][1];
        int[] pos = entity.getPosition();

        this.map[pos[0]][pos[1]][1] = entity;
        this.map[oldX][oldY][1] = null;
    }

    /**
     * Method to update Hero's visibility of displayed map in each direction(NORTH, SOUTH, EAST, EAST)
     * The method update visibilities in each direction until a not see trough entity
     */
    public void updateVisibility() {

        int[] heroPos = this.getHeroPosition();
        this.visibility[heroPos[0]][heroPos[1]] = true;

        boolean stop = true;
        for (int i = heroPos[1] + 1; i < this.map[0].length && stop; i++) {
            try{
            	positionIsEmpty(heroPos[0], i);
                if(!this.map[heroPos[0]][i][0].isSeeThrough())
                    stop = false;
            } catch (CannotWalkOverException e) {
                stop = false;
            } catch (IsTrapException e) {
                stop = true;
            }
            visibility[heroPos[0]][i] = true;
        }

        stop = true;
        for (int i = heroPos[1] - 1; i > 0 && stop; i--) {
            try{
            	positionIsEmpty(heroPos[0], i);
                if(!this.map[heroPos[0]][i][0].isSeeThrough())
                    stop = false;
            } catch (CannotWalkOverException e) {
                stop = false;
            } catch (IsTrapException e) {
                stop = true;
            }
            visibility[heroPos[0]][i] = true;
        }

        stop = true;
        for (int i = heroPos[0] + 1; i < this.map.length && stop; i++) {
            try{
            	positionIsEmpty(i, heroPos[1]);
                if(!this.map[i][heroPos[1]][0].isSeeThrough())
                    stop = false;
            } catch (CannotWalkOverException e) {
                stop = false;
            } catch (IsTrapException e) {
                stop = true;
            }
            visibility[i][heroPos[1]] = true;
        }

        stop = true;
        for (int i = heroPos[0] - 1; i > 0 && stop; i--) {
            try{
            	positionIsEmpty(i, heroPos[1]);
                if(!this.map[i][heroPos[1]][0].isSeeThrough())
                    stop = false;
            } catch (CannotWalkOverException e) {
                stop = false;
            } catch (IsTrapException e) {
                stop = true;
            }
            visibility[i][heroPos[1]] = true;
        }
    }

    /**
     * Method to check visibility of a specific position
     * @param x
     * @param y
     * @return
     */
    public boolean isVisible(int x, int y) {
        return this.visibility[x][y];
    }
}
