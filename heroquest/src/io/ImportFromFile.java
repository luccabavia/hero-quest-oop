package io;

import entity.Entity;
import entity.scenery.Door;
import entity.scenery.CorridorTile;
import entity.scenery.RoomTile;
import entity.scenery.Wall;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Import content from files in disk to the game during runtime.
 */
public class ImportFromFile {

    /**
     * Import content from a text file as String
     *
     * @param path String
     * @return a String with file's contents
     */
    public static String importTxt(String path) {

        File file = new File(path);
        return ImportFromFile.readTxt(file);

    }

    /**
     * Import contents of a map file from disk.
     * File content must be encoded as defined by the developers: 0 -
     * corridor floor tiles, 1 - walls, 2 - room floor tiles, 3 - doors.
     *
     * @param path String
     * @return a 3D array of entities
     */
    public static Entity[][][] importMap(String path) {

        String mapTxt = ImportFromFile.importTxt(path);
        return ImportFromFile.parseMap(mapTxt);
    }

    /**
     * Read text file content.
     *
     * @param file File
     * @return a String with file's contents
     */
    private static String readTxt(File file) {

        try {

            Scanner reader = new Scanner(file);
            StringBuilder text = new StringBuilder();
            while (true) {
                text.append(reader.nextLine());
                if (reader.hasNextLine()) {
                    text.append("\n");
                } else {
                    break;
                }

            }
            reader.close();
            return text.toString();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Parse imported map data, based on pattern defined by the developers.
     *
     * @param mapTxt String
     * @return a 3D array of entities
     */
    private static Entity[][][] parseMap(String mapTxt) {

        String[] lines = mapTxt.split("\n");
        String[][] position = new String[lines.length][];
        int k = 0;
        for (String s : lines) {
            position[k] = s.split(" ");
            k++;
        }

        Entity[][][] entityMap =
                new Entity[position.length][position[0].length][2];
        for (int i = 0; i < position.length; i++) {
            for (int j = 0; j < position[0].length; j++) {
                int entity = Integer.parseInt(position[i][j]);
                switch (entity) {
                    case 0:
                        entityMap[i][j][0] = new CorridorTile(i, j);
                        break;
                    case 1:
                        entityMap[i][j][0] = new Wall(i, j);
                        break;
                    case 2:
                        entityMap[i][j][0] = new RoomTile(i, j);
                        break;
                    case 3:
                        entityMap[i][j][0] = new Door(i, j);
                        break;
                }
            }
        }

        return entityMap;
    }

}
