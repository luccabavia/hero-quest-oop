package map;

import entity.Entity;
import entity.character.Character;
import entity.character.monster.Skeleton;
import entity.scenery.*;
import exception.*;
import io.Display;
import io.ImportFromFile;
import map.MapMode;

import java.util.ArrayList;

public class Map {

    private static MapMode mapMode;
    private static Map instance;
    private Entity[][][] map;
    private boolean visible[][];

    private Map() {
        this.importMap();
//        this.setGameMode();
    }

    private void importMap() {

        String currentDir = System.getProperty("user.dir");
        String path = String.format("%s/%s",
                currentDir,
                "config/map/default_map_hero_quest.txt"
        );
//        path = "/Users/lucca.bavia/Development/MCC322/Projeto/hero-quest-oop" +
//                "/heroquest/config/map/test_map.txt";
        this.map = ImportFromFile.importMap(path);
        
        visible = new boolean[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++) {
            	visible[i][j] = false;	
            }
        }
    }

    public void setGameMode(MapMode mapMode) {

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

    }

    public static Map getInstance() {
        if (instance == null) {
            instance = new Map();
        }
        return instance;
    }

    // ImplementaÃ§Ã£o de singleton antiga
//    public static Map getInstance(MapMode mode) {
//        if (mapInstance == null) {
//            if (mapMode == null) {
//                mapMode = MapMode.DEFAULT;
//            }
//            mapInstance = new Map();
//        }
//        return mapInstance;
//    }

    public boolean hasItem(int[] position) {
        return true;
    }

    public void drawMap() {
        StringBuilder s = new StringBuilder("");
        String fog = "??";
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++) {
                /* Entidade atualmente Ã© uma CLASSE ABSTRATA, podemos trocar
                * para ITERFACE, usando como exemplo o trabalho feito no
                * LAB 13
                */
            	if(visible[i][j]) {
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
    	// return this.map[x][y].equals(floor);
        // REFAZER PENSANDO EM COLOCAR CADA OBJETO DO CENÃ�RIO COMO ESTÃ�TICO
        // TIRAR O OPERADOR INSTANCE OF!!!
        try {
            if (!((Scenery) this.map[x][y][0]).canWalkOver()) {
                throw new CannotWalkOverException(
                		String.format("Cannot walk over wall")
                );
            }
            if (this.map[x][y][1] != null) {
                throw new CannotWalkOverException(
                		String.format("Cannot walk to occupied position")
                );
            }
            return true;
        } catch (ClassCastException e) {
            throw new CannotWalkOverException(
                    String.format("Cannot walk to position (%d, %d)", x, y)
            );
        }

    }

    public boolean isAvailable(int x, int y) throws PositionDoesNotExistException, CannotWalkOverException {
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

    private void createStandardMap() {

        int[][] skeletonPositions = new int[][] {{12, 18}, {1, 1}, {3, 3},
                {2, 4}};

        for (int[] pos: skeletonPositions) {
            this.map[pos[0]][pos[1]][1] = new Skeleton(
                    this,
                    pos[0],
                    pos[1]);
        }

    }

    private void createRandomMap() {

    }

    public ArrayList<Character> getCharacter() {

        ArrayList<Character> chars = new ArrayList<>();
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[0].length; j++) {
                if (this.map[i][j][1] != null) {
                    try {
                        Character character = (Character) this.map[i][j][1];
                        chars.add((Character) this.map[i][j][1]);
                    } catch (ClassCastException e) {
                        System.out.printf("No character found in position " +
                                "(%d, %d).\n", i, j);
                        System.out.println(e);
                    }

                }
            }
        }

        return chars;
    }
    
    public void setEntity(Entity ent) throws PositionDoesNotExistException, CannotWalkOverException {
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


    public void updateMap(int oldX, int oldY) {

        Entity ent = this.map[oldX][oldY][1];
        int[] pos = ent.getPosition();

        this.map[pos[0]][pos[1]][1] = ent;
        this.map[oldX][oldY][1] = null;
    }
    
    public void updateVisibility(int[] pos) {
    	boolean stop = true;
    	
    	for (int i = pos[1]; i < this.map[0].length && stop; i++) {
    		try{
	    			if((!this.map[pos[0]][i][0].getSprite().contains("==") &&
	    				!this.map[pos[0]][i][0].getSprite().contains("++"))
	    				|| !positionIsEmpty(pos[0], i))
	    			
	    				stop = false;
    		
	    			visible[pos[0]][i] = true;
    	    } catch (CannotWalkOverException e) {
    	    	stop = false;
    	    	visible[pos[0]][i] = true;
    	    }	
    		
    	} 
    	
    	stop = true;
    	for (int i = pos[1] - 1; i > 0 && stop; i--) {
    		try{
    			if((!this.map[pos[0]][i][0].getSprite().contains("==") 
    					&& !this.map[pos[0]][i][0].getSprite().contains("++"))
    					|| !positionIsEmpty(pos[0], i))
	    				
    					stop = false;
	    		
	    		visible[pos[0]][i] = true;
	    		
    		} catch (CannotWalkOverException e) {
    			stop = false;
    			visible[pos[0]][i] = true;
    		}	
    	}   
    	
    	stop = true;
    	for (int i = pos[0] + 1; i < this.map.length && stop; i++) {
    		try{
	    		if((!this.map[i][pos[1]][0].getSprite().contains("==") 
	    				&& !this.map[i][pos[1]][0].getSprite().contains("++"))
	    				||  !positionIsEmpty(i, pos[1]))
	    				
	    				stop = false;
	    		
	    		visible[i][pos[1]] = true;
    		} catch (CannotWalkOverException e) {
    			stop = false;
    			visible[i][pos[1]] = true;
    		}	

    	} d
    	
    	stop = true;
    	for (int i = pos[0] - 1; i > 0 && stop; i--) {
    		try{
	    		if((!this.map[i][pos[1]][0].getSprite().contains("==") 
	    				&& !this.map[i][pos[1]][0].getSprite().contains("++"))
	    				||  !positionIsEmpty(i, pos[1]))
	    				
	    				stop = false;
	    		
	    		visible[i][pos[1]] = true;
	    		
    		} catch (CannotWalkOverException e) {
    			stop = false;
    			visible[i][pos[1]] = true;
    		}	

    	} 
    	
    }
    
}
