package map;

import entity.Entity;
import entity.character.Character;
import entity.character.hero.Hero;
import entity.character.monster.Monster;
import entity.character.monster.Skeleton;
import entity.scenery.*;
import exceptions.CannotWalkOverException;
import exceptions.PositionDoesNotExistException;
import io.Display;
import io.ImportFromFile;

import java.util.ArrayList;

public class Map {

    private static MapMode mapMode;
    private static Map instance;
    private Entity[][][] map;
    private Hero hero;

    private Map() {
        this.importMap();
//        this.setGameMode();
    }

    private void importMap() {

        String currentDir = System.getProperty("user.dir");
        String path = String.format("%s/%s",
                currentDir,
                "heroquest/config/map/default_map_hero_quest.txt"
        );
//        path = "/Users/lucca.bavia/Development/MCC322/Projeto/hero-quest-oop" +
//                "/heroquest/config/map/test_map.txt";
        this.map = ImportFromFile.importMap(path);
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

    public boolean hasItem(int[] position) {
        return true;
    }

    public void drawMap() {
        StringBuilder s = new StringBuilder("");
        String piece = "?";
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++) {
                /* Entidade atualmente é uma CLASSE ABSTRATA, podemos trocar
                * para ITERFACE, usando como exemplo o trabalho feito no
                * LAB 13
                */
                if (map[i][j][1] != null) {
                    s.append(map[i][j][1].getSprite());
                } else {
                    s.append(map[i][j][0].getSprite());
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
        // REFAZER PENSANDO EM COLOCAR CADA OBJETO DO CENÁRIO COMO ESTÁTICO
        // TIRAR O OPERADOR INSTANCE OF!!!
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

    public boolean isAvailable(int x, int y) {
        boolean isEmpty = false;
        boolean exists = false;
        try {
            exists = this.positionExists(x, y);
            isEmpty = this.positionIsEmpty(x, y);
        } catch (PositionDoesNotExistException e){
            Display.printWarning(e.getMessage());
            exists = false;
        } catch (CannotWalkOverException e) {
            Display.printWarning(e.getMessage());
            isEmpty = false;
        }
        return isEmpty && exists;
    }

    public void placeHero(Hero hero) {
        this.hero = hero;
        //coloca herói na posição x,y
    }

    public int[] getHeroPosition() {
        return this.hero.getPosition();
    }

    private void createStandardMap() {

        int[][] skeletonPositions = new int[][] {{12, 18}, {0, 0}, {3, 3},
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
}
