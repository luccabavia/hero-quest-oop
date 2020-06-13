package map;

import entity.Entity;
import io.ImportFromFile;

public class Map {

    private static Map mapInstance;
    private Entity[][] map;

    private Map(String mode) {
        this.importMap(mode);
    }

    private void importMap(String mode) {

        String currentDir = System.getProperty("user.dir");
        String path = String.format("%s/%s",
                currentDir,
                "heroquest/config/map/default_map_hero_quest.txt"
        );
//        path = "/Users/lucca.bavia/Development/MCC322/Projeto/hero-quest-oop" +
//                "/heroquest/config/map/test_map.txt";
        this.map = ImportFromFile.importMap(path);

    }

    public static Map getInstance(String mode) {

        if (mapInstance == null) {
            mapInstance = new Map(mode);
        }
        return mapInstance;

    }

    public boolean hasItem(int[] position) {
        return true;
    }

    public void drawMap() {
        StringBuilder s = new StringBuilder("");
        String piece = "?";
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++) {

                s.append(map[i][j].getSprite());
                if (j != map[0].length - 1) {
                    s.append(" ");
                }

            }
            s.append("\n");
        }
        System.out.println(s.toString());
    }

    public boolean isAvailable(int x, int y) {
        return true;
    }
}
