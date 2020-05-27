package map;

import character.hero.Hero;
import java.util.Arrays;
import java.util.Scanner;

public class Map {

	private static String readMap[] = new String[20];
	private int map[][];
	//private static char map_comp[][] = new char[20][20];

	/*

	public static void importMap() {

		Scanner keyboard = new Scanner(System.in);
		int contador = 0;
		boolean running = true ;
		
		//Read of Map
		System.out.println(" Enter Map when finish press q to finish ");
		while ( running ) {
			String command = keyboard . nextLine ();
			if (command.compareTo ("q") == 0) {
				running = false ;
			}else {
				if (contador < 20)
					readMap[contador++] = command;
			}
		}
		//Retira os espaços de mapa
		for(contador = 0; contador < 20; contador++)
			readMap[contador] = readMap[contador].replace("  ", "");
		
		//Transforma em uma matriz de caracteres
		for(int i = 0; i < 20; i++)
			map[i] = readMap[i].toCharArray();
		
	}

	 */

	public void standardMap() {

		map = new int[7][7];
		map[3][3] = 1;
		map[3][2] = 1;
		map[3][1] = 1;
	}

	public void randomMap(){}

	//Fazer função
	public void drawMap() {

		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				if (map[i][j] == 0) {
					System.out.print("__");
				} else if (map[i][j] == 1) {
					System.out.print("XX");
				} else if (map[i][j] == -1) {
					System.out.print("HR");
				}
			}
			System.out.printf("\n");
		}
	}

	//Refazer hasItem
	public static boolean hasItem(int[] i) {
		return false;
	}

	public boolean placeHero(Hero h, int x, int y) {

		// Check if the position is available
		if (positionExists(x, y)) {
			if (positionIsEmpty(x, y)) {

				//Set the robot's current position to empty
				positionSetEmpty(h.getX(), h.getY());

				//Set the new robot's position
				positionSetRobot(x, y);

				return true;
			}
		}

		return false;
	}

	private boolean positionExists(int x, int y) {
		return x >= 0 && y >= 0 && x < map[0].length && y < map[1].length;
	}

	private boolean positionIsEmpty(int x, int y) {
		return map[x][y] == 0;
	}

	private void positionSetEmpty(int x, int y) {
		positionSet(x, y, 0);
	}

	private void positionSetRobot(int x, int y) {
		positionSet(x, y, -1);
	}

	private void positionSet(int x, int y, int value) {
		map[x][y] = value;
	}

	public void startPosition(Hero h) {
		h.setPosition(0,0);
	}
}
